package com.gps.service;


import com.gps.vo.Rca;
import com.gps.vo.RcaEmailNotificationSetting;
import com.gps.vo.RcaNotificationAudit;

import javax.mail.MessagingException;

public interface RcaNotificationService {

    public boolean saveOrUpdateNotificationAuditDetails(RcaNotificationAudit rcaNotificationAudit);
    public RcaNotificationAudit getRcaNotificationAudit(Integer rcaId);
    public void sendRcaInitiatedNotification(Rca rca) throws MessagingException;
    public void sendOwnerAndDelegateNotification(Rca rca, RcaEmailNotificationSetting emailNotificationSetting);
    public void sendCoordinatorApprovalRequestNotification(Rca rca);
    public void sendRcaApprovedNotification(Rca rca);
    public void sendRcaRejectedNotification(Rca rca, String rejectionComments);
    public void sendRcaCancellationNotification(Rca rca, String cancellationComments);
    public void sendRcaClosedNotification(Rca rca);
    public void sendRcaReOpenedNotification(Rca rca, String reOpenedComments);
    public void sendDpeApprovalRequestNotification(Rca rca);

}
