package com.gps.service.impl;

import com.gps.dao.CauseDescriptionDao;
import com.gps.dao.GpsDao;
import com.gps.dao.ServiceDescriptionDao;
import com.gps.exceptions.GPSException;
import com.gps.service.GpsService;
import com.gps.vo.CauseDescription;
import com.gps.vo.Customer;
import com.gps.vo.ServiceDescription;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("gpsService")
public class GpsServiceImpl implements GpsService {
	private static Logger log = Logger.getLogger(GpsServiceImpl.class);
	
	@Autowired
	GpsDao gpsDao;

	@Autowired
	ServiceDescriptionDao sdDao;

	@Autowired
	CauseDescriptionDao cdDao;


	@Override
	public List<Customer> getAllCustomers() {
		return gpsDao.getCustomers();
	}

	@Override
	public List<Customer> getCustomers() throws GPSException {
		return gpsDao.getCustomers();
	}
	
	@Override
	public List<Customer> getCustomers(String sessionId) throws GPSException {
		return gpsDao.getCustomers(sessionId);
	}

	@Override
	public List<ServiceDescription> getServiceDescriptionListByType(String sdType) {
		return sdDao.getServiceDescriptionListByType(sdType);
	}

	@Override
	public List<ServiceDescription> getServiceDescriptionListByParentId(Integer parentId) {
		return sdDao.getServiceDescriptionListByParentId(parentId);
	}

    @Override
    public List<ServiceDescription> getServiceDescriptionListByParentName(String parentName) {
        return sdDao.getServiceDescriptionListByParentName(parentName);
    }

    @Override
	public List<CauseDescription> getCauseDescriptionListByType(String cdType) {
		return cdDao.getCauseDescriptionListByType(cdType);
	}

	@Override
	public List<CauseDescription> getCauseDescriptionListByParentId(Integer parentId) {
		return cdDao.getCauseDescriptionListByParentId(parentId);
	}

    @Override
    public List<CauseDescription> getCauseDescriptionListByParentName(String causeDescriptionName) {
        return cdDao.getCauseDescriptionListByParentName(causeDescriptionName);
    }

	@Override
	public List<ServiceDescription> getServiceDescriptionListByParentNameAndType(String parentName, String type) {
		return sdDao.getServiceDescriptionListByParentNameAndType(parentName, type);
	}


	@Override
	public List<String> getSectors() {
		return gpsDao.getSectors();
	}
	
	
	
}
