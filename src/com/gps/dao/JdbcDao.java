package com.gps.dao;

import com.gps.vo.Contract;
import com.gps.vo.ContractContact;

import java.util.List;

public interface JdbcDao {


	List<Contract> getContractsByFormName(String formDate);

	List<ContractContact> getDPEEmailIdByContractId(String contractId);

	List<ContractContact> getDPEEmailIdByContractIds(String[] contractIds);

}
