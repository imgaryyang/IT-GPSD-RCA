
package com.gps.util;

import com.gps.service.ContractService;
import com.gps.service.GpsService;
import com.gps.vo.Contract;
import com.gps.vo.Customer;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class GpsCacheManager {
	

	@Autowired
	private GpsService gpsService;
	
	@Autowired
	private ContractService contractService;
	
	private Set<Contract> contractList;
	private List<Customer> customers = new ArrayList<Customer>();
	private List<Customer> allCustomers;
	private Map<String,String> accountCustomer;
	private Map<String,String> stateStages;
	private List<String> accesses = new ArrayList<String>();
	
	
	@PostConstruct
	public void init() {		
		buildCache();
	}
	
	
	private void buildCache(){	
		buildContractList();
		buildCustomerList();
		buildStageList();
		buildAccessList();
		buildAccountCustomers();
	}

	private void buildContractList(){
		contractList = new HashSet<Contract>(contractService.getAllContracts());
	}
	
	private void buildCustomerList(){
		customers = gpsService.getCustomers();
	}
	
	public static void main(String[] args) {
		GpsCacheManager g = new GpsCacheManager(); 
		g.buildAccountCustomers();
		for(Map.Entry<String, String> mapData : g.accountCustomer.entrySet()) {
		    System.out.println("Key : " +mapData.getKey()+ " => "+mapData.getValue());
		    }
	}
	
	private void buildAccountCustomers(){
		accountCustomer = new TreeMap<String, String>();
		accountCustomer.put("AAP","AAP");
		accountCustomer.put("AbbVie","AbbVie");
		 accountCustomer.put("Dow Chemical", "Dow Chemical");
		 accountCustomer.put("Marathon","Marathon");
		 accountCustomer.put("Toshiba","Toshiba");
		 accountCustomer.put("DnB", "DnB");
		 accountCustomer.put("Mead Johnson", "Mead Johnson");
		 accountCustomer.put("Twitter", "Twitter");
		 accountCustomer.put("ADP", "ADP");
		 accountCustomer.put("eLearning", "eLearning");
		 accountCustomer.put("MMI", "MMI");
		 accountCustomer.put("Tyco", "Tyco");
		 accountCustomer.put("Aetna", "Aetna");
		 accountCustomer.put("Federal Mogul", "Federal Mogul");
		 accountCustomer.put("Panasonic", "Panasonic");
		 accountCustomer.put("Unilever", "Unilever");
		 accountCustomer.put("ACG", "ACG");
		 accountCustomer.put("Friesland Campina", "Friesland Campina");
		 accountCustomer.put("Pearson", "Pearson");
		 accountCustomer.put("Verizon", "Verizon");
		 accountCustomer.put("AMAT", "AMAT");
		 accountCustomer.put("Goodyear", "Goodyear");
		 accountCustomer.put("Williams", "Williams");
		 accountCustomer.put("Adveo", "Adveo");
		 accountCustomer.put("Global Foundries", "Global Foundries");
		 accountCustomer.put("PepsiCo", "PepsiCo");
		 accountCustomer.put("Xerox", "Xerox");
		 accountCustomer.put("ASML", "ASML");
		 accountCustomer.put("Manpower", "Manpower");
		 accountCustomer.put("Rio Tinto", "Rio Tinto");
		 accountCustomer.put("TCCC / Coke", "TCCC / Coke");
		 accountCustomer.put("Animas", "Animas");
		 accountCustomer.put("Google", "Google");
		 accountCustomer.put("RnA (Analytics)", "RnA (Analytics)");
		 accountCustomer.put("BBC", "BBC");
		 accountCustomer.put("BP", "BP");
		 accountCustomer.put("Honda", "Honda");
		 accountCustomer.put("Cascade", "Cascade");
		 accountCustomer.put("Sara Lee", "Sara Lee");
		 accountCustomer.put("Imperial Tobacco Group", "Imperial Tobacco Group");
		 accountCustomer.put("CCH", "CCH");
		 accountCustomer.put("Intuit", "Intuit");
		 accountCustomer.put("Sharp", "Sharp");
		 accountCustomer.put("Cemex", "Cemex");
		 accountCustomer.put("ITRON", "ITRON");
		 accountCustomer.put("SIBCO", "SIBCO");
		 accountCustomer.put("Colgate", "Colgate");
		 accountCustomer.put("Jarden", "Jarden");
		 accountCustomer.put("Sony", "Sony");
		 accountCustomer.put("CrossMark", "CrossMark");
		 accountCustomer.put("JDE Coffee", "JDE Coffee");
		 accountCustomer.put("Sunoco", "Sunoco");
		 accountCustomer.put("Convatec", "Convatec");
		 accountCustomer.put("KBS", "KBS");
		 accountCustomer.put("Lenovo","Lenovo");
		 accountCustomer.put("Kuoni", "Kuoni");
		 accountCustomer.put("Telefonica", "Telefonica");
		 accountCustomer.put("Linde", "Linde");
		 accountCustomer.put("Toshiba Europe (non TGCS)", "Toshiba Europe (non TGCS)");
		accountCustomer.put("Other", "Other");
	}
	
	
	private void buildStageList(){	
		stateStages = new HashMap<String, String>();
		stateStages.put("0","Active");
		stateStages.put("1","Active");
		stateStages.put("2","Approved");	
	}
	
	private void buildAccessList(){		
		accesses.add("Viewable");
		accesses.add("Editable");
	}
	
	public List<Customer> getCustomers() {
		return customers;
	}
	
	public Map<String, String> getAccountCustomers() {
		return accountCustomer;
	}
	
	public List<Customer> getAllCustomers() {
		return allCustomers;
	}


	public Map<String, String> getStateChange() {
		return stateStages;
	}
	
	public List<String> getAccesses() {
		return accesses;
	}

	public Customer getCustomerByInac(int inac){
        if(CollectionUtils.isEmpty(customers)){
            customers = gpsService.getCustomers();
        }
		for(Customer customer : customers){
			if(customer.getInac() == inac){
				return customer;
			}
		}
		return null;
	}

	public Set<Contract> getContractList() {
		return contractList;
	}


	public void setContractList(Set<Contract> contractList) {
		this.contractList = contractList;
	}



}
