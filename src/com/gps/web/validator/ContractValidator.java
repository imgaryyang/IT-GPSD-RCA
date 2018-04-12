package com.gps.web.validator;

/**
 * 
 */

import com.gps.vo.Contract;
import com.gps.vo.helper.ContractHelper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("contractValidator")
public class ContractValidator implements Validator{
	private static final Logger logger = Logger.getLogger(ContractValidator.class);
	
	@Override
	public boolean supports(Class clazz) {
		return Contract.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Contract contract = (Contract)target;
		logger.debug("Validating contract form");
		ContractHelper contractHelper = contract.getContractHelper();
		
		
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractEndDate", "required.contractEndDate");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractStartDate", "required.contractStartDate");
	}

}
