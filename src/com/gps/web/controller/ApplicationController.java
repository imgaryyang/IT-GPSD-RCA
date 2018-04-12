package com.gps.web.controller;

import com.gps.service.ClientService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class ApplicationController {
	private static final Log logger = LogFactory
			.getLog(ApplicationController.class);
	@Autowired
	ClientService clientCommonService;

	@RequestMapping(value = "/data_migration.htm", method = RequestMethod.GET)
	public String showDataMigrationForm(Map<Object, Object> model) {

		return "data_migration";
	}

	/*@RequestMapping(value = "/data_migration.htm", method = RequestMethod.POST)
	public String processForm(
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "fromHour", required = false) String fromHour,
			@RequestParam(value = "toHour", required = false) String toHour,
			@RequestParam(value = "fromMin", required = false) String fromMin,
			@RequestParam(value = "toMin", required = false) String toMin,
			@RequestParam(value = "tableList", required = false) String[] tableList,
			Map model) {*/
	@RequestMapping(value = "/data_migration.htm", method = RequestMethod.POST)
	public String processForm(
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "fromHour", required = false) String fromHour,
			@RequestParam(value = "toHour", required = false) String toHour,
			@RequestParam(value = "fromMin", required = false) String fromMin,
			@RequestParam(value = "toMin", required = false) String toMin,
			@RequestParam(value = "Lhin", required = false) String Lhine,
			Map model) {

	/*	System.out.println("DataMigrationController.processForm()Lhine" + Lhine);
		if(FieldsUtil.notEmptyAndNull(Lhine) && FieldsUtil.notEmptyAndNull(Lhine.trim())){
			if(!FieldsUtil.isNotEmptyValidInteger(Lhine.trim())){
				model.put("msg", "LHine number is not valid");
				return "data_migration";
			}
			
		}

		if ("00-NaM-0000".equalsIgnoreCase(toDate)) {
			toDate = "";
		}
		if ("00-NaM-0000".equalsIgnoreCase(fromDate)) {
			fromDate = "";
		}
		if (!FieldsUtil.notEmptyAndNull(fromHour)) {
			fromHour = "00";
		}
		if (!FieldsUtil.notEmptyAndNull(toHour)) {
			toHour = "00";
		}

		if (!FieldsUtil.notEmptyAndNull(fromMin)) {
			fromMin = "00";
		}
		if (!FieldsUtil.notEmptyAndNull(toMin)) {
			toMin = "00";
		}
		log.info("fromDate::::" + fromDate);
		log.info("toDate::::" + toDate);
		log.info("toHour::::" + toHour);
		log.info("toMin::::" + toMin);
		log.info("fromHour::::" + fromHour);
		log.info("fromMin::::" + fromMin);
		MigrationHelper migrationHelper = new MigrationHelper();
		migrationHelper.setStartTimeString(fromDate);
		migrationHelper.setEndTimeString(toDate);

		migrationHelper.setStartHourString(fromHour);
		migrationHelper.setEndHourString(toHour);
		migrationHelper.setStartMinString(fromMin);
		migrationHelper.setEndMinString(toMin);
		migrationHelper.setLhinNumber(Lhine);
		migrationHelper = clientCommonService.migrate(migrationHelper);

		model.put("msg", "success");*/
		return "data_migration";
	}
}
