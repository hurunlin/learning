package cn.com.se.enums.军哥代码学习;

import java.io.IOException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.deppon.dpap.framework.shared.util.json.JsonUtils;
import com.deppon.esb.pojo.util.JsonMapperUtil;
import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.oms.module.client.cnjzTraceSubscribe.domain.CnjzTraceSubscribeRequest;
import com.deppon.oms.module.client.cnjzTraceSubscribe.domain.CnjzTraceSubscribeResponse;
import com.deppon.oms.module.constant.interfacelog.InterfaceLogConst;
import com.deppon.oms.module.log.server.service.INoticeLogService;
import com.deppon.oms.module.log.shared.domain.NoticeLogUtilEntity;
import com.deppon.oms.module.util.HttpClientUtil;

/**
 * 菜鸟大件仓配OMS->WQS 轨迹订阅请求类
 * @see 2017年3月9日17:06:30
 * @author 342290
 *
 */
public class CnjzTraceSubscribeToWqsClient {

	/** The log. */
	private static Log LOG = LogFactory.getLog(CnjzTraceSubscribeToWqsClient.class);
	/**
	 * client 客户端
	 */
	private HttpClientUtil client;
	/**
	 * 日志
	 */
	private INoticeLogService noticeLogService;
	/**
	 * 请求地址
	 */
	private String url;
	
	public CnjzTraceSubscribeResponse cnjzSubscribe(CnjzTraceSubscribeRequest request) {
		//JSON格式请求报文
		String requestJson = "";
		//错误信息
		StringBuilder errorMsg = new StringBuilder();
		client = new HttpClientUtil(url);
		// 日志
		LOG.info("菜鸟大件仓配（CNJZ）轨迹订阅请求开始...请求地址："+StringUtil.defaultIfNull(url)+"\n");
		CnjzTraceSubscribeResponse response = null;
		/**
		 * request封装完毕，接下来准备开始推送...
		 */
		try{
			requestJson = JsonMapperUtil.writeValue(request);
			LOG.info("完整请求报文"+requestJson);
			response = client.send(request, CnjzTraceSubscribeResponse.class);
		}catch(Exception e){
			LOG.error("菜鸟大件仓配轨迹订阅推送接口出现异常,异常原因是:\n"+ExceptionUtils.getFullStackTrace(e));
			errorMsg.append(ExceptionUtils.getFullStackTrace(e));
		}finally{
			// 创建日志工具实体
			NoticeLogUtilEntity nlue = new NoticeLogUtilEntity();
			// 设置接口名
			nlue.setInterfaceName("菜鸟大件仓配轨迹订阅接口");
			// 设置接口类型1同步接口，2异步接口双向，3异步接口单向
			nlue.setInterfaceType(InterfaceLogConst.INTERFACE_TYPE_SYN);
			// 设置日志内容
			nlue.setLogText(errorMsg.toString());
			// 判断request的为空性
			if (null != request) {
				// 记录请求的json数据
				try {
					// 将请求信息转成json对象
					requestJson = JsonUtils.toJsonString(request);
					// 设置请求报文
					nlue.setRequestNotice(requestJson);
				}catch(Exception e){
					LOG.debug("请求对象转换成json失败，失败原因是:\n"+ExceptionUtils.getFullStackTrace(e));
					errorMsg.append("请求对象转换成json失败，失败原因是:\n"+ExceptionUtils.getFullStackTrace(e));
				}
			}
			String responseStr = "";
			try {
				responseStr = JsonMapperUtil.writeValue(response);
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			nlue.setResponseNotice(responseStr);
			// 设置服务编码
			nlue.setServiceCode("OMS_WQS_TRACESUBSCRIBE");
			// 设置数据来源系统
			nlue.setSourcesys(InterfaceLogConst.SYS_OMS);
			// 设置服务类型（1服务端2客户端）
			nlue.setServiceType(InterfaceLogConst.SERVICE_TYPE_CLIENT);
			// 调用公共日志存储service进行日志存储
			try {
				noticeLogService.save(nlue);
			} catch (Exception e) {
				LOG.error("菜鸟大件仓配轨迹订阅同步接口(OMS->WQS)记录日志异常", e);
			}
		}
		LOG.info("菜鸟大件仓配轨迹订阅同步接口(OMS->WQS)推送结束...");
		return response;
	}

	public HttpClientUtil getClient() {
		return client;
	}

	public void setClient(HttpClientUtil client) {
		this.client = client;
	}

	public INoticeLogService getNoticeLogService() {
		return noticeLogService;
	}

	public void setNoticeLogService(INoticeLogService noticeLogService) {
		this.noticeLogService = noticeLogService;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
