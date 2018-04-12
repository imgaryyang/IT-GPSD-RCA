package com.gps.util;

import com.gps.service.GpsUserService;
import com.gps.service.SessionACLService;
import com.gps.vo.helper.RcaListing;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Waqar Malik on 12-05-2015.
 */
@Service
public class RcaUtil implements Serializable {

    private static final Logger logger = Logger.getLogger(RcaUtil.class);

    @Autowired
    GpsUserService gpsUserService;

    @Autowired
    SessionACLService sessionACLService;


    public static List<String> loadPrimaryTickets(ArrayList dataList) {
        List<String> primaryTickets = new ArrayList<String>();
        if (CollectionUtils.isNotEmpty(dataList)) {
            for (Object object : dataList) {
                RcaListing rcaListing = (RcaListing) object;
                if (StringUtils.isNotBlank(rcaListing.getPrimaryTicket())) {
                    primaryTickets.add(rcaListing.getPrimaryTicket());
                }
            }
        }
        return primaryTickets;
    }



}
