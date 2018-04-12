package com.gps.service;


import com.gps.vo.RcaAction;
import com.gps.vo.helper.ActionHelper;

public interface RcaActionNotificationService {

    public void sendRcaActionClosedNotification(RcaAction rcaAction, ActionHelper actionHelper);
    public void sendRcaActionReAssignedNotification(String newAssigneeEmail, RcaAction dbRcaAction, ActionHelper actionHelper);
    public void sendRcaActionCreatedNotification(String assigneeEmail, RcaAction dbRcaAction);

}
