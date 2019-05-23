
import React, { PureComponent } from 'react'
import { connect } from 'dva'
import Result from '../../components/Result'
import { Row, Col, Card, Form, Input, Select, Icon, Button, Dropdown, Menu, InputNumber, DatePicker, Modal, message,Alert } from 'antd';
import GlobalComponents from '../../custcomponents'
import PageHeaderLayout from '../../layouts/PageHeaderLayout'
import styles from './DoctorSchedule.search.less'
import ListViewTool from '../../common/ListView.tool'
import PermissionSettingService from '../../permission/PermissionSetting.service'
import appLocaleName from '../../common/Locale.tool'
const  {  hasCreatePermission,hasExecutionPermission,hasDeletePermission,hasUpdatePermission,hasReadPermission } = PermissionSettingService


const {handleSelectRows,handleStandardTableChange,
  showDeletionDialog,handleUpdate,handleDeletionModalVisible,
  handleElementCreate,toggleAssociateModalVisible,handleCloseAlert}=ListViewTool


const buttonMenuFor =(targetComponent, internalName, localeName)=> {
  const userContext = null
  return (
   <Menu >
     <Menu.Item key="1" onClick={()=>toggleAssociateModalVisible(targetComponent,internalName)}>{appLocaleName(userContext,"New")}{localeName}</Menu.Item>
     <Menu.Item key="2">{appLocaleName(userContext,"Merge")}{localeName}</Menu.Item>
    </Menu>
  )

}


 
const showListActionBar = (targetComponent)=>{

  const {selectedRows} = targetComponent.state
  const {metaInfo} = targetComponent.props
  const disable = (selectedRows.length === 0)
  const userContext = null
  return (<div className={styles.tableListOperator}>
  
 
              {hasCreatePermission(metaInfo)&&<Button icon="plus" type="primary" onClick={() => handleElementCreate(targetComponent)}>{appLocaleName(userContext,"New")}</Button>}
              
 {hasDeletePermission(metaInfo)&&<Button onClick={(event)=>handleDeletionModalVisible(event,targetComponent)} type="danger" icon="delete" disabled={disable}>{appLocaleName(userContext,"BatchDelete")}</Button>}
               

               {hasUpdatePermission(metaInfo)&&<Button onClick={()=>handleUpdate(targetComponent)} icon="update" disabled={disable}>{appLocaleName(userContext,"BatchUpdate")}</Button>}
 
 	
    
               
	</div> )


}


const showAssociateDialog = (targetComponent) => {
  const {data, owner, visible,onCancel,onCreate} = targetComponent.props
  const {currentAssociateModal} = targetComponent.state
  
  const {selectedRows} = targetComponent.state
  
  const { DoctorAssociateForm } = GlobalComponents
  const { PeriodAssociateForm } = GlobalComponents
  const { DepartmentAssociateForm } = GlobalComponents
  const { ExpenseTypeAssociateForm } = GlobalComponents
  const { HospitalAssociateForm } = GlobalComponents


  return (
  <div>
  
   
  
    <DoctorAssociateForm 
	visible={currentAssociateModal==='doctor'} 
	data={{doctorScheduleList:selectedRows}} owner={owner}  
	onCancel={()=>toggleAssociateModalVisible(targetComponent,'doctor')} 
	onCreate={()=>toggleAssociateModalVisible(targetComponent,'doctor')}/> <PeriodAssociateForm 
	visible={currentAssociateModal==='period'} 
	data={{doctorScheduleList:selectedRows}} owner={owner}  
	onCancel={()=>toggleAssociateModalVisible(targetComponent,'period')} 
	onCreate={()=>toggleAssociateModalVisible(targetComponent,'period')}/> <DepartmentAssociateForm 
	visible={currentAssociateModal==='department'} 
	data={{doctorScheduleList:selectedRows}} owner={owner}  
	onCancel={()=>toggleAssociateModalVisible(targetComponent,'department')} 
	onCreate={()=>toggleAssociateModalVisible(targetComponent,'department')}/> <ExpenseTypeAssociateForm 
	visible={currentAssociateModal==='expenseType'} 
	data={{doctorScheduleList:selectedRows}} owner={owner}  
	onCancel={()=>toggleAssociateModalVisible(targetComponent,'expenseType')} 
	onCreate={()=>toggleAssociateModalVisible(targetComponent,'expenseType')}/> <HospitalAssociateForm 
	visible={currentAssociateModal==='hospital'} 
	data={{doctorScheduleList:selectedRows}} owner={owner}  
	onCancel={()=>toggleAssociateModalVisible(targetComponent,'hospital')} 
	onCreate={()=>toggleAssociateModalVisible(targetComponent,'hospital')}/> 
 


    </div>
    
    
    
    )
}


class DoctorScheduleSearch extends PureComponent {
  state = {
    deletionModalVisible: false,
    selectedRows: [],
    showDeleteResult: false,
    currentAssociateModal: null,
  }

  render(){
    const { data, loading, count, currentPage, owner,partialList } = this.props;
    const {displayName} = owner.ref
    const { showDeleteResult, selectedRows, deletionModalVisible, showAssociatePaymentForm } = this.state;
    const {DoctorScheduleTable} = GlobalComponents;
    const {DoctorScheduleSearchForm} = GlobalComponents;
    const {DoctorScheduleModalTable} = GlobalComponents;
    
    const userContext = null
    
    
  
    return (
      <PageHeaderLayout title={`${displayName}:${this.props.name}${appLocaleName(userContext,"List")}`}>
        
        {showDeletionDialog(this,DoctorScheduleModalTable,"doctorScheduleIds")}
        {showAssociateDialog(this)}
      </PageHeaderLayout>
    )
  }
}

export default Form.create()(DoctorScheduleSearch)

