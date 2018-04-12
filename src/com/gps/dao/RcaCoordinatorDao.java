package com.gps.dao;

import com.gps.exceptions.GPSException;
import com.gps.vo.RcaCoordinator;

import java.util.List;

public interface RcaCoordinatorDao {

    public void addRcaCoordinator(RcaCoordinator rcaCoordinator);
    public RcaCoordinator getRcaCoordinatorByContractIdAndCoordinatorId(Integer contractId, Integer coordinatorId);
    RcaCoordinator getRcaCoordinatorById(Integer rcaCoordinatorId) throws GPSException;
    public List<RcaCoordinator> getAllRcaCoordinators();
    public List<RcaCoordinator> getRcaCoordinatorByContractId(Integer contractId);
    List<RcaCoordinator> getRcaCoordinatorsByRcaIds(String rcaIds);
    RcaCoordinator getLastInserted(Integer contractId, String intranetId);
    public Integer saveRcaCoordinator(RcaCoordinator rcaCoordinator);
    public void deleteRcaCoordinator(RcaCoordinator rcaCoordinator);
    public void updateRcaCoordinator(RcaCoordinator rcaCoordinator);
}