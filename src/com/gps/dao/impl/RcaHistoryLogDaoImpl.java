package com.gps.dao.impl;

import com.gps.dao.RcaHistoryLogDao;
import com.gps.vo.RcaHistoryLog;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Waqar Malik on 15-12-2014.
 */
@Repository
@Transactional(propagation= Propagation.REQUIRED)
public class RcaHistoryLogDaoImpl implements RcaHistoryLogDao {
    private static final Logger log = Logger.getLogger(RcaHistoryLogDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void addRcaHistoryLog(RcaHistoryLog rcaHistoryLog) {
        try {
            entityManager.persist(rcaHistoryLog);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
     }

    @Override
    public List<RcaHistoryLog> getRcaHistoryLogsByRcaId(Integer rcaId) {
        StringBuilder builder = new StringBuilder();
        List<RcaHistoryLog> rcaHistoryLogs = null;
        try {
            builder.append("SELECT rhl FROM RcaHistoryLog rhl ");
            builder.append("LEFT JOIN FETCH rhl.rca r ");
            builder.append("LEFT JOIN FETCH rhl.submittedBy sb ");
            builder.append("WHERE r.rcaId=:rcaId ");
            builder.append("ORDER BY rhl.submittedOn desc ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("rcaId", rcaId);
            rcaHistoryLogs = (List<RcaHistoryLog>) query.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rcaHistoryLogs;

    }

    @Override
    public List<RcaHistoryLog> getRcaHistoryLogsByRcaIdAndFormAction(Integer rcaId, String formAction) {
        StringBuilder builder = new StringBuilder();
        List<RcaHistoryLog> rcaHistoryLogs = null;
        try {
            builder.append("SELECT rhl FROM RcaHistoryLog rhl ");
            builder.append("LEFT JOIN FETCH rhl.rca r ");
            builder.append("LEFT JOIN FETCH rhl.submittedBy sb ");
            builder.append("WHERE r.rcaId=:rcaId ");
            builder.append("AND rhl.formAction=:formAction ");
            builder.append("ORDER BY rhl.submittedOn desc ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("rcaId", rcaId);
            query.setParameter("formAction", formAction);
            rcaHistoryLogs = (List<RcaHistoryLog>) query.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rcaHistoryLogs;
    }

}
