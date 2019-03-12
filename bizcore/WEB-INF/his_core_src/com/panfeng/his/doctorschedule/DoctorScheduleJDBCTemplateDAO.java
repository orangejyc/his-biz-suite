
package com.panfeng.his.doctorschedule;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;
import com.panfeng.his.HisNamingServiceDAO;
import com.panfeng.his.BaseEntity;
import com.panfeng.his.SmartList;
import com.panfeng.his.AccessKey;
import com.panfeng.his.DateKey;
import com.panfeng.his.StatsInfo;
import com.panfeng.his.StatsItem;

import com.panfeng.his.MultipleAccessKey;
import com.panfeng.his.HisUserContext;


import com.panfeng.his.doctor.Doctor;
import com.panfeng.his.department.Department;
import com.panfeng.his.expensetype.ExpenseType;

import com.panfeng.his.department.DepartmentDAO;
import com.panfeng.his.expensetype.ExpenseTypeDAO;
import com.panfeng.his.doctor.DoctorDAO;



import org.springframework.dao.EmptyResultDataAccessException;

public class DoctorScheduleJDBCTemplateDAO extends HisNamingServiceDAO implements DoctorScheduleDAO{
 
 	
 	private  DoctorDAO  doctorDAO;
 	public void setDoctorDAO(DoctorDAO doctorDAO){
	 	this.doctorDAO = doctorDAO;
 	}
 	public DoctorDAO getDoctorDAO(){
	 	return this.doctorDAO;
 	}
 
 	
 	private  ExpenseTypeDAO  expenseTypeDAO;
 	public void setExpenseTypeDAO(ExpenseTypeDAO expenseTypeDAO){
	 	this.expenseTypeDAO = expenseTypeDAO;
 	}
 	public ExpenseTypeDAO getExpenseTypeDAO(){
	 	return this.expenseTypeDAO;
 	}
 
 	
 	private  DepartmentDAO  departmentDAO;
 	public void setDepartmentDAO(DepartmentDAO departmentDAO){
	 	this.departmentDAO = departmentDAO;
 	}
 	public DepartmentDAO getDepartmentDAO(){
	 	return this.departmentDAO;
 	}


			
		

	
	/*
	protected DoctorSchedule load(AccessKey accessKey,Map<String,Object> options) throws Exception{
		return loadInternalDoctorSchedule(accessKey, options);
	}
	*/
	
	protected String getIdFormat()
	{
		return getShortName(this.getName())+"%06d";
	}
	
	public DoctorSchedule load(String id,Map<String,Object> options) throws Exception{
		return loadInternalDoctorSchedule(DoctorScheduleTable.withId(id), options);
	}
	
	
	
	public DoctorSchedule save(DoctorSchedule doctorSchedule,Map<String,Object> options){
		
		String methodName="save(DoctorSchedule doctorSchedule,Map<String,Object> options)";
		
		assertMethodArgumentNotNull(doctorSchedule, methodName, "doctorSchedule");
		assertMethodArgumentNotNull(options, methodName, "options");
		
		return saveInternalDoctorSchedule(doctorSchedule,options);
	}
	public DoctorSchedule clone(String doctorScheduleId, Map<String,Object> options) throws Exception{
	
		return clone(DoctorScheduleTable.withId(doctorScheduleId),options);
	}
	
	protected DoctorSchedule clone(AccessKey accessKey, Map<String,Object> options) throws Exception{
	
		String methodName="clone(String doctorScheduleId,Map<String,Object> options)";
		
		assertMethodArgumentNotNull(accessKey, methodName, "accessKey");
		assertMethodArgumentNotNull(options, methodName, "options");
		
		DoctorSchedule newDoctorSchedule = loadInternalDoctorSchedule(accessKey, options);
		newDoctorSchedule.setVersion(0);
		
		

		
		saveInternalDoctorSchedule(newDoctorSchedule,options);
		
		return newDoctorSchedule;
	}
	
	
	
	

