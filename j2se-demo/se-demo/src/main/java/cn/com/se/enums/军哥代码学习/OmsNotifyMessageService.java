package cn.com.se.enums.军哥代码学习;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.dpap.module.context.DateUtils;
import com.deppon.dpap.module.dict.server.service.IDictionaryService;
import com.deppon.dpap.module.dict.shared.domain.DictionaryDataEntity;
import com.deppon.dpap.module.monitor.shared.domain.JobMappingEntity;
import com.deppon.dpap.module.orderdispatch.server.dao.IOrderDispatchDao;
import com.deppon.dpap.module.orderdispatch.shared.domain.OrderDispatchEntity;
import com.deppon.dpap.module.orderdispatch.shared.domain.cainiaocangpei.CaiNiaoCangPeiPlaceAnOrderInfo;
import com.deppon.dpap.module.organization.server.dao.impl.DepartmentDao;
import com.deppon.dpap.module.statepush.server.dao.IOmsNotifyMessageDao;
import com.deppon.dpap.module.statepush.server.service.IOmsNotifyMessageService;
import com.deppon.dpap.module.statepush.server.service.ITraceService;
import com.deppon.dpap.module.statepush.shared.constant.FOSSTypeConvert;
import com.deppon.dpap.module.statepush.shared.constant.OmsNotifyMessageConstant;
import com.deppon.dpap.module.statepush.shared.domain.CommonModel;
import com.deppon.dpap.module.statepush.shared.domain.EbmResponse;
import com.deppon.dpap.module.statepush.shared.domain.GoodsBackPushToDOPRequest;
import com.deppon.dpap.module.statepush.shared.domain.GoodsBackPushToDOPResponse;
import com.deppon.dpap.module.statepush.shared.domain.OmsNotifyMessage;
import com.deppon.dpap.module.statepush.shared.domain.OmsStatusPushToDopModel;
import com.deppon.dpap.module.statepush.shared.domain.OrientalShoppingServiceResponse;
import com.deppon.dpap.module.statepush.shared.domain.OriginalTrackingInfo;
import com.deppon.dpap.module.statepush.shared.domain.ParameterModel;
import com.deppon.dpap.module.statepush.shared.domain.QueryWaybillTrajectoryRequest;
import com.deppon.dpap.module.statepush.shared.domain.Receiver;
import com.deppon.dpap.module.statepush.shared.domain.SNBody;
import com.deppon.dpap.module.statepush.shared.domain.SNRequest;
import com.deppon.dpap.module.statepush.shared.domain.Sender;
import com.deppon.dpap.module.statepush.shared.domain.TaobaoUpdateFieldRequest;
import com.deppon.dpap.module.statepush.shared.exception.PushException;
import com.deppon.esb.pojo.util.JsonMapperUtil;
import com.deppon.oms.module.client.cainiaohuichuan.domain.GoodsItem;
import com.deppon.oms.module.client.cainiaohuichuan.domain.PackageInfo;
import com.deppon.oms.module.client.orderAndWaybillAssociated.domain.OrdWaybillRelateRequest;
import com.deppon.oms.module.client.orderAndWaybillAssociated.domain.OrdWaybillRelateResponse;
import com.deppon.oms.module.client.orderAndWaybillAssociated.domain.WayBillDetail;
import com.deppon.oms.module.client.orderAndWaybillAssociated.domain.WaybillCostInfo;
import com.deppon.oms.module.client.orderAndWaybillAssociated.server.OrdWaybillRelateClient;
import com.deppon.oms.module.client.orderStatusPushing.domain.OrderStatusPushingFailEntity;
import com.deppon.oms.module.client.orderStatusPushing.domain.OrderStatusPushingRequest;
import com.deppon.oms.module.client.orderStatusPushing.server.OrderStatusPushingClient;
import com.deppon.oms.module.constant.common.NumberConst;
import com.deppon.oms.module.expOrLtlOrder.shared.domain.ExpOrLtlModel;
import com.deppon.oms.module.util.json.FastJsonUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * (描述类的职责)
 * <p style="display:none">状态推送2.0</p>
 * <p style="display:none">version:V2.0,author:342290,date:2016-11-10 11:12:08 </p>
 * @author 342290 陈军
 * @date 2016年11月10日11:11:51
 * @since
 * @version
 */
public class OmsNotifyMessageService implements IOmsNotifyMessageService {
	/**
	 * （用一句话描述这个变量表示什么）
	 */
	public String mailNoStr = "mailNo";
	/**
	 * （用一句话描述这个变量表示什么）
	 */
	public String weightStr = "weight";
	/**
	 * （用一句话描述这个变量表示什么）
	 */
	public String depponStr = "DEPPON";
	/**
	 * （用一句话描述这个变量表示什么）
	 */
	public String maxTime = null;
	/**
	 * （用一句话描述这个变量表示什么）
	 */
	public String minTime = null;
	/**
	 * （用一句话描述这个变量表示什么）
	 */
	private static final String companyName = "德邦物流";
	/**
	 * （用一句话描述这个变量表示什么）
	 */
	private static final  String OperatorPhone = "95353";
	/**
	 * （用一句话描述这个变量表示什么）
	 */
	private static final String TWELVE = "12";
	/**
	 * （用一句话描述这个变量表示什么）
	 */
	private static final String FORTY = "40";
	/**
	 * 日志
	 */
	private static Log LOG = LogFactory.getLog(OmsNotifyMessageService.class);
	/**
	 * （用一句话描述这个变量表示什么）
	 */
	public ITraceService traceService;
	/**
	 * 日期转换
	 */
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 * （用一句话描述这个变量表示什么）
	 */
	SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 数据库交互DAO层
	 */
	public IOmsNotifyMessageDao omsNotifyMessageDao;
	/**
	 * 官网DAO
	 */
	public OrderStatusPushingClient orderStatusPushingClient;
	/**
	 * 根据运单号获取运单详细
	 */
	private OrdWaybillRelateClient ordWaybillRelateClient;
	/**
	 * 注入部门DAO
	 */
	private DepartmentDao departmentDao;
	/**
	 * 注入订单报文表DAO
	 */
	private IOrderDispatchDao orderDispatchDao;
	/**
	 * dictionaryService
	 */
	private IDictionaryService dictionaryService;
	
	/**
	 * 把需要状态推送的数据从数据库里查出来
	 * @author 342290 陈军
	 * @param jobMappingEntity 
	 * @date   2016年11月9日16:44:54
	 */
	@Override
	public void executeOmsNotifyMessage(JobMappingEntity  jobMappingEntity) {
		//job参数
		ParameterModel parameterModel = new ParameterModel();
		/**
		 * setModel
		 */
		parameterModel.setModel(jobMappingEntity.getModel());
		/**
		 * setQueryNum
		 */
		parameterModel.setQueryNum(jobMappingEntity.getQueryNum());
		/**
		 * setNoteNum
		 */
		parameterModel.setNoteNum(jobMappingEntity.getNoteNum().toString());
		//查询符合条件的需要状态推送的OmsNotifyMessage对象
		List<OmsNotifyMessage> list = omsNotifyMessageDao.queryMessage(parameterModel);
		/**
		 * IF
		 */
		if(list == null || list.isEmpty()){
			return;
		}
		this.executePush(list);
	}
	
	/**
	 * 342290 
	 * 陈军
	 * 2016年11月23日10:13:11
	 * 把从数据库里面查询出集合之后的逻辑单独分离出来，便于写单元测试，确保单元测试跑的过程不要查数据库
	 * @param omsNotifyMessages
	 */
	@Override
	public void executePush(List<OmsNotifyMessage> omsNotifyMessages){
		//东方购物推送数据集合
		List<OmsNotifyMessage> orientalShoppingList = new ArrayList<OmsNotifyMessage>();
		//官网推送数据集合
		List<OmsNotifyMessage> owsList = new ArrayList<OmsNotifyMessage>();
		// 天猫家装推送数据集合
		List<OmsNotifyMessage> tmallJzList = new ArrayList<OmsNotifyMessage>();
		//天猫家装和加瓦云人造数据集合
		List<ExpOrLtlModel> fabricatedList = new ArrayList<ExpOrLtlModel>();
		//加瓦云推送集合
		List<OmsNotifyMessage> javaYunList = new ArrayList<OmsNotifyMessage>();
		//淘宝推送集合
		List<OmsNotifyMessage> taobaoList = new ArrayList<OmsNotifyMessage>();
		//苏宁推送集合
		List<OmsNotifyMessage> suningList = new ArrayList<OmsNotifyMessage>();
		//QQ速递推送集合
		List<OmsNotifyMessage> qqSudiList = new ArrayList<OmsNotifyMessage>();
		//快递一百推送集合
		List<OmsNotifyMessage> express100List = new ArrayList<OmsNotifyMessage>();
		//阿里巴巴推送集合
		List<OmsNotifyMessage> alibabaList = new ArrayList<OmsNotifyMessage>();
		//菜鸟大件仓配推送集合
		List<OmsNotifyMessage> cnjzList = new ArrayList<OmsNotifyMessage>();
		//零担快乐购对接
		List<OmsNotifyMessage> happyGoList = new ArrayList<OmsNotifyMessage>();
		//标准接口推送集合
		List<OmsNotifyMessage> standardList  = new ArrayList<OmsNotifyMessage>(); 
		//建立一个渠道单号和ID的一对多映射集合，因为等一下要根据ID来更新表
		Map<String,Set<String>> channelOrderCacheMap = new HashMap<String,Set<String>>();
		//存放总的渠道单号
		List<String> channelOrderList = new ArrayList<String>();
		//存放总的推送失败的渠道单号
		List<String> failChannelList = new ArrayList<String>();
		//用于存放垃圾数据的渠道单号,垃圾数据的SUCCESS字段将被更新为'H'
		List<String> garbageList = new ArrayList<String>();
		//按照渠道拆分集合，分别处理
		for(OmsNotifyMessage omsNotifyMessage : omsNotifyMessages){
			/**
			 * 下面判断渠道来源
			 */
			String orderResource= omsNotifyMessage.getResource();
			//以下几种都属于官网的范畴
			if(OmsNotifyMessageConstant.ONLINE.equals(orderResource) 
					/**
					 * BUSINESS_DEPT
					 */
					|| OmsNotifyMessageConstant.BUSINESS_DEPT.equals(orderResource) 
					/**
					 * MOW
					 */
					|| OmsNotifyMessageConstant.MOW.equals(orderResource) 
					/**
					 * APP
					 */
					|| OmsNotifyMessageConstant.APP.equals(orderResource) 
					/**
					 * CALLCENTER
					 */
					||OmsNotifyMessageConstant.CALLCENTER.equals(orderResource) 
					/**
					 * CALLCENTER_HF
					 */
					|| OmsNotifyMessageConstant.CALLCENTER_HF.equals(orderResource)){
				orderResource = OmsNotifyMessageConstant.OWS;
			}
			//判断 是不是东方购物
			if(OmsNotifyMessageConstant.DFGW.equals(omsNotifyMessage.getShipperNumber())){
				orderResource = OmsNotifyMessageConstant.DFGW;
			}
			/**
			 * switch
			 */
			switch(orderResource){
			  //东方购物
			  case OmsNotifyMessageConstant.DFGW:
				  orientalShoppingList.add(omsNotifyMessage);
				  break;
			  //官网的
	          case OmsNotifyMessageConstant.OWS:
	        	  owsList.add(omsNotifyMessage);
	        	  break;
	          //淘宝家装
	          case OmsNotifyMessageConstant.TAOBAOJZ:
	        	  tmallJzList.add(omsNotifyMessage);
	        	  break;
	          //加瓦云
	          case OmsNotifyMessageConstant.JIAWAYUN:
	        	  javaYunList.add(omsNotifyMessage);
	        	  break;
	          //淘宝
	          case OmsNotifyMessageConstant.TAOBAO:
	        	  taobaoList.add(omsNotifyMessage);
	        	  break;
	          //苏宁
	          case OmsNotifyMessageConstant.SUNING:
	        	  suningList.add(omsNotifyMessage);
	        	  break;
	         //QQ速递
	          case OmsNotifyMessageConstant.QQSUDI:
	        	  qqSudiList.add(omsNotifyMessage);
	        	  break;
	          //快递一佰
	          case OmsNotifyMessageConstant.YOUSHANG:
	        	  express100List.add(omsNotifyMessage);
	        	  break;
	          //阿里巴巴
	          case OmsNotifyMessageConstant.ALIBABA:
	        	  alibabaList .add(omsNotifyMessage);
	        	  break;
	          //菜鸟大件仓配(菜鸟家装)
	          case OmsNotifyMessageConstant.CNJZ:
	        	  cnjzList.add(omsNotifyMessage);
	        	  break;
	          //零担快乐购
	          case OmsNotifyMessageConstant.EWBKLG:
	        	  happyGoList.add(omsNotifyMessage);
	          //标准的
	          default:
	        	  standardList.add(omsNotifyMessage);
	        	  break;
			}
			channelOrderList.add(omsNotifyMessage.getChannerOrder());
			
			//把渠道单号和ID的映射关系缓存到"channelOrderCache"里面。方便等会儿取值
			String key = omsNotifyMessage.getChannerOrder();
			//如果这个KEY已经存在,给hashSet再添加值
			if(channelOrderCacheMap.containsKey(key)){
				channelOrderCacheMap.get(key).add(omsNotifyMessage.getId());
			}
			//如果没有过这个KEY
			else{
				Set<String> hashSet = new HashSet<String>();
				hashSet.add(omsNotifyMessage.getId());
				channelOrderCacheMap.put(key, hashSet);
			}
		}//for循环结束
		/**
		 * 开始推送
		 */
		//东方购物推送
		failChannelList.addAll(this.pushOrientalShopping(orientalShoppingList));//error
		//官网推送
		failChannelList.addAll(this.pushToOWS(owsList));
		//天猫家装推送
		failChannelList.addAll(this.tmallJzPush(tmallJzList,fabricatedList));
		//java云推送
		failChannelList.addAll(this.javaYunPush(javaYunList,fabricatedList,garbageList));
		//淘宝推送,此处可能有垃圾数据，所以传入garbageList
		failChannelList.addAll(this.taobaoPush(taobaoList,garbageList));
		//苏宁推送,此处可能有垃圾数据，所以传入garbageList
		failChannelList.addAll(this.suningPush(suningList,garbageList));
		//QQ速递推送
		failChannelList.addAll(this.qqSudiPush(qqSudiList));
		//快递100推送
		failChannelList.addAll(this.express100Push(express100List));
		//阿里巴巴推送,此处可能有垃圾数据，所以传入garbageList
		failChannelList.addAll(this.alibabaPush(alibabaList,garbageList));
		//菜鸟大件仓配推送
		failChannelList.addAll(this.cnjzPush(cnjzList));
		//零担快乐购推送
		failChannelList.addAll(this.happyGoPush(happyGoList));
		//标准推送，此处可能有垃圾数据，所以传入garbageList
		failChannelList.addAll(this.standardPush(standardList,garbageList));
		/**
		 * 推送完毕，开始处理推送【成功】，【失败】的数据
		 * 更新完毕后把天猫家装和加瓦云的自定义组装数据插入推送表t_oms_notify_message
		 */
		//更新成功的渠道单号 = 总渠道单号 - 更新失败的 - 垃圾数据
		channelOrderList.removeAll(failChannelList);
		channelOrderList.removeAll(garbageList);
		//更新失败的，15分钟之后重推
		if(! failChannelList.isEmpty()){
			this.updateFail(channelOrderCacheMap,failChannelList);
		}
		//更新成功的，success = "Y"
		if( ! channelOrderList.isEmpty()){
			this.updateSuccess(channelOrderCacheMap,channelOrderList);
		}
		//更新垃圾数据的渠道单号，设success = "H"
		if( ! garbageList.isEmpty()){
			this.updateGarbage(channelOrderCacheMap,garbageList);
		}
		//自定义组装数据入推送表
		if( ! fabricatedList.isEmpty()){
			omsNotifyMessageDao.insertListExpOrLtlOrder(fabricatedList);
		}
	}
	
