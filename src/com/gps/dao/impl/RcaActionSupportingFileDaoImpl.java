package com.gps.dao.impl;

import com.gps.dao.RcaActionSupportingFileDao;
import com.gps.exceptions.GPSException;
import com.gps.vo.RcaActionSupportingFile;
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
public class RcaActionSupportingFileDaoImpl implements RcaActionSupportingFileDao {
    private static final Logger log = Logger.getLogger(RcaActionSupportingFileDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void addRcaActionSupportingFileDao(RcaActionSupportingFile actionSupportingFile) {
        try {
            entityManager.persist(actionSupportingFile);
        } catch (Exception e) {
            throw new GPSException(e.getMessage(), e);
        }
    }

    @Override
    public List<RcaActionSupportingFile> getAllFileByRcaActionId(Integer rcaActionId) {
        StringBuilder builder = new StringBuilder();
        List<RcaActionSupportingFile> supportingFiles = null;
        try {
            builder.append("SELECT rsf FROM RcaActionSupportingFile rsf LEFT JOIN FETCH rsf.rcaAction ra ");
            builder.append("WHERE ra.rcaActionId=:rcaActionId ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("rcaActionId", rcaActionId);
            supportingFiles = (List<RcaActionSupportingFile>) query.getResultList();
        } catch (Exception e) {
             throw new GPSException(e.getMessage(), e);
        }
        return supportingFiles;
    }

    @Override
    public RcaActionSupportingFile getFileById(Integer rcaActionFileId) {
        StringBuilder builder = new StringBuilder();
        RcaActionSupportingFile rcaActionSupportingFile = null;
        try {
            builder.append("SELECT rsf FROM RcaActionSupportingFile rsf LEFT JOIN FETCH rsf.rcaAction ra ");
            builder.append("WHERE rsf.fileId=:rcaActionFileId ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("rcaActionFileId", rcaActionFileId);
            rcaActionSupportingFile = (RcaActionSupportingFile) query.getSingleResult();
        } catch (Exception e) {
            throw new GPSException(e.getMessage(), e);
        }
        return rcaActionSupportingFile;
    }

    @Override
    public void deleteRcaActionSupportingFile(RcaActionSupportingFile rcaActionSupportingFile) {
        try {
            entityManager.remove(entityManager.merge(rcaActionSupportingFile));
        }catch (Exception e){
            throw new GPSException(e.getMessage(), e);
        }
    }
}
