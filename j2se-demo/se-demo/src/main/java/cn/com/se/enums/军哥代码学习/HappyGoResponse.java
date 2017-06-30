package cn.com.se.enums.军哥代码学习;

/**
 * 零担快乐购状态推送响应类
 * 
 * @date 2017年6月29日11:53:54
 * @author Administrator
 */
public class HappyGoResponse {
	private Boolean result;
	private String errInfo = null;
	private String orderNumber; // 订单号
	private String channelNumber; // 渠道单号
	private String waybillNumber; // 运单号

	public Boolean getResult() {
		return result;
	}

	public void setResult(Boolean result) {
		this.result = result;
	}

	public String getErrInfo() {
		return errInfo;
	}

	public void setErrInfo(String errInfo) {
		this.errInfo = errInfo;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getChannelNumber() {
		return channelNumber;
	}

	public void setChannelNumber(String channelNumber) {
		this.channelNumber = channelNumber;
	}

	public String getWaybillNumber() {
		return waybillNumber;
	}

	public void setWaybillNumber(String waybillNumber) {
		this.waybillNumber = waybillNumber;
	}

}