	/**
	 * 根据“渠道单号”<->“id”的map获取Id，然后根据ID，对数据库update
	 * @param cacheMap
	 * @param failList
	 * 2016年11月10日09:54:37
	 * 342290
	 * 陈军
	 */
	public void updateFail(Map<String,Set<String>> cacheMap,List<String> failList){
		//list
		List<String> idList = new ArrayList<String>();
		for(String channelOrder : failList){
			idList.addAll(cacheMap.get(channelOrder));
		}
		//if
		if(! idList.isEmpty()){
			omsNotifyMessageDao.updateByPrimaryKeyErry(idList);
		}
	}
	
	/**
	 *  根据“渠道单号”<->“id”的map获取Id，然后根据ID，对数据库update
	 * @param cacheMap 
	 * @param successList
	 * 2016年11月10日09:54:23
	 * 342290
	 * 陈军
	 */
	public void updateSuccess(Map<String,Set<String>> cacheMap,List<String> successList){
		//list
		List<String> idList = new ArrayList<String>();
		//for
		for(String channelOrder : successList){
			idList.addAll(cacheMap.get(channelOrder));
		}
		//for
		if(! idList.isEmpty()){
			omsNotifyMessageDao.updateByPrimaryKey(idList);
		}
	}
	
	/**
	 * <p>(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 342290
	 * @date 2016-11-26 下午2:51:31
	 * @param cacheMap
	 * @param garbageList
	 * @see
	 */
	public void updateGarbage(Map<String,Set<String>> cacheMap,List<String> garbageList){
		//List
		List<String> idList = new ArrayList<String>();
		//for
		for(String channelOrder : garbageList){
			idList.addAll(cacheMap.get(channelOrder));
		}
		//if
		if(! idList.isEmpty()){
			omsNotifyMessageDao.updateByPrimaryKeyGarbageMothed(idList);
		}
	}
	
	/**
	 * 东方购物推送
	 * 2016-11-8 10:45:11
	 * 342290
	 * 陈军
	 * @return 返回推送失败的渠道单号,并且准备下一次重推
	 */
	public List<String> pushOrientalShopping(List<OmsNotifyMessage> messageList){
		CommonModel  orientalModel = new CommonModel();
		List<CommonModel> orientalShoppings = new ArrayList<CommonModel>();
		List<OrientalShoppingServiceResponse> responses = new ArrayList<OrientalShoppingServiceResponse>();
		List<String> failchannelList = new ArrayList<String>();
		if(messageList.isEmpty()){
			return failchannelList;
		}
		for(OmsNotifyMessage message : messageList){
			Date date = null;
			if(message.getWaybillNo() != null){
				orientalModel.setWaybillNumber(message.getWaybillNo());
				try {
					date = sdfs.parse(message.getCreateTime());
				} catch (ParseException e) {
					date = null;
					LOG.info(ExceptionUtils.getFullStackTrace(e));
				}
				//时间作为备注
				orientalModel.setComment(sdf.format(date));
				//状态
				orientalModel.setOrderStatus(message.getOrderStatus());
				//渠道单号
				orientalModel.setChannerOrder(message.getChannerOrder());
				//唯一标示
				orientalModel.setId(message.getId());
				//添加到集合
				orientalShoppings.add(orientalModel);
			}
		}
		try {
			responses = omsNotifyMessageDao.pushToOrientalShopping(orientalShoppings);
			if(responses != null){
				for(OrientalShoppingServiceResponse response : responses){
					if("false".equals(response.getRemark())){
						failchannelList.add(response.getChannerOrder());
					}
				}
			}else{
				for(OmsNotifyMessage message : messageList){
					failchannelList.add(message.getChannerOrder());
				}
			}
		} catch (Exception e) {
			for(OmsNotifyMessage message : messageList){
				failchannelList.add(message.getChannerOrder());
			}
			LOG.info(ExceptionUtils.getFullStackTrace(e));
		}
		return failchannelList;
	}
	
	/**
	 * 官网推送
	 * @param messageList
	 * @return 返回推送失败的渠道单号,并且准备下一次重推
	 */
	public List<String> pushToOWS(List<OmsNotifyMessage> messageList){
		OrderStatusPushingRequest model = new OrderStatusPushingRequest();
		List<OrderStatusPushingRequest> pushOwsList = new ArrayList<OrderStatusPushingRequest>();
		List<String> failChannelList = new ArrayList<String>();
		if(messageList.isEmpty()){
			return failChannelList;
		}
		for(OmsNotifyMessage message : messageList){
			model.setOrderNo(message.getOrderNo());
			//渠道单号
			model.setChannerOrder(message.getChannerOrder());
			//如已约车，接货中，已开单等
			model.setStatus(message.getOrderStatus());
			//备注
			model.setRemarks(message.getRemarks());
			//收货人姓名
			model.setReceiverName(message.getReceiverName());
			//收货人电话
			model.setReceiverMobilePhone(message.getReceiverMobilePhone());
			//发货人姓名
			model.setPosterName(message.getPosterName());
			//发货人姓名
			model.setPosterMobilePhone(message.getPosterMobilePhone());
			//接货员姓名
			model.setAccepterName(message.getAccepterName());
			//接货员电话
			model.setAccepterMobile(message.getAccepterMobile());
			pushOwsList.add(model);
		}
		List<OrderStatusPushingFailEntity> lists = orderStatusPushingClient.orderStatusPushingSync(pushOwsList);
		for(OrderStatusPushingFailEntity failEntity : lists){
			failChannelList.add(failEntity.getChannerOrder());
		}
		return failChannelList;
	}
	
	/**
	 * Tmall家装推送
	 * 342290
	 * 2016年11月8日11:17:59
	 * 陈军
	 * @param messageList
	 * @return 返回推送失败的渠道单号,并且准备下一次重推
	 */
	public List<String> tmallJzPush(List<OmsNotifyMessage> messageList,List<ExpOrLtlModel> fabricatedList){
		List<OmsStatusPushToDopModel> statusPushToDopModels = new ArrayList<OmsStatusPushToDopModel>();
		List<String> failChannelList = new ArrayList<String>();
		if(messageList.isEmpty()){
			return failChannelList;
		}
		for(OmsNotifyMessage message : messageList){
			OmsStatusPushToDopModel pushModel =  new OmsStatusPushToDopModel();
			CommonModel context = new CommonModel();
			context.setContent("");
			String userNum = message.getOprateUserNum();
			String mobileNumber = message.getOprateUserMobile();
			//操作人(如果操作人为空则操作人为德邦物流)
			context.setOperator(StringUtils.isBlank(userNum) ? companyName : userNum);
			//联系人电话(如果联系人电话为空则联系人电话为德邦客服)
			context.setOperator_contact(StringUtils.isBlank(mobileNumber) ? OperatorPhone : mobileNumber);
			//备注默认为空
			context.setRemark(message.getRemarks());
			//服务编码(通过渠道订单号查询物流公司ID)
			//Service_code 存放的 是 tmsServiceCode
			context.setService_code(message.getProvideId());
			//把天猫状态装换为德邦状态
			context.setStatus(matchTmallStatus(message.getOrderStatus()));
			//操作时间
			context.setOperator_date("");
			Date date = null;
			try{
				//派送发生时间作为安装预约时间
				date = sdfs.parse(message.getCreateTime());
				context.setOperator_date(sdfs.format(date));
			}catch (ParseException e) {
				LOG.info(ExceptionUtils.getFullStackTrace(e));
			}
			/**
			 * 如果是开单状态
			 * 同时将接口中的其他字段一并回传(订单号/运单号/重量/体积/件数/操作人等)
			 */
			if(OmsNotifyMessageConstant.GOT.equals(message.getOrderStatus())){
				//包裹数量
				context.setPackages(message.getTotalPiece());
				//包裹体积，单位立方米，保留两位小数，当状态为TMS_CANVASS - TMS揽货成功
				context.setPackage_volume(message.getTotalVolume());
				//包裹重量，单位千克，当状态为TMS_CANVASS - TMS揽货成功返回 必传
				context.setPackage_weight(message.getTotalWeight());
			}
			/**
			 * 当状态为安装预约的时候 如果推送成功了 则支线开始和支线结束进入推送状态表
			 * TMS-BRANCH-START支线开始
			 * TMS-BRANCH-END 支线结束
			 * TMS-INSTALL-START安装开始
			 * 安装预约推送15~20min后（随机取数）,推送支线开始状态()
			 * 安装预约推送25~35min后（随机取数）,推送支线结束状态
			 * 安装预约推送35~45min后（随机取数），再推送此状态
			 */
			if(OmsNotifyMessageConstant.SENT_SCAN.equals(message.getOrderStatus())){
				/**派送发生时间作为安装预约时间;且在回传的remark字段填入：预约时间+22~26H中间某个随机值*/
				Date newTime = this.addMinute(new Date(), this.randomRange(NumberConst.NUMBER_INT_VALUE22, NumberConst.NUMBER_INT_VALUE26));
				String newTimeStr = sdfs.format(newTime);
				context.setRemark(newTimeStr);
				context.setOperator_date(newTimeStr);
				//人工捏造三条数据,用于下次推送
				fabricatedList.addAll(tmallNextPush(message));
			}
			//家装派送
			if(OmsNotifyMessageConstant.ORDER_STATUS.equals(message.getOrderStatus())){
				Date sendDate = null;
				try {
					sendDate = sdf.parse(message.getRemarks());
				} catch (ParseException e) {
					LOG.info(ExceptionUtils.getFullStackTrace(e));
				}
				if(sendDate != null){
					context.setRemark(sdf.format(date));
					context.setOperator_date(sdf.format(date));
				}
				//人工捏造三条数据,用于下次推送
				fabricatedList.addAll(tmallNextPush(message));
			}
			//当签收状态的时候 先推送安装结束 过一分钟后 再推送签收
			if(OmsNotifyMessageConstant.SIGNSUCCESS.equals(message.getOrderStatus())){
				//遇到【SIGNSUCCESS】状态时，等一分钟再次推送【TMSSIGN】，表示tmall家装特定的签收
				ExpOrLtlModel omModel = new ExpOrLtlModel();
				omModel = fabricatedMessage(message,NumberConst.NUMBER_INT_VALUE1,NumberConst.NUMBER_INT_VALUE3,OmsNotifyMessageConstant.TMSSIGN);
				fabricatedList.add(omModel);
			}
			//当签收状态的时候  提货方式放到Remark字段里去
			if(OmsNotifyMessageConstant.TMSSIGN.equals(message.getOrderStatus())){
				context.setRemark(message.getDeliveryMode());
			}
			//淘宝物流平台的物流编号( 淘宝只允许更新1.运单号.mailNo. 更新运单号，，2.weight，货物重量3.status. 4.exceptions .5 suspect)
			context.setOrder_code(message.getChannerOrder());
			//物流公司编号()
			context.setOut_biz_code(message.getChannerOrder() + System.nanoTime());
			//淘宝物流平台的物流编号( 淘宝只允许更新1.运单号.mailNo. 更新运单号，，2.weight，货物重量3.status. 4.exceptions .5 suspect)
			context.setTms_order_code(message.getWaybillNo());
			
			/**唯一标识*/
			pushModel.setId(message.getId());
			//订单来源
			pushModel.setOrderSource(message.getResource());
			//渠道单号
			pushModel.setChannelOrderNumber(message.getChannerOrder());
			context.setTracesList(null);
			context.setFieldList(null);
			//天猫家装豹纹
			pushModel.setContext(context);
			//推送地址
			pushModel.setUrl(message.getUrl());
			//状态
			pushModel.setStatus(message.getOrderStatus());
			//添加到集合
			statusPushToDopModels.add(pushModel);
		}
		/**
		 * Tmall家装推送数据组装完毕，接下来开始推送..
		 */
		failChannelList.addAll(this.commonDopPush(statusPushToDopModels));
		return failChannelList;
	}
	
