package com.gps.dao.impl;

import com.gps.dao.RcaPreventionDetailDao;
import com.gps.vo.RcaPreventionDetail;
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
public class RcaPreventionDetailDaoImpl implements RcaPreventionDetailDao {
    private static final Logger log = Logger.getLogger(RcaPreventionDetailDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(RcaPreventionDetail rcaPreventionDetail) {
        try {
            entityManager.merge(rcaPreventionDetail);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
     }

    @Override
    public void update(RcaPreventionDetail rcaPreventionDetail) {
        try {
            entityManager.merge(rcaPreventionDetail);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }

    @Override
    public RcaPreventionDetail findByRcaId(Integer rcaId) {
        StringBuilder builder = new StringBuilder();
        RcaPreventionDetail rcaPreventionDetail = null;
        try {
            builder.append("SELECT red FROM RcaPreventionDetail red LEFT JOIN FETCH red.rca r ");
            builder.append("WHERE red.rcaId=:rcaId ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("rcaId", rcaId);
            rcaPreventionDetail = (RcaPreventionDetail) query.getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rcaPreventionDetail;
    }

}
