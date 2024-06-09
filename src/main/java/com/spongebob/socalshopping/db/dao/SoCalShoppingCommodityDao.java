package com.spongebob.socalshopping.db.dao;

import com.spongebob.socalshopping.db.po.SoCalShoppingCommodity;

import java.util.List;

public interface SoCalShoppingCommodityDao {

    //卖家可以创建/修改商品
    int insertCommodity(SoCalShoppingCommodity commodity);

    int updateCommodity(SoCalShoppingCommodity commodity);

    // 卖家/买家可以看到指定商家上架的商品列表

    // 卖家/买家可以看到指定商品名称，搜索商品列表
    List<SoCalShoppingCommodity> listCommoditiesByUserId(long userId);

    // 卖家/买家可以看到所有的商品列表
    List<SoCalShoppingCommodity> listCommodities();

    // 卖家/买家可以查看商品详情
    SoCalShoppingCommodity getCommodityDetails(long commodityId);

    int deductStock(long commodityId);

    int deductStockSP(long commodityId);
}
