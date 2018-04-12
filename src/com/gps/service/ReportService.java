package com.gps.service;

import com.gps.vo.Rca;
import com.gps.vo.RcaAction;
import com.gps.vo.RcaCoordinator;
import com.gps.vo.helper.Page;
import com.gps.vo.helper.RcaListing;
import com.gps.vo.helper.SearchFilter;

import java.util.List;
import java.util.Set;

/**
 * Created by Waqar Malik on 14-04-2015.
 */
public interface ReportService {
    List<Rca> getRcaCoordinatorReport(List<RcaListing> dataList);
    List<RcaCoordinator> getRcaCoordinatorsByRcaIds(String rcaIds);
    Page getRcaReportListBySearchFilter(SearchFilter searchFilter, String sessionId);
    List<Rca> getRcaReportDetails(List<RcaListing> dataList);
    List<RcaAction> getActionSummaryReport(List<RcaListing> dataList);
    Set<Rca> getRcaListByMonthAndYear(int month, int year);
    List<RcaListing> getRcasAndActionsByMonthAndYear(int month, int year);
}
