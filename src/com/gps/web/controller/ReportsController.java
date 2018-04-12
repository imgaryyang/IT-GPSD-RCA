package com.gps.web.controller;

import com.gps.service.ContractService;
import com.gps.service.RcaService;
import com.gps.service.ReportService;
import com.gps.util.*;
import com.gps.vo.Coordinator;
import com.gps.vo.Rca;
import com.gps.vo.RcaAction;
import com.gps.vo.RcaCoordinator;
import com.gps.vo.helper.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.portlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * This class serves as controller for monthly forms.
 *
 * @authorWaqar Malik
 */

@Controller
@SessionAttributes({"userSession"})
public class ReportsController {
    private static Logger log = Logger.getLogger(ReportsController.class);

    @Autowired
    CommonUtil commonUtil;

    @Autowired
    ContractService contractService;

    @Autowired
    RcaService rcaService;

    @Autowired
    ReportService reportService;

    @Value(value = "#{'${report.path}'}")
    private String path;


    @RequestMapping(value = "/downloadReport.htm", method = RequestMethod.GET)
    @ResponseBody
    public void getSLAReport(@RequestParam("title") String title, HttpServletResponse response, HttpSession session) {
        log.debug("/downloadReport.htm (RequestMethod.GET)....");
        if (title == null || title.isEmpty()) {
            title = "generated report";
        }
        String nameAttach = title + ".xls";
        try {
            String name = path + "" + session.getId() + ".xls";
            log.debug("accessing report file: " + name);
            File cache = new File(name);
            if (cache.exists() && cache.isFile()) {
                log.debug("file exists....");
                FileInputStream fis = new FileInputStream(cache);
                response.setContentType("application/excel");
                response.setContentLength(new Integer("" + cache.length()));
                response.setHeader("content-Disposition", "attachment; filename=" + nameAttach);
                log.debug("read file into buffer and dispatch......");
                int j = -1;
                byte[] buffer = new byte[1024 * 8];
                while ((j = fis.read(buffer)) != -1) {
                    response.getOutputStream().write(buffer, 0, j);
                }
            }
        } catch (IOException ioe) {
            log.error(ioe.getMessage(), ioe);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        log.debug("Response completed.");
    }

    @RequestMapping(value = "/reports.htm", method = RequestMethod.GET)
    public String showRcaReports(Map<Object, Object> model, HttpServletRequest request) {
        Map<String, Object> referenceData = commonUtil.buildReferenceData(request.getSession(), "rca");
        log.debug("loading rca reports................");
        Integer month = null;
        Integer year = null;
        Calendar calendar = Calendar.getInstance();
        if (month == null || year == null) {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
            month = calendar.get(Calendar.MONTH) + 1;
            year = calendar.get(Calendar.YEAR);
        }

        List<Rca> rcaList = new ArrayList<Rca>(reportService.getRcaListByMonthAndYear(month, year));
        loadRcaOwnersAndDelegates(referenceData, rcaList);
        List<RcaListing> questionnaireListings = reportService.getRcasAndActionsByMonthAndYear(month, year);
        referenceData.put("listRcasAndActions", questionnaireListings);


        Set<String> rcaCoordinators = getRcaCoordinators(rcaList);
        referenceData.put("listRcaCoordinators", rcaCoordinators);

        SearchFilter rcaReportFilter = new SearchFilter();
        loadRcaAndActionNumbers(questionnaireListings, rcaReportFilter, referenceData);
        rcaReportFilter.setMonth(month);
        rcaReportFilter.setYear(year);
        rcaReportFilter.setStartOutageMonth(month);
        rcaReportFilter.setStartOutageYear(year);
        rcaReportFilter.setEndOutageMonth(month);
        rcaReportFilter.setEndOutageYear(year);
        model.put("rcaReportFilter", rcaReportFilter);
        model.put("referenceData", referenceData);
        return "reports";
    }


    @RequestMapping(value = "/rcaReportCount.htm", method = RequestMethod.POST)
    @ResponseBody
    public String getRcaListByFilter(@ModelAttribute SearchFilter searchFilter, HttpSession session) {
        log.debug("/rcaReportCount.htm (RequestMethod.POST)....");
        String result = searchFilter.getFormType().equalsIgnoreCase(ConstantDataManager.RCA_FORM_TYPE) ? "Total RCAs: " : "Total Actions: ";
        log.debug("Fetching search results from db.....");
        loadStartAndEndOutageDate(searchFilter);
        Page page = reportService.getRcaReportListBySearchFilter(searchFilter, session.getId());
        result += page.getRowCount();
        return result;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/reportRcaList.htm", method = RequestMethod.POST)
    public ModelAndView getRcaListForReport(@ModelAttribute SearchFilter searchFilter,
                                            BindingResult result, Map<Object, Object> model, ModelMap modelMap, HttpSession session) {
        loadStartAndEndOutageDate(searchFilter);
        log.debug("Fetching page...." + searchFilter.getPagination().getPageNumber());
        Page page = null;
        if (searchFilter.getPagingAction().equals(Constant.NEW_SEARCH)) {
            log.debug("Fetching search results from db.....");
            page = reportService.getRcaReportListBySearchFilter(searchFilter, session.getId());
            session.setAttribute("Allcontracts", page.getDataList());
            session.setAttribute("totalRowCount", page.getRowCount());
        }
        model.put("rcas", (List<RcaListing>) session.getAttribute("Allcontracts"));
        Long totalRowCount = (Long) session.getAttribute("totalRowCount");
        model.put("totalRowCount", session.getAttribute("totalRowCount"));
        model.put("formType", searchFilter.getFormType());
        int endRow = searchFilter.getPagination().getPageNumber() * Pagination.PAGE_SIZE;
        log.debug("displaying list of contract: " + session.getAttribute("totalRowCount"));
        return new ModelAndView("reportRcaList");
    }


    @RequestMapping(value = "/genRcaCoordinatorReport.htm", method = RequestMethod.POST)
    @ResponseBody
    public String generateRcaCoordinatorReport(@ModelAttribute SearchFilter searchFilter, HttpSession session) {
        log.debug("/genRcaCoordinatorReport.htm (RequestMethod.POST)....");
        ReportGeneratorUtil reportGeneratorUtil = new ReportGeneratorUtil();
        String result = "Failed to generate report.";
        Page page = null;
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        log.debug(userSession.getGpsUser().getEmail() + " has generated a monthly report.");
        log.debug("Fetching search results from db.....");
        loadStartAndEndOutageDate(searchFilter);
        searchFilter.setFormType("RCA");
        page = reportService.getRcaReportListBySearchFilter(searchFilter, session.getId());
        List<Rca> rcaList = reportService.getRcaReportDetails((List<RcaListing>) page.getDataList());
        log.debug("RCAs = " + page.getRowCount());
        result += page.getRowCount();
        log.debug("Invoking report APIs...");
        byte[] report = null;
        try {
            report = reportGeneratorUtil.generateRcaCoordinatorReport(rcaList, path);
            String fileName = path + "" + session.getId() + ".xls";
            File cache = new File(fileName);
            if (cache.exists() && cache.isFile()) {
                cache.delete();
                cache = new File(fileName);
            }
            cache.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(cache);
            fos.write(report);
            fos.close();
            result = Constant.SUCCESSFUL;
        } catch (IOException ioe) {
            log.error(ioe.getMessage(), ioe);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        log.debug("report generated: " + result);
        return result;
    }

    @RequestMapping(value = "/genRcaSummaryReport.htm", method = RequestMethod.POST)
    @ResponseBody
    public String generateRcaSummaryReport(@ModelAttribute SearchFilter searchFilter, HttpSession session) {
        log.debug("/genRcaSummaryReport.htm (RequestMethod.POST)....");
        ReportGeneratorUtil reportGeneratorUtil = new ReportGeneratorUtil();
        String result = "Failed to generate report.";
        Page page = null;
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        log.debug(userSession.getGpsUser().getEmail() + " has generated a monthly report.");
        log.debug("Fetching search results from db.....");
        loadStartAndEndOutageDate(searchFilter);
        searchFilter.setFormType("RCA");
        page = reportService.getRcaReportListBySearchFilter(searchFilter, session.getId());
        List<Rca> rcaList = reportService.getRcaReportDetails((List<RcaListing>) page.getDataList());
        log.debug("RCAs = " + page.getRowCount());
        result += page.getRowCount();
        log.debug("Invoking report APIs...");
        byte[] report = null;
        try {
            report = reportGeneratorUtil.generateRcaSummaryReport(rcaList, path);
            String fileName = path + "" + session.getId() + ".xls";
            File cache = new File(fileName);
            if (cache.exists() && cache.isFile()) {
                cache.delete();
                cache = new File(fileName);
            }
            cache.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(cache);
            fos.write(report);
            fos.close();
            result = Constant.SUCCESSFUL;
        } catch (IOException ioe) {
            log.error(ioe.getMessage(), ioe);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        log.debug("report generated: " + result);
        return result;
    }

    @RequestMapping(value = "/genActionSummaryReport.htm", method = RequestMethod.POST)
    @ResponseBody
    public String generateActionSummaryReport(@ModelAttribute SearchFilter searchFilter, HttpSession session) {
        log.debug("/genActionSummaryReport.htm (RequestMethod.POST)....");
        ReportGeneratorUtil reportGeneratorUtil = new ReportGeneratorUtil();
        String result = "Failed to generate report.";
        Page page = null;
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        log.debug(userSession.getGpsUser().getEmail() + " has generated a monthly report.");
        log.debug("Fetching search results from db.....");
        loadStartAndEndOutageDate(searchFilter);
        page = reportService.getRcaReportListBySearchFilter(searchFilter, session.getId());
        List<RcaAction> actionList = reportService.getActionSummaryReport((List<RcaListing>) page.getDataList());
        log.debug("RCAs = " + page.getRowCount());
        result += page.getRowCount();
        log.debug("Invoking report APIs...");
        byte[] report = null;
        try {
            report = reportGeneratorUtil.generateActionSummaryReport(actionList, path);
            String fileName = path + "" + session.getId() + ".xls";
            File cache = new File(fileName);
            if (cache.exists() && cache.isFile()) {
                cache.delete();
                cache = new File(fileName);
            }
            cache.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(cache);
            fos.write(report);
            fos.close();
            result = Constant.SUCCESSFUL;
        } catch (IOException ioe) {
            log.error(ioe.getMessage(), ioe);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        log.debug("report generated: " + result);
        return result;
    }

    @RequestMapping(value = "/genCustomerFormattedReport.htm", method = RequestMethod.POST)
    @ResponseBody
    public String generateCustomerFormattedReport(@ModelAttribute SearchFilter searchFilter, HttpSession session) {
        log.debug("/genCustomerFormattedReport.htm (RequestMethod.POST)....");
        ReportGeneratorUtil reportGeneratorUtil = new ReportGeneratorUtil();
        String result = "Failed to generate report.";
        Page page = null;
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        log.debug(userSession.getGpsUser().getEmail() + " has generated a monthly report.");
        log.debug("Fetching search results from db.....");
        loadStartAndEndOutageDate(searchFilter);
        searchFilter.setFormType("RCA");
        page = reportService.getRcaReportListBySearchFilter(searchFilter, session.getId());
        List<Rca> rcaList = reportService.getRcaReportDetails((List<RcaListing>) page.getDataList());
        log.debug("RCAs = " + page.getRowCount());
        result += page.getRowCount();
        log.debug("Invoking report APIs...");
        byte[] report = null;
        try {
            report = reportGeneratorUtil.generateCustomerFormattedReport(rcaList, path);
            // report = reportGeneratorUtil.generateCFReport(rcaList, path);
            String fileName = path + "" + session.getId() + ".xls";
            File cache = new File(fileName);
            if (cache.exists() && cache.isFile()) {
                cache.delete();
                cache = new File(fileName);
            }
            cache.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(cache);
            fos.write(report);
            fos.close();
            result = Constant.SUCCESSFUL;
        } catch (IOException ioe) {
            log.error(ioe.getMessage(), ioe);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        log.debug("report generated: " + result);
        return result;
    }


    @RequestMapping(value = "/genRcaDetailedReport.htm", method = RequestMethod.POST)
    @ResponseBody
    public String generateRcaDetailedReport(@ModelAttribute SearchFilter searchFilter, HttpSession session) {
        log.debug("/genRcaDetailedReport.htm (RequestMethod.POST)....");
        ReportGeneratorUtil reportGeneratorUtil = new ReportGeneratorUtil();
        String result = "Failed to generate report.";
        Page page = null;
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        log.debug(userSession.getGpsUser().getEmail() + " has generated a monthly report.");
        log.debug("Fetching search results from db.....");
        loadStartAndEndOutageDate(searchFilter);
        searchFilter.setFormType("RCA");
        page = reportService.getRcaReportListBySearchFilter(searchFilter, session.getId());
        List<Rca> rcaList = reportService.getRcaReportDetails((List<RcaListing>) page.getDataList());
        log.debug("RCAs = " + page.getRowCount());
        result += page.getRowCount();
        log.debug("Invoking report APIs...");
        byte[] report = null;
        try {
            report = reportGeneratorUtil.generateRcaDetailedReport(rcaList, path);
            String fileName = path + "" + session.getId() + ".xls";
            File cache = new File(fileName);
            if (cache.exists() && cache.isFile()) {
                cache.delete();
                cache = new File(fileName);
            }
            cache.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(cache);
            fos.write(report);
            fos.close();
            result = Constant.SUCCESSFUL;
        } catch (IOException ioe) {
            log.error(ioe.getMessage(), ioe);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        log.debug("report generated: " + result);
        return result;
    }

    @RequestMapping(value = "/genWeeklyOperationsReport.htm", method = RequestMethod.POST)
    @ResponseBody
    public String generateWeeklyOperationsReport(@ModelAttribute SearchFilter searchFilter, HttpSession session) {
        log.debug("/genWeeklyOperationsReport.htm (RequestMethod.POST)....");
        ReportGeneratorUtil reportGeneratorUtil = new ReportGeneratorUtil();
        String result = "Failed to generate report.";
        Page page = null;
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        log.debug(userSession.getGpsUser().getEmail() + " has generated a monthly report.");
        log.debug("Fetching search results from db.....");
        loadStartAndEndOutageDate(searchFilter);
        searchFilter.setFormType("RCA");
        page = reportService.getRcaReportListBySearchFilter(searchFilter, session.getId());
        List<Rca> rcaList = reportService.getRcaReportDetails((List<RcaListing>) page.getDataList());
        log.debug("RCAs = " + page.getRowCount());
        result += page.getRowCount();
        log.debug("Invoking report APIs...");
        byte[] report = null;
        String fileName = "";
        try {
            report = reportGeneratorUtil.generateWeeklyOperationsReport(rcaList, path);
            fileName = path + "" + session.getId() + ".xls";
            File cache = new File(fileName);
            if (cache.exists() && cache.isFile()) {
                cache.delete();
                cache = new File(fileName);
            }
            cache.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(cache);
            fos.write(report);
            fos.close();
            result = Constant.SUCCESSFUL;
        } catch (IOException ioe) {
            log.error(ioe.getMessage(), ioe);
        } catch (Exception e) {
        	log.error("Exception writing file: "+fileName);
            log.error(e.getMessage(), e);
        }
        log.debug("report generated: " + result);
        return result;
    }

    @RequestMapping(value = "/genGreenTemplateReport.htm", method = RequestMethod.POST)
    @ResponseBody
    public String generateRcaGreenTemplateReport(@ModelAttribute SearchFilter searchFilter, HttpSession session) {
        log.debug("/genGreenTemplateReport.htm (RequestMethod.POST)....");
        ReportGeneratorUtil reportGeneratorUtil = new ReportGeneratorUtil();
        String result = "Failed to generate report.";
        Page page = null;
        UserSession userSession = (UserSession) session.getAttribute("userSession");
        log.debug(userSession.getGpsUser().getEmail() + " has generated a monthly report.");
        log.debug("Fetching search results from db.....");
        loadStartAndEndOutageDate(searchFilter);
        searchFilter.setFormType("RCA");
        page = reportService.getRcaReportListBySearchFilter(searchFilter, session.getId());
        List<Rca> rcaList = reportService.getRcaReportDetails((List<RcaListing>) page.getDataList());
        log.debug("RCAs = " + page.getRowCount());
        result += page.getRowCount();
        log.debug("Invoking report APIs...");
        byte[] report = null;
        try {
            report = reportGeneratorUtil.generateRcaGreenTemplateReport(rcaList, path);
            String fileName = path + "" + session.getId() + ".xls";
            File cache = new File(fileName);
            if (cache.exists() && cache.isFile()) {
                cache.delete();
                cache = new File(fileName);
            }
            cache.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(cache);
            fos.write(report);
            fos.close();
            result = Constant.SUCCESSFUL;
        } catch (IOException ioe) {
            log.error(ioe.getMessage(), ioe);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        log.debug("report generated: " + result);
        return result;
    }

    private void loadRcaOwnersAndDelegates(Map<String, Object> referenceData, List<Rca> rcaList) {
        Set<String> rcaOwners = new HashSet<String>();
        Set<String> rcaDelegates = new HashSet<String>();

        for (Rca rca : rcaList) {
            if (StringUtils.isNotBlank(rca.getRcaOwner())) {
                rcaOwners.add(BluePages.getNotesIdFromIntranetId(rca.getRcaOwner()));
            }
            if (StringUtils.isNotBlank(rca.getRcaDelegate())) {
                rcaDelegates.add(BluePages.getNotesIdFromIntranetId(rca.getRcaDelegate()));
            }
        }
        referenceData.put("rcaDelegates", new ArrayList<String>(rcaDelegates));
        referenceData.put("rcaOwners", new ArrayList<String>(rcaOwners));
    }

    private void loadStartAndEndOutageDate(SearchFilter searchFilter) {
        Integer startDay = 1;
        Integer endDay = 31;

        if (searchFilter.getMonth() == null) {
            searchFilter.setMonth(searchFilter.getStartOutageMonth());
        }
        if (searchFilter.getYear() == null) {
            searchFilter.setYear(searchFilter.getStartOutageYear());
        }
        if (searchFilter.getStartOutageDay() != null) {
            startDay = searchFilter.getStartOutageDay();
        }
        Integer startMonth = searchFilter.getStartOutageMonth();
        Integer startYear = searchFilter.getStartOutageYear();
        String startDateStr = "" + startYear + "-" + startMonth + "-" + startDay + " 00:00";
        searchFilter.setStartDate(CommonUtil.convertToTimestamp(startDateStr));

        if (searchFilter.getEndOutageDay() != null) {
            endDay = searchFilter.getEndOutageDay();
        }
        Integer endMonth = searchFilter.getEndOutageMonth();
        Integer endYear = searchFilter.getEndOutageYear();
        String endDateStr = "" + endYear + "-" + endMonth + "-" + endDay + " 23:59";
        searchFilter.setEndDate(CommonUtil.convertToTimestamp(endDateStr));
    }

    private Map<String, List<String>> getRcaAndActionNumbers(List<RcaListing> questionareListings) {
        Map<String, List<String>> rcaAndActionMap = new HashMap<String, List<String>>();
        List<String> rcaNumbers = new ArrayList<String>();
        List<String> actionNumbers = new ArrayList<String>();
        if (CollectionUtils.isNotEmpty(questionareListings)) {
            for (RcaListing rcaListing : questionareListings) {
                if (rcaListing.getListingType().equalsIgnoreCase("rca")) {
                    rcaNumbers.add(rcaListing.getRcaOrActionNumber() + "_rca");
                } else if (rcaListing.getListingType().equalsIgnoreCase("action")) {
                    actionNumbers.add(rcaListing.getRcaOrActionNumber() + "_action");
                }
            }
        }
        rcaAndActionMap.put("rcaNumbers", rcaNumbers);
        rcaAndActionMap.put("actionNumbers", actionNumbers);

        return rcaAndActionMap;

    }

    private void loadRcaAndActionNumbers(List<RcaListing> questionareListings, SearchFilter rcaReportFilter, Map<String, Object> referenceData) {
        Map<String, List<String>> rcasAndActionsMap = getRcaAndActionNumbers(questionareListings);
        List<String> rcaNumbers = rcasAndActionsMap.get("rcaNumbers");
        List<String> actionNumbers = rcasAndActionsMap.get("actionNumbers");
        if (CollectionUtils.isNotEmpty(rcaNumbers)) {
            rcaReportFilter.setRcaNumbers(StringUtils.join(rcaNumbers, ","));
        }
        if (CollectionUtils.isNotEmpty(actionNumbers)) {
            rcaReportFilter.setActionNumbers(StringUtils.join(actionNumbers, ","));
        }
        referenceData.put("rcaNumberList", rcaNumbers);

    }

    private Set<String> getRcaCoordinators(List<Rca> rcaList) {
        Set<String> rcaCoordinators = new HashSet<String>();
        for (Rca rca : rcaList) {
            if (com.gps.util.StringUtils.isNotBlank(rca.getRcaCoordinatorId()) && !rcaCoordinators.contains(rca.getRcaCoordinatorId()))
                if (!BluePages.isNotesID(rca.getRcaCoordinatorId())) {
                    rcaCoordinators.add(BluePages.getNotesIdFromIntranetId(rca.getRcaCoordinatorId()));
                } else {
                    rcaCoordinators.add(rca.getRcaCoordinatorId());
                }
        }
        return rcaCoordinators;
    }

    private String getRcaIds(List<Rca> rcaList) {
        List<String> rcaIds = new ArrayList<String>();
        for (Rca rca : rcaList) {
            rcaIds.add(String.valueOf(rca.getRcaId()));
        }
        return StringUtils.join(rcaIds, ",");
    }


    private Set<Coordinator> getCoordinators(Set<RcaCoordinator> rcaCoordinators) {
        Map<String, Coordinator> coordinatorMap = new HashMap<String, Coordinator>();
        for (RcaCoordinator rcaCoordinator : rcaCoordinators) {
            if (rcaCoordinator != null && rcaCoordinator.getCoordinator() != null) {
                Coordinator coordinator = rcaCoordinator.getCoordinator();
                coordinator.setCoordinatorId(rcaCoordinator.getRcaCoordinatorId());
                coordinator.setNotesId(BluePages.getNotesIdFromIntranetId(coordinator.getIntranetId()));
                if (!coordinatorMap.containsKey(coordinator.getIntranetId())) {
                    coordinatorMap.put(coordinator.getIntranetId(), coordinator);
                }
            }
        }
        return new HashSet<Coordinator>(coordinatorMap.values());

    }

    private List<?> paginate(List<?> dataList, Integer pageNo) {
        Integer startRow = (pageNo.intValue() * Pagination.PAGE_SIZE.intValue()) - Pagination.PAGE_SIZE.intValue();
        Integer endRow = startRow + Pagination.PAGE_SIZE;
        if (endRow > dataList.size())
            endRow = dataList.size();
        return dataList.subList(startRow, endRow);
    }


}
