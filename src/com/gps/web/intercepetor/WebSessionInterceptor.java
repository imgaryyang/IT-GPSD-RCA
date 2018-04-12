
package com.gps.web.intercepetor;

import com.gps.service.ClientService;
import com.gps.util.UserSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;


/**
 * @author Hassan Hanif
 */
public class WebSessionInterceptor extends HandlerInterceptorAdapter {
    Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    ClientService clientCommonService;

    @Value(value = "#{'${application.name}'}")
    private String applicationName;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String contextPath = request.getContextPath();
        //logger.debug("applicationName: "+ applicationName);
        logger.debug("context path: " + request.getContextPath() + ", last index => " + contextPath.lastIndexOf(applicationName));
        if (!contextPath.contains("/services/delivery")) {
            contextPath = contextPath.substring(0, contextPath.lastIndexOf(applicationName));
        } else {
            contextPath = "";
        }
        logger.debug("request.getQueryString() => " + request.getQueryString());
        HttpSession session = request.getSession();
        UserSession sessionObject = (UserSession) session.getAttribute("userSession");
        if (sessionObject == null) {
            logger.debug("UserSession is null: sending redirect from WebSessionInterceptor");
            saveURL(request);
            sendRedirect(response, contextPath);
        } else {
            session.setAttribute("last_request", request.getRequestURI());
            if (session.getAttribute("query_string") != null) {
                String query_string = "" + session.getAttribute("query_string");
                session.removeAttribute("query_string");
                sendRedirect(response, query_string);
            }
        }
        return super.preHandle(request, response, handler);
    }

    private void saveURL(HttpServletRequest request) {
        //http://localhost:8080/ISCRCA/accessRequest.htm?action=sh&requestId=511
        HttpSession newSession = request.getSession(true);
        Map<String, String[]> parameters = request.getParameterMap();
        String query = "?";
        String requestURI = "";
        if (!request.getRequestURI().contains("/services/delivery")) {
            requestURI = request.getRequestURI().replace("/" + applicationName, "");
        } else {
            requestURI = requestURI = request.getRequestURI().replace("/services/delivery" +"/" + applicationName, "");
        }

        String keyValue = "";
        String value = "";
        for (String parameter : parameters.keySet()) {
            value = request.getParameter(parameter);

            keyValue = parameter + "=" + value;

            if (query != "?") {
                query = query + "&" + keyValue;
            } else {
                query = query + keyValue;
            }
        }
        query = requestURI + query;
        logger.debug("query_string => " + query);
        newSession.setAttribute("query_string", query);

    }

    private boolean sendRedirect(HttpServletResponse response, String redirectURL) throws ModelAndViewDefiningException {
        RedirectView redirectView = null;
        logger.debug("Session Not Found Redirecting to Login");
        redirectView = new RedirectView(redirectURL);
        redirectView.setContextRelative(true);
        ModelAndView modelAndView = new ModelAndView(redirectView);
        throw new ModelAndViewDefiningException(modelAndView);

    }
}
