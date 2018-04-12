
package com.gps.web.controller;

import com.gps.dao.RcaEmailNotificationSettingDao;
import com.gps.service.*;
import com.gps.util.CommonUtil;
import com.gps.util.ConstantDataManager;
import com.gps.util.GpsCacheManager;
import com.gps.util.UserSession;
import com.gps.vo.Contract;
import com.gps.vo.GpsUser;
import com.gps.vo.RcaCoordinator;
import com.gps.vo.SessionAcl;
import com.gps.vo.helper.*;
import com.gps.web.validator.ContractValidator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.portlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Controller
@SessionAttributes({"userSession"})
public class ContractController {
    private static final Logger log = Logger.getLogger(ContractController.class);

    @Autowired
    ClientService clientCommonService;

    @Autowired
    ContractService contractService;


    @Autowired
    GpsCacheManager gpsCacheManager;

    @Autowired
    GpsService gpsService;

    @Autowired
    ContractProcessService contractProcessService;

    @Autowired
    ProcessService processService;

    @Autowired
    CommonUtil commonUtil;

    @Autowired
    SessionACLService sessionACLService;

    @Autowired
    private ContractValidator contractValidator;

    @Autowired
    GpsUserService gpsUserService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    private RcaEmailNotificationSettingDao rcaEmailNotificationSettingDao;

    @RequestMapping(value = "/contracts.htm", method = RequestMethod.GET)
    public String showForm(Map<Object, Object> model, HttpSession session) {
        model.put("referenceData", commonUtil.buildReferenceData(session));
        model.put("searchFilter", new SearchFilter());
        model.put("contract", new Contract());

        return "contracts";
    }


    @RequestMapping(value = "/contractList.htm", method = RequestMethod.POST)
    public ModelAndView getContracts(@ModelAttribute SearchFilter searchFilter, BindingResult result, Map<Object, Object> model, ModelMap modelMap, HttpSession session) {
        log.debug("Fetching page...." + searchFilter.getPagination().getPageNumber());
        Page page = null;
        if (searchFilter.getPagingAction().equals(Constant.NEW_SEARCH)) {
            log.debug("Fetching search results from db.....");
            page = contractService.getContractsBySearchFilter(searchFilter, session.getId());
            session.setAttribute("Allcontracts", page.getDataList());
            session.setAttribute("totalRowCount", page.getRowCount());
        }

        model.put("contracts", paginate((List<Contract>) session.getAttribute("Allcontracts"), searchFilter.getPagination().getPageNumber()));
        Long totalRowCount = (Long) session.getAttribute("totalRowCount");
        model.put("totalRowCount", session.getAttribute("totalRowCount"));
        model.put("pageNo", searchFilter.getPagination().getPageNumber());
        int endRow = searchFilter.getPagination().getPageNumber() * Pagination.PAGE_SIZE;
        model.put("startRow", (endRow - Pagination.PAGE_SIZE) + 1);
        model.put("endRow", endRow > totalRowCount.intValue() ? totalRowCount.intValue() : endRow);

        log.debug("displaying list of contract: " + session.getAttribute("totalRowCount"));
        return new ModelAndView("contractList");
    }

    private List<?> paginate(List<?> dataList, Integer pageNo) {
        Integer startRow = (pageNo.intValue() * Pagination.PAGE_SIZE.intValue()) - Pagination.PAGE_SIZE.intValue();
        Integer endRow = startRow + Pagination.PAGE_SIZE;
        if (endRow > dataList.size())
            endRow = dataList.size();
        return dataList.subList(startRow, endRow);
    }


    @RequestMapping(value = "/editContract.htm", method = RequestMethod.GET)
    public String editContract(@RequestParam Integer contractId, Map<Object, Object> model, HttpSession session) {
        Contract contract = contractService.getContractByIdAndTitle(contractId);
        SessionAcl sessionAcl = sessionACLService.getSessionAcl(session.getId(), contractId, "Profile");
        getAccessLevel(contract, sessionAcl);
        contractService.loadRcaEmailSettings(contract);
        log.debug("sessionAclId = " + sessionAcl.getSessionAclId() + ", level = " + sessionAcl.getActiveAccessLevel());
        model.put("contract", contract);
        model.put("rcaCoordinators", contract.getRcaCoordinators());
        model.put("sessionAcl", sessionAcl);
        return "editContract";
    }



