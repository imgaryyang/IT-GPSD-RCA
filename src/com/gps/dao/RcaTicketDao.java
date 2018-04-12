package com.gps.dao;

import com.gps.exceptions.GPSException;
import com.gps.vo.RcaTicket;

import java.util.List;

public interface RcaTicketDao {

    void addRcaTicket(RcaTicket rcaTicket) throws GPSException;
    void deleteRcaTicket(RcaTicket rcaTicket) throws GPSException;
    void updateRcaTicket(RcaTicket rcaTicket) throws GPSException;
    List<RcaTicket> getRcaTicketsByRcaId(Integer rcaId) throws GPSException;
    RcaTicket getRcaTicketById(Integer rcaTicketId) throws GPSException;

}