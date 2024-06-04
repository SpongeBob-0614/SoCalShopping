package com.spongebob.socalshopping.db.dao.impl;

import com.spongebob.socalshopping.db.dao.SoCalShoppingUserDao;
import com.spongebob.socalshopping.db.mappers.SoCalShoppingUserMapper;
import com.spongebob.socalshopping.db.po.SoCalShoppingUser;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class SoCalShoppingUserDaoImpl implements SoCalShoppingUserDao {

    @Resource
    SoCalShoppingUserMapper mapper;
    @Override
    public int insertUser(SoCalShoppingUser user) {
        return mapper.insert(user);
    }
}
