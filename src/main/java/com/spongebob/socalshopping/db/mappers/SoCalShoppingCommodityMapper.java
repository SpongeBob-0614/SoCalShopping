package com.spongebob.socalshopping.db.mappers;

import com.spongebob.socalshopping.db.po.SoCalShoppingCommodity;

public interface SoCalShoppingCommodityMapper {
    int deleteByPrimaryKey(Long commodityId);

    int insert(SoCalShoppingCommodity record);

    int insertSelective(SoCalShoppingCommodity record);

    SoCalShoppingCommodity selectByPrimaryKey(Long commodityId);

    int updateByPrimaryKeySelective(SoCalShoppingCommodity record);

    int updateByPrimaryKey(SoCalShoppingCommodity record);
}