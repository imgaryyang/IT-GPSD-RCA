package com.gps.web.validator;

/**
 *
 */

import com.gps.dao.GpsUserDao;
import com.gps.util.CommonUtil;
import com.gps.util.ConstantDataManager;
import com.gps.util.StringUtils;
import com.gps.vo.*;
import com.gps.vo.helper.RcaForm;
import com.gps.vo.helper.RcaFormDateHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.List;

@Component("rcaValidator")
public class RcaFormValidator implements Validator {
    private static final Logger logger = Logger.getLogger(RcaFormValidator.class);
    public static final String CLOSE = "close";

    @Autowired
    GpsUserDao gpsUserDao;

    @Override
    public boolean supports(Class clazz) {
        return Contract.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RcaForm rcaForm = (RcaForm) target;
        logger.debug("Validating rca form");
        Rca rca = rcaForm.getRca();
        RcaEventDetail rcaEventDetail = rca.getRcaEventDetail();
        RcaPreventionDetail rcaPreventionDetail = rca.getRcaPreventionDetail();
        RcaCause primaryRcaCause = rca.getPrimaryRcaCause();
        RcaFormDateHelper rcaFormDateHelper = rcaForm.getRcaFormDateHelper();
        List<RcaCause> contributingCauses = CollectionUtils.isNotEmpty(rcaForm.getRcaContributingCauses()) ? rcaForm.getRcaContributingCauses() : new ArrayList<RcaCause>();
        List<RcaAction> rcaActions = CollectionUtils.isNotEmpty(rcaForm.getRcaActions()) ? rcaForm.getRcaActions() : new ArrayList<RcaAction>();
        List<RcaTicket> rcaTickets = CollectionUtils.isNotEmpty(rcaForm.getRcaTickets()) ? rcaForm.getRcaTickets() : new ArrayList<RcaTicket>();

        if (StringUtils.isNullOrEmpty(rca.getTitle())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.title", "required.rcaTitle");
        }
        if (StringUtils.isNullOrEmpty(rca.getRcaOwner())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.rcaOwner", "required.rcaOwner");
        }
        if (StringUtils.isNullOrEmpty(rca.getRcaDelegate())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.rcaDelegate", "required.rcaDelegate");
        }
        if (rca.getRcaDpeId() == 0 || rca.getRcaDpeId() == null) {
            errors.rejectValue("rca.rcaDpeId", "required.rcaDpe", new Object[]{""}, null);
        }
        if (!rca.getIsOwnerAccepted().trim().equalsIgnoreCase("Y") && !rca.getIsDelegateAccepted().trim().equalsIgnoreCase("Y")) {
            errors.rejectValue("rca.isOwnerAccepted", "required.isOwnerAccepted", new Object[]{""}, null);
        }
        if (StringUtils.isNotBlank(rca.getRcaOwner()) && rca.getRcaDpeId() != null) {
            GpsUser dpe = gpsUserDao.getUserById(rca.getRcaDpeId());
            if (dpe != null && dpe.getEmail().equalsIgnoreCase(rca.getRcaOwner())) {
                errors.rejectValue("rca.rcaOwner", "same.owner.dpe", new Object[]{""}, null);
            }
        }
        
        boolean incidentError = Boolean.FALSE;
        if (StringUtils.isNullOrEmpty(rcaFormDateHelper.getIncidentStartDateTime())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.incidentStartTime", "required.incidentStartTime");
            incidentError = Boolean.TRUE;
        }
        if (StringUtils.isNullOrEmpty(rcaFormDateHelper.getIncidentEndDateTime())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.incidentEntTime", "required.incidentEntTime");
            incidentError = Boolean.TRUE;
        }
        if (StringUtils.isBlank(rca.getIncidentDuration())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.incidentDuration", "required.incidentDuration");
        }
        if (StringUtils.isNotBlank(rca.getBusinessImpactInMins()) && !CommonUtil.isNumber(rca.getBusinessImpactInMins())) {
            errors.rejectValue("rca.businessImpactInMins", "invalid.businessImpactInMins", new Object[]{""}, null);
        }

//        logger.debug("incidentError = "+incidentError);
        if(!incidentError){
        	java.sql.Timestamp incidentStart = CommonUtil.convertToTimestamp(rcaFormDateHelper.getIncidentStartDateTime());
        	java.sql.Timestamp incidentEnd = CommonUtil.convertToTimestamp(rcaFormDateHelper.getIncidentEndDateTime());
        	
//        	logger.debug("comparing start & End");
        	if(incidentStart.after(incidentEnd)){
        		errors.rejectValue("rca.incidentEntTime", "incidentEntTime.not.after", null);
        	}
        }
        // validating primary ticket details
        validatePrimaryTicket(errors, rca);

        //validating the secondary rca ticket
        if (CollectionUtils.isNotEmpty(rcaTickets)) {
            int i = 0;
            for (RcaTicket rcaTicket : rcaTickets) {
                if (StringUtils.isNullOrEmpty(rcaTicket.getTicketNum())) {
                    errors.rejectValue("rcaTickets[" + i + "].ticketNum", "required.secondary.ticketNum", new Object[]{"" + i + ""}, null);
                    break;
                }
                i++;
            }

        }
        if (StringUtils.isBlank(rca.getServiceImpacted())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.serviceImpacted", "required.serviceImpacted");
        }
        if (StringUtils.isBlank(rca.getSystemImpacted())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.systemImpacted", "required.systemImpacted");
        }
        if (StringUtils.isBlank(rca.getCustomerImpacted())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.customerImpacted", "required.customerImpacted");
        } else if(rcaForm.getCustImpactList().contains("Other")){
        	if(StringUtils.isBlank(rca.getCustomerOther())) {
        		 ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.customerOther", "required.customerOther");
        	}

        }
        if (StringUtils.isNotBlank(rca.getCustomerImpacted()) && rca.getCustomerImpacted().equalsIgnoreCase(ConstantDataManager.YES)
                && StringUtils.isBlank(rca.getCustomerImpactedList())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.customerImpactedList", "required.customerImpactedList");
        }
        if (StringUtils.isBlank(rca.getLocationOfFailure())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.locationOfFailure", "required.locationOfFailure");
        } else if("Other".equalsIgnoreCase(rca.getLocationOfFailure())){
        	if (StringUtils.isBlank(rca.getRcaOtherLocation())) {
        	 ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.rcaOtherLocation", "required.rcaOtherLocation");
        	}
        }
        if (StringUtils.isBlank(rca.getRcaReason())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.rcaReason", "required.rcaReason");
        } else if("Other".equalsIgnoreCase(rca.getRcaReason())){
        	if (StringUtils.isBlank(rca.getOtherReasonDetail())) {
           	 ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.rcaOtherLocation", "required.rcaOtherLocation");
           	}
        }


        // validating the rca event details
        validateRcaEventDetails(errors, rcaEventDetail);
        // validating the rca causes
        validateRcaCauses(errors, primaryRcaCause, contributingCauses);


        //validating the rca actions
        if (CollectionUtils.isNotEmpty(rcaActions)) {
            int i = 0;
            int j = i + 1;
            for (RcaAction rcaAction : rcaActions) {
                if (StringUtils.isNullOrEmpty(rcaAction.getAssignedTo())) {
                    errors.rejectValue("rcaActions[" + i + "].assignedTo", "action.assignedTo", new Object[]{"Action ID:" + j + ""}, null);
                }
                i++;
                j++;
            }

        }


        //check for open action items if form action is close
        if (rcaForm.getFormAction().equalsIgnoreCase(CLOSE) && CollectionUtils.isNotEmpty(rcaActions)) {
            int i = 0;
            int j = i + 1;
            for (RcaAction rcaAction : rcaActions) {
                if (!rcaAction.getActionStatus().equalsIgnoreCase("closed")) {
                    errors.rejectValue("rcaActions[" + i + "].assignedTo", "action.notClosed", new Object[]{"Action ID:" + j + ""}, null);
                    break;
                }
                i++;
                j++;
            }
        }

        // validating the rca prevention details
        if (StringUtils.isBlank(rcaPreventionDetail.getNewPoliciesImplemented())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.rcaPreventionDetail.newPoliciesImplemented", "required.newPoliciesImplemented");
        }
        if (StringUtils.isBlank(rcaPreventionDetail.getComments())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.rcaPreventionDetail.comments", "required.comments");
        }

        if(rcaForm.getIsDueDateModificationEnabled().equalsIgnoreCase(ConstantDataManager.YES) && StringUtils.isBlank(rcaForm.getRca().getDueDateModificationReason())){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.dueDateModificationReason", "required.dueDateModificationReason");
        }

        // validating for Rca Coordinator
        if (rcaForm.getLoggedInUser().getEmail().equalsIgnoreCase(rca.getRcaCoordinatorId()) && rca.getRcaStage().equalsIgnoreCase(ConstantDataManager.AWAITING_STAGE) && ( StringUtils.isBlank(rca.getHasCoordinatorApproved()) ||
                (StringUtils.isNotBlank(rca.getHasCoordinatorApproved()) && rca.getHasCoordinatorApproved().equalsIgnoreCase(ConstantDataManager.NO))) ) {
            errors.rejectValue("rca.hasCoordinatorApproved", "required.coordinatorApproval", new Object[]{""}, null);
        }

    }

