package com.gps.web.listener;

import com.gps.service.SessionACLService;
import com.gps.util.UserSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Component
public class SessionListener implements HttpSessionListener{
	private static Logger log = Logger.getLogger(SessionListener.class);
	
	@Autowired
	SessionACLService sessionACLService;
	
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		String sessionId = event.getSession().getId();
		log.debug("going to destory session....."+sessionId);
		
//		sessionACLService.dropSessionAcl(sessionId);
		cleanSession(event);
	}
	
	private void cleanSession(HttpSessionEvent sessionEvent){
        HttpSession session = sessionEvent.getSession();
        if(sessionACLService == null){
        	ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
        	sessionACLService = (SessionACLService) applicationContext.getBean("sessionACLService");
        }
        UserSession userSession = (UserSession) session.getAttribute("userSession");
		if(userSession != null){
			long lastAction = session.getLastAccessedTime();
	        long now = System.currentTimeMillis();
	        long sleep = (now-lastAction) / 1000;
	        int timeout= session.getMaxInactiveInterval();
	        if (sleep > timeout){
	        	log.warn("Session has time out for user "+userSession.getGpsUser().getEmail());
	        }
		}
        
        sessionACLService.dropSessionAcl(session.getId());
  }

}
