package cn.com.hrl;

import cn.com.hrl.database.mybatis.DynamicDataSourceRegister;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
// 初始化spring boot 容器
@SpringBootApplication
//启注解事务管理
@EnableTransactionManagement
// 自动装配
@EnableAutoConfiguration()
// 扫描mapper
@MapperScan("cn.com.hrl.mapper")
// 注册动态数据源
@Import({DynamicDataSourceRegister.class})
public class Application  extends SpringBootServletInitializer {

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
