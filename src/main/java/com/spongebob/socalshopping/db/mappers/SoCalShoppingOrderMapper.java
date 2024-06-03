package com.spongebob.socalshopping.db.mappers;

import com.spongebob.socalshopping.db.po.SoCalShoppingOrder;

public interface SoCalShoppingOrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(SoCalShoppingOrder record);

    int insertSelective(SoCalShoppingOrder record);

    SoCalShoppingOrder selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(SoCalShoppingOrder record);

    int updateByPrimaryKey(SoCalShoppingOrder record);
}