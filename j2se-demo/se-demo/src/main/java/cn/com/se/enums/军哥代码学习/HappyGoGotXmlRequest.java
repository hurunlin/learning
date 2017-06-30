package cn.com.se.enums.军哥代码学习;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 快乐购生成XML给Dop推送状态的类 
 * 342290 陈军
 * 2017年6月29日16:38:56
 * @author Administrator
 */
@XmlRootElement(name="Program")
@XmlType(propOrder={"parameters", "delyinfo"})
public class HappyGoGotXmlRequest {
	private Parameters parameters;
	private Delyinfo delyinfo;
	
	@XmlType(propOrder={"wh_code"})
	static class Parameters{
		private String wh_code;
		public String getWh_code() {
			return wh_code;
		}
		public void setWh_code(String wh_code) {
			this.wh_code = wh_code;
		}
	}
	
	@XmlType(propOrder={"row1"})
	static class Delyinfo{
		private Row1 row1;
		
		public Row1 getRow1() {
			return row1;
		}

		public void setRow1(Row1 row1) {
			this.row1 = row1;
		}

		@XmlType(propOrder={"wb_no","order_no","receive_name","weight","comments","shipping_date",
				"tel","mobile","pieces","address","request_time","receive_remark"})
		static class Row1{
			private String wb_no;
			private String order_no;
			private String receive_name;
			private String weight;
			private String comments;
			private String shipping_date;
			private String tel;
			private String mobile;
			private String pieces;
			private String address;
			private String request_time;
			private String receive_remark;
			public String getWb_no() {
				return wb_no;
			}
			public void setWb_no(String wb_no) {
				this.wb_no = wb_no;
			}
			public String getOrder_no() {
				return order_no;
			}
			public void setOrder_no(String order_no) {
				this.order_no = order_no;
			}
			public String getReceive_name() {
				return receive_name;
			}
			public void setReceive_name(String receive_name) {
				this.receive_name = receive_name;
			}
			public String getWeight() {
				return weight;
			}
			public void setWeight(String weight) {
				this.weight = weight;
			}
			public String getComments() {
				return comments;
			}
			public void setComments(String comments) {
				this.comments = comments;
			}
			public String getShipping_date() {
				return shipping_date;
			}
			public void setShipping_date(String shipping_date) {
				this.shipping_date = shipping_date;
			}
			public String getTel() {
				return tel;
			}
			public void setTel(String tel) {
				this.tel = tel;
			}
			public String getMobile() {
				return mobile;
			}
			public void setMobile(String mobile) {
				this.mobile = mobile;
			}
			public String getPieces() {
				return pieces;
			}
			public void setPieces(String pieces) {
				this.pieces = pieces;
			}
			public String getAddress() {
				return address;
			}
			public void setAddress(String address) {
				this.address = address;
			}
			public String getRequest_time() {
				return request_time;
			}
			public void setRequest_time(String request_time) {
				this.request_time = request_time;
			}
			public String getReceive_remark() {
				return receive_remark;
			}
			public void setReceive_remark(String receive_remark) {
				this.receive_remark = receive_remark;
			}
		}
	}

	public Parameters getParameters() {
		return parameters;
	}
	
	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}
	
	public Delyinfo getDelyinfo() {
		return delyinfo;
	}
	
	public void setDelyinfo(Delyinfo delyinfo) {
		this.delyinfo = delyinfo;
	}
	
	public static void main(String []a) throws JAXBException, IOException{
		HappyGoGotXmlRequest request = new HappyGoGotXmlRequest();
		Parameters parameters = new Parameters();
		Delyinfo delyinfo = new Delyinfo();
		Delyinfo.Row1 row1 = new Delyinfo.Row1();
		row1.setAddress("address");
		row1.setComments("comments");
		row1.setMobile("2531318");
		row1.setOrder_no("LP11111111111");
		row1.setPieces("yioikm");
		row1.setReceive_name("陈军");
		row1.setReceive_remark("速度");
		row1.setRequest_time("2017年6月29日15:32:28");
		row1.setShipping_date("2017年6月29日");
		row1.setTel("010-1010110");
		row1.setWb_no("www");
		row1.setWeight("60吨");
		request.setDelyinfo(delyinfo);
		delyinfo.setRow1(row1);
		parameters.setWh_code("WHCODE");
		request.setParameters(parameters);
		
		StringWriter sw = new StringWriter();
		JAXBContext context = JAXBContext.newInstance(HappyGoGotXmlRequest.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal(request, sw);
		String requestStr = sw.toString();
		System.out.println(requestStr);
		sw.close();
	}
}
