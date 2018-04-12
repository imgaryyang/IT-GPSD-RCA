package com.gps.dao;

import com.gps.vo.RcaEditor;

import java.util.List;


public interface RcaEditorDao {

    public void addRcaEditor(RcaEditor rcaEditor);

    public void updateEditor(RcaEditor rcaEditor);

    public RcaEditor getRcaEditorByRcaIdAndUserId(Integer rcaId, Integer userId);

    public List<RcaEditor> getRcaEditorsByRcaId(Integer rcaId);


}
