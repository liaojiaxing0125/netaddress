package com.yc.dao;

import com.yc.entry.Account;

import java.util.List;

public interface AccountDao {

    //添加账号 如何取出auto_increment自动生成的id
    public int insert(double money);
    //根据账号将money更新
    public void update(int account,double money);
    public void delete(int accountid);
    public int findCount();
    public List<Account> findAll();
    public Account findById(int accountid);
}
