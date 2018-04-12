package com.gps.dao.impl;

import com.gps.dao.RcaCauseDao;
import com.gps.exceptions.GPSException;
import com.gps.vo.RcaCause;
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
public class RcaCauseDaoImpl implements RcaCauseDao {
    private static final Logger log = Logger.getLogger(RcaCauseDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addRcaCause(RcaCause rcaCause) {
        try {
            entityManager.persist(rcaCause);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }

    }

    @Override
    public void updateRcaCause(RcaCause rcaCause) {
        try {
            entityManager.merge(rcaCause);
        } catch (Exception e) {
            throw new GPSException(e.getMessage(), e);
        }

    }

    @Override
    public void deleteRcaCause(RcaCause rcaCause) {
        try {
            entityManager.remove(entityManager.merge(rcaCause));
        } catch (Exception e) {
             log.error(e.getMessage(),e);
        }

    }

    @Override
    public List<RcaCause> getRcaCausesByRcaIdAndType(Integer rcaId, String type) {
        StringBuilder builder = new StringBuilder();
        List<RcaCause> rcaCauses = null;
        try {
            builder.append("SELECT rc FROM RcaCause rc LEFT JOIN FETCH rc.rca r ");
            builder.append("WHERE r.rcaId=:rcaId ");
            builder.append("AND rc.isPrimary=:type ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("rcaId", rcaId);
            query.setParameter("type", type);
            rcaCauses = (List<RcaCause>) query.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rcaCauses;
    }

    @Override
    public List<RcaCause> getRcaCausesByRcaId(Integer rcaId) {
        StringBuilder builder = new StringBuilder();
        List<RcaCause> rcaCauses = null;
        try {
            builder.append("SELECT rc FROM RcaCause rc LEFT JOIN FETCH rc.rca r ");
            builder.append("WHERE r.rcaId=:rcaId ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("rcaId", rcaId);
            rcaCauses = (List<RcaCause>) query.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rcaCauses;

    }

    @Override
    public RcaCause getRcaCauseById(Integer rcaCauseId) {
        StringBuilder builder = new StringBuilder();
        RcaCause rcaCause = null;
        try {
            builder.append("SELECT rc FROM RcaCause rc LEFT JOIN FETCH rc.rca r ");
            builder.append("WHERE rc.rcaCauseId=:rcaCauseId ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("rcaCauseId", rcaCauseId);
            rcaCause = (RcaCause) query.getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rcaCause;
    }
}
