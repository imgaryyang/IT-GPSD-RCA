package com.gps.web.controller;

import com.gps.service.ContractService;
import com.gps.service.SupportService;
import com.gps.util.CommonUtil;
import com.gps.util.ConstantDataManager;
import com.gps.util.StringUtils;
import com.gps.util.UserSession;
import com.gps.vo.Contract;
import com.gps.vo.ContractRequest;
import com.gps.vo.helper.ArchiveSearchFilter;
import com.gps.vo.helper.Constant;
import com.gps.web.validator.SupportValidator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@SessionAttributes({"userSession"})
public class SupportController {
    private static Logger log = Logger.getLogger(SupportController.class);

    @Autowired
    CommonUtil commonUtil;

    @Autowired
    SupportService supportService;

    @Autowired
    ContractService contractService;

    @Autowired
    SupportValidator supportValidator;

    private String computeAction(String status) {
        String action = "Review";
        if (status.equals(Constant.NEW_STATE)) {

        }

        return action;
    }

    @RequestMapping(value = "/accessRequest.htm", method = RequestMethod.GET)
    public String accessRequest(@RequestParam(value = "requestId", required = false) Long requestId,
                                @RequestParam(value = "title", required = false) String title,
                                @RequestParam(value = "action", required = false) String action,
                                @ModelAttribute ContractRequest contractRequest, Map<Object, Object> model, HttpServletRequest request, HttpSession session) {
        log.debug("loading support accessRequest................" + requestId);
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        Contract contract = null;
        if (title != null && !title.equalsIgnoreCase("All")) {
            title = handleAccountTitles(title);
            contract = contractService.getContractByTitle(title);
            contractRequest.setContractName(title);
        } else if (StringUtils.isNotBlank(title) && title.equalsIgnoreCase("All")) {
            contractRequest.setContractName("All");
        }
        if (requestId != null) {
            contractRequest = supportService.getContractRequest(requestId);
            if (contractRequest.getContractName() != null && !contractRequest.getContractName().isEmpty()) {
                if (StringUtils.isNotBlank(title) && !title.equalsIgnoreCase("All") && title != contractRequest.getContractName()) {
                    contract = contractService.getContractByTitle(title);
                    contractRequest.setContractName(contract.getTitle());
                    contractRequest.setContractId(contract.getContractId());
                } else if (StringUtils.isNotBlank(title) && !title.equalsIgnoreCase("All") && title == contractRequest.getContractName()) {
                    contract = contractService.getContractByTitle(contractRequest.getContractName());
                }else if(StringUtils.isBlank(title) && StringUtils.isNotBlank(contractRequest.getContractName())){
                    contract = contractService.getContractByTitle(contractRequest.getContractName());
                }

            }
        }

        // populating model

        if (userSession.getGpsUser().getEmail().equalsIgnoreCase(ConstantDataManager.APPLICATION_OWNER)) {
            model.put("isApplicationOwner", "true");
        }
        model.put("userSession", userSession);
        if(StringUtils.isNotBlank(action)){
            contractRequest.setAction(action);
            model.put("action", action);

        }
        model.put("contractRequest", contractRequest);
        model.put("contract", contract);
        model.put("referenceData", commonUtil.buildReferenceData());
        if (requestId != null) {
            model.put("requestId", requestId);
        }
        log.debug("support singleAclRequest page processing completed.");
        return "accessRequest";
    }

    private String handleAccountTitles(String title) {
        if (StringUtils.isNotBlank(title) && title.equalsIgnoreCase("GPS D FA ")) {
            return "GPS D FA & SCM";
        }
        return title;
    }

    @RequestMapping(value = "/accessRequest.htm", method = RequestMethod.POST)
    public String saveAccessRequest(@ModelAttribute ContractRequest contractRequest, BindingResult result, Map<Object, Object> model, HttpServletRequest request, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        log.debug("processing accessRequest................");
        if (contractRequest.getFormAction() != null && contractRequest.getFormAction().equalsIgnoreCase("back")) {
            return "redirect:/support.htm";
        }
        contractRequest.setRequestType(Constant.ACCESS_REQUEST);
        contractRequest.setRequestedBy(userSession.getGpsUser().getEmail());
        if (contractRequest.getRequestId() != null && contractRequest.getFormAction().equalsIgnoreCase("submit")) {
            contractRequest.setFormAction("resubmit");
        }
        supportValidator.validate(contractRequest, result);
        if (result.hasErrors()) {
            model.put("referenceData", commonUtil.buildReferenceData());
            return "accessRequest";
        }
        supportService.processContractRequest(contractRequest);
        log.debug("support accessRequest page processing completed.");
        return "redirect:/support.htm";
    }

