package com.gps.dao.impl;

import com.gps.dao.RcaSupportingFileDao;
import com.gps.exceptions.GPSException;
import com.gps.vo.RcaSupportingFile;
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
public class RcaSupportingFileDaoImpl implements RcaSupportingFileDao {
    private static final Logger log = Logger.getLogger(RcaSupportingFileDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void addRcaSupportingFile(RcaSupportingFile rcaSupportingFile) {
        try {
            entityManager.persist(rcaSupportingFile);
        } catch (Exception e) {
            throw new GPSException(e.getMessage(), e);
        }
    }

    @Override
    public List<RcaSupportingFile> getAllFileByRcaId(Integer rcaId) {
        StringBuilder builder = new StringBuilder();
        List<RcaSupportingFile> supportingFiles = null;
        try {
            builder.append("SELECT rsf FROM RcaSupportingFile rsf LEFT JOIN FETCH rsf.rca ra ");
            builder.append("WHERE ra.rcaId=:rcaId ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("rcaId", rcaId);
            supportingFiles = (List<RcaSupportingFile>) query.getResultList();
        } catch (Exception e) {
             throw new GPSException(e.getMessage(), e);
        }
        return supportingFiles;
    }

    @Override
    public RcaSupportingFile getFileById(Integer rcaFileId) {
        StringBuilder builder = new StringBuilder();
        RcaSupportingFile rcaSupportingFile = null;
        try {
            builder.append("SELECT rsf FROM RcaSupportingFile rsf LEFT JOIN FETCH rsf.rca ra ");
            builder.append("WHERE rsf.fileId=:rcaFileId ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("rcaFileId", rcaFileId);
            rcaSupportingFile = (RcaSupportingFile) query.getSingleResult();
        } catch (Exception e) {
            throw new GPSException(e.getMessage(), e);
        }
        return rcaSupportingFile;
    }

    @Override
    public void deleteRcaSupportingFile(RcaSupportingFile rcaActionSupportingFile) {
        try {
            entityManager.remove(entityManager.merge(rcaActionSupportingFile));
        }catch (Exception e){
            throw new GPSException(e.getMessage(), e);
        }
    }
}
