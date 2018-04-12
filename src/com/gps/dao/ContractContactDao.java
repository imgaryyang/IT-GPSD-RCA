package com.gps.dao;

import com.gps.exceptions.GPSException;
import com.gps.vo.ContractContact;

public interface ContractContactDao {

    public void addContractContact(ContractContact contractContact);
    public void updateContractContact(ContractContact contractContact);
    ContractContact getContractContactById(Integer contractContactId) throws GPSException;
}