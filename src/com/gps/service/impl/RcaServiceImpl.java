package com.gps.service.impl;

import com.gps.dao.*;
import com.gps.exceptions.GPSException;
import com.gps.service.GpsUserService;
import com.gps.service.RcaActionNotificationService;
import com.gps.service.RcaNotificationService;
import com.gps.service.RcaService;
import com.gps.util.BluePages;
import com.gps.util.CommonUtil;
import com.gps.util.ConstantDataManager;
import com.gps.util.StringUtils;
import com.gps.vo.*;
import com.gps.vo.helper.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Waqar Malik on 23-12-2014.
 */
@Service
public class RcaServiceImpl implements RcaService {
    public static final String SEPARATOR = "_";
    private static Logger log = Logger.getLogger(RcaServiceImpl.class);

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    RcaDao rcaDao;
    @Autowired
    ContractDao contractDao;
    @Autowired
    RcaCoordinatorDao rcaCoordinatorDao;
    @Autowired
    ContractContactDao contractContactDao;
    @Autowired
    RcaActionDao rcaActionDao;
    @Autowired
    GpsDao gpsDao;
    @Autowired
    RcaTicketDao rcaTicketDao;
    @Autowired
    GpsUserService gpsUserService;
    @Autowired
    RcaCauseDao rcaCauseDao;
    @Autowired
    RcaHistoryLogDao rcaHistoryLogDao;
    @Autowired
    UserRoleDao userRoleDao;
    @Autowired
    RcaEventDetailDao rcaEventDetailDao;
    @Autowired
    RcaNotificationService rcaNotificationService;
    @Autowired
    RcaActionNotificationService rcaActionNotificationService;
    @Autowired
    RcaEditorDao rcaEditorDao;

    @Autowired
    RcaEmailNotificationSettingDao rcaEmailNotificationSettingsDao;

    @Autowired
    EmailReminderDao emailReminderDao;

    @Autowired
    RcaNotificationAuditDao rcaNotificationAuditDao;

    @Value(value = "#{'${initiate.rca.notification}'}")
    private String initiateRcaNotification;

    @Autowired
    RcaPreventionDetailDao rcaPreventionDetailDao;

    @Autowired
    RcaActionDao actionDao;

    private boolean submittedByRcaCoordinator = false;


    @Override
    public List<Rca> getAllRcas() throws GPSException {
        return rcaDao.getAllRcas();
    }

    @Override
    public Rca getRcaById(Integer rcaId) throws GPSException {
        return rcaDao.getRcaById(rcaId);
    }

    @Override
    public Set<Rca> getRcaListByMonthAndYear(int month, int year) {
        return rcaDao.getRcaListByMonthAndYear(month, year);
    }

    @Override
    public List<Rca> getRcaListByContractIds(String contractIds) {
        return rcaDao.getRcaListByContractId(contractIds);
    }

