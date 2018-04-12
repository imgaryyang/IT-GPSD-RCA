
/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: AdminController.java
 *    CREATOR:Waqar Malik
 *    DEPT: GBS PAK
 *    DATE: 28/01/2013
 *
 *-PURPOSE-----------------------------------------------------------------
 * 
 *--------------------------------------------------------------------------
 *
 *
 *-CHANGE LOG--------------------------------------------------------------
 * 28/01/2013Waqar Malik Initial coding.
 *==========================================================================
 * </pre> */
package com.gps.web.controller;

import com.gps.service.ContractService;
import com.gps.util.CommonUtil;
import com.gps.vo.helper.MonthlyForm;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Map;


/**
 * This class serves as controller for admin.
 *  
 * @authorWaqar Malik
 */

@Controller
@SessionAttributes({ "userSession" })
public class AdminController {
	private static Logger log = Logger.getLogger(AdminController.class);
	

	@Autowired
	ContractService contractService;

	

	@RequestMapping(value = "/admin.htm", method = RequestMethod.GET)
	public String showForm(Map<Object, Object> model) {
		log.debug("loading admin ................");
		return "admin";
	}
	
	@RequestMapping(value = "/generateMonthlyForms.htm", method = RequestMethod.GET)
	public String generateMonthlyForms(@ModelAttribute MonthlyForm monthlyForm, BindingResult result, Map<Object, Object> model) {
		log.debug("loading monthly form ................");
		Map<Integer, String> contractList = contractService.listActiveContracts();
		model.put("monthList", CommonUtil.getMonthList());
		model.put("years", CommonUtil.getYearList());
		model.put("contractList", contractList);
		model.put("monthlyForm", monthlyForm);
		return "generateMonthlyForms";
	} 
	
	
}
