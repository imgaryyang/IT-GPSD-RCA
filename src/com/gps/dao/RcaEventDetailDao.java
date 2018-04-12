package com.gps.dao;

import com.gps.vo.RcaEventDetail;

public interface RcaEventDetailDao {

    public void addRcaEventDetail(RcaEventDetail rcaEventDetail);

    public void update(RcaEventDetail rcaEventDetail);

    public RcaEventDetail getRcaEventDetailByRcaId(Integer rcaId);
}