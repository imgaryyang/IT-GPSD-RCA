package com.gps.dao;


import com.gps.vo.RcaEmailNotificationSetting;

public interface RcaEmailNotificationSettingDao {

    public void addRcaEmailNotificationSetting(RcaEmailNotificationSetting rcaEmailNotificationSetting);
    public void updateRcaEmailNotificationSetting(RcaEmailNotificationSetting rcaEmailNotificationSetting);
    public void deleteRcaEmailNotificationSetting(RcaEmailNotificationSetting rcaEmailNotificationSetting);
    public RcaEmailNotificationSetting getRcaEmailNotificationSettingByContractId(Integer contractId);
    public RcaEmailNotificationSetting saveRcaEmailNotificationSetting(RcaEmailNotificationSetting rcaEmailNotificationSetting);


}