package com.gps.dao;


import com.gps.vo.RcaCause;

import java.util.List;

public interface RcaCauseDao {

    public void addRcaCause(RcaCause rcaCause);
    public void updateRcaCause(RcaCause rcaCause);
    public void deleteRcaCause(RcaCause rcaCause);
    List<RcaCause> getRcaCausesByRcaIdAndType(Integer rcaId, String type);
    public List<RcaCause> getRcaCausesByRcaId(Integer rcaId);
    public RcaCause getRcaCauseById(Integer rcaCauseId);

}