/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: EmailTemplateDao.java
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
 * 04/10/2013Waqar Malik Initial coding.
 *==========================================================================
 * </pre> */
package com.gps.dao;

import com.gps.exceptions.GPSException;
import com.gps.vo.EmailTemplate;

/**
 * This class provides interface for EmailTemplateDao.
 *  
 * @authorWaqar Malik
 */
public interface EmailTemplateDao {

	 /**
	  * 
	  * @param emailTemplateName
	  * @return
	  * @throws GPSException
	  * @authorWaqar Malik
	  */
	EmailTemplate getEmailTemplateByName(String emailTemplateName) throws GPSException;
	 

}