    @RequestMapping(value = "/removeAccessRequest.htm", method = RequestMethod.GET)
    public String removeAccessRequest(@RequestParam(value = "requestId", required = false) Long requestId,
                                      @RequestParam(value = "title", required = false) String title,
                                      @RequestParam(value = "action", required = false) String action,
                                      @ModelAttribute ContractRequest contractRequest, Map<Object, Object> model, HttpServletRequest request) {
        log.debug("loading support accessRequest................" + requestId);
        Contract contract = null;
        if (title != null && !title.equalsIgnoreCase("All")) {
            contract = contractService.getContractByTitle(title);
            contractRequest.setContractName(title);
        } else {
            contractRequest.setContractName("All");
        }
        if (requestId != null) {
            contractRequest = supportService.getContractRequest(requestId);
            if (contractRequest.getContractName() != null && !contractRequest.getContractName().isEmpty()) {
                contract = contractService.getContractByTitle(contractRequest.getContractName());
            }
        }
        model.put("action", action);
        model.put("contractRequest", contractRequest);
        model.put("contract", contract);
        model.put("referenceData", commonUtil.buildReferenceData());
        log.debug("support singleAclRequest page processing completed.");
        return "removeAccessRequest";
    }


    @RequestMapping(value = "/removeAccessRequest.htm", method = RequestMethod.POST)
    public String saveRemoveAccessRequest(@ModelAttribute ContractRequest contractRequest, BindingResult result, Map<Object, Object> model, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        log.debug("processing accessRequest................");
        if (contractRequest.getFormAction() != null && contractRequest.getFormAction().equalsIgnoreCase("back")) {
            return "redirect:/support.htm";
        }
        contractRequest.setRequestType(Constant.REMOVE_ACCESS_REQUEST);
        contractRequest.setRequestedBy(userSession.getGpsUser().getEmail());
        if (contractRequest.getRequestId() != null && contractRequest.getFormAction().equalsIgnoreCase("submit")) {
            contractRequest.setFormAction("resubmit");
        }
        supportValidator.validate(contractRequest, result);
        if (result.hasErrors()) {
            return "removeAccessRequest";
        }
        supportService.processContractRequest(contractRequest);
        log.debug("support accessRequest page processing completed.");
        return "redirect:/support.htm";
    }

    @RequestMapping(value = "/cancelRequest.htm", method = RequestMethod.GET)
    public String cancelRequest(@RequestParam(value = "requestId") Long requestId, Map<Object, Object> model, HttpServletRequest request) {
        log.debug("cancel request................");
        if (requestId != null) {
            supportService.cancelContractRequest(requestId);
        }
        log.debug("cancel request processing completed.");
        return "redirect:/support.htm";
    }

    @RequestMapping(value = "/markArchive.htm", method = RequestMethod.GET)
    public String markArchive(@RequestParam(value = "requestId") Long requestId, Map<Object, Object> model, HttpServletRequest request) {
        log.debug("put in archive................");
        if (requestId != null) {
            supportService.markArchive(requestId);
        }
        log.debug("put in archive completed.");
        return "redirect:/support.htm";
    }

    @RequestMapping(value = "/modifyContract.htm", method = RequestMethod.GET)
    public String modifyContract(@RequestParam(value = "requestId", required = false) Long requestId,
                                 @RequestParam(value = "title", required = false) String title, @RequestParam(value = "action", required = false) String action,
                                 @ModelAttribute ContractRequest contractRequest, Map<Object, Object> model, HttpServletRequest request) {
        log.debug("loading support accessRequest................" + requestId);
        Contract contract = null;
        if (title != null) {
            contract = contractService.getContractByTitle(title);
            contractRequest.setContractName(title);
            contractRequest.setNewContractName(title);
            contractRequest.setContractId(contract.getContractId());
        }
        if (requestId != null) {
            contractRequest = supportService.getContractRequest(requestId);
            if (contractRequest.getContractName() != null && !contractRequest.getContractName().isEmpty()) {
                contract = contractService.getContractByTitle(contractRequest.getContractName());
            }
        }
        model.put("action", action);
        model.put("contractRequest", contractRequest);
        model.put("contract", contract);
        model.put("referenceData", commonUtil.buildReferenceData());
        log.debug("support modifyContract page processing completed.");
        return "modifyContract";
    }

    @RequestMapping(value = "/modifyContract.htm", method = RequestMethod.POST)
    public String saveModifyContract(@ModelAttribute ContractRequest contractRequest, BindingResult result, Map<Object, Object> model, HttpServletRequest request, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        log.debug("processing newContract................");
        if (contractRequest.getFormAction() != null && contractRequest.getFormAction().equalsIgnoreCase("back")) {
            return "redirect:/support.htm";
        }
        contractRequest.setRequestType(Constant.MOD_CONTRACT_REQUEST);
        contractRequest.setRequestedBy(userSession.getGpsUser().getEmail());
        supportValidator.validate(contractRequest, result);
        if (result.hasErrors()) {
            return "modifyContract";
        }
        supportService.processContractRequest(contractRequest);
        log.debug("support newContract page processing completed.");
        return "redirect:/support.htm";
    }