	protected void throwIfHasException(String doctorScheduleId,int version,int count) throws Exception{
		if (count == 1) {
			throw new DoctorScheduleVersionChangedException(
					"The object version has been changed, please reload to delete");
		}
		if (count < 1) {
			throw new DoctorScheduleNotFoundException(
					"The " + this.getTableName() + "(" + doctorScheduleId + ") has already been deleted.");
		}
		if (count > 1) {
			throw new IllegalStateException(
					"The table '" + this.getTableName() + "' PRIMARY KEY constraint has been damaged, please fix it.");
		}
	}
	
	
	public void delete(String doctorScheduleId, int version) throws Exception{
	
		String methodName="delete(String doctorScheduleId, int version)";
		assertMethodArgumentNotNull(doctorScheduleId, methodName, "doctorScheduleId");
		assertMethodIntArgumentGreaterThan(version,0, methodName, "options");
		
	
		String SQL=this.getDeleteSQL();
		Object [] parameters=new Object[]{doctorScheduleId,version};
		int affectedNumber = singleUpdate(SQL,parameters);
		if(affectedNumber == 1){
			return ; //Delete successfully
		}
		if(affectedNumber == 0){
			handleDeleteOneError(doctorScheduleId,version);
		}
		
	
	}
	
	
	
	
	

	public DoctorSchedule disconnectFromAll(String doctorScheduleId, int version) throws Exception{
	
		
		DoctorSchedule doctorSchedule = loadInternalDoctorSchedule(DoctorScheduleTable.withId(doctorScheduleId), emptyOptions());
		doctorSchedule.clearFromAll();
		this.saveDoctorSchedule(doctorSchedule);
		return doctorSchedule;
		
	
	}
	
	@Override
	protected String[] getNormalColumnNames() {

		return DoctorScheduleTable.NORMAL_CLOUMNS;
	}
	@Override
	protected String getName() {
		
		return "doctor_schedule";
	}
	@Override
	protected String getBeanName() {
		
		return "doctorSchedule";
	}
	
	
	
	
	
	protected boolean checkOptions(Map<String,Object> options, String optionToCheck){
	
 		return DoctorScheduleTokens.checkOptions(options, optionToCheck);
	
	}

 

 	protected boolean isExtractDoctorEnabled(Map<String,Object> options){
 		
	 	return checkOptions(options, DoctorScheduleTokens.DOCTOR);
 	}

 	protected boolean isSaveDoctorEnabled(Map<String,Object> options){
	 	
 		return checkOptions(options, DoctorScheduleTokens.DOCTOR);
 	}
 	

 	
  

 	protected boolean isExtractExpenseTypeEnabled(Map<String,Object> options){
 		
	 	return checkOptions(options, DoctorScheduleTokens.EXPENSETYPE);
 	}

 	protected boolean isSaveExpenseTypeEnabled(Map<String,Object> options){
	 	
 		return checkOptions(options, DoctorScheduleTokens.EXPENSETYPE);
 	}
 	

 	
  

 	protected boolean isExtractDepartmentEnabled(Map<String,Object> options){
 		
	 	return checkOptions(options, DoctorScheduleTokens.DEPARTMENT);
 	}

 	protected boolean isSaveDepartmentEnabled(Map<String,Object> options){
	 	
 		return checkOptions(options, DoctorScheduleTokens.DEPARTMENT);
 	}
 	

 	
 
		

	

	protected DoctorScheduleMapper getDoctorScheduleMapper(){
		return new DoctorScheduleMapper();
	}

	
	
	protected DoctorSchedule extractDoctorSchedule(AccessKey accessKey, Map<String,Object> loadOptions) throws Exception{
		try{
			DoctorSchedule doctorSchedule = loadSingleObject(accessKey, getDoctorScheduleMapper());
			return doctorSchedule;
		}catch(EmptyResultDataAccessException e){
			throw new DoctorScheduleNotFoundException("DoctorSchedule("+accessKey+") is not found!");
		}

	}

	
	

