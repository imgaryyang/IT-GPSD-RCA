package com.gps.dao.impl;

import com.gps.dao.RcaActionHistoryLogDao;
import com.gps.vo.RcaActionHistoryLog;
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
public class RcaActionHistoryLogDaoImpl implements RcaActionHistoryLogDao {
    private static final Logger log = Logger.getLogger(RcaActionHistoryLogDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void addRcaActionHistoryLog(RcaActionHistoryLog rcaActionHistoryLog) {
        try {
            entityManager.persist(rcaActionHistoryLog);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
     }

    @Override
    public List<RcaActionHistoryLog> getRcaActionHistoryLogsByRcaActionId(Integer rcaActionId) {
        StringBuilder builder = new StringBuilder();
        List<RcaActionHistoryLog> rcaActionHistoryLogs = null;
        try {
            builder.append("SELECT rahl FROM RcaActionHistoryLog rahl ");
            builder.append("LEFT JOIN FETCH rahl.rcaAction ra ");
            builder.append("LEFT JOIN FETCH rahl.submittedBy sb ");
            builder.append("WHERE ra.rcaActionId=:rcaActionId ");
            builder.append("ORDER BY rahl.submittedOn desc ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("rcaActionId", rcaActionId);
            rcaActionHistoryLogs = (List<RcaActionHistoryLog>) query.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rcaActionHistoryLogs;

    }

    @Override
    public List<RcaActionHistoryLog> getRcaActionHistoryLogsByRcaActionIdAndFormAction(Integer rcaActionId, String formAction) {
        StringBuilder builder = new StringBuilder();
        List<RcaActionHistoryLog> rcaActionHistoryLogs = null;
        try {
            builder.append("SELECT rahl FROM RcaActionHistoryLog rahl ");
            builder.append("LEFT JOIN FETCH rahl.rcaAction ra ");
            builder.append("LEFT JOIN FETCH rahl.submittedBy sb ");
            builder.append("WHERE ra.rcaActionId=:rcaActionId ");
            builder.append("AND ra.formAction=:formAction ");
            builder.append("ORDER BY rahl.submittedOn desc ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("rcaActionId", rcaActionId);
            query.setParameter("formAction", formAction);
            rcaActionHistoryLogs = (List<RcaActionHistoryLog>) query.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rcaActionHistoryLogs;
    }

    @Override
    public void deleteRcaActionHistoryLog(RcaActionHistoryLog actionHistoryLog) {
        try {
            entityManager.remove(entityManager.merge(actionHistoryLog));
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }

}
