package com.gps.service.impl;

import com.gps.dao.*;
import com.gps.exceptions.GPSException;
import com.gps.service.GpsUserService;
import com.gps.service.RcaNotificationService;
import com.gps.util.BluePages;
import com.gps.util.CommonUtil;
import com.gps.util.ConstantDataManager;
import com.gps.util.GPSMailer;
import com.gps.vo.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Waqar Malik on 23-12-2014.
 */
@Service
public class RcaNotificationServiceImpl implements RcaNotificationService {
    public static final String SEPARATOR = "_";
    public static final String SEND_TO_OWNER_AND_DELEGATE = "sendToOwnerAndDelegate";
    public static final String SEND_TO_OWNER_ONLY = "sendToOwnerOnly";
    public static final String SEND_TO_DELEGATE_ONLY = "sendToDelegateOnly";
    private static Logger log = Logger.getLogger(RcaNotificationServiceImpl.class);


    @Autowired
    EmailTemplateDao emailTemplateDao;

    @Autowired
    GPSMailer gpsMailer;

    @Autowired
    GpsUserService gpsUserService;

    @Autowired
    RcaNotificationAuditDao rcaNotificationAuditDao;

    @Autowired
    RcaEditorDao rcaEditorDao;

    @Autowired
    RcaCoordinatorDao rcaCoordinatorDao;

    @Autowired
    private RcaEmailNotificationSettingDao rcaEmailNotificationSettingsDao;

    @Autowired
    EmailReminderDao emailReminderDao;

    @Value(value = "#{'${initiate.rca.notification}'}")
    private String initiateRcaNotification;


