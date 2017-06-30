package cn.com.se.enums.军哥代码学习;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.deppon.dpap.module.context.DateUtils;
import com.deppon.dpap.module.dict.server.service.IDictionaryService;
import com.deppon.dpap.module.dict.shared.domain.DictionaryDataEntity;
import com.deppon.dpap.module.monitor.shared.domain.JobMappingEntity;
import com.deppon.dpap.module.organization.server.dao.IEmployeeDao;
import com.deppon.dpap.module.statepush.shared.domain.ParameterModel;
import com.deppon.dpap.module.tianfangpush.server.dao.IBaseDataSyncPushDao;
import com.deppon.dpap.module.tianfangpush.server.entity.EmployeePushEntity;
import com.deppon.dpap.module.tianfangpush.server.entity.SalesDeptPushEntity;
import com.deppon.dpap.module.tianfangpush.server.entity.SalesDeptPushEntity.SalesDeptStatus;
import com.deppon.dpap.module.tianfangpush.server.entity.VehicleDataSyncPushEntity;
import com.deppon.dpap.module.tianfangpush.server.entity.VehicleDataSyncPushEntity.VehicleStatus;
import com.deppon.dpap.module.tianfangpush.server.entity.VehicleDataSyncPushEntity.VehicleType;
import com.deppon.dpap.module.tianfangpush.server.job.BaseDataSyncPushJob;
import com.deppon.dpap.module.tianfangpush.server.service.IBaseDataSyncPushService;
import com.deppon.oms.module.client.pushToTianFangkeji.client.OmsPushToTianFangClient;
import com.deppon.oms.module.client.pushToTianFangkeji.domain.EmpPushToTianFangRequest;
import com.deppon.oms.module.client.pushToTianFangkeji.domain.EmpPushToTianFangResponse;
import com.deppon.oms.module.client.pushToTianFangkeji.domain.ExpressVehiclePushToTianFangRequest;
import com.deppon.oms.module.client.pushToTianFangkeji.domain.ExpressVehiclePushToTianFangResponse;
import com.deppon.oms.module.client.pushToTianFangkeji.domain.SalesDeptPushToTianFangRequest;
import com.deppon.oms.module.client.pushToTianFangkeji.domain.SalesDeptPushToTianFangResponse;
import com.deppon.oms.module.expressvehicle.server.dao.IExpressVehicleDao;
import com.deppon.oms.module.salesdepartment.server.dao.ISalesDepartmentDao;

public class BaseDataSyncPushService implements IBaseDataSyncPushService {
	@Autowired
	private IBaseDataSyncPushDao baseDataSyncPushDao;
	@Autowired
	private IEmployeeDao employeeDao;
	@Autowired
	private IExpressVehicleDao expressVehicleDao;
	@Autowired
	private ISalesDepartmentDao salesDepartmentDao;
	@Autowired
	private OmsPushToTianFangClient omsPushToTianFangClient;

	/**
	 * 日志
	 */
	private static Log LOG = LogFactory.getLog(BaseDataSyncPushService.class);
	
	public enum InterfaceType{
		快递人员信息("2001"),
		快递车辆信息("2002"),
		末端网点信息("2006");
		private String value;
		
