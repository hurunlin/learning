package cn.com.se.enums.军哥代码学习;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.oms.module.client.happygo.domain.HappyGoRequest;
import com.deppon.oms.module.client.happygo.domain.HappyGoResponse;
import com.deppon.oms.module.constant.common.NumberConst;
import com.deppon.oms.module.log.server.service.INoticeLogService;
import com.deppon.oms.module.util.HttpClientUtil;

/**
 * 零担欢乐购订单推送给DOP的请求Client
 * @author Administrator
 * 342290 陈军
 * 2017年6月29日16:51:48
 */
public class HappyGoPushToDopClient {
	/**
	 * client 客户端
	 */
	private HttpClientUtil client;
	/**
	 * 日志
	 */
	private static Log LOG = LogFactory.getLog(HappyGoPushToDopClient.class);
	
	/**
	 * kibanna日志service
	 */
	private INoticeLogService noticeLogService;
	
	/**
	 * 推送给DOP的URL
	 */
	private String employeePushToDopUrl;
	
	/**
	 * set方法，给spring依赖注入
	 */
	public void setNoticeLogService(INoticeLogService noticeLogService) {
		this.noticeLogService = noticeLogService;
	}
	
	public String getEmployeePushToDopUrl() {
		return employeePushToDopUrl;
	}

	public void setEmployeePushToDopUrl(String employeePushToDopUrl) {
		this.employeePushToDopUrl = employeePushToDopUrl;
	}
	
	public HttpClientUtil getClient() {
		return client;
	}

	public void setClient(HttpClientUtil client) {
		this.client = client;
	}

	public HappyGoResponse pushToDop(HappyGoRequest request) throws Exception{
		LOG.debug("零担快乐购状态推送开始...");
		long startTime = System.currentTimeMillis();
		HappyGoResponse response = new HappyGoResponse();
		//记得释放资源
		StringReader stringReader = null;
		try{
			client = new HttpClientUtil(employeePushToDopUrl);
			//超时时间设置5K毫秒
			String responseStr = client.sendStringUTF(request, NumberConst.NUMBER_INT_VALUE5000);
			if(StringUtils.isBlank(responseStr)){
				if(responseStr == null){
					LOG.error("零担快乐购状态推送接收到DOP响应报文为空!");
				}else{
					LOG.error("零担快乐购状态推送接收到DOP响应报文为空白字符串!");
				}
			}else{
				//反解析XML
				stringReader = new StringReader(responseStr);
				JAXBContext parseContext = JAXBContext.newInstance(HappyGoResponse.class);
				Unmarshaller unmarshaller = parseContext.createUnmarshaller();
				response = (HappyGoResponse) unmarshaller.unmarshal(stringReader);
			}
			LOG.debug(new StringBuilder("零担快乐购状态推送结束,耗时【").append(System.currentTimeMillis() - startTime).append("】毫秒").toString());
		}catch(Exception e){
			LOG.error(ExceptionUtils.getFullStackTrace(e));
		}finally{
			if(stringReader != null){
				stringReader.close();
			}
		}
		return response;
	}
	
	
}
