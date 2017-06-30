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
@XmlRootElement(name = "Program")
@XmlType(propOrder = { "parameters", "delyinfo" })
public class HappyGoSignXmlRequest {
	private Parameters parameters;
	private Delyinfo delyinfo;
	
	@XmlType(propOrder = { "wh_code" })
	static class Parameters {
		private String wh_code;

		public String getWh_code() {
			return wh_code;
		}

		public void setWh_code(String wh_code) {
			this.wh_code = wh_code;
		}
	}

	@XmlType(propOrder = { "row1" })
	static class Delyinfo {
		private Row1 row1;

		public Row1 getRow1() {
			return row1;
		}

		public void setRow1(Row1 row1) {
			this.row1 = row1;
		}

		@XmlType(propOrder = { "wb_no", "order_no", "dely_date", "send_yn", "send_gb", "receive_name", "reason_code", "pieces", "weight", "order_exception", "comments" })
		static class Row1 {
			private String wb_no;
			private String order_no;
			private String dely_date;
			private String send_yn;
			private String send_gb;
			private String receive_name;
			private String reason_code;
			private String pieces;
			private String weight;
			private String order_exception;
			private String comments;

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

			public String getDely_date() {
				return dely_date;
			}

			public void setDely_date(String dely_date) {
				this.dely_date = dely_date;
			}

			public String getSend_yn() {
				return send_yn;
			}

			public void setSend_yn(String send_yn) {
				this.send_yn = send_yn;
			}

			public String getSend_gb() {
				return send_gb;
			}

			public void setSend_gb(String send_gb) {
				this.send_gb = send_gb;
			}

			public String getReceive_name() {
				return receive_name;
			}

			public void setReceive_name(String receive_name) {
				this.receive_name = receive_name;
			}

			public String getReason_code() {
				return reason_code;
			}

			public void setReason_code(String reason_code) {
				this.reason_code = reason_code;
			}

			public String getPieces() {
				return pieces;
			}

			public void setPieces(String pieces) {
				this.pieces = pieces;
			}

			public String getWeight() {
				return weight;
			}

			public void setWeight(String weight) {
				this.weight = weight;
			}

			public String getOrder_exception() {
				return order_exception;
			}

			public void setOrder_exception(String order_exception) {
				this.order_exception = order_exception;
			}

			public String getComments() {
				return comments;
			}

			public void setComments(String comments) {
				this.comments = comments;
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

	public static void main(String[] args) throws JAXBException, IOException {
		HappyGoSignXmlRequest happyGoSignXmlRequest = new HappyGoSignXmlRequest();
		Delyinfo delyinfo = new Delyinfo();
		Parameters parameters = new Parameters();
		
		happyGoSignXmlRequest.setParameters(parameters);
		happyGoSignXmlRequest.setDelyinfo(delyinfo);
		parameters.setWh_code("WHCODE");
		
		Delyinfo.Row1 row1 = new Delyinfo.Row1();
		delyinfo.setRow1(row1);
		
		row1.setComments("撒大大");
		row1.setDely_date("2017年6月29");
		row1.setOrder_exception("IO异常");
		row1.setOrder_no("ytuy555616");
		row1.setPieces("sjnm");
		row1.setReason_code("未知错误");
		row1.setReceive_name("陈庆");
		row1.setSend_gb("20GB");
		row1.setSend_yn("60gb");
		row1.setWb_no("WWW");
		row1.setWeight("60T");
		
		StringWriter sw = new StringWriter();
		JAXBContext context = JAXBContext.newInstance(HappyGoSignXmlRequest.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.marshal(happyGoSignXmlRequest, sw);
		String requestStr = sw.toString();
		System.out.println(requestStr);
		sw.close();
	}
}


