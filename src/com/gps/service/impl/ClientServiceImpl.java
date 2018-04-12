
package com.gps.service.impl;


import com.gps.dao.ContractDao;
import com.gps.dao.EmailTemplateDao;
import com.gps.dao.GpsUserDao;
import com.gps.dao.JdbcDao;
import com.gps.exceptions.GPSException;
import com.gps.service.ClientService;
import com.gps.service.SessionACLService;
import com.gps.service.UserRoleService;
import com.gps.util.CommonUtil;
import com.gps.util.DateMigratorWrapper;
import com.gps.util.GPSMailer;
import com.gps.util.StringUtils;
import com.gps.vo.*;
import com.gps.vo.helper.Constant;
import com.gps.vo.helper.UserVo;
import com.ibm.swat.password.cwa2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.naming.NamingException;
import javax.servlet.http.HttpSession;

import java.sql.Timestamp;
import java.util.*;

@Service("clientCommonService")
public class ClientServiceImpl implements ClientService {

    private static Logger log = Logger.getLogger(ClientServiceImpl.class);

    @Autowired
    JdbcDao commonJdbc;

    @Autowired
    GpsUserDao gpsUserDao;

    @Autowired
    ContractDao contractDao;

    @Autowired
    EmailTemplateDao emailTemplateDao;
    
    @Autowired
    GPSMailer gpsMailer;
    
    @Autowired
    SessionACLService sessionACLService;
    
    @Autowired
    UserRoleService userRoleService;

    @Value(value = "#{'${password.authentication}'}")
    private String passAuthentic;
    
    @Override
    @Transactional
    public DateMigratorWrapper loginUser(DateMigratorWrapper dateMigratorWrapper) {
        UserVo userVo = dateMigratorWrapper.getUserVo();
        if (StringUtils.isNotBlank(userVo.getUserName())) {
            userVo.setUserName(StringUtils.toLowerCase(userVo.getUserName()));
        }
        log.info("login in attempt by: " + userVo.getUserName());
        //set true to let application deployed on sun jre.
        // true disables password authentication on some machines. check for exception.
        Boolean isVerified = verifyPassword(userVo.getUserName(), userVo.getPassword());
        dateMigratorWrapper.setPasswordVerified(isVerified);
        if (isVerified) {
            GpsUser gpsUser = null;
            try {
                gpsUser = gpsUserDao.getGpsUserByEmail(userVo.getUserName());
            } catch (GPSException e) {
                log.error(e.getMessage(), e);
            }
            dateMigratorWrapper.setGpsUser(gpsUser);
            if (gpsUser != null) {
                log.info("successfully loged in user: " + gpsUser.getUserId() + ", " + gpsUser.getEmail());

                // log user roles
                List<UserRole> userRoles = userRoleService.getUserRolesByUserId(gpsUser.getUserId());
                List<SessionAcl> listSessionACL = getContractsByUserRoles(new HashSet<UserRole>(userRoles));
                try {
                    sessionACLService.storeSessionAcl(listSessionACL, dateMigratorWrapper.getSessionId(), gpsUser);
                } catch (GPSException e) {
                    log.warn("ACL information not saved in db for: " + gpsUser.getEmail());
                }
                dateMigratorWrapper.setListSessionACL(listSessionACL);
                dateMigratorWrapper.setHasError(false);
            } else {
                log.error("I assume user exist in bluepages. let user go to support.");
                gpsUser = new GpsUser();
                gpsUser.setEmail(userVo.getUserName());
                dateMigratorWrapper.setGpsUser(gpsUser);
                GpsUser dbUser = gpsUserDao.getGpsUserByEmail(userVo.getUserName());
                if (dbUser != null) {
                    List<UserRole> roles = userRoleService.getUserRolesByUserId(dbUser.getUserId());
                    if (CollectionUtils.isNotEmpty(roles)) {
                        dateMigratorWrapper.setHasError(false);
                    }
                } else {
                    dateMigratorWrapper.setHasError(true);
                }

            }
        }
        log.debug("login attempt results: blue pages = " + dateMigratorWrapper.isPasswordVerified() + ", errors = " + dateMigratorWrapper.isHasError());
        log.debug("logging in service processing finished.");
        return dateMigratorWrapper;
    }

