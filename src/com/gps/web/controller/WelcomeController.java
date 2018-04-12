package com.gps.web.controller;

import com.gps.service.ClientService;
import com.gps.service.SessionACLService;
import com.gps.service.UserRoleService;
import com.gps.util.*;
import com.gps.vo.SessionAcl;
import com.gps.vo.UserRole;
import com.gps.vo.helper.Constant;
import com.gps.vo.helper.UserVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;


@Controller
@SessionAttributes({"userSession"})
public class WelcomeController {
    private static Logger log = Logger.getLogger(WelcomeController.class);

    @Autowired
    ClientService clientService;

    @Autowired
    MessageSource ms;

    @Autowired
    SessionACLService sessionACLService;

    @Autowired
    UserRoleService userRoleService;

    @RequestMapping(value = "/welcome.do", method = RequestMethod.GET)
    public String showForm(Map<Object, Object> model) {
        UserVo userVo = new UserVo();
        userVo.setOtpCheck("N");
        model.put("userVo", userVo);
        return "welcome";
    }

    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    public String processLogin(@Valid UserVo userVo, BindingResult result, Map<Object, Object> model, ModelMap modelMap, HttpSession session) {
        String navigate = "login";
        if (result.hasErrors()) {
            return navigate;
        }
        sessionACLService.dropSessionAcl(session.getId());
        UserSession userSession = null;
        userVo = (UserVo) model.get("userVo");
        userVo.setApplicationId(1L);

        DateMigratorWrapper dateMigratorWrapper = new DateMigratorWrapper();
        dateMigratorWrapper.setSiteId(1L);
        dateMigratorWrapper.setSiteCode("100");
        dateMigratorWrapper.setSessionId(session.getId());
        dateMigratorWrapper.setUserVo(userVo);

        dateMigratorWrapper = clientService.loginUser(dateMigratorWrapper);
        // allowing all user to login


        userSession = new UserSession();
        userSession.setGpsUser(dateMigratorWrapper.getGpsUser());
        userSession.setAccessLevel(dateMigratorWrapper.getMaxLevel());
        userSession.setAccessLevelTypes(getAccessLevelTypes(dateMigratorWrapper));
        List<UserRole> roles = userRoleService.getUserRolesByUserId(userSession.getGpsUser().getUserId());
        userSession.setRoles(roles);
        userSession.setUserRoles(loadUserRolesInString(roles));
        userSession.setIsAdmin(CommonUtil.checkIfUserIsAdmin(roles));


        // session.setAttribute("userSession",userSession);
        modelMap.addAttribute("userSession", userSession);
        model.put("userVo", userVo);
        navigate = "redirect:/support.htm";
        if (!dateMigratorWrapper.isHasError()) {
            if ((CollectionUtils.isNotEmpty(roles)) && !isReportAdminOnly(userSession.getUserRoles())) {
                navigate = "redirect:/rcas.htm";
            }else if((CollectionUtils.isNotEmpty(roles)) && isReportAdminOnly(userSession.getUserRoles())) {
                navigate = "redirect:/reports.htm";
            }
        }
        log.debug("Droping user to: " + navigate);
        return navigate;
    }


    private boolean isReportAdminOnly(List<String> userRoles){
        if(userRoles.contains("reportAdmin") && !userRoles.contains("dpe") && !userRoles.contains("coordinator")
                && !userRoles.contains("delegate") && !userRoles.contains("editor") && !userRoles.contains("creator")
                && !userRoles.contains("owner") && !userRoles.contains("reader") && !userRoles.contains("admin") ){
            return true;
        }
        return false;
    }

    private List<String> loadUserRolesInString(List<UserRole> roles) {
        List<String> userRoles = new ArrayList<String>();
        if (CollectionUtils.isNotEmpty(roles)){
            for(UserRole role : roles){
                if(!userRoles.contains(role.getRole())){
                    userRoles.add(role.getRole());
                }
            }
         }
        return userRoles;
    }


    private String formAccessType(DateMigratorWrapper dateMigratorWrapper) {
        String formAccessType = null;
        for (SessionAcl acl : dateMigratorWrapper.getListSessionACL()) {
            if (acl.getFormType().equalsIgnoreCase("profile")) {
                formAccessType = "profile";
                break;
            }
            if (acl.getFormType().equalsIgnoreCase("rca")) {
                formAccessType = "rca";
                break;
            }
        }
        return formAccessType;
    }