    @RequestMapping(value = "/editContract.htm", method = RequestMethod.POST)
    public String getContracts(@ModelAttribute UserSession userSession, @ModelAttribute Contract contract,
                               BindingResult result, Map<Object, Object> model, ModelMap modelMap, HttpSession session, HttpServletRequest request) {

        int contractId = contract.getContractId();
        log.info("contractId --> " + contractId);


        ContractHelper contractHelper = contract.getContractHelper();

        // Update the RCA Coordinator
        processRCACoordinators(contract, request, contractHelper);


        String formAction = contractHelper.getFormAction();
        log.info("formAction : " + formAction);

        GpsUser intranetId = userSession.getGpsUser();
        Contract refContract = contractService.getContractByIdAndTitle(contractId);

        contract.setGpsUser(intranetId);

        Map<String, Object> referenceData = showEditContract(refContract, session);

        if (formAction.equalsIgnoreCase(ConstantDataManager.SUBMIT) || (formAction.equalsIgnoreCase("save") && refContract.getState().equalsIgnoreCase("2"))) {
            contractValidator.validate(contract, result);
            log.info("validator result: " + result.hasErrors());
            if (result.hasErrors()) {
                log.info("Errors found in contract: " + contract.getContractId());
                //now clear indexes.
                model.put("referenceData", referenceData);
                model.put("contract", contract);
                //	model.put("sessionAcl", sessionACLService.getSessionAcl(session.getId(), contractId, "Profile"));
                return "editContract";
            } else {
                log.debug("Succesfully validated contract: " + contract.getContractId());
                contractService.processContractSubmit(contract);
                log.debug("redirect:/contracts.htm");
                return "redirect:/contracts.htm";
            }
        } else if (formAction.equalsIgnoreCase("reject")) {
            contractService.processContractSubmit(contract);
            return "redirect:/contracts.htm";
        }
        else if (formAction.equalsIgnoreCase("save")) {
            contractService.processContractSubmit(contract);
            log.debug("successfull");
            return editContract(contract.getContractId(), model, session);
        } else if (formAction.equalsIgnoreCase("back")) {
            return "redirect:/contracts.htm";
        } else if (formAction.equalsIgnoreCase("reset")) {
            contractService.processContractSubmit(contract);
            return "redirect:/contracts.htm";
        }

        return editContract(contract.getContractId(), model, session);

    }


    @RequestMapping(value = "/addRcaCoordinator.htm", method = RequestMethod.GET)
    @ResponseBody
    public String addRcaCoordinator(@RequestParam("contract_id") Integer contractId, @ModelAttribute Contract contract, BindingResult result, Map<Object, Object> model, ModelMap modelMap, HttpSession session) {
        String newRcaCoId = null;
        if (contractId != null) {
            Contract dbContract = contractService.getContractByIdAndTitle(contractId);
            newRcaCoId = contractService.addRcaCoordinator(dbContract);
        }
        return newRcaCoId;
    }


    @RequestMapping(value = "/deleteRcaCoordinator.htm", method = RequestMethod.GET)
    @ResponseBody
    public String deleteRcaCoordinator(@RequestParam("rca_coordinator_id") Integer rcaCoordinatorId, @ModelAttribute RcaForm rcaForm, BindingResult result, Map<Object, Object> model) {
        String response = Constant.FAIL;
        log.debug("calling deleteRcaCoordinator() ................" + rcaCoordinatorId);
        if (rcaCoordinatorId != null) {
            Boolean isRcaCoordinatorDeleted = contractService.deleteRcaCoordinator(rcaCoordinatorId);
            if (isRcaCoordinatorDeleted) {
                response = Constant.SUCCESS;
            }
        }
        return response;
    }


    private void processRCACoordinators(Contract contract, HttpServletRequest request, ContractHelper contractHelper) {

        int rcaRowCount = Integer.parseInt(request.getParameter("rowCount"));
        if (rcaRowCount > 0) {
            //   rcaRowCount++;
            for (int i = 0; i <= rcaRowCount; i++) {

                String rcaCoName = request.getParameter("rca_name_" + i);
                String rcaCoEmail = request.getParameter("rca_email_" + i);
                String rcaCoPhone = request.getParameter("rca_phone_" + i);
                String rcaCoordinatorId = request.getParameter("rca_coordinator_id_" + i);

                if (StringUtils.isNotBlank(rcaCoordinatorId)) {
                    RcaCoordinator dbRcaCoordinator = contractService.getRcaCoordinatorById(Integer.parseInt(rcaCoordinatorId));
                    if (dbRcaCoordinator != null) {
                        dbRcaCoordinator.getCoordinator().setCoordinatorName(rcaCoName);
                        dbRcaCoordinator.getCoordinator().setIntranetId(rcaCoEmail);
                        dbRcaCoordinator.getCoordinator().setPhone(rcaCoPhone);

                        // adding rca coordinators in list
                        if (CollectionUtils.isNotEmpty(contract.getContractHelper().getRcaCoordinators())) {
                            contract.getContractHelper().getRcaCoordinators().add(dbRcaCoordinator);
                        } else {
                            List<RcaCoordinator> rcaCoordinators = new ArrayList<RcaCoordinator>();
                            rcaCoordinators.add(dbRcaCoordinator);
                            contract.getContractHelper().setRcaCoordinators(rcaCoordinators);
                        }
                    }
                }
            }
        }
    }