	/**
	 * 返回捏造出来的三条数据，录进t_oms_notify_message表，用于下次推送
	 * @param message
	 * @return
	 */
	public List<ExpOrLtlModel> tmallNextPush(OmsNotifyMessage message) {
		//用于存放捏造出来的三条数据
		List<ExpOrLtlModel> virtualList = new ArrayList<ExpOrLtlModel>();
		ExpOrLtlModel model1 = fabricatedMessage(message,NumberConst.NUMBER_INT_VALUE15,NumberConst.NUMBER_INT_VALUE6,OmsNotifyMessageConstant.TMSBRANCHSTART);
		ExpOrLtlModel model2 = fabricatedMessage(message,NumberConst.NUMBER_INT_VALUE25,NumberConst.NUMBER_INT_VALUE6,OmsNotifyMessageConstant.TMSBRANCHEND);
		ExpOrLtlModel model3 = fabricatedMessage(message,NumberConst.NUMBER_INT_VALUE35,NumberConst.NUMBER_INT_VALUE11,OmsNotifyMessageConstant.TMSINSTALLSTART);
		virtualList.add(model1);
		virtualList.add(model2);
		virtualList.add(model3);
		return virtualList;
	}
	
	/**
	 * 用于天猫家装和加瓦云在遇到特定状态时,捏造数据
	 * @param message 推送对象
	 * @param min 最小分钟数
	 * @param max 分钟数偏移量
	 * @param orderStatus 订单状态 
	 * 2016年11月11日10:20:31 342290
	 * 陈军
	 * @return
	 */
	public ExpOrLtlModel fabricatedMessage(OmsNotifyMessage message,Integer min,Integer max,String orderStatus){
		ExpOrLtlModel omModel = new ExpOrLtlModel();
		//计算随机数范围
		int radomValue = this.randomRange(min, max);
		//随机时间(分钟)
		omModel.setRandomNumber(String.valueOf(radomValue));
		//接货员联系方式
		omModel.setAccepterMobile(message.getAccepterMobile());
		//接货员联系电话
		omModel.setAccepterName(message.getAccepterName());
		omModel.setChannelCustId(message.getChannelCustId());
		omModel.setChannerOrder(message.getChannerOrder());
		omModel.setDepartureId(message.getDepartureId());
		omModel.setOprateUserNum(message.getOprateUserNum());
		omModel.setOrderNo(message.getOrderNo());
		omModel.setOrderStatus(orderStatus);
		omModel.setPosterMobilePhone(message.getPosterMobilePhone());
		omModel.setPosterName(message.getPosterName());
		omModel.setProvideId(message.getProvideId());
		omModel.setReceiverMobilePhone(message.getReceiverMobilePhone());
		omModel.setReceiverName(message.getReceiverName());
		omModel.setReceivingtoPointid(message.getReceivingtoPointid());
		//当前时间加上随机时间
		Date newDate = this.addMinute(new Date(), radomValue);
		String newDateStr = sdfs.format(newDate);
		if(OmsNotifyMessageConstant.TMSSIGN.equals(message.getOrderStatus())){
			//同时将运单提货方式填充入remark字段
			//这个查询    SIGNSUCCESS 数据
			omModel.setRemarks(message.getDeliveryMode());
		}else{
			omModel.setRemarks(newDateStr);
		}
		omModel.setResource(message.getResource());
		omModel.setServiceType(message.getServiceType());
		omModel.setShipperNumber(message.getShipperNumber());
		omModel.setTotalFee(message.getTotalFee());
		omModel.setTotalPiece(message.getTotalPiece());
		omModel.setTotalVolume(message.getTotalVolume());
		omModel.setTotalWeight(message.getTotalWeight());
		omModel.setWaybillNo(message.getWaybillNo());
		omModel.setCreateTime(newDateStr);
		omModel.setDeliveryMode(message.getDeliveryMode());
		return omModel;
	}

