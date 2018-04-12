package com.gps.dao;


import com.gps.vo.ServiceDescription;

import java.util.List;

public interface ServiceDescriptionDao {

    public List<ServiceDescription> getServiceDescriptionListByType(String serviceDescriptionType);

    public List<ServiceDescription> getServiceDescriptionListByParentId(Integer parentId);

    public List<ServiceDescription> getServiceDescriptionListByParentName(String parentName);

    public List<ServiceDescription> getServiceDescriptionListByParentNameAndType(String parentName, String type);

    public ServiceDescription getServiceDescriptionById(Integer serviceDescriptionId);




}