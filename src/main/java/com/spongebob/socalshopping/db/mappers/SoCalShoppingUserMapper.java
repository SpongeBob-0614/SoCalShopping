package com.spongebob.socalshopping.db.mappers;

import com.spongebob.socalshopping.db.po.SoCalShoppingUser;

public interface SoCalShoppingUserMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(SoCalShoppingUser record);

    int insertSelective(SoCalShoppingUser record);

    SoCalShoppingUser selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(SoCalShoppingUser record);

    int updateByPrimaryKey(SoCalShoppingUser record);
}