/**
 * <pre>
 * ==========================================================================
 *
 * Copyright: (C) IBM Corporation 2013 -- IBM Internal Use Only
 *
 * ==========================================================================
 *
 *    FILE: AdminController.java
 *    CREATOR:Waqar Malik
 *    DEPT: GBS PAK
 *    DATE: 13/02/2013
 *
 * -PURPOSE-----------------------------------------------------------------
 *
 * --------------------------------------------------------------------------
 *
 *
 * -CHANGE LOG--------------------------------------------------------------
 * 13/02/2013Waqar Malik Initial coding.
 * ==========================================================================
 * </pre>
 */
package com.gps.util;

import com.gps.service.ContractService;
import com.gps.service.GpsService;
import com.gps.service.GpsUserService;
import com.gps.service.UserRoleService;
import com.gps.vo.*;
import com.gps.vo.Process;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class contains common utility methods across the controller and business layers.
 *
 * @authorWaqar Malik
 */
@Service
public class CommonUtil implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1159044882017245683L;
    private static final Logger logger = Logger.getLogger(CommonUtil.class);

    @Autowired
    GpsCacheManager gpsCacheManager;

    @Autowired
    private ContractService contractService;

    @Autowired
    private GpsService gpsService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    private GpsUserService gpsUserService;


    public static Map<Integer, String> getMonthList() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "January");
        map.put(2, "February");
        map.put(3, "March");
        map.put(4, "April");
        map.put(5, "May");
        map.put(6, "June");
        map.put(7, "July");
        map.put(8, "August");
        map.put(9, "September");
        map.put(10, "October");
        map.put(11, "November");
        map.put(12, "December");
        return map;
    }

    public static String getFileType(String contentType, String name) {
        String type = null;
        if (name != null && !name.isEmpty()) {
            int index = name.lastIndexOf(".");
            String ext = "";
            if (index > 0) {
                ext = name.substring(index);
            }
            if (ext.length() > 4) {
                type = ext;
            } else {
                type = contentType;
            }
        }
        return type;
    }

    public static String getLongStatus(String ragStatus) {
        if (ragStatus == null || ragStatus.isEmpty()) {
            return ragStatus;
        }
        String status = null;
        if (ragStatus.equals("G")) {
            status = "Green";
        } else if (ragStatus.equals("A")) {
            status = "Amber";
        } else if (ragStatus.equals("R")) {
            status = "Red";
        }
        return status;
    }

    /**
     * @param rag
     * @param rag2
     * @return
     * @authorWaqar Malik
     */
    public static String getSevereStatus(String rag, String rag2) {
//		logger.debug("getSevereStatus()..."+rag+", ["+rag2+"]");
        if (rag == null || rag.trim().isEmpty()) {
            return rag2;
        }
        if (rag2 == null || rag2.trim().isEmpty()) {
            return rag;
        }
        String status = rag;
        if (rag.equals("N")) {
            status = rag2;
        } else if (rag.equals("G") || rag.equals("Green")) {
            if (!rag2.equals("N")) {
                status = rag2;
            }
        } else if (rag.equals("A") || rag.equals("Amber")) {
            if (rag2.equals("R") || rag2.equals("Red")) {
                status = rag2;
            }
        }
        logger.debug("getSevereStatus()...status=" + status);
        return status;
    }

    public static List<Integer> getYearList() {
        List<Integer> years = new ArrayList<Integer>();
        years.add(2011);
        years.add(2012);
        years.add(2013);
        years.add(2014);
        years.add(2015);
        years.add(2016);
        years.add(2017);
        years.add(2018);
        return years;
    }

    public static String getAccessName(Integer level) {
        return getAcessLevelMap().get(level);
    }

    public static Map<Integer, String> getAcessLevelMap() {
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(-1, "No Access");
        map.put(0, "Public Read");
        map.put(1, "Read");
        map.put(2, "Save");
        map.put(4, "Submit");
        map.put(8, "Accept/Reject");
        map.put(16, "Contract Admin");
        map.put(32, "Full Admin");
        return map;
    }

    public static List convertSetToList(Set set) {
        List list = LazyList.decorate(new ArrayList(), FactoryUtils.instantiateFactory(Object.class));

        for (Object lob : set) {
            list.add(lob);
        }
        return list;
    }

    public static boolean isDouble(String string) {
        try {
            double d = Double.parseDouble(string);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static boolean isInteger(String string) {
        try {
            int number = Integer.parseInt(string);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    public static boolean isNotEmptyAndNull(String string) {
        if (string != null && !string.trim().equalsIgnoreCase("")) {
            return true;
        } else {
            return false;
        }
    }

    public static List<Date> getCurrYearList(int year) {
        try {
            //int year =2013;
            List<Date> result = new ArrayList<Date>();

            int dayOfWeek = Calendar.FRIDAY;
            // instantiate Calender and set to first Sunday of year
            Calendar cal = new GregorianCalendar();
            cal.set(year, 0, 1, 0, 0);
            cal.getTime();
            cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
            cal.getTime();
            int i = 1;
            while (cal.get(Calendar.YEAR) == year) {
                //System.out.println(dayOfWeekString + " " + i + ": " + dateFormat.format(cal.getTime()));

                result.add(cal.getTime());
                cal.add(Calendar.DAY_OF_MONTH, 7);
                i++;
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Map<String, Object> buildReferenceData(HttpSession session) {
        Map<String, Object> map = buildData();
        TreeSet<Contract> sortedListContracts = null;
        TreeSet<Customer> sortedListCustomers = null;

        //find in session first;
        //	if(session.getServletContext().getAttribute("referenceContracts") != null){
//			sortedListContracts = (TreeSet<Contract>) session.getServletContext().getAttribute("referenceContracts");
//		}else {
        logger.info("referenceContracts not in cache, going to build contracts cache.");
        sortedListContracts = new TreeSet<Contract>(ContractNameComparator);
        sortedListContracts.addAll(new HashSet<Contract>(contractService.getAllContracts(session.getId())));
        session.getServletContext().setAttribute("referenceContracts", sortedListContracts);
//		}
        map.put("listContracts", sortedListContracts);


        if (session.getServletContext().getAttribute("referenceCustomers") != null) {
            sortedListCustomers = (TreeSet<Customer>) session.getServletContext().getAttribute("referenceCustomers");
        } else {
            logger.info("referenceCustomers not in cache, going to build customers cashe.");
            sortedListCustomers = new TreeSet<Customer>(CustomerNameComparator);
            sortedListCustomers.addAll(gpsService.getCustomers(session.getId()));
            session.getServletContext().setAttribute("referenceCustomers", sortedListCustomers);
        }
        map.put("customers", sortedListCustomers);
        return map;
    }

    public Map<String, Object> buildReferenceData(HttpSession session, String formType) {
        Map<String, Object> map = buildData();
        if (formType.equalsIgnoreCase("rca")) {
            TreeSet<Contract> sortedListContracts = null;
            TreeSet<Customer> sortedListCustomers = null;
            TreeSet<Contract> sortedListRcaContracts = null;

            sortedListContracts = new TreeSet<Contract>(ContractNameComparator);
            sortedListContracts.addAll(new HashSet<Contract>(contractService.getAllContracts(session.getId())));
            session.getServletContext().setAttribute("referenceContracts", sortedListContracts);
            map.put("listContracts", sortedListContracts);

            sortedListRcaContracts = new TreeSet<Contract>(ContractNameComparator);
            sortedListRcaContracts.addAll(new HashSet<Contract>(contractService.getAllContractsForRcaForm(session.getId())));
            session.getServletContext().setAttribute("referenceRcaContracts", sortedListRcaContracts);
            if (CollectionUtils.isEmpty(sortedListRcaContracts)) {
                UserSession userSession = (UserSession) session.getAttribute("userSession");
                GpsUser loggedInUser = gpsUserService.getUserByIntranetId(userSession.getGpsUser().getEmail());
                if (loggedInUser == null) {
                    loggedInUser = new GpsUser();
                    loggedInUser.setEmail(userSession.getGpsUser().getEmail());
                    gpsUserService.addUser(loggedInUser);
                }
                List<UserRole> userRoles = userRoleService.getUserRolesByUserId(loggedInUser.getUserId());
                if (CollectionUtils.isNotEmpty(userRoles)) {
                    for (UserRole role : userRoles) {
                        // if it has global role
                        if (role.getContract() == null) {
                            List<Contract> allContracts = contractService.getAllContracts();
                            for (Contract contract : allContracts) {
                                if (contract != null && !sortedListRcaContracts.contains(contract)) {
                                    sortedListRcaContracts.add(contract);
                                }
                            }
                        }
                        if (role.getContract() != null && !sortedListRcaContracts.contains(role.getContract())) {
                            sortedListRcaContracts.add(role.getContract());
                        }
                    }
                }


            }
            map.put("listRcaContracts", sortedListRcaContracts);

            if (session.getServletContext().getAttribute("referenceCustomers") != null) {
                sortedListCustomers = (TreeSet<Customer>) session.getServletContext().getAttribute("referenceCustomers");
            } else {
                logger.info("referenceCustomers not in cache, going to build customers cashe.");
                sortedListCustomers = new TreeSet<Customer>(CustomerNameComparator);
                sortedListCustomers.addAll(gpsService.getCustomers(session.getId()));
                session.getServletContext().setAttribute("referenceCustomers", sortedListCustomers);
            }
            map.put("customers", sortedListCustomers);
        }
        return map;
    }

    public Map<String, Object> buildReferenceData() {
        Map<String, Object> map = buildData();

        TreeSet<Contract> sortedListContracts = new TreeSet<Contract>(ContractNameComparator);
        sortedListContracts.addAll(new HashSet<Contract>(contractService.getAllContracts()));
        map.put("listContracts", sortedListContracts);

        TreeSet<Customer> sortedListCustomers = new TreeSet<Customer>(CustomerNameComparator);
        sortedListCustomers.addAll(gpsCacheManager.getCustomers());
        map.put("customers", sortedListCustomers);

        return map;
    }

    private Map<String, Object> buildData() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("stages", gpsCacheManager.getStateChange());
        map.put("accesses", gpsCacheManager.getAccesses());
        map.put("lobs", "IBM");

        List<String> sectors = new ArrayList<String>();
        for (Iterator<String> i = sectors.iterator(); i.hasNext(); ) {
            String s = i.next();
            s = s.trim();
            if (s.equalsIgnoreCase("none")) {
                i.remove();
            }
        }
        map.put("sectors", sectors);

        Set<String> geos = new HashSet<String>();
        for (Iterator<String> i = geos.iterator(); i.hasNext(); ) {
            String s = i.next();
            s = s.trim();
            if (s.equalsIgnoreCase("none")) {
                i.remove();
            }
        }
        map.put("geos", geos);

        Set<String> iots = new HashSet<String>();
        for (Iterator<String> i = iots.iterator(); i.hasNext(); ) {
            String s = i.next();
            s = s.trim();
            if (s.equalsIgnoreCase("none")) {
                i.remove();
            }
        }
        map.put("iots", iots);

        Set<String> imts = new HashSet<String>();
        for (Iterator<String> i = imts.iterator(); i.hasNext(); ) {
            String s = i.next();
            s = s.trim();
            if (s.equalsIgnoreCase("none")) {
                i.remove();
            }
        }
        map.put("monthList", getMonthList());
        map.put("yearList", getYearList());

        return map;
    }

    public static String appendThreeDigit(String num) {
        StringBuilder str = new StringBuilder();
        for (int i = num.length(); i < 3; i++) {
            str.append("0");
        }
        str.append(num);
        return str.toString();
    }

    public static boolean isNotEmptyAndNull(Integer integer) {
        if (integer != null)
            return true;
        else
            return false;
    }

    public static boolean isPosInteger(String string) {
        int number = Integer.parseInt(string);
        if (number > 0) {
            return true;
        } else
            return false;
    }

    public static boolean isValidVR(String string, Integer integer) {
        int number = Integer.parseInt(string);
        if (number > 0 && number <= integer) {
            return true;
        } else
            return false;
    }

    public static boolean isValidMSOrVH(String string) {
        int number = Integer.parseInt(string);
        if (number > 0 && number <= 100) {
            return true;
        } else
            return false;
    }

    

    /**
     * @author muhammad_attique
     */
    public static Comparator<Process> PROCESS_COMPARATOR = new Comparator<Process>() {
        @Override
        public int compare(final Process p1, final Process p2) {
            int result = -1;
            if (p1.getName() == null) {
                result = (p2.getName() == null) ? 0 : -1;
            } else {
                result = p1.getName().compareTo(p2.getName());
            }
            return result;
        }
    };


    /**
     * @author muhammad_attique
     */
    public static Comparator<Contract> ContractNameComparator = new Comparator<Contract>() {
        public int compare(Contract contract1, Contract contract2) {
            String contractName1 = contract1.getTitle().toUpperCase();
            String contractName2 = contract2.getTitle().toUpperCase();
            return contractName1.compareTo(contractName2);
        }
    };

    public static Comparator<Customer> CustomerNameComparator = new Comparator<Customer>() {
        public int compare(Customer customer1, Customer customer2) {
            String customerName1 = customer1.getName().toUpperCase();
            String customerName2 = customer2.getName().toUpperCase();
            return customerName1.compareTo(customerName2);
        }
    };

    public static void setWeeklyDate(Calendar calender) {
        int dayOfweek = calender.get(Calendar.DAY_OF_WEEK);

        if (dayOfweek > 6) {
            calender.add(Calendar.DATE, 6 - dayOfweek);
        } else if (dayOfweek < 6) {
            if (dayOfweek == 1) {
                calender.add(Calendar.DATE, 6 - 8);
            } else if (dayOfweek == 2) {
                calender.add(Calendar.DATE, 6 - 9);
            } else {
                calender.add(Calendar.DATE, 6 - dayOfweek);
            }
        }
    }

    public static List<String> getYearList(int year) {
        List<String> list = new ArrayList<String>();
        for (int i = -5; i <= 5; i++) {
            String yearVal = (year + i) + "";
            list.add(yearVal);
        }

        return list;
    }

    public static String calculateRagStatus(String number) {
        String ragStatus = "N/A";

        if (StringUtils.isEmpty(number)) {
            return ragStatus;
        } else if (number.equals("1")) {
            ragStatus = "Red-";
        } else if (number.equals("2")) {
            ragStatus = "Red=";
        } else if (number.equals("3")) {
            ragStatus = "Red";
        } else if (number.equals("4")) {
            ragStatus = "Amber-";
        } else if (number.equals("5")) {
            ragStatus = "Amber=";
        } else if (number.equals("6")) {
            ragStatus = "Amber+";
        } else if (number.equals("7")) {
            ragStatus = "Amber";
        } else if (number.equals("8")) {
            ragStatus = "Green=";
        } else if (number.equals("9")) {
            ragStatus = "Green+";
        } else if (number.equals("10")) {
            ragStatus = "Green";
        }
        return ragStatus;

    }

    public static String calculateDateTimeDifference(Date startDate, Date endDate) {
        return DurationFormatUtils.formatDurationWords(endDate.getTime() - startDate.getTime(), true, true);
    }

    public static Timestamp convertToTimestamp(String dateStr) {
        Timestamp timeStampDate = null;
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = (Date) sdf.parse(dateStr);
            timeStampDate = new Timestamp(date.getTime());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return timeStampDate;
    }

    public static java.sql.Date convertToDate(String dateStr) {
        Date date = null;
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = (Date) sdf.parse(dateStr);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return new java.sql.Date(date.getTime());
    }

    public static String convertTimestampToString(Timestamp timestamp) {
        String timeStampStr = null;
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            timeStampStr = sdf.format(timestamp);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return timeStampStr;
    }

    public static String convertDateToString(Date date) {
        String dateStr = null;
        try {
            DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dateStr = sdf.format(date);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return dateStr;
    }

    public static String convertDateToStringFormat(Date date) {
        String dateStr = null;
        try {
            DateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            dateStr = sdf.format(date);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return dateStr;
    }

    public static String calculateTimestampDifference(Timestamp startDate, Timestamp endDate) {
        return DurationFormatUtils.formatDurationWords(endDate.getTime() - startDate.getTime(), false, true);
    }

    public static Long calculateTheDifferenceBetweenDates(Date date1, Date date2) {
        long diff = 0L;
        if (date1 != null && date2 != null) {
            diff = date2.getTime() - date1.getTime();
            return diff / 1000L / 60L / 60L / 24L;
        }
        return diff;
    }


    public static boolean isActionNumber(String rcaOrActionNumber) {
        if (org.apache.commons.lang.StringUtils.contains(rcaOrActionNumber, "A")) {
            return true;
        }
        return false;
    }

    public static Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }


    public static Date addWorkingDays(Date date, int workingDays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int i = 0;
        while (i < workingDays) {
        	cal.add(Calendar.DAY_OF_YEAR, 1);
            if (isWorkingDay(cal)) {
                i++;
            } else {
                //cal.add(Calendar.DAY_OF_YEAR, 1);
            }
        }
        return cal.getTime();
    }

    public static Date subtractWorkingDays(Date date, int workingDays) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int i = 1;
        while (i < workingDays) {
            if (isWorkingDay(cal)) {
                cal.add(Calendar.DAY_OF_YEAR, -1);
                i++;
            } else {
                cal.add(Calendar.DAY_OF_YEAR, -1);
            }
        }
        return cal.getTime();
    }

    private static boolean isWorkingDay(Calendar cal) {
    	DateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
//        logger.warn(sdf.format(cal.getTime())+" =>DAY_OF_WEEK="+dayOfWeek+", "+Calendar.SATURDAY+" or "+Calendar.SUNDAY);
        if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
            return false;
        }
        return true;
    }

    public static Hashtable<String, String> getValueMap(Rca rca) {
        Hashtable<String, String> keys = new Hashtable<String, String>();
        keys.put("rcaNumber", rca.getRcaNumber());
        keys.put("contractName", rca.getContract().getTitle());
        keys.put("contractId", String.valueOf(rca.getContract().getContractId()));
        keys.put("rcaEndDate", rca.getDueDate() != null ? convertDateToStringFormat(rca.getDueDate()) : "");

        return keys;
    }

    public static Hashtable<String, String> getValueMap(RcaAction rcaAction) {
        Hashtable<String, String> keys = new Hashtable<String, String>();
        keys.put("actionItemNo", rcaAction.getActionNumber());
        keys.put("targetDate", rcaAction.getTargetDate() != null ? convertDateToStringFormat(rcaAction.getTargetDate()) : "");
        keys.put("rcaNumber", rcaAction.getRca().getRcaNumber());
        keys.put("contractName", rcaAction.getRca().getContract().getTitle());
        keys.put("contractId", String.valueOf(rcaAction.getRca().getContract().getContractId()));
        keys.put("rcaEndDate", rcaAction.getRca().getDueDate() != null ? convertDateToStringFormat(rcaAction.getRca().getDueDate()) : "");

        return keys;
    }

    public static boolean isWorkingDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
            return false;
        }
        return true;
    }


    public static String checkIfUserIsAdmin(List<UserRole> roles) {
        String isAdmin = "false";
        if (CollectionUtils.isNotEmpty(roles)) {
            for (UserRole role : roles) {
                if (role.getRole().equalsIgnoreCase("admin")) {
                    isAdmin = "true";
                    break;
                }
            }
        }
        return isAdmin;
    }

    public static String genPassword(int len){
    	logger.debug("genOTPassword()...");
        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$%^&*_=+-/.?<>)";
 
        String values = Capital_chars + Small_chars + numbers;
        // Using random method
        Random random = new Random();
        char[] password = new char[len];
 
        for (int i = 0; i < len; i++){
            // Use of charAt() method : to get character value
            // Use of nextInt() as it is scanning the value as int
            password[i] =  values.charAt(random.nextInt(values.length()));
 
        }
        return new String(password);
    }
    
    public static boolean isAdmin(List<UserRole> roles) {
        if (CollectionUtils.isNotEmpty(roles)) {
            for (UserRole userRole : roles) {
                if (userRole.getRole().equalsIgnoreCase("admin")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String getBasePath(HttpServletRequest request) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        return basePath;
    }

    public static List<String> getLocationsOfBusiness() {
        List<String> locationsOfBusiness = new ArrayList<String>();
        locationsOfBusiness.add("CG");
        locationsOfBusiness.add("Infinity");
        locationsOfBusiness.add("444");
        locationsOfBusiness.add("ASF");
        locationsOfBusiness.add("Building-8");
        locationsOfBusiness.add("Noida Sector 57");
        locationsOfBusiness.add("Noida Sector 135");
        locationsOfBusiness.add("UCP");
        locationsOfBusiness.add("Chandigarh");
        locationsOfBusiness.add("Kolkatta DLF");
        locationsOfBusiness.add("Kolkatta Millennium City");
        locationsOfBusiness.add("Pune TPO");
        locationsOfBusiness.add("Mumbai Magnus");
        locationsOfBusiness.add("Bangalore MTP");
        locationsOfBusiness.add("Bangalore PTP");
        locationsOfBusiness.add("Bangalore ITPL");
        locationsOfBusiness.add("Chennai T-Nagar");
        locationsOfBusiness.add("Chennai Navallur");
        locationsOfBusiness.add("Vizag");
        locationsOfBusiness.add("Dalian");
        locationsOfBusiness.add("Costa Rica");
        locationsOfBusiness.add("Hortolandia");
        locationsOfBusiness.add("Budapest");
        locationsOfBusiness.add("Bangalore");
        locationsOfBusiness.add("Krakow");
        locationsOfBusiness.add("Tulsa");
        locationsOfBusiness.add("St. John");
        locationsOfBusiness.add("Daleville");
        locationsOfBusiness.add("Manila");
        locationsOfBusiness.add("Greenville");
        locationsOfBusiness.add("Beaverton");
        locationsOfBusiness.add("Shangai");
        locationsOfBusiness.add("Cairo");
        locationsOfBusiness.add("Madrid");
        locationsOfBusiness.add("Montevideo");
        locationsOfBusiness.add("Buenos Aries");

        locationsOfBusiness.add("Braga");
        locationsOfBusiness.add("Sofia");
        locationsOfBusiness.add("Coventry");
        locationsOfBusiness.add("Newcastle");
        locationsOfBusiness.add("Dublin");
        locationsOfBusiness.add("Istanbul");
        locationsOfBusiness.add("Greenock");
        locationsOfBusiness.add("UCP Gurgaon");
        locationsOfBusiness.add("Kolkatta Unitech");
        locationsOfBusiness.add("Other");

        return locationsOfBusiness;
    }

    public static List<String> getLocationsOfFailure() {
        List<String> locationsOfFailure = new ArrayList<String>();
        locationsOfFailure.add("Bristol - UK (Verizon)");
        locationsOfFailure.add("Birmingham - UK (Verizon)");
        locationsOfFailure.add("Boulder");
        locationsOfFailure.add("St. Louis");
        locationsOfFailure.add("Beaverton");
        locationsOfFailure.add("Columbus");
        locationsOfFailure.add("Dallas");
        locationsOfFailure.add("Greenville");
        locationsOfFailure.add("Gurgaon");
        locationsOfFailure.add("Hortolandia");
        locationsOfFailure.add("Los Angeles - UA (Verizon)");
        locationsOfFailure.add("London - UK (BT)");
        locationsOfFailure.add("Krakow");
        locationsOfFailure.add("Melbourne - AUS (Singtel)");
        locationsOfFailure.add("Montreal - CA (Bell Canada)");
        locationsOfFailure.add("New York - US (AT&T)");
        locationsOfFailure.add("New York - US (Tata)");
        locationsOfFailure.add("Poughkeepsie");
        locationsOfFailure.add("Raleigh");
        locationsOfFailure.add("Salem");
        locationsOfFailure.add("Sydney - AUS (Telstra)");
        locationsOfFailure.add("Tulsa");
        locationsOfFailure.add("Other");
        locationsOfFailure.add("BLR-ITPL");
        locationsOfFailure.add("BLR-MTP");
        locationsOfFailure.add("BLR-PTP");
        locationsOfFailure.add("CHD-DLF");
        locationsOfFailure.add("CHN-ALPHA");
        locationsOfFailure.add("CHN-ALPHA");
        locationsOfFailure.add("CHN-VBC");
        locationsOfFailure.add("GGN-184");
        locationsOfFailure.add("GGN-ASF");
        locationsOfFailure.add("GGN-CG");
        locationsOfFailure.add("GGN-INF");
        locationsOfFailure.add("GGN-UCP");
        locationsOfFailure.add("KOL-DLF");

        locationsOfFailure.add("KOL-MC");
        locationsOfFailure.add("KOL-UNITECH");
        locationsOfFailure.add("MUM-MAGNUS");
        locationsOfFailure.add("NOI-INFOCITY");
        locationsOfFailure.add("PUN-TPO");
        locationsOfFailure.add("VIZ-REDNAM");

        return locationsOfFailure;
    }

    public static List<String> getImpactedTowers() {
        List<String> impactedTowers = new ArrayList<String>();
        impactedTowers.add("CRM");
        impactedTowers.add("SDD");
        impactedTowers.add("FA");
        impactedTowers.add("GERS");
        impactedTowers.add("HR");
        impactedTowers.add("IPS");
        impactedTowers.add("SETERUS");
        impactedTowers.add("Insurance");
        impactedTowers.add("Internal Accounts");
        return impactedTowers;
    }


    public static boolean isNumber(final String number) {
        boolean bisNumber = false;

        if (StringUtils.isBlank(number)) {
            bisNumber = false;
        }
        try {
            Integer.parseInt(number);
            bisNumber = true;
        } catch (NumberFormatException ne) {
            try {
                Double.parseDouble(number);
                bisNumber = true;
            } catch (NumberFormatException e) {
                bisNumber = false;
            }
        }

        return bisNumber;
    }

    public static java.sql.Date convertDueDate(String dateStr) {
        Date date = null;
        try {
            DateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
            date = (Date) sdf.parse(dateStr);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return new java.sql.Date(date.getTime());
    }

    public static Integer getMonthFromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        return month;
    }

    public static Integer getYearFromDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    public static String getMonthNameFromNumber(int month) {


        if (month == 1) {
            return "January";
        }
        if (month == 2) {
            return "February";
        }
        if (month == 3) {
            return "March";
        }
        if (month == 4) {
            return "April";
        }
        if (month == 5) {
            return "May";
        }
        if (month == 6) {
            return "June";
        }
        if (month == 7) {
            return "July";
        }
        if (month == 8) {
            return "August";
        }
        if (month == 9) {
            return "September";
        }
        if (month == 10) {
            return "October";
        }
        if (month ==11) {
            return "November";
        }
        if (month == 12) {
            return "December";
        }
        return "";
    }

    public static String getCurrentDateTime(){

        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
        Date date = new Date();
        return dateFormat.format(date);
    }




}
