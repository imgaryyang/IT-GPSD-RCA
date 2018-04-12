package com.gps.dao;

import com.gps.vo.RcaActionSupportingFile;

import java.util.List;

public interface RcaActionSupportingFileDao {

    public void addRcaActionSupportingFileDao(RcaActionSupportingFile actionSupportingFile);
    public List<RcaActionSupportingFile> getAllFileByRcaActionId(Integer rcaActionId);
    public RcaActionSupportingFile getFileById(Integer rcaActionFileId);
    public void deleteRcaActionSupportingFile(RcaActionSupportingFile rcaActionSupportingFile);
}