	/**
	 * 加瓦云推送
	 * 2016年11月8日11:22:55
	 * 342290
	 * 陈军
	 * @param messageList
	 * @return 返回推送失败的渠道单号,并且准备下一次重推
	 * 注意，此渠道可能有垃圾数据
	 */
	public List<String> javaYunPush(List<OmsNotifyMessage> messageList,List<ExpOrLtlModel> fabricatedList,List<String> garbageList){
		List<OmsStatusPushToDopModel> statusPushToDopModels = new ArrayList<OmsStatusPushToDopModel>();
		List<String> failChannelList = new ArrayList<String>();
		if(messageList.isEmpty()){
			return failChannelList;
		}
		for(OmsNotifyMessage message : messageList){
			OmsStatusPushToDopModel pushModel =  new OmsStatusPushToDopModel();
			CommonModel context = new CommonModel();
			//物流公司名称
			context.setCompanyName(companyName);
			//运单号(开单后才有 所以得做非空验证)
			if(message.getWaybillNo() != null){
				context.setWaybillNo(message.getWaybillNo());
			}
			//始发网点
			String departureId = message.getDepartureId();
			//接货网点
			String receivingtoPointid = message.getReceivingtoPointid();
			//操作类型
			context.setOperateType(jiaYunMatchStatus(message.getOrderStatus(), departureId, receivingtoPointid));
			//服务描述 (这个字段没有待确定)
			context.setOperateDetail(message.getRemarks());
			//操作时间
			Date date = null;
			try{
				date = sdfs.parse(message.getCreateTime());
				context.setOperatedAt(sdfs.format(date));
			}catch (ParseException e) {
				context.setOperatedAt("");
			}
			//操作人员
			String userName = message.getOprateUserName();
			//操作联系人电话
			String number = message.getOprateUserMobile();
			//判断操作人员
			context.setOperatorName(StringUtils.isBlank(userName) ? "" : userName);
			//判断操作联系人电话
			context.setOperatorPhone(StringUtils.isBlank(number) ? "" : number);
			//是否派件扫描
			context.setOtherTime("");
			if(OmsNotifyMessageConstant.SENT_SCAN.equals(message.getOrderStatus()) && date != null){
				context.setOtherTime(sdfs.format(date)); 
				//同时生成一个新的状态插入状态推送表，准备下次推送
				ExpOrLtlModel omModel = new ExpOrLtlModel();
				omModel = fabricatedMessage(message,NumberConst.NUMBER_INT_VALUE1,NumberConst.NUMBER_INT_VALUE3,OmsNotifyMessageConstant.BRANCH_START);
				//签收状态
				fabricatedList.add(omModel);
			}
			context.setTracesList(null);
			context.setFieldList(null);
			/**唯一标识*/
			pushModel.setId(message.getId());
			//订单来源
			pushModel.setOrderSource(message.getResource());
			//渠道单号
			pushModel.setChannelOrderNumber(message.getChannerOrder());
			//家洼云豹纹
			pushModel.setContext(context);
			//推送地址
			pushModel.setUrl(message.getUrl());
			//状态
			pushModel.setStatus(message.getOrderStatus());
			//如果状态不在范围内做垃圾数据处理
			if(!"".equals(context.getStatusType())){
				//添加到集合
				statusPushToDopModels.add(pushModel);
			}else{
				garbageList.add(message.getChannerOrder());
			}
		}
		/**
		 * java云推送数据组装完毕，接下来开始推送..
		 */
		failChannelList.addAll(this.commonDopPush(statusPushToDopModels));
		return failChannelList;
	}
	
	
	/**
	 * 淘宝推送,现在淘宝只需要推送已开单（GOT）了  
	 * 2016年11月8日11:25:55）
	 * 342290
	 * 陈军
	 * @param messageList
	 * @return 返回推送失败的渠道单号,并且准备下一次重推
	 */
	public List<String> taobaoPush(List<OmsNotifyMessage> messageList,List<String> garbageList){
		//开单前推送
		List<OmsNotifyMessage> frontList = new ArrayList<OmsNotifyMessage>();
		List<OmsNotifyMessage> afterList = new ArrayList<OmsNotifyMessage>();
		List<OmsNotifyMessage> goodsBackList= new ArrayList<OmsNotifyMessage>();
		List<String> failChannelList = new ArrayList<String>();
		if(messageList.isEmpty()){
			return failChannelList;
		}
		/**
		 * 根据订单状态分为【开单前】，【开单后】,【异常单派送拉回】
		 * 开单前有“已受理”，“开单失败”“已拒绝”，“约车”
		 * 开单后有“已开单”，“已签收”，“签收失败”，“异常单”
		 * 异常单（菜鸟）只有一种状态->GOODS_BACK
		 */
		for(OmsNotifyMessage message : messageList){
			String orderStatus = message.getOrderStatus();
					if(OmsNotifyMessageConstant.ACCEPT.equals(orderStatus) 
							|| OmsNotifyMessageConstant.FAILGOT.equals(orderStatus) 
							|| OmsNotifyMessageConstant.REJECT.equals(orderStatus)
							||OmsNotifyMessageConstant.SHOUTCAR.equals(orderStatus))
					{
						frontList.add(message);
					}
					else if(OmsNotifyMessageConstant.GOT.equals(orderStatus) 
							|| OmsNotifyMessageConstant.SIGNSUCCESS.equals(orderStatus) 
							|| OmsNotifyMessageConstant.SIGNFAILED.equals(orderStatus) 
							|| OmsNotifyMessageConstant.SIGNFAIL.equals(orderStatus))
					{
						afterList.add(message);
					}else if(OmsNotifyMessageConstant.GOODS_BACK.equals(orderStatus))
					{
						goodsBackList.add(message);
					}else{
						//如果不在上述范围内，则判断为垃圾数据
						garbageList.add(message.getChannerOrder());
					}
		}
		/**
		 * 三种情况提出来分别处理
		 */
		List<String> frontChannel = new ArrayList<String>();
		List<String> afterChannel = new ArrayList<String>();
		List<String> goodsBackChannel = new ArrayList<String>();
		if( ! frontList.isEmpty()){
			frontChannel = this.taobaoFrontPush(frontList);
		}
		if( ! afterList.isEmpty()){
			afterChannel = this.taobaoAfterPush(afterList,garbageList);
		}
		if( ! goodsBackList.isEmpty()){
			goodsBackChannel = this.taobaoGoodsBackPush(goodsBackList);
		}
		
		failChannelList.addAll(frontChannel);
		failChannelList.addAll(afterChannel);
		failChannelList.addAll(goodsBackChannel);
		
		return failChannelList;
	}
	
	
	/**
	 * 苏宁推送
	 * 2016年11月8日11:25:55
	 * 342290
	 * 陈军
	 * @param messageList
	 * @return 返回推送失败的渠道单号,并且准备下一次重推
	 * 注意：此渠道可能有垃圾数据
	 */
	public List<String> suningPush(List<OmsNotifyMessage> messageList,List<String> garbageList){
		List<OmsStatusPushToDopModel> statusPushToDopModels = new ArrayList<OmsStatusPushToDopModel>();
		List<String> failChannelList = new ArrayList<String>();
		if(messageList.isEmpty()){
			return failChannelList;
		}
		for(OmsNotifyMessage message : messageList){
			OmsStatusPushToDopModel pushModel =  new OmsStatusPushToDopModel();
			CommonModel context = new CommonModel();
			//SNBody
			SNBody snBody = new SNBody();
			//运单状态对应地址   Y
			snBody.setAddress("");
			if(message.getWaybillNo() != null){
				snBody =  findSnBody(message);
			}
			//运单号 (开单后)
			snBody.setMailNo(message.getWaybillNo());
			// 运单当前状态 01=等待接单 02=不接单 03=已揽收 04=揽收失败 05=运输中 06=已送达 07=已签收 08=签收异常 09=退件 Y
			snBody.setMailStatus(message.getOrderStatus());
			//订单号   Y
			snBody.setOrderId(message.getOrderNo());
			//备注
			snBody.setRemark(message.getRemarks());
			//签收人
			snBody.setSigner("");
			if(message.getWaybillNo() != null){
				//根据运单号获取运行轨迹
				List<OriginalTrackingInfo> waybillTrajectoryInfos = null;
				QueryWaybillTrajectoryRequest model = new QueryWaybillTrajectoryRequest();
				model.setWaybillNo(message.getWaybillNo());
				try {
					waybillTrajectoryInfos = traceService.callWaybillTrace(model);
				} catch (Exception e1) {
					//看看返还的是啥
					waybillTrajectoryInfos = null;
				}
				//当订单签收的时候 才会有签收人
				if(null !=waybillTrajectoryInfos &&  "SIGNSUCCESS".equals(message.getOrderStatus())){
					//订单状态发生改变的时候  备注里面填写的是状态改变原因
					snBody.setSigner(waybillTrajectoryInfos.get(waybillTrajectoryInfos.size()-1).getOperateContent());
				}
			}
			Date date = null;
			try {
				date = sdfs.parse(message.getCreateTime());
				snBody.setTime(sdfs.format(date));
			} catch (ParseException e) {
				LOG.info(ExceptionUtils.getFullStackTrace(e));
			}
			//SNRequest
			SNRequest snRequest = new SNRequest();
			//snBody
			snRequest.setSn_body(snBody);
			context.setSn_request(snRequest);
			pushModel.setChannelOrderNumber(message.getChannerOrder());
			
			//物流公司ID（订单来源）
			pushModel.setOrderSource(message.getResource());
			context.setTracesList(null);
			context.setFieldList(null);
			/** 唯一标识*/
			pushModel.setId(message.getId());
			//开单后实体
			pushModel.setContext(context);
			//推送地址
			pushModel.setUrl(message.getUrl());
			//状态
			pushModel.setStatus(message.getOrderStatus());
			if(StringUtils.isNotBlank(snBody.getMailStatus())){
				//添加到集合
				statusPushToDopModels.add(pushModel);
			}else{
				garbageList.add(message.getChannerOrder());
			}
		}
		/**
		 * 推送实体组装完毕，接下来开始推送数据
		 */
		failChannelList.addAll(this.commonDopPush(statusPushToDopModels));
		return failChannelList;
	}
	
	
	/**
	 * qq速递推送
	 * 2016年11月8日11:26:31
	 * 342290
	 * 陈军
	 * @param messageList
	 * @return 返回推送失败的渠道单号,并且准备下一次重推
	 */
	public List<String> qqSudiPush(List<OmsNotifyMessage> messageList){
		List<OmsStatusPushToDopModel> statusPushToDopModels = new ArrayList<OmsStatusPushToDopModel>();
		List<String> failChannelList = new ArrayList<String>();
		if(messageList.isEmpty()){
			return failChannelList;
		}
		for(OmsNotifyMessage message : messageList){
			OmsStatusPushToDopModel pushModel =  new OmsStatusPushToDopModel();
			CommonModel context = new CommonModel();
			context.setLogisticCompanyID(depponStr);
			context.setLogisticID(message.getChannerOrder());
			Date date = null;
			try {
				date = sdfs.parse(message.getCreateTime());
				context.setGmtUpdated(sdf.format(date));
			} catch (ParseException e) {
				context.setGmtUpdated("");
				LOG.info(ExceptionUtils.getFullStackTrace(e));
			}
			//状态类型
			context.setStatusType(matchState(message.getOrderStatus()));
			//备注
			context.setComment(message.getRemarks());
			//判断是不是开单后
			String ordeStatus = message.getOrderStatus();
			if(OmsNotifyMessageConstant.ACCEPT.equals(ordeStatus) 
					|| OmsNotifyMessageConstant.UNACCEPT.equals(ordeStatus) 
					|| OmsNotifyMessageConstant.NOGET.equals(ordeStatus)){
				//运单号
				context.setMailNo(message.getWaybillNo());
			}
			context.setTracesList(null);
			context.setFieldList(null);
			/**唯一标识**/
			pushModel.setId(message.getId());
			//渠道单号
			pushModel.setChannelOrderNumber(message.getChannerOrder());
			//物流公司ID(订单来源)
			pushModel.setOrderSource(message.getResource());
			//开单后推送实体
			pushModel.setContext(context);
			//推送地址
			pushModel.setUrl(message.getUrl());
			//状态
			pushModel.setStatus(message.getOrderStatus());
			//添加到集合
			statusPushToDopModels.add(pushModel);
		}
		/**
		 * 推送实体组装完毕，接下来开始推送数据
		 */
		failChannelList.addAll(this.commonDopPush(statusPushToDopModels));
		return failChannelList;
	}
	
	
	/**
	 * 快递一百推送
	 * 2016-11-8 11:26:34
	 * 342290
	 * 陈军
	 * @param messageList
	 * @return 返回推送失败的渠道单号,并且准备下一次重推
	 */
	public List<String> express100Push(List<OmsNotifyMessage> messageList){
		List<OmsStatusPushToDopModel> statusPushToDopModels = new ArrayList<OmsStatusPushToDopModel>();
		List<String> failChannelList = new ArrayList<String>();
		if(messageList.isEmpty()){
			return failChannelList;
		}
		for(OmsNotifyMessage message : messageList){
			OmsStatusPushToDopModel pushModel =  new OmsStatusPushToDopModel();
			CommonModel context = new CommonModel();
			context.setLogisticCompanyID(depponStr);
			context.setLogisticID(message.getChannerOrder());
			Date date = null;
			try {
				date = sdfs.parse(message.getCreateTime());
				context.setGmtUpdated(sdf.format(date));
			} catch (ParseException e) {
				context.setGmtUpdated("");
			}
			context.setStatusType(matchYoushang(message.getOrderStatus()));
			context.setComments(message.getRemarks());
			if(OmsNotifyMessageConstant.CANCELLED.equals(message.getOrderStatus())){
				context.setOrderState(OmsNotifyMessageConstant.ACCEPT);
				context.setFeedBcak("运单取消");
			}
			//判断是不是开单后
			String orderStatus = message.getOrderStatus();
			if(OmsNotifyMessageConstant.GOT.equals(orderStatus) 
					|| OmsNotifyMessageConstant.SIGNSUCCESS.equals(orderStatus)
					|| OmsNotifyMessageConstant.SIGNFAILED.equals(orderStatus)){
				//运单号
				context.setMailNo(context.getWaybillNo());
				String waybillNo = context.getWaybillNo();
				if(null != waybillNo && !"".equals(waybillNo)){
					WayBillDetail detail = null;
					//调用FOSS的同步接口
					try {
						detail = findWayBiNo(message);
						if(detail ==  null){
							LOG.info("快递100渠道单号："+message.getChannerOrder()+" ,当前times="+message.getTimes()+" FOSS运单详情查询接口查询失败！！！该单作推送失败处理，times值+1,下次重推");
							throw new PushException("FOSS运单详情查询接口查询失败！！！该单作推送失败处理");
						}else{
							waybillMethod(context, detail);
						}
					} catch (Exception e) {
						LOG.info(ExceptionUtils.getFullStackTrace(e));
						failChannelList.add(message.getChannerOrder());
					}
				}
			}
			//置为空
			context.setTracesList(null);
			//置为空
			context.setFieldList(null);
			/**唯一标识**/
			pushModel.setId(message.getId());
			//渠道单号
			pushModel.setChannelOrderNumber(message.getChannerOrder());
			//物流公司ID(订单来源)
			pushModel.setOrderSource(message.getResource());
			//开单后实体
			pushModel.setContext(context);
			//推送地址
			pushModel.setUrl(message.getUrl());
			//状态
			pushModel.setStatus(message.getOrderStatus());
			//添加到集合
			statusPushToDopModels.add(pushModel);
		}
		/**
		 * 推送实体组装完毕 ，接下来准备进行推送
		 */
		failChannelList.addAll(this.commonDopPush(statusPushToDopModels));
		return failChannelList;
	}
	
	
	/**
	 * 阿里巴巴推送
	 * 2016年11月8日11:26:37
	 * 342290
	 * 陈军
	 * @param messageList
	 * @return 返回推送失败的渠道单号,并且准备下一次重推
	 */
	public List<String> alibabaPush(List<OmsNotifyMessage> messageList,List<String> garbageList){
		List<OmsStatusPushToDopModel> statusPushToDopModels = new ArrayList<OmsStatusPushToDopModel>();
		List<String> failChannelList = new ArrayList<String>();
		if(messageList.isEmpty()){
			return failChannelList;
		}
		for(OmsNotifyMessage message : messageList){
			OmsStatusPushToDopModel pushModel =  new OmsStatusPushToDopModel();
			CommonModel context = new CommonModel();
			context.setLogisticCompanyID(depponStr);
			//渠道单号
			context.setLogisticID(message.getChannerOrder());
			//状态更新时间
			long gmtUpdated = 0;
			Date date = null;
			try {
				date = sdfs.parse(message.getCreateTime());
				gmtUpdated = date.getTime();
			} catch (ParseException e) {
				LOG.info(ExceptionUtils.getFullStackTrace(e));
			}
			context.setGmtUpdated(gmtUpdated+"");
			context.setStatusType(matchState(message.getOrderStatus()));
			context.setComment(message.getRemarks());
			//判断是不是开单后的状态
			String orderStatus = message.getOrderStatus();
			//运单号
			context.setMailNo(message.getWaybillNo());
			//非空校验
			String waybillNo = message.getWaybillNo();
			if(OmsNotifyMessageConstant.GOT.equals(orderStatus) 
					|| OmsNotifyMessageConstant.SIGNSUCCESS.equals(orderStatus) 
					|| OmsNotifyMessageConstant.SIGNFAILED.equals(orderStatus)){
				if(StringUtils.isNotBlank(waybillNo)){
					WayBillDetail detail = null;
					try {
						detail = findWayBiNo(message);
						if(detail ==  null){
							LOG.info("阿里巴巴渠道渠道单号："+message.getChannerOrder()+" ,当前times="+message.getTimes()+" FOSS运单详情查询接口查询失败！！！该单作推送失败处理，times值+1,下次重推");
							throw new PushException("FOSS运单详情查询接口查询失败！！！该单作推送失败处理");
						}else{
							waybillMethod(context, detail);
						}
					} catch (Exception e) {
						LOG.info(ExceptionUtils.getFullStackTrace(e));
						failChannelList.add(message.getChannerOrder());
					}
				}
			}
			context.setTracesList(null);
			context.setFieldList(null);
			/**唯一 标识**/
			pushModel.setId(message.getId());
			//渠道单号
			pushModel.setChannelOrderNumber(message.getChannerOrder());
			//物流公司ID
			pushModel.setOrderSource(message.getResource());
			//开单后实体
			pushModel.setContext(context);
			//推送地址
			pushModel.setUrl(message.getUrl());
			//状态
			pushModel.setStatus(message.getOrderStatus());
			//如果订单状态转换没有失败
			if(StringUtils.isNotBlank(context.getStatusType())){
				//添加到集合
				statusPushToDopModels.add(pushModel);
			}else{
				//作为垃圾数据处理
				garbageList.add(message.getChannerOrder());
				LOG.info("阿里巴巴推送,ID:"+message.getId()+"转换订单状态时匹配失败");
			}
		}
		/**
		 * 订单推送实体组装完毕，接下来开始推送
		 */
		failChannelList.addAll(this.commonDopPush(statusPushToDopModels));
		return failChannelList;
	}
	