    /**
     * 
     * @param intranetId
     * @return
     * @author Muhammad Attique
     */
    @Override
    public String getUserRole(String intranetId){
    	 String role = "";
        try {
        	GpsUser gpsUser = gpsUserDao.getGpsUserByEmail(intranetId);
            List<UserRole> userRoles = userRoleService.getUserRolesByUserId(gpsUser.getUserId());
            
            if(userRoles != null && userRoles.size() > 0){
            	if (CollectionUtils.isNotEmpty(userRoles)) {
                    for (UserRole userRole : userRoles) {
                        if (userRole.getRole().equalsIgnoreCase("admin")) {
                            role =  userRole.getRole();
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        log.debug("role= "+role);
        return role;
    }
    
    @Override
    public void genOTPassword(UserVo userVo, HttpSession session){
    	log.debug("genOTPassword()..."+userVo.getUserName());
    	EmailTemplate emailTemplate = emailTemplateDao.getEmailTemplateByName("LOGIN_OTP");
    	//Generate otp password
    	String passCode = CommonUtil.genPassword(6);
    	Hashtable<String, String> keys = new Hashtable<String, String>();
    	keys.put("passCode", passCode);
    	
    	String body = StringUtils.replaceAll(emailTemplate.getBody(), keys);
        String subject = StringUtils.replaceAll(emailTemplate.getSubject(), keys);
        List<String> recipients = new ArrayList<String>();
        recipients.add(userVo.getUserName());
        
        List<String> ccList = new ArrayList<String>();
        
        //dispatch otp
        try {
			gpsMailer.sendEmail(emailTemplate.getFromAlias(), subject, body, recipients, ccList);
		} catch (MessagingException e) {
			log.error("MessagingException: "+e.getMessage(), e);
		} catch (Exception e) {
			log.error("Exception: "+e.getMessage(), e);
		}
        //store otp for verification
    	session.setAttribute(Constant.OTP_CODE, passCode);
    	Calendar now = Calendar.getInstance();
    	session.setAttribute(Constant.OTP_TIME, now);
    }
    
    /**
     * verifies the userId, Password from blue pages.
     *
     * @param userName
     * @param password
     * @return
     */
    public Boolean verifyPassword(String intranetId, String password) {
        Boolean bluepagesVerify = Boolean.parseBoolean(passAuthentic);
        Boolean isVerified = Boolean.TRUE;
        if (bluepagesVerify) {
            try {
                cwa2 CWA2 = new cwa2("bluepages.ibm.com:636", "bluegroups.ibm.com:636");
                //cwa2 CWA2 = new cwa2("bluepages.ibm.com", "bluegroups.ibm.com");
                try {
                    isVerified = CWA2.authenticate_throw(intranetId, password);
                } catch (NamingException ne) {
                    isVerified = Boolean.FALSE;
                    log.debug(ne.getMessage(), ne);
                    log.info("Invalid Credentials. " + intranetId + " was denied by bluepages");
                }
            } catch (NoClassDefFoundError e) {
                isVerified = Boolean.FALSE;
                log.error(e.getMessage(), e);
            }
        }
        return isVerified;
    }

    private Integer swapLevel(Integer previous, String current) {
        Integer currentLevel = Integer.parseInt(current);
        if (previous > currentLevel) {
            currentLevel = previous;
        }
        return currentLevel;

    }

    class AclKey {
        String form;
        Integer contractId;

        @Override
        public boolean equals(Object obj) {
            boolean equal = false;
            if (obj instanceof AclKey) {
                if (this.contractId != null && this.form != null) {
                    AclKey reference = (AclKey) obj;
                    if (reference.contractId != null && this.contractId.intValue() == reference.contractId.intValue()) {
                        if (this.form.trim().equalsIgnoreCase(reference.form.trim())) {
                            log.info("AclKey equal: contractId=" + contractId + ", form=" + form);
                            equal = true;
                        }
                    }
                } else {
//					log.info("AclKey not equal: contractId="+contractId+", form="+form);
                }
            }
            return equal;
        }

        @Override
        public int hashCode() {
            int result = 5;
            if (contractId != null) {
                result = 20 * result + contractId.hashCode();
            }
            if (form != null) {
                result = 20 * result + form.hashCode();
            }
            return result;
        }
    }

    class UserRoleKey {
        String form;
        Integer contractId;
        String role;

        @Override
        public boolean equals(Object obj) {
            boolean equal = false;
            if (obj instanceof UserRoleKey) {
                if (this.contractId != null && this.form != null) {
                    UserRoleKey reference = (UserRoleKey) obj;
                    if (reference.contractId != null && this.contractId.intValue() == reference.contractId.intValue()) {
                        if (this.form.trim().equalsIgnoreCase(reference.form.trim())) {
                            if (this.role.trim().equalsIgnoreCase(reference.form.trim())) {
                                log.info("UserRoleKey equal: contractId=" + contractId + ", form=" + form);
                                equal = true;
                            }
                        }
                    }
                } else {
//					log.info("AclKey not equal: contractId="+contractId+", form="+form);
                }
            }
            return equal;
        }

        @Override
        public int hashCode() {
            int result = 5;
            if (contractId != null) {
                result = 20 * result + contractId.hashCode();
            }
            if (form != null) {
                result = 20 * result + form.hashCode();
            }
            if (role != null) {
                result = 20 * result + role.hashCode();
            }
            return result;
        }
    }

    @Override
    public List<SessionAcl> getContractsByAcls(Set<AccessControlList> acls) {
        log.debug("getContractsByAcls()......");
        List<SessionAcl> contractIds = new ArrayList<SessionAcl>();
        Map<AclKey, SessionAcl> aclMap = new HashMap<AclKey, SessionAcl>();
        if (acls == null) {
            return contractIds;
        }

        StringBuilder query = new StringBuilder("FROM Contract c WHERE 1=1  ");
        StringBuilder contracts = new StringBuilder("");


        contollist:
        for (AccessControlList acl : acls) {

            //clear query buffers.
            query = new StringBuilder("FROM Contract c WHERE 1=1  ");
            contracts = new StringBuilder("");


            log.debug("access id = " + acl.getAclId() + ", level=" + acl.getActiveAccessLevel());
            // check for Global admin.
            if (acl.getActiveAccessLevel() != null && !acl.getActiveAccessLevel().isEmpty()) {
                if (acl.getActiveAccessLevel().equals("32")) {
                    log.debug("user is a admin....");
                }
            }

            if (acl.getContract() != null) {
                if (!contracts.toString().equals("")) {
                    contracts.append(", ");
                }
                contracts.append(acl.getContract().getContractId());
            }

            // build the query.
            if (!contracts.toString().equals("")) {
                log.debug("contracts = " + contracts);
                query.append(" and c.contractId in ( " + contracts.toString() + " ) ");
            }

            addAll(aclMap, getSessionAcls(query.toString(), acl));

        }


        contractIds.addAll(aclMap.values());
        return contractIds;
    }

    @Override
    public List<SessionAcl> getContractsByUserRoles(Set<UserRole> userRoles) {
        log.debug("getContractsByUserRoles()......");
        List<SessionAcl> contractIds = new ArrayList<SessionAcl>();
        Map<UserRoleKey, SessionAcl> urMap = new HashMap<UserRoleKey, SessionAcl>();
        if (userRoles == null) {
            return contractIds;
        }

        StringBuilder query = new StringBuilder("FROM Contract c WHERE 1=1  ");
        StringBuilder contracts = new StringBuilder("");


        contollist:
        for (UserRole ur : userRoles) {

            //clear query buffers.
            query = new StringBuilder("FROM Contract c WHERE 1=1  ");
            contracts = new StringBuilder("");


            log.debug("access id = " + ur.getUrId() + ", role=" + ur.getRole());

            // check for Global admin.
            if (ur.getRole().equalsIgnoreCase("admin") && ur.getContract() == null) {
                log.debug("user is a admin....");
            }
            //}

            if (ur.getContract() != null) {
                if (!contracts.toString().equals("")) {
                    contracts.append(", ");
                }
                contracts.append(ur.getContract().getContractId());
            }

            // build the query.
            if (!contracts.toString().equals("")) {
                log.debug("contracts = " + contracts);
                query.append(" and c.contractId in ( " + contracts.toString() + " ) ");
            }

            addAllUr(urMap, getSessionAcls(query.toString(), ur));

        }


        contractIds.addAll(urMap.values());
        return contractIds;
    }

    private void addAll(Map<AclKey, SessionAcl> aclMap, List<SessionAcl> acls) {
        AclKey aclKey;
        for (SessionAcl sessionAcl : acls) {
            aclKey = new AclKey();
            aclKey.form = sessionAcl.getFormType();
            aclKey.contractId = sessionAcl.getContract().getContractId();
            SessionAcl entry = aclMap.get(aclKey);

            if (entry != null) {
//				log.debug("Entry found for: contractId="+entry.getContract().getContractId()+", form="+entry.getFormType());
                if (sessionAcl.getActiveAccessLevel() > entry.getActiveAccessLevel() && sessionAcl.getApprovedAccessLevel() > entry.getApprovedAccessLevel()) {
//					log.debug("Replacing Entry => Level {"+entry .getActiveAccessLevel() +", "+entry .getApprovedAccessLevel()+"} sessionAcl => Level {"+sessionAcl .getActiveAccessLevel() +", "+sessionAcl.getApprovedAccessLevel()+"}");
                    aclMap.put(aclKey, sessionAcl);
                } else {
//					log.debug("Entry found => Level {"+entry .getActiveAccessLevel() +", "+entry .getApprovedAccessLevel()+"} sessionAcl => Level {"+sessionAcl .getActiveAccessLevel() +", "+sessionAcl.getApprovedAccessLevel()+"}");
                }
            } else {
//				log.debug("Addin ACL entry: contractId="+sessionAcl.getContract().getContractId()+", form="+sessionAcl.getFormType());
                aclMap.put(aclKey, sessionAcl);
            }
        }
    }

    private void addAllUr(Map<UserRoleKey, SessionAcl> aclMap, List<SessionAcl> acls) {
        UserRoleKey userRoleKey;
        for (SessionAcl sessionAcl : acls) {
            userRoleKey = new UserRoleKey();
            userRoleKey.form = sessionAcl.getFormType();
            userRoleKey.contractId = sessionAcl.getContract().getContractId();
            userRoleKey.role = sessionAcl.getRole();
            SessionAcl entry = aclMap.get(userRoleKey);

            //	if (entry != null) {
            aclMap.put(userRoleKey, sessionAcl);
            //		}
        }
    }

    /**
     * Helper method.
     *
     * @param query
     * @param acl
     * @return
     * @authorWaqar Malik
     */
    private List<SessionAcl> getSessionAcls(String query, AccessControlList acl) {
        Integer activeAccessLevel = -1;
        Integer approvedAccessLevel = -1;
        List<SessionAcl> contractIds = new ArrayList<SessionAcl>();

        if (acl.getActiveAccessLevel() != null && !acl.getActiveAccessLevel().isEmpty()) {
            activeAccessLevel = swapLevel(activeAccessLevel, acl.getActiveAccessLevel());
        }
        if (acl.getApprovedAccessLevel() != null && !acl.getApprovedAccessLevel().isEmpty()) {
            approvedAccessLevel = swapLevel(approvedAccessLevel, acl.getApprovedAccessLevel());
        }
        if (activeAccessLevel < 0 || approvedAccessLevel < 0) {
            return contractIds;
        }
        log.debug("Executing query for ACL: " + query);
        List<Contract> contractList = contractDao.getContractsByQuery(query.toString());
        log.debug("number of contracts returned by ACL query = " + contractList.size());

        SessionAcl sessionACL = null;
        for (Contract contract : contractList) {
            sessionACL = new SessionAcl();
            sessionACL.setContract(contract);
            sessionACL.setActiveAccessLevel(activeAccessLevel);
            sessionACL.setApprovedAccessLevel(approvedAccessLevel);
            sessionACL.setFormType(acl.getFormType().trim());
            sessionACL.setSignInTime(new Timestamp(new Date().getTime()));
            contractIds.add(sessionACL);
        }
        return contractIds;
    }

    private List<SessionAcl> getSessionAcls(String query, UserRole userRole) {
        List<SessionAcl> contractIds = new ArrayList<SessionAcl>();

        log.debug("Executing query for ACL: " + query);
        List<Contract> contractList = contractDao.getContractsByQuery(query.toString());
        log.debug("number of contracts returned by ACL query = " + contractList.size());

        SessionAcl sessionACL = null;
        for (Contract contract : contractList) {
            sessionACL = new SessionAcl();
            sessionACL.setContract(contract);
            sessionACL.setRole(userRole.getRole());
            sessionACL.setFormType("RCA");
            sessionACL.setSignInTime(new Timestamp(new Date().getTime()));
            contractIds.add(sessionACL);
        }
        return contractIds;
    }


}
