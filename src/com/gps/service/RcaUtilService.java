package com.gps.service;

import com.gps.util.UserSession;
import com.gps.vo.*;
import com.gps.vo.helper.RcaForm;
import com.gps.vo.helper.RcaListing;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RcaUtilService {

    public boolean isGlobalCoordinator(int userId);

    public boolean isGlobalDpe(int userId);

    public boolean isGlobalDelegate(int userId);

    public boolean isGlobalOwner(int userId);

    public boolean isGlobalEditor(int userId);

    public boolean isGlobalReader(int userId);

    public boolean isGlobalCreator(int userId);


    public List<Contract> loadInitiateRcaContracts(UserSession userSession, Map<String, Object> referenceData, HttpSession session);

    public List<String> loadPrimaryTickets(List<RcaListing> dataList);

    public Set<String> getRcaCoordinatorsFromRcaList(List<Rca> rcaList);

    Set<Coordinator> getCoordinators(Set<RcaCoordinator> rcaCoordinators);


    boolean isAdmin(int userId);

    boolean supportingFileProcessed(MultipartFile file, RcaForm rcaFormForm, BindingResult result, GpsUser loggedInUser);

    void saveFileInDb(MultipartFile file, String fileDescription, Rca rca, GpsUser loggedInUser);

    void processDeletedFiles(RcaForm rcaForm);

    void loadSupportingFiles(Rca rca, RcaForm rcaForm);

    void saveRcaTickets(List<RcaTicket> rcaTickets);

    void saveRcaCauses(List<RcaCause> rcaCauses);
}
