package com.gps.service;


import com.gps.vo.GpsUser;
import com.gps.vo.RcaAction;
import com.gps.vo.RcaActionHistoryLog;
import com.gps.vo.RcaActionSupportingFile;
import com.gps.vo.helper.ActionHelper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface RcaActionService {

    public boolean addNewRcaAction(Integer rcaId, GpsUser createdBy);
    public boolean deleteRcaAction(Integer rcaActionId);
    public List<RcaAction> getRcaActionsByRcaId(Integer rcaId);
    public RcaAction getRcaActionByActionNumber(String actionNumber);
    public RcaAction getRcaActionByActionId(Integer actionId);
    public List<RcaActionSupportingFile> getFilesByRcaActionId(Integer rcaActionId);
    public RcaActionSupportingFile getSupportingFileById(Integer rcaActionFileId);
    public void deleteRcaActionSupportFile(RcaActionSupportingFile rcaActionSupportingFile);
    public void saveAction(RcaAction rcaAction, ActionHelper actionHelper, GpsUser loggedInUser, String roles);
    public void saveFileInDb(MultipartFile file, String fileDescription, RcaAction rcaAction, GpsUser loggedInUser);
    public List<RcaActionHistoryLog> getHistoryLogsByActionId(Integer rcaActionId);
    public List<RcaActionHistoryLog> getHistoryLogsByActionIdAndFormAction(Integer rcaActionId, String formAction);
    public void closeAction(RcaAction rcaAction, ActionHelper actionHelper, GpsUser loggedInUser,String roles);
    public void saveRcaActions(List<RcaAction> rcaActions);
}
