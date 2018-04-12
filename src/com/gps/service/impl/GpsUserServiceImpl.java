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

import com.gps.dao.GpsUserDao;
import com.gps.exceptions.GPSException;
import com.gps.service.GpsUserService;
import com.gps.util.StringUtils;
import com.gps.vo.GpsUser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class provides implementation of ACLService.
 *
 * @authorWaqar Malik
 */
@Service
public class GpsUserServiceImpl implements GpsUserService {
    private static Logger log = Logger.getLogger(GpsUserServiceImpl.class);

    @Autowired
    GpsUserDao gpsUserDao;


    @Override
    public List<GpsUser> getEmails() throws GPSException {
        log.debug("list all emails...");
        return gpsUserDao.listEmails();
    }


    @Override
    public GpsUser getUserById(Integer userId) throws GPSException {
        GpsUser user = gpsUserDao.getUserById(userId);
        if (user != null && StringUtils.isBlank(user.getNotesId())) {
            user.setNotesId(com.gps.util.BluePages.getNotesIdFromIntranetId(user.getEmail()));
        }
        return user;
    }

    @Override
    public GpsUser getUserByIntranetId(String intranetId) throws GPSException {
        try {
            return gpsUserDao.getGpsUserByEmail(intranetId);
        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void addUser(GpsUser dbUser) {
        try {
            gpsUserDao.addGpsUser(dbUser);
        } catch (Exception e) {
            //throw new GPSException(e.getMessage(), e);
            log.error(e.getMessage(), e);
        }
    }


}
