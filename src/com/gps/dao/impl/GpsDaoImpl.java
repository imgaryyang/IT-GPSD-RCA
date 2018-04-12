package com.gps.dao.impl;

import com.gps.dao.GpsDao;
import com.gps.exceptions.GPSException;
import com.gps.vo.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class GpsDaoImpl implements GpsDao{
	
	@Autowired
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Customer> getCustomers() throws GPSException{
		TypedQuery<Customer> query = entityManager.createQuery("select cus FROM Customer cus inner join cus.contracts c ", Customer.class);
		List<Customer> list = null;
		try {
			list = query.getResultList();
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		return list;		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getAllCustomers() {
		Query query = entityManager.createQuery("FROM Customer");
		List<Customer> list = query.getResultList();
		
		return list;		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getSectors() {
		Query query = entityManager.createQuery("select DISTINCT sectorName FROM IndustrySect");
		List<String> list = query.getResultList();
		return list;
	}

    @Override
    public Customer getCustomerByInac(Integer inac) {
        StringBuilder builder = new StringBuilder();
        Customer customer = null;
        try {
            builder.append("SELECT c FROM Customer c ");
            builder.append("WHERE c.inac=:inac ");
            Query query = entityManager.createQuery(builder.toString());
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("inac", inac);
            customer = (Customer) query.getSingleResult();
        } catch (Exception e) {
            //  throw new GPSException(e.getMessage(), e);
        }
        return customer;
    }


    @Override
	public List<Customer> getCustomers(String sessionId) throws GPSException {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT cu FROM Customer cu LEFT JOIN cu.contracts c ");
           //     "LEFT JOIN c.sessionAcls acl ");
		//builder.append(" where acl.formType in ('profile','Profile') and acl.sessionId = :sessionId ");
		builder.append(" ORDER BY cu.name asc ");
		List<Customer> customers = null;
		try {
			TypedQuery<Customer> query = entityManager.createQuery(builder.toString(), Customer.class);
		//	query.setParameter("sessionId", sessionId);
			customers = query.getResultList();
		}
		catch(Exception e){
			throw new GPSException(e.getMessage(), e);
		}
		return customers;
	}
	
}