	/**
	 * 淘宝开单前状态推送
	 * @param message
	 * @return 返回推送失败的渠道单号,并且准备下一次重推
	 * 2016年11月8日15:02:32
	 * 342290
	 * 陈军
	 */
	public List<String> taobaoFrontPush(List<OmsNotifyMessage> messageList){
		List<OmsStatusPushToDopModel> statusPushToDopModels = new ArrayList<OmsStatusPushToDopModel>();
		List<String> failChannelList = new ArrayList<String>();
		for(OmsNotifyMessage message : messageList){
			OmsStatusPushToDopModel omsStatusPushToDopModel = new OmsStatusPushToDopModel();
			CommonModel context = new CommonModel();
			//淘宝修改订单请求信息
			TaobaoUpdateFieldRequest fieldModel = new TaobaoUpdateFieldRequest();
			//fieldName 淘宝只允许更新1.运单号.mailNo. 更新运单号，，2.weight，货物重量 3.status. 4.exceptions .5 suspect
			fieldModel.setFieldName("status");
			//更新的字段值
			fieldModel.setFieldValue(this.convertTaobaoStatus(message.getOrderStatus()));
			//取消订单、不接单、不拦收时此字段用于填写原因
			fieldModel.setRemark(message.getRemarks());
			if(TWELVE.equals(message.getServiceType()) || FORTY.equals(message.getServiceType())){
				fieldModel.setRemark("");
				fieldModel.setCourierInfo(message.getRemarks());
			}
			//淘宝物流平台的物流编号(渠道单号)
			fieldModel.setTxLogisticID(message.getChannerOrder());
			//电商标识
			context.setEcCompanyId(OmsNotifyMessageConstant.TAOBAO);
			//物流公司ID
			context.setLogisticProviderID(depponStr);
			//更新内容
			List<TaobaoUpdateFieldRequest> fieldList = new ArrayList<TaobaoUpdateFieldRequest>();
			fieldList.add(fieldModel);
			context.setFieldList(fieldList);
			omsStatusPushToDopModel.setOrderSource(message.getResource());
			omsStatusPushToDopModel.setContext(context);
			if(TWELVE.equals(message.getServiceType()) || FORTY.equals(message.getServiceType())){
				//区分是不是裹裹
				omsStatusPushToDopModel.setServiceType(message.getServiceType());
			}
			String url = null;
			if("DBL".equals(message.getProvideId())){
				url = message.getUrl().split(";")[0];
			}else if("DBKD".equals(message.getProvideId())){
				url = message.getUrl().split(";")[1];
			}
			omsStatusPushToDopModel.setId(message.getId());
			//推送地址
			omsStatusPushToDopModel.setUrl(url);
			//渠道单号
			omsStatusPushToDopModel.setChannelOrderNumber(message.getChannerOrder());
			//状态
			omsStatusPushToDopModel.setStatus(message.getOrderStatus());
			statusPushToDopModels.add(omsStatusPushToDopModel);
		}
		/**
		 * 实体【statusPushToDopModels 】组装完毕，下面开始推送...
		 */
		failChannelList.addAll(this.commonDopPush(statusPushToDopModels));
		return failChannelList;
	}
	
	/**
	 * 淘宝开单后状态推送
	 * @param message
	 * @return 返回推送失败的渠道单号,并且准备下一次重推
	 * 2016年11月8日15:02:32
	 * 342290
	 * 陈军
	 *注意事项：该渠道可能有垃圾数据，所以加上garbageList这个参数
	 */
	public List<String> taobaoAfterPush(List<OmsNotifyMessage> messageList,List<String> garbageList){
		List<OmsStatusPushToDopModel> statusPushToDopModels = new ArrayList<OmsStatusPushToDopModel>();
		List<String> failChannelList = new ArrayList<String>();
		for(OmsNotifyMessage message : messageList){
			OmsStatusPushToDopModel omsStatusPushToDopModel = new OmsStatusPushToDopModel();
			CommonModel context = new CommonModel();
			if(OmsNotifyMessageConstant.GOT.equals(message.getOrderStatus())){
				if(TWELVE.equals(message.getServiceType()) || FORTY.equals(message.getServiceType())){
					//淘宝修改订单请求信息
					TaobaoUpdateFieldRequest fieldModel1 = new TaobaoUpdateFieldRequest();
					TaobaoUpdateFieldRequest fieldModel2 = new TaobaoUpdateFieldRequest();
					context.setMailNo(message.getWaybillNo());
					context.setOrderCode(message.getChannerOrder());
					fieldModel1.setTxLogisticID(message.getChannerOrder());
					fieldModel1.setFieldName(mailNoStr);
					fieldModel1.setFieldValue(message.getWaybillNo());
					
					fieldModel2.setFieldName(weightStr);
					fieldModel2.setFieldValue(message.getTotalWeight());
					
					List<TaobaoUpdateFieldRequest> fieldModelList = new ArrayList<TaobaoUpdateFieldRequest>();
					fieldModelList.add(fieldModel1);
					fieldModelList.add(fieldModel2);
					context.setFieldList(fieldModelList);
					omsStatusPushToDopModel.setServiceType(message.getServiceType());
				}else{
					TaobaoUpdateFieldRequest fieldModel1 = new TaobaoUpdateFieldRequest();
					TaobaoUpdateFieldRequest fieldModel2 = new TaobaoUpdateFieldRequest();
					
					fieldModel1.setTxLogisticID(message.getChannerOrder());
					fieldModel1.setFieldName(mailNoStr);
					fieldModel1.setFieldValue(message.getWaybillNo());
					
					fieldModel2.setTxLogisticID(message.getChannerOrder());
					fieldModel2.setFieldName(weightStr);
					fieldModel2.setFieldValue(message.getTotalWeight());
					
					List<TaobaoUpdateFieldRequest> fieldModelList = new ArrayList<TaobaoUpdateFieldRequest>();
					fieldModelList.add(fieldModel1);
					fieldModelList.add(fieldModel2);
					context.setFieldList(fieldModelList);
					omsStatusPushToDopModel.setMailNo(message.getWaybillNo());
				}
			}else{
				context.setTracesList(null);
			}
			String url = null;
			if("DBL".equals(message.getProvideId())){
				url = message.getUrl().split(";")[0];
			}else if("DBKD".equals(message.getProvideId())){
				if(TWELVE.equals(message.getServiceType()) || FORTY.equals(message.getServiceType())){
					//推送地址
					url = message.getUrl().split(";")[2];
					omsStatusPushToDopModel.setServiceType(message.getServiceType());
				}else{
					//推送地址
					url = message.getUrl().split(";")[1];
				}
			}
			/**唯一标识*/
			omsStatusPushToDopModel.setId(message.getId());
			//推送url
			omsStatusPushToDopModel.setUrl(url);
			//渠道单号
			omsStatusPushToDopModel.setChannelOrderNumber(message.getChannerOrder());
			//运单号
			omsStatusPushToDopModel.setMailNo(message.getWaybillNo());
			//物流公司ID(订单来源)
			omsStatusPushToDopModel.setOrderSource(message.getResource());
			//开单后报文
			omsStatusPushToDopModel.setContext(context);
			//区分是不是淘宝
			omsStatusPushToDopModel.setProvideID(message.getProvideId());
			//状态
			omsStatusPushToDopModel.setStatus(message.getOrderStatus());
			//添加到集合,现在的业务是只需要推送已开单的
			if(OmsNotifyMessageConstant.GOT.equals(message.getOrderStatus())){
				statusPushToDopModels.add(omsStatusPushToDopModel);
			}else{
				garbageList.add(message.getChannerOrder());
			}
		}
		/**
		 * 推送实体组装完毕，下面开始推送
		 */
		failChannelList.addAll(this.commonDopPush(statusPushToDopModels));
		return failChannelList;
	}
	
	/**
	 * 菜鸟异常单派送拉回推送
	 * @param message
	 * 2016年11月8日15:02:32
	 * 342290
	 * 陈军
	 * @return
	 */
	public List<String> taobaoGoodsBackPush(List<OmsNotifyMessage> messageList){
		List<GoodsBackPushToDOPRequest> goodsBackPushToDOPRequests = new ArrayList<GoodsBackPushToDOPRequest>();
		List<GoodsBackPushToDOPResponse> responses = new ArrayList<GoodsBackPushToDOPResponse>();
		List<String> failChannelList = new ArrayList<String>();
		for(OmsNotifyMessage message : messageList){
			//定义派送拉回请求对象
			GoodsBackPushToDOPRequest goodsBackPushToDOPRequest = new GoodsBackPushToDOPRequest();
			//物流公司编号
			goodsBackPushToDOPRequest.setCpCode("Deppon");
			//菜鸟异常单ID
			goodsBackPushToDOPRequest.setExceptionOrderId("");
			//物流订单号
			goodsBackPushToDOPRequest.setLogisticsId(message.getChannerOrder());
			//运单号,必填
			goodsBackPushToDOPRequest.setMailNo(message.getWaybillNo());
			//异常编码,必填
			goodsBackPushToDOPRequest.setExceptionCode(message.getRemarks());
			//操作网点ID，必填
			goodsBackPushToDOPRequest.setOperateStation(message.getReceivingtoPointid());
			//处理异常的指令编码
			goodsBackPushToDOPRequest.setActionCode("");
			//备注
			goodsBackPushToDOPRequest.setRemark(message.getRemarks());
			//期望异常处理完成的时间
			goodsBackPushToDOPRequest.setActionTimeLimit("");
			//异常发生时间,必填
			goodsBackPushToDOPRequest.setExceptionTime(convertNaviteDateString(message.getCreateTime()));
			//工单状态,必填
			goodsBackPushToDOPRequest.setExceptionStatus("BUSINESS_INITIAL_STATUS");
			//下一次行动时间
			goodsBackPushToDOPRequest.setNextDispatchTime("");
			//转单物流公司编号
			goodsBackPushToDOPRequest.setTsfcpCode(null);
			//转单运单号
			goodsBackPushToDOPRequest.setTsfmailNo(null);
			//异常操作人手机
			goodsBackPushToDOPRequest.setOperatorMobile(message.getOprateUserMobile());
			//异常操作人电话
			goodsBackPushToDOPRequest.setOperatorPhone(message.getOprateUserMobile());
			//异常发生地点
			goodsBackPushToDOPRequest.setExceptionHappenedPlace("");
			//其他异常处理信息
			goodsBackPushToDOPRequest.setExtendHandlingContent("");
			//Cp异常单ID,必填
			goodsBackPushToDOPRequest.setCpExceptionOrderId(message.getWaybillNo());
			//快递公司自己的异常编码
			goodsBackPushToDOPRequest.setCpexceptionCode("");
			//添加需要推送的对象到集合里面
			goodsBackPushToDOPRequests.add(goodsBackPushToDOPRequest);
		}
		/**
		 * 推送实体组装完毕，下面开始推送
		 */
		try {
			responses = omsNotifyMessageDao.goodsBackPushToDopClient(goodsBackPushToDOPRequests);
			if(responses!=null && !responses.isEmpty()){
				for(GoodsBackPushToDOPResponse response : responses){
					if("false".equals(response.getSuccess())){
						failChannelList.add(response.getLogisticsId());
					}
				}
			}else{
				for(OmsNotifyMessage item : messageList){
					failChannelList.add(item.getChannerOrder());
				}
			}
		}
		catch (Exception e) {
			for(OmsNotifyMessage item : messageList){
				failChannelList.add(item.getChannerOrder());
			}
			LOG.info(ExceptionUtils.getFullStackTrace(e));
		}
		
		return failChannelList;
	}
	
	/**
	 * 菜鸟大件仓配推送接口
	 * 2016年12月20日17:27:39
	 * 342290
	 * 陈军
	 * @param messageList
	 * @return
	 */
	public List<String> cnjzPush(List<OmsNotifyMessage> messageList){
		List<OmsStatusPushToDopModel> statusPushToDopModels = new ArrayList<OmsStatusPushToDopModel>();
		List<String> failChannelList = new ArrayList<String>();
		// 空校验
		if(messageList == null || messageList.size() == 0) {
			return failChannelList;
		}
		/**
		 * 临时解决方案, 数据字典开关，等轨迹CNJZ订阅稳定可以把这个渠道彻底删除
		 */
		DictionaryDataEntity dict = new DictionaryDataEntity();
		dict = dictionaryService.getDictDateByCode("CNJZ_OLD_STATE_PUSH_SWITCH", "SWITCH_STATE");
		String dictValue = dict.getValueName();
		if("OFF".equals(dictValue)){
			List<String> idList = new ArrayList<String>();
			for(OmsNotifyMessage item : messageList){
				idList.add(item.getId());
			}
			try{
				omsNotifyMessageDao.updateByPrimaryKey(idList);
				return failChannelList;
			}
			catch(Exception upException){
				LOG.info("菜鸟大件仓配在DOP状态的DOP推送的时候出现了DAO层异常");
			}
		}else if("ON".equals(dictValue)){
			for(OmsNotifyMessage message : messageList){
				OmsStatusPushToDopModel pushModel = new OmsStatusPushToDopModel();
				/**
				 * 封装CnjzPushToDopModel
				 */
				pushModel.setChannelOrderNumber(message.getChannerOrder());
				pushModel.setId(message.getId());
				pushModel.setOrderSource(message.getResource());
				//把德邦状态转换为菜鸟状态
				pushModel.setStatus(message.getOrderStatus());
				pushModel.setUrl(message.getUrl());
				
				/**
				 * 封装content
				 */
				CommonModel content = new CommonModel();
				//测试环境是 :DISTRIBUTOR_476661
				//该数据字典项配置在 【系统配置->数据字典->总分类->状态推送配置信息->菜鸟大件仓配服务编码】
				DictionaryDataEntity dictionaryEntity = new DictionaryDataEntity();
				dictionaryEntity = dictionaryService.getDictDateByCode("CNJZ_PUSH_SERVICE_CODE_TYPE", "CNJZ_PUSH_SERVICE_CODE");
				if(StringUtils.isNotBlank(dictionaryEntity.getValueName())){
					content.setServiceCode(dictionaryEntity.getValueName());
				}else{
					content.setServiceCode("DISTRIBUTOR_13159133");
				}
				content.setUniqueCode(message.getId());
				//把德邦状态转换为菜鸟状态
				content.setStatus(this.matchCaiNiaoCangPei(message.getOrderStatus()));
				String userNumer = message.getOprateUserNum();
				content.setOperator(StringUtils.isBlank(userNumer) ? "DEPPON" : userNumer);
				String userPhone = message.getOprateUserMobile();
				content.setOperatorPhone(StringUtils.isBlank(userPhone) ? "95353" : userPhone);
				content.setOperatTime(message.getCreateTime());
				content.setFieldList(null);
				content.setTracesList(null);
				String text = "";
				if("GOT".equals(message.getOrderStatus())){
					text = "配送公司揽货成功";
				}else if("IN_TRANSIT".equals(message.getOrderStatus())){
					text = "订单配送中";
				}else if("SIGNSUCCESS".equals(message.getOrderStatus())){
					text="订单已签收，感谢使用菜鸟网络服务";
				}else if("SIGNFAIL".equals(message.getOrderStatus())){
					text="订单已拒收,拒收原因"+message.getRemarks();
				}else{
					//强制把垃圾状态直接更新，省的后面麻烦
					omsNotifyMessageDao.forceUpdateGarbage(message.getChannerOrder(), message.getOrderStatus());
				}
				content.setContent(text);
				List<GoodsItem> goodsList = new ArrayList<GoodsItem>();
				//渠道单号
				String channelNumber = message.getChannerOrder();
				//根据渠道单号查询报文实体
				List<OrderDispatchEntity> dispatchList =  orderDispatchDao.selectByChannelNumber(channelNumber);
				try{
					if(CollectionUtils.isNotEmpty(dispatchList)){
						//取出原始报文进行解析
						OrderDispatchEntity entity = dispatchList.get(0);
						String jsonStr = entity.getParameter();
						CaiNiaoCangPeiPlaceAnOrderInfo info = JsonMapperUtil.readValue(jsonStr, CaiNiaoCangPeiPlaceAnOrderInfo.class);
						if(info != null){
							PackageInfo packageInfo = info.getPackageInfo();
							if(packageInfo != null){
								goodsList = packageInfo.getGoodsItems();
							}
						}
						content.setOrderCode(info.getOrder().getTmsCode());
						content.setMailNo(info.getOrder().getMailNo());
					}
				}catch(JsonParseException e1){
					LOG.info(ExceptionUtils.getFullStackTrace(e1));
				}catch(JsonMappingException e2){
					LOG.info(ExceptionUtils.getFullStackTrace(e2));
				}catch(IOException e3){
					LOG.info(ExceptionUtils.getFullStackTrace(e3));
				}
				content.setGoodsList(goodsList);
				content.setRemark(message.getRemarks());
				pushModel.setContext(content);
				String orderStatus = message.getOrderStatus();
				if("IN_TRANSIT".equals(orderStatus) ||  "GOT".equals(orderStatus) || "SIGNSUCCESS".equals(orderStatus)
						|| "SIGNFAIL".equals(orderStatus) || "FAILGOT".equals(orderStatus)){
					statusPushToDopModels.add(pushModel);
				}
			}
		}else{
			//不做任何操作
		}
		/**
		 * 推送实体组装完毕，接下来开始推送..
		 */
		failChannelList.addAll(this.commonDopPush(statusPushToDopModels));
		return failChannelList;
	}
	