    private void validateRcaCauses(Errors errors, RcaCause primaryRcaCause, List<RcaCause> contributingCauses ) {
        // validating the primary rca cause
        if (primaryRcaCause != null && StringUtils.isBlank(primaryRcaCause.getServiceProvider())) {
            errors.rejectValue("rca.primaryRcaCause.serviceProvider", "required.serviceProvider", new Object[]{""}, null);
        } else if("Other".equalsIgnoreCase(primaryRcaCause.getServiceProvider())){
        	if (StringUtils.isBlank(primaryRcaCause.getOtherServiceProvider())) {
             	 ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.primaryRcaCause.otherServiceProvider", "required.otherServiceProvider");
             	}
       }
        
        if (primaryRcaCause != null && StringUtils.isBlank(primaryRcaCause.getLocationOfBusinessImpact())) {
            errors.rejectValue("rca.primaryRcaCause.locationOfBusinessImpact", "required.locationOfBusinessImpact", new Object[]{""}, null);
        } else if("Other".equalsIgnoreCase(primaryRcaCause.getLocationOfBusinessImpact())){
        	if (StringUtils.isBlank(primaryRcaCause.getOtherLocOfBusinessImpact())) {
              	 ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.primaryRcaCause.otherLocOfBusinessImpact", "required.otherLocOfBusinessImpact");
              	}
        }
        
        if (primaryRcaCause != null && (StringUtils.isBlank(primaryRcaCause.getOutageCategory()) || StringUtils.isBlank(primaryRcaCause.getRootOrContributingCause())
                || StringUtils.isBlank(primaryRcaCause.getCauseCategory()))) {
            errors.rejectValue("rca.primaryRcaCause.outageCategory", "required.outageCategory", new Object[]{""}, null);
        }
        
        if("Other".equalsIgnoreCase(primaryRcaCause.getOutageSubCategory2())){
        	if (StringUtils.isBlank(primaryRcaCause.getOtherOutageSubCat2())) {
             	 ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.primaryRcaCause.otherOutageSubCat2", "required.otherOutageSubCat2");
             	}
       }
        

        // validating the contributing causes
        if (CollectionUtils.isNotEmpty(contributingCauses)) {
            int i = 0, j = i + 1;
            for (RcaCause rcaCause : contributingCauses) {
                if (rcaCause != null && (StringUtils.isNullOrEmpty(rcaCause.getOutageCategory()) || StringUtils.isNullOrEmpty(rcaCause.getLocationOfBusinessImpact())
                        || StringUtils.isNullOrEmpty(rcaCause.getRootOrContributingCause()) || StringUtils.isNullOrEmpty(rcaCause.getCauseCategory())
                        || StringUtils.isNullOrEmpty(rcaCause.getCauseSummary()))) {
                    errors.rejectValue("rcaContributingCauses[" + i + "].causeSummary", "required.contributingCauses", new Object[]{"" + j + ""}, null);
                    break;
                }
                i++;
                j++;
            }
        }
    }

