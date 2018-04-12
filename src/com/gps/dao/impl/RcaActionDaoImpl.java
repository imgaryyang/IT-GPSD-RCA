package com.gps.dao.impl;


import com.gps.dao.RcaActionDao;
import com.gps.exceptions.GPSException;
import com.gps.vo.RcaAction;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(propagation= Propagation.REQUIRED)
public class RcaActionDaoImpl implements RcaActionDao {
	private static final Logger log = Logger.getLogger(RcaActionDaoImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

    @Override
    public void addRcaAction(RcaAction rcaAction) {
        try {
            entityManager.persist(rcaAction);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }

    @Override
    public void updateRcaAction(RcaAction rcaAction) {
        try {
            entityManager.merge(rcaAction);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }

    @Override
    public void deleteRcaAction(RcaAction rcaAction) {
        try {
            entityManager.remove(entityManager.merge(rcaAction));
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }

    @Override
    public List<RcaAction> getRcaActionsByRcaId(Integer rcaId) {
        StringBuilder builder = new StringBuilder();
        List<RcaAction> rcaActions = null;
        try {
            builder.append("SELECT ra FROM RcaAction ra LEFT JOIN FETCH ra.rca r ");
            builder.append("WHERE r.rcaId=:rcaId ");
            builder.append("ORDER BY ra.actionNumber asc ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("rcaId", rcaId);
            rcaActions = (List<RcaAction>) query.getResultList();
        } catch (Exception e) {
            throw new GPSException(e.getMessage(), e);
        }
        return rcaActions;
    }

    @Override
    public RcaAction getRcaActionById(Integer rcaActionId) {
        StringBuilder builder = new StringBuilder();
        RcaAction rcaAction = null;
        try {
            builder.append("SELECT ra FROM RcaAction ra LEFT JOIN FETCH ra.rca r ");
            builder.append("LEFT JOIN FETCH r.contract c ");
            builder.append("WHERE ra.rcaActionId=:rcaActionId ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("rcaActionId", rcaActionId);
            rcaAction = (RcaAction) query.getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rcaAction;
    }

    @Override
    public RcaAction getRcaActionByNumber(String actionNumber) {
        StringBuilder builder = new StringBuilder();
        RcaAction rcaAction = null;
        try {
            builder.append("SELECT ra FROM RcaAction ra LEFT JOIN FETCH ra.rca r ");
            builder.append("LEFT JOIN FETCH r.contract c ");
            builder.append("LEFT JOIN FETCH c.customer cus ");
            builder.append("WHERE ra.actionNumber=:actionNumber ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("actionNumber", actionNumber);
            rcaAction = (RcaAction) query.getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rcaAction;
    }

    @Override
    public List<Object[]> getActionListByQueryParameters(String sQuery, List<Object> queryParameters) {
        List<Object[]> actionList = null;
        try {
            Query query = entityManager.createQuery(sQuery);
            int i = 1;
            for (Object parameter : queryParameters) {
                query.setParameter("A" + i++, parameter);
            }
            actionList = query.getResultList();
        } catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return actionList;
    }
}



