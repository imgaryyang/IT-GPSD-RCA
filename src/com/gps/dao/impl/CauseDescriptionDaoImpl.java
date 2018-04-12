package com.gps.dao.impl;

import com.gps.dao.CauseDescriptionDao;
import com.gps.vo.CauseDescription;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by Waqar Malik on 15-12-2014.
 */
@Repository
public class CauseDescriptionDaoImpl implements CauseDescriptionDao {
    private static final Logger log = Logger.getLogger(CauseDescriptionDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<CauseDescription> getCauseDescriptionListByType(String causeDescriptionType) {
        StringBuilder builder = new StringBuilder();
        List<CauseDescription> causeDescriptionList = null;
        try {
            builder.append("SELECT cd FROM CauseDescription cd ");
            builder.append("WHERE cd.causeDescriptionType=:causeDescriptionType ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("causeDescriptionType", causeDescriptionType);
            causeDescriptionList = (List<CauseDescription>) query.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return causeDescriptionList;
    }

    @Override
    public List<CauseDescription> getCauseDescriptionListByParentId(Integer parentId) {
        StringBuilder builder = new StringBuilder();
        List<CauseDescription> causeDescriptionList = null;
        try {
            builder.append("SELECT cd FROM CauseDescription cd ");
            builder.append("WHERE cd.parent.causeDescriptionId=:parentId ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("parentId", parentId);
            causeDescriptionList = (List<CauseDescription>) query.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return causeDescriptionList;
    }

    @Override
    public List<CauseDescription> getCauseDescriptionListByParentName(String causeDescriptionName) {
        StringBuilder builder = new StringBuilder();
        List<CauseDescription> causeDescriptionList = null;
        try {
            builder.append("SELECT cd FROM CauseDescription cd ");
            builder.append("WHERE cd.parent.causeDescriptionName=:causeDescriptionName ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("causeDescriptionName", causeDescriptionName);
            causeDescriptionList = (List<CauseDescription>) query.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return causeDescriptionList;
    }
}

