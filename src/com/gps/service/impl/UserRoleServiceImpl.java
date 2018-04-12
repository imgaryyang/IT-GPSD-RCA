/**
 * <pre>
 * ==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 * ==========================================================================
 *
 *    FILE: ACLServiceImpl.java
 *    CREATOR:Waqar Malik
 *    DEPT: GBS PAK
 *    DATE: 19/07/2013
 *
 * -PURPOSE-----------------------------------------------------------------
 *
 * --------------------------------------------------------------------------
 *
 *
 * -CHANGE LOG--------------------------------------------------------------
 * 19/07/2013Waqar Malik Initial coding.
 * ==========================================================================
 * </pre>
 */
package com.gps.service.impl;

import com.gps.dao.ContractDao;
import com.gps.dao.GpsUserDao;
import com.gps.dao.UserRoleDao;
import com.gps.exceptions.GPSException;
import com.gps.service.UserRoleService;
import com.gps.util.BluePages;
import com.gps.util.StringUtils;
import com.gps.vo.Contract;
import com.gps.vo.GpsUser;
import com.gps.vo.UserRole;
import com.gps.vo.helper.Constant;
import com.gps.vo.helper.UserRoleForm;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class provides implementation of UserRoleService.
 *
 * @author Waqar Malik
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
    private static Logger log = Logger.getLogger(UserRoleServiceImpl.class);

    @Autowired
    UserRoleDao userRoleDao;

    @Autowired
    GpsUserDao gpsUserDao;


    @Autowired
    ContractDao contractDao;


    @Override
    public UserRole loadUserRole(Integer urId) throws GPSException {
        return userRoleDao.getUserRoleById(urId);
    }

    @Override
    @Transactional
    public Boolean deleteUserRole(Integer urId, Integer loggedInUser) throws GPSException {
        Boolean droped = Boolean.FALSE;
        UserRole userRole = null;
        if (urId == null || urId == 0) {
            return droped;
        }
        log.info("Droping UserRole: " + urId);
        userRole = userRoleDao.getUserRoleById(urId);
        if (userRole != null) {
            log.debug("Deleting UserRole: " + userRole.getUrId());
            userRole.setIsEnabled(Constant.NO);
            Timestamp now = new Timestamp(new Date().getTime());
            userRole.setDisabledOn(now);
            userRole.setDisabledBy(loggedInUser);
            userRoleDao.softDeleteUserRole(userRole);
            droped = Boolean.TRUE;
        }
        return droped;
    }

    @Override
    @Transactional
    public boolean processUserRole(UserRoleForm userRoleForm, String command) throws GPSException {
        return false;
    }

    @Override
    @Transactional
    public boolean processUserRole(Integer urId, String email, Integer contractId, String role) throws GPSException {
        boolean result = false;
        if (email == null || email.isEmpty()) {
            log.error("email found empty. can't do any thing...??");
            return result;
        }
        UserRoleForm userRoleForm = new UserRoleForm();
        GpsUser user = loadUserDetails(email);

        //check if contract specific role
        if (contractId > 0) {
            UserRole userRole = userRoleDao.getUserRolesByContractIdAndUserIdAndRole(contractId, user.getUserId(), role);
            if (userRole == null) {
                userRole = new UserRole();
                userRole.setGpsUser(user);
                userRole.setIsEnabled(Constant.YES);
                userRole.setContract(contractDao.getContractById(contractId));
                userRole.setUrId(urId);
                userRole.setRole(role);
                userRoleForm.setUserRole(userRole);
                result = addUserRole(userRoleForm);
                return result;
            }
        } else {
            UserRole userRole = userRoleDao.findGlobalRole(user.getUserId(), role);
            if (userRole == null) {
                userRole = new UserRole();
                userRole.setIsEnabled(Constant.YES);
                userRole.setGpsUser(user);
                userRole.setUrId(urId);
                userRole.setRole(role);
                userRoleForm.setUserRole(userRole);
                result = addUserRole(userRoleForm);
                return result;
            }


        }
        return result;
    }

    @Override
    public List<UserRole> getUserRolesByUserId(Integer userId) throws GPSException {
        return userRoleDao.getUserRolesByUser(userId);
    }

    @Override
    public List<UserRole> getUserRolesByContractId(Integer contractId) throws GPSException {
        return null;
    }

    @Override
    public List<GpsUser> getAllUsersHavingRole() throws GPSException {
        return gpsUserDao.listUsersHavingRoles();
    }

    @Override
    public List<UserRole> getUserRolesByContractIdAndRole(Integer contractId, String role) {
        List<UserRole> roles = userRoleDao.getUserRolesByContractIdAndRole(contractId, role);
        if (CollectionUtils.isNotEmpty(roles)) {
            int i = 0;
            for (UserRole urole : roles) {
                if (StringUtils.isBlank(urole.getGpsUser().getNotesId())) {
                    roles.get(i).getGpsUser().setNotesId(BluePages.getNotesIdFromIntranetId(urole.getGpsUser().getEmail()));
                }
                i++;
            }
        }
        return roles;
    }

    @Override
    public List<UserRole> getUserRolesByContractIdAndUserId(Integer contractId, Integer userId) {
        return userRoleDao.getUserRolesByContractIdAndUserId(contractId, userId);
    }

    @Override
    public UserRole getUserRolesByContractIdAndUserIdAndRole(Integer contractId, Integer userId, String role) {
        return userRoleDao.getUserRolesByContractIdAndUserIdAndRole(contractId, userId, role);
    }

    @Override
    public void saveUserRole(UserRole userRole) {
        userRoleDao.addUserRole(userRole);
    }

    @Override
    public UserRole getGlobalRole(String role) {
        List<UserRole> userRoles = userRoleDao.getUserRolesByRoleName(role);
        if (CollectionUtils.isNotEmpty(userRoles)) {
            for (UserRole userRole : userRoles) {
                if (userRole.getRole().equalsIgnoreCase(role) && userRole.getContract() == null) {
                    return userRole;
                }
            }
        }
        return null;
    }

    @Override
    public List<UserRole> getGlobalCoordinators() {
        List<UserRole> globalCoordinators = new ArrayList<UserRole>();
        List<UserRole> userRoles = userRoleDao.getUserRolesByRoleName("coordinator");
        if (CollectionUtils.isNotEmpty(userRoles)) {
            for (UserRole userRole : userRoles) {
                if (userRole.getContract() == null) {
                    if(StringUtils.isBlank(userRole.getGpsUser().getNotesId())){
                        userRole.getGpsUser().setNotesId(BluePages.getNotesIdFromIntranetId(userRole.getGpsUser().getEmail()));
                    }
                    globalCoordinators.add(userRole);
                }
            }
        }
        return globalCoordinators;
    }

    @Override
    public List<UserRole> getDpeListByContractIdAndRole(Integer contractId, String dpeRole) {
        List<String> userEmails = new ArrayList<String>();
        List<UserRole> dpeList = new ArrayList<UserRole>();
        List<UserRole> roles = userRoleDao.getUserRolesByRoleName(dpeRole);
        if (CollectionUtils.isNotEmpty(roles)) {
            for (UserRole role : roles) {
                if (!userEmails.contains(role.getGpsUser().getEmail()) && (role.getContract() == null || (role.getContract() != null && role.getContract().getContractId().equals(contractId)))) {
                    if (StringUtils.isBlank(role.getGpsUser().getNotesId())) {
                        role.getGpsUser().setNotesId(BluePages.getNotesIdFromIntranetId(role.getGpsUser().getEmail()));
                    }
                    dpeList.add(role);

                    userEmails.add(role.getGpsUser().getEmail());
                }
            }
        }
        return dpeList;
    }

    @Override
    public List<UserRole> getEditorListByContractIdAndRole(Integer contractId, String editorRole) {
        List<String> userEmails = new ArrayList<String>();
        List<UserRole> editorList = new ArrayList<UserRole>();
        List<UserRole> roles = userRoleDao.getUserRolesByRoleName(editorRole);
        if (CollectionUtils.isNotEmpty(roles)) {
            for (UserRole role : roles) {
                if (!userEmails.contains(role.getGpsUser().getEmail()) && (role.getContract() == null || (role.getContract() != null && role.getContract().getContractId().equals(contractId)))) {
                    if (StringUtils.isBlank(role.getGpsUser().getNotesId())) {
                        role.getGpsUser().setNotesId(BluePages.getNotesIdFromIntranetId(role.getGpsUser().getEmail()));
                    }
                    editorList.add(role);
                    userEmails.add(role.getGpsUser().getEmail());
                }
            }
        }
        return editorList;
    }


    private boolean addUserRole(UserRoleForm userRoleForm) {
        log.debug("adding user role entry/logon.");
        boolean result = false;
        UserRole userRole = userRoleForm.getUserRole();
        loadUserDetail(userRole);


        if (userRole.getUrId() != null) {
            userRoleDao.updateUserRole(userRole);
        } else {
            userRoleDao.addUserRole(userRole);
        }
        result = true;
        return result;
    }

    private void loadUserDetail(UserRole userRole) {
        GpsUser user = null;
        try {
            user = gpsUserDao.getGpsUserByEmailNoFetch(userRole.getGpsUser().getEmail());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (user == null) {
            log.debug("adding bpd user: " + userRole.getGpsUser().getEmail());
            gpsUserDao.addGpsUser(userRole.getGpsUser());
        } else {
            userRole.setGpsUser(user);
        }
    }

    private GpsUser loadUserDetails(String email) {
        GpsUser user = gpsUserDao.getGpsUserByEmail(email);
        if (user == null) {
            user = new GpsUser();
            user.setEmail(email);
            user.setNotesId(BluePages.getNotesIdFromIntranetId(email));
            gpsUserDao.addGpsUser(user);
        } else {
            if (StringUtils.isBlank(user.getNotesId()))
                user.setNotesId(BluePages.getNotesIdFromIntranetId(email));
            //
        }
        return user;
    }

}
