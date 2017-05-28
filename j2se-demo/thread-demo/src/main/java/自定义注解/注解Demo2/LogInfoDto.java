package 自定义注解.注解Demo2;

import java.io.Serializable;
/**
 * 记录日志操作内容
 * @author azy
 *
 */
public class LogInfoDto implements Serializable{
	private static final long serialVersionUID = -5969219542243908615L;
	private String content;

	public LogInfoDto(String content){
		this.content=content;
	}
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
