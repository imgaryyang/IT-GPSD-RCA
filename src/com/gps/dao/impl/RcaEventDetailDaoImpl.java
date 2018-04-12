package com.gps.dao.impl;

import com.gps.dao.RcaEventDetailDao;
import com.gps.vo.RcaEventDetail;
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
public class RcaEventDetailDaoImpl implements RcaEventDetailDao {
    private static final Logger log = Logger.getLogger(RcaEventDetailDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addRcaEventDetail(RcaEventDetail rcaEventDetail) {
        try {
            entityManager.merge(rcaEventDetail);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
     }

    @Override
    public void update(RcaEventDetail rcaEventDetail) {
        try {
            entityManager.merge(rcaEventDetail);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }

    @Override
    public RcaEventDetail getRcaEventDetailByRcaId(Integer rcaId) {
        StringBuilder builder = new StringBuilder();
        RcaEventDetail rcaEventDetail = null;
        try {
            builder.append("SELECT red FROM RcaEventDetail red LEFT JOIN FETCH red.rca r ");
            builder.append("WHERE red.rcaId=:rcaId ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("rcaId", rcaId);
            rcaEventDetail = (RcaEventDetail) query.getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rcaEventDetail;
    }

}
