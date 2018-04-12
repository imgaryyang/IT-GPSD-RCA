
/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: ACLController.java
 *    CREATOR:Waqar Malik
 *    DEPT: GBS PAK
 *    DATE: 17/07/2013
 *
 *-PURPOSE-----------------------------------------------------------------
 * 
 *--------------------------------------------------------------------------
 *
 *
 *-CHANGE LOG--------------------------------------------------------------
 * 17/07/2013Waqar Malik Initial coding.
 *==========================================================================
 * </pre> */
package com.gps.web.controller;

import com.gps.service.ACLService;
import com.gps.service.ContractService;
import com.gps.service.GpsUserService;
import com.gps.util.CommonUtil;
import com.gps.util.StringUtils;
import com.gps.vo.AccessControlList;
import com.gps.vo.GpsUser;
import com.gps.vo.helper.ACLForm;
import com.gps.vo.helper.Constant;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * This class serves as controller for ACLs.
 *  
 * @authorWaqar Malik
 */

@Controller
@SessionAttributes({ "userSession" })
public class ACLController {
	private static Logger log = Logger.getLogger(ACLController.class);
	
	@Autowired
	ACLService aCLService;
	
	@Autowired
	CommonUtil commonUtil;
	
	@Autowired
	GpsUserService gpsUserService;
	
	@Autowired
	ContractService contractService;
	
	@RequestMapping(value = "/addACL.htm", method = RequestMethod.GET)
	public String showACL(@RequestParam("acl_id") Integer aclId, @ModelAttribute  ACLForm aCLForm, BindingResult result, Map<Object, Object> model) {
		log.debug("loading acl form ................");
		if(aclId != null){
			AccessControlList acl = aCLService.loadAcl(aclId);
			if(acl != null){
				if(acl.getContract() != null){
					aCLForm.setAcl(acl);
				}else{
					aCLForm.setGroup(acl);
				}
			}
		}
		aCLForm.setContracts( contractService.getAllContracts());
		List<GpsUser> singleContractACLs = aCLService.getSingleContractAcls();
		model.put("referenceData", commonUtil.buildReferenceData());
		model.put("emails", gpsUserService.getEmails());
		model.put("singleContractACLs", singleContractACLs);
		model.put("aCLForm", aCLForm);
		return "addACL";
	} 
	
	@RequestMapping(value = "/addSingleAcl.htm", method = RequestMethod.GET)
	public @ResponseBody String addACL(@RequestParam("acl_id") Integer aclId, @RequestParam("email") String email, @RequestParam("contractId") Integer contractId,
			@RequestParam("formType") String formType, @RequestParam("activeLevel") String activeLevel, @RequestParam("awaitingLevel") String awaitingLevel, @RequestParam("approvedLevel") String approvedLevel) {
		if(StringUtils.isNotBlank(email)){
            email = StringUtils.toLowerCase(email);
        }
        log.debug("ACL Command received for user => "+email);
		String response = "No ACL Entry added in the system.";
		boolean outcome = aCLService.processSingleACL(aclId, email, contractId, formType, activeLevel, awaitingLevel, approvedLevel);
		if(outcome){
			response = "Successfully added ACL Entry.";
		}

		return response;
	}
	
	@RequestMapping(value = "/nonContractAcl.htm", method = RequestMethod.GET)
	public @ResponseBody String addNonContractACL(@RequestParam("acl_id") Integer aclId, @RequestParam("email") String email, @RequestParam("formType") String formType, 
			@RequestParam("activeLevel") String activeLevel, @RequestParam("awaitingLevel") String awaitingLevel,
            @RequestParam("approvedLevel") String approvedLevel) {
        if(StringUtils.isNotBlank(email)){
            email = StringUtils.toLowerCase(email);
        }
		log.debug("ACL Command received for user => "+email);
		String response = "No ACL Entry added in the system.";
		boolean outcome = aCLService.processNonContractACL(aclId, email, formType,null, activeLevel,awaitingLevel, approvedLevel);
		if(outcome){
			response = "Successfully added ACL Entry.";
		}

		return response;
	}
	
	@RequestMapping(value = "/addACL.htm", method = RequestMethod.POST)
	public @ResponseBody String addACL(@ModelAttribute  ACLForm aCLForm, BindingResult result, Map<Object, Object> model, ModelMap modelMap) {
		log.debug("ACL Command received => "+aCLForm.getCommand());
		String response = "No ACL Entry added in the system.";
		boolean outcome = aCLService.processACL(aCLForm, aCLForm.getCommand());
		if(outcome){
			response = "Successfully added ACL Entry.";
		}

		return response;
	}
	
	@RequestMapping(value = "/modifyAcl.htm", method = RequestMethod.GET)
	public String showModifyACL(@RequestParam("user_id") Integer userId, @ModelAttribute  ACLForm aCLForm, BindingResult result, Map<Object, Object> model) {
		log.debug("loading modify form ................"+userId);
		List<AccessControlList> singleContractACLs = aCLService.getAclsByUserId(userId);
		model.put("referenceData", commonUtil.buildReferenceData());
		model.put("singleContractACLs", singleContractACLs);
		model.put("aCLForm", aCLForm);
//		model.put("accessMap", CommonUtil.getAcessLevelMap());
		log.debug("showModifyACL() response: modifyAcl");
		return "modifyAcl";
	} 
	
	@RequestMapping(value = "/editAcl.htm", method = RequestMethod.GET)
	public String editACL(@RequestParam("acl_id") Integer aclId, @ModelAttribute  ACLForm aCLForm, BindingResult result, Map<Object, Object> model) {
		log.debug("loading acl form ................aclId="+aclId);
		String navigation = "editSingleAcl";
		if(aclId != null){
			AccessControlList acl = aCLService.loadAcl(aclId);
				if(acl.getContract() != null){
					aCLForm.setAcl(acl);
					model.put("acl", acl);
				}else{
					aCLForm.setGroup(acl);
					model.put("group", acl);
					navigation = "editNonContractAcl";
				}
		}
		aCLForm.setContracts( contractService.getAllContracts());
		model.put("referenceData", commonUtil.buildReferenceData());
		model.put("aCLForm", aCLForm);
		return navigation;
	} 
	
	@RequestMapping(value = "/dropAcl.htm", method = RequestMethod.GET)
	public @ResponseBody String dropACL(@RequestParam("acl_id") Integer aclId) {
		log.debug("drop acl request received aclId = "+aclId);
		String navigation = Constant.FAIL;
		if(aclId != null){
			Boolean droped = aCLService.deleteAcl(aclId);
			if(droped.booleanValue()){
				navigation = Constant.SUCCESS;
			}
		}
		return navigation;
	} 
	
	@RequestMapping(value = "/editGroup.htm", method = RequestMethod.GET)
	public String editGroup(@RequestParam("gu_id") Integer guId, @ModelAttribute  ACLForm aCLForm, BindingResult result, Map<Object, Object> model) {
		log.debug("loading group form ................");
		String navigation = "editGroup";
		if(guId != null){

			AccessControlList acl = aCLService.loadAcl(guId);
					aCLForm.setAcl(acl);
					model.put("acl", acl);
		}
		aCLForm.setContracts( contractService.getAllContracts());
		model.put("referenceData", commonUtil.buildReferenceData());
		model.put("aCLForm", aCLForm);
		return navigation;
	} 
	
}
