package com.spongebob.socalshopping.db.dao;

import com.spongebob.socalshopping.db.po.SoCalShoppingCommodity;
import com.spongebob.socalshopping.db.po.SoCalShoppingUser;

import java.util.List;

public interface SoCalShoppingUserDao {

    //卖家可以创建/修改商品
    int insertUser(SoCalShoppingUser user);

}