    private void validateRcaEventDetails(Errors errors, RcaEventDetail rcaEventDetail) {
        if (StringUtils.isNullOrEmpty(rcaEventDetail.getIssueDescription())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.rcaEventDetail.issueDescription", "required.issueDescription");
        }
        if (StringUtils.isNullOrEmpty(rcaEventDetail.getChronology())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.rcaEventDetail.chronology", "required.chronology");
        }
        if (StringUtils.isNullOrEmpty(rcaEventDetail.getExecutiveSummary())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.rcaEventDetail.executiveSummary", "required.executiveSummary");
        }
        if (StringUtils.isNullOrEmpty(rcaEventDetail.getExecutiveAlert())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.rcaEventDetail.executiveAlert", "required.executiveAlert");
        }
        if (StringUtils.isNullOrEmpty(rcaEventDetail.getImpactedTower())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.rcaEventDetail.impactedTower", "required.impactedTower");
        }
        if (StringUtils.isNullOrEmpty(rcaEventDetail.getRepeatIssue())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.rcaEventDetail.repeatIssue", "required.repeatIssue");
        }
        if (StringUtils.isNullOrEmpty(rcaEventDetail.getRepeatRisk())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.rcaEventDetail.repeatRisk", "required.repeatRisk");
        }
        if (StringUtils.isNullOrEmpty(rcaEventDetail.getWhyProblem())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.rcaEventDetail.whyProblem", "required.whyProblem");
        }
        if (StringUtils.isNullOrEmpty(rcaEventDetail.getWhy1())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.rcaEventDetail.why1", "required.why1");
        }
        if (StringUtils.isNullOrEmpty(rcaEventDetail.getWhy2())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.rcaEventDetail.why2", "required.why2");
        }
        if (StringUtils.isNullOrEmpty(rcaEventDetail.getWhy3())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.rcaEventDetail.why3", "required.why3");
        }
        if (StringUtils.isNullOrEmpty(rcaEventDetail.getWhy4())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.rcaEventDetail.why4", "required.why4");
        }
    }

