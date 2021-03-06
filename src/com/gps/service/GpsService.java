package com.gps.service;

import com.gps.exceptions.GPSException;
import com.gps.vo.CauseDescription;
import com.gps.vo.Customer;
import com.gps.vo.ServiceDescription;

import java.util.List;

public interface GpsService {
	
	
	
	public List<Customer> getAllCustomers();
	public List<String> getSectors();

	
	/**
	 * 
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public List<Customer> getCustomers() throws GPSException;
	/**
	 * 
	 * @param sessionId
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public List<Customer> getCustomers(String sessionId) throws GPSException;
	
	/**
	 * This process lists all enabled subprocesses.
	 * @return
	 * @authorWaqar Malik
	 */

	public List<ServiceDescription> getServiceDescriptionListByType(String sdType);
	public List<ServiceDescription> getServiceDescriptionListByParentId(Integer parentId);
    public List<ServiceDescription> getServiceDescriptionListByParentName(String parentName);
    public List<CauseDescription> getCauseDescriptionListByType(String cdType);
	public List<CauseDescription> getCauseDescriptionListByParentId(Integer parentId);
    public List<CauseDescription> getCauseDescriptionListByParentName(String causeDescriptionName);
	public List<ServiceDescription> getServiceDescriptionListByParentNameAndType(String parentName, String type);

}
