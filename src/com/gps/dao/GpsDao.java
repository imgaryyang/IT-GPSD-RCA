package com.gps.dao;

import com.gps.exceptions.GPSException;
import com.gps.vo.Customer;

import java.util.List;

public interface GpsDao {


	public List<Customer> getAllCustomers();
	
	/**
	 * 
	 * @param sessionId
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public List<Customer> getCustomers(String sessionId) throws GPSException;
	/**
	 * 
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public List<Customer> getCustomers() throws GPSException;
	
	public List<String> getSectors();

    public Customer getCustomerByInac(Integer inac);


}