	protected DoctorSchedule loadInternalDoctorSchedule(AccessKey accessKey, Map<String,Object> loadOptions) throws Exception{
		
		DoctorSchedule doctorSchedule = extractDoctorSchedule(accessKey, loadOptions);
 	
 		if(isExtractDoctorEnabled(loadOptions)){
	 		extractDoctor(doctorSchedule, loadOptions);
 		}
  	
 		if(isExtractExpenseTypeEnabled(loadOptions)){
	 		extractExpenseType(doctorSchedule, loadOptions);
 		}
  	
 		if(isExtractDepartmentEnabled(loadOptions)){
	 		extractDepartment(doctorSchedule, loadOptions);
 		}
 
		
		return doctorSchedule;
		
	}

	 

 	protected DoctorSchedule extractDoctor(DoctorSchedule doctorSchedule, Map<String,Object> options) throws Exception{

		if(doctorSchedule.getDoctor() == null){
			return doctorSchedule;
		}
		String doctorId = doctorSchedule.getDoctor().getId();
		if( doctorId == null){
			return doctorSchedule;
		}
		Doctor doctor = getDoctorDAO().load(doctorId,options);
		if(doctor != null){
			doctorSchedule.setDoctor(doctor);
		}
		
 		
 		return doctorSchedule;
 	}
 		
  

 	protected DoctorSchedule extractExpenseType(DoctorSchedule doctorSchedule, Map<String,Object> options) throws Exception{

		if(doctorSchedule.getExpenseType() == null){
			return doctorSchedule;
		}
		String expenseTypeId = doctorSchedule.getExpenseType().getId();
		if( expenseTypeId == null){
			return doctorSchedule;
		}
		ExpenseType expenseType = getExpenseTypeDAO().load(expenseTypeId,options);
		if(expenseType != null){
			doctorSchedule.setExpenseType(expenseType);
		}
		
 		
 		return doctorSchedule;
 	}
 		
  

 	protected DoctorSchedule extractDepartment(DoctorSchedule doctorSchedule, Map<String,Object> options) throws Exception{

		if(doctorSchedule.getDepartment() == null){
			return doctorSchedule;
		}
		String departmentId = doctorSchedule.getDepartment().getId();
		if( departmentId == null){
			return doctorSchedule;
		}
		Department department = getDepartmentDAO().load(departmentId,options);
		if(department != null){
			doctorSchedule.setDepartment(department);
		}
		
 		
 		return doctorSchedule;
 	}
 		
 
		
		
  	
