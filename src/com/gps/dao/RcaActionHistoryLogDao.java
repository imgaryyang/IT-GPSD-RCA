package com.gps.dao;

import com.gps.vo.RcaActionHistoryLog;

import java.util.List;

public interface RcaActionHistoryLogDao {

    public void addRcaActionHistoryLog(RcaActionHistoryLog rcaActionHistoryLog);

    public List<RcaActionHistoryLog> getRcaActionHistoryLogsByRcaActionId(Integer rcaActionId);

    public List<RcaActionHistoryLog> getRcaActionHistoryLogsByRcaActionIdAndFormAction(Integer rcaActionId, String formAction);

    public void deleteRcaActionHistoryLog(RcaActionHistoryLog actionHistoryLog);
}