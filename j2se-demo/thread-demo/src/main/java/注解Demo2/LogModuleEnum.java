package 注解Demo2;
/**
 * 日志操作模块枚举
 * @author azy
 *
 */
public enum LogModuleEnum {
	INFO(1,"消息模块");
	
	private int id;
	private String name;
	
	private LogModuleEnum(int id,String name) {
		this.id=id;
		this.name=name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