    @RequestMapping(value = "/modifyDeleteContracts.htm", method = RequestMethod.GET)
    public String showModifyDeleteContracts(Map<Object, Object> model, HttpSession session) {
        model.put("referenceData", commonUtil.buildReferenceData(session));
        model.put("searchFilter", new SearchFilter());
        model.put("contract", new Contract());
        return "modifyDeleteContracts";
    }

    @RequestMapping(value = "/modifyDeleteContractList.htm", method = RequestMethod.POST)
    public ModelAndView getModifyDeleteContractList(@ModelAttribute SearchFilter searchFilter,
                                                    BindingResult result, Map<Object, Object> model, ModelMap modelMap, HttpSession session) {
        log.debug("modifyDeleteContractList.htm ..............");
        CommandHelper commandHelper = new CommandHelper();
        List<Contract> contracts = null;
        Contract contract = null;
        contracts = contractService.getContractsByFilter(searchFilter);
        // setting the page
        Page page = new Page();
        page.setDataList(contracts);
        page.setRowCount((long) contracts.size());
        session.setAttribute("Allcontracts", page.getDataList());
        session.setAttribute("totalRowCount", page.getRowCount());

        commandHelper.setContracts(contracts);
        if (contracts != null && contracts.size() > 0) {
            contract = contracts.get(0);
        } else {
            contract = getContract();
        }

        model.put("commandHelper", commandHelper);
        //model.put("contracts", contracts);
        model.put("contract", contract);
        model.put("referenceData", commonUtil.buildReferenceData(session));
        model.put("contracts", paginate((List<Contract>) session.getAttribute("Allcontracts"), searchFilter.getPagination().getPageNumber()));
        model.put("totalRowCount", session.getAttribute("totalRowCount"));
        model.put("pageNo", searchFilter.getPagination().getPageNumber());
        int endRow = searchFilter.getPagination().getPageNumber() * Pagination.PAGE_SIZE;
        model.put("startRow", (endRow - Pagination.PAGE_SIZE) + 1);
        model.put("endRow", endRow);
        log.debug("ModelAndView(modifyDeleteContractList)");
        return new ModelAndView("modifyDeleteContractList");
    }


    @RequestMapping(value = "/deleteContract.htm", method = RequestMethod.GET)
    public
    @ResponseBody
    String deleteContract(@RequestParam("contract_id") String contractID, Map<Object, Object> model, HttpSession session) {
        log.warn("/deleteContract.htm [GET].... " + contractID);
        Integer contractId = new Integer(contractID);
        String response = "Successfully Deleted the contract " + contractId;
        if (contractID == null || contractID.isEmpty()) {
            response = "Can not proceed with action contractId is null ";
        } else {
            try {
                contractService.removeContract(contractId);
            } catch (Exception e) {
                log.warn("[Exception] " + e.getMessage(), e);
                response = "Unable to delete contract. Please try later";
            }
        }
        model.put("referenceData", commonUtil.buildReferenceData(session));

        return response;
    }

    @RequestMapping(value = "/showModifyContract.htm", method = RequestMethod.GET)
    public String showModifyContract(@RequestParam("contract_id") String contractID, Map<Object, Object> model, ModelMap modelMap, HttpServletRequest request, HttpSession session) {
        String referer = request.getHeader("Referer");
        if (contractID == null || contractID.isEmpty()) {
            return "redirect:" + referer;
        }
        Integer contractId = new Integer(contractID);
        Contract contract = null;
        try {
            contract = contractService.getContractByIdAndTitle(contractId);
            contract.getContractHelper().setCustomerPreference("Listed");
        } catch (Exception e) {
            log.warn("[Exception] " + e.getMessage(), e);
        }
        model.put("contract", contract);
        model.put("referenceData", commonUtil.buildReferenceData(session));
        return "showModifyContract";
    }

