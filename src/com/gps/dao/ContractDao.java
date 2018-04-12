package com.gps.dao;

import com.gps.exceptions.GPSException;
import com.gps.vo.Contract;
import com.gps.vo.helper.SearchFilter;

import java.util.List;

public interface ContractDao {
	
	 public void addContract(Contract contract);
	 public List<Contract> getContractsByTitle(SearchFilter searchFilter);
	 /**
	  * This DAO method returns list of Active contracts ('Transformation','Steady State','Phase Out')
	  * 
	  * @return
	  * @throws GPSException
	  * @authorWaqar Malik
	  */
	 public List<Contract> listActiveContracts()throws GPSException;
	 
	 /**
	  * This DAO method returns single contract by Id.
	  * 
	  * @param contractId
	  * @return
	  * @throws GPSException
	  * @authorWaqar Malik
	  */
	 Contract getContractById(Integer contractId) throws GPSException;
	 
	 /**
	  * This DAO method returns single contract filled with contract process by Id.
	  * 
	  * @param contractId
	  * @return
	  * @throws GPSException
	  * @authorWaqar Malik
	  */
	 public Contract getContractByIdFilledProcess(Integer contractId) throws GPSException;
	 /**
	  * This method returns list of contract Id's for purpose of login acl lists.
	  * @param stringQuery
	  * @return
	  * @throws GPSException
	  * @authorWaqar Malik
	  */
	 public List<Contract> getContractsByQuery(String stringQuery)  throws GPSException;
	 
	 /**
	  * This method returns list of contract Id's for provided query and parameters.
	  * 
	  * @param query
	  * @param queryParameters
	  * @return
	  * @authorWaqar Malik
	  */
	 public List<Integer> getContractIdsByQueryParam(String query,List<Object> queryParameters);
	 
	 /**
	  * 
	  * @param title
	  * @return
	  * @throws GPSException
	  * @authorWaqar Malik
	  */
	 public Contract getContractByTitle(String title) throws GPSException;
	 
	 public void removeContract(Contract contract);
	 public List<Contract> getAllContracts();
	 
	 /**
	  * 
	  * @return
	  * @throws GPSException
	  * @authorWaqar Malik
	  */
	 public Integer getMaxContractId() throws GPSException;
	 
	 /**
	  * 
	  * @param sessionId
	  * @return
	  * @authorWaqar Malik
	  */
	 public List<Contract> getAllContracts(String sessionId) throws GPSException;
	 public void updateContract(Contract contract);
	 public Contract getContractByIdAndTitle(Integer contractId);
	 public void saveContract(Contract contract);
	 public void remove(Object obj);
	 public List<Object[]>  getContractsByQueryParameters(Integer pageNo, String query,List<Object> queryParameters) ;
	 public void save(Object obj);
	 public Contract getContractsForFormGeneration(String formName) throws GPSException;
	 Long getCountContractsByQueryParameters(String query, List<Object> queryParameters);
	 
	 /**
	  * 
	  * @param contractIds
	  * @param month
	  * @param year
	  * @return
	  * @throws GPSException
	  * @authorWaqar Malik
	  */
	Contract getSLASLOReportByContractId(Integer contractId, Integer month, Integer year) throws GPSException;

    Contract getProfileDetailedReport(Integer contactId) throws GPSException;

    Contract getProfileProcesses(Integer contractId) throws GPSException;

    Contract getProfileCusSatSurveys(Integer contractId) throws GPSException;

    Contract getProfileCusBcrs(Integer contractId) throws GPSException;

    Contract getProfileContractContacts(Integer contractId) throws GPSException;

    /**
     *
     * @param contractId
     * @return
     * @throws GPSException
     */
    Contract getProfileSloDetails(Integer contractId) throws GPSException;

    /**
     *
     * @param contractId
     * @return
     * @throws GPSException
     */
    Contract getProfileSlaDetails(Integer contractId) throws GPSException;
    
    /**
     * 
     * @param contractId
     * @param month
     * @param year
     * @return
     * @throws GPSException
     * @authorWaqar Malik
     */
	Contract getFaScmSLAReportByContract(Integer contractId, Integer month, Integer year) throws GPSException;

	/**
	 * 
	 * @param contractId
	 * @param month
	 * @param year
	 * @return
	 * @throws GPSException
	 * @authorWaqar Malik
	 */
	Contract getFaScmSLOReportByContract(Integer contractId, Integer month, Integer year) throws GPSException;

    public List<Contract> getAllContractsForRcaForm(String sessionId) throws GPSException;



}
