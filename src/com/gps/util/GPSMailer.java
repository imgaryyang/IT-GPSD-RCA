/**<pre> 
 *==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 *==========================================================================
 *
 *    FILE: GPSMailer.java
 *    CREATOR:Waqar Malik
 *    DEPT: GBS PAK
 *    DATE: 04/10/2013
 *
 *-PURPOSE-----------------------------------------------------------------
 *
 *--------------------------------------------------------------------------
 *
 *
 *-CHANGE LOG--------------------------------------------------------------
 * 04/10/2013Waqar Malik Initial coding.
 *==========================================================================
 * </pre> */
package com.gps.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.gps.exceptions.GPSException;
import com.ibm.alert.AlertSender;
import com.ibm.alert.exception.AlertSendingFailedException;
import com.ibm.alert.vo.AlertVO;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;

/**
 * This class is used to send email messages.
 *
 * @authorWaqar Malik
 */
@Service
public class GPSMailer {
    private static Logger log = Logger.getLogger(GPSMailer.class);

    private MailSender mailSender;
    private final String DEFAULT_ENCODING = "UTF-8";
    Thread thread;

    public boolean sendAlertMail(String from, String to, String cc, String bcc, String subject, String body) throws GPSException{
		log.debug("sending alert mail message... To="+to+", subject="+subject);
		if(to == null || to.isEmpty()){
			throw new GPSException("Receipient not present.");
		}
		if(body == null || body.isEmpty()){
			throw new GPSException("Message body is empty.");
		}
		try {
			if(BluePages.getInstance().checkEmail(to)){
				log.trace("receipeint succesfully verified from bluepages.");
			}
		}catch(Exception e){
			log.warn("Exception: "+to+" couldn't be verified from blue pages.");
			log.error(e.getMessage(), e);
			throw new GPSException(e.getMessage(),e);
		}
		Boolean mailSent = false;
		AlertVO emailVO = new AlertVO();
		emailVO.setDebug(false);
		emailVO.setFrom(from);
		emailVO.setHost(Constants.SMPT_ADDRESS);
		emailVO.setSubject(subject);							
		emailVO.setBody(body);
		emailVO.setTo(new String[]{to});
		if(cc != null && !cc.isEmpty()){
			emailVO.setCc(new String[]{cc});
		}
		if(bcc != null && !bcc.isEmpty()){
			emailVO.setBcc(new String[] {bcc});
		}
		try {
			log.trace("deliver alert message to transport layer.....");
			AlertSender.sendEmail(emailVO);
			log.trace("delivered alert mail message to transport layer successfully.");
			mailSent = true;
		} catch (AlertSendingFailedException ae) {
			log.warn("AlertSendingFailedException: "+to+", subject="+subject);
			log.error(ae.getMessage(), ae);
		} catch (Exception ae) {
			log.warn("Exception: "+to+", subject="+subject);
			log.error(ae.getMessage(), ae);
		} finally {
			emailVO.resetFields();
		}
		return mailSent;
	}


    public void sendEmail(String from, String subject, String body, List<String> recipients, List<String> ccList) throws MessagingException {
    	String to = null;
    	String cc = null;
    	String bcc = null;
        try {
            Properties properties = System.getProperties();
            // Setup mail server
            properties.setProperty("mail.smtp.host", Constants.SMPT_ADDRESS);

            // Get the default Session object.
            Session session = Session.getDefaultInstance(properties);

            MimeMessage message = new MimeMessage(session);


            MimeMessageHelper helper = new MimeMessageHelper(message, DEFAULT_ENCODING);
            helper.setFrom(from);
            helper.setSubject(subject);
            helper.setText(body, true);
            helper.setPriority(1);
            // addReplyTo(helper, email.getReplyTo());
            to = addRecipients(helper, recipients);
            cc = addCarbonCopys(helper, ccList);
            // addBlindCarbonCopys(helper, email.getBlindCarbonCopys());;

            // Send message
            Transport.send(message);
            log.debug("Email sent successfully. "+subject);
        } catch (MessagingException mex) {
            log.info("Exception sending email: "+subject+", to="+to);
            log.error("MessagingException "+mex.getMessage());
            sendAlertMail(from, to, cc, bcc, subject, body);
//            log.error(mex.getMessage(), mex);
        }
    }

    private String addRecipients(MimeMessageHelper helper, List<String> recipients) throws MessagingException {
    	String to = "";
        for (String rec : recipients) {
            if (org.apache.commons.lang.StringUtils.isNotBlank(rec)) {
            	to = to+rec+", ";
//                log.info("Recipient: " + rec);
                helper.addTo(rec);
            }
        }
        return to;
    }

    private String addCarbonCopys(MimeMessageHelper helper, List<String> carbonCopys) throws MessagingException {
    	String cc = "";
        if (CollectionUtils.isNotEmpty(carbonCopys)) {
            for (String carbonCopy : carbonCopys) {
                if (org.apache.commons.lang.StringUtils.isNotBlank(carbonCopy)) {
                	cc = cc+carbonCopy+", ";
//                    log.debug("CC: " + carbonCopy);
                    helper.addCc(carbonCopy);
                }
            }
        }
        return cc;
    }


    /**
     * @return the mailSender
     */
    public MailSender getMailSender() {
        return mailSender;
    }

    /**
     * @param mailSender the mailSender to set
     */
    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

}
