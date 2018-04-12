package com.gps.dao.impl;

import com.gps.dao.RcaCoordinatorDao;
import com.gps.exceptions.GPSException;
import com.gps.vo.RcaCoordinator;
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
public class RcaCoordinatorDaoImpl implements RcaCoordinatorDao {
    private static final Logger log = Logger.getLogger(RcaCoordinatorDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void addRcaCoordinator(RcaCoordinator rcaCoordinator) {
        try {
            entityManager.persist(rcaCoordinator);

        }
        catch (Exception e){
            throw new GPSException(e.getMessage(),e);
        }

    }

    @Override
    public RcaCoordinator getRcaCoordinatorByContractIdAndCoordinatorId(Integer contractId, Integer coordinatorId) {
        StringBuilder builder = new StringBuilder();
        RcaCoordinator rcaCoordinator = null;
        try {
            builder.append("SELECT rcac FROM RcaCoordinator rcac ");
            builder.append("WHERE rcac.coordinator.coordinatorId=:coordinatorId  AND rcac.contract.contractId=:contractId ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("coordinatorId", coordinatorId);
            query.setParameter("contractId", contractId);
            rcaCoordinator = (RcaCoordinator) query.getSingleResult();
        } catch (Exception e) {
            throw new GPSException(e.getMessage(), e);
        }
        return rcaCoordinator;
    }

    @Override
    public RcaCoordinator getRcaCoordinatorById(Integer rcaCoordinatorId) throws GPSException {
        StringBuilder builder = new StringBuilder();
        RcaCoordinator rcaCoordinator = null;
        try {
            builder.append("SELECT r FROM RcaCoordinator r ");
            builder.append("WHERE r.rcaCoordinatorId=:rcaCoordinatorId ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("rcaCoordinatorId", rcaCoordinatorId);
            rcaCoordinator = (RcaCoordinator) query.getSingleResult();
        } catch (Exception e) {
        //    throw new GPSException(e.getMessage(), e);
        }
        return rcaCoordinator;
    }

    @Override
    public List<RcaCoordinator> getAllRcaCoordinators()    {
        StringBuilder builder = new StringBuilder();
        List<RcaCoordinator> rcaCoordinatorList = null;
        try {
            builder.append("SELECT r FROM RcaCoordinator r JOIN r.coordinator c ");
            builder.append("ORDER BY c.coordinatorName ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            rcaCoordinatorList = (List<RcaCoordinator>) query.getResultList();
        } catch (Exception e) {
            //throw new GPSException(e.getMessage(), e);
            log.error(e.getMessage(), e);
        }
        return rcaCoordinatorList;
    }

    @Override
    public List<RcaCoordinator> getRcaCoordinatorByContractId(Integer contractId) {
        StringBuilder builder = new StringBuilder();
        List<RcaCoordinator> rcaCoordinators = null;
        try {
            builder.append("SELECT rcac FROM RcaCoordinator rcac JOIN rcac.contract c ");
            builder.append("WHERE c.contractId=:contractId ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("contractId", contractId);
            rcaCoordinators = (List<RcaCoordinator>) query.getResultList();
        } catch (Exception e) {
            //    throw new GPSException(e.getMessage(), e);
        }
        return rcaCoordinators;
    }

    @Override
    public List<RcaCoordinator> getRcaCoordinatorsByRcaIds(String rcaIds) {
        StringBuilder builder = new StringBuilder();
        List<RcaCoordinator> rcaCoordinators = null;
        try {
            builder.append("SELECT rcac FROM RcaCoordinator rcac JOIN rcac.contract c ");
            builder.append("WHERE rcac.rcaId IN (:rcaIds) ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("rcaIds", rcaIds);
            rcaCoordinators = (List<RcaCoordinator>) query.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
        return rcaCoordinators;
    }

    @Override
    public RcaCoordinator getLastInserted(Integer contractId, String intranetId) {
        StringBuilder builder = new StringBuilder();
        RcaCoordinator rcaCoordinator = null;
        try {
            builder.append("SELECT rcac FROM RcaCoordinator rcac ");
            builder.append("WHERE rcac.coordinator.intranetId=:intranetId  AND rcac.contract.contractId=:contractId ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("intranetId", intranetId);
            query.setParameter("contractId", contractId);
            rcaCoordinator = (RcaCoordinator) query.getSingleResult();
        } catch (Exception e) {
            throw new GPSException(e.getMessage(), e);
        }
        return rcaCoordinator;
    }

    @Override
    public Integer saveRcaCoordinator(RcaCoordinator rcaCoordinator) {
        try {
            entityManager.persist(rcaCoordinator);
        }
        catch (Exception e){
            throw new GPSException(e.getMessage(),e);
        }
        return rcaCoordinator.getRcaCoordinatorId();
    }

    @Override
    public void deleteRcaCoordinator(RcaCoordinator rcaCoordinator) {
        try {
            entityManager.remove(rcaCoordinator);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }

    @Override
    public void updateRcaCoordinator(RcaCoordinator rcaCoordinator) {
        try {
            entityManager.merge(rcaCoordinator);
        }
        catch (Exception e){
            throw new GPSException(e.getMessage(),e);
        }
    }
}
