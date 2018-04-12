package com.gps.service;

import com.gps.exceptions.GPSException;
import com.gps.vo.*;
import com.gps.vo.helper.Page;
import com.gps.vo.helper.SearchFilter;

import java.util.List;
import java.util.Map;

public interface ContractService {

	public void addContract(Contract contract) throws GPSException;
	
	public List<Contract> getAllContracts();
	
	public List<Contract> getContractsByFilter(SearchFilter searchFilter);
	
	/**
	 * 
	 * @param contract
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public void modifyContract(Contract contract) throws GPSException;

	/**
	 * 
	 * @param sessionId
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public List<Contract> getAllContracts(String sessionId) throws GPSException;
	
	/**
	 * This service method returns list of Active contracts in 'Transformation', 'Steady State' and 'Phase Out' phases of contracts
	 * 
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public Map<Integer,String> listActiveContracts() throws GPSException;
	/**
	 * This service method returns list of Active contracts in 'Transformation', 'Steady State' and 'Phase Out' phases of contracts
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public List<Contract> getActiveContracts() throws GPSException;
	
	/**
	 * 
	 * @param title
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public Contract getContractByTitle(String title) throws GPSException;
	
	/**
	 * 
	 * @param contractId
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	Contract getContractByIdNoFetch(Integer contractId) throws GPSException;
	
	/**
	 * 
	 * @param contractId
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	public void removeContract(Integer contractId) throws GPSException;
	public Contract getContractByIdAndTitle(Integer contractId);
	public void updateContract(Contract contracts);
	public void saveContract(Contract contract);
	public void processContractSubmit(Contract contract);
	public Page getContractsBySearchFilter(SearchFilter searchFilter, String sessionId);
    public void saveCoordinator(Coordinator coordinator);
    public Coordinator getCoordinatorByNameAndIntranetId(String name, String intranetId);
    public void updateCoordinator(Coordinator coordinator);
    public void saveRcaCoordinator(RcaCoordinator rcaCoordinator);
    public RcaCoordinator getRcaCoordinatorByContractIdAndCoordinatorId(Integer contractId, Integer coordinatorId);
    public RcaCoordinator getRcaCoordinatorById(Integer rcaCoordinatorId);
    public GpsUser getUserByIntranetId(String intranetId);
    public void addUser(GpsUser dbUser);
    public List<Contract> getAllContractsForRcaForm(String sessionId);
    public String addRcaCoordinator(Contract contract);
    public Boolean deleteRcaCoordinator(Integer rcaCoordinatorId);
    public RcaEmailNotificationSetting addRcaEmailSettingNotification(RcaEmailNotificationSetting notificationSetting);
    public void loadRcaEmailSettings(Contract contract);
}