 	public SmartList<DoctorSchedule> findDoctorScheduleByDoctor(String doctorId,Map<String,Object> options){
 	
  		SmartList<DoctorSchedule> resultList = queryWith(DoctorScheduleTable.COLUMN_DOCTOR, doctorId, options, getDoctorScheduleMapper());
		// analyzeDoctorScheduleByDoctor(resultList, doctorId, options);
		return resultList;
 	}
 	 
 
 	public SmartList<DoctorSchedule> findDoctorScheduleByDoctor(String doctorId, int start, int count,Map<String,Object> options){
 		
 		SmartList<DoctorSchedule> resultList =  queryWithRange(DoctorScheduleTable.COLUMN_DOCTOR, doctorId, options, getDoctorScheduleMapper(), start, count);
 		//analyzeDoctorScheduleByDoctor(resultList, doctorId, options);
 		return resultList;
 		
 	}
 	public void analyzeDoctorScheduleByDoctor(SmartList<DoctorSchedule> resultList, String doctorId, Map<String,Object> options){
		if(resultList==null){
			return;//do nothing when the list is null.
		}
		
 		MultipleAccessKey filterKey = new MultipleAccessKey();
 		filterKey.put(DoctorSchedule.DOCTOR_PROPERTY, doctorId);
 		Map<String,Object> emptyOptions = new HashMap<String,Object>();
 		
 		StatsInfo info = new StatsInfo();
 		
 		
 		resultList.setStatsInfo(info);

 	
 		
 	}
 	@Override
 	public int countDoctorScheduleByDoctor(String doctorId,Map<String,Object> options){

 		return countWith(DoctorScheduleTable.COLUMN_DOCTOR, doctorId, options);
 	}
 	@Override
	public Map<String, Integer> countDoctorScheduleByDoctorIds(String[] ids, Map<String, Object> options) {
		return countWithIds(DoctorScheduleTable.COLUMN_DOCTOR, ids, options);
	}
 	
  	
 	public SmartList<DoctorSchedule> findDoctorScheduleByExpenseType(String expenseTypeId,Map<String,Object> options){
 	
  		SmartList<DoctorSchedule> resultList = queryWith(DoctorScheduleTable.COLUMN_EXPENSE_TYPE, expenseTypeId, options, getDoctorScheduleMapper());
		// analyzeDoctorScheduleByExpenseType(resultList, expenseTypeId, options);
		return resultList;
 	}
 	 
 
 	public SmartList<DoctorSchedule> findDoctorScheduleByExpenseType(String expenseTypeId, int start, int count,Map<String,Object> options){
 		
 		SmartList<DoctorSchedule> resultList =  queryWithRange(DoctorScheduleTable.COLUMN_EXPENSE_TYPE, expenseTypeId, options, getDoctorScheduleMapper(), start, count);
 		//analyzeDoctorScheduleByExpenseType(resultList, expenseTypeId, options);
 		return resultList;
 		
 	}
 	public void analyzeDoctorScheduleByExpenseType(SmartList<DoctorSchedule> resultList, String expenseTypeId, Map<String,Object> options){
		if(resultList==null){
			return;//do nothing when the list is null.
		}
		
 		MultipleAccessKey filterKey = new MultipleAccessKey();
 		filterKey.put(DoctorSchedule.EXPENSE_TYPE_PROPERTY, expenseTypeId);
 		Map<String,Object> emptyOptions = new HashMap<String,Object>();
 		
 		StatsInfo info = new StatsInfo();
 		
 		
 		resultList.setStatsInfo(info);

 	
 		
 	}
 	@Override
 	public int countDoctorScheduleByExpenseType(String expenseTypeId,Map<String,Object> options){

 		return countWith(DoctorScheduleTable.COLUMN_EXPENSE_TYPE, expenseTypeId, options);
 	}
 	@Override
	public Map<String, Integer> countDoctorScheduleByExpenseTypeIds(String[] ids, Map<String, Object> options) {
		return countWithIds(DoctorScheduleTable.COLUMN_EXPENSE_TYPE, ids, options);
	}
 	
  	
 	public SmartList<DoctorSchedule> findDoctorScheduleByDepartment(String departmentId,Map<String,Object> options){
 	
  		SmartList<DoctorSchedule> resultList = queryWith(DoctorScheduleTable.COLUMN_DEPARTMENT, departmentId, options, getDoctorScheduleMapper());
		// analyzeDoctorScheduleByDepartment(resultList, departmentId, options);
		return resultList;
 	}
 	 
 
 	public SmartList<DoctorSchedule> findDoctorScheduleByDepartment(String departmentId, int start, int count,Map<String,Object> options){
 		
 		SmartList<DoctorSchedule> resultList =  queryWithRange(DoctorScheduleTable.COLUMN_DEPARTMENT, departmentId, options, getDoctorScheduleMapper(), start, count);
 		//analyzeDoctorScheduleByDepartment(resultList, departmentId, options);
 		return resultList;
 		
 	}
 	public void analyzeDoctorScheduleByDepartment(SmartList<DoctorSchedule> resultList, String departmentId, Map<String,Object> options){
		if(resultList==null){
			return;//do nothing when the list is null.
		}
		
 		MultipleAccessKey filterKey = new MultipleAccessKey();
 		filterKey.put(DoctorSchedule.DEPARTMENT_PROPERTY, departmentId);
 		Map<String,Object> emptyOptions = new HashMap<String,Object>();
 		
 		StatsInfo info = new StatsInfo();
 		
 		
 		resultList.setStatsInfo(info);

 	
 		
 	}
 	@Override
 	public int countDoctorScheduleByDepartment(String departmentId,Map<String,Object> options){

 		return countWith(DoctorScheduleTable.COLUMN_DEPARTMENT, departmentId, options);
 	}
 	@Override
	public Map<String, Integer> countDoctorScheduleByDepartmentIds(String[] ids, Map<String, Object> options) {
		return countWithIds(DoctorScheduleTable.COLUMN_DEPARTMENT, ids, options);
	}
 	
 	
		
		
		

	

