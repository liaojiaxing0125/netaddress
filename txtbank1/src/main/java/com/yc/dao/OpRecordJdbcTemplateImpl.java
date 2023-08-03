package com.yc.dao;

import com.yc.configs.DataSourceConfig;
import com.yc.entry.OpRecord;
import com.yc.entry.OpType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.util.List;
@Repository
public class OpRecordJdbcTemplateImpl  implements OpRecordDao {
    private JdbcTemplate jdbcTemplate;
    public void init(DataSource dataSource){
        this.jdbcTemplate=new JdbcTemplate(dataSource);

    }
    @Override
    public void insertOpRecord(OpRecord opRecord) {
        String sql="insert into oprecord(accountid,opmonoey,optime,optype,transferid) values(?,?,now(),?,?)";
        this.jdbcTemplate.update(sql,opRecord.getAccountid(),opRecord.getOpmoney(),opRecord.getOptype().getKey(),opRecord.getTransferid());


    }

    @Override
    public List<OpRecord> findOpRecord(int accountid) {
        List<OpRecord> list=this.jdbcTemplate.query("select * from oprecord where accountid=? order by optime desc",(rs, rowNum) ->{
            OpRecord opRecord=new OpRecord();
            opRecord.setId(rs.getInt(1));
            opRecord.setAccountid(rs.getInt(2));
            opRecord.setOpmoney(rs.getDouble(3));
            opRecord.setOptime(rs.getString(4));
            String optype=rs.getString(5);
            if (optype.equalsIgnoreCase("withdraw")){
                opRecord.setOptype(OpType.WITHDRWA);


            }else if (optype.equalsIgnoreCase("deposite")){
                opRecord.setOptype(OpType.DEPOSITE);

            }else {
                opRecord.setOptype(OpType.TREABSFER);

            }
            opRecord.setTransferid(rs.getInt(6));
            return  opRecord;

        } ,accountid);
        return list;
    }

    @Override
    public List<OpRecord> findOpRecord(int accountid, String opType) {
        List<OpRecord> list=this.jdbcTemplate.query("select * from oprecord where accountid=? and opType=? order by optime desc",(rs, rowNum) ->{
            OpRecord opRecord=new OpRecord();
            opRecord.setId(rs.getInt(1));
            opRecord.setAccountid(rs.getInt(2));
            opRecord.setOpmoney(rs.getDouble(3));
            opRecord.setOptime(rs.getString(4));
            String optype=rs.getString(5);
            if (optype.equalsIgnoreCase("withdraw")){
                opRecord.setOptype(OpType.WITHDRWA);


            }else if (optype.equalsIgnoreCase("deposite")){
                opRecord.setOptype(OpType.DEPOSITE);

            }else {
                opRecord.setOptype(OpType.TREABSFER);

            }
            opRecord.setTransferid(rs.getInt(6));
            return  opRecord;

        } ,accountid,opType);
        return  list;
    }

    @Override
    public List<OpRecord> findOpRecord(OpRecord opRecord) {
        return null;
    }
}
