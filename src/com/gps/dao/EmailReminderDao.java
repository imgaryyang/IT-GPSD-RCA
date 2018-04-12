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
import com.gps.vo.EmailReminder;

import java.util.List;

/**
 * This class provides interface for EmailTemplateDao.
 *  
 * @authorWaqar Malik
 */
public interface EmailReminderDao {

	void addEmailReminder(EmailReminder emailReminder);

    void deleteEmailReminder(EmailReminder emailReminder);

    EmailReminder findByQuestionnaireIdAndReminderType(Integer questionnaireId, String reminderType) throws GPSException;

    List<EmailReminder> findByReminderType(String reminderType);

    void updateEmailReminder(EmailReminder emailReminder);

    EmailReminder findById(Integer emailReminderId);
}
