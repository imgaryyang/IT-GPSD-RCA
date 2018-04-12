/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: EmailTemplateDaoImpl.java
 *    CREATOR:Waqar Malik
 *    DEPT: GBS PAK
 *    DATE: 04/10/2013
 *
 *-PURPOSE-----------------------------------------------------------------
 * 
 *--------------------------------------------------------------------------
 *
 *
 *-CHANGE LOG--------------------------------------------------------------
 * 25/09/2013Waqar Malik Initial coding.
 *==========================================================================
 * </pre> */
package com.gps.dao.impl;

import com.gps.dao.EmailTemplateDao;
import com.gps.exceptions.GPSException;
import com.gps.vo.EmailTemplate;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * This class implements EmailTemplateDao.
 *  
 * @authorWaqar Malik
 */
@Repository
public class EmailTemplateDaoImpl implements EmailTemplateDao {
	private static Logger log = Logger.getLogger(EmailTemplateDaoImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public EmailTemplate getEmailTemplateByName(String emailTemplateName) throws GPSException {
		log.debug("querying database for template = "+emailTemplateName);
		StringBuilder builder = new StringBuilder();
		EmailTemplate emailTemplate = null;
		try {
			builder.append("SELECT et FROM EmailTemplate et ");
			builder.append("WHERE et.emailTemplateName=:emailTemplateName ");
			Query query = entityManager.createQuery(builder.toString());
			query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
			query.setParameter("emailTemplateName", emailTemplateName);
			emailTemplate = (EmailTemplate) query.getSingleResult();
		} catch (Exception e) {
			log.error(e.getMessage(),e);	}
		return emailTemplate;
	}

}