		InterfaceType(String value){
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

	/**
	 * 方法分为三步，
	 * 第一步：查询员工表limit XXX
	 * 第二步：插入中间表
	 * 第三步 : 更新历史记录表中的偏移量
	 */
	public void executePushEmp(JobMappingEntity jobMappingEntity) {
		//job参数
		ParameterModel parameterModel = new ParameterModel();
		/**
		 * 取模值
		 */
		parameterModel.setModel(jobMappingEntity.getModel());
		/**
		 * 查询条数
		 */
		parameterModel.setQueryNum(jobMappingEntity.getQueryNum());
		/**
		 * 节点数
		 */
		parameterModel.setNoteNum(jobMappingEntity.getNoteNum().toString());
		/**
		 * 从表里面查询出集合，然后开始推送
		 */
		EmpPushToTianFangResponse response = null;
		List<EmployeePushEntity> list = baseDataSyncPushDao.findList(jobMappingEntity);
		if(CollectionUtils.isNotEmpty(list)){
			List<EmpPushToTianFangRequest.DeliveryPerson> deliveryPersons = new ArrayList<EmpPushToTianFangRequest.DeliveryPerson>();
			EmpPushToTianFangRequest request = new EmpPushToTianFangRequest();
			EmpPushToTianFangRequest.MsgHead msgHead = new EmpPushToTianFangRequest.MsgHead();
			/**
			 * 封装报文头部
			 */
			request.setService("DeliveryPersonService");
			//电子商务企业代码
			msgHead.setEcCompanyCode("dbwl");
			//快递企业代码
			msgHead.setExpressCompanyCode(BaseDataSyncPushJob.expressCompanyCode);
			//报文类型
			msgHead.setProcessCode(InterfaceType.快递人员信息.value);
			//报文序列号
			msgHead.setTradeNo(String.valueOf(new Date().getTime()));
			/**
			 * 封装报文体
			 */
			EmpPushToTianFangRequest.Body body = new EmpPushToTianFangRequest.Body();
			for(EmployeePushEntity entity : list){
				EmpPushToTianFangRequest.DeliveryPerson deliveryPerson = new EmpPushToTianFangRequest.DeliveryPerson();
				deliveryPerson.setAddress(StringUtils.isBlank(entity.gethAddress()) ? "*" : entity.gethAddress());
				deliveryPerson.setCertificatesType("07000001"); //07000001代表二代身份证,07000002代表护照
				deliveryPerson.setCertificateNo(StringUtils.isBlank(entity.getIdentityCard()) ? "*" : entity.getIdentityCard());
				deliveryPerson.setDeliveryLevel(StringUtils.isBlank(entity.getJobLevel()) ? "*" : entity.getJobLevel());
				deliveryPerson.setDeliveryNo(StringUtils.isBlank(entity.getIdentityCard()) ? "*" : entity.getIdentityCard());
				deliveryPerson.setDeliveryPersonID(StringUtils.isBlank(entity.getEmpCode()) ? "*" : entity.getEmpCode());
				deliveryPerson.setEntryTime(StringUtils.isBlank(entity.getInDate()) ? "*" : entity.getInDate() );
				deliveryPerson.setName(StringUtils.isBlank(entity.getEmpName()) ? "*" : entity.getEmpName());
				deliveryPerson.setPhone(StringUtils.isBlank(entity.getMobileNo()) ? "*" : entity.getMobileNo());
				deliveryPerson.setQuitTime(StringUtils.isBlank(entity.getOutDate()) ? "*" : entity.getOutDate());
				deliveryPerson.setStatus("Y".equals(entity.getStatus()) ? "在职":"离职");
				deliveryPerson.setRemark(StringUtils.isBlank(entity.getRemark()) ? "*" : entity.getRemark());
				deliveryPersons.add(deliveryPerson);
			}
			body.setDeliveryPersons(deliveryPersons);
			request.setMsgHead(msgHead);
			request.setBody(body);
			
			/**
			 * 开始推送数据给DOP
			 */
			response = omsPushToTianFangClient.pushEmployee(request);
			/**
			 * 推送完毕,开始更新推送成功的数据
			 */
			List<String> idList = new ArrayList<String>();
			for(EmployeePushEntity item : list){
				idList.add(String.valueOf(item.getId()));
			}
			if(response != null){
				//更新为Y
				baseDataSyncPushDao.updateSuccess(idList);
			}else{
				//更新为N，设置push_count = push_count+1
				baseDataSyncPushDao.updateFail(idList);
			}
				
		}else{
			LOG.info("没有查到待推数据");
		}
		
	}

	/**
	 * 天津邮管局快递车辆信息推送
	 */
	public void executePushExpressVehicle(JobMappingEntity jobMappingEntity) {
		//job参数
		ParameterModel parameterModel = new ParameterModel();
		/**
		 * 取模值
		 */
		parameterModel.setModel(jobMappingEntity.getModel());
		/**
		 * 查询条数
		 */
		parameterModel.setQueryNum(jobMappingEntity.getQueryNum());
		/**
		 * 节点数
		 */
		parameterModel.setNoteNum(jobMappingEntity.getNoteNum().toString());
		/**
		 * 从表里面查询出集合，然后开始推送
		 */
		ExpressVehiclePushToTianFangResponse response = null;
		List<VehicleDataSyncPushEntity> list = baseDataSyncPushDao.findVehicleList(jobMappingEntity);
		ExpressVehiclePushToTianFangRequest request = new ExpressVehiclePushToTianFangRequest();
		ExpressVehiclePushToTianFangRequest.MsgHead msgHead = new ExpressVehiclePushToTianFangRequest.MsgHead();
		ExpressVehiclePushToTianFangRequest.Body body = new ExpressVehiclePushToTianFangRequest.Body();
		List<ExpressVehiclePushToTianFangRequest.Vehicle> vehicles = new ArrayList<ExpressVehiclePushToTianFangRequest.Vehicle>();
		/**
		 * 封装报文头部
		 */
		//服务名称
		request.setService("DeliveryVehicleService");
		//电子商务企业代码
		msgHead.setEcCompanyCode("dbwl");
		//快递企业代码
		msgHead.setExpressCompanyCode(BaseDataSyncPushJob.expressCompanyCode);
		//报文类型
		msgHead.setProcessCode(InterfaceType.快递车辆信息.value);
		//报文序列号
		msgHead.setTradeNo(String.valueOf(new Date().getTime()));
		/**
		 * 封装报文体
		 */
		if(CollectionUtils.isNotEmpty(list)){
			for(VehicleDataSyncPushEntity entity : list){
				ExpressVehiclePushToTianFangRequest.Vehicle vehicle = new ExpressVehiclePushToTianFangRequest.Vehicle();
				vehicle.setDeliveryVehicleID(StringUtils.isBlank(entity.getDeliveryVehicleID()) ? "*":entity.getDeliveryVehicleID());
				vehicle.setVehicleNo(StringUtils.isBlank(entity.getVehicleNo()) ? "*" : entity.getVehicleNo());
				vehicle.setVehicleType(convertLength(entity.getVehicleLength()));
				vehicle.setVehicleDriverName(StringUtils.isBlank(entity.getEmpName()) ? "*" : entity.getEmpName());
				vehicle.setDriverPhone(StringUtils.isBlank(entity.getMobilePhone()) ? "*" : entity.getMobilePhone());
				vehicle.setEntryTime(DateUtils.formatDate(entity.getVehicleEntryTime()));
				vehicle.setQuitTime("*");
				vehicle.setStatus("Y".equals(entity.getIsActive()) ? VehicleStatus.在用.getValue() : VehicleStatus.停用.getValue());
				vehicle.setRemark(StringUtils.isBlank(entity.getRemark()) ? "*" : entity.getRemark());
				vehicles.add(vehicle);
			}
			body.setDeliveryVehicles(vehicles);
			request.setMsgHead(msgHead);
			request.setBody(body);
			/**
			 * 开始推送数据给DOP
			 */
			response = omsPushToTianFangClient.pushExpressVehicle(request);
			/**
			 * 推送完毕,开始更新推送成功的数据
			 */
			List<String> idList = new ArrayList<String>();
			for(VehicleDataSyncPushEntity item : list){
				idList.add(String.valueOf(item.getId()));
			}
			if(response != null){
				//更新为Y
				baseDataSyncPushDao.updateSuccess(idList);
			}else{
				//更新为N，设置push_count = push_count+1
				baseDataSyncPushDao.updateFail(idList);
			}
		}else{
			LOG.info("没有查到待推数据");
		}
	}
	
	/**
	 * 342290 陈军
	 * 营业网点信息推送给天津邮管局
	 */
	public void executePushSalesDepartment(JobMappingEntity jobMappingEntity) {
		//job参数
		ParameterModel parameterModel = new ParameterModel();
		/**
		 * 取模值
		 */
		parameterModel.setModel(jobMappingEntity.getModel());
		/**
		 * 查询条数
		 */
		parameterModel.setQueryNum(jobMappingEntity.getQueryNum());
		/**
		 * 节点数
		 */
		parameterModel.setNoteNum(jobMappingEntity.getNoteNum().toString());
		/**
		 * 从表里面查询出集合，然后开始推送
		 */
		SalesDeptPushToTianFangResponse response = null;
		List<SalesDeptPushEntity> entities = baseDataSyncPushDao.findSalesDeptPush(jobMappingEntity);
		if(CollectionUtils.isNotEmpty(entities)){
			SalesDeptPushToTianFangRequest request = new SalesDeptPushToTianFangRequest();
			SalesDeptPushToTianFangRequest.MsgHead msgHead = new SalesDeptPushToTianFangRequest.MsgHead();
			SalesDeptPushToTianFangRequest.Body body = new SalesDeptPushToTianFangRequest.Body();
			List<SalesDeptPushToTianFangRequest.Network> networks = new ArrayList<SalesDeptPushToTianFangRequest.Network>();
			/**
			 *  封装报文头部
			 */
			request.setService("ExpressTerminalService");
			//电子商务企业代码
			msgHead.setEcCompanyCode("dbwl");
			//快递企业代码
			msgHead.setExpressCompanyCode(BaseDataSyncPushJob.expressCompanyCode);
			//报文类型
			msgHead.setProcessCode(InterfaceType.末端网点信息.value);
			//报文序列号
			msgHead.setTradeNo(String.valueOf(new Date().getTime()));
			
			/**
			 * 封装报文体
			 */
			for(SalesDeptPushEntity entity : entities){
				SalesDeptPushToTianFangRequest.Network network = new SalesDeptPushToTianFangRequest.Network();
				network.setTerminalID("*");
				network.setTerminalName(StringUtils.isBlank(entity.getName()) ? "*" : entity.getName());
				network.setEntryTime(DateUtils.formatDate(entity.getEntryTime()));
				network.setQuitTime("*");
				network.setTerminalPerson(StringUtils.isBlank(entity.getTerminalPerson()) ? "*" : entity.getTerminalPerson());
				network.setTerminalPhone(StringUtils.isBlank(entity.getTerminalPhone()) ? "*" : entity.getTerminalPhone());
				network.setTerminalAddress(StringUtils.isBlank(entity.getTerminalAddress()) ? "*" : entity.getTerminalAddress());
				network.setTerminalStatus("Y".equals(entity.getIsActive())? SalesDeptStatus.在用.getValue():SalesDeptStatus.非在用.getValue());
				network.setTerminalType(StringUtils.isBlank(entity.getTerminalType()) ? "*" : entity.getTerminalType());
				network.setLatitude("*");
				network.setLongitude("*");
				network.setRemark("*");
				networks.add(network);
			}
			body.setNetworks(networks);
			request.setMsgHead(msgHead);
			request.setBody(body);
			
			/**
			 * 开始推送数据给DOP
			 */
			response = omsPushToTianFangClient.pushSalesDepartment(request);
			/**
			 * 推送完毕,开始更新推送成功的数据
			 */
			List<String> idList = new ArrayList<String>();
			for(SalesDeptPushEntity item : entities){
				idList.add(String.valueOf(item.getId()));
			}
			if(response != null){
				//更新为Y
				baseDataSyncPushDao.updateSuccess(idList);
			}else{
				//更新为N，设置push_count = push_count+1
				baseDataSyncPushDao.updateFail(idList);
			}
		}else{
			LOG.info("没有查到待推数据");
		}
	}
	
	/**
	 * 小型汽车是指：总质量不超过 4.5t、乘坐人数（包括驾驶员）
	 * 不超过9人或车长 6m 以下的汽车。如轿车、吉普车、
	 * 微型车、轻型客车、轻型载货汽车及小型专用汽车都属于小型汽车。
	 * @param vehicleLength
	 * @return
	 */
	public String convertLength(String vehicleLength){
		String result = VehicleType.小汽车.getValue();  //默认为小汽车
		if( ! isDouble(vehicleLength)){
			Double length = Double.valueOf(vehicleLength);
			if(length>0 && length<=3){
				result = VehicleType.节能电动车.getValue();
			}
			if(length>3 && length<=6){
				result = VehicleType.小汽车.getValue();
			}
			if(length>6 && length<=12){
				result = VehicleType.箱式货车.getValue();
			}
			if(length>12){
				result = VehicleType.集装箱车.getValue();
			}
		}
		return result;
	}
	
	/**
	 * 判断是不是可以转换成Double的类型
	 * @param str
	 * @return
	 */
	public boolean isDouble(String str){
	   try{
	      Double.parseDouble(str);
	      return true;
	   }
	   catch(NumberFormatException ex){}
	   return false;
	}
	
	
	/**
	 * @return the baseDataSyncPushDao
	 */
	public IBaseDataSyncPushDao getBaseDataSyncPushDao() {
		return baseDataSyncPushDao;
	}

	/**
	 * @param baseDataSyncPushDao the baseDataSyncPushDao to set
	 */
	public void setBaseDataSyncPushDao(IBaseDataSyncPushDao baseDataSyncPushDao) {
		this.baseDataSyncPushDao = baseDataSyncPushDao;
	}

	/**
	 * @return the employeeDao
	 */
	public IEmployeeDao getEmployeeDao() {
		return employeeDao;
	}

	/**
	 * @param employeeDao
	 *            the employeeDao to set
	 */
	public void setEmployeeDao(IEmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	/**
	 * @return the expressVehicleDao
	 */
	public IExpressVehicleDao getExpressVehicleDao() {
		return expressVehicleDao;
	}

	/**
	 * @param expressVehicleDao
	 *            the expressVehicleDao to set
	 */
	public void setExpressVehicleDao(IExpressVehicleDao expressVehicleDao) {
		this.expressVehicleDao = expressVehicleDao;
	}

	/**
	 * @return the salesDepartmentDao
	 */
	public ISalesDepartmentDao getSalesDepartmentDao() {
		return salesDepartmentDao;
	}

	/**
	 * @param salesDepartmentDao
	 *            the salesDepartmentDao to set
	 */
	public void setSalesDepartmentDao(ISalesDepartmentDao salesDepartmentDao) {
		this.salesDepartmentDao = salesDepartmentDao;
	}

	
	/**
	 * @return the omsPushToTianFangClient
	 */
	public OmsPushToTianFangClient getOmsPushToTianFangClient() {
		return omsPushToTianFangClient;
	}

	/**
	 * @param omsPushToTianFangClient the omsPushToTianFangClient to set
	 */
	public void setOmsPushToTianFangClient(OmsPushToTianFangClient omsPushToTianFangClient) {
		this.omsPushToTianFangClient = omsPushToTianFangClient;
	}

}