	protected DoctorSchedule saveDoctorSchedule(DoctorSchedule  doctorSchedule){
		
		if(!doctorSchedule.isChanged()){
			return doctorSchedule;
		}
		
		
		String SQL=this.getSaveDoctorScheduleSQL(doctorSchedule);
		//FIXME: how about when an item has been updated more than MAX_INT?
		Object [] parameters = getSaveDoctorScheduleParameters(doctorSchedule);
		int affectedNumber = singleUpdate(SQL,parameters);
		if(affectedNumber != 1){
			throw new IllegalStateException("The save operation should return value = 1, while the value = "
				+ affectedNumber +"If the value = 0, that mean the target record has been updated by someone else!");
		}
		
		doctorSchedule.incVersion();
		return doctorSchedule;
	
	}
	public SmartList<DoctorSchedule> saveDoctorScheduleList(SmartList<DoctorSchedule> doctorScheduleList,Map<String,Object> options){
		//assuming here are big amount objects to be updated.
		//First step is split into two groups, one group for update and another group for create
		Object [] lists=splitDoctorScheduleList(doctorScheduleList);
		
		batchDoctorScheduleCreate((List<DoctorSchedule>)lists[CREATE_LIST_INDEX]);
		
		batchDoctorScheduleUpdate((List<DoctorSchedule>)lists[UPDATE_LIST_INDEX]);
		
		
		//update version after the list successfully saved to database;
		for(DoctorSchedule doctorSchedule:doctorScheduleList){
			if(doctorSchedule.isChanged()){
				doctorSchedule.incVersion();
			}
			
		
		}
		
		
		return doctorScheduleList;
	}

	public SmartList<DoctorSchedule> removeDoctorScheduleList(SmartList<DoctorSchedule> doctorScheduleList,Map<String,Object> options){
		
		
		super.removeList(doctorScheduleList, options);
		
		return doctorScheduleList;
		
		
	}
	
	protected List<Object[]> prepareDoctorScheduleBatchCreateArgs(List<DoctorSchedule> doctorScheduleList){
		
		List<Object[]> parametersList=new ArrayList<Object[]>();
		for(DoctorSchedule doctorSchedule:doctorScheduleList ){
			Object [] parameters = prepareDoctorScheduleCreateParameters(doctorSchedule);
			parametersList.add(parameters);
		
		}
		return parametersList;
		
	}
	protected List<Object[]> prepareDoctorScheduleBatchUpdateArgs(List<DoctorSchedule> doctorScheduleList){
		
		List<Object[]> parametersList=new ArrayList<Object[]>();
		for(DoctorSchedule doctorSchedule:doctorScheduleList ){
			if(!doctorSchedule.isChanged()){
				continue;
			}
			Object [] parameters = prepareDoctorScheduleUpdateParameters(doctorSchedule);
			parametersList.add(parameters);
		
		}
		return parametersList;
		
	}
	protected void batchDoctorScheduleCreate(List<DoctorSchedule> doctorScheduleList){
		String SQL=getCreateSQL();
		List<Object[]> args=prepareDoctorScheduleBatchCreateArgs(doctorScheduleList);
		
		int affectedNumbers[] = batchUpdate(SQL, args);
		
	}
	
	
	protected void batchDoctorScheduleUpdate(List<DoctorSchedule> doctorScheduleList){
		String SQL=getUpdateSQL();
		List<Object[]> args=prepareDoctorScheduleBatchUpdateArgs(doctorScheduleList);
		
		int affectedNumbers[] = batchUpdate(SQL, args);
		
		
		
	}
	
	
	
