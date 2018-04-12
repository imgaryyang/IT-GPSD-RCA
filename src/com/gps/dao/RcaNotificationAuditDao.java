package com.gps.dao;


import com.gps.vo.RcaNotificationAudit;

public interface RcaNotificationAuditDao {

    public void saveOrUpdateRcaNotificationAuditDetails(RcaNotificationAudit rcaNotificationAudit);

     public RcaNotificationAudit getNotificationAuditByRcaId(Integer rcaId);
}