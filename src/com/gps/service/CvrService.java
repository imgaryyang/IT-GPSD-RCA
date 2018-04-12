package com.gps.service;

import com.gps.vo.Contract;
import com.gps.vo.ContractContact;
import com.gps.vo.EmailTemplate;

import java.util.ArrayList;
import java.util.List;

public interface CvrService {



	ArrayList<Contract> findAllContracts();


	List<Contract> getContractsByFormName(String formDate);


	List<ContractContact> getDPEEmailIdByContractId(String contractId);


	List<ContractContact> getDPEEmailIdByContractIds(String[] contractIds);


	List<EmailTemplate> getEmailTemplateList();


	EmailTemplate getEmailTemplateById(String emailTemplateId);


	List<EmailTemplate> getEmailTemplateListByFormName(String formName);



}
