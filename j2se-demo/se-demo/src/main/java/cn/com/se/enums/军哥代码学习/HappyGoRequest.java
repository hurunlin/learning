package cn.com.se.enums.军哥代码学习;

/**
 * 零担快乐购状态推送请求类(OMS->DOP)
 * @author Administrator
 * @date 2017年6月29日11:53:54
 */
public class HappyGoRequest {
	private String xmlParam; // xml报文
	
	public enum InterfaceType {
		GOT, // 开单
		SIGN; // 签收
	}
	
	private InterfaceType interfaceType; // 接口类型
	private String orderNumber; // 订单号
	private String channelNumber; // 渠道单号
	private String waybillNumber; // 运单号

	
	public String getXmlParam() {
		return xmlParam;
	}

	public void setXmlParam(String xmlParam) {
		this.xmlParam = xmlParam;
	}

	public InterfaceType getInterfaceType() {
		return interfaceType;
	}

	public void setInterfaceType(InterfaceType interfaceType) {
		this.interfaceType = interfaceType;
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
	
	public static void main(String[] args) {
		HappyGoRequest goRequest = new HappyGoRequest();
		goRequest.setXmlParam("x,ml");
		goRequest.setInterfaceType(InterfaceType.GOT);
	}
}
