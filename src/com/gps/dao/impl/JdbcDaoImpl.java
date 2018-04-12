package com.gps.dao.impl;

import com.gps.dao.JdbcDao;
import com.gps.vo.Contract;
import com.gps.vo.ContractContact;
import com.gps.vo.helper.SearchFilterForCvr;
import org.springframework.jdbc.core.RowCallbackHandler;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class JdbcDaoImpl extends org.springframework.jdbc.core.support.JdbcDaoSupport implements JdbcDao {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	

	@Override
	public List<Contract> getContractsByFormName(String formName)
	{
		List paramList = new ArrayList();
		String pageQueryString = null;
		
		if(formName.equalsIgnoreCase("dpesc") || formName.equalsIgnoreCase("Monthly"))
		{
			pageQueryString = " select CONTRACT_ID, TITLE  from DB2INST1.CONTRACT where CURRENT_PHASE_ID NOT IN (?,?) OR AUTO_HEALTH_UPDATE <> ?";
			paramList.add(1);
			paramList.add(5);
			paramList.add("N");
		}
		if(formName.equalsIgnoreCase("cvr"))
		{
			pageQueryString = " select CONTRACT_ID, TITLE  from DB2INST1.CONTRACT where CURRENT_PHASE_ID <> ? OR AUTO_HEALTH_UPDATE <> ? AND TOWER_ID IN (?,?,?,?,?)";			
			paramList.add(5);
			paramList.add("N");
			paramList.add(1);
			paramList.add(2);
			paramList.add(4);
			paramList.add(6);
			paramList.add(9);
		}

		Object parameters[] = (Object[]) paramList.toArray(new Object[0]);
		final List<Contract> contractList = new ArrayList<Contract>();
		RowCallbackHandler callback = new RowCallbackHandler() {
			public void processRow(ResultSet resultSet) throws SQLException {
				Contract contract = new Contract();
				contract.setContractId(resultSet.getInt("CONTRACT_ID"));
				contract.setTitle(resultSet.getString("TITLE"));				
				contractList.add(contract);
			}
		};
		logger.info("jdbc >>> "+pageQueryString);
		this.getJdbcTemplate().query(pageQueryString, parameters, callback);

		return contractList; 
	}
	
	@Override
	public List<ContractContact> getDPEEmailIdByContractId(String contractId)
	{
		List paramList = new ArrayList();
		String pageQueryString = null;
		
		pageQueryString = " select CONTACT_INTRANET_ID from DB2INST1.CONTRACT_CONTACT where CT_ID = ? AND CONTRACT_ID = ?";
		paramList.add(5);
		paramList.add(Integer.parseInt(contractId));			
		

		Object parameters[] = (Object[]) paramList.toArray(new Object[0]);
		final List<ContractContact> contractList = new ArrayList<ContractContact>();
		RowCallbackHandler callback = new RowCallbackHandler() {
			public void processRow(ResultSet resultSet) throws SQLException {
				ContractContact contract = new ContractContact();			
				contractList.add(contract);
			}
		};
		logger.info("jdbc >>> "+pageQueryString);
		this.getJdbcTemplate().query(pageQueryString, parameters, callback);

		return contractList; 
	}
	
	@Override
	public List<ContractContact> getDPEEmailIdByContractIds(String[] contractIds)
	{
		List paramList = new ArrayList();
		String pageQueryString = null;
		
		pageQueryString = " select CONTACT_INTRANET_ID from DB2INST1.CONTRACT_CONTACT where CT_ID = ? AND CONTRACT_ID IN (";
		for(int i=0; i < contractIds.length; i++)
		{
			if(i == contractIds.length-1)
			{
				pageQueryString = pageQueryString +"?";
			}
			else
			{
				pageQueryString = pageQueryString +"?,";
			}
		}
		pageQueryString = pageQueryString + ")";
		paramList.add(5);
		for(int i=0; i < contractIds.length; i++)
		{
			paramList.add(Integer.parseInt(contractIds[i]));
		}					
		

		Object parameters[] = (Object[]) paramList.toArray(new Object[0]);
		final List<ContractContact> contractList = new ArrayList<ContractContact>();
		RowCallbackHandler callback = new RowCallbackHandler() {
			public void processRow(ResultSet resultSet) throws SQLException {
				ContractContact contract = new ContractContact();	
				contractList.add(contract);
			}
		};
		logger.info("jdbc >>> "+pageQueryString);
		this.getJdbcTemplate().query(pageQueryString, parameters, callback);

		return contractList; 
	}
	
	
	private Integer getCvrFormsBySearchFilterRowCount(SearchFilterForCvr searchFilter)
	{
		List paramList = new ArrayList();
		String pageQueryString = null;
		
		pageQueryString = " select count(*) as count from (select row_number() over (order by cnt.title asc) as rownumber, cvr.cvr_id,cvr.contract_id,cnt.title,cvr.form_date from db2inst1.cvr cvr inner join db2inst1.contract cnt on cvr.contract_id =cnt.contract_id where Month(cvr.form_date)=? and Year(cvr.form_date)=?)";
		
		Integer pageNo = searchFilter.getPage();
		Integer pageSize = SearchFilterForCvr.PAGE_SIZE;
		
		DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date parsedUtilDate = null;
		try {
			parsedUtilDate = formater.parse(searchFilter.getFormDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		
		Calendar calendar = Calendar.getInstance(); 
		
		calendar.setTime(parsedUtilDate);
		
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		
		paramList.add(month);
		paramList.add(year);
		
		Object parameters[] = (Object[]) paramList.toArray(new Object[0]);
		final List<Integer> contractList = new ArrayList<Integer>();
		RowCallbackHandler callback = new RowCallbackHandler() {
			public void processRow(ResultSet resultSet) throws SQLException {
				
				contractList.add(resultSet.getInt("COUNT"));
			}
		};
		logger.info("jdbc >>> "+pageQueryString);
		this.getJdbcTemplate().query(pageQueryString, parameters, callback);

		return contractList.get(0); 
	}
	
	
}
