package com.gps.web.controller;

import com.gps.util.CommonUtil;
import com.gps.vo.helper.UserVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class HomeController {
	@RequestMapping(value = {"/home.htm"},method = RequestMethod.GET)
	public String showForm(Map<Object, Object> model) {
		UserVo userVo = new UserVo();
		model.put("userVo", userVo);
		return "home";
	}

	
public static void main(String[] args) {
	java.sql.Timestamp incidentStart = CommonUtil.convertToTimestamp("2016-08-03 18:10");
	java.sql.Timestamp incidentEnd = CommonUtil.convertToTimestamp("2016-07-01 23:20");
	
	if(incidentStart.after(incidentEnd)){
		System.err.println("After start.");
	}
}
}
