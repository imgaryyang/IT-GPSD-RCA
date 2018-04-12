package com.gps.service;

/**
 * Created by Waqar Malik on 29-05-2015.
 */
public interface EmailReminderService {

    public void sendRcaNotClosedEmailReminders();

    public void sendRcaNotApprovedReminders();

    public void sendRcaNotAcceptedByOwnerReminders();

    public void sendRcaNotSubmittedByOwnerReminders();

    public void sendActionNotClosedReminders();

}
