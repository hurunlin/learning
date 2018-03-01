/**
 * Project Name: learning
 * File Name: DataSourceConfiguration.java
 * Package Name: cn.com.hrl.source
 * Date: 2017-06-09 13:59
 * Copyright (c) 2016, 杉德巍康企业服务有限公司.
 *
 * @author hu.rl
 */
package cn.com.hrl.core.database.druid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * ClassName: DataSourceConfiguration <br>
 * Function: 创建数据源<br>
 * Date:  2017-06-09 13:59 <br>
 */

//@Configuration
// 通知spring开启事务
//@EnableTransactionManagement
public class DataSourceConfiguration {
    @Value("${druid.type}")
    private Class<? extends DataSource> dataSourceType;

    @Bean(name = "masterDataSource")
    @Primary
    @ConfigurationProperties(prefix = "druid.master")
    public DataSource masterDataSource(){
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean(name = "slaveDataSource")
    @ConfigurationProperties(prefix = "druid.slave")
    public DataSource slaveDataSource1(){
        return DataSourceBuilder.create().type(dataSourceType).build();
    }
}
