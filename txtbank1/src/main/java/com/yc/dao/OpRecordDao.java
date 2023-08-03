package com.yc.dao;

import com.yc.entry.OpRecord;

import javax.swing.*;
import java.util.List;

public interface OpRecordDao {
    public void insertOpRecord(OpRecord opRecord);


//    根据id查询账户
    public List<OpRecord> findOpRecord(int accountid);
    /*


    查询accountid账户根据optype查
     */

    public List<OpRecord> findOpRecord(int accountid,String opType);

    public List<OpRecord> findOpRecord( OpRecord opRecord);

}