    private void validatePrimaryTicket(Errors errors, Rca rca) {
        if (StringUtils.isBlank(rca.getPrimaryTicketExists())) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.primaryTicketExists", "required.primaryTicketExists");
        }
        if (StringUtils.isNotBlank(rca.getPrimaryTicketExists()) && rca.getPrimaryTicketExists().equalsIgnoreCase(ConstantDataManager.NO)
                && StringUtils.isBlank(rca.getNoPrimaryTicketExplanation())) {
            errors.rejectValue("rca.noPrimaryTicketExplanation", "required.noPrimaryTicketExplanation", new Object[]{""}, null);
        }

        if (StringUtils.isNotBlank(rca.getPrimaryTicketExists()) && rca.getPrimaryTicketExists().equalsIgnoreCase(ConstantDataManager.YES)) {
            if (StringUtils.isNullOrEmpty(rca.getProblemIndidentRecord())) {
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.problemIndidentRecord", "required.problemIndidentRecord");
            }
            if (rca.getSeverity() == null || rca.getSeverity() == 0) {
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.severity", "required.severity");
            }
            if (StringUtils.isBlank(rca.getPrimaryTicketType())) {
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.primaryTicketType", "required.primaryTicketType");
            }
            if (StringUtils.isBlank(rca.getPrimaryTicketAssociation())) {
                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.primaryTicketAssociation", "required.primaryTicketAssociation");
            }

        }
    }


    public void validateOnSave(Object target, Errors errors) {
        RcaForm rcaForm = (RcaForm) target;
        logger.debug("Validating rca form");
        Rca rca = rcaForm.getRca();

        List<RcaAction> rcaActions = CollectionUtils.isNotEmpty(rcaForm.getRcaActions()) ? rcaForm.getRcaActions() : new ArrayList<RcaAction>();
        if (StringUtils.isNotBlank(rca.getRcaOwner()) && rca.getRcaDpeId() != null) {
            GpsUser dpe = gpsUserDao.getUserById(rca.getRcaDpeId());
            if (dpe != null && dpe.getEmail().equalsIgnoreCase(rca.getRcaOwner())) {
                errors.rejectValue("rca.rcaOwner", "same.owner.dpe", new Object[]{""}, null);
            }
        }

//        if (StringUtils.isBlank(rca.getCustomerImpacted())) {
//            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.customerImpacted", "required.customerImpacted");
//        } else if(rcaForm.getCustImpactList().contains("Other")){
//        	if(StringUtils.isBlank(rca.getCustomerOther())) {
//        		 ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.customerOther", "required.customerOther");
//        	}
//
//        }
        
        //validating the rca actions
        if (CollectionUtils.isNotEmpty(rcaActions)) {
            int i = 0;
            int j = i + 1;
            for (RcaAction rcaAction : rcaActions) {
                if (StringUtils.isNullOrEmpty(rcaAction.getAssignedTo())) {
                    errors.rejectValue("rcaActions[" + i + "].assignedTo", "action.assignedTo", new Object[]{"Action ID:" + j + ""}, null);
                }
                if (StringUtils.isNullOrEmpty(rcaAction.getActionTargetDate())) {
                    errors.rejectValue("rcaActions[" + i + "].targetDate", "action.targetDate", new Object[]{"Action ID:" + j + ""}, null);
                }
                i++;
                j++;
            }

        }

        if(rcaForm.getIsDueDateModificationEnabled().equalsIgnoreCase(ConstantDataManager.YES) && StringUtils.isBlank(rcaForm.getRca().getDueDateModificationReason())){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.dueDateModificationReason", "required.dueDateModificationReason");
        }
    }

    public void validateOnApprove(Object target, Errors errors) {
        RcaForm rcaForm = (RcaForm) target;
        logger.debug("Validating rca form");
        Rca rca = rcaForm.getRca();

        if (rca.getRcaStage().equalsIgnoreCase(ConstantDataManager.AWAITING_STAGE) && ( StringUtils.isBlank(rca.getHasCoordinatorApproved()) ||
                StringUtils.isNotBlank(rca.getHasCoordinatorApproved()) && rca.getHasCoordinatorApproved().equalsIgnoreCase(ConstantDataManager.NO))) {
                errors.rejectValue("rca.hasCoordinatorApproved", "required.coordinatorApproval", new Object[]{""}, null);
        }
        if(StringUtils.isBlank(rcaForm.getRcaFormActionsHelper().getApprovalComments())){
            errors.rejectValue("rcaFormActionsHelper.approvalComments", "required.approvalComments", new Object[]{""}, null);
        }
        if(rcaForm.getIsDueDateModificationEnabled().equalsIgnoreCase(ConstantDataManager.YES) && StringUtils.isBlank(rcaForm.getRca().getDueDateModificationReason())){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rca.dueDateModificationReason", "required.dueDateModificationReason");
        }
    }
}
