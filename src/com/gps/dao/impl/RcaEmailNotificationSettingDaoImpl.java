package com.gps.dao.impl;

import com.gps.dao.RcaEmailNotificationSettingDao;
import com.gps.vo.RcaEmailNotificationSetting;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by Waqar Malik on 15-12-2014.
 */
@Repository
@Transactional(propagation= Propagation.REQUIRED)
public class RcaEmailNotificationSettingDaoImpl implements RcaEmailNotificationSettingDao {
    private static final Logger log = Logger.getLogger(RcaEmailNotificationSettingDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void addRcaEmailNotificationSetting(RcaEmailNotificationSetting rcaEmailNotificationSetting) {
        try {
            entityManager.persist(rcaEmailNotificationSetting);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void updateRcaEmailNotificationSetting(RcaEmailNotificationSetting rcaEmailNotificationSetting) {
        try {
            entityManager.merge(rcaEmailNotificationSetting);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void deleteRcaEmailNotificationSetting(RcaEmailNotificationSetting rcaEmailNotificationSetting) {
        try {
            entityManager.remove(rcaEmailNotificationSetting);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public RcaEmailNotificationSetting getRcaEmailNotificationSettingByContractId(Integer contractId) {
        StringBuilder builder = new StringBuilder();
        RcaEmailNotificationSetting rcaEmailNotificationSetting = null;
        try {
            builder.append("SELECT r FROM RcaEmailNotificationSetting r ");
            builder.append("LEFT JOIN FETCH r.contract c ");
            builder.append("WHERE c.contractId=:contractId ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("contractId", contractId);
            rcaEmailNotificationSetting = (RcaEmailNotificationSetting) query.getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rcaEmailNotificationSetting;
    }

    @Override
    public RcaEmailNotificationSetting saveRcaEmailNotificationSetting(RcaEmailNotificationSetting rcaEmailNotificationSetting) {
        try {
            entityManager.persist(rcaEmailNotificationSetting);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rcaEmailNotificationSetting;
    }
}
