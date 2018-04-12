package com.gps.dao;


import com.gps.vo.CauseDescription;

import java.util.List;

public interface CauseDescriptionDao {

    public List<CauseDescription> getCauseDescriptionListByType(String causeDescriptionType);
    public List<CauseDescription> getCauseDescriptionListByParentId(Integer parentId);
    public List<CauseDescription> getCauseDescriptionListByParentName(String causeDescriptionName);
}