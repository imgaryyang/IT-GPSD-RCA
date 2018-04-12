package com.gps.dao;

import com.gps.vo.RcaAction;

import java.util.List;


public interface RcaActionDao {

    public void addRcaAction(RcaAction rcaAction);
    public void updateRcaAction(RcaAction rcaAction);
    public void deleteRcaAction(RcaAction rcaAction);
    public List<RcaAction> getRcaActionsByRcaId(Integer rcaId);
    public RcaAction getRcaActionById(Integer rcaActionId);
    public RcaAction getRcaActionByNumber(String actionNumber);
    public List<Object[]> getActionListByQueryParameters(String sQuery, List<Object> queryParameters);


}