    @RequestMapping(value = "/newContract.htm", method = RequestMethod.GET)
    public String newContract(@RequestParam(value = "requestId", required = false) Long requestId, @RequestParam(value = "action", required = false) String action,
                              @ModelAttribute ContractRequest contractRequest, Map<Object, Object> model, HttpServletRequest request) {
        log.debug("loading support newContract................");
        if (requestId != null) {
            contractRequest = supportService.getContractRequest(requestId);
        }
        model.put("action", action);
        model.put("contractRequest", contractRequest);
        model.put("referenceData", commonUtil.buildReferenceData());
        log.debug("support newContract page processing completed.");
        return "newContract";
    }

    @RequestMapping(value = "/newContract.htm", method = RequestMethod.POST)
    public String saveNewContract(@ModelAttribute ContractRequest contractRequest, BindingResult result, Map<Object, Object> model, HttpServletRequest request, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        log.debug("processing newContract................");
        if (contractRequest.getFormAction() != null && contractRequest.getFormAction().equalsIgnoreCase("back")) {
            return "redirect:/support.htm";
        }
        contractRequest.setRequestType(Constant.NEW_CONTRACT_REQUEST);
        contractRequest.setRequestedBy(userSession.getGpsUser().getEmail());
        supportValidator.validate(contractRequest, result);
        if (result.hasErrors()) {
            return "newContract";
        }
        supportService.processContractRequest(contractRequest);
        log.debug("support newContract page processing completed.");
        return "redirect:/support.htm";
    }

    @RequestMapping(value = "/support.htm", method = RequestMethod.GET)
    public String showSupport(@ModelAttribute UserSession userSession, Map<Object, Object> model, HttpServletRequest request) {
        log.debug("loading support listing for user ..............." + userSession.getGpsUser().getEmail());
        List<ContractRequest> awaiting = supportService.listAwaitingRequests(userSession.getGpsUser().getEmail());

        if ((CollectionUtils.isNotEmpty(userSession.getRoles()) && CommonUtil.isAdmin(userSession.getRoles())) || userSession.getGpsUser().getEmail().equalsIgnoreCase(ConstantDataManager.APPLICATION_OWNER)) {
            List<ContractRequest> requiringAction = supportService.listRequiringAction();
            model.put("requiringAction", requiringAction);
        }

        model.put("accessLevel", userSession.getAccessLevel());
        model.put("accessLevelTypes", userSession.getAccessLevelTypes());
        model.put("awaiting", awaiting);
        model.put("userSession", userSession);
        log.debug("support listing page processing completed.");
        return "support";
    }

    @RequestMapping(value = "/archive.htm", method = RequestMethod.GET)
    public String showArchive(@ModelAttribute ArchiveSearchFilter searchFilter, Map<Object, Object> model, HttpServletRequest request, @ModelAttribute UserSession userSession) {
        log.debug("loading support archive................");
        List<String> submitters = null;
        List<String> approvers = null;
        if ((CollectionUtils.isNotEmpty(userSession.getRoles()) && CommonUtil.isAdmin(userSession.getRoles())) || userSession.getGpsUser().getEmail().equalsIgnoreCase(ConstantDataManager.APPLICATION_OWNER)) {
            submitters = supportService.listSubmitters();
            approvers = new ArrayList<String>();
            // approvers.add(ConstantDataManager.APPLICATION_OWNER);
        }
        model.put("submitters", submitters);
        model.put("approvers", approvers);
        model.put("searchFilter", searchFilter);
        model.put("accessLevel", userSession.getAccessLevel());
        log.debug("support archive page processing completed.");
        return "archive";
    }

    @RequestMapping(value = "/archive.htm", method = RequestMethod.POST)
    public String searchArchive(@ModelAttribute ArchiveSearchFilter searchFilter, Map<Object, Object> model, HttpServletRequest request, @ModelAttribute UserSession userSession) {
        log.debug("processing support filter request................");
        String userEmail = null;
        if (!(CollectionUtils.isNotEmpty(userSession.getRoles()) && CommonUtil.isAdmin(userSession.getRoles())) && !userSession.getGpsUser().getEmail().equalsIgnoreCase(ConstantDataManager.APPLICATION_OWNER)) {
            userEmail = userSession.getGpsUser().getEmail();
        }
        List<ContractRequest> archiveList = supportService.listArchive(searchFilter, userEmail);
        model.put("archiveList", archiveList);
        log.debug("support archive page processing completed.");
        return "archiveList";
    }

    private ContractRequest getContractRequest() {
        return new ContractRequest();
    }

}
