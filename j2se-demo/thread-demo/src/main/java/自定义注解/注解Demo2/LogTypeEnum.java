package 自定义注解.注解Demo2;
/**
 * 日志操作类型枚举
 * @author azy
 *
 */
public enum LogTypeEnum {
	INFO_ADD(1,"新增消息"),
	INFO_SEND(2,"推送消息"),
	INFO_DEL(3,"删除消息");
	
	private int id;
	private String name;
	
	private LogTypeEnum(int id,String name) {
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
