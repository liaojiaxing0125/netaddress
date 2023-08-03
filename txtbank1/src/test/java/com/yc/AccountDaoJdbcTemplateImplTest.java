package com.yc;

import com.yc.configs.DataSourceConfig;
import com.yc.dao.AccountDao;
import com.yc.entry.Account;
import junit.framework.TestCase;
import lombok.extern.log4j.Log4j2;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.crypto.Data;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class, DataSourceConfig.class})
@Log4j2
public class AccountDaoJdbcTemplateImplTest extends TestCase {
    @Autowired
    private AccountDao accountDao;


    @Test
    public void insert(){
        accountDao.insert(300);


    }
    @Test
    public void findCount(){
        int total=accountDao.findCount();
        Assert.assertEquals(2,total);

    }
   @Test
    public void findAll(){
        List<Account> all = accountDao.findAll();
        log.info(all);

    }

    @Test
    public void findById(){
        Account byId = accountDao.findById(2);
        log.info(byId);

    }


}
