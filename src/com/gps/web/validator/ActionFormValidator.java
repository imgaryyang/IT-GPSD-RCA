package com.gps.web.validator;

/**
 *
 */

import com.gps.util.StringUtils;
import com.gps.vo.Contract;
import com.gps.vo.RcaAction;
import com.gps.vo.helper.ActionForm;
import com.gps.vo.helper.ActionHelper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component("actionValidator")
public class ActionFormValidator implements Validator {
    private static final Logger logger = Logger.getLogger(ActionFormValidator.class);

    @Override
    public boolean supports(Class clazz) {
        return Contract.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ActionForm actionForm = (ActionForm) target;
        logger.debug("Validating action form");
        RcaAction rcaAction = actionForm.getRcaAction();
        ActionHelper actionHelper = actionForm.getActionHelper();

        if (StringUtils.isNullOrEmpty(rcaAction.getActionDesc())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rcaAction.actionDesc", "required.action.actionDesc");
        }
        if (StringUtils.isNullOrEmpty(rcaAction.getAssignedTo())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rcaAction.assignedTo", "required.action.assignedTo");
        }
        if (StringUtils.isNullOrEmpty(actionHelper.getTargetDate())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "actionHelper.targetDate", "required.action.targetDate");
        }
        if (actionHelper.getTargetDateUpdated()) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rcaAction.targetDateModificationReason", "required.action.targetDateModificationReason");
        }
    }

    public void validateOnSave(Object target, Errors errors) {
        ActionForm actionForm = (ActionForm) target;
        logger.debug("Validating action form");
        RcaAction rcaAction = actionForm.getRcaAction();
        ActionHelper actionHelper = actionForm.getActionHelper();

        if (StringUtils.isNullOrEmpty(actionHelper.getTargetDate())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "actionHelper.targetDate", "required.action.targetDate");
        }
        if (actionHelper.getTargetDateUpdated()) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rcaAction.targetDateModificationReason", "required.action.targetDateModificationReason");
        }


    }

}
