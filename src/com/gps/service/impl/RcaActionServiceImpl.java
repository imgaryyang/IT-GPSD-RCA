package com.gps.service.impl;

import com.gps.dao.*;
import com.gps.service.RcaActionNotificationService;
import com.gps.service.RcaActionService;
import com.gps.service.RcaService;
import com.gps.service.RcaUtilService;
import com.gps.util.BluePages;
import com.gps.util.CommonUtil;
import com.gps.util.ConstantDataManager;
import com.gps.vo.*;
import com.gps.vo.helper.ActionHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Waqar Malik on 23-12-2014.
 */
@Service
public class RcaActionServiceImpl implements RcaActionService {
    public static final String SEPARATOR = "_";
    private static Logger log = Logger.getLogger(RcaActionServiceImpl.class);


    @Autowired
    RcaActionDao rcaActionDao;

    @Autowired
    RcaService rcaService;

    @Autowired
    RcaActionSupportingFileDao actionSupportingFileDao;

    @Autowired
    RcaActionHistoryLogDao rcaActionHistoryLogDao;

    @Autowired
    GpsUserServiceImpl gpsUserService;

    @Autowired
    RcaActionNotificationService rcaActionNotificationService;

    @Autowired
    EmailReminderDao emailReminderDao;

    @Autowired
    RcaEmailNotificationSettingDao emailNotificationSettingDao;

    @Autowired
    RcaUtilService rcaUtilService;

    @Autowired
    private RcaDao rcaDao;

