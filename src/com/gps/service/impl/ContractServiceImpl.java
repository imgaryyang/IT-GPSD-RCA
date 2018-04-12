
package com.gps.service.impl;

import com.gps.dao.*;
import com.gps.exceptions.GPSException;
import com.gps.service.ContractProcessService;
import com.gps.service.ContractService;
import com.gps.service.UserRoleService;
import com.gps.util.BluePages;
import com.gps.util.GpsCacheManager;
import com.gps.vo.*;
import com.gps.vo.helper.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ContractServiceImpl implements ContractService {
    private static Logger log = Logger.getLogger(ContractServiceImpl.class);

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    ContractDao contractDao;

    @Autowired
    GpsCacheManager gpsCacheManager;

    @Autowired
    ContractProcessDao contractProcessDao;

    @Autowired
    ProcessDao processDao;

    @Autowired
    ContractProcessService contractProcessService;

    @Autowired
    RcaCoordinatorDao rcaCoordinatorDao;

    @Autowired
    ContractContactDao contractContactDao;

    @Autowired
    GpsUserDao gpsUserDao;

    @Autowired
    GpsDao gpsDao;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    CoordinatorDao coordinatorDao;

    @Autowired
    RcaEmailNotificationSettingDao rcaEmailNotificationSettingDao;

    @Transactional
    public void modifyContract(Contract contract) throws GPSException {
        try {
            Contract dbContract = contractDao.getContractById(contract.getContractId());

            if (!dbContract.getTitle().equals(contract.getTitle())) {
                log.debug("Contract Title: old=" + dbContract.getTitle() + ", new=" + contract.getTitle());
                dbContract.setTitle(contract.getTitle());
            }
            if (dbContract.getCustomer().getInac() != contract.getCustomer().getInac()) {

                if (contract.getCustomer().getInac() > 0) {
                    Customer customer = gpsDao.getCustomerByInac(contract.getCustomer().getInac());
                    dbContract.setCustomer(customer);
                }
            }

            contractDao.updateContract(dbContract);
        } catch (Exception e) {
            log.error("[Exception] " + e.getMessage());
            throw new GPSException(e.getMessage(), e);
        }
    }

    @Transactional
    public void addContract(Contract contract) throws GPSException {
        try {
            contract.setAutoHealthUpdate(Constant.YES);
            Timestamp stamp = new Timestamp((new Date()).getTime());
            contract.setCreatedOn(stamp);
            contract.setCreatedBy(contract.getGpsUser() != null ? contract.getGpsUser().getUserId() : null);
            contract.setUpdatedOn(stamp);
            contract.setState("0");
            contract.setCurrentPhaseId(1);
            contract.setIsDeleted("N");
            contract.setIsActive("Y");
            if (contract.getCustomer() != null && contract.getCustomer().getInac() == 0) {
                contract.setCustomer(null);
            }
            contractDao.addContract(contract);
            // Adding RCA Setting
            RcaEmailNotificationSetting rcaEmailNotificationSetting = new RcaEmailNotificationSetting();
            rcaEmailNotificationSetting = buildRcaEmailSettings(contract);
            rcaEmailNotificationSettingDao.addRcaEmailNotificationSetting(rcaEmailNotificationSetting);
        } catch (Exception e) {
            log.error("Exception: " + e.getMessage());
            throw new GPSException(e.getMessage(), e);
        }
    }

    @Override
    public List<Contract> getAllContracts() {
        return contractDao.getAllContracts();
    }

    @Override
    public List<Contract> getContractsByFilter(SearchFilter searchFilter) {
        return contractDao.getContractsByTitle(searchFilter);
    }

    @Override
    public List<Contract> getAllContracts(String sessionId) throws GPSException {
        return contractDao.getAllContracts(sessionId);
    }


    /* (non-Javadoc)
     * @see com.gps.service.ContractService#listActiveContracts()
     */
    public Map<Integer, String> listActiveContracts() throws GPSException {
        log.debug("listing 'Transformation' 'Steady State' and 'Phase Out' contracts.....");
        List<Contract> contracts = contractDao.listActiveContracts();
        Map<Integer, String> contractMap = null;
        if (contracts != null) {
            contractMap = new HashMap<Integer, String>();
            for (Contract contract : contracts) {
                contractMap.put(contract.getContractId(), contract.getTitle());
            }
            log.debug("got list of " + contracts.size() + " active contracts.");
        } else {
            log.info("no active contract found.");
        }
        return contractMap;
    }

    public List<Contract> getActiveContracts() throws GPSException {
        log.debug("listing 'Transformation' 'Steady State' and 'Phase Out' contracts.....");
        List<Contract> contracts = contractDao.listActiveContracts();
        return contracts;
    }

    @Override
    @Transactional
    public void removeContract(Integer contractId) throws GPSException {
        Contract dbContract = contractDao.getContractByIdAndTitle(contractId);
        if (dbContract != null) {
            dbContract.setIsDeleted("Y");
            contractDao.updateContract(dbContract);
        }
    }

    @Override
    public Contract getContractByIdNoFetch(Integer contractId) throws GPSException {
        Contract contract = contractDao.getContractById(contractId);
        return contract;
    }

    @Override
    public Contract getContractByIdAndTitle(Integer contractId) {
        Contract contract = contractDao.getContractByIdAndTitle(contractId);
        prepareContractDisplay(contract);
        return contract;
    }

    @Override
    public Contract getContractByTitle(String title) throws GPSException {
        Contract contract = null;
        log.debug("contract title: " + title);
        if (title == null || title.isEmpty()) {
            return contract;
        }
        try {
            contract = contractDao.getContractByTitle(title);
        } catch (GPSException e) {
            log.debug("error while fetching contract");
            log.error(e.getMessage());
        }
        return contract;
    }

    @Override
    @Transactional
    public void updateContract(Contract contract) {
        try {
            contractDao.updateContract(contract);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void saveContract(Contract contract) {
        contractDao.saveContract(contract);
    }

    @Override
    @Transactional
    public void processContractSubmit(Contract contract) {
        log.debug("processing profile submit........");
        Contract dbContract = getContractByIdAndTitle(contract.getContractId());
        //handle the primary RCA Coordinator
        processPrimaryRcaCoordinator(contract);
        processSecondaryRcaCoordinators(contract);
        processRcaEmailNotificationSettings(contract);
        prepareContractForSaveUpdate(contract, dbContract);
        updateContractContacts(dbContract);
        updateContract(dbContract);
    }

    private void processRcaEmailNotificationSettings(Contract contract) {
        try {
            if (contract.getContractHelper().getEmailNotificationSetting() != null) {
                RcaEmailNotificationSetting formRcaEmailNotificationSetting = contract.getContractHelper().getEmailNotificationSetting();
                RcaEmailNotificationSetting dbRcaEmailNotificationSetting = rcaEmailNotificationSettingDao.getRcaEmailNotificationSettingByContractId(contract.getContractId());
                if (dbRcaEmailNotificationSetting != null) {
                    dbRcaEmailNotificationSetting.setRcaCompletionTargetBusinessDays(formRcaEmailNotificationSetting.getRcaCompletionTargetBusinessDays());
                    dbRcaEmailNotificationSetting.setRcaRoutingMatrixEnabled(formRcaEmailNotificationSetting.getRcaRoutingMatrixEnabled());
                    dbRcaEmailNotificationSetting.setRcaWorkflowNotificationEnabled(formRcaEmailNotificationSetting.getRcaWorkflowNotificationEnabled());
                    dbRcaEmailNotificationSetting.setNewRcaNotificationToCoordinator(formRcaEmailNotificationSetting.getNewRcaNotificationToCoordinator());
                    dbRcaEmailNotificationSetting.setNewRcaNotificationToCoordinator(formRcaEmailNotificationSetting.getNewRcaNotificationToCoordinator());
                    dbRcaEmailNotificationSetting.setRcaAssignmentNotificationToOwner(formRcaEmailNotificationSetting.getRcaAssignmentNotificationToOwner());
                    dbRcaEmailNotificationSetting.setRcaNotAcceptedReminderForOwner(formRcaEmailNotificationSetting.getRcaNotAcceptedReminderForOwner());
                    dbRcaEmailNotificationSetting.setRcaNotAcceptedReminderForOwnerDays(formRcaEmailNotificationSetting.getRcaNotAcceptedReminderForOwnerDays());
                    dbRcaEmailNotificationSetting.setRcaNotSubmittedReminderForOwner(formRcaEmailNotificationSetting.getRcaNotSubmittedReminderForOwner());
                    dbRcaEmailNotificationSetting.setRcaNotSubmittedReminderForOwnerDays(formRcaEmailNotificationSetting.getRcaNotSubmittedReminderForOwnerDays());
                    dbRcaEmailNotificationSetting.setRcaAwaitingApprovalNotification(formRcaEmailNotificationSetting.getRcaAwaitingApprovalNotification());
                    dbRcaEmailNotificationSetting.setRcaApprovedNotification(formRcaEmailNotificationSetting.getRcaApprovedNotification());
                    dbRcaEmailNotificationSetting.setRcaRejectedNotification(formRcaEmailNotificationSetting.getRcaRejectedNotification());
                    dbRcaEmailNotificationSetting.setRcaNotApprovedReminder(formRcaEmailNotificationSetting.getRcaNotApprovedReminder());
                    dbRcaEmailNotificationSetting.setRcaNotApprovedReminderDaysAfterCreation(formRcaEmailNotificationSetting.getRcaNotApprovedReminderDaysAfterCreation());
                    dbRcaEmailNotificationSetting.setRcaNotApprovedReminderDuration(formRcaEmailNotificationSetting.getRcaNotApprovedReminderDuration());
                    dbRcaEmailNotificationSetting.setNewActionItemNotification(formRcaEmailNotificationSetting.getNewActionItemNotification());
                    dbRcaEmailNotificationSetting.setActionNotClosedReminder(formRcaEmailNotificationSetting.getActionNotClosedReminder());
                    dbRcaEmailNotificationSetting.setActionNotClosedReminderDaysBeforeTarget(formRcaEmailNotificationSetting.getActionNotClosedReminderDaysBeforeTarget());
                    dbRcaEmailNotificationSetting.setActionNotClosedReminderDuration(formRcaEmailNotificationSetting.getActionNotClosedReminderDuration());
                    dbRcaEmailNotificationSetting.setRcaNotClosedReminder(formRcaEmailNotificationSetting.getRcaNotClosedReminder());
                    dbRcaEmailNotificationSetting.setRcaNotClosedReminderDaysAfterCreation(formRcaEmailNotificationSetting.getRcaNotClosedReminderDaysAfterCreation());
                    dbRcaEmailNotificationSetting.setRcaNotClosedReminderDuration(formRcaEmailNotificationSetting.getRcaNotClosedReminderDuration());
                    dbRcaEmailNotificationSetting.setRcaClosedNotification(formRcaEmailNotificationSetting.getRcaClosedNotification());
                    dbRcaEmailNotificationSetting.setRcaCancelledNotification(formRcaEmailNotificationSetting.getRcaCancelledNotification());
                    dbRcaEmailNotificationSetting.setActionItemCancelledNotification(formRcaEmailNotificationSetting.getActionItemCancelledNotification());
                    dbRcaEmailNotificationSetting.setActionItemClosedNotification(formRcaEmailNotificationSetting.getActionItemClosedNotification());
                    rcaEmailNotificationSettingDao.updateRcaEmailNotificationSetting(dbRcaEmailNotificationSetting);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void processSecondaryRcaCoordinators(Contract contract) {
        try {
            if (CollectionUtils.isNotEmpty(contract.getContractHelper().getRcaCoordinators())) {
                for (RcaCoordinator rcaCoordinator : contract.getContractHelper().getRcaCoordinators()) {
                    if (rcaCoordinator.getRcaCoordinatorId() != null) {
                        RcaCoordinator dbRcaCoordinator = rcaCoordinatorDao.getRcaCoordinatorById(rcaCoordinator.getRcaCoordinatorId());

                        //check if coordinator with similar details already exists
                        Coordinator dbCoordinator = coordinatorDao.getCoordinatorByIntranetId(rcaCoordinator.getCoordinator().getIntranetId());
                        if (dbCoordinator != null) {
                            dbCoordinator.setPhone(rcaCoordinator.getCoordinator().getPhone());
                            if (CollectionUtils.isNotEmpty(dbCoordinator.getRcaCoordinators())) {
                                dbCoordinator.getRcaCoordinators().add(dbRcaCoordinator);
                            } else {
                                List<RcaCoordinator> rcaCoordinators = new ArrayList<RcaCoordinator>();
                                rcaCoordinators.add(dbRcaCoordinator);
                                dbCoordinator.setRcaCoordinators(rcaCoordinators);
                            }
                            coordinatorDao.updateCoordinator(dbCoordinator);
                            dbRcaCoordinator.setCoordinator(dbCoordinator);
                            rcaCoordinatorDao.updateRcaCoordinator(dbRcaCoordinator);

                            //delete the newly created one.
                            Coordinator newCoordinator = coordinatorDao.getCoordinatorById(rcaCoordinator.getCoordinator().getCoordinatorId());
                            if (newCoordinator != null && StringUtils.isBlank(newCoordinator.getIntranetId())) {
                                coordinatorDao.deleteCoordinator(newCoordinator);
                            }

                        } else {
                            Coordinator newCoordinator = coordinatorDao.getCoordinatorById(rcaCoordinator.getCoordinator().getCoordinatorId());
                            if (StringUtils.isNotBlank(rcaCoordinator.getCoordinator().getIntranetId())) {
                                newCoordinator.setCoordinatorName(rcaCoordinator.getCoordinator().getCoordinatorName());
                                newCoordinator.setIntranetId(rcaCoordinator.getCoordinator().getIntranetId());
                                newCoordinator.setPhone(rcaCoordinator.getCoordinator().getPhone());
                                coordinatorDao.updateCoordinator(newCoordinator);
                            }
                            dbRcaCoordinator.setCoordinator(newCoordinator);
                            rcaCoordinatorDao.updateRcaCoordinator(dbRcaCoordinator);
                        }
                    }

                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    private void updateContractContacts(Contract dbContract) {
        for (ContractContact contractContact : dbContract.getContractContacts()) {
            if (contractContact.getContractContactId() == null) {
                contractContactDao.addContractContact(contractContact);
            } else {
                contractContactDao.updateContractContact(contractContact);
            }
        }
    }


    @Override
    public Page getContractsBySearchFilter(SearchFilter searchFilter, String sessionId) {
        List<Object> queryParameters = new ArrayList<Object>();
        String query = "Select c.contractId, c.title, c.updatedOn, c.gpsUser.email, c.state, c.isDeleted" +
                " FROM Contract c JOIN c.gpsUser JOIN c.sessionAcls acl ";
        String orderBy = " ORDER BY c.title asc";
        StringBuilder subClause = new StringBuilder();
        int index = 1;

        if (!isNullOrEmpty(searchFilter.getContract())) {
            subClause.append(" and c.title Like :A" + index++);
            queryParameters.add(searchFilter.getContract() + "%");
        }

        if (!isNullOrEmpty(searchFilter.getCustomer()) && gpsCacheManager.getCustomerByInac(Integer.parseInt(searchFilter.getCustomer())) != null) {
            subClause.append(" and c.customer = :A" + index++);
            queryParameters.add(gpsCacheManager.getCustomerByInac(Integer.parseInt(searchFilter.getCustomer())));
        }


        if (!isNullOrEmpty(searchFilter.getStage())) {
            subClause.append(" and c.state = :A" + index++);
            queryParameters.add(searchFilter.getStage());
        }

        if (!isNullOrEmpty(searchFilter.getFormType())) {
            subClause.append(" and acl.formType in ('" + searchFilter.getFormType() + "')");
        }

        if (!isNullOrEmpty(sessionId)) {
            subClause.append(" and acl.sessionId = :A" + index++);
            queryParameters.add(sessionId);
        }

        if (!isNullOrEmpty(subClause.toString())) {
            query = query + " WHERE c.isDeleted = 'N' " + subClause.toString();
        } else {
            query = query + " WHERE c.isDeleted = 'N' ";
        }

        String q2 = "Select count(c.contractId) FROM Contract c JOIN c.gpsUser JOIN c.sessionAcls acl WHERE c.isDeleted = 'N' " + subClause.toString();
        query = query + orderBy;
        log.debug(query);

        q2 = q2.replace("FETCH", "");
        log.debug(q2);
        Page page = new Page();

        page.setRowCount(contractDao.getCountContractsByQueryParameters(q2, queryParameters));
        List<Object[]> contractList = contractDao.getContractsByQueryParameters(searchFilter.getPagination().getPageNumber(), query, queryParameters);
        log.error("listing.........");
        List<ContractListing> contracts = new ArrayList<ContractListing>();
        for (Object[] contract : contractList) {
            ContractListing contractListing = new ContractListing();
            Integer contractId = (Integer) contract[0];
            contractListing.setContractId(contractId);
            contractListing.setEmail("" + contract[3]);
            contractListing.setTitle("" + contract[1]);
            contractListing.setUpdatedOn((Timestamp) contract[2]);
            contractListing.setState(String.valueOf(contract[4]));
            contracts.add(contractListing);
        }
        page.setDataList(contracts);
        return page;
    }

    @Transactional
    public void saveCoordinator(Coordinator coordinator) {
        try {
            coordinatorDao.addCoordinator(coordinator);
        } catch (Exception e) {
            new GPSException(e.getMessage(), e);
        }
    }

    @Override
    public Coordinator getCoordinatorByNameAndIntranetId(String name, String intranetId) {
        Coordinator coordinator = null;
        try {
            coordinator = coordinatorDao.getCoordinatorByNameAndIntranetId(name, intranetId);
        } catch (Exception e) {
            throw new GPSException(e.getMessage(), e);
        }
        return coordinator;
    }

    @Override
    @Transactional
    public void updateCoordinator(Coordinator coordinator) {
        try {
            coordinatorDao.updateCoordinator(coordinator);
        } catch (Exception e) {
            throw new GPSException(e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void saveRcaCoordinator(RcaCoordinator rcaCoordinator) {
        try {
            rcaCoordinatorDao.addRcaCoordinator(rcaCoordinator);
        } catch (Exception e) {
            new GPSException(e.getMessage(), e);
        }
    }

    @Override
    public RcaCoordinator getRcaCoordinatorByContractIdAndCoordinatorId(Integer contractId, Integer coordinatorId) {
        RcaCoordinator rcaCoordinator = null;
        try {
            rcaCoordinator = rcaCoordinatorDao.getRcaCoordinatorByContractIdAndCoordinatorId(contractId, coordinatorId);
        } catch (Exception e) {
            //   throw new GPSException(e.getMessage(), e);
        }
        return rcaCoordinator;
    }

    @Override
    public RcaCoordinator getRcaCoordinatorById(Integer rcaCoordinatorId) {
        RcaCoordinator rcaCoordinator = null;
        try {
            rcaCoordinator = rcaCoordinatorDao.getRcaCoordinatorById(rcaCoordinatorId);
        } catch (Exception e) {
            throw new GPSException(e.getMessage(), e);
        }
        return rcaCoordinator;
    }

    @Override
    public GpsUser getUserByIntranetId(String intranetId) {
        GpsUser user = null;
        try {
            user = gpsUserDao.getGpsUserByEmail(intranetId);
        } catch (Exception e) {
            //throw new GPSException(e.getMessage(), e);
        }
        return user;

    }

    @Override
    @Transactional
    public void addUser(GpsUser dbUser) {
        try {
            gpsUserDao.addGpsUser(dbUser);
        } catch (Exception e) {
            throw new GPSException(e.getMessage(), e);
        }
    }

    @Override
    public List<Contract> getAllContractsForRcaForm(String sessionId) {
        return contractDao.getAllContractsForRcaForm(sessionId);
    }

    @Override
    @Transactional
    public String addRcaCoordinator(Contract contract) {
        String newlyAddedId = null;
        RcaCoordinator rcaCoordinator = new RcaCoordinator();
        Coordinator coordinator = new Coordinator();
        coordinatorDao.addCoordinator(coordinator);
        rcaCoordinator.setCoordinator(coordinator);
        rcaCoordinator.setContract(contract);
        try {
            Integer rcaCoId = rcaCoordinatorDao.saveRcaCoordinator(rcaCoordinator);
            if (rcaCoId != null) {
                newlyAddedId = String.valueOf(rcaCoId);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return newlyAddedId;
    }

    @Override
    @Transactional
    public Boolean deleteRcaCoordinator(Integer rcaCoordinatorId) {
        RcaCoordinator rcaCoordinator = rcaCoordinatorDao.getRcaCoordinatorById(rcaCoordinatorId);
        if (rcaCoordinator != null) {
            try {
                // coordinatorDao.deleteCoordinator(rcaCoordinator.getCoordinator());
                rcaCoordinatorDao.deleteRcaCoordinator(rcaCoordinator);
                return true;
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return false;
    }

    @Override
    public RcaEmailNotificationSetting addRcaEmailSettingNotification(RcaEmailNotificationSetting notificationSetting) {
        return null;
    }

    @Override
    @Transactional
    public void loadRcaEmailSettings(Contract contract) {
        RcaEmailNotificationSetting rcaEmailNotificationSetting = null;
        if (contract.getContractHelper().getEmailNotificationSetting() == null) {
            rcaEmailNotificationSetting = rcaEmailNotificationSettingDao.getRcaEmailNotificationSettingByContractId(contract.getContractId());
            if (rcaEmailNotificationSetting == null) {
                rcaEmailNotificationSetting = buildRcaEmailSettings(contract);
                rcaEmailNotificationSetting = rcaEmailNotificationSettingDao.saveRcaEmailNotificationSetting(rcaEmailNotificationSetting);
            }
            contract.getContractHelper().setEmailNotificationSetting(rcaEmailNotificationSetting);
        }
    }



    private void prepareContractForSaveUpdate(Contract contract, Contract dbContract) {
        ContractHelper helper = contract.getContractHelper();

        if (helper.getFormAction().equals("reject") && !dbContract.getState().equals("0")) {
            log.info("contract form rejected; moving contract to approved state");
            dbContract.setState("0");
        } else if (helper.getFormAction().equals("Submit") && !dbContract.getState().equals("2")) {
            log.info("contract form submitted; moving contract to approved state");
            dbContract.setState("2");
        } else if (helper.getFormAction().equals("reset")) {
            log.info("resetting form");
            dbContract.setState("0");
            resetForm(contract, dbContract);
            return;
        } else {
            log.warn("can't understand what to do with posted contract form?");
        }


        dbContract.setStartDate(!isNullOrEmpty(contract.getContractStartDate()) ? getTimeStamp(contract.getContractStartDate()) : null);
        dbContract.setEndDate(!isNullOrEmpty(contract.getContractEndDate()) ? getTimeStamp(contract.getContractEndDate()) : null);
        dbContract.setApplicationDesc(contract.getApplicationDesc());
        dbContract.setBtmtId(contract.getBtmtId());
        dbContract.setApplicationAlias(contract.getApplicationAlias());
        dbContract.setRcaCoordinators(contract.getRcaCoordinators());
        dbContract.setRcaCoordinatorId(contract.getRcaCoordinatorId());
        dbContract.setApplicationName(contract.getApplicationName());
        dbContract.setUpdatedOn(new Timestamp(new Date().getTime()));
        dbContract.setGpsUser(contract.getGpsUser());

        List<ContractContact> contractContacts = new ArrayList<ContractContact>();
        ContactType aoType = new ContactType();
        aoType.setCtId(1);
        ContractContact ao = null;
        for (ContractContact contactContact : dbContract.getContractContacts()) {
            if (contactContact.getContactType().getCtId() == 1) {
                ao = contactContact;
            }
        }
        if (ao == null) {
            ao = new ContractContact();
            ao.setContactType(aoType);
        }
        ao.setContactName(helper.getAoName());
        ao.setContactIntranetId(helper.getAoEmail());
        ao.setContractPhone(helper.getAoPhone());
        ao.setContract(contract);
        if (!(helper.getAoEmail() == null || helper.getAoEmail().equals(""))) {
            contractContacts.add(ao);
        }

        ContactType afpType = new ContactType();
        afpType.setCtId(2);
        ContractContact afp = null;
        for (ContractContact contactContact : dbContract.getContractContacts()) {
            if (contactContact.getContactType().getCtId() == 2) {
                afp = contactContact;
            }
        }
        if (afp == null) {
            afp = new ContractContact();
            afp.setContactType(afpType);
        }
        afp.setContactName(helper.getAfpName());
        afp.setContactIntranetId(helper.getAfpEmail());
        afp.setContractPhone(helper.getAfpPhone());
        afp.setContract(contract);
        if (!(helper.getAfpEmail() == null || helper.getAfpEmail().equals(""))) {
            contractContacts.add(afp);
        }


        ContactType bpoType = new ContactType();
        bpoType.setCtId(3);
        ContractContact bpo = null;
        for (ContractContact contactContact : dbContract.getContractContacts()) {
            if (contactContact.getContactType().getCtId() == 3) {
                bpo = contactContact;
            }
        }
        if (bpo == null) {
            bpo = new ContractContact();
            bpo.setContactType(bpoType);
        }
        bpo.setContactName(helper.getBpoName());
        bpo.setContactIntranetId(helper.getBpoEmail());
        bpo.setContractPhone(helper.getBpoPhone());
        bpo.setContract(contract);
        if (!(helper.getBpoEmail() == null || helper.getBpoEmail().equals(""))) {
            contractContacts.add(bpo);
        }


        ContactType sdmType = new ContactType();
        sdmType.setCtId(4);
        ContractContact sdm = null;
        for (ContractContact contactContact : dbContract.getContractContacts()) {
            if (contactContact.getContactType().getCtId() == 4) {
                sdm = contactContact;
            }
        }
        if (sdm == null) {
            sdm = new ContractContact();
            sdm.setContactType(sdmType);
        }
        sdm.setContactName(helper.getSdmName());
        sdm.setContactIntranetId(helper.getSdmEmail());
        sdm.setContractPhone(helper.getSdmPhone());
        sdm.setContract(contract);
        if (!(helper.getSdmEmail() == null || helper.getSdmEmail().equals(""))) {
            contractContacts.add(sdm);
        }


        ContactType dpeType = new ContactType();
        dpeType.setCtId(5);
        ContractContact dpe = null;
        for (ContractContact contactContact : dbContract.getContractContacts()) {
            if (contactContact.getContactType().getCtId() == 5) {
                dpe = contactContact;
            }

        }
        if (dpe == null) {
            dpe = new ContractContact();
            dpe.setContactType(dpeType);
        }
        dpe.setContactName(helper.getDpeName());
        dpe.setContactIntranetId(helper.getDpeEmail());
        dpe.setContractPhone(helper.getDpePhone());
        dpe.setContract(contract);
        if (!(helper.getDpeEmail() == null || helper.getDpeEmail().equals(""))) {
            contractContacts.add(dpe);
        }

        ContactType pcType = new ContactType();
        pcType.setCtId(6);
        ContractContact pc = null;
        for (ContractContact contactContact : dbContract.getContractContacts()) {
            if (contactContact.getContactType().getCtId() == 6) {
                pc = contactContact;
            }

        }
        if (pc == null) {
            pc = new ContractContact();
            pc.setContactType(pcType);
        }
        pc.setContactName(helper.getPcName());
        pc.setContactIntranetId(helper.getPcEmail());
        pc.setContractPhone(helper.getPcPhone());
        pc.setContract(contract);
        if (!(helper.getPcEmail() == null || helper.getPcEmail().equals(""))) {
            contractContacts.add(pc);
        }


        if (contractContacts.size() > 0) {
            dbContract.setContractContacts(contractContacts);
        }

        java.util.Date date = new java.util.Date();
        dbContract.setUpdatedOn(new Timestamp(date.getTime()));
        //dbContract.setGpsUser(contract.getGpsUser());
    }

    private void resetForm(Contract contract, Contract dbContract) {
        // TODO Auto-generated method stub
        ContractHelper helper = contract.getContractHelper();
        Customer customer = contract.getCustomer();

        java.util.Date date = new java.util.Date();
        dbContract.setUpdatedOn(new Timestamp(date.getTime()));
        //dbContract.setGpsUser(contract.getGpsUser());

        dbContract.setApplicationAlias(null);
        dbContract.setApplicationDesc(null);
        dbContract.setBtmtId(null);

        dbContract.setContractEndDate(null);
        dbContract.setContractStartDate(null);
        dbContract.setCustomer(null);

        dbContract.setEndDate(null);
        dbContract.setStartDate(null);

        helper.setContractPhaseId(null);
        helper.setIsUnderGoingTransition(null);

        dbContract.setReportingExcludeFlag("N");

        dbContract.setReportingIncludeFlag("N");

        customer.setName(null);

        Set<ContractContact> contractContacts = new HashSet<ContractContact>();
        ContactType aoType = new ContactType();
        aoType.setCtId(1);
        ContractContact ao = null;
        for (ContractContact contactContact : dbContract.getContractContacts()) {
            if (contactContact.getContactType().getCtId() == 1) {
                ao = contactContact;
            }
        }
        if (ao == null) {
            ao = new ContractContact();
            ao.setContactType(aoType);
        }
        ao.setContactName(helper.getAoName());
        ao.setContactIntranetId(helper.getAoEmail());
        ao.setContractPhone(helper.getAoPhone());
        ao.setContract(contract);
        if (!(helper.getAoEmail() == null || helper.getAoEmail().equals(""))) {
            contractContacts.add(ao);
        }


        ContactType afpType = new ContactType();
        afpType.setCtId(2);
        ContractContact afp = null;
        for (ContractContact contactContact : dbContract.getContractContacts()) {
            if (contactContact.getContactType().getCtId() == 2) {
                afp = contactContact;
            }
        }
        if (afp == null) {
            afp = new ContractContact();
            afp.setContactType(afpType);
        }
        afp.setContactName(helper.getAfpName());
        afp.setContactIntranetId(helper.getAfpEmail());
        afp.setContractPhone(helper.getAfpPhone());
        afp.setContract(contract);
        if (!(helper.getAfpEmail() == null || helper.getAfpEmail().equals(""))) {
            contractContacts.add(afp);
        }


        ContactType bpoType = new ContactType();
        bpoType.setCtId(3);
        ContractContact bpo = null;
        for (ContractContact contactContact : dbContract.getContractContacts()) {
            if (contactContact.getContactType().getCtId() == 3) {
                bpo = contactContact;
            }
        }
        if (bpo == null) {
            bpo = new ContractContact();
            bpo.setContactType(bpoType);
        }
        bpo.setContactName(helper.getPeName());
        bpo.setContactIntranetId(helper.getPeEmail());
        bpo.setContractPhone(helper.getPePhone());
        bpo.setContract(contract);
        if (!(helper.getBpoEmail() == null || helper.getBpoEmail().equals(""))) {
            contractContacts.add(bpo);
        }


        ContactType sdmType = new ContactType();
        sdmType.setCtId(4);
        ContractContact sdm = null;
        for (ContractContact contactContact : dbContract.getContractContacts()) {
            if (contactContact.getContactType().getCtId() == 4) {
                sdm = contactContact;
            }
        }
        if (sdm == null) {
            sdm = new ContractContact();
            sdm.setContactType(sdmType);
        }
        sdm.setContactName(helper.getSdmName());
        sdm.setContactIntranetId(helper.getSdmEmail());
        sdm.setContractPhone(helper.getSdmPhone());
        sdm.setContract(contract);
        if (!(helper.getSdmEmail() == null || helper.getSdmEmail().equals(""))) {
            contractContacts.add(sdm);
        }


        ContactType dpeType = new ContactType();
        dpeType.setCtId(5);
        ContractContact dpe = null;
        for (ContractContact contactContact : dbContract.getContractContacts()) {
            if (contactContact.getContactType().getCtId() == 5) {
                dpe = contactContact;
            }

        }
        if (dpe == null) {
            dpe = new ContractContact();
            dpe.setContactType(dpeType);
        }
        dpe.setContactName(helper.getDpeName());
        dpe.setContactIntranetId(helper.getDpeEmail());
        dpe.setContractPhone(helper.getDpePhone());
        dpe.setContract(contract);
        if (!(helper.getDpeEmail() == null || helper.getDpeEmail().equals(""))) {
            contractContacts.add(dpe);
        }

        ContactType pcType = new ContactType();
        pcType.setCtId(6);
        ContractContact pc = null;
        for (ContractContact contactContact : dbContract.getContractContacts()) {
            if (contactContact.getContactType().getCtId() == 6) {
                pc = contactContact;
            }

        }
        if (pc == null) {
            pc = new ContractContact();
            pc.setContactType(pcType);
        }
        pc.setContactName(helper.getPcName());
        pc.setContactIntranetId(helper.getPcEmail());
        pc.setContractPhone(helper.getPcPhone());
        pc.setContract(contract);
        if (!(helper.getPcEmail() == null || helper.getPcEmail().equals(""))) {
            contractContacts.add(pc);
        }

	/*	ContactType faType = new ContactType();
        faType.setCtId(7);
		ContractContact fa = null;
		for(ContractContact contactContact: dbContract.getContractContacts()){
			if(contactContact.getContactType().getCtId() == 7){
				fa = contactContact;
			}
			
		}
		if(fa == null){
			fa = new ContractContact();
			fa.setContactType(faType);
		}
		fa.setContactName(helper.getFaName());
		fa.setContactIntranetId(helper.getFaEmail());
		fa.setContractPhone(helper.getFaPhone());
		fa.setContract(contract);
		if(!(helper.getFaEmail() == null || helper.getFaEmail().equals(""))){
			contractContacts.add(fa);
		}
		
		ContactType bomType = new ContactType();
		bomType.setCtId(8);		
		ContractContact bom = null;
		for(ContractContact contactContact: dbContract.getContractContacts()){
			if(contactContact.getContactType().getCtId() == 8){
				bom = contactContact;
			}
			
		}
		if(bom == null){
			bom = new ContractContact();
			bom.setContactType(bomType);
		}
		bom.setContactName(helper.getBomName());
		bom.setContactIntranetId(helper.getBomEmail());
		bom.setContractPhone(helper.getBomPhone());
		bom.setContract(contract);
		if(!(helper.getBomEmail() == null || helper.getBomEmail().equals(""))){
			contractContacts.add(bom);
		}
	  */
    }


    private void prepareContractDisplay(Contract dbContract) {
        log.debug("displaying contract....." + dbContract.getContractId());
        ContractHelper helper = dbContract.getContractHelper() != null ? dbContract.getContractHelper() : new ContractHelper();
        dbContract.setContractHelper(helper);
        dbContract.setContractStartDate(dbContract.getStartDate() != null ? getFormattedDate(dbContract.getStartDate()) : "");
        dbContract.setContractEndDate(dbContract.getEndDate() != null ? getFormattedDate(dbContract.getEndDate()) : "");
        List<String> contractScopes = new ArrayList<String>();
        helper.setContractScope(contractScopes);

        //Load the primary RCA Coordinator
        if (dbContract.getRcaCoordinatorId() != null && dbContract.getRcaCoordinatorId() > 0 && CollectionUtils.isNotEmpty(dbContract.getRcaCoordinators())) {
            for (RcaCoordinator priRcaCoordinator : dbContract.getRcaCoordinators()) {
                if (priRcaCoordinator.getRcaCoordinatorId().equals(dbContract.getRcaCoordinatorId())) {
                    helper.setPriRcacName(priRcaCoordinator.getCoordinator().getCoordinatorName());
                    helper.setPriRcacEmail(priRcaCoordinator.getCoordinator().getIntranetId());
                    helper.setPriRcacPhone(priRcaCoordinator.getCoordinator().getPhone());
                }
            }
        }

        // Load the secondary RCA Coordinators
        helper.setRcaCoordinators(new ArrayList<RcaCoordinator>(dbContract.getRcaCoordinators()));


        for (ContractContact contactContact : dbContract.getContractContacts()) {
            if (contactContact.getContactType().getCtId() == 1) {
                helper.setAoName(contactContact.getContactName());
                helper.setAoEmail(contactContact.getContactIntranetId());
                helper.setAoPhone(contactContact.getContractPhone());
            }

            if (contactContact.getContactType().getCtId() == 2) {
                helper.setAfpName(contactContact.getContactName());
                helper.setAfpEmail(contactContact.getContactIntranetId());
                helper.setAfpPhone(contactContact.getContractPhone());
            }

            if (contactContact.getContactType().getCtId() == 3) {
                helper.setBpoName(contactContact.getContactName());
                helper.setBpoEmail(contactContact.getContactIntranetId());
                helper.setBpoPhone(contactContact.getContractPhone());
            }

            if (contactContact.getContactType().getCtId() == 4) {
                helper.setSdmName(contactContact.getContactName());
                helper.setSdmEmail(contactContact.getContactIntranetId());
                helper.setSdmPhone(contactContact.getContractPhone());
            }

            if (contactContact.getContactType().getCtId() == 5) {
                helper.setDpeName(contactContact.getContactName());
                helper.setDpeEmail(contactContact.getContactIntranetId());
                helper.setDpePhone(contactContact.getContractPhone());
            }

            if (contactContact.getContactType().getCtId() == 6) {
                helper.setPcName(contactContact.getContactName());
                helper.setPcEmail(contactContact.getContactIntranetId());
                helper.setPcPhone(contactContact.getContractPhone());
            }

        }
    }

    private boolean isNullOrEmpty(String str) {
        return str == null || str.equals("") ? true : false;
    }

    private String getFormattedDate(Date date) {
        return dateFormat.format(date);

    }

    private Timestamp getTimeStamp(String date) {
        try {
            Date date1 = dateFormat.parse(date);
            long time = date1.getTime();
            return new Timestamp(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private void processPrimaryRcaCoordinator(Contract contract) {

        ContractHelper contractHelper = contract.getContractHelper();
        if (StringUtils.isNotBlank(contractHelper.getPriRcacEmail()) && StringUtils.isNotBlank(contractHelper.getPriRcacName())) {
            Coordinator priCoordinator = getCoordinatorByNameAndIntranetId(contractHelper.getPriRcacName(), contractHelper.getPriRcacEmail());
            saveOrUpdateCoordinator(contractHelper, priCoordinator);
            Coordinator dbPriCoordinator = getCoordinatorByNameAndIntranetId(contractHelper.getPriRcacName(), contractHelper.getPriRcacEmail());
            RcaCoordinator dbPriRcaCoordinator = getRcaCoordinatorByContractIdAndCoordinatorId(contract.getContractId(), dbPriCoordinator.getCoordinatorId());
            if (dbPriRcaCoordinator == null) {
                dbPriRcaCoordinator = new RcaCoordinator();
                dbPriRcaCoordinator.setContract(contract);
                dbPriRcaCoordinator.setCoordinator(dbPriCoordinator);
                saveRcaCoordinator(dbPriRcaCoordinator);
            }
            contract.setRcaCoordinatorId(dbPriRcaCoordinator.getRcaCoordinatorId());

            // Save as User if it doesn't exist
            GpsUser user = gpsUserDao.getGpsUserByEmail(dbPriCoordinator.getIntranetId());
            if (user == null) {
                user = new GpsUser();
                user.setEmail(priCoordinator.getIntranetId());
                user.setNotesId(BluePages.getNotesIdFromIntranetId(priCoordinator.getIntranetId()));
                gpsUserDao.addGpsUser(user);
            }

            // Save as Coordinator in user role
            GpsUser dbUser = gpsUserDao.getGpsUserByEmail(dbPriCoordinator.getIntranetId());
            UserRole userRole = userRoleService.getUserRolesByContractIdAndUserIdAndRole(contract.getContractId(), dbUser.getUserId(), "coordinator");
            if (userRole == null) {
                userRole = new UserRole();
                userRole.setGpsUser(user);
                userRole.setContract(contract);
                userRole.setRole("coordinator");
                userRoleService.saveUserRole(userRole);
            }

        }
    }

    private void saveOrUpdateCoordinator(ContractHelper contractHelper, Coordinator priCoordinator) {
        if (priCoordinator == null) {
            priCoordinator = new Coordinator();
            priCoordinator.setCoordinatorName(contractHelper.getPriRcacName());
            priCoordinator.setIntranetId(contractHelper.getPriRcacEmail());
            priCoordinator.setPhone(contractHelper.getPriRcacPhone());
            saveCoordinator(priCoordinator);
        } else {
            priCoordinator.setCoordinatorName(contractHelper.getPriRcacName());
            priCoordinator.setIntranetId(contractHelper.getPriRcacEmail());
            priCoordinator.setPhone(contractHelper.getPriRcacPhone());
            updateCoordinator(priCoordinator);
        }
    }

    private RcaEmailNotificationSetting buildRcaEmailSettings(Contract contract) {
        RcaEmailNotificationSetting rcaEmailNotificationSetting;
        rcaEmailNotificationSetting = new RcaEmailNotificationSetting();
        rcaEmailNotificationSetting.setRcaCompletionTargetBusinessDays(10);
        rcaEmailNotificationSetting.setRcaRoutingMatrixEnabled("Y");
        rcaEmailNotificationSetting.setRcaWorkflowNotificationEnabled("Y");
        rcaEmailNotificationSetting.setNewRcaNotificationToCoordinator("Y");
        rcaEmailNotificationSetting.setNewRcaNotificationToCoordinator("Y");
        rcaEmailNotificationSetting.setRcaAssignmentNotificationToOwner("Y");
        rcaEmailNotificationSetting.setRcaNotAcceptedReminderForOwner("Y");
        rcaEmailNotificationSetting.setRcaNotAcceptedReminderForOwnerDays(3);
        rcaEmailNotificationSetting.setRcaNotSubmittedReminderForOwner("Y");
        rcaEmailNotificationSetting.setRcaNotSubmittedReminderForOwnerDays(10);
        rcaEmailNotificationSetting.setRcaAwaitingApprovalNotification("Y");
        rcaEmailNotificationSetting.setRcaApprovedNotification("Y");
        rcaEmailNotificationSetting.setRcaRejectedNotification("Y");
        rcaEmailNotificationSetting.setRcaNotApprovedReminder("Y");
        rcaEmailNotificationSetting.setRcaNotApprovedReminderDaysAfterCreation(3);
        rcaEmailNotificationSetting.setRcaNotApprovedReminderDuration(5);
        rcaEmailNotificationSetting.setNewActionItemNotification("Y");
        rcaEmailNotificationSetting.setActionNotClosedReminder("Y");
        rcaEmailNotificationSetting.setActionNotClosedReminderDaysBeforeTarget(5);
        rcaEmailNotificationSetting.setActionNotClosedReminderDuration(10);
        rcaEmailNotificationSetting.setRcaNotClosedReminder("Y");
        rcaEmailNotificationSetting.setRcaNotClosedReminderDaysAfterCreation(5);
        rcaEmailNotificationSetting.setRcaNotClosedReminderDuration(14);
        rcaEmailNotificationSetting.setRcaClosedNotification("Y");
        rcaEmailNotificationSetting.setRcaCancelledNotification("Y");
        rcaEmailNotificationSetting.setActionItemCancelledNotification("Y");
        rcaEmailNotificationSetting.setActionItemClosedNotification("Y");
        rcaEmailNotificationSetting.setContract(contract);
        return rcaEmailNotificationSetting;
    }


}