    @RequestMapping(value = "/addNewContract.htm", method = RequestMethod.POST)
    public
    @ResponseBody
    String addContract(@ModelAttribute Contract contract, BindingResult result, Map<Object, Object> model, ModelMap modelMap, HttpSession session) {
        String response = "Successfully added new contract in the system.";

        if (StringUtils.isBlank(contract.getTitle())) {
            return "Failed to add contract, Please enter contract name.";
        }
        if (StringUtils.isNotBlank(contract.getTitle()) && isContractAlreadyExist(contract.getTitle())) {
            return "Failed to add contract, contract with same name already exists.";
        }

        UserSession userSession = (UserSession) session.getAttribute("userSession");
        log.debug("addContract()....contractId=" + contract.getContractId());

        // process Gps User
        processGpsUser(contract, userSession);

        try {
            if (contract.getContractId() == null) {
                contract.setCreatedBy(userSession.getGpsUser().getUserId());
                contractService.addContract(contract);
            } else {
                response = "Successfully modified contract.";
                contractService.modifyContract(contract);
                log.debug("modified contract successfull.");
            }
            session.getServletContext().removeAttribute("referenceContracts");
            commonUtil.buildReferenceData(session);
        } catch (Exception e) {
            log.warn("#Exception#", e);
            if (contract.getContractId() == null) {
                response = "Failed to add new contract in the system, please try later.";
            } else {
                response = "Failed to modify contract, please try later.";
            }
        }
        return response;
    }

    private boolean isContractAlreadyExist(String title) {
        Contract contract = contractService.getContractByTitle(title);
        if (contract != null) {
            return true;
        }
        return false;
    }

    private void processGpsUser(Contract contract, UserSession userSession) {
        GpsUser user = new GpsUser();
        user.setUserId(userSession.getGpsUser().getUserId());
        user.setEmail(userSession.getGpsUser().getEmail());
        GpsUser dbUser = contractService.getUserByIntranetId(user.getEmail());
        if (dbUser == null) {
            contractService.addUser(user);
        }
        contract.setGpsUser(user);
    }

    @RequestMapping(value = "/showAddContract.htm", method = RequestMethod.GET)
    public String showAddContract(@ModelAttribute Contract contract,
                                  BindingResult result, Map<Object, Object> model, ModelMap modelMap, HttpSession session) {

        model.put("referenceData", commonUtil.buildReferenceData(session));
        model.put("contract", new Contract());
        return "showAddContract";
    }

    /**
     * Loads the contract details
     *
     * @param contractId
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "/showContractDetails.htm", method = RequestMethod.GET)
    public
    @ResponseBody
    Contract showContractDetails(@RequestParam("contract_id") String contractId, Map<Object, Object> model, HttpSession session) {
        Contract contract = contractService.getContractByIdAndTitle(Integer.parseInt(contractId));
        model.put("contract", contract);
        return contract;
    }

    private Map<String, Object> showEditContract(Contract contract, HttpSession session) {
        log.info("contract.getContractId() -->" + contract.getContractId());

        Map<String, Object> referenceData = commonUtil.buildReferenceData(session);


        return referenceData;
    }

    private List<Contract> getContracts() {

        return contractService.getAllContracts();
    }

    private Contract getContract() {
        Contract contract1 = new Contract();
        contract1.setContractId(1);
        contract1.setTitle("1967 Indus Profile");
        contract1.setCreatedOn(new Timestamp((new Date()).getTime()));
        //contract1.setLanguage("en");
        return contract1;
    }

    private void getAccessLevel(Contract contract, SessionAcl sessionAcl) {
        if (sessionAcl != null) {
            ContractHelper helper = getHelper(contract);
            if (StringUtils.isNotBlank(contract.getState()) && contract.getState().equals("0")) {
                helper.setAccessLevel(String.valueOf(sessionAcl.getActiveAccessLevel()));
            }
            if (StringUtils.isNotBlank(contract.getState()) && contract.getState().equals("2")) {
                helper.setAccessLevel(String.valueOf(sessionAcl.getApprovedAccessLevel()));
            }
            contract.setContractHelper(helper);
        }
    }

    private ContractHelper getHelper(Contract contract) {
        ContractHelper helper = null;
        if (contract.getContractHelper() == null) {
            helper = new ContractHelper();
        } else {
            helper = contract.getContractHelper();
        }
        return helper;
    }


}
