package com.it.Springyuan.yc;

import com.it.Springyuan.annotation.YcResource;
import com.it.Springyuan.annotation.YcService;

@YcService
public class UserBizImpl  implements  UserBiz {
    @YcResource(name = "userDaoImpl")
    private UserDao userDao;
    @Override
    public void add(String uname) {

    }
}