    @Override
    @Transactional
    public synchronized boolean addNewRcaAction(Integer rcaId, GpsUser createdBy) {
        boolean isRcaActionAdded = false;
        int numOfActions = 0;
        try {
            Rca rca = rcaService.getRcaById(rcaId);
            if (rca != null) {
                List<RcaAction> rcaActionsFromDB = rca.getRcaActions();
                if (CollectionUtils.isNotEmpty(rcaActionsFromDB)) {
                    numOfActions = rcaActionsFromDB.size();
                }
                String generatedRcaActNum = generateRcaActionNumber(rca.getRcaNumber(), numOfActions);
                if (StringUtils.isBlank(generatedRcaActNum)) {
                    return false;
                }
                RcaAction rcaAction = new RcaAction();
                rcaAction.setRca(rca);
                rcaAction.setActionNumber(generatedRcaActNum);
                rcaAction.setActionStatus("Open");
                rcaAction.setCreatedBy(createdBy.getUserId());
                rcaAction.setUpdatedBy(createdBy.getUserId());
                rcaAction.setUpdatedOn(new Timestamp(new Date().getTime()));
                rcaAction.setAssignedOn(new Timestamp(new Date().getTime()));
                rcaAction.setCreatedOn(new Timestamp(new Date().getTime()));
                rcaActionDao.addRcaAction(rcaAction);


                // rca action history log
                RcaActionHistoryLog actionHistoryLog = new RcaActionHistoryLog();
                actionHistoryLog.setFormAction("Created");
                String roles = getLoggedInUserRole(rca, createdBy);
                actionHistoryLog.setRole(configureHistoryRole(createdBy, roles, rca));
                actionHistoryLog.setSubmittedBy(createdBy);
                actionHistoryLog.setSubmittedOn(new Timestamp(new Date().getTime()));
                actionHistoryLog.setRcaAction(rcaAction);
                rcaActionHistoryLogDao.addRcaActionHistoryLog(actionHistoryLog);


                isRcaActionAdded = true;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return isRcaActionAdded;
    }

    @Override
    public boolean deleteRcaAction(Integer rcaActionId) {
        try {
            // Deleting Rca Action History Log
            List<RcaActionHistoryLog> actionHistoryLogs = rcaActionHistoryLogDao.getRcaActionHistoryLogsByRcaActionId(rcaActionId);
            if(CollectionUtils.isNotEmpty(actionHistoryLogs)){
                for(RcaActionHistoryLog actionHistoryLog : actionHistoryLogs){
                    rcaActionHistoryLogDao.deleteRcaActionHistoryLog(actionHistoryLog);
                }
            }

            RcaAction rcaAction = rcaActionDao.getRcaActionById(rcaActionId);
            if (rcaAction != null) {
                rcaActionDao.deleteRcaAction(rcaAction);
                return true;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }

    @Override
    public List<RcaAction> getRcaActionsByRcaId(Integer rcaId) {
        List<RcaAction> rcaActions = new ArrayList<RcaAction>();
        try {
            rcaActions = rcaActionDao.getRcaActionsByRcaId(rcaId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rcaActions;
    }

    @Override
    public RcaAction getRcaActionByActionNumber(String actionNumber) {
        return rcaActionDao.getRcaActionByNumber(actionNumber);
    }

    @Override
    public RcaAction getRcaActionByActionId(Integer actionId) {
        return rcaActionDao.getRcaActionById(actionId);
    }


    @Override
    public void saveFileInDb(MultipartFile file, String fileDescription, RcaAction rcaAction, GpsUser uploadedBy) {
        log.debug("saving file: " + file.getOriginalFilename());
        Calendar now = Calendar.getInstance();
        RcaActionSupportingFile actionSupportingFile = new RcaActionSupportingFile();
        try {
            InputStream fis = file.getInputStream();
            byte[] data = IOUtils.toByteArray(fis);
            actionSupportingFile.setContents(data);
            log.debug("Total file size to save (in bytes) : " + actionSupportingFile.getContents().length);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        actionSupportingFile.setMime(file.getContentType());
        actionSupportingFile.setType(CommonUtil.getFileType(file.getContentType(), file.getOriginalFilename()));
        actionSupportingFile.setName(file.getOriginalFilename());
        actionSupportingFile.setSize(file.getSize());
        actionSupportingFile.setUploadedOn(new Timestamp(now.getTimeInMillis()));
        actionSupportingFile.setDescription(fileDescription);
        actionSupportingFile.setUploadedBy(uploadedBy);
        actionSupportingFile.setRcaAction(rcaAction);
        try {
            actionSupportingFileDao.addRcaActionSupportingFileDao(actionSupportingFile);
        } catch (Exception gpe) {
            log.error(gpe.getMessage(), gpe);
        }


    }

    @Override
    public List<RcaActionHistoryLog> getHistoryLogsByActionId(Integer rcaActionId) {
        return rcaActionHistoryLogDao.getRcaActionHistoryLogsByRcaActionId(rcaActionId);
    }

    @Override
    public List<RcaActionHistoryLog> getHistoryLogsByActionIdAndFormAction(Integer rcaActionId, String formAction) {
        return rcaActionHistoryLogDao.getRcaActionHistoryLogsByRcaActionIdAndFormAction(rcaActionId, formAction);
    }

    @Override
    public void closeAction(RcaAction rcaAction, ActionHelper actionHelper, GpsUser loggedInUser, String roles) {
        RcaAction dbRcaAction = rcaActionDao.getRcaActionById(rcaAction.getRcaActionId());
        if (StringUtils.isNotBlank(rcaAction.getAssignedTo()) && BluePages.isNotesID(rcaAction.getAssignedTo())) {
            rcaAction.setAssignedTo(BluePages.getIntranetIdFromNotesId(rcaAction.getAssignedTo()));
        }
        if (dbRcaAction != null) {
            // save and close rca action
            saveRcaAction(rcaAction, actionHelper, loggedInUser, dbRcaAction, "Closed");

            // save the rca action history log
            saveHistoryLog(loggedInUser, roles, dbRcaAction, "Closed", actionHelper.getResolutionDescription());

            //update the closed action items
            updateClosedActionItems(rcaAction);


            //Delete the Rca Action Not Closed tomorrow
            EmailReminder emailReminder = emailReminderDao.findByQuestionnaireIdAndReminderType(rcaAction.getRcaActionId(), ConstantDataManager.ACTION_NOT_CLOSED_REMINDER);
            if (emailReminder != null) {
                emailReminderDao.deleteEmailReminder(emailReminder);
            }
        }
    }

    @Override
    public void saveRcaActions(List<RcaAction> rcaActions) {
        try {
            if (CollectionUtils.isNotEmpty(rcaActions)) {
                for (RcaAction rcaAction : rcaActions) {
                    RcaAction dbRcaAction = rcaActionDao.getRcaActionById(rcaAction.getRcaActionId());
                    dbRcaAction.setActionDesc(rcaAction.getActionDesc());
                    dbRcaAction.setAssignedTo(rcaAction.getAssignedTo());
                    dbRcaAction.setTargetDate(com.gps.util.StringUtils.isNullOrEmpty(rcaAction.getActionTargetDate()) ? null : CommonUtil.convertToDate(rcaAction.getActionTargetDate()));
                    dbRcaAction.setUpdatedOn(new Timestamp(new Date().getTime()));
                    rcaActionDao.updateRcaAction(dbRcaAction);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void updateClosedActionItems(RcaAction action) {
        Rca rca = rcaService.getRcaById(action.getRca().getRcaId());
        List<RcaAction> rcaActions = rcaActionDao.getRcaActionsByRcaId(rca.getRcaId());
        Integer actionItemsOpened = 0;
        Integer actionItemsClosed = 0;
        if (CollectionUtils.isNotEmpty(rcaActions)) {
            for (RcaAction rcaAction : rcaActions) {
                // update closed and open items
                if (com.gps.util.StringUtils.isNotBlank(rcaAction.getActionStatus()) && rcaAction.getActionStatus().equalsIgnoreCase("Closed")) {
                    actionItemsClosed = actionItemsClosed + 1;
                }
                if (com.gps.util.StringUtils.isNotBlank(rcaAction.getActionStatus()) && rcaAction.getActionStatus().equalsIgnoreCase("Open")) {
                    actionItemsOpened = actionItemsOpened + 1;
                }
            }
            updateOpenAndClosedActionItems(rca, actionItemsOpened, actionItemsClosed);
        }
    }

    private void updateOpenAndClosedActionItems(Rca dbRca, Integer actionItemsOpened, Integer actionItemsClosed) {
        try {
            dbRca.setActionItemClosed(actionItemsClosed);
            dbRca.setActionItemOpen(actionItemsOpened);
            rcaDao.updateRca(dbRca);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }


    @Override
    public List<RcaActionSupportingFile> getFilesByRcaActionId(Integer rcaActionId) {
        try {
            return actionSupportingFileDao.getAllFileByRcaActionId(rcaActionId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public RcaActionSupportingFile getSupportingFileById(Integer rcaActionFileId) {
        return actionSupportingFileDao.getFileById(rcaActionFileId);
    }

    @Override
    public void deleteRcaActionSupportFile(RcaActionSupportingFile rcaActionSupportingFile) {
        try {
            actionSupportingFileDao.deleteRcaActionSupportingFile(rcaActionSupportingFile);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void saveAction(RcaAction rcaAction, ActionHelper actionHelper, GpsUser loggedInUser, String roles) {
        RcaAction dbRcaAction = rcaActionDao.getRcaActionById(rcaAction.getRcaActionId());
        if (StringUtils.isNotBlank(rcaAction.getAssignedTo()) && BluePages.isNotesID(rcaAction.getAssignedTo())) {
            rcaAction.setAssignedTo(BluePages.getIntranetIdFromNotesId(rcaAction.getAssignedTo()));
        }
        if (dbRcaAction != null) {
            // check if rca Action is re-assigned
            handleReAssignedAction(rcaAction, dbRcaAction, actionHelper);
            // save rca action
            saveRcaAction(rcaAction, actionHelper, loggedInUser, dbRcaAction, "Open");
            // save the rca action history log
            saveHistoryLog(loggedInUser, roles, dbRcaAction, "Saved", actionHelper.getResolutionDescription());
            // add Rca Action Not Closed Reminder
            addActionNotClosedReminder(dbRcaAction);
        }
    }

    private void handleReAssignedAction(RcaAction rcaAction, RcaAction dbRcaAction, ActionHelper actionHelper) {
        String newAssigneeEmail = "";
        try {
            if (StringUtils.isNotBlank(dbRcaAction.getAssignedTo()) && (!dbRcaAction.getAssignedTo().equalsIgnoreCase(rcaAction.getAssignedTo()))) {
                String newAssignee = rcaAction.getAssignedTo();
                if (BluePages.isNotesID(newAssignee)) {
                    newAssigneeEmail = BluePages.getIntranetIdFromNotesId(newAssignee);
                } else {
                        newAssigneeEmail = rcaAction.getAssignedTo();
                    
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        if (StringUtils.isNotBlank(newAssigneeEmail)) {
            rcaActionNotificationService.sendRcaActionReAssignedNotification(newAssigneeEmail, dbRcaAction, actionHelper);
        }
    }

    private void saveHistoryLog(GpsUser loggedInUser, String roles, RcaAction dbRcaAction, String formAction, String resolutionDescription) {
        RcaActionHistoryLog actionHistoryLog = new RcaActionHistoryLog();
        actionHistoryLog.setFormAction(formAction);
        actionHistoryLog.setRole(configureHistoryRole(loggedInUser, roles, dbRcaAction.getRca()));
        actionHistoryLog.setSubmittedBy(loggedInUser);
        actionHistoryLog.setSubmittedOn(new Timestamp(new Date().getTime()));
        actionHistoryLog.setRcaAction(dbRcaAction);
        if (StringUtils.isNotBlank(resolutionDescription)) {
            actionHistoryLog.setComments(resolutionDescription);
        }
        rcaActionHistoryLogDao.addRcaActionHistoryLog(actionHistoryLog);
    }

    private void saveRcaAction(RcaAction rcaAction, ActionHelper actionHelper, GpsUser loggedInUser, RcaAction dbRcaAction, String stage) {
        dbRcaAction.setActionDesc(rcaAction.getActionDesc());
        dbRcaAction.setAssignedTo(rcaAction.getAssignedTo());
        dbRcaAction.setTargetDate(CommonUtil.convertToDate(actionHelper.getTargetDate()));
        dbRcaAction.setAdditionalInfo(rcaAction.getAdditionalInfo());
        dbRcaAction.setActionItemNote(rcaAction.getActionItemNote());
        dbRcaAction.setFollowupActivity(rcaAction.getFollowupActivity());
        dbRcaAction.setRcaNote(rcaAction.getRcaNote());
        dbRcaAction.setUpdatedOn(new Timestamp(new Date().getTime()));
        dbRcaAction.setUpdatedBy(loggedInUser.getUserId());
        dbRcaAction.setActionStatus(stage);
        dbRcaAction.setTargetDateModificationReason(rcaAction.getTargetDateModificationReason());
        dbRcaAction.setCompletePercent(rcaAction.getCompletePercent());


        if (stage.equalsIgnoreCase("closed")) {
            dbRcaAction.setCloseDate(new Date());
        }
        rcaActionDao.updateRcaAction(dbRcaAction);
    }

    private String generateRcaActionNumber(String rcaNumber, int numOfActions) {
        String generatedRcaActionNumber = "";
        String actionNumStr = "";
        if (numOfActions == 0) {
            actionNumStr = "A01";
        } else {
            numOfActions++;
            if (String.valueOf(numOfActions).length() == 1) {
                actionNumStr = "A0" + numOfActions;
            } else {
                actionNumStr = "A" + numOfActions;
            }
        }
        generatedRcaActionNumber = rcaNumber + "-" + actionNumStr;

        return generatedRcaActionNumber;
    }

    private String configureHistoryRole(GpsUser loggedInUser, String roles, Rca dbRca) {
        String historyRole = "";
        if ((StringUtils.isBlank(dbRca.getRcaCoordinatorId()) && loggedInUser.getUserId() == Integer.parseInt(dbRca.getRcaCoordinatorId()))
                || rcaUtilService.isGlobalCoordinator(loggedInUser.getUserId())) {
            historyRole = "Coordinator";
        } else if ((org.apache.commons.lang.StringUtils.contains(roles, "rcaDpe")) || rcaUtilService.isGlobalDpe(loggedInUser.getUserId())) {
            historyRole = "Dpe";
        } else if ((org.apache.commons.lang.StringUtils.contains(roles, "rcaOwner")) || rcaUtilService.isGlobalOwner(loggedInUser.getUserId())) {
            historyRole = "Owner";
        } else if (org.apache.commons.lang.StringUtils.contains(roles, "rcaDelegate") || rcaUtilService.isGlobalDelegate(loggedInUser.getUserId())) {
            historyRole = "Delegate";
        } else if (rcaUtilService.isGlobalEditor(loggedInUser.getUserId())) {
            historyRole = "Editor";
        } else if (rcaUtilService.isGlobalReader(loggedInUser.getUserId())) {
            historyRole = "Reader";
        }
        return historyRole;
    }

    private String getLoggedInUserRole(Rca rca, GpsUser loggedInUser) {
        StringBuilder roles = new StringBuilder();
        if (loggedInUser.getEmail().equalsIgnoreCase(rca.getRcaOwner())) {
            roles.append("rcaOwner,");
        }
        if (loggedInUser.getEmail().equals(rca.getRcaDelegate())) {
            roles.append("rcaDelegate,");
        }
        if (rca.getRcaDpeId() != null) {
            GpsUser dpe = gpsUserService.getUserById(rca.getRcaDpeId());
            if (dpe != null && dpe.getUserId() == loggedInUser.getUserId()) {
                roles.append("rcaDpe");
            }
        }
        if (org.apache.commons.lang.StringUtils.isNotBlank(roles.toString())) {
            String lastCharStr = roles.toString().substring(roles.length() - 2, roles.length() - 1);
            if (lastCharStr.equals(",")) {
                roles = new StringBuilder(roles.toString().substring(0, roles.length() - 2));
            }
        }
        return roles.toString();
    }


    private void addActionNotClosedReminder(RcaAction rcaAction) {

        RcaEmailNotificationSetting emailNotificationSetting = emailNotificationSettingDao.getRcaEmailNotificationSettingByContractId(rcaAction.getRca().getContract().getContractId());
        EmailReminder emailReminder = emailReminderDao.findByQuestionnaireIdAndReminderType(rcaAction.getRcaActionId(), ConstantDataManager.ACTION_NOT_CLOSED_REMINDER);
        if (emailReminder == null) {
            emailReminder = new EmailReminder();
            emailReminder.setQuestionnaireId(rcaAction.getRcaActionId().toString());
            emailReminder.setQuestionnaireType(ConstantDataManager.ACTION_FORM_TYPE);
            emailReminder.setToEmail(rcaAction.getAssignedTo());
            if (com.gps.util.StringUtils.isNotBlank(rcaAction.getRca().getRcaOwner()) && com.gps.util.StringUtils.isNotBlank(rcaAction.getRca().getRcaDelegate())) {
                emailReminder.setCcEmail(rcaAction.getRca().getRcaOwner() + "," + rcaAction.getRca().getRcaDelegate());
            } else if (com.gps.util.StringUtils.isNotBlank(rcaAction.getRca().getRcaOwner()) && com.gps.util.StringUtils.isBlank(rcaAction.getRca().getRcaDelegate())) {
                emailReminder.setCcEmail(rcaAction.getRca().getRcaOwner());
            } else if (com.gps.util.StringUtils.isBlank(rcaAction.getRca().getRcaOwner()) && com.gps.util.StringUtils.isNotBlank(rcaAction.getRca().getRcaDelegate())) {
                emailReminder.setCcEmail(rcaAction.getRca().getRcaDelegate());
            }

            Date reminderStartDate = CommonUtil.subtractWorkingDays(rcaAction.getTargetDate(), emailNotificationSetting.getActionNotClosedReminderDaysBeforeTarget());
            Date reminderEndDate = CommonUtil.addWorkingDays(reminderStartDate, emailNotificationSetting.getActionNotClosedReminderDuration());
            emailReminder.setStartDate(reminderStartDate);
            emailReminder.setEndDate(reminderEndDate);
            emailReminder.setReminderType(ConstantDataManager.ACTION_NOT_CLOSED_REMINDER);
            emailReminder.setIsEnabled(ConstantDataManager.YES);
            emailReminderDao.addEmailReminder(emailReminder);
        } else {
            Date reminderStartDate = CommonUtil.subtractWorkingDays(emailReminder.getStartDate(), emailNotificationSetting.getActionNotClosedReminderDaysBeforeTarget());
            Date reminderEndDate = CommonUtil.addWorkingDays(reminderStartDate, emailNotificationSetting.getActionNotClosedReminderDuration());
            emailReminder.setStartDate(reminderStartDate);
            emailReminder.setEndDate(reminderEndDate);
            emailReminderDao.updateEmailReminder(emailReminder);

        }
    }


}
