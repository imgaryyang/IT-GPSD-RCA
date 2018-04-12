package com.gps.dao;

import com.gps.exceptions.GPSException;
import com.gps.vo.Coordinator;

public interface CoordinatorDao {

    public void addCoordinator(Coordinator coordinator);
    public void updateCoordinator(Coordinator coordinator);
    public Coordinator getCoordinatorByNameAndIntranetId(String name, String intranetId);
    public Coordinator getCoordinatorByIntranetId(String intranetId);
    public Coordinator getCoordinatorById(Integer coordinatorId) throws GPSException;
    public void deleteCoordinator(Coordinator coordinator);
}