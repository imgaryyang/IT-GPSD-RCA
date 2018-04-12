package com.gps.dao;


import com.gps.vo.RcaPreventionDetail;

public interface RcaPreventionDetailDao {

    public void add(RcaPreventionDetail rcaPreventionDetail);

    public void update(RcaPreventionDetail rcaPreventionDetail);

    public RcaPreventionDetail findByRcaId(Integer rcaId);
}