    @Override
    public Page getRcaListBySearchFilter(SearchFilter searchFilter, String sessionId) {
        String rcaListingType = null;
        List<Object> queryParameters = new ArrayList<Object>();
        String query = "FROM Rca r JOIN r.contract c JOIN c.sessionAcls acl ";
        String orderBy = " ORDER BY r.rcaId asc";
        String customQuery = "";
        StringBuilder whereClause = new StringBuilder();
        Page page = new Page();
        List<RcaListing> rcaListings = new ArrayList<RcaListing>();
        int index = 1;
        Map<String, RcaListing> rcaListingMap = new HashMap<String, RcaListing>();

        try {

            if (!StringUtils.isNullOrEmpty(searchFilter.getRcaOrActionNumber())) {
                String[] tokens = searchFilter.getRcaOrActionNumber().split(SEPARATOR);
                if (tokens != null && tokens.length > 0) {
                    rcaListingType = tokens[tokens.length - 1];
                }
            } else {
                if (StringUtils.isNotBlank(searchFilter.getFormType())) {
                    rcaListingType = searchFilter.getFormType();
                }

            }

            if (rcaListingType == null || rcaListingType.equalsIgnoreCase("rca")) {

                if (!isNullOrEmpty(searchFilter.getContract())) {
                    whereClause.append(" and c.title Like :A" + index++);
                    queryParameters.add(searchFilter.getContract() + "%");
                }

                if (!isNullOrEmpty(searchFilter.getCustomer())) {
                    Customer customer = gpsDao.getCustomerByInac(Integer.parseInt(searchFilter.getCustomer()));
                    if (customer != null) {
                        whereClause.append(" and c.customer = :A" + index++);
                        queryParameters.add(customer);
                    }
                }

                if (searchFilter.getMonth() != null) {
                    whereClause.append(" and r.month = :A" + index++);
                    queryParameters.add(searchFilter.getMonth());
                }


                if (searchFilter.getYear() != null) {
                    whereClause.append(" and r.year = :A" + index++);
                    queryParameters.add(searchFilter.getYear());
                }

                if (!StringUtils.isNullOrEmpty(searchFilter.getRcaOrActionNumber())) {
                    whereClause.append(" and r.rcaNumber = :A" + index++);
                    String[] tokens = searchFilter.getRcaOrActionNumber().split(SEPARATOR);
                    if (tokens != null && tokens.length > 0) {
                        String rcaNumber = tokens[0];
                        queryParameters.add(rcaNumber);
                    }
                }

                if (!StringUtils.isNullOrEmpty(searchFilter.getRcaCoordinator())) {
                    whereClause.append(" and r.rcaCoordinatorId = :A" + index++);
                    queryParameters.add(searchFilter.getRcaCoordinator());

                }

                if (!StringUtils.isNullOrEmpty(searchFilter.getRcaOwner())) {
                    whereClause.append(" and r.rcaOwner = :A" + index++);
                    queryParameters.add(searchFilter.getRcaOwner());

                }

                if (!StringUtils.isNullOrEmpty(searchFilter.getRcaDelegate())) {
                    whereClause.append(" and r.rcaDelegate = :A" + index++);
                    queryParameters.add(searchFilter.getRcaDelegate());

                }

                if (!StringUtils.isNullOrEmpty(searchFilter.getStage())) {
                    whereClause.append(" and r.rcaStage = :A" + index++);
                    queryParameters.add(searchFilter.getStage());

                }
                if (!StringUtils.isNullOrEmpty(searchFilter.getPrimaryTicket())) {
                    whereClause.append(" and r.problemIndidentRecord = :A" + index++);
                    queryParameters.add(searchFilter.getPrimaryTicket());
                }

                if (!isNullOrEmpty(sessionId)) {
                    whereClause.append(" and acl.formType in ('rca','RCA') and acl.sessionId = :A" + index++);
                    queryParameters.add(sessionId);
                }

                if (!isNullOrEmpty(whereClause.toString())) {
                    query = query + " WHERE 1=1 " + whereClause.toString();
                } else {

                }

                log.trace("query: " + query);
                log.debug("whereClause: {" + whereClause + "}");

                query = query + orderBy;
                customQuery = "Select r.rcaId, r.rcaNumber, r.rcaOwner, r.rcaDelegate, r.dueDate, r.createDate, r.rcaCoordinatorId, r.rcaDpeId, r.rcaStage, r.contract.title, r.problemIndidentRecord " +
                        query;

                List<Object[]> rcaList = rcaDao.getRcaListByQueryParameters(customQuery, queryParameters);
                // page.setRowCount((long) rcaList.size());
                log.debug("number of rows found = " + page.getRowCount());
                Iterator<Object[]> iter = rcaList.iterator();
                Object[] resultObject = null;
                while (iter.hasNext()) {
                    resultObject = iter.next();

                    Integer rcaId = (Integer) resultObject[0];
                    Rca rca = rcaDao.getRcaById(rcaId);
                    List<RcaAction> rcaActions = new ArrayList<RcaAction>();
                    if (rca != null) {
                        rcaActions = rca.getRcaActions();
                    }

                    // load rcaListing
                    if (StringUtils.isNullOrEmpty(searchFilter.getFormType()) || searchFilter.getFormType().equalsIgnoreCase("rca")) {
                        RcaListing rcaListing = buildRcaListing(resultObject, rca);
                        // adding RCAs
                        if (StringUtils.isNullOrEmpty(searchFilter.getPrimaryTicket())) {
                            if (!rcaListingMap.containsKey(rcaListing.getRcaOrActionNumber())) {
                                rcaListings.add(rcaListing);
                                rcaListingMap.put(rcaListing.getRcaOrActionNumber(), rcaListing);
                            }
                        } else if (searchFilter.getPrimaryTicket().equalsIgnoreCase(rcaListing.getPrimaryTicket())) {
                            if (!rcaListingMap.containsKey(rcaListing.getRcaOrActionNumber())) {
                                rcaListings.add(rcaListing);
                                rcaListingMap.put(rcaListing.getRcaOrActionNumber(), rcaListing);
                            }
                        }
                    }

                    // adding RCA Actions
                    if ((StringUtils.isNullOrEmpty(rcaListingType) || !rcaListingType.equalsIgnoreCase("rca")) &&
                            (StringUtils.isNullOrEmpty(searchFilter.getFormType()) || searchFilter.getFormType().equalsIgnoreCase("action"))) {
                        loadRcaActions(rcaActions, rcaListings, searchFilter, rcaListingMap);
                    }
                }

            }

            if (org.apache.commons.lang.StringUtils.isNotBlank(rcaListingType) && rcaListingType.equalsIgnoreCase("action")) {

                index = 1;
                query = "FROM RcaAction a JOIN a.rca r JOIN r.contract c JOIN c.sessionAcls acl";

                if (!isNullOrEmpty(searchFilter.getContract())) {
                    whereClause.append(" and c.title Like :A" + index++);
                    queryParameters.add(searchFilter.getContract() + "%");
                }


                if (searchFilter.getStartDate() != null || searchFilter.getEndDate() != null) {
                    if (searchFilter.getStartDate() != null) {
                        whereClause.append(" and r.incidentStartTime >= :A" + index++);
                        queryParameters.add(searchFilter.getStartDate());
                    }
                    if (searchFilter.getEndDate() != null) {
                        whereClause.append(" and r.incidentEntTime <= :A" + index++);
                        queryParameters.add(searchFilter.getEndDate());
                    }
                }

                if (searchFilter.getStartDate() == null && searchFilter.getEndDate() == null) {
                    if (searchFilter.getMonth() != null) {
                        whereClause.append(" and r.month = :A" + index++);
                        queryParameters.add(searchFilter.getMonth());
                    }

                    if (searchFilter.getYear() != null) {
                        whereClause.append(" and r.year = :A" + index++);
                        queryParameters.add(searchFilter.getYear());
                    }
                }

                if (!StringUtils.isNullOrEmpty(searchFilter.getRcaOrActionNumber())) {
                    whereClause.append(" and a.actionNumber = :A" + index++);
                    String[] tokens = searchFilter.getRcaOrActionNumber().split(ConstantDataManager.SEPARATOR);
                    if (tokens != null && tokens.length > 0) {
                        String actionNumber = tokens[0];
                        queryParameters.add(actionNumber);
                    }
                }

                if (!StringUtils.isNullOrEmpty(searchFilter.getRcaCoordinator())) {
                    whereClause.append(" and r.rcaCoordinatorId = :A" + index++);
                    queryParameters.add(searchFilter.getRcaCoordinator());

                }

                if (!StringUtils.isNullOrEmpty(searchFilter.getRcaOwner())) {
                    whereClause.append(" and r.rcaOwner = :A" + index++);
                    queryParameters.add(BluePages.getIntranetIdFromNotesId(searchFilter.getRcaOwner()));

                }

                if (!StringUtils.isNullOrEmpty(searchFilter.getRcaDelegate())) {
                    whereClause.append(" and r.rcaDelegate = :A" + index++);
                    queryParameters.add(BluePages.getIntranetIdFromNotesId(searchFilter.getRcaDelegate()));

                }

                if (!StringUtils.isNullOrEmpty(searchFilter.getStage())) {
                    whereClause.append(" and a.actionStatus = :A" + index++);
                    queryParameters.add(searchFilter.getStage());

                }


                if (!isNullOrEmpty(sessionId)) {
                    whereClause.append(" and acl.formType in ('rca','RCA') and acl.sessionId = :A" + index++);
                    queryParameters.add(sessionId);
                }

                if (!isNullOrEmpty(whereClause.toString())) {
                    query = query + " WHERE 1=1 " + whereClause.toString();
                } else {

                }

                log.trace("query: " + query);
                log.debug("whereClause: {" + whereClause + "}");

                orderBy = " ORDER BY a.rcaActionId asc";
                query = query + orderBy;

                customQuery = "Select a.rcaActionId, a.actionNumber, r.rcaOwner, r.rcaDelegate, r.dueDate, r.createDate, r.rcaCoordinatorId, r.rcaDpeId, a.actionStatus, r.contract.title " +
                        query;

                List<Object[]> actionList = actionDao.getActionListByQueryParameters(customQuery, queryParameters);
                page.setRowCount((long) actionList.size());
                log.debug("number of rows found = " + page.getRowCount());
                Iterator<Object[]> iter = actionList.iterator();
                Object[] resultObject = null;
                while (iter.hasNext()) {
                    resultObject = iter.next();
                    RcaListing rcaListing = buildActionListing(resultObject);

                    if (!rcaListingMap.containsKey(rcaListing.getRcaOrActionNumber())) {
                        rcaListings.add(rcaListing);
                        rcaListingMap.put(rcaListing.getRcaOrActionNumber(), rcaListing);
                    }

                }
            }

            page.setDataList(rcaListings);
            page.setRowCount((long) rcaListings.size());

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return page;

    }

    private RcaListing buildActionListing(Object[] resultObject) {

        RcaListing rcaListing = new RcaListing();


        rcaListing.setRcaOrActionNumber((String) resultObject[1]);
        rcaListing.setRcaOwner((String) resultObject[2]);
        rcaListing.setRcaDelegate((String) resultObject[3]);
        rcaListing.setRcaDueDate((resultObject[4] != null ? getFormattedDate((Date) resultObject[4], "dd-MM-yyyy") : ""));
        rcaListing.setRcaCreateDate((resultObject[5] != null ? getFormattedDate((Date) resultObject[5], "dd-MM-yyyy") : ""));
        rcaListing.setListingType("action");
        String rcaCoordinatorId = ((String) resultObject[6]);
        rcaListing.setRcaCoordinator(rcaCoordinatorId);
        Integer rcaDpeId = ((Integer) resultObject[7]);
        if (rcaDpeId != null && rcaDpeId > 0) {
            ContractContact rcaDpe = contractContactDao.getContractContactById(rcaDpeId);
            rcaListing.setRcaDpe(rcaDpe != null ? rcaDpe.getContactName() : "");
        }
        rcaListing.setRcaStage((String) resultObject[8]);
        rcaListing.setRcaAccountTitle((String) resultObject[9]);


        return rcaListing;
    }

    private RcaListing buildRcaListing(Object[] resultObject, Rca rca) {
        RcaListing rcaListing = new RcaListing();
        if (rca != null) {
            rcaListing.setRcaId(rca.getRcaId());
            rcaListing.setRcaOrActionNumber((String) resultObject[1]);
            String rcaOwner = (String) resultObject[2];
            rcaListing.setRcaOwner(StringUtils.isNotBlank(rcaOwner) && !BluePages.isNotesID(rcaOwner) ? BluePages.getNotesIdFromIntranetId(rcaOwner) : "");
            String rcaDelegate = (String) resultObject[3];
            rcaListing.setRcaDelegate(StringUtils.isNotBlank(rcaDelegate) && !BluePages.isNotesID(rcaDelegate) ? BluePages.getNotesIdFromIntranetId(rcaDelegate) : "");
            rcaListing.setRcaDueDate((resultObject[4] != null ? getFormattedDate((Date) resultObject[4], "dd-MM-yyyy") : ""));
            rcaListing.setRcaCreateDate((resultObject[5] != null ? getFormattedDate((Date) resultObject[5], "dd-MM-yyyy") : ""));
            rcaListing.setListingType("rca");
            String rcaCoordinatorIntranetId = ((String) resultObject[6]);
            rcaListing.setRcaCoordinator(StringUtils.isNotBlank(rcaCoordinatorIntranetId) && !BluePages.isNotesID(rcaCoordinatorIntranetId) ? BluePages.getNotesIdFromIntranetId(rcaCoordinatorIntranetId) : "");

            Integer rcaDpeId = ((Integer) resultObject[7]);
            if (rcaDpeId != null && rcaDpeId > 0) {
                GpsUser rcaDpe = gpsUserService.getUserById(rcaDpeId);
                rcaListing.setRcaDpe(rcaDpe != null ? BluePages.getNotesIdFromIntranetId(rcaDpe.getEmail()) : "");
            }
            rcaListing.setRcaStage((String) resultObject[8]);
            rcaListing.setRcaAccountTitle((String) resultObject[9]);
            rcaListing.setPrimaryTicket((String) resultObject[10]);
        }

        return rcaListing;
    }

    @Override
    public List<RcaListing> getAllRcasAndActions() {
        List rcaAndActionList = new ArrayList();

        List<Rca> rcaList = rcaDao.getAllRcas();
        for (Rca rca : rcaList) {
            RcaListing rcaListing = new RcaListing();
            rcaListing.setRcaOrActionNumber(rca.getRcaNumber());
            rcaListing.setNumberAndType(rca.getRcaNumber() + SEPARATOR + "rca");
            rcaListing.setListingType("rca");
            rcaAndActionList.add(rcaListing);
            if (CollectionUtils.isNotEmpty(rca.getRcaActions())) {
                for (RcaAction rcaAction : rca.getRcaActions()) {
                    RcaListing rcaActionListing = new RcaListing();
                    rcaActionListing.setRcaOrActionNumber(rcaAction.getActionNumber());
                    rcaActionListing.setNumberAndType(rcaAction.getActionNumber() + SEPARATOR + "action");
                    rcaActionListing.setListingType("action");
                    rcaAndActionList.add(rcaActionListing);
                }
            }
        }

        return rcaAndActionList;

    }

    @Override
    public List<RcaListing> getRcasAndActionsByMonthAndYear(int month, int year) {
        List rcaAndActionList = new ArrayList();
        List<Rca> rcaList = new ArrayList<Rca>(rcaDao.getRcaListByMonthAndYear(month, year));
        for (Rca rca : rcaList) {
            RcaListing rcaListing = new RcaListing();
            rcaListing.setRcaOrActionNumber(rca.getRcaNumber());
            rcaListing.setNumberAndType(rca.getRcaNumber() + SEPARATOR + "rca");
            rcaListing.setListingType("rca");
            rcaListing.setPrimaryTicket(rca.getProblemIndidentRecord());
            rcaAndActionList.add(rcaListing);
            if (CollectionUtils.isNotEmpty(rca.getRcaActions())) {
                for (RcaAction rcaAction : rca.getRcaActions()) {
                    RcaListing rcaActionListing = new RcaListing();
                    rcaActionListing.setRcaOrActionNumber(rcaAction.getActionNumber());
                    rcaActionListing.setNumberAndType(rcaAction.getActionNumber() + SEPARATOR + "action");
                    rcaActionListing.setListingType("action");
                    rcaAndActionList.add(rcaActionListing);
                }
            }
        }

        return rcaAndActionList;
    }

    @Override
    public List<RcaListing> getRcasAndActionsByOutageDates(Timestamp startDate, Timestamp endDate) {
        List rcaAndActionList = new ArrayList();
        List<Rca> rcaList = new ArrayList<Rca>(rcaDao.getRcaListByOutageDates(startDate, endDate));
        for (Rca rca : rcaList) {
            RcaListing rcaListing = new RcaListing();
            rcaListing.setRcaOrActionNumber(rca.getRcaNumber());
            rcaListing.setNumberAndType(rca.getRcaNumber() + SEPARATOR + "rca");
            rcaListing.setListingType("rca");
            rcaAndActionList.add(rcaListing);
            if (CollectionUtils.isNotEmpty(rca.getRcaActions())) {
                for (RcaAction rcaAction : rca.getRcaActions()) {
                    RcaListing rcaActionListing = new RcaListing();
                    rcaActionListing.setRcaOrActionNumber(rcaAction.getActionNumber());
                    rcaActionListing.setNumberAndType(rcaAction.getActionNumber() + SEPARATOR + "action");
                    rcaActionListing.setListingType("action");
                    rcaAndActionList.add(rcaActionListing);
                }
            }
        }

        return rcaAndActionList;
    }

    @Override
    public List<RcaCoordinator> getAllRcaCoordinators() {
        return rcaCoordinatorDao.getAllRcaCoordinators();
    }

    @Override
    public List<RcaCoordinator> getRcaCoordinatorsByContractId(Integer contractId) {
        return rcaCoordinatorDao.getRcaCoordinatorByContractId(contractId);
    }

    @Override
    @Transactional
    public String initiateRca(InitiateRcaForm initiateRcaForm, String email) throws GPSException, MessagingException {
        String generatedRcaNumber = null;
        log.info("initiateRca() " + initiateRcaForm.getRcaContract());
        boolean isRcaInitiated = true;

        //set month and year
        Calendar calendar = Calendar.getInstance();
        Integer month = !StringUtils.isNullOrEmpty(initiateRcaForm.getRcaMonth()) ? Integer.parseInt(initiateRcaForm.getRcaMonth()) : calendar.get(Calendar.MONTH) + 1;
        Integer year = !StringUtils.isNullOrEmpty(initiateRcaForm.getRcaYear()) ? Integer.parseInt(initiateRcaForm.getRcaYear()) : calendar.get(Calendar.YEAR);

        Set<Rca> monthlyRcas = rcaDao.getRcaListByMonthAndYear(month, year);
        Integer count = CollectionUtils.isNotEmpty(monthlyRcas) ? monthlyRcas.size() : 0;
        Rca rca = new Rca();
        if (count != null) {
            try {
                GpsUser rcaUser = processGpsUser(email);
                rca.setCreatedBy(rcaUser.getUserId());
                rca.setUpdatedBy(rcaUser.getUserId());
                rca.setCreateDate(new Timestamp((new Date()).getTime()));
                rca.setUpdatedOn(new Timestamp((new Date()).getTime()));
                rca.setRcaStage("Open");
                rca.setIsOwnerAccepted("N");
                rca.setIsDelegateAccepted("N");
                rca.setActionItemOpen(0);
                rca.setActionItemClosed(0);
                rca.setMonth(month);
                rca.setYear(year);

                Contract contract = new Contract();
                contract.setContractId(Integer.parseInt(initiateRcaForm.getRcaContract()));
                Contract dbContract = contractDao.getContractById(Integer.parseInt(initiateRcaForm.getRcaContract()));
                rca.setContract(dbContract);
               /* if (dbContract.getCustomer() != null) {
                    rca.setCustomerImpacted(dbContract.getCustomer().getName());
                }*/

                //generate RCA Number
                generatedRcaNumber = generateRcaNumber(dbContract, count, month, year);
                rca.setRcaNumber(generatedRcaNumber);
                initiateRcaForm.setRcaNumber(rca.getRcaNumber());

                //set rca coordinator
                if (!StringUtils.isNullOrEmpty(initiateRcaForm.getRcaCoordinator())) {
                    rca.setRcaCoordinatorId(initiateRcaForm.getRcaCoordinator());
                }

                rcaDao.createRca(rca);
                log.info("succesfully saved rca: " + rca.getRcaId());

                //create rca event
                RcaEventDetail rcaEventDetail = new RcaEventDetail();
                rcaEventDetail.setRcaId(rca.getRcaId());
                rcaEventDetail.setRca(rca);
                rcaEventDetail.setRcaCoordinatorId(rca.getRcaCoordinatorId());
                rcaEventDetailDao.addRcaEventDetail(rcaEventDetail);


                // create rca prevention details
                RcaPreventionDetail rcaPreventionDetail = new RcaPreventionDetail();
                rcaPreventionDetail.setRcaId(rca.getRcaId());
                rcaPreventionDetail.setRca(rca);
                rcaPreventionDetailDao.add(rcaPreventionDetail);

                // create rca primary cause
                RcaCause rcaCause = new RcaCause();
                rcaCause.setRca(rca);
                rcaCause.setIsPrimary("Y");
                rcaCauseDao.addRcaCause(rcaCause);

                //create rca history log
                RcaHistoryLog rcaHistoryLog = new RcaHistoryLog();
                rcaHistoryLog.setRca(rca);
                rcaHistoryLog.setSubmittedBy(rcaUser);
                rcaHistoryLog.setSubmittedOn(rca.getCreateDate());
                rcaHistoryLog.setFormAction("Created");
                List<UserRole> userRoles = userRoleDao.getUserRolesByContractIdAndUserId(rca.getContract().getContractId(), rcaUser.getUserId());
                if (CollectionUtils.isNotEmpty(userRoles)) {
                    rcaHistoryLog.setRole(userRoles.get(0).getRole());
                }
                rcaHistoryLogDao.addRcaHistoryLog(rcaHistoryLog);

                //create rca notification audit entry
                RcaNotificationAudit rcaNotificationAudit = new RcaNotificationAudit();
                rcaNotificationAudit.setRca(rca);
                rcaNotificationAudit.setRcaId(rca.getRcaId());
                rcaNotificationAudit.setIsOwnerNotificationSent("N");
                rcaNotificationAudit.setIsDelegateNotificationSent("N");
                rcaNotificationService.saveOrUpdateNotificationAuditDetails(rcaNotificationAudit);

                if (isRcaInitiated) {
                    //send notification
                    sendInitiateRcaNotification(initiateRcaForm, rca);
                }

            } catch (Exception e) {
                isRcaInitiated = false;
                log.error("Exception: " + e.getMessage(), e);
            }


        }
        return generatedRcaNumber;
    }

    private void sendInitiateRcaNotification(InitiateRcaForm initiateRcaForm, Rca rca) throws MessagingException {
        RcaEmailNotificationSetting emailNotificationSetting = rcaEmailNotificationSettingsDao.getRcaEmailNotificationSettingByContractId(Integer.parseInt(initiateRcaForm.getRcaContract()));
        if (emailNotificationSetting != null && emailNotificationSetting.getRcaWorkflowNotificationEnabled().equalsIgnoreCase(ConstantDataManager.YES)
                && emailNotificationSetting.getNewRcaNotificationToCoordinator().equalsIgnoreCase(ConstantDataManager.YES)) {
            rcaNotificationService.sendRcaInitiatedNotification(rca);
        }
    }

    @Override
    @Transactional
    public Rca saveRca(RcaForm rcaForm, String stage, String formAction, RcaEmailNotificationSetting emailNotificationSetting) throws GPSException {
        Rca dbRca = null;
        try {

            // handle newly created actions
            handleNewlyCreatedActions(rcaForm);
            // save RCA
            dbRca = saveRcaDetails(rcaForm, stage, emailNotificationSetting);
            //save Rca Event Details
            saveRcaEventDetails(rcaForm);
            // save Rca History Log
            saveRcaHistoryLog(rcaForm, dbRca, formAction);
            //save secondary tickets
            saveRcaTickets(rcaForm);
            //save primary and contributing causes
            savePrimaryAndContCauses(rcaForm);
            // save rca actions
            saveRcaActions(rcaForm, emailNotificationSetting);
            //save rca editors
            saveRcaEditors(rcaForm, dbRca);
            // save rca prevention details
            saveRcaPreventionDetails(rcaForm.getRca(), dbRca);

        } catch (Exception e) {
            log.debug(e.getMessage(), e);
        }
        return dbRca;
    }


    @Override
    public Rca submitRca(RcaForm rcaForm) throws Exception {
        Rca dbRca = null;
        try {
            Rca rca = rcaDao.getRcaById(rcaForm.getRca().getRcaId());
            //  check if submitted by Rca Coordinator
            if (rcaForm.getLoggedInUser() != null && StringUtils.isNotBlank(rca.getRcaCoordinatorId())
                    && rcaForm.getLoggedInUser().getEmail().equalsIgnoreCase(rca.getRcaCoordinatorId())) {
                submittedByRcaCoordinator = true;
            }

            // save Rca
            dbRca = saveRca(rcaForm, "Awaiting", "Submitted", null);

            // Delete RCA NOT Submitted Reminder
            EmailReminder emailReminder = emailReminderDao.findByQuestionnaireIdAndReminderType(rcaForm.getRca().getRcaId(), ConstantDataManager.RCA_NOT_SUBMITTED_REMINDER);
            if (emailReminder != null) {
                emailReminderDao.deleteEmailReminder(emailReminder);
            }

            // add Rca Not Approved Reminder
            addRcaNotApprovedReminder(dbRca);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return dbRca;
    }

    @Override
    public Rca approveRca(Integer rcaId, String approvalComments, GpsUser loggedInUser, String roles) {
        Rca dbRca = getRcaById(rcaId);
        if (dbRca != null) {
            dbRca.setRcaStage("Approved");
            dbRca.setUpdatedBy(loggedInUser.getUserId());
            dbRca.setUpdatedOn(new Timestamp(new Date().getTime()));
            rcaDao.updateRca(dbRca);

            RcaHistoryLog rcaHistoryLog = new RcaHistoryLog();
            rcaHistoryLog.setFormAction("Approved");
            rcaHistoryLog.setComments(approvalComments);
            rcaHistoryLog.setRca(dbRca);
            rcaHistoryLog.setSubmittedBy(loggedInUser);
            rcaHistoryLog.setSubmittedOn(new Timestamp(new Date().getTime()));
            rcaHistoryLog.setRole(configureHistoryRole(loggedInUser, roles, dbRca));
            rcaHistoryLogDao.addRcaHistoryLog(rcaHistoryLog);

            // Add Email Reminder for Not closed RCA//
            addRcaNotClosedReminder(rcaId, dbRca);

            // Delete Rca Not Approved Reminder if exits
            deleteEmailReminder(rcaId, ConstantDataManager.RCA_NOT_APPROVED_REMINDER);

        }
        return dbRca;
    }


    @Override
    public Rca rejectRca(Integer rcaId, String rejectionComments, GpsUser loggedInUser, String roles) {
        Rca dbRca = getRcaById(rcaId);

        if (dbRca != null) {
            dbRca.setRcaStage("Active");
            dbRca.setUpdatedBy(loggedInUser.getUserId());
            dbRca.setUpdatedOn(new Timestamp(new Date().getTime()));
            dbRca.setHasCoordinatorApproved("N");
            rcaDao.updateRca(dbRca);

            RcaHistoryLog rcaHistoryLog = new RcaHistoryLog();
            rcaHistoryLog.setFormAction("Rejected");
            rcaHistoryLog.setComments(rejectionComments);
            rcaHistoryLog.setRca(dbRca);
            rcaHistoryLog.setSubmittedBy(loggedInUser);
            rcaHistoryLog.setSubmittedOn(new Timestamp(new Date().getTime()));
            rcaHistoryLog.setRole(configureHistoryRole(loggedInUser, roles, dbRca));
            rcaHistoryLogDao.addRcaHistoryLog(rcaHistoryLog);

        }
        return dbRca;
    }

    @Override
    public Rca cancelRca(Integer rcaId, GpsUser loggedInUser, String loggedInUserRoles, String cancellationComments) {
        Rca dbRca = getRcaById(rcaId);

        if (dbRca != null) {
            dbRca.setRcaStage("Cancelled");
            dbRca.setUpdatedBy(loggedInUser.getUserId());
            dbRca.setUpdatedOn(new Timestamp(new Date().getTime()));
            rcaDao.updateRca(dbRca);

            RcaHistoryLog rcaHistoryLog = new RcaHistoryLog();
            rcaHistoryLog.setFormAction("Cancelled");
            rcaHistoryLog.setRca(dbRca);
            rcaHistoryLog.setComments(cancellationComments);
            rcaHistoryLog.setSubmittedBy(loggedInUser);
            rcaHistoryLog.setSubmittedOn(new Timestamp(new Date().getTime()));
            rcaHistoryLog.setRole(configureHistoryRole(loggedInUser, loggedInUserRoles, dbRca));
            rcaHistoryLogDao.addRcaHistoryLog(rcaHistoryLog);

        }
        return dbRca;
    }

    @Override
    public Rca closeRca(RcaForm rcaForm, String rcaStage, String formAction) {
        // save Rca
        Rca dbRca = saveRca(rcaForm, rcaStage, formAction, null);

        // closing Rca State
        if (dbRca != null) {
            dbRca.setRcaStage(rcaStage);
            rcaDao.updateRca(dbRca);
        }

        // delete RCA Not Closed Reminder if exists
        deleteEmailReminder(dbRca.getRcaId(), ConstantDataManager.RCA_NOT_CLOSED_REMINDER);

        return dbRca;
    }

    @Override
    public Rca reopenRca(RcaForm rcaForm, String reopenComments, GpsUser loggedInUser, String roles) {
        // update rca
        Rca dbRca = getRcaById(rcaForm.getRca().getRcaId());
        try {
            if (dbRca != null) {
                dbRca.setHasCoordinatorApproved("N");
                dbRca.setRcaStage("Active");
                rcaDao.updateRca(dbRca);
            }
            //updating the notification audits
            RcaNotificationAudit audit = rcaNotificationAuditDao.getNotificationAuditByRcaId(dbRca.getRcaId());
            audit.setIsCoordinatorApprovalRequestSent("N");
            audit.setCoordinatorApprovalRequestSentDate(null);
            audit.setIsDpeApprovalRequestSent("N");
            audit.setDpeApprovalRequestSentDate(null);
            rcaNotificationAuditDao.saveOrUpdateRcaNotificationAuditDetails(audit);

            // update history
            RcaHistoryLog rcaHistoryLog = new RcaHistoryLog();
            rcaHistoryLog.setFormAction("Reopened");
            rcaHistoryLog.setComments(reopenComments);
            rcaHistoryLog.setRca(dbRca);
            rcaHistoryLog.setSubmittedBy(loggedInUser);
            rcaHistoryLog.setSubmittedOn(new Timestamp(new Date().getTime()));
            rcaHistoryLog.setRole(configureHistoryRole(loggedInUser, roles, dbRca));
            rcaHistoryLogDao.addRcaHistoryLog(rcaHistoryLog);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return dbRca;

    }


    private String configureHistoryRole(GpsUser loggedInUser, String roles, Rca dbRca) {
        String historyRole = "";

        if (StringUtils.isNullOrEmpty(dbRca.getRcaCoordinatorId()) && loggedInUser.getUserId() == Integer.parseInt(dbRca.getRcaCoordinatorId())) {
            historyRole = "Coordinator";
        } else if (org.apache.commons.lang.StringUtils.contains(roles, "rcaDpe")) {
            historyRole = "Dpe";
        } else if (org.apache.commons.lang.StringUtils.contains(roles, "rcaOwner")) {
            historyRole = "Owner";
        } else if (org.apache.commons.lang.StringUtils.contains(roles, "rcaDelegate")) {
            historyRole = "Delegate";
        }
        return historyRole;
    }

    private void saveRcaActions(RcaForm rcaForm, RcaEmailNotificationSetting emailNotificationSetting) {
        if (CollectionUtils.isNotEmpty(rcaForm.getRcaActions())) {
            for (RcaAction rcaAction : rcaForm.getRcaActions()) {
                RcaAction dbRcaAction = rcaActionDao.getRcaActionById(rcaAction.getRcaActionId());
                dbRcaAction.setActionDesc(rcaAction.getActionDesc());
                dbRcaAction.setAssignedTo(rcaAction.getAssignedTo());
                dbRcaAction.setTargetDate(StringUtils.isNullOrEmpty(rcaAction.getActionTargetDate()) ? null : CommonUtil.convertToDate(rcaAction.getActionTargetDate()));
                dbRcaAction.setUpdatedOn(new Timestamp(new Date().getTime()));
                rcaActionDao.updateRcaAction(dbRcaAction);
                if (StringUtils.isNotBlank(rcaAction.getActionTargetDate()) && StringUtils.isNotBlank(rcaAction.getAssignedTo())) {
                    // add RCA Action item Not Closed Reminder
                    addActionNotClosedReminder(rcaForm, dbRcaAction, emailNotificationSetting);
                }

            }
        }
    }

    private Rca saveRcaDetails(RcaForm rcaForm, String stage, RcaEmailNotificationSetting emailNotificationSetting) {
        //process owner and delegate
        if (BluePages.isNotesID(rcaForm.getRca().getRcaOwner())) {
            rcaForm.getRca().setRcaOwner(BluePages.getIntranetIdFromNotesId(rcaForm.getRca().getRcaOwner()));
        }
        if (BluePages.isNotesID(rcaForm.getRca().getRcaDelegate())) {
            rcaForm.getRca().setRcaDelegate(BluePages.getIntranetIdFromNotesId(rcaForm.getRca().getRcaDelegate()));
        }

        Rca dbRca = rcaDao.getRcaById(rcaForm.getRca().getRcaId());
        try {
            if (dbRca != null) {

                dbRca.setTitle(rcaForm.getRca().getTitle().trim());
                dbRca.setDueDate(StringUtils.isBlank(rcaForm.getRcaDueDate()) ? null : CommonUtil.convertDueDate(rcaForm.getRcaDueDate()));
                dbRca.setDueDateModificationReason(StringUtils.isNotBlank(rcaForm.getRca().getDueDateModificationReason()) ? rcaForm.getRca().getDueDateModificationReason() : "");
                dbRca.setRcaAssociatedWith(StringUtils.isNotBlank(rcaForm.getRca().getRcaAssociatedWith()) ? rcaForm.getRca().getRcaAssociatedWith() : "");
                dbRca.setListOfSlaSloImpacted(StringUtils.isNotBlank(rcaForm.getRca().getListOfSlaSloImpacted()) ? rcaForm.getRca().getListOfSlaSloImpacted() : "");
                dbRca.setIncidentStartTime(StringUtils.isNullOrEmpty(rcaForm.getRcaFormDateHelper().getIncidentStartDateTime()) ? null : CommonUtil.convertToTimestamp(rcaForm.getRcaFormDateHelper().getIncidentStartDateTime()));
                dbRca.setIncidentEntTime(StringUtils.isNullOrEmpty(rcaForm.getRcaFormDateHelper().getIncidentEndDateTime()) ? null : CommonUtil.convertToTimestamp(rcaForm.getRcaFormDateHelper().getIncidentEndDateTime()));
                dbRca.setRcaDpeId(rcaForm.getRca().getRcaDpeId() != 0 || rcaForm.getRca().getRcaDpeId() == null ? rcaForm.getRca().getRcaDpeId() : null);
                dbRca.setProblemIndidentRecord(rcaForm.getRca().getProblemIndidentRecord().trim());
                dbRca.setSeverity(rcaForm.getRca().getSeverity());
                dbRca.setPrimaryTicketType(rcaForm.getRca().getPrimaryTicketType());
                dbRca.setPrimaryTicketExists(rcaForm.getRca().getPrimaryTicketExists());
                dbRca.setPrimaryTicketAssociation(rcaForm.getRca().getPrimaryTicketAssociation());
                handleOtherAssocicatedTool(rcaForm);
                dbRca.setPriOtherAssociatedTool(rcaForm.getRca().getPriOtherAssociatedTool());
                dbRca.setNoPrimaryTicketExplanation(rcaForm.getRca().getNoPrimaryTicketExplanation());
                dbRca.setImpactDetails(rcaForm.getRca().getImpactDetails());
                dbRca.setServiceImpacted(rcaForm.getRca().getServiceImpacted());
                dbRca.setSystemImpacted(rcaForm.getRca().getSystemImpacted());
                dbRca.setBusinessImpactInMins(rcaForm.getRca().getBusinessImpactInMins());
                dbRca.setTotalGpsPractitionersImpacted(rcaForm.getRca().getTotalGpsPractitionersImpacted());
                dbRca.setSourceNotification(rcaForm.getRca().getSourceNotification());
                dbRca.setLocationOfFailure(rcaForm.getRca().getLocationOfFailure());
                dbRca.setRcaReason(rcaForm.getRca().getRcaReason());
                dbRca.setApprovalsOrReviews(rcaForm.getRca().getApprovalsOrReviews());
                dbRca.setCustomerImpacted(rcaForm.getRca().getCustomerImpacted());
                dbRca.setCustomerImpactedList(rcaForm.getRca().getCustomerImpactedList());
                dbRca.setNotesAndEscalations(rcaForm.getRca().getNotesAndEscalations());
                dbRca.setUpdatedBy(rcaForm.getLoggedInUser().getUserId());
                dbRca.setUpdatedOn(new Timestamp(new Date().getTime()));
                dbRca.setIsOwnerAccepted(rcaForm.getRca().getIsOwnerAccepted().trim());
                dbRca.setIsDelegateAccepted(rcaForm.getRca().getIsDelegateAccepted().trim());
                dbRca.setRcaOwner(rcaForm.getRca().getRcaOwner());
                dbRca.setRcaDelegate(rcaForm.getRca().getRcaDelegate());
                dbRca.setRcaOpeningReason(rcaForm.getRca().getRcaOpeningReason());
                dbRca.setFailedChangeCriteria(rcaForm.getRca().getFailedChangeCriteria());
                dbRca.setFailedChangeImpactLevel(rcaForm.getRca().getFailedChangeImpactLevel());
                dbRca.setFailedChangeAssignee(rcaForm.getRca().getFailedChangeAssignee());
                dbRca.setFailedChangeAssigneeGroup(rcaForm.getRca().getFailedChangeAssigneeGroup());
                dbRca.setCustomerOther(rcaForm.getRca().getCustomerOther());
                dbRca.setManagedBy(rcaForm.getRca().getManagedBy());
                //  dbRca.setOtherReasonDetail(rcaForm.getRca().getOtherReasonDetail());


                if (rcaForm.getRca().getRcaStage().equalsIgnoreCase(ConstantDataManager.AWAITING_STAGE) && (StringUtils.isBlank(dbRca.getHasCoordinatorApproved()) ||
                        dbRca.getHasCoordinatorApproved().equalsIgnoreCase("N")) || (StringUtils.isBlank(rcaForm.getRca().getHasCoordinatorApproved()) ||
                        rcaForm.getRca().getHasCoordinatorApproved().equalsIgnoreCase("Y"))) {
                    dbRca.setHasCoordinatorApproved(rcaForm.getRca().getHasCoordinatorApproved());

                    // send Dpe Approval
                    if (submittedByRcaCoordinator && dbRca.getHasCoordinatorApproved().equalsIgnoreCase("Y")) {
                        sendDpeApproval(dbRca);
                    }
                }

                if ((StringUtils.isNotBlank(rcaForm.getRca().getIsDelegateAccepted().trim()) && rcaForm.getRca().getIsDelegateAccepted().equalsIgnoreCase("Y"))
                        || (StringUtils.isNotBlank(dbRca.getIsOwnerAccepted().trim()) && rcaForm.getRca().getIsOwnerAccepted().equalsIgnoreCase("Y"))) {
                    // Delete the RCA Not Accepted Reminder if exists
                    EmailReminder emailReminder = emailReminderDao.findByQuestionnaireIdAndReminderType(rcaForm.getRca().getRcaId(), ConstantDataManager.RCA_NOT_ACCEPTED_BY_OWNER_REMINDER);
                    if (emailReminder != null) {
                        emailReminderDao.deleteEmailReminder(emailReminder);
                    }

                    //Add RCA Not submitted Reminder
                    addRcaNotSubmittedReminder(rcaForm, emailNotificationSetting);

                    if (stage.equalsIgnoreCase("Open")) {
                        stage = "Active";
                    }
                }

                if (!dbRca.getRcaStage().equalsIgnoreCase(ConstantDataManager.AWAITING_STAGE) && !dbRca.getRcaStage().equalsIgnoreCase(ConstantDataManager.APPROVED_STAGE)
                        && !dbRca.getRcaStage().equalsIgnoreCase(ConstantDataManager.CLOSED_STAGE)) {
                    dbRca.setRcaStage(stage);
                }


                if (stage.equalsIgnoreCase("Closed")) {
                    dbRca.setCloseDate(new Date());
                }
                rcaDao.updateRca(dbRca);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return dbRca;
    }

    private void handleOtherAssocicatedTool(RcaForm rcaForm) {
        if(!rcaForm.getRca().getPrimaryTicketAssociation().equalsIgnoreCase("Other")){
            rcaForm.getRca().setPriOtherAssociatedTool("");
        }
    }

    private void sendDpeApproval(Rca dbRca) {
        try {
            //Send RCA Coordinator Approved Email to DPE
            rcaNotificationService.sendDpeApprovalRequestNotification(dbRca);

            //update notification audit
            RcaNotificationAudit audit = rcaNotificationAuditDao.getNotificationAuditByRcaId(dbRca.getRcaId());
            if (audit != null) {
                audit.setIsDpeApprovalRequestSent("Y");
                audit.setDpeApprovalRequestSentDate(new Timestamp(new Date().getTime()));
                rcaNotificationAuditDao.saveOrUpdateRcaNotificationAuditDetails(audit);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void addRcaNotSubmittedReminder(RcaForm rcaForm, RcaEmailNotificationSetting emailNotificationSetting) {

        try {
            RcaNotificationAudit rcaNotificationAudit = rcaNotificationAuditDao.getNotificationAuditByRcaId(rcaForm.getRca().getRcaId());
            EmailReminder rcaNotSubmittedReminder = emailReminderDao.findByQuestionnaireIdAndReminderType(rcaForm.getRca().getRcaId(), ConstantDataManager.RCA_NOT_SUBMITTED_REMINDER);
            if (rcaNotSubmittedReminder == null && rcaNotificationAudit != null) {
                rcaNotSubmittedReminder = new EmailReminder();
                rcaNotSubmittedReminder.setQuestionnaireId(rcaForm.getRca().getRcaId().toString());
                rcaNotSubmittedReminder.setQuestionnaireType(ConstantDataManager.RCA_FORM_TYPE);

                if (org.apache.commons.lang.StringUtils.isNotBlank(rcaForm.getRca().getRcaOwner()) && org.apache.commons.lang.StringUtils.isNotBlank(rcaForm.getRca().getRcaDelegate())) {
                    rcaNotSubmittedReminder.setToEmail(rcaForm.getRca().getRcaOwner() + "," + rcaForm.getRca().getRcaDelegate());
                } else if (org.apache.commons.lang.StringUtils.isNotBlank(rcaForm.getRca().getRcaOwner()) && org.apache.commons.lang.StringUtils.isBlank(rcaForm.getRca().getRcaDelegate())) {
                    rcaNotSubmittedReminder.setToEmail(rcaForm.getRca().getRcaOwner());
                } else if (org.apache.commons.lang.StringUtils.isBlank(rcaForm.getRca().getRcaOwner()) && org.apache.commons.lang.StringUtils.isNotBlank(rcaForm.getRca().getRcaDelegate())) {
                    rcaNotSubmittedReminder.setToEmail(rcaForm.getRca().getRcaDelegate());
                }
                rcaNotSubmittedReminder.setCcEmail(rcaForm.getRca().getRcaCoordinatorId());
                if (rcaNotificationAudit.getOwnerNotificationSentDate() != null) {
                    Date notificationSentDate = new Date(rcaNotificationAudit.getOwnerNotificationSentDate().getTime());
                    Date reminderStartDate = CommonUtil.addDays(notificationSentDate, emailNotificationSetting.getRcaNotSubmittedReminderForOwnerDays());
                    rcaNotSubmittedReminder.setStartDate(reminderStartDate);
                    rcaNotSubmittedReminder.setReminderType(ConstantDataManager.RCA_NOT_SUBMITTED_REMINDER);
                    rcaNotSubmittedReminder.setIsEnabled(ConstantDataManager.YES);
                    emailReminderDao.addEmailReminder(rcaNotSubmittedReminder);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void savePrimaryAndContCauses(RcaForm rcaForm) {
        saveRcaCauseDetails(rcaForm.getRca().getPrimaryRcaCause());
        if (CollectionUtils.isNotEmpty(rcaForm.getRcaContributingCauses())) {
            for (RcaCause rcaCause : rcaForm.getRcaContributingCauses()) {
                saveRcaCauseDetails(rcaCause);
            }
        }
    }

    private void saveRcaTickets(RcaForm rcaForm) {
        if (CollectionUtils.isNotEmpty(rcaForm.getRcaTickets())) {
            List<RcaTicket> rcaTickets = rcaForm.getRcaTickets();
            for (RcaTicket rcaTicket : rcaTickets) {
                RcaTicket dbRcaTicket = rcaTicketDao.getRcaTicketById(rcaTicket.getRcaTicketId());
                dbRcaTicket.setTicketNum(rcaTicket.getTicketNum());
                dbRcaTicket.setSeverity(rcaTicket.getSeverity());
                dbRcaTicket.setSecondaryTicketType(rcaTicket.getSecondaryTicketType());
                dbRcaTicket.setAssociatedTool(rcaTicket.getAssociatedTool());
                if(!rcaTicket.getAssociatedTool().equalsIgnoreCase("Other")){
                    rcaTicket.setOtherAssociatedTool("");
                }
                dbRcaTicket.setOtherAssociatedTool(rcaTicket.getOtherAssociatedTool());
                rcaTicketDao.updateRcaTicket(dbRcaTicket);
            }
        }
    }

    private void saveRcaHistoryLog(RcaForm rcaForm, Rca dbRca, String formAction) {
        RcaHistoryLog rcaHistoryLog = new RcaHistoryLog();
        rcaHistoryLog.setRca(dbRca);
        rcaHistoryLog.setSubmittedBy(rcaForm.getLoggedInUser());
        rcaHistoryLog.setSubmittedOn(dbRca.getUpdatedOn());
        rcaHistoryLog.setFormAction(formAction);
        List<UserRole> userRoles = userRoleDao.getUserRolesByContractIdAndUserId(dbRca.getContract().getContractId(), rcaForm.getLoggedInUser().getUserId());
        if (CollectionUtils.isNotEmpty(userRoles)) {
            rcaHistoryLog.setRole(userRoles.get(0).getRole());
        }
        rcaHistoryLogDao.addRcaHistoryLog(rcaHistoryLog);
    }

    private void saveRcaCauseDetails(RcaCause rcaCause) {
        RcaCause dbRcaCause = rcaCauseDao.getRcaCauseById(rcaCause.getRcaCauseId());
        dbRcaCause.setServiceProvider(rcaCause.getServiceProvider());
        dbRcaCause.setOtherServiceProvider(rcaCause.getOtherServiceProvider());
        dbRcaCause.setOutageCategory(rcaCause.getOutageCategory());
        dbRcaCause.setLocationOfBusinessImpact(rcaCause.getLocationOfBusinessImpact());
        dbRcaCause.setOtherLocOfBusinessImpact(rcaCause.getOtherLocOfBusinessImpact());
        dbRcaCause.setOutageSubCategory(rcaCause.getOutageSubCategory());
        dbRcaCause.setOutageSubCategory2(rcaCause.getOutageSubCategory2());
        dbRcaCause.setLocOfSystem(rcaCause.getLocOfSystem());
        dbRcaCause.setRootOrContributingCause(rcaCause.getRootOrContributingCause());
        dbRcaCause.setCauseCategory(rcaCause.getCauseCategory());
        dbRcaCause.setCauseSubCategory(rcaCause.getCauseSubCategory());
        dbRcaCause.setCauseSelectionGuidance(rcaCause.getCauseSelectionGuidance());
        dbRcaCause.setCauseSummary(rcaCause.getCauseSummary());
        rcaCauseDao.updateRcaCause(dbRcaCause);
    }


    @Override
    public Rca getRcaByNumber(String rcaNumber) {
        Rca rca = null;
        try {
            rca = rcaDao.getRcaByNumber(rcaNumber);
        } catch (Exception e) {

        }
        return rca;
    }


    @Override
    public RcaCoordinator getRcaCoordinatorById(Integer rcaCoordinatorId) {
        RcaCoordinator rcaCoordinator = null;
        try {
            rcaCoordinator = rcaCoordinatorDao.getRcaCoordinatorById(rcaCoordinatorId);
        } catch (Exception e) {
        }
        return rcaCoordinator;

    }

    @Override
    public List<RcaTicket> getRcaTicketsByRcaId(Integer rcaId) {
        List rcaTickets = null;
        try {
            rcaTickets = rcaTicketDao.getRcaTicketsByRcaId(rcaId);
        } catch (Exception e) {
        }

        return rcaTickets;
    }

    @Override
    public boolean addSecondaryTicket(RcaTicket rcaTicket) {
        log.debug("in rca server: addSecondaryTicket() ");
        boolean isRcaTicketAdded = true;
        try {
            rcaTicketDao.addRcaTicket(rcaTicket);
            log.debug("successfully saved from rcaService");
        } catch (Exception e) {
            log.debug("exception thrown from rcaService");
            isRcaTicketAdded = false;
        }
        return isRcaTicketAdded;
    }

    @Override
    @Transactional
    public boolean deleteSecondaryTicket(RcaTicket rcaTicket) {
        boolean isTicketDeleted = true;
        try {
            rcaTicketDao.deleteRcaTicket(rcaTicket);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return isTicketDeleted;
    }

    @Override
    public RcaTicket getRcaTicketById(Integer rcaTicketId) {
        return rcaTicketDao.getRcaTicketById(rcaTicketId);
    }

    @Override
    public List<RcaCause> getRcaContributingCauses(Integer rcaId) {
        return rcaCauseDao.getRcaCausesByRcaIdAndType(rcaId, "N");
    }

    @Override
    @Transactional
    public boolean addRcaContributingCause(RcaCause rcaCause) {
        boolean isRcaCausedAdded = true;
        try {
            rcaCauseDao.addRcaCause(rcaCause);
        } catch (Exception e) {
            isRcaCausedAdded = false;
            log.error(e.getMessage(), e);
        }
        return isRcaCausedAdded;
    }

    @Override
    @Transactional
    public boolean deleteRcaContributingCause(RcaCause rcaCause) {
        boolean isRcaCausedDeleted = true;
        try {
            rcaCauseDao.deleteRcaCause(rcaCause);
        } catch (Exception e) {
            isRcaCausedDeleted = false;
            log.error(e.getMessage(), e);
        }
        return isRcaCausedDeleted;

    }

    @Override
    public RcaCause getRcaCauseById(Integer rcaCauseId) {
        return rcaCauseDao.getRcaCauseById(rcaCauseId);
    }

    @Override
    public List<RcaHistoryLog> getRcaHistoryLogsByRcaId(Integer rcaId) {
        return rcaHistoryLogDao.getRcaHistoryLogsByRcaId(rcaId);
    }

    @Override
    public List<RcaEditor> getRcaEditorsByRcaId(Integer rcaId) {
        return rcaEditorDao.getRcaEditorsByRcaId(rcaId);
    }

    private void saveRcaEventDetails(RcaForm rcaForm) {
        try {
            RcaEventDetail dbRcaEventDetails = rcaEventDetailDao.getRcaEventDetailByRcaId(rcaForm.getRca().getRcaId());
            if (dbRcaEventDetails != null) {
                dbRcaEventDetails.setChronology(rcaForm.getRca().getRcaEventDetail().getChronology().trim());
                dbRcaEventDetails.setHowServiceRestored(rcaForm.getRca().getRcaEventDetail().getHowServiceRestored().trim());
                dbRcaEventDetails.setIssueDescription(org.apache.commons.lang.StringUtils.isNotBlank(rcaForm.getRca().getRcaEventDetail().getIssueDescription()) ? rcaForm.getRca().getRcaEventDetail().getIssueDescription() : "");
                dbRcaEventDetails.setKeyIssues(rcaForm.getRca().getRcaEventDetail().getKeyIssues());
                dbRcaEventDetails.setRepeatIssue(rcaForm.getRca().getRcaEventDetail().getRepeatIssue().trim());
                dbRcaEventDetails.setRepeatRisk(rcaForm.getRca().getRcaEventDetail().getRepeatRisk().trim());
                dbRcaEventDetails.setExecutiveAlert(rcaForm.getRca().getRcaEventDetail().getExecutiveAlert().trim());
                dbRcaEventDetails.setImpactedTower(rcaForm.getRca().getRcaEventDetail().getImpactedTower().trim());
                dbRcaEventDetails.setRepeatRcaOrTicketNum(rcaForm.getRca().getRcaEventDetail().getRepeatRcaOrTicketNum());
                dbRcaEventDetails.setWhyProblem(rcaForm.getRca().getRcaEventDetail().getWhyProblem().trim());
                dbRcaEventDetails.setWhy1(rcaForm.getRca().getRcaEventDetail().getWhy1().trim());
                dbRcaEventDetails.setWhy2(rcaForm.getRca().getRcaEventDetail().getWhy2().trim());
                dbRcaEventDetails.setWhy3(rcaForm.getRca().getRcaEventDetail().getWhy3().trim());
                dbRcaEventDetails.setWhy4(rcaForm.getRca().getRcaEventDetail().getWhy4().trim());
                dbRcaEventDetails.setExecutiveSummary(rcaForm.getRca().getRcaEventDetail().getExecutiveSummary().trim());
                rcaEventDetailDao.update(dbRcaEventDetails);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    private void saveRcaPreventionDetails(Rca rca, Rca dbRca) {
        RcaPreventionDetail dbRcaPreventionDetail = rcaPreventionDetailDao.findByRcaId(rca.getRcaId());
        if (dbRcaPreventionDetail != null) {
            dbRcaPreventionDetail.setRcaId(rca.getRcaId());
            dbRcaPreventionDetail.setComments(rca.getRcaPreventionDetail().getComments());
            dbRcaPreventionDetail.setFuturePrevention(rca.getRcaPreventionDetail().getFuturePrevention());
            dbRcaPreventionDetail.setNewPoliciesImplemented(rca.getRcaPreventionDetail().getNewPoliciesImplemented());
            dbRcaPreventionDetail.setRca(dbRca);
            rcaPreventionDetailDao.update(dbRcaPreventionDetail);
        } else {
            dbRcaPreventionDetail = new RcaPreventionDetail();
            dbRcaPreventionDetail.setRcaId(rca.getRcaId());
            dbRcaPreventionDetail.setComments(rca.getRcaPreventionDetail().getComments());
            dbRcaPreventionDetail.setFuturePrevention(rca.getRcaPreventionDetail().getFuturePrevention());
            dbRcaPreventionDetail.setNewPoliciesImplemented(rca.getRcaPreventionDetail().getNewPoliciesImplemented());
            dbRcaPreventionDetail.setRca(dbRca);
            rcaPreventionDetailDao.add(dbRcaPreventionDetail);


        }


    }


    private String generateRcaNumber(Contract dbContract, Integer maxRcaId, Integer month, Integer year) {
        String generatedRcaNumber = null;
        String monthStr = null;
        String yearStr = null;
        String maxRcaIdStr = null;
        String contractIdStr = null;

        if (maxRcaId != null && dbContract != null) {
            maxRcaId++;
            if (String.valueOf(maxRcaId).length() == 1) {
                maxRcaIdStr = "0" + maxRcaId;
            } else {
                maxRcaIdStr = String.valueOf(maxRcaId);
            }
            if (String.valueOf(month).length() == 1) {
                monthStr = "0" + month;
            } else {
                monthStr = String.valueOf(month);
            }
            yearStr = String.valueOf(year);
            contractIdStr = String.valueOf(dbContract.getContractId());
            generatedRcaNumber = contractIdStr + "-" + yearStr + monthStr + "-" + maxRcaIdStr;
        }
        return generatedRcaNumber;
    }

    private Hashtable<String, String> getValueMap(Rca rca) {
        Hashtable<String, String> keys = new Hashtable<String, String>();
        keys.put("rcaNumber", rca.getRcaNumber());
        keys.put("contractName", rca.getContract().getTitle());
        keys.put("contractId", String.valueOf(rca.getContract().getContractId()));
        keys.put("rcaCoordinatorName", rcaCoordinatorDao.getRcaCoordinatorById(Integer.parseInt(rca.getRcaCoordinatorId())).getCoordinator().getCoordinatorName());


        return keys;
    }


    private void loadRcaActions(List<RcaAction> rcaActions, List<RcaListing> rcaListings, SearchFilter searchFilter, Map<String, RcaListing> rcaListingMap) {

        if (CollectionUtils.isNotEmpty(rcaActions)) {
            for (RcaAction rcaAction : rcaActions) {
                RcaListing rcaActionListing = new RcaListing();
                rcaActionListing.setRcaOrActionNumber(rcaAction.getActionNumber());
                rcaActionListing.setRcaAccountTitle(rcaAction.getRca().getContract().getTitle());
                rcaActionListing.setRcaStage(rcaAction.getActionStatus());
                rcaActionListing.setRcaOwner(StringUtils.isNotBlank(rcaAction.getRca().getRcaOwner()) ? BluePages.getNotesIdFromIntranetId(rcaAction.getRca().getRcaOwner()) : "");
                rcaActionListing.setRcaDelegate(StringUtils.isNotBlank(rcaAction.getRca().getRcaDelegate()) ? BluePages.getNotesIdFromIntranetId(rcaAction.getRca().getRcaDelegate()) : "");
                rcaActionListing.setRcaCreateDate((rcaAction.getAssignedOn() != null ? getFormattedDate(rcaAction.getAssignedOn(), "dd-MM-yyyy") : ""));
                rcaActionListing.setRcaDueDate((rcaAction.getTargetDate() != null ? getFormattedDate(rcaAction.getTargetDate(), "dd-MM-yyyy") : ""));
                rcaActionListing.setListingType("action");
                if (StringUtils.isNotBlank(rcaAction.getRca().getRcaCoordinatorId())) {
                    rcaActionListing.setRcaCoordinator(BluePages.getNotesIdFromIntranetId(rcaAction.getRca().getRcaCoordinatorId()));
                }

                if ((Integer) rcaAction.getRca().getRcaDpeId() != null && rcaAction.getRca().getRcaDpeId() > 0) {
                    GpsUser rcaDpe = gpsUserService.getUserById(rcaAction.getRca().getRcaDpeId());
                    rcaActionListing.setRcaDpe(rcaDpe != null ? BluePages.getNotesIdFromIntranetId(rcaDpe.getEmail()) : "");
                }
                rcaActionListing.setPrimaryTicket(rcaAction.getRca().getProblemIndidentRecord());


                if(!rcaListingMap.containsKey(rcaActionListing.getRcaOrActionNumber())) {
                    rcaListings.add(rcaActionListing);
                    rcaListingMap.put(rcaActionListing.getRcaOrActionNumber(), rcaActionListing);
                }


            }
        }
    }


    private boolean isNullOrEmpty(String str) {
        return str == null || str.equals("") ? true : false;
    }

    private static String getFormattedDate(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

  /*  private String getPrimaryTicket(Rca rca) {
        List<RcaTicket> rcaTickets = rca.getRcaTickets();
        String primaryTicket = null;
        if (CollectionUtils.isNotEmpty(rcaTickets)) {
            for (RcaTicket rcaTicket : rcaTickets) {
                if (rcaTicket.getTicketType().equalsIgnoreCase("primary")) {
                    primaryTicket = rcaTicket.getTicketNum();
                    break;
                }
            }
        }
        return primaryTicket;
    }*/

    private GpsUser processGpsUser(String email) {

        Integer userId = null;
        GpsUser dbUser = gpsUserService.getUserByIntranetId(email);
        if (dbUser != null) {
            return dbUser;
        } else {
            GpsUser newUser = new GpsUser();
            newUser.setEmail(email);
            gpsUserService.addUser(newUser);
        }

        dbUser = gpsUserService.getUserByIntranetId(email);


        return dbUser;
    }


    private String getToEmail(String whom, Rca rca, Set<ContractContact> contractContacts) {
        String to = whom;

        if (to == null || to.isEmpty()) {
            to = null;
        } else if (to.trim().equalsIgnoreCase("RCA Coordinator")) {
            if (!StringUtils.isNullOrEmpty(rca.getRcaCoordinatorId())) {
                RcaCoordinator rcaCoordinator = rcaCoordinatorDao.getRcaCoordinatorById(Integer.parseInt(rca.getRcaCoordinatorId()));
                to = rcaCoordinator.getCoordinator().getIntranetId();
            } else {
                to = null;
            }
        } else if (CollectionUtils.isNotEmpty(contractContacts)) {
            if (to.trim().equalsIgnoreCase("Global Tower Leader")) {
                for (ContractContact contractContact : contractContacts) {
                    if (1 == contractContact.getContactType().getCtId()) {
                        to = contractContact.getContactIntranetId();
                        break;
                    }
                }
            } else if (to.trim().equalsIgnoreCase("Geo Delivery Leader")) {
                for (ContractContact contractContact : contractContacts) {
                    if (2 == contractContact.getContactType().getCtId()) {
                        to = contractContact.getContactIntranetId();
                        break;
                    }
                }
            } else if (to.trim().equalsIgnoreCase("Project Executive (PE)")) {
                for (ContractContact contractContact : contractContacts) {
                    if (3 == contractContact.getContactType().getCtId()) {
                        to = contractContact.getContactIntranetId();
                        break;
                    }
                }
            } else if (to.trim().equalsIgnoreCase("Senior Delivery Project Manager (SDPE)")) {
                for (ContractContact contractContact : contractContacts) {
                    if (4 == contractContact.getContactType().getCtId()) {
                        to = contractContact.getContactIntranetId();
                        break;
                    }
                }
            } else if (to.trim().equalsIgnoreCase("Delivery Project Manager (DPE)")) {
                for (ContractContact contractContact : contractContacts) {
                    if (5 == contractContact.getContactType().getCtId()) {
                        to = contractContact.getContactIntranetId();
                        break;
                    }
                }
            } else if (to.trim().equalsIgnoreCase("Transition Manager")) {
                to = "bpdgs@pk.ibm.com";
                for (ContractContact contractContact : contractContacts) {
                    if (6 == contractContact.getContactType().getCtId()) {
                        to = contractContact.getContactIntranetId();
                        if (contractContact.getContactIntranetId() == null || contractContact.getContactIntranetId().isEmpty()) {
                            to = "bpdgs@pk.ibm.com";
                            if (contractContact.getContract() != null)
                                log.debug("Transition Manager not present: " + contractContact.getContract().getContractId());
                        }
                        break;
                    }
                }
            }
        }

        return to;
    }


    private void addRcaNotClosedReminder(Integer rcaId, Rca dbRca) {
        try {
            RcaEmailNotificationSetting emailNotificationSetting = rcaEmailNotificationSettingsDao.getRcaEmailNotificationSettingByContractId(dbRca.getContract().getContractId());
            if (emailNotificationSetting != null && emailNotificationSetting.getRcaNotClosedReminder().equalsIgnoreCase(ConstantDataManager.YES)) {
                EmailReminder emailReminder = new EmailReminder();
                emailReminder.setQuestionnaireId(rcaId.toString());
                emailReminder.setQuestionnaireType(ConstantDataManager.RCA_FORM_TYPE);
                RcaCoordinator rcaCoordinator = rcaCoordinatorDao.getRcaCoordinatorById(Integer.parseInt(dbRca.getRcaCoordinatorId()));
                emailReminder.setToEmail(rcaCoordinator.getCoordinator().getIntranetId());
                emailReminder.setCcEmail(dbRca.getRcaOwner() + "," + dbRca.getRcaDelegate());
                Date createDate = new Date(dbRca.getCreateDate().getTime());
                Date reminderStartDate = CommonUtil.addDays(createDate, emailNotificationSetting.getRcaNotClosedReminderDaysAfterCreation());
                Date reminderEndDate = CommonUtil.addWorkingDays(reminderStartDate, emailNotificationSetting.getRcaNotClosedReminderDuration());
                emailReminder.setStartDate(reminderStartDate);
                emailReminder.setEndDate(reminderEndDate);
                emailReminder.setReminderType(ConstantDataManager.RCA_NOT_CLOSED_REMINDER);
                emailReminder.setIsEnabled(ConstantDataManager.YES);
                emailReminderDao.addEmailReminder(emailReminder);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    private void addRcaNotApprovedReminder(Rca dbRca) {
        try {
            dbRca = rcaDao.getRcaById(dbRca.getRcaId());
            RcaEmailNotificationSetting emailNotificationSetting = rcaEmailNotificationSettingsDao.getRcaEmailNotificationSettingByContractId(dbRca.getContract().getContractId());
            if (emailNotificationSetting != null && emailNotificationSetting.getRcaNotApprovedReminder().equalsIgnoreCase(ConstantDataManager.YES)
                    && emailNotificationSetting.getRcaWorkflowNotificationEnabled().equalsIgnoreCase(ConstantDataManager.YES)) {

                EmailReminder emailReminder = emailReminderDao.findByQuestionnaireIdAndReminderType(dbRca.getRcaId(), ConstantDataManager.RCA_NOT_APPROVED_REMINDER);
                if (emailReminder == null) {
                    emailReminder = new EmailReminder();
                    emailReminder.setQuestionnaireId(dbRca.getRcaId().toString());
                    emailReminder.setQuestionnaireType(ConstantDataManager.RCA_FORM_TYPE);
                    GpsUser dpe = gpsUserService.getUserById(dbRca.getRcaDpeId());
                    emailReminder.setToEmail(dpe.getEmail());
                    emailReminder.setCcEmail(dbRca.getRcaOwner() + "," + dbRca.getRcaDelegate());
                    Date createDate = new Date(dbRca.getCreateDate().getTime());
                    Date reminderStartDate = CommonUtil.addDays(createDate, emailNotificationSetting.getRcaNotApprovedReminderDaysAfterCreation());
                    Date reminderEndDate = CommonUtil.addWorkingDays(reminderStartDate, emailNotificationSetting.getRcaNotApprovedReminderDuration());
                    emailReminder.setStartDate(reminderStartDate);
                    emailReminder.setEndDate(reminderEndDate);
                    emailReminder.setReminderType(ConstantDataManager.RCA_NOT_APPROVED_REMINDER);
                    emailReminder.setIsEnabled(ConstantDataManager.YES);
                    emailReminderDao.addEmailReminder(emailReminder);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }


    private void addActionNotClosedReminder(RcaForm rcaForm, RcaAction rcaAction, RcaEmailNotificationSetting emailNotificationSetting) {

        try {
            EmailReminder emailReminder = emailReminderDao.findByQuestionnaireIdAndReminderType(rcaAction.getRcaActionId(), ConstantDataManager.ACTION_NOT_CLOSED_REMINDER);
            if (emailReminder == null) {
                emailReminder = new EmailReminder();
                emailReminder.setQuestionnaireId(rcaAction.getRcaActionId().toString());
                emailReminder.setQuestionnaireType(ConstantDataManager.ACTION_FORM_TYPE);
                emailReminder.setToEmail(rcaAction.getAssignedTo());
                if (StringUtils.isNotBlank(rcaForm.getRca().getRcaOwner()) && StringUtils.isNotBlank(rcaForm.getRca().getRcaDelegate())) {
                    emailReminder.setCcEmail(rcaForm.getRca().getRcaOwner() + "," + rcaForm.getRca().getRcaDelegate());
                } else if (StringUtils.isNotBlank(rcaForm.getRca().getRcaOwner()) && StringUtils.isBlank(rcaForm.getRca().getRcaDelegate())) {
                    emailReminder.setCcEmail(rcaForm.getRca().getRcaOwner());
                } else if (StringUtils.isBlank(rcaForm.getRca().getRcaOwner()) && StringUtils.isNotBlank(rcaForm.getRca().getRcaDelegate())) {
                    emailReminder.setCcEmail(rcaForm.getRca().getRcaDelegate());
                }

                Date reminderStartDate = CommonUtil.subtractWorkingDays(rcaAction.getTargetDate(), emailNotificationSetting.getActionNotClosedReminderDaysBeforeTarget());
                Date reminderEndDate = CommonUtil.addWorkingDays(reminderStartDate, emailNotificationSetting.getActionNotClosedReminderDuration());
                emailReminder.setStartDate(reminderStartDate);
                emailReminder.setEndDate(reminderEndDate);
                emailReminder.setReminderType(ConstantDataManager.ACTION_NOT_CLOSED_REMINDER);
                emailReminder.setIsEnabled(ConstantDataManager.YES);
                emailReminderDao.addEmailReminder(emailReminder);
            } else {
                Date reminderStartDate = CommonUtil.subtractWorkingDays(emailReminder.getStartDate(), emailNotificationSetting.getActionNotClosedReminderDaysBeforeTarget());
                Date reminderEndDate = CommonUtil.addWorkingDays(reminderStartDate, emailNotificationSetting.getActionNotClosedReminderDuration());
                emailReminder.setStartDate(reminderStartDate);
                emailReminder.setEndDate(reminderEndDate);
                emailReminderDao.updateEmailReminder(emailReminder);

            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void deleteEmailReminder(int rcaId, String reminderType) {
        EmailReminder emailReminder = emailReminderDao.findByQuestionnaireIdAndReminderType(rcaId, reminderType);
        if (emailReminder != null) {
            emailReminderDao.deleteEmailReminder(emailReminder);
        }
    }

    private void handleNewlyCreatedActions(RcaForm rcaForm) {
        //process action notes id

        processActionsNotesIDs(rcaForm);

        RcaEmailNotificationSetting emailNotificationSetting = rcaEmailNotificationSettingsDao.getRcaEmailNotificationSettingByContractId(rcaForm.getRca().getContract().getContractId());
        List<RcaAction> formRcaActions = rcaForm.getRcaActions();
        if (CollectionUtils.isNotEmpty(formRcaActions)) {
            for (RcaAction formRcaAction : formRcaActions) {
                RcaAction dbRcaAction = rcaActionDao.getRcaActionById(formRcaAction.getRcaActionId());
                if (StringUtils.isNullOrEmpty(dbRcaAction.getAssignedTo()) && !StringUtils.isNullOrEmpty(formRcaAction.getAssignedTo())) {
                    //check if the new action item notification is enabled then send the email
                    if (emailNotificationSetting != null && emailNotificationSetting.getRcaWorkflowNotificationEnabled().equalsIgnoreCase(ConstantDataManager.YES)
                            && emailNotificationSetting.getNewActionItemNotification().equalsIgnoreCase(ConstantDataManager.YES)) {
                        rcaActionNotificationService.sendRcaActionCreatedNotification(formRcaAction.getAssignedTo(), dbRcaAction);
                    }
                }
            }
        }
    }

    private void saveRcaEditors(RcaForm rcaForm, Rca dbRca) {
        if (CollectionUtils.isNotEmpty(rcaForm.getRcaEditorIds())) {
            List<Integer> editorUserIds = rcaForm.getRcaEditorIds();
            for (Integer editorUserId : editorUserIds) {
                RcaEditor dbRcaEditor = rcaEditorDao.getRcaEditorByRcaIdAndUserId(rcaForm.getRca().getRcaId(), editorUserId);
                if (dbRcaEditor == null) {
                    RcaEditor rcaEditor = new RcaEditor();
                    rcaEditor.setRca(dbRca);
                    rcaEditor.setGpsUser(gpsUserService.getUserById(editorUserId));
                    rcaEditorDao.addRcaEditor(rcaEditor);
                }
            }

        }
    }

    private void processActionsNotesIDs(RcaForm rcaForm) {
        if (CollectionUtils.isNotEmpty(rcaForm.getRcaActions())) {
            int i = 0;
            for (RcaAction rcaAction : rcaForm.getRcaActions()) {
                if (StringUtils.isNotBlank(rcaAction.getAssignedTo()) && BluePages.isNotesID(rcaAction.getAssignedTo())) {
                    rcaForm.getRcaActions().get(i).setAssignedTo(BluePages.getIntranetIdFromNotesId(rcaAction.getAssignedTo()));
                }
                i++;
            }
        }
    }

}
