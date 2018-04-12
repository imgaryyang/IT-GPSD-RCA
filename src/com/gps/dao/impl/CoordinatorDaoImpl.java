package com.gps.dao.impl;

import com.gps.dao.CoordinatorDao;
import com.gps.exceptions.GPSException;
import com.gps.vo.Coordinator;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 * Created by Waqar Malik on 15-12-2014.
 */


@Repository
public class CoordinatorDaoImpl implements CoordinatorDao {
    private static final Logger log = Logger.getLogger(CoordinatorDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void addCoordinator(Coordinator coordinator) {
        try {
            entityManager.persist(coordinator);
        } catch (Exception e) {
            throw new GPSException(e.getMessage(), e);
        }
    }

    @Override
    public void updateCoordinator(Coordinator coordinator) {
        try {
            entityManager.merge(coordinator);
        } catch (Exception e) {
            throw new GPSException(e.getMessage(), e);
        }
    }

    @Override
    public Coordinator getCoordinatorByNameAndIntranetId(String name, String intranetId) {
        StringBuilder builder = new StringBuilder();
        Coordinator coordinator = null;
        try {
            builder.append("SELECT co FROM Coordinator co ");
            builder.append("WHERE co.coordinatorName=:name  AND co.intranetId=:intranetId ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("name", name);
            query.setParameter("intranetId", intranetId);
            coordinator = (Coordinator) query.getSingleResult();
        } catch (Exception e) {
            //  throw new GPSException(e.getMessage(), e);
        }
        return coordinator;
    }

    @Override
    public Coordinator getCoordinatorByIntranetId(String intranetId) {
        StringBuilder builder = new StringBuilder();
        Coordinator coordinator = null;
        try {
            builder.append("SELECT co FROM Coordinator co ");
            builder.append("WHERE co.intranetId=:intranetId ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);

            query.setParameter("intranetId", intranetId);
            coordinator = (Coordinator) query.getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return coordinator;
    }

    @Override
    public Coordinator getCoordinatorById(Integer coordinatorId) throws GPSException {
        StringBuilder builder = new StringBuilder();
        Coordinator coordinator = null;
        try {
            builder.append("SELECT co FROM Coordinator co ");
            builder.append("WHERE co.coordinatorId=:coordinatorId ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);

            query.setParameter("coordinatorId", coordinatorId);
            coordinator = (Coordinator) query.getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return coordinator;
    }

    @Override
    public void deleteCoordinator(Coordinator coordinator) {
        try {
            entityManager.remove(coordinator);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
