package com.yc.configs;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.DriverManager;

@Configuration
@PropertySource("classpath:db.properties")
@Data
public class DataSourceConfig {
    @Value("root")
    private String username;
    @Value("666666liao")
    private String password;
    @Value("jdbc:mysql://localhost:3306/bank?serverTimezone=UTC")
    private String url;
    @Value("com.mysql.cj.jdbc.Driver")
    private String driverclass;
    @Value("#{T(Runtime).getRuntime().availableProcessors()*2}")
    private int cpuCount;
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource=new DriverManagerDataSource();
        dataSource.setDriverClassName(driverclass);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;


    }
    //另外要注意的idea会对这个返回值方法进行解析,判断是否有init
    @Bean(initMethod = "init",destroyMethod = "close")//DruidDataSource提供了init初始化方法
    public DruidDataSource druidDataSource(){
        DruidDataSource dds=new DruidDataSource();
        dds.setUrl(url);
        dds.setUsername(username);
        dds.setPassword(password);
        dds.setDriverClassName(driverclass);
        //以上只是配置参数,并没有创建连接池，在这个init()完成了连接的创建
        //当前主机数的cpu数*2
        dds.setInitialSize(cpuCount);
        dds.setMaxActive(cpuCount*2);
        return dds;


    }
    @Bean
    public DataSource dbcpDataSource(){
        BasicDataSource dataSource=new BasicDataSource();
        dataSource.setDriverClassName(driverclass);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return  dataSource;

    }


}
