package com.gps.dao;

import com.gps.vo.RcaSupportingFile;

import java.util.List;

public interface RcaSupportingFileDao {

    public void addRcaSupportingFile(RcaSupportingFile rcaSupportingFile);
    public List<RcaSupportingFile> getAllFileByRcaId(Integer rcaId);
    public RcaSupportingFile getFileById(Integer rcaFileId);
    public void deleteRcaSupportingFile(RcaSupportingFile rcaSupportingFile);
}