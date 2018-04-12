package com.gps.dao.impl;

import com.gps.dao.RcaEditorDao;
import com.gps.exceptions.GPSException;
import com.gps.vo.RcaEditor;
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
 * Created by Waqar Malik
 */
@Repository
@Transactional(propagation= Propagation.REQUIRED)
public class RcaEditorDaoImpl implements RcaEditorDao {
    private static final Logger log = Logger.getLogger(RcaEditorDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void addRcaEditor(RcaEditor rcaEditor) {
        try {
            entityManager.persist(rcaEditor);
        } catch (Exception e) {
            throw new GPSException(e.getMessage(), e);
        }
    }

    @Override
    public void updateEditor(RcaEditor rcaEditor) {
        try {
            entityManager.merge(rcaEditor);
        } catch (Exception e) {
            throw new GPSException(e.getMessage(), e);
        }
    }

    @Override
    public List<RcaEditor> getRcaEditorsByRcaId(Integer rcaId) {
        StringBuilder builder = new StringBuilder();
        List<RcaEditor> rcaEditors = null;
        try {
            builder.append("SELECT re FROM RcaEditor re LEFT JOIN FETCH re.rca r LEFT JOIN FETCH re.gpsUser u ");
            builder.append("WHERE r.rcaId=:rcaId  ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("rcaId", rcaId);
            rcaEditors = (List<RcaEditor>) query.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rcaEditors;
    }

    @Override
    public RcaEditor getRcaEditorByRcaIdAndUserId(Integer rcaId, Integer userId) {
        StringBuilder builder = new StringBuilder();
        RcaEditor rcaEditor = null;
        try {
            builder.append("SELECT re FROM RcaEditor re LEFT JOIN FETCH re.rca r LEFT JOIN FETCH re.gpsUser u ");
            builder.append("WHERE r.rcaId=:rcaId  ");
            builder.append("AND u.userId=:userId");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("rcaId", rcaId);
            query.setParameter("userId", userId);
            rcaEditor = (RcaEditor) query.getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rcaEditor;
    }

}
