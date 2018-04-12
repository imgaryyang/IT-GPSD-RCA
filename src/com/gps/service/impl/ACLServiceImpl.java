/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: ACLServiceImpl.java
 *    CREATOR:Waqar Malik
 *    DEPT: GBS PAK
 *    DATE: 19/07/2013
 *
 *-PURPOSE-----------------------------------------------------------------
 * 
 *--------------------------------------------------------------------------
 *
 *
 *-CHANGE LOG--------------------------------------------------------------
 * 19/07/2013Waqar Malik Initial coding.
 *==========================================================================
 * </pre> */
package com.gps.service.impl;

import com.gps.dao.*;
import com.gps.exceptions.GPSException;
import com.gps.service.ACLService;
import com.gps.util.StringUtils;
import com.gps.vo.*;
import com.gps.vo.helper.ACLForm;
import com.gps.vo.helper.Constant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides implementation of ACLService.
 *  
 * @authorWaqar Malik
 */
@Service
public class ACLServiceImpl implements ACLService {
	private static Logger log = Logger.getLogger(ACLServiceImpl.class);
	
	@Autowired
	AccessControlListDao accessControlListDao;
	
	@Autowired
	GpsUserDao gpsUserDao;
	
	@Autowired
	GroupGpsuserDao groupGpsuserDao;
	

    @Autowired
    ContractDao contractDao;

	@Override
	public AccessControlList loadAcl(Integer aclId) throws GPSException {
		AccessControlList accessControlList =  null;
		if(aclId == null || aclId == 0){
			return accessControlList;
		}
		accessControlList = accessControlListDao.getACLById(aclId);
		log.debug("Acl: "+accessControlList.getFormType()+", "+accessControlList.getActiveAccessLevel());
		return accessControlList;
	}
	

	@Override
	@Transactional
	public Boolean deleteAcl(Integer aclId) throws GPSException {
		Boolean droped = Boolean.FALSE;
		AccessControlList accessControlList =  null;
		if(aclId == null || aclId == 0){
			return droped;
		}
		log.info("Droping ACL: "+aclId);
		accessControlList = accessControlListDao.getACLById(aclId);
		if(accessControlList != null){
			log.debug("Deleting ACL: "+accessControlList.toCsv());
			accessControlListDao.deleteAccessControlList(accessControlList);
			droped = Boolean.TRUE;
		}
		return droped;
	}
	
	@Override
	public List<GpsUser> getSingleContractAcls() throws GPSException {
		return gpsUserDao.listSingleContractAcls();
	}
	
	@Override
	public List<AccessControlList> getNonContractAcls() throws GPSException {
		return accessControlListDao.listNonContractAcls();
	}

	@Override
	public List<AccessControlList> getAclsByUserId(Integer userId) throws GPSException {
		List<AccessControlList> listAcls = null;
		if(userId == null){
			return listAcls;
		}
		listAcls = accessControlListDao.getAclsByUserId(userId);
//		for(AccessControlList access : listAcls){
//			log.debug("Access "+access.getGpsUser().getEmail()+" :"+access.getActiveAccessLevel()+", "+access.getApprovedAccessLevel());
//		}
		
		return listAcls;
	}

		
	
	@Override
	@Transactional
	public boolean processACL(ACLForm aCLForm, String command) throws GPSException {
		boolean result = false;
		log.error("ACL command = "+command);
		if(command == null || command.isEmpty()){
			log.error("ACL command found empty. can't do any thing...??");
			return result;
		}
		if(Constant.ADD_SINGLE_CONTRACT_ACL.equalsIgnoreCase(command)){
			result = addSingleAcl(aCLForm);
		}else if (Constant.ADD_GROUP_ACL.equalsIgnoreCase(command)){
			result = addGroupAcl(aCLForm);
		}
		return result;
	}
	
