package com.yc.dao;

import com.google.gson.internal.$Gson$Preconditions;
import com.yc.Config;
import com.yc.configs.DataSourceConfig;
import com.yc.entry.OpRecord;
import com.yc.entry.OpType;
import junit.framework.TestCase;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {Config.class, DataSourceConfig.class})
@Log4j2
public class OpRecordJdbcTemplateImplTest extends TestCase {
    @Autowired
    private OpRecordDao opRecordDao;

    @Test
    public void testInsertOpRecord() {
        OpRecord opRecord=new OpRecord();
        opRecord.setAccountid(6);
        opRecord.setOpmoney(5);
        opRecord.setOptype(OpType.DEPOSITE);
        this.opRecordDao.insertOpRecord(opRecord);
    }
    @Test
    public void testFindOpRecord() {
        List<OpRecord> list=opRecordDao.findOpRecord(19);
        System.out.println(list);
    }
    @Test
    public void testTestFindOpRecord() {
        List<OpRecord> list=opRecordDao.findOpRecord(19,"DEPOSITE");
    }
    @Test
    public void testTestFindOpRecord1() {
    }
}