	static final int CREATE_LIST_INDEX=0;
	static final int UPDATE_LIST_INDEX=1;
	
	protected Object[] splitDoctorScheduleList(List<DoctorSchedule> doctorScheduleList){
		
		List<DoctorSchedule> doctorScheduleCreateList=new ArrayList<DoctorSchedule>();
		List<DoctorSchedule> doctorScheduleUpdateList=new ArrayList<DoctorSchedule>();
		
		for(DoctorSchedule doctorSchedule: doctorScheduleList){
			if(isUpdateRequest(doctorSchedule)){
				doctorScheduleUpdateList.add( doctorSchedule);
				continue;
			}
			doctorScheduleCreateList.add(doctorSchedule);
		}
		
		return new Object[]{doctorScheduleCreateList,doctorScheduleUpdateList};
	}
	
	protected boolean isUpdateRequest(DoctorSchedule doctorSchedule){
 		return doctorSchedule.getVersion() > 0;
 	}
 	protected String getSaveDoctorScheduleSQL(DoctorSchedule doctorSchedule){
 		if(isUpdateRequest(doctorSchedule)){
 			return getUpdateSQL();
 		}
 		return getCreateSQL();
 	}
 	
 	protected Object[] getSaveDoctorScheduleParameters(DoctorSchedule doctorSchedule){
 		if(isUpdateRequest(doctorSchedule) ){
 			return prepareDoctorScheduleUpdateParameters(doctorSchedule);
 		}
 		return prepareDoctorScheduleCreateParameters(doctorSchedule);
 	}
 	protected Object[] prepareDoctorScheduleUpdateParameters(DoctorSchedule doctorSchedule){
 		Object[] parameters = new Object[11];
 
 		parameters[0] = doctorSchedule.getName();
 		parameters[1] = doctorSchedule.getScheduleDate();
 		parameters[2] = doctorSchedule.getPeriod(); 	
 		if(doctorSchedule.getDoctor() != null){
 			parameters[3] = doctorSchedule.getDoctor().getId();
 		}
 
 		parameters[4] = doctorSchedule.getAvailable();
 		parameters[5] = doctorSchedule.getPrice(); 	
 		if(doctorSchedule.getExpenseType() != null){
 			parameters[6] = doctorSchedule.getExpenseType().getId();
 		}
  	
 		if(doctorSchedule.getDepartment() != null){
 			parameters[7] = doctorSchedule.getDepartment().getId();
 		}
 		
 		parameters[8] = doctorSchedule.nextVersion();
 		parameters[9] = doctorSchedule.getId();
 		parameters[10] = doctorSchedule.getVersion();
 				
 		return parameters;
 	}
 	protected Object[] prepareDoctorScheduleCreateParameters(DoctorSchedule doctorSchedule){
		Object[] parameters = new Object[9];
		String newDoctorScheduleId=getNextId();
		doctorSchedule.setId(newDoctorScheduleId);
		parameters[0] =  doctorSchedule.getId();
 
 		parameters[1] = doctorSchedule.getName();
 		parameters[2] = doctorSchedule.getScheduleDate();
 		parameters[3] = doctorSchedule.getPeriod(); 	
 		if(doctorSchedule.getDoctor() != null){
 			parameters[4] = doctorSchedule.getDoctor().getId();
 		
 		}
 		
 		parameters[5] = doctorSchedule.getAvailable();
 		parameters[6] = doctorSchedule.getPrice(); 	
 		if(doctorSchedule.getExpenseType() != null){
 			parameters[7] = doctorSchedule.getExpenseType().getId();
 		
 		}
 		 	
 		if(doctorSchedule.getDepartment() != null){
 			parameters[8] = doctorSchedule.getDepartment().getId();
 		
 		}
 				
 				
 		return parameters;
 	}
 	