    @RequestMapping(value = "/logout.htm", method = RequestMethod.GET)
    public String processLogout(Map<Object, Object> model, ModelMap modelMap, HttpSession session) {
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        if (userSession != null) {
            log.warn("Logout request received by user " + userSession.getGpsUser().getEmail());
        }
        session.removeAttribute("userSession");
        String sessionId = session.getId();
        sessionACLService.dropSessionAcl(sessionId);
        session.invalidate();
        return "redirect:/welcome.do";
    }

    @RequestMapping(value = "/validateUser.do", method = RequestMethod.POST)
    public
    @ResponseBody
    String validateUser(@ModelAttribute UserVo userVo, BindingResult result, Map<Object, Object> model, ModelMap modelMap, HttpSession session) {
        String response = ms.getMessage(Constant.MSGS_KEY_INVALID_USER, null, Locale.US);
        if (StringUtils.isNotBlank(userVo.getUserName())) {
            userVo.setUserName(StringUtils.toLowerCase(userVo.getUserName()));
        }
        String intranetId = userVo.getUserName();
        String password = userVo.getPassword();
        Calendar now = Calendar.getInstance();
        
        log.debug("intranetId: " + intranetId);
        if (StringUtils.isNullOrEmpty(intranetId)) {
            return response;
        }
        if (StringUtils.isNullOrEmpty(password)) {
            return response;
        }

        Boolean verified = clientService.verifyPassword(intranetId, password);
        
        if (verified) {
        	log.debug("OTP Check = "+userVo.getOtpCheck());
        	if(Constant.YES.equals(userVo.getOtpCheck())){
        		log.debug("varify OTP..");
        		String otpCode = (String) session.getAttribute(Constant.OTP_CODE);
        		Calendar otpTime = (Calendar) session.getAttribute(Constant.OTP_TIME);
        		// Add two mins.
        		otpTime.set(Calendar.MINUTE, otpTime.get(Calendar.MINUTE)+2);
        		
        		response = ms.getMessage(Constant.MSGS_KEY_INVALID_OTP, null, Locale.US);
        		if(now.before(otpTime)) {
	        		if (!StringUtils.isNullOrEmpty(userVo.getOtp())) {
	        			 log.debug(userVo.getOtp()+" == "+otpCode);
	        			 if(userVo.getOtp().equals(otpCode)){
	             			response = Constant.SUCCESS;
	             		}
	        	     }
        		}
        	} else {
        		String role = "";
        		try{
        			role = clientService.getUserRole(intranetId);
        		} catch (Exception e){
        			log.error(e.getMessage(), e);
        		}
            	if(Constant.ROLE_ADMIN.equals(role)){
            		clientService.genOTPassword(userVo, session);
            		response = Constant.ADMIN_SUCCESS;
            	} else {
            		response = Constant.SUCCESS;
            	}
        	}
        }
        log.debug("response = "+response);
        return response;
    }

    private String getAccessLevelTypes(DateMigratorWrapper dateMigratorWrapper) {
        StringBuilder accessLevelTypes = new StringBuilder();
        int i = 1;
        if (CollectionUtils.isNotEmpty(dateMigratorWrapper.getListSessionACL())) {
            List<SessionAcl> aclList = dateMigratorWrapper.getListSessionACL();
            for (SessionAcl acl : aclList) {
                if (acl.getFormType().equalsIgnoreCase(ConstantDataManager.PROFILE_FORM_TYPE) && (!org.apache.commons.lang.StringUtils.contains(accessLevelTypes.toString(), ConstantDataManager.PROFILE_FORM_TYPE))) {
                    accessLevelTypes.append(ConstantDataManager.PROFILE_FORM_TYPE);
                }
                if (acl.getFormType().equalsIgnoreCase(ConstantDataManager.RCA_FORM_TYPE) && (!org.apache.commons.lang.StringUtils.contains(accessLevelTypes.toString(), ConstantDataManager.RCA_FORM_TYPE))) {
                    accessLevelTypes.append(ConstantDataManager.RCA_FORM_TYPE);
                }

                if (i < aclList.size()) {
                    accessLevelTypes.append(",");
                }
                i++;
            }
        }
        return accessLevelTypes.toString();
    }

}