	/**
	 * 零担快乐购对接
	 * 342290 陈军
	 * 2017年6月29日11:44:35
	 * @param messageList
	 * @return
	 */
	public List<String> happyGoPush(List<OmsNotifyMessage> messageList){
		List<String> list = new ArrayList<String>(0);
		//如果为空，直接返回上级方法
		if(CollectionUtils.isEmpty(messageList)){
			return list;
		}
		for(OmsNotifyMessage message : messageList){
			
		}
		return null;
	}
	
	/**
	 * 标准推送接口
	 * 2016年11月9日16:07:56
	 * 342290 陈军
	 * @param messageList
	 * @return 返回失败的渠道单号
	 */
	public List<String> standardPush(List<OmsNotifyMessage> messageList,List<String> garbageList){
		//标准接口开单前集合
		List<OmsNotifyMessage> frontList = new ArrayList<OmsNotifyMessage>();
		//标准接口开单后集合
		List<OmsNotifyMessage> afterList = new ArrayList<OmsNotifyMessage>();
		List<String> failChannelList = new ArrayList<String>();
		if(messageList.isEmpty()){
			return failChannelList;
		}
		for(OmsNotifyMessage message : messageList){
			String url = message.getUrl();
			if(StringUtils.isBlank(url)){
				garbageList.add(message.getChannerOrder());
				continue;
			}
			String orderStatus = message.getOrderStatus();
			if((OmsNotifyMessageConstant.REJECT).equals(orderStatus)  //REJECT
					|| (OmsNotifyMessageConstant.ACCEPT).equals(orderStatus) 
					|| (OmsNotifyMessageConstant.FAILGOT).equals(orderStatus))  //FAILGOT
			{
				frontList.add(message);
			}
			else if((OmsNotifyMessageConstant.GOT).equals(orderStatus) 
					|| (OmsNotifyMessageConstant.SIGNFAILED).equals(orderStatus)  
					|| (OmsNotifyMessageConstant.SIGNSUCCESS).equals(orderStatus))
			{
				afterList.add(message);
			}else{
				//如果不在上述范围内，则判定为垃圾数据
				garbageList.add(message.getChannerOrder());
			}
		}
		/*
		 * 二种情况提出来分别处理
		 */
		List<String> frontChannel = this.standardFrontPush(frontList); //开单前处理完返回失败的渠道单号
		List<String> afterChannel = this.standardAfterPush(afterList);  //开单后处理完返回失败的渠道单号
		
		failChannelList.addAll(frontChannel); //开单前的
		failChannelList.addAll(afterChannel); //开单后的
		return failChannelList;
	}
	
	/**
	 * 标准推送接口（开单前）
	 * 2016年11月9日16:07:56
	 * 342290 陈军
	 * @param messageList
	 * @return 返回失败的渠道单号
	 */
	public List<String> standardFrontPush(List<OmsNotifyMessage> messageList){
		List<String> failChannelList = new ArrayList<String>();
		if(CollectionUtils.isEmpty(messageList)){
			return failChannelList;
		}
		List<OmsStatusPushToDopModel> statusPushToDopModels = new ArrayList<OmsStatusPushToDopModel>();
		for(OmsNotifyMessage message : messageList){
			OmsStatusPushToDopModel pushMode = new OmsStatusPushToDopModel();
			CommonModel context = new CommonModel();
			//渠道单号
			context.setLogisticID(message.getChannerOrder());
			//物流公司ID(订单来源)
			context.setLogisticCompanyID(depponStr);
			//反馈原因 (备注)
			context.setComments(message.getRemarks());
			pushMode.setChannelOrderNumber(message.getChannerOrder());
			pushMode.setOrderSource(message.getResource());
			//状态类型(channerOrder)
			context.setStatusType(message.getOrderStatus());
			//状态更新时间
			long gmtUpdated;
			Date date = null;
			try {
				date = sdfs.parse(message.getCreateTime());
				gmtUpdated = date.getTime();
			} catch (ParseException e1) {
				gmtUpdated = 0;
			}
			context.setGmtUpdated(gmtUpdated+"");
			context.setTracesList(null);
			context.setFieldList(null);
			/** 唯一标识*/
			pushMode.setId(message.getId());
			//推送地址
			pushMode.setUrl(message.getUrl());
			//状态
			pushMode.setStatus(message.getOrderStatus());
			//开单前实体
			pushMode.setContext(context);
			//添加到集合
			statusPushToDopModels.add(pushMode);
		}
		/**
		 * 推送实体组装完毕，接下来开始推送..
		 */
		failChannelList.addAll(this.commonDopPush(statusPushToDopModels));
		return failChannelList;
	}
	
	
	/**
	 * 标准推送接口（开单后）
	 * 2016年11月9日16:07:56
	 * 342290 陈军
	 * @param messageList
	 * @return 返回失败的渠道单号
	 */
	public List<String> standardAfterPush(List<OmsNotifyMessage> messageList){
		List<String> failChannelList = new ArrayList<String>();
		if(CollectionUtils.isEmpty(messageList)){
			return failChannelList;
		}
		List<OmsStatusPushToDopModel> statusPushToDopModels = new ArrayList<OmsStatusPushToDopModel>();
		for(OmsNotifyMessage message : messageList){
			OmsStatusPushToDopModel pushMode = new OmsStatusPushToDopModel();
			CommonModel context = new CommonModel();
			//渠道单号
			context.setLogisticID(message.getChannerOrder());
			pushMode.setChannelOrderNumber(message.getChannerOrder());
			//物流公司ID(订单来源)
			context.setLogisticCompanyID(depponStr);
			pushMode.setOrderSource(message.getResource());
			//反馈原因 (备注)
			context.setComments(message.getRemarks());
			//状态类型(channerOrder)
			context.setStatusType(message.getOrderStatus());
			//状态更新时间
			long gmtUpdated;
			Date date = null;
			try {
				date = sdfs.parse(message.getCreateTime());
				gmtUpdated = date.getTime();
			} catch (ParseException e1) {
				gmtUpdated = 0;
			}
			context.setGmtUpdated(gmtUpdated+"");
			String waybillNo = message.getWaybillNo();
			//运单号
			context.setMailNo(message.getWaybillNo());
			if(null != waybillNo && !"".equals(waybillNo)){
				WayBillDetail detail = null;
				try {
					detail = findWayBiNo(message);
					if(detail ==  null){
						LOG.info("【标准推送接口开单后】状态渠道单号："+message.getChannerOrder()+" ,当前times="+message.getTimes()+" FOSS运单详情查询接口查询失败！！！该单作推送失败处理，times值+1,下次重推");
						throw new PushException("FOSS运单详情查询接口查询失败！！！该单作推送失败处理");
					}else{
						waybillMethod(context, detail);
					}
				} catch (Exception e) {
					LOG.info(ExceptionUtils.getFullStackTrace(e));
					failChannelList.add(message.getChannerOrder());
				}
			}
			context.setTracesList(null);
			context.setFieldList(null);
			/** 唯一标识*/
			pushMode.setId(message.getId());
			//开单后实体
			pushMode.setContext(context);
			//推送信息 还有 url(推送地址)  
			pushMode.setUrl(message.getUrl());
			//状态
			pushMode.setStatus(message.getOrderStatus());
			//添加到集合
			if(StringUtils.isNotBlank(message.getUrl())){
				statusPushToDopModels.add(pushMode);
			}
		}
		/**
		 * 推送实体组装完毕，接下来开始推送..
		 */
		failChannelList.addAll(this.commonDopPush(statusPushToDopModels));
		return failChannelList;
	}
	
	/**
	 * 公用的DOP推送方法,只要推送实体是List<OmsStatusPushToDopModel>形式的，都可以公用此方法
	 * @return
	 */
	public List<String> commonDopPush( List<OmsStatusPushToDopModel> omsStatusPushToDopModels){
		List<String> failChannelList   = new ArrayList<String>();
		if(CollectionUtils.isEmpty(omsStatusPushToDopModels)){
			return failChannelList;
		}
		try {
			EbmResponse ebmResponseModel = omsNotifyMessageDao.omsNotifyDopClient(omsStatusPushToDopModels);
			if(ebmResponseModel != null){
				String errMsg = ebmResponseModel.getErrorMsg();
				if(StringUtils.isNotBlank(errMsg)){
					List<String> list = this.parseListJson(ebmResponseModel.getMessage());
					failChannelList.addAll(list);
				}
			}else{
				for(OmsStatusPushToDopModel item : omsStatusPushToDopModels){
					failChannelList.add(item.getChannelOrderNumber());
				}
			}
		} catch (Exception e) {
			/** 异常作为错误处理*/
			for(OmsStatusPushToDopModel item : omsStatusPushToDopModels){
				failChannelList.add(item.getChannelOrderNumber());
			}
			LOG.info(ExceptionUtils.getFullStackTrace(e));
		}
		return failChannelList;
	}
	
	
	/**
	 * 把德邦状态转换为淘宝状态
	 * @param depponStatus
	 * @return
	 */
	public String convertTaobaoStatus(String depponStatus){
		String taobaoStatus = "";
		if(OmsNotifyMessageConstant.ACCEPT.equals(depponStatus) 
				|| OmsNotifyMessageConstant.SHOUTCAR.equals(depponStatus) 
				|| OmsNotifyMessageConstant.RECEIPTING.equals(depponStatus))
		{
			taobaoStatus = OmsNotifyMessageConstant.ACCEPT;
		}
		else if(OmsNotifyMessageConstant.FAILGOT.equals(depponStatus)){
			taobaoStatus = "NOT_SEND";
		}
		else if(OmsNotifyMessageConstant.REJECT.equals(depponStatus)){
			taobaoStatus = OmsNotifyMessageConstant.UNACCEPT;
		}
		return taobaoStatus;
	}
	
