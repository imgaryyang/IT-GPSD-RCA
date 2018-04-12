package com.gps.dao.impl;

import com.gps.dao.ServiceDescriptionDao;
import com.gps.vo.ServiceDescription;
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
public class ServiceDescriptionDaoImpl implements ServiceDescriptionDao {
    private static final Logger log = Logger.getLogger(ServiceDescriptionDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<ServiceDescription> getServiceDescriptionListByType(String serviceDescriptionType) {
        StringBuilder builder = new StringBuilder();
        List<ServiceDescription> serviceDescriptionList = null;
        try {
            builder.append("SELECT sd FROM ServiceDescription sd ");
            builder.append("WHERE sd.serviceDescriptionType=:serviceDescriptionType ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("serviceDescriptionType", serviceDescriptionType);
            serviceDescriptionList = (List<ServiceDescription>) query.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return serviceDescriptionList;
    }

    @Override
    public List<ServiceDescription> getServiceDescriptionListByParentId(Integer parentId) {
        StringBuilder builder = new StringBuilder();
        List<ServiceDescription> serviceDescriptionList = null;
        try {
            builder.append("SELECT sd FROM ServiceDescription sd ");
            builder.append("WHERE sd.parent.serviceDescriptionId=:parentId ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("parentId", parentId);
            serviceDescriptionList = (List<ServiceDescription>) query.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return serviceDescriptionList;
    }

    @Override
    public List<ServiceDescription> getServiceDescriptionListByParentName(String parentName) {
        StringBuilder builder = new StringBuilder();
        List<ServiceDescription> serviceDescriptionList = null;
        try {
            builder.append("SELECT distinct sd FROM ServiceDescription sd ");
            builder.append("WHERE sd.parent.serviceDescriptionName=:parentName ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("parentName", parentName);
            serviceDescriptionList = (List<ServiceDescription>) query.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return serviceDescriptionList;
    }

    @Override
    public List<ServiceDescription> getServiceDescriptionListByParentNameAndType(String parentName, String type) {
        StringBuilder builder = new StringBuilder();
        List<ServiceDescription> serviceDescriptionList = null;
        try {
            builder.append("SELECT distinct sd FROM ServiceDescription sd ");
            builder.append("WHERE sd.parent.serviceDescriptionName=:parentName AND sd.serviceDescriptionType=:type ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("parentName", parentName);
            query.setParameter("type", type);
            serviceDescriptionList = (List<ServiceDescription>) query.getResultList();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return serviceDescriptionList;
    }

    @Override
    public ServiceDescription getServiceDescriptionById(Integer serviceDescriptionId) {
        StringBuilder builder = new StringBuilder();
        ServiceDescription serviceDescription = null;
        try {
            builder.append("SELECT sd FROM ServiceDescription sd ");
            builder.append("WHERE sd.serviceDescriptionId=:serviceDescriptionId ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("serviceDescriptionId", serviceDescriptionId);
            serviceDescription = (ServiceDescription) query.getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return serviceDescription;
    }
}

