package com.gps.service.impl;

import com.gps.dao.JdbcDao;
import com.gps.repository.ContractRepository;
import com.gps.service.CvrService;
import com.gps.util.GpsCacheManager;
import com.gps.vo.Contract;
import com.gps.vo.ContractContact;
import com.gps.vo.EmailTemplate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Service("cvrService")
@Transactional
public class CvrServiceImpl implements CvrService {
	

	
	static final String ENROLL_GARBAGE_DATE_STRING_ = "1900-01-01 00:00:00.0";
	Log logger = LogFactory.getLog(CvrServiceImpl.class);
	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;
	
	@Autowired
	RepositoryFactorySupport repositoryFactory;
	@Autowired
	JdbcDao cvrJdbcDao;
	
	private static SessionFactory sessionFactory;
    private static StandardServiceRegistryBuilder serviceRegistry;
    
    @Autowired
	GpsCacheManager gpsCacheManager;

    
	
	@Resource
	private DataSource dataSource;
	
//	@Resource
//	private SessionFactory sessionFactory;
	
	private SimpleJdbcInsert simpleJdbcInsert;  
	
	
    public void setDataSource(DataSource dataSource) {  
          
        this.simpleJdbcInsert =   
                new SimpleJdbcInsert(dataSource)  
                 .withTableName("cvr")
                 .usingGeneratedKeyColumns("cvr_id");  
    }
	
	
	@Override
	public ArrayList<Contract> findAllContracts() {
				
		return repositoryFactory
				.getRepository(ContractRepository.class)
				.findAll();
	}

	
	@Override
	public List<Contract> getContractsByFormName(String formName)
	{
		//return cvrJdbcDao.getContractsByFormName(formName);
	
		Query jpqlQuery = null;
		
		if(formName.equalsIgnoreCase("monthly")) {
			String autoHelthQuery = "Select c.contractId,c.title FROM Contract c where currentPhaseId NOT IN (:a,:b) OR autoHealthUpdate <> :c order by c.title ";
			String query = "Select c.contractId,c.title FROM Contract c where currentPhaseId NOT IN (1,5) and c.lobName NOT IN ('Concentrix') order by c.title ";
			jpqlQuery = entityManager.createQuery(query);
		}
		
		if(formName.equalsIgnoreCase("cvr")) {
			String query = "Select c.contractId,c.title FROM Contract c join c.tower t where (c.currentPhaseId <> 5 OR c.autoHealthUpdate <> :b) AND t.towerId IN (1,2,4,6,9) order by c.title ";
			
			jpqlQuery = entityManager.createQuery(query);
			
			jpqlQuery.setParameter("b" , "N");
		}
		
		if(formName.equalsIgnoreCase("weekly")) {
			String query = "Select c.contractId,c.title FROM Contract c where currentPhaseId NOT IN (1,5) and c.lobName NOT IN ('Concentrix') order by c.title ";
			jpqlQuery = entityManager.createQuery(query);
		}
		
		if(formName.equalsIgnoreCase("weeklyTrans"))
		{
			String query = "Select c.contractId,c.title FROM Contract c " +
					"where c.contractId in ( " +
						"Select sc.contractId FROM Contract sc LEFT JOIN sc.contractPhases phase  where sc.currentPhaseId = 1 " +
						"OR (sc.currentPhaseId = 3 AND phase.isInTransition = 'Y') " +
					") and c.lobName NOT IN ('Concentrix') order by c.title ";
			
			jpqlQuery = entityManager.createQuery(query);		
		}
					
		List<Object[]> list = null;
		List<Contract> contractList = null;
		try{
			list = jpqlQuery.getResultList();
		}
		catch (NoResultException e){
			list = null;
		}
		finally
		{
			if(list != null)
			{
				contractList = new ArrayList<Contract>();
				for(Object[] obj:list)
				{
					Contract contract = new Contract();
					contract.setContractId((Integer) obj[0]);
					contract.setTitle((String) obj[1]);					
					contractList.add(contract);
				}
			}
			entityManager.flush();
			entityManager.clear();
		}
				
		
		return contractList;
	}
	
	@Override
	public List<ContractContact> getDPEEmailIdByContractId(String contractId)
	{
		return cvrJdbcDao.getDPEEmailIdByContractId(contractId);
	}
	
	@Override
	public List<ContractContact> getDPEEmailIdByContractIds(String[] contractIds)
	{
		return cvrJdbcDao.getDPEEmailIdByContractIds(contractIds);
	}
	
	@Override
	public List<EmailTemplate> getEmailTemplateList()
	{
		return entityManager.createQuery("from EmailTemplate").getResultList();
	}
	
	@Override
	public EmailTemplate getEmailTemplateById(String emailTemplateId)
	{
		Query query = entityManager.createQuery("FROM EmailTemplate e WHERE e.emailTemplateId = :id");
		query.setParameter("id", Integer.parseInt(emailTemplateId));
		
		return	(EmailTemplate) query.getSingleResult();
	}
		
	@Override
	public List<EmailTemplate> getEmailTemplateListByFormName(String formName)
	{
		Query query = entityManager.createQuery("FROM EmailTemplate e WHERE e.formType = :formName");
		query.setParameter("formName", formName);
		
		return	query.getResultList();
	}
	
	
	private boolean isNullOrEmpty(String str){
		return str == null || str.equals("")? true : false; 
	}



}