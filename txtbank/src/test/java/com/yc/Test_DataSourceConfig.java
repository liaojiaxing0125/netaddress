package com.yc;

import com.yc.configs.DataSourceConfig;
import junit.framework.TestCase;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringJUnit4ClassRunner.class)//表示在springtest器中运行
@ContextConfiguration(classes = {Config.class, DataSourceConfig.class})
@Log4j2
public class Test_DataSourceConfig extends TestCase {

    @Autowired
    private DataSourceConfig dsc;

    @Autowired
    private Environment env;


    @Autowired
    @Qualifier("dbcpDataSource")
    private DataSource dbcpDataSource;


    @Autowired
    @Qualifier("druidDataSource")
    private DataSource druidDataSource;


    @Test
    public void testPropertySource(){


    }
    @Test
    public void testDruidDataSource() throws SQLException {
        Assert.assertNotNull(druidDataSource.getConnection());
        Connection con=druidDataSource.getConnection();
        System.out.println(con);


    }
    public void testDbcpDataSource() throws SQLException {
        Assert.assertNotNull(dbcpDataSource.getConnection());
        Connection con=dbcpDataSource.getConnection();


    }



}