	/**
	 * <p>(把德邦状态转换成QQ速递的状态)</p> 
	 * @date 2016年11月9日10:24:21
	 * @param 342290
	 * @return 陈军
	 */
	public String matchState(String depponStatus){
		String tmStatus="";
		if(OmsNotifyMessageConstant.WAITACCEPT.equals(depponStatus)){
			tmStatus = "WAITACCEPT";
		}else if(OmsNotifyMessageConstant.ACCEPT.equals(depponStatus)){
			tmStatus = OmsNotifyMessageConstant.ACCEPT;
		}else if(OmsNotifyMessageConstant.CANCEL.equals(depponStatus)){
			tmStatus = "CANCELLED";
		}else if(OmsNotifyMessageConstant.REJECT.equals(depponStatus)){
			tmStatus = OmsNotifyMessageConstant.UNACCEPT;
		}else if(OmsNotifyMessageConstant.SIGNSUCCESS.equals(depponStatus)){
			tmStatus = OmsNotifyMessageConstant.SIGNSUCCESS;
		}else if(OmsNotifyMessageConstant.SIGNFAIL.equals(depponStatus)){
			tmStatus = "SIGNFAILED";
		}else if(OmsNotifyMessageConstant.FAILGOT.equals(depponStatus)){
			tmStatus = "NOGET";
		}else if(OmsNotifyMessageConstant.GOT.equals(depponStatus)){
			tmStatus = "GOT";
		}
		return tmStatus;
	}
	
	/**
	 * <p>(把德邦状态转换成Tmall家装的状态)</p> 
	 * @date 2016-5-13 下午10:03:00
	 * @param oldStatus
	 * @param departureId
	 * @param receivingtoPointid
	 * @return
	 */
	public String matchTmallStatus(String oldStatus){
		String tmStatus="";
		// 已受理(接单成功)
		if((OmsNotifyMessageConstant.ACCEPT).equals(oldStatus)){
			tmStatus = "TMS_ACCEPT";
		// 已开单(揽货成功)
		}else if((OmsNotifyMessageConstant.GOT).equals(oldStatus)){
			tmStatus = "TMS_CANVASS";
		// 揽货失败  
		}else if((OmsNotifyMessageConstant.FAILGOT).equals(oldStatus)){
			tmStatus = "TMS_UNCANVASS";
		//干线开始(出发城市作为干线开始)
		}else if(OmsNotifyMessageConstant.TRUCKDEPARTURE.equals(oldStatus)){
			tmStatus = "TMS_TRUNK_START";
		//干线结束(到大城市作为干线结束)
		}else if(OmsNotifyMessageConstant.CANCELTRUCKARRIVAL.equals(oldStatus)){
			tmStatus = "TMS_TRUNK_END";
		//支线开始
		}else if(OmsNotifyMessageConstant.TMSBRANCHSTART.equals(oldStatus)){
			tmStatus = OmsNotifyMessageConstant.TMSBRANCHSTART;
		//支线结束
		}else if(OmsNotifyMessageConstant.TMSBRANCHEND.equals(oldStatus)){
			tmStatus = OmsNotifyMessageConstant.TMSBRANCHEND;
		//派送：若运单提货方式为“派送”几种类型	
		//安装开始	
		//家装派送(家装过来的派送)
		}else if(OmsNotifyMessageConstant.SENT_SCAN.equals(oldStatus)
				||OmsNotifyMessageConstant.ORDER_STATUS.equals(oldStatus)){
			tmStatus = "TMS_APPOINTMENT";
		}else if(OmsNotifyMessageConstant.TMSINSTALLSTART.equals(oldStatus)){
			tmStatus = OmsNotifyMessageConstant.TMSINSTALLSTART;
		//安装结束(签收先推送安装成功之后推送签收成功)
		}else if(OmsNotifyMessageConstant.SIGNSUCCESS.equals(oldStatus)){
			//取FOSS运单签收作为结点，在签收状态到达DOP后，先推送安装结束，再推送签收
			tmStatus = "TMS_INSTANLL_END";
		//拒单||签收(签收成功)
		}else if(OmsNotifyMessageConstant.REJECT.equals(oldStatus)
				||OmsNotifyMessageConstant.TMSSIGN.equals(oldStatus)){
			tmStatus = OmsNotifyMessageConstant.TMSSIGN;
		}
		return tmStatus;
	}
	
	/**
	 * <p>(把德邦状态转换成家洼云的状态)</p> 
	 * @date 2016-5-13 下午10:03:13
	 * @param oldStatus
	 * @param departureId
	 * @param receivingtoPointid
	 * @return
	 */
	public String jiaYunMatchStatus(String oldStatus,String departureId,String receivingtoPointid){
		String tmStatus="";
		int count = omsNotifyMessageDao.findDeparture(departureId, receivingtoPointid);
		switch(oldStatus){
			case OmsNotifyMessageConstant.ACCEPT:
				tmStatus = OmsNotifyMessageConstant.ACCEPT;
				break;
			case OmsNotifyMessageConstant.GOT:
				tmStatus = OmsNotifyMessageConstant.CANVASS;
				break;
			case OmsNotifyMessageConstant.FAILGOT:
				tmStatus = OmsNotifyMessageConstant.UNCANVASS;
				break;
			case OmsNotifyMessageConstant.TRUCKDEPARTURE:
				if(count == 0){
					tmStatus = OmsNotifyMessageConstant.TRUNK_START;
				}
				break;
			case OmsNotifyMessageConstant.CANCELTRUCKARRIVAL:
				if(count == 0){
					tmStatus = OmsNotifyMessageConstant.TRUNK_END;
				}
				break;
			case OmsNotifyMessageConstant.SENT_SCAN:
				tmStatus = "APPOINTMENT";//支线开始
				//同时把 支线开始 插入到数据库中去 + ";BRANCH_START
				break;
			case OmsNotifyMessageConstant.BRANCH_START:
				tmStatus = OmsNotifyMessageConstant.BRANCH_START;
				break;
			case OmsNotifyMessageConstant.SIGNSUCCESS:
				tmStatus = OmsNotifyMessageConstant.SIGN;
				break;
			default:
				tmStatus=""; 
				break;
		}
		return tmStatus;
	}
	
	/**
	 * <p>(把德邦状态转换成快递100的状态)</p> 
	 * @date 2016年11月9日10:48:29
	 * @param depponStatus
	 * @return 342290 陈军
	 */
	public String matchYoushang(String depponStatus){
		String tmStatus="";
		if(OmsNotifyMessageConstant.WAITACCEPT.equals(depponStatus)){
			tmStatus = "WAIT_ACCEPT";
		}else if(OmsNotifyMessageConstant.ACCEPT.equals(depponStatus)){
			tmStatus = OmsNotifyMessageConstant.ACCEPT;
		}else if(OmsNotifyMessageConstant.CANCELLED.equals(depponStatus)){
			tmStatus = "CANCEL";
		}else if(OmsNotifyMessageConstant.UNACCEPT.equals(depponStatus)){
			tmStatus = "REJECT";
		}else if(OmsNotifyMessageConstant.SIGNSUCCESS.equals(depponStatus)){
			tmStatus = OmsNotifyMessageConstant.SIGNSUCCESS;
		}else if(OmsNotifyMessageConstant.SIGNFAILED.equals(depponStatus)){
			tmStatus = "SIGNFAIL";
		}else if(OmsNotifyMessageConstant.NOGET.equals(depponStatus)){
			tmStatus = "FAILGOT";
		}else if(OmsNotifyMessageConstant.GOT.equals(depponStatus)){
			tmStatus = "GOT";
		}
		return tmStatus;
	}
	
	/**
	 * 把德邦状态转换为菜鸟大件仓配的状态
	 * @param depponStatus
	 * @return
	 */
	public String matchCaiNiaoCangPei(String depponStatus){
		String result = "";
		if(StringUtils.isBlank(depponStatus)){
			return result;
		}else{
			//已开单(也叫已揽收)
			if("GOT".equals(depponStatus)){
				result="TRUNK_ACCEPT";
			}
			//在途
			else if("IN_TRANSIT".equals(depponStatus)){
				result="TRUNK_DELIVERING";
			}
			//正常签收
			else if("SIGNSUCCESS".equals(depponStatus)){
				result="TRUNK_SIGN";
			}
			//异常签收(拒收)
			else if("SIGNFAIL".equals(depponStatus)){
				result="TRUNK_SIGN_FAILED";
			}
			//以上都不是
			else{
				result="";
			}
		}
		return result;
	}
	
	/**
	 * @author 342290
	 * @date 2016年11月8日17:28:59
	 * @param param
	 * @return
	 */
	public List parseListJson(String param) {
		if (StringUtils.isEmpty(param)) {
			return null;
		}
		try {
			return FastJsonUtil.parseList(param, OmsStatusPushToDopModel.class);
		} catch (Exception ex) {
			LOG.info(ExceptionUtils.getFullStackTrace(ex));
		}
		return null;
	}
	
	/**
	 * <p>(findSnBody)</p> 
	 * @param list
	 * @param i
	 * @param snBody
	 */
	public SNBody findSnBody(OmsNotifyMessage message) {
		SNBody snBody = new SNBody();
		//根据运单号 查询运单轨迹
		List<OriginalTrackingInfo> waybillTrajectoryInfos = null;
		QueryWaybillTrajectoryRequest model = new QueryWaybillTrajectoryRequest();
		model.setWaybillNo(message.getWaybillNo());
		try {
			//轨迹
			waybillTrajectoryInfos = traceService.callWaybillTrace(model);
		} 
		catch (Exception e1) {
			waybillTrajectoryInfos = null;
		}
		//非空校验
		if(null == waybillTrajectoryInfos){
			snBody.setAddress("");
		}else if("GOT".equals(message.getOrderStatus()) && !waybillTrajectoryInfos.isEmpty()){
			//取第一条数据，开单状态没有签收人
			snBody.setAddress(waybillTrajectoryInfos.get(0).getOperateDept());
		}else if((message.getOrderStatus()).contains("SIGN")){
			//取最后一条数据
			snBody.setAddress(waybillTrajectoryInfos.get(waybillTrajectoryInfos.size()-1).getOperateDept());
		}
		return snBody;
	}
	
