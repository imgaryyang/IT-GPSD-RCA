package com.gps.dao.impl;

import com.gps.dao.ContractContactDao;
import com.gps.exceptions.GPSException;
import com.gps.vo.ContractContact;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by Waqar Malik on 16-12-2014.
 */
@Repository
public class ContractContactDaoImpl implements ContractContactDao {

    private static final Logger log = Logger.getLogger(ContractContactDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void addContractContact(ContractContact contractContact) {
        try {
            entityManager.persist(contractContact);
        }
        catch (Exception e){
            throw new GPSException(e.getMessage(),e);
        }
    }

    @Override
    public void updateContractContact(ContractContact contractContact) {
        try {
            entityManager.merge(contractContact);
        }
        catch (Exception e){
            throw new GPSException(e.getMessage(),e);
        }
    }

    @Override
    public ContractContact getContractContactById(Integer contractContactId) throws GPSException {
        StringBuilder builder = new StringBuilder();
        ContractContact contractContact = null;
        try {
            builder.append("SELECT cc FROM ContractContact cc ");
            builder.append("WHERE cc.contractContactId=:contractContactId ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("contractContactId", contractContactId);
            contractContact = (ContractContact) query.getSingleResult();
        } catch (Exception e) {
            //    throw new GPSException(e.getMessage(), e);
        }
        return contractContact;
    }
}
