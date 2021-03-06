
package com.gps.web.controller;

import com.gps.dao.RcaActionDao;
import com.gps.dao.RcaDao;
import com.gps.dao.RcaEmailNotificationSettingDao;
import com.gps.service.*;
import com.gps.util.*;
import com.gps.vo.*;
import com.gps.vo.helper.Constant;
import com.gps.vo.helper.InitiateRcaForm;
import com.gps.vo.helper.RcaForm;
import org.apache.commons.beanutils.converters.IntegerArrayConverter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.portlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
@SessionAttributes({"userSession"})
public class RcaFormUtilController {
    private static final Logger log = Logger.getLogger(RcaFormUtilController.class);
    public static final String SECONDARY_TICKET_TYPE = "secondary";


    @Autowired
    GpsService gpsService;

    @Autowired
    CommonUtil commonUtil;

    @Autowired
    RcaService rcaService;

    @Autowired
    RcaActionService rcaActionService;

    @Autowired
    GpsUserService gpsUserService;

    @Autowired
    RcaDao rcaDao;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    RcaEmailNotificationSettingDao rcaEmailNotificationSettingsDao;

    @Autowired
    RcaActionDao rcaActionDao;

    @Autowired
    private RcaActionNotificationService rcaActionNotificationService;

    @Autowired
    RcaUtilService rcaUtilService;

    @RequestMapping(value = "/deleteRCAContributingCause.htm", method = RequestMethod.POST)
    public
    @ResponseBody
    String deleteRCAContributingCause(@RequestParam("rca_cause_id") Integer rcaCauseId, @ModelAttribute RcaForm rcaForm, BindingResult result, Map<Object, Object> model) {
        String response = Constant.FAIL;
        log.debug("calling deleteRCAContributingCause() ................" + rcaCauseId);

        // save exisiting
        rcaUtilService.saveRcaCauses(rcaForm.getRcaContributingCauses());

        RcaCause rcaCause = rcaService.getRcaCauseById(rcaCauseId);
        if (rcaCause != null) {
            Boolean isRcaCauseDeleted = rcaService.deleteRcaContributingCause(rcaCause);
            if (isRcaCauseDeleted) {
                response = Constant.SUCCESS;
            }
        }
        log.debug("loadRCAContributingCauses() ");
        return response;
    }

    @RequestMapping(value = "/loadRcaActions.htm", method = RequestMethod.GET)
    public ModelAndView loadRcaActions(@RequestParam("rca_id") Integer rcaId, @ModelAttribute RcaForm rcaForm, BindingResult result, Map<Object, Object> model, HttpServletRequest request) {
        log.debug("loading rca actions for ................" + rcaId);
        List<RcaAction> rcaActions = rcaActionService.getRcaActionsByRcaId(rcaId);
        Rca dbRca = rcaService.getRcaById(rcaId);
        Integer actionItemsOpened = 0;
        Integer actionItemsClosed = 0;
        if (CollectionUtils.isNotEmpty(rcaActions)) {
            for (RcaAction rcaAction : rcaActions) {
                rcaAction.setActionTargetDate(rcaAction.getTargetDate() != null ? rcaAction.getTargetDate().toString() : null);
                rcaAction.setActionClosedDate(rcaAction.getCloseDate() != null ? rcaAction.getCloseDate().toString() : null);
                if (StringUtils.isNotBlank(rcaAction.getAssignedTo()) && !BluePages.isNotesID(rcaAction.getAssignedTo())) {
                    rcaAction.setAssignedTo(BluePages.getNotesIdFromIntranetId(rcaAction.getAssignedTo()));
                }

                // update closed and open items
                if (StringUtils.isNotBlank(rcaAction.getActionStatus()) && rcaAction.getActionStatus().equalsIgnoreCase("Closed")) {
                    actionItemsClosed = actionItemsClosed + 1;
                }
                if (StringUtils.isNotBlank(rcaAction.getActionStatus()) && rcaAction.getActionStatus().equalsIgnoreCase("Open")) {
                    actionItemsOpened = actionItemsOpened + 1;
                }
            }
            updateOpenAndClosedActionItems(dbRca, actionItemsOpened, actionItemsClosed);

            model.put("rcaStage", rcaActions.get(0).getRca().getRcaStage());
        }
        model.put("referenceData", commonUtil.buildReferenceData());
        model.put("basePath", commonUtil.getBasePath(request));
        rcaForm.setRcaActions(rcaActions);
        model.put("rcaForm", rcaForm);
        log.debug("loadRcaActions() ");
        return new ModelAndView("loadRcaActions");
    }

