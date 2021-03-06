package com.gps.dao;

import com.gps.exceptions.GPSException;
import com.gps.vo.Rca;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

public interface RcaDao {

    public List<Rca> getAllRcas();

    public Set<Rca> getRcaListByMonthAndYear(int month, int year);

    public List<Object[]> getRcaListByQueryParameters(String sQuery, List<Object> queryParameters);

    public List<Rca> getRcasByQueryParameters(String sQuery, List<Object> queryParameters);

    public Rca getRcaById(Integer rcaId);

    public void createRca(Rca rca);

    public void updateRca(Rca rca);

    public Integer getTotalNumberOfRca();

    public Integer getMaxRcaId() throws GPSException;

    public Rca getRcaByNumber(String rcaNumber);

    public Integer getCountByMonthAndYear(Integer month, Integer year) throws GPSException;

    public List<Rca> getRcaListByContractId(String contractIds);

    public Set<Rca> getRcaListByOutageDates(Timestamp startOutageDate, Timestamp endOutageDate);

    public Set<Rca> getRcaListByMonthAndYearForReports(int month, int year);
}
