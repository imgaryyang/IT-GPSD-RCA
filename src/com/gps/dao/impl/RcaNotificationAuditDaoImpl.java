package com.gps.dao.impl;


import com.gps.dao.RcaNotificationAuditDao;
import com.gps.vo.RcaNotificationAudit;
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
public class RcaNotificationAuditDaoImpl implements RcaNotificationAuditDao {
    private static final Logger log = Logger.getLogger(RcaNotificationAuditDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void saveOrUpdateRcaNotificationAuditDetails(RcaNotificationAudit rcaNotificationAudit) {
        try {
            entityManager.merge(rcaNotificationAudit);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
     }


    @Override
    public RcaNotificationAudit getNotificationAuditByRcaId(Integer rcaId) {
        StringBuilder builder = new StringBuilder();
        RcaNotificationAudit rcaNotificationAudit = null;
        try {
            builder.append("SELECT rna FROM RcaNotificationAudit rna LEFT JOIN FETCH rna.rca r ");
            builder.append("WHERE rna.rcaId=:rcaId ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("rcaId", rcaId);
            rcaNotificationAudit = (RcaNotificationAudit) query.getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rcaNotificationAudit;
    }

}
