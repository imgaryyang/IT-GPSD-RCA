
package com.gps.web.controller;


import com.gps.dao.*;
import com.gps.service.*;
import com.gps.util.BluePages;
import com.gps.util.CommonUtil;
import com.gps.util.ConstantDataManager;
import com.gps.util.GpsCacheManager;
import com.gps.util.UserSession;
import com.gps.vo.*;
import com.gps.vo.helper.*;
import com.gps.web.validator.RcaFormValidator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.portlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
@SessionAttributes({"userSession"})
public class RcaController {
    private static final Logger log = Logger.getLogger(RcaController.class);

    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");


    @Autowired
    GpsService gpsService;

    @Autowired
    CommonUtil commonUtil;

    @Autowired
    SessionACLService sessionACLService;

    @Autowired
    RcaService rcaService;

    @Autowired
    MessageSource ms;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    GpsUserService gpsUserService;

    @Autowired
    RcaCauseDao rcaCausesDao;

    @Autowired
    RcaNotificationService rcaNotificationService;

    @Autowired
    RcaFormValidator rcaFormValidator;

    @Autowired
    RcaCoordinatorDao rcaCoordinatorDao;

    @Autowired
    RcaUtilService rcaUtilService;

    @Autowired
    ContractContactDao contractContactDao;

    @Autowired
    ContractService contractService;
    @Autowired
    GpsCacheManager gpsCacheManager;
    @Autowired
    RcaEmailNotificationSettingDao rcaEmailNotificationSettingsDao;

    @Autowired
    RcaSupportingFileDao rcaSupportingFileDao;
    @Autowired
    private RcaNotificationAuditDao rcaNotificationAuditDao;