    @Override
    public boolean saveOrUpdateNotificationAuditDetails(RcaNotificationAudit rcaNotificationAudit) {
        try {
            rcaNotificationAuditDao.saveOrUpdateRcaNotificationAuditDetails(rcaNotificationAudit);
            return true;

        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public RcaNotificationAudit getRcaNotificationAudit(Integer rcaId) {
        return rcaNotificationAuditDao.getNotificationAuditByRcaId(rcaId);
    }

    @Override
    public void sendRcaInitiatedNotification(Rca rca) throws MessagingException {
        Boolean isNotification = Boolean.parseBoolean(initiateRcaNotification);
        if (!isNotification) {
            log.info("notification is disabled.");
            return;
        }
        log.info("notification is enabled.");
        EmailTemplate emailTemplate = emailTemplateDao.getEmailTemplateByName("RCA_INITIATED");
        Hashtable<String, String> keys = getValueMap(rca);
        if (emailTemplate != null) {
            String to = rca.getRcaCoordinatorId();
            String body = com.gps.util.StringUtils.replaceAll(emailTemplate.getBody(), keys);
            String subject = com.gps.util.StringUtils.replaceAll(emailTemplate.getSubject(), keys);
            String cc = getToEmail(emailTemplate.getCcEmail(), rca);
            String bcc = getToEmail(emailTemplate.getBccEmail(), rca);

            log.trace("To: " + emailTemplate.getToEmail() + " => " + to);
            try {
                List<String> recipients = Arrays.asList(to);
                List<String> ccList = Arrays.asList(cc);
                gpsMailer.sendEmail(emailTemplate.getFromAlias(), subject, body, recipients, ccList);

            } catch (GPSException e) {
                log.info(e.getMessage(), e);
                log.trace("To: " + to + ", cc: " + cc + ", bcc: " + bcc);
            }
        }
        log.trace("notifaction sent to users.");
    }

    @Override
    @Transactional
    public void sendOwnerAndDelegateNotification(Rca rca, RcaEmailNotificationSetting emailNotificationSetting) {
        try {

            RcaNotificationAudit rcaNotificationAudit = getRcaNotificationAudit(rca.getRcaId());
            String rcaOwner = rca.getRcaOwner();
            String rcaDelegate = rca.getRcaDelegate();
            String isOwnerAccepted = rca.getIsOwnerAccepted().trim();
            String isDelegateAccepted = rca.getIsDelegateAccepted().trim();

            if (rcaNotificationAudit != null && (rcaNotificationAudit.getIsOwnerNotificationSent().equals("N") || rcaNotificationAudit.getIsDelegateNotificationSent().equals("N"))) {

                if (StringUtils.isNotBlank(rcaOwner) && isOwnerAccepted.equalsIgnoreCase("N") && StringUtils.isNotBlank(rcaDelegate) && isDelegateAccepted.equalsIgnoreCase("N")) {

                    //send email notification
                    sendRcaAssignedEmailNotification(rca, SEND_TO_OWNER_AND_DELEGATE);

                    //update notification audit
                    rcaNotificationAudit.setIsDelegateNotificationSent("Y");
                    rcaNotificationAudit.setIsOwnerNotificationSent("Y");
                    rcaNotificationAudit.setOwnerNotificationSentDate(new Timestamp(new Date().getTime()));
                    rcaNotificationAudit.setDelegateNotificationSentDate(new Timestamp(new Date().getTime()));
                    rcaNotificationAuditDao.saveOrUpdateRcaNotificationAuditDetails(rcaNotificationAudit);

                    //Add Email Reminders for RCA Not Accepted By Owner
                    addRcaNotAcceptedReminder(rca, emailNotificationSetting, rcaNotificationAudit);


                } else if ((StringUtils.isNotBlank(rcaOwner) && isOwnerAccepted.equalsIgnoreCase("N")) && (StringUtils.isBlank(rcaDelegate) || isDelegateAccepted.equalsIgnoreCase("Y"))) {

                    //send email notification
                    sendRcaAssignedEmailNotification(rca, SEND_TO_OWNER_ONLY);

                    //update notification audit
                    rcaNotificationAudit.setIsOwnerNotificationSent("Y");
                    rcaNotificationAudit.setOwnerNotificationSentDate(new Timestamp(new Date().getTime()));
                    rcaNotificationAuditDao.saveOrUpdateRcaNotificationAuditDetails(rcaNotificationAudit);

                    //Add Email Reminders for RCA Not Accepted By Owner
                    addRcaNotAcceptedReminder(rca, emailNotificationSetting, rcaNotificationAudit);


                } else if ((StringUtils.isBlank(rcaOwner) || isOwnerAccepted.equalsIgnoreCase("Y")) && (StringUtils.isNotBlank(rcaDelegate) && isDelegateAccepted.equalsIgnoreCase("N"))) {

                    //send email notification
                    sendRcaAssignedEmailNotification(rca, SEND_TO_DELEGATE_ONLY);

                    //update notification audit
                    rcaNotificationAudit.setIsDelegateNotificationSent("Y");
                    rcaNotificationAudit.setDelegateNotificationSentDate(new Timestamp(new Date().getTime()));
                    rcaNotificationAuditDao.saveOrUpdateRcaNotificationAuditDetails(rcaNotificationAudit);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    private void addRcaNotAcceptedReminder(Rca rca, RcaEmailNotificationSetting emailNotificationSetting, RcaNotificationAudit rcaNotificationAudit) {
        EmailReminder emailReminder = emailReminderDao.findByQuestionnaireIdAndReminderType(rca.getRcaId(), ConstantDataManager.RCA_NOT_ACCEPTED_BY_OWNER_REMINDER);
        if (emailReminder == null) {
            emailReminder = new EmailReminder();
            emailReminder.setQuestionnaireId(rca.getRcaId().toString());
            emailReminder.setQuestionnaireType(ConstantDataManager.RCA_FORM_TYPE);
            if (StringUtils.isNotBlank(rca.getRcaOwner()) && StringUtils.isNotBlank(rca.getRcaDelegate())) {
                emailReminder.setToEmail(rca.getRcaOwner() + "," + rca.getRcaDelegate());
            } else if (StringUtils.isNotBlank(rca.getRcaOwner()) && StringUtils.isBlank(rca.getRcaDelegate())) {
                emailReminder.setToEmail(rca.getRcaOwner());
            } else if (StringUtils.isBlank(rca.getRcaOwner()) && StringUtils.isNotBlank(rca.getRcaDelegate())) {
                emailReminder.setToEmail(rca.getRcaDelegate());
            }
            emailReminder.setCcEmail(rca.getRcaCoordinatorId());
            Date notificationSentDate = new Date(rcaNotificationAudit.getOwnerNotificationSentDate().getTime());
            Date reminderStartDate = CommonUtil.addDays(notificationSentDate, emailNotificationSetting.getRcaNotAcceptedReminderForOwnerDays());
           // Date reminderEndDate = CommonUtil.addWorkingDays(reminderStartDate, emailNotificationSetting.getRcaNotClosedReminderDuration());
            emailReminder.setStartDate(reminderStartDate);
          //  emailReminder.setEndDate(reminderEndDate);
            emailReminder.setReminderType(ConstantDataManager.RCA_NOT_ACCEPTED_BY_OWNER_REMINDER);
            emailReminder.setIsEnabled(ConstantDataManager.YES);
            emailReminderDao.addEmailReminder(emailReminder);
        }
    }

    @Override
    public void sendCoordinatorApprovalRequestNotification(Rca rca) {
        try {
            log.info("notification is enabled.");
            EmailTemplate emailTemplate = emailTemplateDao.getEmailTemplateByName("COORDINATOR_APPROVAL_REQUEST");
            Hashtable<String, String> keys = getValueMap(rca);
            if (emailTemplate != null) {
                String to =  rca.getRcaCoordinatorId();
                String body = com.gps.util.StringUtils.replaceAll(emailTemplate.getBody(), keys);
                String subject = com.gps.util.StringUtils.replaceAll(emailTemplate.getSubject(), keys);
                String cc = getToEmail(emailTemplate.getCcEmail(), rca);
                String bcc = getToEmail(emailTemplate.getBccEmail(), rca);
                List<String> recipients = Arrays.asList(to);
                List<String> ccList = Arrays.asList(cc.split(","));
                gpsMailer.sendEmail(emailTemplate.getFromAlias(), subject, body, recipients, ccList);
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void sendRcaApprovedNotification(Rca rca) {
        try {
            log.info("notification is enabled.");
            EmailTemplate emailTemplate = emailTemplateDao.getEmailTemplateByName("RCA_APPROVED");
            Hashtable<String, String> keys = getValueMap(rca);
            if (emailTemplate != null) {
                String to = rca.getRcaCoordinatorId();
                String body = com.gps.util.StringUtils.replaceAll(emailTemplate.getBody(), keys);
                String subject = com.gps.util.StringUtils.replaceAll(emailTemplate.getSubject(), keys);
                String cc = getToEmail(emailTemplate.getCcEmail(), rca);
                String bcc = getToEmail(emailTemplate.getBccEmail(), rca);
                List<String> recipients = Arrays.asList(to);
                List<String> ccList = Arrays.asList(cc.split(","));
                gpsMailer.sendEmail(emailTemplate.getFromAlias(), subject, body, recipients, ccList);
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    @Override
    public void sendRcaRejectedNotification(Rca rca, String rejectionComments) {
        try {
            log.info("notification is enabled.");
            EmailTemplate emailTemplate = emailTemplateDao.getEmailTemplateByName("RCA_REJECTED");
            Hashtable<String, String> keys = getValueMap(rca);
            keys.put("rcaRejectionComments", rejectionComments);
            if (emailTemplate != null) {
                String to = getToEmail(emailTemplate.getToEmail(), rca);
                String body = com.gps.util.StringUtils.replaceAll(emailTemplate.getBody(), keys);
                String subject = com.gps.util.StringUtils.replaceAll(emailTemplate.getSubject(), keys);
                String cc = getToEmail(emailTemplate.getCcEmail(), rca);
                String bcc = getToEmail(emailTemplate.getBccEmail(), rca);
                List<String> recipients = Arrays.asList(to.split(","));
                List<String> ccList = Arrays.asList(cc.split(","));
                gpsMailer.sendEmail(emailTemplate.getFromAlias(), subject, body, recipients, ccList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void sendRcaCancellationNotification(Rca rca, String cancellationComments) {
        try {
            log.info("notification is enabled.");
            EmailTemplate emailTemplate = emailTemplateDao.getEmailTemplateByName("RCA_CANCELLED");
            Hashtable<String, String> keys = getValueMap(rca);
            keys.put("cancellationComments", cancellationComments);
            if (emailTemplate != null) {
                String to = rca.getRcaCoordinatorId();
                String body = com.gps.util.StringUtils.replaceAll(emailTemplate.getBody(), keys);
                String subject = com.gps.util.StringUtils.replaceAll(emailTemplate.getSubject(), keys);
                String cc = getToEmail(emailTemplate.getCcEmail(), rca);
                String bcc = getToEmail(emailTemplate.getBccEmail(), rca);
                List<String> recipients = Arrays.asList(to.split(","));
                List<String> ccList = Arrays.asList(cc.split(","));
                gpsMailer.sendEmail(emailTemplate.getFromAlias(), subject, body, recipients, ccList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void sendRcaClosedNotification(Rca rca) {
        try {
            log.info("notification is enabled.");
            EmailTemplate emailTemplate = emailTemplateDao.getEmailTemplateByName("RCA_CLOSED");
            Hashtable<String, String> keys = getValueMap(rca);
            if (emailTemplate != null) {
                String to = rca.getRcaCoordinatorId();
                String body = com.gps.util.StringUtils.replaceAll(emailTemplate.getBody(), keys);
                String subject = com.gps.util.StringUtils.replaceAll(emailTemplate.getSubject(), keys);
                String cc = getToEmail(emailTemplate.getCcEmail(), rca);
                String bcc = getToEmail(emailTemplate.getBccEmail(), rca);
                List<String> recipients = Arrays.asList(to.split(","));
                List<String> ccList = Arrays.asList(cc.split(","));
                gpsMailer.sendEmail(emailTemplate.getFromAlias(), subject, body, recipients, ccList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void sendRcaReOpenedNotification(Rca rca, String reOpenedComments) {
        try {
            log.info("notification is enabled.");
            EmailTemplate emailTemplate = emailTemplateDao.getEmailTemplateByName("RCA_REOPENED");
            Hashtable<String, String> keys = getValueMap(rca);
            keys.put("reOpenedComments", reOpenedComments);
            if (emailTemplate != null) {
                String to =  getToEmail(emailTemplate.getToEmail(), rca);
                String body = com.gps.util.StringUtils.replaceAll(emailTemplate.getBody(), keys);
                String subject = com.gps.util.StringUtils.replaceAll(emailTemplate.getSubject(), keys);
                String cc = getToEmail(emailTemplate.getCcEmail(), rca);
                String bcc = getToEmail(emailTemplate.getBccEmail(), rca);
                List<String> recipients = Arrays.asList(to.split(","));
                List<String> ccList = Arrays.asList(cc.split(","));
                gpsMailer.sendEmail(emailTemplate.getFromAlias(), subject, body, recipients, ccList);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void sendDpeApprovalRequestNotification(Rca rca) {
        try {
            log.info("notification is enabled.");
            EmailTemplate emailTemplate = emailTemplateDao.getEmailTemplateByName("DPE_APPROVAL_REQUEST");
            Hashtable<String, String> keys = getValueMap(rca);
            if (emailTemplate != null) {
                String to = getToEmail(emailTemplate.getToEmail(), rca);
                String body = com.gps.util.StringUtils.replaceAll(emailTemplate.getBody(), keys);
                String subject = com.gps.util.StringUtils.replaceAll(emailTemplate.getSubject(), keys);
                String cc = getToEmail(emailTemplate.getCcEmail(), rca);
                String bcc = getToEmail(emailTemplate.getBccEmail(), rca);
                List<String> recipients = Arrays.asList(to);
                List<String> ccList = Arrays.asList(cc.split(","));
                gpsMailer.sendEmail(emailTemplate.getFromAlias(), subject, body, recipients, ccList);
            }

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    private void sendRcaAssignedEmailNotification(Rca rca, String toListType) throws MessagingException {
        try {

            log.info("notification is enabled.");
            EmailTemplate emailTemplate = emailTemplateDao.getEmailTemplateByName("RCA_ASSIGNED");
            Hashtable<String, String> keys = getValueMap(rca);
            if (emailTemplate != null) {
                String[] toList = null;
                String to = "";

                String body = com.gps.util.StringUtils.replaceAll(emailTemplate.getBody(), keys);
                String subject = com.gps.util.StringUtils.replaceAll(emailTemplate.getSubject(), keys);
                String cc = getToEmail(emailTemplate.getCcEmail(), rca);
                String bcc = getToEmail(emailTemplate.getBccEmail(), rca);
                List<String> ccList = null;
                if(StringUtils.isNotBlank(cc)) {
                    ccList = Arrays.asList(cc.split(","));
                }

                if (toListType.equals(SEND_TO_OWNER_AND_DELEGATE)) {
                    List<String> recipients = getRecipientEmail(emailTemplate.getToEmail(), rca);
                    gpsMailer.sendEmail(emailTemplate.getFromAlias(), subject, body, recipients, ccList);
                }
                if (toListType.equalsIgnoreCase(SEND_TO_OWNER_ONLY)) {
                    to = getRcaOwnerEmail(emailTemplate.getToEmail(), rca);
                    log.trace("To: " + emailTemplate.getToEmail() + " => " + to);
                    List<String> recipients = Arrays.asList(to);
                    gpsMailer.sendEmail(emailTemplate.getFromAlias(), subject, body, recipients, ccList);
                }
                if (toListType.equalsIgnoreCase(SEND_TO_DELEGATE_ONLY)) {
                    to = getRcaDelegateEmail(emailTemplate.getToEmail(), rca);
                    log.trace("To: " + emailTemplate.getToEmail() + " => " + to);
                    List<String> recipients = Arrays.asList(to);
                    gpsMailer.sendEmail(emailTemplate.getFromAlias(), subject, body, recipients, ccList);
                }

            }
        } catch (GPSException e) {
            log.info(e.getMessage(), e);
        }

        log.trace("notifaction sent to users.");
    }

    private Hashtable<String, String> getValueMap(Rca rca) {
        Hashtable<String, String> keys = new Hashtable<String, String>();
        keys.put("rcaNumber", rca.getRcaNumber());
        keys.put("contractName", rca.getContract().getTitle());
        keys.put("contractId", String.valueOf(rca.getContract().getContractId()));
        keys.put("rcaEndDate", rca.getDueDate() != null ? CommonUtil.convertDateToStringFormat(rca.getDueDate()) : "");
        // get the Rca Coordinator Name
        if (StringUtils.isNotBlank(rca.getRcaCoordinatorId())) {
           keys.put("rcaCoordinatorName", BluePages.getNotesIdFromIntranetId(rca.getRcaCoordinatorId()));
        }
        return keys;
    }

    private String getToEmail(String whom, Rca rca) {
        StringBuilder toList = new StringBuilder();
        if (StringUtils.isBlank(whom)) {
            return null;
        }
        StringTokenizer st = new StringTokenizer(whom, ",");
        if (st.countTokens() > 1) {
            while (st.hasMoreTokens()) {
                String recipientType = st.nextToken();
                if (recipientType.equalsIgnoreCase("RCA Owner")) {
                    String owner = rca.getRcaOwner();
                    if (StringUtils.isNotBlank(owner)) {
                        toList.append(owner.trim());
                        toList.append(",");
                    }
                }
                if (recipientType.equalsIgnoreCase("RCA Delegate")) {
                    String delegate = rca.getRcaDelegate();
                    if (StringUtils.isNotBlank(delegate)) {
                        toList.append(delegate.trim());
                    }
                }
                if (recipientType.equalsIgnoreCase("RCA Editors")) {
                    List<RcaEditor> rcaEditors = rcaEditorDao.getRcaEditorsByRcaId(rca.getRcaId());
                    if (CollectionUtils.isNotEmpty(rcaEditors)) {
                        for (RcaEditor rcaEditor : rcaEditors) {
                            if (toList.toString().indexOf(rcaEditor.getGpsUser().getEmail()) < 0) {
                                toList.append(",");
                                toList.append(rcaEditor.getGpsUser().getEmail().trim());
                            }
                        }
                    }
                }
                if (recipientType.equalsIgnoreCase("RCA Dpe")) {
                    try {
                        GpsUser user = gpsUserService.getUserById(rca.getRcaDpeId());
                        if (user != null) {
                            toList.append(",");
                            toList.append(user.getEmail());
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }
                if (recipientType.equalsIgnoreCase("RCA Coordinator")) {
                    try {

                        if (StringUtils.isNotBlank(rca.getRcaCoordinatorId())) {
                            toList.append(",");
                            toList.append(rca.getRcaCoordinatorId());
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }

            }
        } else {
            if (whom.equalsIgnoreCase("RCA Dpe")) {
                try {
                    GpsUser user = gpsUserService.getUserById(rca.getRcaDpeId());
                    if (user != null) {
                        toList.append(user.getEmail());
                    }
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (whom.equalsIgnoreCase("RCA Editors")) {
                List<RcaEditor> rcaEditors = rcaEditorDao.getRcaEditorsByRcaId(rca.getRcaId());
                if (CollectionUtils.isNotEmpty(rcaEditors)) {
                    for (RcaEditor rcaEditor : rcaEditors) {
                        if (toList.toString().indexOf(rcaEditor.getGpsUser().getEmail()) < 0) {
                            toList.append(",");
                            toList.append(rcaEditor.getGpsUser().getEmail().trim());
                        }
                    }
                }
            }


        }

        return toList.toString();
    }

    private String getRcaOwnerEmail(String whom, Rca rca) {
        String toList = "";
        StringTokenizer st = new StringTokenizer(whom, ",");
        if (StringUtils.isBlank(whom)) {
            toList = null;
        } else if (st.countTokens() > 0) {
            while (st.hasMoreTokens()) {
                String recipient = st.nextToken();
                if (recipient.equalsIgnoreCase("RCA Owner")) {
                    String owner = rca.getRcaOwner();
                    if (StringUtils.isNotBlank(owner)) {
                        toList = owner;
                    }
                }
            }
        }

        return toList;
    }

    private String getRcaDelegateEmail(String whom, Rca rca) {
        String toList = "";
        StringTokenizer st = new StringTokenizer(whom, ",");
        if (StringUtils.isBlank(whom)) {
            toList = null;
        } else if (st.countTokens() > 0) {
            while (st.hasMoreTokens()) {
                String recipient = st.nextToken();
                if (recipient.equalsIgnoreCase("RCA Delegate")) {
                    String owner = rca.getRcaOwner();
                    if (StringUtils.isNotBlank(owner)) {
                        toList = owner;
                    }
                }
            }
        }

        return toList;
    }

    private String[] getRecipientsEmail(String whom, Rca rca) {
        List<String> recipients = new ArrayList<String>();

        if (StringUtils.isBlank(whom)) {
            return null;
        }
        StringTokenizer st = new StringTokenizer(whom, ",");
        if (st.countTokens() > 0) {
            while (st.hasMoreTokens()) {
                String recipient = st.nextToken();
                if (recipient.equalsIgnoreCase("RCA Owner")) {
                    String owner = rca.getRcaOwner();
                    if (StringUtils.isNotBlank(owner)) {
                        recipients.add(owner);
                    }
                }
                if (recipient.equalsIgnoreCase("RCA Delegate")) {
                    String delegate = rca.getRcaDelegate();
                    if (StringUtils.isNotBlank(delegate)) {
                        recipients.add(delegate);
                    }
                }
            }
        }

        return recipients.toArray(new String[recipients.size()]);
    }

    private List<String> getRecipientEmail(String whom, Rca rca) {
        List<String> recipients = new ArrayList<String>();

        if (StringUtils.isBlank(whom)) {
            return null;
        }
        StringTokenizer st = new StringTokenizer(whom, ",");
        if (st.countTokens() > 0) {
            while (st.hasMoreTokens()) {
                String recipient = st.nextToken();
                if (recipient.equalsIgnoreCase("RCA Owner")) {
                    String owner = rca.getRcaOwner();
                    if (StringUtils.isNotBlank(owner)) {
                        recipients.add(owner);
                    }
                }
                if (recipient.equalsIgnoreCase("RCA Delegate")) {
                    String delegate = rca.getRcaDelegate();
                    if (StringUtils.isNotBlank(delegate)) {
                        recipients.add(delegate);
                    }
                }
            }
        }

        return recipients;
    }


}