	/**
	 * OMS<--------->FOSS同步接口
	 * 根据运单号查询运单详情
	 * @param list
	 * @param i
	 * @param beforeModel
	 * @param erryList
	 */
	private WayBillDetail findWayBiNo(OmsNotifyMessage message) throws Exception{
		//运单详细
		WayBillDetail waybillDetail = null;
		try{
			//运单查询接口参数
			OrdWaybillRelateRequest request = new OrdWaybillRelateRequest();
			List<String> waybills = new ArrayList<String>();
			waybills.add(message.getWaybillNo());
			request.setWaybillNo(waybills);
			//接口对象返回
			OrdWaybillRelateResponse response = ordWaybillRelateClient.ordWaybillRelateQuerydata(request);
			//判断
			if(response != null && response.getWayBillDetailList() != null && response.getWayBillDetailList().size() > 0){
				waybillDetail = response.getWayBillDetailList().get(0);
			}
		}catch(Exception e){
			// 日志
			LOG.error("运单信息查询异常：" + e.getMessage(),e);
			throw e;
		}
		return waybillDetail;
	}
	
	
	/**
	 * @date 2016-5-13 下午10:05:38
	 * @param beforeModel
	 * @param wayBillDetail
	 */
	public void waybillMethod(CommonModel context,WayBillDetail wayBillDetail) {
		// 汇总费用
		double charges = 0.0;
		//发货人详细
		Sender senderModel = new Sender();
		//发货人公司
		senderModel.setCompanyName(null);
		//发货人姓名
		senderModel.setName(wayBillDetail.getSender());
		//发货人邮编
		senderModel.setPostCode(null);
		//发货人电话
		senderModel.setPhone(wayBillDetail.getSenderPhone());
		//发货人手机
		senderModel.setMobile(wayBillDetail.getSenderMobile());
		//发货人省份
		senderModel.setProvince(wayBillDetail.getSenderProvinceName());
		//发货人城市
		senderModel.setCity(wayBillDetail.getSenderCityName());
		//发货人区县
		senderModel.setCounty(null);
		//发货人详细地址
		senderModel.setAddress(wayBillDetail.getSenderAddress());
		context.setSender(senderModel);
		//收货人 详细
		Receiver receicerModel = new Receiver();
		//收货人公司
		receicerModel.setCompanyName(null);
		//收货人姓名
		receicerModel.setName(wayBillDetail.getConsignee());
		//收货人邮编
		receicerModel.setPostCode(null);
		//收货人电话
		receicerModel.setPhone(wayBillDetail.getConsigneePhone());
		//收货人手机
		receicerModel.setMobile(wayBillDetail.getConsigneeMobile());
		//收货人省份
		receicerModel.setProvince(wayBillDetail.getConsigneeProvinceName());
		//收货人城市
		receicerModel.setCity(wayBillDetail.getConsigneeCityName());
		//收货人区县
		receicerModel.setCounty(null);
		//收货人详细地址
		receicerModel.setAddress(wayBillDetail.getConsigneeAddress());
		context.setReceiver(receicerModel);
		//货物名称
		context.setCargoName(wayBillDetail.getGoodName());
		//总件数
		context.setTotalNumber(wayBillDetail.getPieces()+"");
		//总重量，单位：kg 总件数 保留小数点后2位
		context.setTotalWeight(Double.parseDouble(new DecimalFormat("0.00").format(wayBillDetail.getWeight()))+"");
		//总体积，单位m3  保留小数点后2位
		context.setTotalVolume(Double.parseDouble(new DecimalFormat("0.00").format(wayBillDetail.getCubage()))+"");
		//总价格
		context.setTotalPrice(wayBillDetail.getTotalCharge()+"");
		/*
		 * 修改描述：FOSS中总运费包含了代收货款的钱，计算其他金额时需要将代收货款部分的钱减掉
		 */
		if(null != wayBillDetail.getRefund()){
			context.setTotalPrice(wayBillDetail.getTotalCharge().subtract(wayBillDetail.getRefund())+"");
		}
		//发货地址编码
		context.setBusinessNetworkNo(wayBillDetail.getDepartureDeptNumber());
		//收货地址编码
		context.setToNetworkNo(wayBillDetail.getLadingStationNumber());
		//等通知发货
		context.setWaitNotifySend("N");
		//运费
		context.setTransportPrice(wayBillDetail.getPublishCharge()+"");
		if (null != wayBillDetail.getPublishCharge()) {
			charges += wayBillDetail.getPublishCharge().doubleValue();
		}
		//支付方式
		if(null != wayBillDetail.getPayment()){
			context.setPayType(getPayType(wayBillDetail.getPayment()));
		}
		//运输方式
		if(null != wayBillDetail.getTranProperty()){
			context.setTransportType(getTransType(wayBillDetail.getTranProperty()));
		}
		//保价金额
		context.setInsuranceValue(wayBillDetail.getInsuranceValue()+"");
		//保价费
		context.setInsurancePrice(wayBillDetail.getInsurance()+"");
		// 累计费用
		if (null != wayBillDetail.getInsuranceValue()) {
			charges += wayBillDetail.getInsuranceValue().doubleValue();
		}
		//代收货款类型（三日还，即日还等）
		if(null != wayBillDetail.getRefundType()){
			context.setCodType(convertCodType(wayBillDetail.getRefundType()));
		}
		//代收货款
		context.setCodValue(wayBillDetail.getRefund()+"");
		//代收货款费
		context.setCodPrice(wayBillDetail.getRefundFee()+"");
		// 累计费用
		if (null != wayBillDetail.getRefundFee()) {
			//费用累加
			charges += wayBillDetail.getRefundFee().doubleValue();
		}
		//是否上门接货
		if(null != wayBillDetail.getIsDoorToDoorPick()){
			//是否接货
			context.setVistReceive(isVistReceive(wayBillDetail.getIsDoorToDoorPick()));
		}
		//上门接货费
		context.setVistReceivePrice(wayBillDetail.getConsignCharge()+"");
		// 累计费用
		if (null != wayBillDetail.getConsignCharge()) {
			//费用累计
			charges += wayBillDetail.getConsignCharge().doubleValue();
		}
		//送货方式
		context.setDeliveryType(getPickGoodType(wayBillDetail.getDeliveryType()));
		//送货费
		context.setDeliveryPrice(wayBillDetail.getDeliveryCharge()+"");
		// 累计费用
		if (null != wayBillDetail.getDeliveryCharge()) {
			//费用累计
			charges += wayBillDetail.getDeliveryCharge().doubleValue();
		}
		//签收回单
		if(null != wayBillDetail.getSignBillBackWay()){
			//签收回单
			context.setBackSignBill(getReturnBillType(wayBillDetail.getSignBillBackWay()));
		}
		//包装
		if(!"".equals(wayBillDetail.getPacking()) && null != wayBillDetail.getPacking()){
			//包装
			context.setPackageService(wayBillDetail.getPacking());
		}
		//包装费
		context.setPackageServicePrice(wayBillDetail.getPickCharge()+"");
		// 累计费用
		if (null != wayBillDetail.getPickCharge()) {
			// 累计费用
			charges += wayBillDetail.getPickCharge().doubleValue();
		}
		//等通知发货
		context.setSmsNotify("N");
		//短信通知费(等通知放货业务已取消，不累积费用)
		context.setSmsNotifyPrice("");
		// 运单的其他费用，主要查询“返单费用”、“综合服务费”、“等通知放货费”、"燃油附加费"这几类费用
		List<WaybillCostInfo> otherCharges = wayBillDetail.getWaybillCostInfos();
		for(WaybillCostInfo costInfo : otherCharges){
			charges = findCostinfo(context, wayBillDetail, charges,costInfo);
		}
		//备注
		if(null != wayBillDetail.getTranDesc() && !"".equals(wayBillDetail.getTranDesc())){
			context.setRemark(wayBillDetail.getTranDesc());
		}
		//到达网点名称
		context.setLadingStationName(wayBillDetail.getLadingStationName());
		//到达网点地址
		context.setLadingStationAddr(wayBillDetail.getLadingStationAddr());
		//到达网点电话
		context.setLadingStationPhone(wayBillDetail.getLadingStationPhone());
	}
	
	/**转换代收货款类型
	 * @author 342290
	 * 陈军
	 * @date 2016年11月9日11:19:54
	 * @param beforeModel
	 * @param wayBillDetail
	 */
	public String convertCodType(String codType) {
		String result = "";
		//即日退
		if("R1".equals(codType)){
			result = "1";
		}
		//三日退
		if("R3".equals(codType)){
			result = "3";
		}
		//其他情况，如审核退，目前有这么一种方式，但渠道不处理
		if("RA".equals(codType) || "".equals(codType)){
			result = "";
		}
		return result;
	}
	
	/**
	 * 把date.toString格式的时间字符串转换为yyyy-MM-dd HH:mm:ss格式的
	 * @param naviteDateString
	 * @return
	 */
	private String convertNaviteDateString(String naviteDateString){
		String result = "";
		try {
			Date date = sdfs.parse(naviteDateString);
			result = DateUtils.formatDate(date, "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * 判断是否上门接货
	 * @param b
	 * @return
	 * 342290
	 * 陈军
	 *2016年11月9日11:20:08
	 */
	public String isVistReceive(boolean b){
		if (b) {
			return "Y";
		} else {
			return "N";
		}
	}
	
	/**
	 * @author 342290 陈军
	 * @date 2016年11月9日11:20:17
	 * @param beforeModel
	 * @param wayBillDetail
	 * @param charges
	 * @param costInfo
	 * @return
	 */
	public double findCostinfo(CommonModel beforeModel,WayBillDetail wayBillDetail, double charges,WaybillCostInfo costInfo) {
		// 其他费用名称
		String chargeName = costInfo.getCostName();
		BigDecimal chargeAmount = costInfo.getCostMoney();
		// 返单费用
		if ("QS".equals(chargeName)) {
			//返单费用
			beforeModel.setBackSignBillPrice(chargeAmount+"");
			// 累计费用
			if (null != costInfo.getCostMoney()) {
				charges += costInfo.getCostMoney().doubleValue();
			}
		}
		// 综合信息费,所属增值服务中其他费用
		if ("ZHXX".equals(chargeName)) {
			// 累计费用
			if (null != costInfo.getCostMoney()) {
				charges += costInfo.getCostMoney().doubleValue();
			}
		}
		//等通知放货费; 等通知放货业务已取消，不累积费用; 燃油附加费
		if ("RYFJ".equals(chargeName)) {
			beforeModel.setFuelSurcharge("Y");
			beforeModel.setFuelSurchargePrice(chargeAmount+"");
			// 累计费用
			if (null != costInfo.getCostMoney()) {
				charges += costInfo.getCostMoney().doubleValue();
			}
		}
		// 其他费用重新计算，减去返单费用、综合服务费
		if (null != wayBillDetail.getTotalCharge()) {
			BigDecimal bd=new BigDecimal(beforeModel.getTotalPrice());
			beforeModel.setOtherPrice(BigDecimal.valueOf(bd.doubleValue() - charges) +"");
		}
		return charges;
	}
	
	/**
	 *  返回两个数之间的随机整数X， minNunber ≤ X <maxNumber
	 * @param minNumber
	 * @param maxNumber
	 * @return
	 */
	public Integer randomRange(Integer minNumber,Integer maxNumber){
		if(minNumber != null && maxNumber != null){
			return (int)(minNumber + Math.random()*(maxNumber));
		}
		return null;
	}
	
	/**
	 * 返回
	 * @param date
	 * @param day
	 * @return
	 */
	public Date addMinute(Date date, int minute) {
		Calendar nowTime = Calendar.getInstance();
		nowTime.setTime(date);
		nowTime.add(Calendar.MINUTE, minute);
		return nowTime.getTime();
	}
	
	/**
	 * @date 2016年11月9日11:20:29
	 * @param payType
	 * @return
	 */
	public static String getPayType(String payType) {
		// 发货人支付包括：现金、银行卡、欠款、月结、返单付款（没有这种业务） 收货人支付包括：到付
		return FOSSTypeConvert.foss2dopPayType(payType);
	}
	/**
	 * @date 2016年11月9日11:20:29
	 * @param transType
	 * @return
	 */
	public static String getTransType(String transType) {
		/**
		 * @date 2016年11月9日11:20:29
		 * @param transType
		 * @return
		 */
		return FOSSTypeConvert.foss2dopTransType(transType);
	}
	/**
	 * @date 2016年11月9日11:20:29
	 * @param pickType
	 * @return
	 */
	public static String getPickGoodType(String pickType) {
		/**
		 * @date 2016年11月9日11:20:29
		 * @param pickType
		 * @return
		 */
		return FOSSTypeConvert.foss2dopPickupGoodsType(pickType);
	}
	/**
	 * @date 2016年11月9日11:20:29
	 * @param returnBillType
	 * @return
	 */
	public static String getReturnBillType(String returnBillType) {
		/**
		 * @date 2016年11月9日11:20:29
		 * @param returnBillType
		 * @return
		 */
		return FOSSTypeConvert.foss2dopBackSignType(returnBillType.trim());
	}
	
	/**
	 * @date 2016年11月9日11:20:29
	 * @param omsNotifyMessageDao
	 */
	public void setOmsNotifyMessageDao(IOmsNotifyMessageDao omsNotifyMessageDao) {
		/**
		 * @date 2016年11月9日11:20:29
		 * @param omsNotifyMessageDao
		 */
		this.omsNotifyMessageDao = omsNotifyMessageDao;
	}

	/**
	 * @date 2016年11月8日15:06:15
	 * @param orderStatusPushingClient
	 */
	public void setOrderStatusPushingClient(OrderStatusPushingClient orderStatusPushingClient) {
		/**
		 * @date 2016年11月8日15:06:15
		 * @param orderStatusPushingClient
		 */
		this.orderStatusPushingClient = orderStatusPushingClient;
	}

	/**
	 * <p>(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 
	 * @date 2016-11-26 上午9:08:48
	 * @return
	 * @see
	 */
	public void setTraceService(ITraceService traceService) {
		/**
		 * <p>(方法详细描述说明、方法参数的具体涵义)</p> 
		 * @author 
		 * @date 2016-11-26 上午9:08:48
		 * @return
		 * @see
		 */
		this.traceService = traceService;
	}
	/**
	 * <p>(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 
	 * @date 2016-11-26 上午9:08:48
	 * @return
	 * @see
	 */
	public void setOrdWaybillRelateClient(OrdWaybillRelateClient ordWaybillRelateClient) {
		/**
		 * <p>(方法详细描述说明、方法参数的具体涵义)</p> 
		 * @author 
		 * @date 2016-11-26 上午9:08:48
		 * @return
		 * @see
		 */
		this.ordWaybillRelateClient = ordWaybillRelateClient;
	}
	/**
	 * <p>(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 
	 * @date 2016-11-26 上午9:08:48
	 * @return
	 * @see
	 */
	public DepartmentDao getDepartmentDao() {
		/**
		 * <p>(方法详细描述说明、方法参数的具体涵义)</p> 
		 * @author 
		 * @date 2016-11-26 上午9:08:48
		 * @return
		 * @see
		 */
		return departmentDao;
	}
	/**
	 * <p>(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 
	 * @date 2016-11-26 上午9:08:48
	 * @return
	 * @see
	 */
	public void setDepartmentDao(DepartmentDao departmentDao) {
		/**
		 * <p>(方法详细描述说明、方法参数的具体涵义)</p> 
		 * @author 
		 * @date 2016-11-26 上午9:08:48
		 * @return
		 * @see
		 */
		this.departmentDao = departmentDao;
	}

	/**
	 * <p>(方法详细描述说明、方法参数的具体涵义)</p> 
	 * @author 
	 * @date 2016-11-26 上午9:08:48
	 * @return
	 * @see
	 */
	public IOmsNotifyMessageDao getOmsNotifyMessageDao() {
		/**
		 * <p>(方法详细描述说明、方法参数的具体涵义)</p> 
		 * @author 
		 * @date 2016-11-26 上午9:08:48
		 * @return
		 * @see
		 */
		return omsNotifyMessageDao;
	}

	/**
	 * @return the orderDispatchDao
	 */
	public IOrderDispatchDao getOrderDispatchDao() {
		return orderDispatchDao;
	}

	/**
	 * @param orderDispatchDao the orderDispatchDao to set
	 */
	public void setOrderDispatchDao(IOrderDispatchDao orderDispatchDao) {
		this.orderDispatchDao = orderDispatchDao;
	}

	/**
	 * @return the dictionaryService
	 */
	public IDictionaryService getDictionaryService() {
		return dictionaryService;
	}

	/**
	 * @param dictionaryService the dictionaryService to set
	 */
	public void setDictionaryService(IDictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
	
}