    @RequestMapping(value = "/rcas.htm", method = RequestMethod.GET)
    public String showForm(SearchFilter searchFilter, Map<Object, Object> model, HttpSession session, HttpServletRequest request) {
        Integer month = null;
        Integer year = null;

        if (searchFilter != null && searchFilter.getMonth() != null && searchFilter.getYear() != null) {
            month = searchFilter.getMonth();
            year = searchFilter.getYear();
        }

        Calendar calendar = Calendar.getInstance();
        if (month == null || year == null) {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
            month = calendar.get(Calendar.MONTH) + 1;
            year = calendar.get(Calendar.YEAR);
        }
        Map<String, Object> referenceData = commonUtil.buildReferenceData(session, "rca");
        List<RcaListing> rcaListings = rcaService.getRcasAndActionsByMonthAndYear(month, year);
        referenceData.put("listRcasAndActions", rcaListings);
        List<Rca> rcaList = new ArrayList<Rca>(rcaService.getRcaListByMonthAndYear(month, year));
        Set<String> rcaCoordinators = rcaUtilService.getRcaCoordinatorsFromRcaList(rcaList);
        referenceData.put("listRcaCoordinators", rcaCoordinators);
        loadRcaOwnersAndDelegates(referenceData, rcaList);

        // configure initiate RCA contracts
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        List<Contract> initiateRcaContracts = rcaUtilService.loadInitiateRcaContracts(userSession, referenceData, session);

        referenceData.put("initiateRcaContracts", initiateRcaContracts);
        referenceData.put("listRca", rcaList);
        referenceData.put("primaryTickets", rcaUtilService.loadPrimaryTickets(rcaListings));

        //SearchFilter searchFilter = new SearchFilter();
        searchFilter = new SearchFilter();
        searchFilter.setMonth(month);
        searchFilter.setYear(year);

        model.put("monthList", CommonUtil.getMonthList());
        model.put("yearList", CommonUtil.getYearList());
        model.put("hasCoordinatorOrDpeOrCreatorRole", hasCoordinatorOrDpeOrCreatorRole(initiateRcaContracts, gpsUserService.getUserByIntranetId(userSession.getGpsUser().getEmail())));
        model.put("referenceData", referenceData);
        model.put("searchFilter", searchFilter);
        model.put("contract", new Contract());
        model.put("initiateRcaForm", new InitiateRcaForm());
        model.put("basePath", CommonUtil.getBasePath(request));

        return "rcas";
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/rcaList.htm", method = RequestMethod.POST)
    public ModelAndView getRcas(@ModelAttribute SearchFilter searchFilter,
                                BindingResult result, Map<Object, Object> model, ModelMap modelMap, HttpSession session) {
        log.debug("Fetching page...." + searchFilter.getPagination().getPageNumber());
        Page page = null;
        if (searchFilter.getPagingAction().equals(Constant.NEW_SEARCH)) {
            log.debug("Fetching search results from db.....");
            page = rcaService.getRcaListBySearchFilter(searchFilter, session.getId());
            session.setAttribute("Allcontracts", page.getDataList());
            session.setAttribute("totalRowCount", page.getRowCount());
        }
        model.put("rcas", paginate((List<RcaListing>) session.getAttribute("Allcontracts"), searchFilter.getPagination().getPageNumber()));
        Long totalRowCount = (Long) session.getAttribute("totalRowCount");
        model.put("totalRowCount", session.getAttribute("totalRowCount"));
        model.put("pageNo", searchFilter.getPagination().getPageNumber());
        int endRow = searchFilter.getPagination().getPageNumber() * Pagination.PAGE_SIZE;
        model.put("startRow", (endRow - Pagination.PAGE_SIZE) + 1);
        model.put("endRow", endRow > totalRowCount.intValue() ? totalRowCount.intValue() : endRow);
        log.debug("displaying list of contract: " + session.getAttribute("totalRowCount"));
        return new ModelAndView("rcaList");
    }

    @RequestMapping(value = "/resetRcaFitler.htm", method = RequestMethod.GET)
    public String resetForm(@RequestParam Integer month, @RequestParam Integer year, Map<Object, Object> model, HttpSession session, HttpServletRequest request) {
        SearchFilter searchFilter = new SearchFilter();
        searchFilter.setMonth(month);
        searchFilter.setYear(year);
        return showForm(searchFilter, model, session, request);
    }


    @RequestMapping(value = "/validateRca.do", method = RequestMethod.POST)
    public
    @ResponseBody
    String validateRca(@ModelAttribute InitiateRcaForm initiateRcaForm, BindingResult result, Map<Object, Object> model, ModelMap modelMap) {
        String response = Constant.SUCCESS;

        String rcaContract = initiateRcaForm.getRcaContract();
        String rcaCoordinator = initiateRcaForm.getRcaCoordinator();

        if (StringUtils.isBlank(rcaContract)) {
            response = ms.getMessage(Constant.MSG_KEY_EMPTY_CONTRACT, null, Locale.US);
            return response;
        }
        if (StringUtils.isBlank(rcaCoordinator)) {
            response = ms.getMessage(Constant.MSG_KEY_EMPTY_RCA_COORDINATOR, null, Locale.US);
            return response;
        }
        return response;
    }


    @RequestMapping(value = "/initiateRCA.do", method = RequestMethod.POST)
    public String initiateRCA(@Valid InitiateRcaForm initiateRcaForm, BindingResult result, Map<Object, Object> model, ModelMap modelMap, HttpSession session) {
        String navigate = "redirect:/rcas.htm";
        try {
            UserSession userSession = (UserSession) session.getAttribute("userSession");
            String rcaNumber = rcaService.initiateRca(initiateRcaForm, userSession.getGpsUser().getEmail());
            model.put("initiateRcaForm", initiateRcaForm);
            if (StringUtils.isNotBlank(rcaNumber)) {
                navigate = "redirect:/editRca.htm?rcaNumber=" + rcaNumber;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return navigate;
    }

    @RequestMapping(value = "/editRca.htm", method = RequestMethod.GET)
    public String editRca(@RequestParam String rcaNumber, Map<Object, Object> model, HttpSession session, HttpServletRequest request) {
    	log.debug("/editRca.htm GET....."+rcaNumber);
        RcaForm rcaForm = new RcaForm();
        RcaCoordinator rcaCoordinator = null;
        Rca rca = rcaService.getRcaByNumber(rcaNumber);
        if (StringUtils.isNotBlank(rca.getRcaCoordinatorId())) {
            rcaForm.setRcaCoordinatorName(BluePages.getNotesIdFromIntranetId(rca.getRcaCoordinatorId()));
            rcaForm.setRcaCoordinatorManager(BluePages.getFirstLineMgrIntranetId(rca.getRcaCoordinatorId()));
        }

        // set the created by user
        GpsUser createdBy = gpsUserService.getUserById(rca.getCreatedBy());
        if(createdBy != null){
            createdBy.setNotesId(BluePages.getNotesIdFromIntranetId(createdBy.getEmail()));
            rca.setRcaCreatedBy(createdBy);
        }
        // load the primary rca Cause
        loadPrimaryRcaCause(rca);
        // Calculate the totalIncidentDuration and serviceRestoredDuration
        setIncidentAndServiceRestoreDuration(rca);
        // get the contract editors and dpe
        List<UserRole> contractEditors = userRoleService.getEditorListByContractIdAndRole(rca.getContract().getContractId(), ConstantDataManager.EDITOR_ROLE);
        List<UserRole> contractDpeList = userRoleService.getDpeListByContractIdAndRole(rca.getContract().getContractId(), ConstantDataManager.DPE_ROLE);
        // get the service lines
        List<ServiceDescription> outageCategories = gpsService.getServiceDescriptionListByType(ConstantDataManager.OUTAGE_CATEGORY);
        // get the cause areas
        List<CauseDescription> rootCauses = gpsService.getCauseDescriptionListByType(ConstantDataManager.ROOT_CAUSE);
        // set Logged-in user
        GpsUser loggedInUser = setLoggedInUser(session, rcaForm);
        // set the logged in user role
        configureLoggedInUserRole(rcaForm, rca, loggedInUser);
        // set the rca dates
        setRcaFormDates(rcaForm, rca);


        //load the supporting files
        rcaUtilService.loadSupportingFiles(rca, rcaForm);

        //load editors
        List<RcaEditor> rcaEditors = rcaService.getRcaEditorsByRcaId(rca.getRcaId());
        List<Integer> rcaSelectedEditorUserIds = new ArrayList<Integer>();
        if (CollectionUtils.isNotEmpty(rcaEditors)) {
            for (RcaEditor rcaEditor : rcaEditors) {
                rcaSelectedEditorUserIds.add(rcaEditor.getGpsUser().getUserId());
            }
        }
        log.debug("CustomerImpactedList = "+rca.getCustomerImpactedList());
        rcaForm.setCustImpactList(com.gps.util.StringUtils.commaSepareatedToList(rca.getCustomerImpactedList()));
        log.debug("setCustImpactList: "+rcaForm.getCustImpactList().size());
        rcaForm.setRcaEditorIds(rcaSelectedEditorUserIds);
        rcaForm.setRcaFormActionsHelper(new RcaFormActionsHelper());
        log.debug("Cache Customers: "+ gpsCacheManager.getAccountCustomers().size());
       
        rcaForm.setRca(rca);

        // process owner and delegate lotus id
        processOwnerAndDelegate(rcaForm);

        loadMonthInForm(rca, rcaForm);

        // load dpe approval request sent status
        loadDpeApprovalSentStatus(rcaForm, rca);

        // load the successfully saved message if exist.
        if(session.getAttribute("savedMessage") != null){
            String savedMessage = (String) session.getAttribute("savedMessage");
            if(StringUtils.isNotBlank(savedMessage)){
                rcaForm.setFormSavedMessage(savedMessage);
            }
            session.removeAttribute("savedMessage");
        }

        model.put("rcaForm", rcaForm);
        model.put("rcaCoordinator", rcaCoordinator);
        model.put("contractEditors", new HashSet<UserRole>(contractEditors));
        model.put("contractDpeList", new HashSet<UserRole>(contractDpeList));
        model.put("outageCategories", outageCategories);
        model.put("rootCauses", rootCauses);
        model.put("locationsOfBusiness", CommonUtil.getLocationsOfBusiness());
        model.put("locationsOfFailure", CommonUtil.getLocationsOfFailure());
        model.put("impactedTowers", CommonUtil.getImpactedTowers());
        model.put("customerImpactedList", gpsCacheManager.getAccountCustomers().keySet());
        model.put("rca", rca);
        model.put("rcaNumber", rcaNumber);
        model.put("basePath", CommonUtil.getBasePath(request));
        model.put("rcaMonth", rcaForm.getMonth());
        model.put("rcaYear", rcaForm.getRca().getYear());
        model.put("contractId", rcaForm.getRca().getContract().getContractId());
        model.put("contractTitle", rcaForm.getRca().getContract().getTitle());
        loadCauses(model, rca);

        log.debug("navigateTo: editRca");
        return "editRca";
    }




    @RequestMapping(value = "/editRca.htm", method = RequestMethod.POST)
    public String editRca(@RequestParam("file") MultipartFile file, @ModelAttribute RcaForm rcaForm, BindingResult result, Map<Object, Object> model, HttpSession session, HttpServletRequest request) {
        String navigateToListing = "redirect:/rcas.htm";
        String rcaFormUrl = "editRca";

        UserSession userSession = (UserSession) session.getAttribute("userSession");
        GpsUser loggedInUser = gpsUserService.getUserByIntranetId(userSession.getGpsUser().getEmail());
        rcaForm.setLoggedInUser(loggedInUser);

        // RCA Email Notification Setting
        RcaEmailNotificationSetting rcaEmailSetting = rcaEmailNotificationSettingsDao.getRcaEmailNotificationSettingByContractId(rcaForm.getRca().getContract().getContractId());

        // load the rca coordinator details...
        Rca rca_ = rcaService.getRcaById(rcaForm.getRca().getRcaId());
        rcaForm.getRca().setRcaCoordinatorId(rca_.getRcaCoordinatorId());
        rcaForm.getRca().setCustomerImpactedList(com.gps.util.StringUtils.getCommaSepareated(rcaForm.getCustImpactList()));
        
        if (rcaForm.getFormAction().equals(ConstantDataManager.SAVE)) {

            //process deleted support files
            rcaUtilService.processDeletedFiles(rcaForm);

            if (!rcaUtilService.supportingFileProcessed(file, rcaForm, result, loggedInUser)) {
                return rcaFormUrl;
            }
            rcaFormValidator.validateOnSave(rcaForm, result);
            if (result.hasErrors()) {
            	setUpDataInForm(rcaForm, model, session);
                return rcaFormUrl;
            } else {
                Rca dbRca = rcaService.saveRca(rcaForm, "Open", "Saved", rcaEmailSetting);

                if (rcaEmailSetting != null && rcaEmailSetting.getRcaWorkflowNotificationEnabled().equalsIgnoreCase(ConstantDataManager.YES)
                        && rcaEmailSetting.getRcaRoutingMatrixEnabled().equalsIgnoreCase(ConstantDataManager.YES)) {
                    rcaNotificationService.sendOwnerAndDelegateNotification(dbRca, rcaEmailSetting);
                }
                // saved successfully message
                session.setAttribute("savedMessage", "RCA Form has been saved successfully.");
                return "redirect:/editRca.htm?rcaNumber=" + dbRca.getRcaNumber();
            }
        }
        if (rcaForm.getFormAction().equals(ConstantDataManager.SUBMIT)) {
            try {

                //process deleted support files
                rcaUtilService.processDeletedFiles(rcaForm);

                if (!rcaUtilService.supportingFileProcessed(file, rcaForm, result, loggedInUser)) {
                    return rcaFormUrl;
                }

                rcaFormValidator.validate(rcaForm, result);
                if (result.hasErrors()) {
                    setUpDataInForm(rcaForm, model, session);
                    return rcaFormUrl;
                } else {
                    Rca dbRca = rcaService.submitRca(rcaForm);

                    // check if routing matrix notification is enabled then send the notification email
                    if (rcaEmailSetting != null && rcaEmailSetting.getRcaWorkflowNotificationEnabled().equalsIgnoreCase(ConstantDataManager.YES)
                            && rcaEmailSetting.getRcaRoutingMatrixEnabled().equalsIgnoreCase(ConstantDataManager.YES)) {
                        // update the notification audit
                        RcaNotificationAudit audit = rcaNotificationService.getRcaNotificationAudit(dbRca.getRcaId());
                        if (audit != null && (StringUtils.isBlank(audit.getIsCoordinatorApprovalRequestSent()) ||
                                (StringUtils.isNotBlank(audit.getIsCoordinatorApprovalRequestSent())
                                && audit.getIsCoordinatorApprovalRequestSent().equalsIgnoreCase("N")))) {

                           //send notification to coordinator for approval
                            rcaNotificationService.sendCoordinatorApprovalRequestNotification(dbRca);
                            audit.setIsCoordinatorApprovalRequestSent(ConstantDataManager.YES);
                            rcaNotificationService.saveOrUpdateNotificationAuditDetails(audit);
                        }
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        if (rcaForm.getFormAction().equals(ConstantDataManager.APPROVE)) {
            rcaFormValidator.validateOnApprove(rcaForm, result);
            if (result.hasErrors()) {
                setUpDataInForm(rcaForm, model, session);
                return rcaFormUrl;
            } else {
                Rca dbRca = rcaService.approveRca(rcaForm.getRca().getRcaId(), rcaForm.getRcaFormActionsHelper().getApprovalComments(), loggedInUser, rcaForm.getLoggedInUserRoles());

                // check if approve notification is enabled then send the notification email
                if (rcaEmailSetting != null && rcaEmailSetting.getRcaWorkflowNotificationEnabled().equalsIgnoreCase(ConstantDataManager.YES)
                        && rcaEmailSetting.getRcaApprovedNotification().equalsIgnoreCase(ConstantDataManager.YES)) {
                    rcaNotificationService.sendRcaApprovedNotification(dbRca);
                }
            }
        }

        if (rcaForm.getFormAction().equals(ConstantDataManager.REJECT)) {
            Rca dbRca = rcaService.rejectRca(rcaForm.getRca().getRcaId(), rcaForm.getRcaFormActionsHelper().getRejectionComments(), loggedInUser, rcaForm.getLoggedInUserRoles());

            // check if reject notification is enabled then send the notification email
            if (rcaEmailSetting != null && rcaEmailSetting.getRcaWorkflowNotificationEnabled().equalsIgnoreCase(ConstantDataManager.YES)
                    && rcaEmailSetting.getRcaRejectedNotification().equalsIgnoreCase(ConstantDataManager.YES)) {
                rcaNotificationService.sendRcaRejectedNotification(dbRca, rcaForm.getRcaFormActionsHelper().getRejectionComments());
            }
        }

        if (rcaForm.getFormAction().equals(ConstantDataManager.CANCEL)) {
            Rca dbRca = rcaService.cancelRca(rcaForm.getRca().getRcaId(), loggedInUser, rcaForm.getLoggedInUserRoles(), rcaForm.getRcaFormActionsHelper().getCancellationComments());

            // check if cancellation notification is enabled then send the notification email
            if (rcaEmailSetting != null && rcaEmailSetting.getRcaWorkflowNotificationEnabled().equalsIgnoreCase(ConstantDataManager.YES)
                    && rcaEmailSetting.getRcaCancelledNotification().equalsIgnoreCase(ConstantDataManager.YES)) {
                rcaNotificationService.sendRcaCancellationNotification(dbRca, rcaForm.getRcaFormActionsHelper().getCancellationComments());
            }
        }

        if (rcaForm.getFormAction().equals(ConstantDataManager.CLOSE)) {
            try {
                rcaFormValidator.validate(rcaForm, result);
                if (result.hasErrors()) {
                    setUpDataInForm(rcaForm, model, session);
                    return rcaFormUrl;
                } else {
                    Rca dbRca = rcaService.closeRca(rcaForm, "Closed", "Closed");

                    // check if closed notification is enabled then send the notification email
                    if (rcaEmailSetting != null && rcaEmailSetting.getRcaWorkflowNotificationEnabled().equalsIgnoreCase(ConstantDataManager.YES)
                            && rcaEmailSetting.getRcaClosedNotification().equalsIgnoreCase(ConstantDataManager.YES)) {
                        rcaNotificationService.sendRcaClosedNotification(dbRca);
                    }
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        if (rcaForm.getFormAction().equals(ConstantDataManager.RE_OPEN)) {
            try {
                Rca dbRca = rcaService.reopenRca(rcaForm, rcaForm.getRcaFormActionsHelper().getReopenComments(), loggedInUser, rcaForm.getLoggedInUserRoles());
                rcaNotificationService.sendRcaReOpenedNotification(dbRca, rcaForm.getRcaFormActionsHelper().getReopenComments());
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }

        return navigateToListing;
    }

    @RequestMapping(value = "/getRcaFile.htm", method = RequestMethod.GET)
    @ResponseBody
    public void getRcaFile(@RequestParam("rcaFileId") Integer rcaFileId, HttpServletResponse response) {
        RcaSupportingFile rcaSupportingFile = rcaSupportingFileDao.getFileById(rcaFileId);
        if (rcaSupportingFile != null) {
            try {
                response.setContentType(rcaSupportingFile.getMime());
                response.setContentLength((new Long(rcaSupportingFile.getSize()).intValue()));
                response.setHeader("content-Disposition", "attachment; filename=" + rcaSupportingFile.getName());// "attachment;filename=test.xls"
                response.getOutputStream().write(rcaSupportingFile.getContents());
            } catch (IOException ex) {
                log.info("Error writing file to output stream. Filename was '" + rcaSupportingFile.getName() + "'");
                log.error(ex.getMessage(), ex);
            }
        }
    }


    private void setUpDataInForm(RcaForm rcaForm, Map<Object, Object> model, HttpSession session) {
        // get the contract editors and dpe
        Rca rca = rcaService.getRcaById(rcaForm.getRca().getRcaId());

        loadPrimaryRcaCause(rca);

        // set the created by user
        GpsUser createdBy = gpsUserService.getUserById(rca.getCreatedBy());
        if(createdBy != null){
            createdBy.setNotesId(BluePages.getNotesIdFromIntranetId(createdBy.getEmail()));
            rca.setRcaCreatedBy(createdBy);
        }

        rcaForm.setRca(rca);

        // Calculate the totalIncidentDuration and serviceRestoredDuration
        setIncidentAndServiceRestoreDuration(rcaForm.getRca());

        // get the service lines
        List<ServiceDescription> outageCategories = gpsService.getServiceDescriptionListByType(ConstantDataManager.OUTAGE_CATEGORY);
        // get the cause areas
        List<CauseDescription> rootCauses = gpsService.getCauseDescriptionListByType(ConstantDataManager.ROOT_CAUSE);
        // set Logged-in user

        List<UserRole> contractEditors = userRoleService.getEditorListByContractIdAndRole(rca.getContract().getContractId(), ConstantDataManager.EDITOR_ROLE);
        List<UserRole> contractDpeList = userRoleService.getDpeListByContractIdAndRole(rca.getContract().getContractId(), ConstantDataManager.DPE_ROLE);

        model.put("contractEditors", new HashSet<UserRole>(contractEditors));
        model.put("contractDpeList", new HashSet<UserRole>(contractDpeList));

        model.put("outageCategories", outageCategories);
        model.put("rootCauses", rootCauses);
        model.put("locationsOfBusiness", CommonUtil.getLocationsOfBusiness());
        model.put("locationsOfFailure", CommonUtil.getLocationsOfFailure());
        model.put("impactedTowers", CommonUtil.getImpactedTowers());
        model.put("customerImpactedList", gpsCacheManager.getAccountCustomers().keySet());
        GpsUser loggedInUser = setLoggedInUser(session, rcaForm);
        // set the logged in user role
        configureLoggedInUserRole(rcaForm, rcaForm.getRca(), loggedInUser);

        loadCauses(model, rca);

        // process owner and delegate
        processOwnerAndDelegate(rcaForm);

        loadDpeApprovalSentStatus(rcaForm, rca);

        model.put("rcaMonth", rcaForm.getMonth());
        model.put("rcaYear", rcaForm.getRca().getYear());
        model.put("contractId", rcaForm.getRca().getContract().getContractId());
        model.put("contractTitle", rcaForm.getRca().getContract().getTitle());
    }

    private void loadPrimaryRcaCause(Rca rca) {
        List<RcaCause> rcaCauses = rcaCausesDao.getRcaCausesByRcaIdAndType(rca.getRcaId(), "Y");
        if (CollectionUtils.isNotEmpty(rcaCauses)) {
            for (RcaCause rcaCause : rcaCauses) {
                rca.setPrimaryRcaCause(rcaCause);
            }
        }
    }

    private void setIncidentAndServiceRestoreDuration(Rca rca) {
        Date incidentStartDate = null;
        Date incidentEndDate = null;

        if (rca.getIncidentStartTime() != null) {
            try {
                incidentStartDate = sdf.parse(rca.getIncidentStartTime().toString());
                setIncidentDuration(rca, incidentStartDate, incidentEndDate);

            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    private void setIncidentDuration(Rca rca, Date incidentStartDate, Date incidentEndDate) throws ParseException {
        if (rca.getIncidentEntTime() != null) {
            incidentEndDate = sdf.parse(rca.getIncidentEntTime().toString());
        }
        if (incidentStartDate != null && incidentEndDate != null) {
            String incidentDuration = CommonUtil.calculateDateTimeDifference(incidentStartDate, incidentEndDate);
            if (StringUtils.isNotBlank(incidentDuration)) {
                rca.setIncidentDuration(incidentDuration);
            }
        }
    }


    private List<?> paginate(List<?> dataList, Integer pageNo) {
        Integer startRow = (pageNo.intValue() * Pagination.PAGE_SIZE.intValue()) - Pagination.PAGE_SIZE.intValue();
        Integer endRow = startRow + Pagination.PAGE_SIZE;
        if (endRow > dataList.size())
            endRow = dataList.size();
        return dataList.subList(startRow, endRow);
    }

    private GpsUser setLoggedInUser(HttpSession session, RcaForm rcaForm) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        GpsUser loggedInUser = gpsUserService.getUserByIntranetId(userSession.getGpsUser().getEmail());
        if (loggedInUser == null) {
            loggedInUser = new GpsUser();
            loggedInUser.setEmail(userSession.getGpsUser().getEmail());
            gpsUserService.addUser(loggedInUser);
        }
        rcaForm.setLoggedInUser(loggedInUser);
        return loggedInUser;
    }

    private void configureLoggedInUserRole(RcaForm rcaForm, Rca rca, GpsUser loggedInUser) {
        List<String> rolesList = new ArrayList();
        List<String> roles = new ArrayList();

        // check for rca owner

        if (loggedInUser.getEmail().equalsIgnoreCase(rca.getRcaOwner())) {
            rcaForm.setRcaOwnerLoggedIn("true");
            roles.add("rcaOwner,");
            rolesList.add("owner");
        } else {
            rcaForm.setRcaOwnerLoggedIn("false");
        }

        // check for rca delegate
        if (loggedInUser.getEmail().equals(rca.getRcaDelegate())) {
            rcaForm.setRcaDelegateLoggedIn("true");
            roles.add("rcaDelegate,");
            rolesList.add("delegate");
        } else {
            rcaForm.setRcaDelegateLoggedIn("false");
        }

        // check for rca dpe
        if (rca.getRcaDpeId() != null) {
            GpsUser dpe = gpsUserService.getUserById(rca.getRcaDpeId());
            if (dpe != null && dpe.getUserId() == loggedInUser.getUserId()) {
                roles.add("rcaDpe");
                rolesList.add("dpe");
            }
        }

        // check for rca coordinator
        if (rca.getRcaCoordinatorId() != null) {
            if (rca.getRcaCoordinatorId().equalsIgnoreCase(loggedInUser.getEmail())) {
                rolesList.add("coordinator");
            }
        }

        //check if the user is creator
        GpsUser creator = gpsUserService.getUserById(rca.getCreatedBy());
        if (creator.getEmail().equalsIgnoreCase(loggedInUser.getEmail())) {
            rolesList.add("creator");
        }

        // check if user is reader
        UserRole readerRole = userRoleService.getUserRolesByContractIdAndUserIdAndRole(rca.getContract().getContractId(), loggedInUser.getUserId(), "reader");
        if (readerRole != null) {
            rolesList.add("reader");
        }

        // check if user is admin
        if (rcaUtilService.isAdmin(loggedInUser.getUserId())) {
            rolesList.add("admin");
        }

        configureGlobalRoles(loggedInUser, rolesList);

        // check for Contract based roles
        List<UserRole> contractUserRoles = userRoleService.getUserRolesByContractIdAndUserId(rca.getContract().getContractId(), loggedInUser.getUserId());
        if (CollectionUtils.isNotEmpty(contractUserRoles)) {
            for (UserRole role : contractUserRoles) {
                if (role.getRole().equalsIgnoreCase("owner") && !rolesList.contains("owner")) {
                    rolesList.add("owner");
                }
                if (role.getRole().equalsIgnoreCase("delegate") && !rolesList.contains("delegate")) {
                    rolesList.add("delegate");
                }
                if (role.getRole().equalsIgnoreCase("dpe") && !rolesList.contains("dpe")) {
                    rolesList.add("dpe");
                }
                if (role.getRole().equalsIgnoreCase("editor") && !rolesList.contains("editor")) {
                    rolesList.add("editor");
                }
            }
        }


        rcaForm.setUserRoles(StringUtils.join(rolesList, ","));
        rcaForm.setLoggedInUserRoles(StringUtils.join(roles, ","));


    }

    private void configureGlobalRoles(GpsUser loggedInUser, List<String> rolesList) {
        //check for global coordinator
        if (rcaUtilService.isGlobalCoordinator(loggedInUser.getUserId())) {
            rolesList.add("globalCoordinator");
        }
        //check for global delegate
        if (rcaUtilService.isGlobalDelegate(loggedInUser.getUserId())) {
            rolesList.add("globalDelegate");
        }
        //check for global dpe
        if (rcaUtilService.isGlobalDpe(loggedInUser.getUserId())) {
            rolesList.add("globalDpe");
        }
        //check for global owner
        if (rcaUtilService.isGlobalOwner(loggedInUser.getUserId())) {
            rolesList.add("globalOwner");
        }
        //check for global coordinator or global
        if (rcaUtilService.isGlobalEditor(loggedInUser.getUserId())) {
            rolesList.add("globalEditor");
        }
        //check for global reader
        if (rcaUtilService.isGlobalReader(loggedInUser.getUserId())) {
            rolesList.add("globalReader");
        }

        // check for global creator
        if (rcaUtilService.isGlobalCreator(loggedInUser.getUserId())) {
            rolesList.add("globalCreator");
        }
    }

    private void setRcaFormDates(RcaForm rcaForm, Rca rca) {
        SimpleDateFormat timeStampFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        RcaFormDateHelper rcaFormDateHelper = new RcaFormDateHelper();
        rcaFormDateHelper.setRcaClosedDate(rca.getCloseDate() != null ? CommonUtil.convertDateToStringFormat(rca.getCloseDate()) : null);
        rcaFormDateHelper.setRcaDueDate(rca.getDueDate() != null ? CommonUtil.convertDateToStringFormat(rca.getDueDate()) : null);
        rcaForm.setRcaDueDate(rca.getDueDate() != null ? CommonUtil.convertDateToStringFormat(rca.getDueDate()) : null);
        rcaFormDateHelper.setIncidentStartDateTime(rca.getIncidentStartTime() != null ? timeStampFormatter.format(rca.getIncidentStartTime()) : null);
        rcaFormDateHelper.setIncidentEndDateTime(rca.getIncidentEntTime() != null ? timeStampFormatter.format(rca.getIncidentEntTime()) : null);
        rcaFormDateHelper.setRcaCreatedDate(rca.getCreateDate() != null ? CommonUtil.convertDateToStringFormat(rca.getCreateDate()) : "");
        rcaForm.setRcaFormDateHelper(rcaFormDateHelper);

    }

    private String getAccessLevel(String rcaStage, SessionAcl sessionAcl) {
        String accessLevel = null;
        if (sessionAcl != null) {
            if (rcaStage.equalsIgnoreCase("active") || rcaStage.equalsIgnoreCase("open") || rcaStage.equalsIgnoreCase("awaiting")) {
                accessLevel = String.valueOf(sessionAcl.getActiveAccessLevel());
            }
            if (rcaStage.equalsIgnoreCase("approved")) {
                accessLevel = String.valueOf((sessionAcl.getApprovedAccessLevel()));
            }
        }
        return accessLevel;
    }


    private void loadMonthInForm(Rca rca, RcaForm rcaForm) {
        if (rca.getMonth() != null) {
            if (rca.getMonth() == 1)
                rcaForm.setMonth("January");
            if (rca.getMonth() == 2)
                rcaForm.setMonth("February");
            if (rca.getMonth() == 3)
                rcaForm.setMonth("March");
            if (rca.getMonth() == 4)
                rcaForm.setMonth("April");
            if (rca.getMonth() == 5)
                rcaForm.setMonth("May");
            if (rca.getMonth() == 6)
                rcaForm.setMonth("June");
            if (rca.getMonth() == 7)
                rcaForm.setMonth("July");
            if (rca.getMonth() == 8)
                rcaForm.setMonth("August");
            if (rca.getMonth() == 9)
                rcaForm.setMonth("September");
            if (rca.getMonth() == 10)
                rcaForm.setMonth("October");
            if (rca.getMonth() == 11)
                rcaForm.setMonth("November");
            if (rca.getMonth() == 12)
                rcaForm.setMonth("December");
        }
    }

    private String hasCoordinatorOrDpeOrCreatorRole(List<Contract> initiateRcaContracts, GpsUser loggedInUser) {

        if (rcaUtilService.isGlobalCoordinator(loggedInUser.getUserId()) || rcaUtilService.isGlobalDpe(loggedInUser.getUserId())) {
            return "true";
        }
        for (Contract initiateContract : initiateRcaContracts) {
            Contract contract = contractService.getContractByIdAndTitle(initiateContract.getContractId());
            if ((StringUtils.isNotBlank(contract.getContractHelper().getPriRcacEmail()) && contract.getContractHelper().getPriRcacEmail().equalsIgnoreCase(loggedInUser.getEmail()))
                    || (StringUtils.isNotBlank(contract.getContractHelper().getDpeEmail()) && contract.getContractHelper().getDpeEmail().equalsIgnoreCase(loggedInUser.getEmail()))) {
                return "true";
            }
            UserRole coordinator = userRoleService.getUserRolesByContractIdAndUserIdAndRole(contract.getContractId(), loggedInUser.getUserId(), "coordinator");
            if (coordinator != null) {
                return "true";
            }
            UserRole dpe = userRoleService.getUserRolesByContractIdAndUserIdAndRole(contract.getContractId(), loggedInUser.getUserId(), "dpe");
            if (dpe != null) {
                return "true";
            }
            UserRole creator = userRoleService.getUserRolesByContractIdAndUserIdAndRole(contract.getContractId(), loggedInUser.getUserId(), "creator");
            if (creator != null) {
                return "true";
            }
        }

        // check the global roles
        if (rcaUtilService.isGlobalCoordinator(loggedInUser.getUserId())) {
            return "true";
        }

        //check for global dpe
        if (rcaUtilService.isGlobalDpe(loggedInUser.getUserId())) {
            return "true";
        }
        // check for global creator
        if (rcaUtilService.isGlobalCreator(loggedInUser.getUserId())) {
            return "true";
        }


        return "false";
    }


    private void loadRcaOwnersAndDelegates(Map<String, Object> referenceData, List<Rca> rcaList) {
        Set<String> rcaOwners = new HashSet<String>();
        Set<String> rcaDelegates = new HashSet<String>();

        for (Rca rca : rcaList) {
            if (StringUtils.isNotBlank(rca.getRcaOwner())) {
                rcaOwners.add(rca.getRcaOwner());
            }
            if (StringUtils.isNotBlank(rca.getRcaDelegate())) {
                rcaDelegates.add(rca.getRcaDelegate());
            }
        }
        referenceData.put("rcaDelegates", new ArrayList<String>(rcaDelegates));
        referenceData.put("rcaOwners", new ArrayList<String>(rcaOwners));
    }


    private void loadDpeApprovalSentStatus(RcaForm rcaForm, Rca rca) {
        RcaNotificationAudit audit = rcaNotificationAuditDao.getNotificationAuditByRcaId(rca.getRcaId());
        if (audit != null) {
            rcaForm.setIsDpeApprovalRequestSent(StringUtils.isNotBlank(audit.getIsDpeApprovalRequestSent()) ? audit.getIsDpeApprovalRequestSent() : "N");
        }
    }

    private void processOwnerAndDelegate(RcaForm rcaForm) {
        try {
            if (StringUtils.isNotBlank(rcaForm.getRca().getRcaOwner()) && !BluePages.isNotesID(rcaForm.getRca().getRcaOwner())) {
                rcaForm.getRca().setRcaOwner(BluePages.getNotesIdFromIntranetId(rcaForm.getRca().getRcaOwner()));
            }
            if (StringUtils.isNotBlank(rcaForm.getRca().getRcaDelegate()) && !BluePages.isNotesID(rcaForm.getRca().getRcaDelegate())) {
                rcaForm.getRca().setRcaDelegate(BluePages.getNotesIdFromIntranetId(rcaForm.getRca().getRcaDelegate()));
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void loadCauses(Map<Object, Object> model, Rca rca) {
        try {
            if (rca.getPrimaryRcaCause() != null && StringUtils.isNotBlank(rca.getPrimaryRcaCause().getOutageCategory())) {
                model.put("outageSubCategories", gpsService.getServiceDescriptionListByParentId(Integer.parseInt(rca.getPrimaryRcaCause().getOutageCategory())));
            }
            if (rca.getPrimaryRcaCause() != null && StringUtils.isNotBlank(rca.getPrimaryRcaCause().getOutageSubCategory())) {
                model.put("outageSubCategories2", gpsService.getServiceDescriptionListByParentId(Integer.parseInt(rca.getPrimaryRcaCause().getOutageSubCategory())));
            }
            if (rca.getPrimaryRcaCause() != null && StringUtils.isNotBlank(rca.getPrimaryRcaCause().getRootOrContributingCause())) {
                model.put("causeCategories", gpsService.getCauseDescriptionListByParentName(rca.getPrimaryRcaCause().getRootOrContributingCause()));
            }
            if (rca.getPrimaryRcaCause() != null && StringUtils.isNotBlank(rca.getPrimaryRcaCause().getRootOrContributingCause())) {
                model.put("causeSubCategories", gpsService.getCauseDescriptionListByParentName(rca.getPrimaryRcaCause().getCauseCategory()));
            }
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }

}
