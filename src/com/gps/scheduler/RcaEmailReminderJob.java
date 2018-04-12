package com.gps.scheduler;

import com.gps.service.EmailReminderService;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Created by Waqar Malik on 27-05-2015.
 */
@Service
public class RcaEmailReminderJob extends QuartzJobBean {

    @Autowired
    EmailReminderService emailReminderService;

    private static final Logger logger = Logger.getLogger(RcaEmailReminderJob.class);

    @Override
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {

        logger.error("*********WELCOME to RCA Email Reminder Service *************");
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

        // Send RCA NOT CLOSED REMINDERS
        logger.error("**** Starting RCA Not Closed Reminders ****");
        emailReminderService.sendRcaNotClosedEmailReminders();
        logger.error("**** RCA Not Closed Reminders Completed ****");

        // Send RCA NOT APPROVED REMINDERS
        logger.error("**** Starting RCA Not Approved Reminders ****");
        emailReminderService.sendRcaNotApprovedReminders();
        logger.error("**** RCA Not Approved Reminders Completed ****");

        // Send RCA NOT ACCEPTED BY OWNER REMINDERS
        logger.error("**** Starting RCA Not Accepted By Owner Reminders ****");
        emailReminderService.sendRcaNotAcceptedByOwnerReminders();
        logger.error("**** RCA Not Accepted By Owner Completed ****");

        // Send RCA NOT SUBMITTED REMINDERS
        logger.error("**** Starting RCA Not Submitted Reminders ****");
        emailReminderService.sendRcaNotSubmittedByOwnerReminders();
        logger.error("**** RCA Not Submitted Completed ****");

        // Send Action Not Closed REMINDERS
        logger.error("**** Starting Action Not Closed Reminders ****");
        emailReminderService.sendActionNotClosedReminders();
        logger.error("**** Action Not Closed Completed ****");

    }

}
