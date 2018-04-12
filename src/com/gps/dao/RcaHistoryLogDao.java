package com.gps.dao;

import com.gps.vo.RcaHistoryLog;

import java.util.List;

public interface RcaHistoryLogDao {

    public void addRcaHistoryLog(RcaHistoryLog rcaHistoryLog);

    public List<RcaHistoryLog> getRcaHistoryLogsByRcaId(Integer rcaId);

    public List<RcaHistoryLog> getRcaHistoryLogsByRcaIdAndFormAction(Integer rcaId, String formAction);


}