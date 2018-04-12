package com.gps.repository;

import com.gps.vo.Contract;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * 
 * @author Hassan Hanif
 *
 */
@Repository
public interface ContractRepository extends
		CrudRepository<Contract, Serializable> {

	@Query("FROM Contract")
	ArrayList<Contract> findAll();
	Contract findByContractId(int id);
	

}