	@Override
	@Transactional
	public boolean processSingleACL(Integer aclId, String email, Integer contractId, String formType, String activeLevel, String awaitingLevel, String approvedLevel) throws GPSException {
		boolean result = false;
		if(email == null || email.isEmpty()){
			log.error("email found empty. can't do any thing...??");
			return result;
		}
		ACLForm aCLForm = new ACLForm();
		GpsUser GpsUser = new GpsUser();
		GpsUser.setEmail(email);
		AccessControlList acl = new AccessControlList();
		acl.setAclId(aclId);
		acl.setFormType(formType);
        acl.setActiveAccessLevel(activeLevel);
        acl.setAwaitingAccessLevel(awaitingLevel);
		acl.setApprovedAccessLevel(approvedLevel);
        if(!(awaitingLevel.equals("32") || activeLevel.equals("32") || approvedLevel.equals("32")) )  {
            Contract contract = new Contract();
            contract.setContractId(contractId);
            acl.setContract(contract);
        }
		acl.setGpsUser(GpsUser);
		aCLForm.setAcl(acl);
		result = addSingleAcl(aCLForm);
		return result;
		
	}
	
	@Override
	@Transactional
	public boolean processNonContractACL(Integer aclId, String email, String formType, String customer, String activeLevel, String awaitingLevel, String approvedLevel) throws GPSException{
		boolean result = false;
		if(email == null || email.isEmpty()){
			log.error("email found empty. can't do any thing...??");
			return result;
		}
		ACLForm aCLForm = new ACLForm();
		GpsUser GpsUser = new GpsUser();
		GpsUser.setEmail(email);
		AccessControlList acl = new AccessControlList();
		acl.setAclId(aclId);
		acl.setFormType(formType);
		acl.setActiveAccessLevel(activeLevel);
		acl.setApprovedAccessLevel(approvedLevel);
        acl.setAwaitingAccessLevel(awaitingLevel);
        acl.setGpsUser(GpsUser);

		aCLForm.setGroup(acl);
		result = addGroupAcl(aCLForm);
		return result;
	}
	
	/**
	 * 
	 * @param aCLForm
	 * @return
	 * @authorWaqar Malik.
	 */
	private boolean addSingleAcl(ACLForm aCLForm){
		boolean result = false;
		log.debug("adding single contract entry/logon");
		AccessControlList acl = aCLForm.getAcl();
        if(StringUtils.isNotBlank(acl.getGpsUser().getEmail())){
            acl.getGpsUser().setEmail(StringUtils.toLowerCase(acl.getGpsUser().getEmail()));
        }
		log.debug("email = "+acl.getGpsUser().getEmail());
        loadContractDetails(acl);
        loadUserDetail(acl);
        if(acl.getAclId() != null ){
			accessControlListDao.updateAccessControlList(acl);
		}else{
			accessControlListDao.addAccessControlList(acl);
		}
		result = true;
		return result;
	}

    private void loadUserDetail(AccessControlList acl) {
        GpsUser user = null;
        try{
            user = gpsUserDao.getGpsUserByEmailNoFetch(acl.getGpsUser().getEmail());
        } catch(Exception e){
            log.error(e.getMessage(),e);
        }
        if(user == null){
            log.debug("adding bpd user: "+acl.getGpsUser().getEmail());
            gpsUserDao.addGpsUser(acl.getGpsUser());
        }else{
            acl.setGpsUser(user);
        }
    }

    private void loadContractDetails(AccessControlList acl) {
        Contract contract = null;
        try {
            if(acl.getContract() != null && acl.getContract().getContractId() > 0){
                contract = contractDao.getContractById(acl.getContract().getContractId());
            }
         }
        catch (Exception e){
            log.error(e.getMessage(),e);
        }
        if(contract != null) {
            acl.setContract(contract);
        }
    }

    /**
	 * 
	 * @param aCLForm
	 * @return
	 * @authorWaqar Malik.
	 */
	private boolean addGroupAcl(ACLForm aCLForm){
		log.debug("adding global entry/logon.");
		boolean result = false;
		AccessControlList group = aCLForm.getGroup();
        loadUserDetail(group);
		if(group.getAclId() != null ){
			accessControlListDao.updateAccessControlList(group);
		}else{
			accessControlListDao.addAccessControlList(group);
		}
		
		result = true;
		return result;
	}
	

	

}
