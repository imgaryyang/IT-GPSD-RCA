package com.gps.dao.impl;

import com.gps.dao.RcaTicketDao;
import com.gps.exceptions.GPSException;
import com.gps.vo.RcaTicket;
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
public class RcaTicketDaoImpl implements RcaTicketDao {
    private static final Logger log = Logger.getLogger(RcaTicketDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public void addRcaTicket(RcaTicket rcaTicket) throws GPSException {
        try {
            log.debug("in rcaTicketDAO: addRcaTicket" + rcaTicket.getRca().getRcaId());
             entityManager.persist(rcaTicket);
             log.debug("saved from DAO");
        }
        catch (Exception e){
            log.debug("addRcaTicket: exception occured");
            throw new GPSException(e.getMessage(),e);
        }
    }

    @Override
    public void deleteRcaTicket(RcaTicket rcaTicket) throws GPSException {
        try {
            entityManager.remove(entityManager.merge(rcaTicket));
        }
        catch (Exception e){
            throw new GPSException(e.getMessage(),e);
        }
    }

    @Override
    public void updateRcaTicket(RcaTicket rcaTicket) throws GPSException {
        try {
            entityManager.merge(rcaTicket);
        }
        catch (Exception e){
            throw new GPSException(e.getMessage(),e);
        }
    }

    @Override
    public List<RcaTicket> getRcaTicketsByRcaId(Integer rcaId) throws GPSException {
        StringBuilder builder = new StringBuilder();
        List<RcaTicket> rcaTickets = null;
        try {
            builder.append("SELECT rt FROM RcaTicket rt JOIN rt.rca r ");
            builder.append("WHERE r.rcaId=:rcaId ");
            builder.append("ORDER BY rt.rcaTicketId asc ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("rcaId", rcaId);
            rcaTickets = (List<RcaTicket>) query.getResultList();
        } catch (Exception e) {
            //    throw new GPSException(e.getMessage(), e);
        }
        return rcaTickets;
    }

    @Override
    public RcaTicket getRcaTicketById(Integer rcaTicketId) throws GPSException {
        StringBuilder builder = new StringBuilder();
        RcaTicket rcaTicket = null;
        try {
            builder.append("SELECT rt FROM RcaTicket rt LEFT JOIN FETCH rt.rca r ");
            builder.append("WHERE rt.rcaTicketId=:rcaTicketId ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("rcaTicketId", rcaTicketId);
            rcaTicket = (RcaTicket) query.getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            //    throw new GPSException(e.getMessage(), e);
        }
        return rcaTicket;
    }
}
