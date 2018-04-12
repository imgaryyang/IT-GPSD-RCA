package com.gps.service;

import com.gps.exceptions.GPSException;
import com.gps.vo.*;
import com.gps.vo.helper.*;

import javax.mail.MessagingException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

public interface RcaService {

    // RCA
    List<Rca> getAllRcas() throws GPSException;

    Rca getRcaById(Integer rcaId) throws GPSException;

    Set<Rca> getRcaListByMonthAndYear(int month, int year);

    List<Rca> getRcaListByContractIds(String contractIds);

    Page getRcaListBySearchFilter(SearchFilter searchFilter, String sessionId);

    List<RcaListing> getAllRcasAndActions();

    List<RcaListing> getRcasAndActionsByMonthAndYear(int month, int year);

    List<RcaListing> getRcasAndActionsByOutageDates(Timestamp startDate, Timestamp endDate);

    List<RcaCoordinator> getAllRcaCoordinators();

    List<RcaCoordinator> getRcaCoordinatorsByContractId(Integer contractId);

    String initiateRca(InitiateRcaForm initiateRcaForm, String email) throws GPSException, MessagingException;

    Rca saveRca(RcaForm rcaForm, String stage, String formAction, RcaEmailNotificationSetting emailNotificationSetting) throws GPSException;

    Rca submitRca(RcaForm rcaForm) throws Exception;

    Rca approveRca(Integer rcaId, String approvalComments, GpsUser loggedInUser, String roles);

    Rca rejectRca(Integer rcaId, String rejectionComments, GpsUser loggedInUser, String roles);

    Rca cancelRca(Integer rcaId, GpsUser loggedInUser, String loggedInUserRoles, String cancellationComments);

    Rca getRcaByNumber(String rcaNumber);

    Rca closeRca(RcaForm rcaForm, String rcaStage, String formAction);

    Rca reopenRca(RcaForm rcaForm, String reopenComments, GpsUser loggedInUser, String roles);

    // RCA Tickets
    RcaCoordinator getRcaCoordinatorById(Integer rcaId);

    List<RcaTicket> getRcaTicketsByRcaId(Integer rcaId);

    boolean addSecondaryTicket(RcaTicket rcaTicket);

    boolean deleteSecondaryTicket(RcaTicket rcaTicket);

    RcaTicket getRcaTicketById(Integer rcaTicketId);


    // RCA Causes
    List<RcaCause> getRcaContributingCauses(Integer rcaId);

    boolean addRcaContributingCause(RcaCause rcaCause);

    boolean deleteRcaContributingCause(RcaCause rcaCause);

    RcaCause getRcaCauseById(Integer rcaCauseId);

    //RCA History Log
    List<RcaHistoryLog> getRcaHistoryLogsByRcaId(Integer rcaId);

    // RCA Editors
    List<RcaEditor> getRcaEditorsByRcaId(Integer rcaId);


}