	protected DoctorSchedule saveInternalDoctorSchedule(DoctorSchedule doctorSchedule, Map<String,Object> options){
		
		saveDoctorSchedule(doctorSchedule);
 	
 		if(isSaveDoctorEnabled(options)){
	 		saveDoctor(doctorSchedule, options);
 		}
  	
 		if(isSaveExpenseTypeEnabled(options)){
	 		saveExpenseType(doctorSchedule, options);
 		}
  	
 		if(isSaveDepartmentEnabled(options)){
	 		saveDepartment(doctorSchedule, options);
 		}
 
		
		return doctorSchedule;
		
	}
	
	
	
	//======================================================================================
	 
 
 	protected DoctorSchedule saveDoctor(DoctorSchedule doctorSchedule, Map<String,Object> options){
 		//Call inject DAO to execute this method
 		if(doctorSchedule.getDoctor() == null){
 			return doctorSchedule;//do nothing when it is null
 		}
 		
 		getDoctorDAO().save(doctorSchedule.getDoctor(),options);
 		return doctorSchedule;
 		
 	}
 	
 	
 	
 	 
	
  
 
 	protected DoctorSchedule saveExpenseType(DoctorSchedule doctorSchedule, Map<String,Object> options){
 		//Call inject DAO to execute this method
 		if(doctorSchedule.getExpenseType() == null){
 			return doctorSchedule;//do nothing when it is null
 		}
 		
 		getExpenseTypeDAO().save(doctorSchedule.getExpenseType(),options);
 		return doctorSchedule;
 		
 	}
 	
 	
 	
 	 
	
  
 
 	protected DoctorSchedule saveDepartment(DoctorSchedule doctorSchedule, Map<String,Object> options){
 		//Call inject DAO to execute this method
 		if(doctorSchedule.getDepartment() == null){
 			return doctorSchedule;//do nothing when it is null
 		}
 		
 		getDepartmentDAO().save(doctorSchedule.getDepartment(),options);
 		return doctorSchedule;
 		
 	}
 	
 	
 	
 	 
	
 

	

		

	public DoctorSchedule present(DoctorSchedule doctorSchedule,Map<String, Object> options){
	

		return doctorSchedule;
	
	}
		

	

	protected String getTableName(){
		return DoctorScheduleTable.TABLE_NAME;
	}
	
	
	
	public void enhanceList(List<DoctorSchedule> doctorScheduleList) {		
		this.enhanceListInternal(doctorScheduleList, this.getDoctorScheduleMapper());
	}
	@Override
	public void collectAndEnhance(BaseEntity ownerEntity) {
		List<DoctorSchedule> doctorScheduleList = ownerEntity.collectRefsWithType(DoctorSchedule.INTERNAL_TYPE);
		this.enhanceList(doctorScheduleList);
		
	}
	
	@Override
	public SmartList<DoctorSchedule> findDoctorScheduleWithKey(MultipleAccessKey key,
			Map<String, Object> options) {
		
  		return queryWith(key, options, getDoctorScheduleMapper());

	}
	@Override
	public int countDoctorScheduleWithKey(MultipleAccessKey key,
			Map<String, Object> options) {
		
  		return countWith(key, options);

	}
	public Map<String, Integer> countDoctorScheduleWithGroupKey(String groupKey, MultipleAccessKey filterKey,
			Map<String, Object> options) {
			
  		return countWithGroup(groupKey, filterKey, options);

	}
	
	@Override
	public SmartList<DoctorSchedule> queryList(String sql, Object... parameters) {
	    return this.queryForList(sql, parameters, this.getDoctorScheduleMapper());
	}
}