    private void updateOpenAndClosedActionItems(Rca dbRca, Integer actionItemsOpened, Integer actionItemsClosed) {
        try {
            dbRca.setActionItemClosed(actionItemsClosed);
            dbRca.setActionItemOpen(actionItemsOpened);
            rcaDao.updateRca(dbRca);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    @RequestMapping(value = "/addRCAContributingCause.htm", method = RequestMethod.POST)
    public
    @ResponseBody
    String addRCAContributingCause(@RequestParam("rca_id") Integer rcaId, @ModelAttribute RcaForm rcaForm, BindingResult result, Map<Object, Object> model) {
        String response = Constant.SUCCESS;
        log.debug("calling addRCAContributingCause() ................" + rcaId);

        //Save existing causes
        rcaUtilService.saveRcaCauses(rcaForm.getRcaContributingCauses());

        // Creating new Rca Action
        Rca dbRca = rcaService.getRcaById(rcaId);
        RcaCause rcaCause = new RcaCause();
        rcaCause.setIsPrimary("N");
        rcaCause.setRca(dbRca);
        Boolean isRcaCauseAdded = rcaService.addRcaContributingCause(rcaCause);
        if (!isRcaCauseAdded) {
            response = Constant.FAIL;
        }
        log.debug("addRCAContributingCause() ");
        return response;
    }

    @RequestMapping(value = "/addRcaAction.htm", method = RequestMethod.POST)
    public
    @ResponseBody
    String addRcaAction(@RequestParam("rca_id") Integer rcaId, @ModelAttribute RcaForm rcaForm, BindingResult result, Map<Object, Object> model, HttpSession session) {
        String response = Constant.SUCCESS;
        try {
            log.debug("calling addRcaAction() ................" + rcaForm.getRca().getRcaId());
            // Creating new Rca Action
            UserSession userSession = (UserSession) session.getAttribute("userSession");
            GpsUser loggedInUser = gpsUserService.getUserByIntranetId(userSession.getGpsUser().getEmail());
            Boolean isRcaActionTicketAdded = rcaActionService.addNewRcaAction(rcaId, loggedInUser);

            // handle newly created actions
            handleNewlyCreatedActions(rcaForm);

            //save existing actions
            rcaActionService.saveRcaActions(rcaForm.getRcaActions());

            // updating the open action items
            updateOpenActionItems(rcaId);

            if (!isRcaActionTicketAdded) {
                response = Constant.FAIL;
            }
            log.debug("addRcaAction() ");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            response = Constant.FAIL;

        }
        return response;
    }


    @RequestMapping(value = "/deleteRcaAction.htm", method = RequestMethod.POST)
    public
    @ResponseBody
    String deleteRcaAction(@RequestParam("rca_action_id") Integer rcaActionId, @ModelAttribute RcaForm rcaForm, BindingResult result, Map<Object, Object> model) {
        String response = Constant.FAIL;
        log.debug("calling deleteRcaAction() ................" + rcaActionId);

        //updating the open action items
        RcaAction rcaAction = rcaActionService.getRcaActionByActionId(rcaActionId);
        Rca rca = rcaService.getRcaById(rcaAction.getRca().getRcaId());
        if (rca.getActionItemOpen() != null) {
            rca.setActionItemOpen(rca.getActionItemOpen() - 1);
        }
        rcaDao.updateRca(rca);

        //deleting the rca action
        Boolean isRcaTicketDeleted = rcaActionService.deleteRcaAction(rcaActionId);

        updateOpenActionItems(rca.getRcaId());

        if (isRcaTicketDeleted) {
            response = Constant.SUCCESS;
        }

        log.debug("loadRcaActions() ");
        return response;
    }

    @RequestMapping(value = "/loadRcaHistoryLog.htm", method = RequestMethod.GET)
    public ModelAndView loadRcaHistoryLog(@RequestParam("rca_id") Integer rcaId, @ModelAttribute RcaForm rcaForm, BindingResult result, Map<Object, Object> model) {
        log.debug("loading history log for ................" + rcaId);
        List<RcaHistoryLog> rcaHistoryLogs = rcaService.getRcaHistoryLogsByRcaId(rcaId);
        model.put("referenceData", commonUtil.buildReferenceData());
        rcaForm.setRcaHistoryLogs(rcaHistoryLogs);
        model.put("rcaForm", rcaForm);
        log.debug("loadRcaActions() ");
        return new ModelAndView("loadRcaHistoryLog");
    }


    @RequestMapping(value = "/loadRCATickets.htm", method = RequestMethod.GET)
    public ModelAndView loadRCTickets(@RequestParam("rca_id") Integer rcaId, @ModelAttribute RcaForm rcaForm, BindingResult result, Map<Object, Object> model, HttpServletRequest request) {
        log.debug("loading rca tickets for ................" + rcaId);
        List<RcaTicket> rcaTickets = rcaService.getRcaTicketsByRcaId(rcaId);
        model.put("referenceData", commonUtil.buildReferenceData());
        model.put("rcaTickets", rcaTickets);
        rcaForm.setRcaTickets(rcaTickets);
        model.put("rcaForm", rcaForm);
        model.put("basePath", CommonUtil.getBasePath(request));
        log.debug("loadRCATickets() ");
        return new ModelAndView("loadRCATickets");
    }


    @RequestMapping(value = "/addSecondaryRCATicket.htm", method = RequestMethod.POST)
    public
    @ResponseBody
    String addSecondaryRCATicket(@RequestParam("rca_id") Integer rcaId, @ModelAttribute RcaForm rcaForm, BindingResult result, Map<Object, Object> model) {
        String response = Constant.SUCCESS;
        log.debug("calling addSecondaryRCATicket() ................" + rcaId);
        Rca rca = rcaService.getRcaById(rcaId);

        //Saving existing rca tickets
        rcaUtilService.saveRcaTickets(rcaForm.getRcaTickets());


        // Creating Rca Ticket
        RcaTicket rcaTicket = new RcaTicket();
        rcaTicket.setRca(rca);
        rcaTicket.setSeverity((short) 1);
        rcaTicket.setTicketType(SECONDARY_TICKET_TYPE);
        Boolean isRcaTicketAdded = rcaService.addSecondaryTicket(rcaTicket);
        if (!isRcaTicketAdded) {
            response = Constant.FAIL;
        }
        log.debug("loadRCATickets() ");
        return response;
    }

    @RequestMapping(value = "/deleteRcaTicket.htm", method = RequestMethod.POST)
    public
    @ResponseBody
    String deleteRcaTicket(@RequestParam("rca_ticket_id") Integer rcaTicketId, @ModelAttribute RcaForm rcaForm, BindingResult result, Map<Object, Object> model) {
        String response = Constant.FAIL;
        log.debug("calling deleteRcaTicket() ................" + rcaTicketId);


        //Saving existing rca tickets
        rcaUtilService.saveRcaTickets(rcaForm.getRcaTickets());

        RcaTicket rcaTicket = rcaService.getRcaTicketById(rcaTicketId);
        if (rcaTicket != null) {
            Boolean isRcaTicketDeleted = rcaService.deleteSecondaryTicket(rcaTicket);
            if (isRcaTicketDeleted) {
                response = Constant.SUCCESS;
            }
        }
        log.debug("loadRCATickets() ");
        return response;
    }


    @RequestMapping(value = "/calculateTimeSpan.htm", method = RequestMethod.GET)
    public
    @ResponseBody
    String calculateTimeSpan(@RequestParam("fromDate") String fromDateStr, @RequestParam("toDate") String toDateStr, @ModelAttribute RcaForm rcaForm, BindingResult result, Map<Object, Object> model) {
        String duration = "";
        log.debug("calling calculateTimeSpan() ................");
        Timestamp fromDate = CommonUtil.convertToTimestamp(fromDateStr);
        Timestamp toDate = CommonUtil.convertToTimestamp(toDateStr);
        try {
            duration = CommonUtil.calculateTimestampDifference(fromDate, toDate);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return duration;
    }

    @RequestMapping(value = "/outageSubCategories.htm", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ServiceDescription> getServiceArea(@RequestParam("outage_category") String outage_category_id, @ModelAttribute RcaForm rcaForm, BindingResult result, Map<Object, Object> model, HttpServletResponse response) {
        response.setContentType("application/json");
        //  String response = Constant.SUCCESS;
        log.debug("calling getServiceArea() ................");
        List<ServiceDescription> outageSubCategories = new ArrayList<ServiceDescription>();
        try {
            //outageSubCategories = gpsService.getServiceDescriptionListByParentNameAndType(outage_category_id, "outage_sub_category");
            outageSubCategories = gpsService.getServiceDescriptionListByParentId(Integer.parseInt(outage_category_id));
            model.put("outageSubCategories", outageSubCategories);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return outageSubCategories;
    }

    @RequestMapping(value = "/outageSubCategories2.htm", method = RequestMethod.GET)
    public
    @ResponseBody
    List<ServiceDescription> getServiceArea2(@RequestParam("outage_category") String outage_category, @ModelAttribute RcaForm rcaForm, BindingResult result, Map<Object, Object> model, HttpServletResponse response) {
        response.setContentType("application/json");
        //  String response = Constant.SUCCESS;
        log.debug("calling getServiceArea() ................");
        List<ServiceDescription> outageSubCategories = new ArrayList<ServiceDescription>();
        try {
            outageSubCategories = gpsService.getServiceDescriptionListByParentNameAndType(outage_category,"outage_sub_category_2");
//            outageSubCategories = gpsService.getServiceDescriptionListByParentId(Integer.parseInt(outage_category));

            model.put("outageSubCategories", outageSubCategories);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return outageSubCategories;
    }




    @RequestMapping(value = "/loadCauses.htm", method = RequestMethod.GET)
    public
    @ResponseBody
    List<CauseDescription> getCauses(@RequestParam("cause_description_name") String causeDescriptionName, @ModelAttribute RcaForm rcaForm, BindingResult result, Map<Object, Object> model) {
        List<CauseDescription> causeDescriptions = new ArrayList<CauseDescription>();
        log.debug("calling getCauses() ................");
        try {
            causeDescriptions = gpsService.getCauseDescriptionListByParentName(causeDescriptionName);
            model.put("causeDescriptions", causeDescriptions);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return causeDescriptions;
    }


    @RequestMapping(value = "/loadRCAContributingCauses.htm", method = RequestMethod.GET)
    public ModelAndView loadRCAContributingCauses(@RequestParam("rca_id") Integer rcaId, @ModelAttribute RcaForm rcaForm, BindingResult result, Map<Object, Object> model, HttpServletRequest request) {
        log.debug("loading rca contributing causes for ................" + rcaId);
        List<RcaCause> rcaCauses = rcaService.getRcaContributingCauses(rcaId);


        // get the service lines
        List<ServiceDescription> outageCategories = gpsService.getServiceDescriptionListByType(ConstantDataManager.OUTAGE_CATEGORY);

        // get the cause areas
        List<CauseDescription> rootCauses = gpsService.getCauseDescriptionListByType(ConstantDataManager.ROOT_CAUSE);

        if (CollectionUtils.isNotEmpty(rcaCauses)) {
            for (RcaCause rcaCause : rcaCauses) {
                List<ServiceDescription> outageSubCategories = gpsService.getServiceDescriptionListByParentId(!StringUtils.isNullOrEmpty(rcaCause.getOutageCategory()) ? Integer.parseInt(rcaCause.getOutageCategory()) : outageCategories.get(0).getServiceDescriptionId());
                rcaCause.setOutageSubCategories(outageSubCategories);

                if (StringUtils.isNotBlank(rcaCause.getOutageSubCategory()) && CollectionUtils.isNotEmpty(outageSubCategories)) {
                    List<ServiceDescription> outageSubCategories2 = gpsService.getServiceDescriptionListByParentId(!StringUtils.isNullOrEmpty(rcaCause.getOutageSubCategory()) ? Integer.parseInt(rcaCause.getOutageSubCategory()) : outageSubCategories.get(0).getServiceDescriptionId());
                    rcaCause.setOutageSubCategories2(outageSubCategories2);
                }

                List<CauseDescription> causeCategories = gpsService.getCauseDescriptionListByParentName(!StringUtils.isNullOrEmpty(rcaCause.getRootOrContributingCause()) ? rcaCause.getRootOrContributingCause() : rootCauses.get(0).getCauseDescriptionName());
                rcaCause.setCauseCategories(causeCategories);

                if (!StringUtils.isNullOrEmpty(rcaCause.getCauseCategory())) {
                    List<CauseDescription> causeSubCategories = gpsService.getCauseDescriptionListByParentName(rcaCause.getCauseCategory());
                    rcaCause.setCauseSubCategories(causeSubCategories);
                }
            }
        }

        model.put("referenceData", commonUtil.buildReferenceData());
        model.put("rcaContributingCauses", rcaCauses);
        rcaForm.setRcaContributingCauses(rcaCauses);
        model.put("rcaForm", rcaForm);
        model.put("outageCategories", outageCategories);
        model.put("rootCauses", rootCauses);
        model.put("basePath", CommonUtil.getBasePath(request));
        model.put("locationsOfBusiness", CommonUtil.getLocationsOfBusiness());
        log.debug("loadRCAContributingCauses() ");
        return new ModelAndView("loadRCAContributingCauses");
    }

    @RequestMapping(value = "/loadRCACoordinators.htm", method = RequestMethod.GET)
    public ModelAndView loadRCACoordinators(@RequestParam("contract_id") Integer contractId, @ModelAttribute InitiateRcaForm initiateRcaForm, BindingResult result, Map<Object, Object> model, HttpSession session) {
        log.debug("loading rca coordinators for ................" + contractId);
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        GpsUser loggedInUser = gpsUserService.getUserByIntranetId(userSession.getGpsUser().getEmail());
        List<UserRole> rcaCoordinators = userRoleService.getUserRolesByContractIdAndRole(contractId, "coordinator");
        List<UserRole> globalCoordinators = userRoleService.getGlobalCoordinators();
        if (CollectionUtils.isNotEmpty(globalCoordinators)) {
            rcaCoordinators.addAll(globalCoordinators);
        }
        model.put("referenceData", commonUtil.buildReferenceData());
        model.put("rcaCoordinators", rcaCoordinators);
        model.put("initiateRcaForm", initiateRcaForm);
        log.debug("loadRCACoordinators() ");
        return new ModelAndView("loadRCACoordinators");
    }

    @RequestMapping(value = "/calculateDueDate.htm", method = RequestMethod.GET)
    public
    @ResponseBody
    String calculateDueDate(@RequestParam("incidentEndTime") String incidentEndDateStr, @ModelAttribute RcaForm rcaForm, BindingResult result, Map<Object, Object> model) {
        String dueDate = "";
        log.debug("calling calculateDueDate() ................"+incidentEndDateStr);
        try {
            Date incidentEndDate = CommonUtil.convertToDate(incidentEndDateStr);
//            Date nextDate = new Date(incidentEndDate.getTime());
//            nextDate.setDate(nextDate.getDate() +1);
            if (incidentEndDate != null) {
                Date rcaDue = CommonUtil.addWorkingDays(incidentEndDate, 5);
                dueDate = rcaDue != null ? CommonUtil.convertDateToStringFormat(rcaDue) : "";
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return dueDate;
    }


    private void updateOpenActionItems(Integer rcaId) {
         Rca rca = rcaService.getRcaById(rcaId);
        List<RcaAction> rcaActions = rcaActionDao.getRcaActionsByRcaId(rca.getRcaId());
        Integer actionItemsOpened = 0;
        Integer actionItemsClosed = 0;
        if (CollectionUtils.isNotEmpty(rcaActions)) {
            for (RcaAction rcaAction : rcaActions) {
                // update closed and open items
                if (com.gps.util.StringUtils.isNotBlank(rcaAction.getActionStatus()) && rcaAction.getActionStatus().equalsIgnoreCase("Closed")) {
                    actionItemsClosed = actionItemsClosed + 1;
                }
                if (com.gps.util.StringUtils.isNotBlank(rcaAction.getActionStatus()) && rcaAction.getActionStatus().equalsIgnoreCase("Open")) {
                    actionItemsOpened = actionItemsOpened + 1;
                }
            }
            updateOpenAndClosedActionItems(rca, actionItemsOpened, actionItemsClosed);
        }
    }

    private void handleNewlyCreatedActions(RcaForm rcaForm) {

        processActionsNotesIDs(rcaForm);

        RcaEmailNotificationSetting emailNotificationSetting = rcaEmailNotificationSettingsDao.getRcaEmailNotificationSettingByContractId(rcaForm.getRca().getContract().getContractId());
        List<RcaAction> formRcaActions = rcaForm.getRcaActions();
        if (CollectionUtils.isNotEmpty(formRcaActions)) {
            for (RcaAction formRcaAction : formRcaActions) {
                RcaAction dbRcaAction = rcaActionDao.getRcaActionById(formRcaAction.getRcaActionId());
                if (StringUtils.isNullOrEmpty(dbRcaAction.getAssignedTo()) && !StringUtils.isNullOrEmpty(formRcaAction.getAssignedTo())) {
                    //check if the new action item notification is enabled then send the email
                    if (emailNotificationSetting != null && emailNotificationSetting.getRcaWorkflowNotificationEnabled().equalsIgnoreCase(ConstantDataManager.YES)
                            && emailNotificationSetting.getNewActionItemNotification().equalsIgnoreCase(ConstantDataManager.YES)) {
                        rcaActionNotificationService.sendRcaActionCreatedNotification(formRcaAction.getAssignedTo(), dbRcaAction);
                    }
                }
            }
        }
    }


    private void processActionsNotesIDs(RcaForm rcaForm) {
        if (CollectionUtils.isNotEmpty(rcaForm.getRcaActions())) {
            int i = 0;
            for (RcaAction rcaAction : rcaForm.getRcaActions()) {
                if (StringUtils.isNotBlank(rcaAction.getAssignedTo()) && BluePages.isNotesID(rcaAction.getAssignedTo())) {
                    rcaForm.getRcaActions().get(i).setAssignedTo(BluePages.getIntranetIdFromNotesId(rcaAction.getAssignedTo()));
                }
                i++;
            }
        }
    }


}
