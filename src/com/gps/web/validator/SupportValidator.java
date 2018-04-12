package com.gps.web.validator;

/**
 *
 */

import com.gps.vo.ContractRequest;
import com.gps.vo.helper.Constant;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component("supportValidator")
public class SupportValidator implements Validator {
    private static final Logger logger = Logger.getLogger(SupportValidator.class);

    @Override
    public boolean supports(Class clazz) {
        return ContractRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ContractRequest contractRequest = (ContractRequest) target;
        logger.debug("Validating support form");

        if (contractRequest.getRequestType().equalsIgnoreCase(Constant.ACCESS_REQUEST) || contractRequest.getRequestType().equalsIgnoreCase(Constant.REMOVE_ACCESS_REQUEST)) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractName", "required.existingContractName");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "profileEmail", "required.profileEmail");
        }
        if (contractRequest.getRequestType().equalsIgnoreCase(Constant.NEW_CONTRACT_REQUEST)) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractName", "required.contractName");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "businessPurpose", "required.businessPurpose");
        }
        if (contractRequest.getRequestType().equalsIgnoreCase(Constant.MOD_CONTRACT_REQUEST)) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newContractName", "required.newContractName");

        }
    }

}
