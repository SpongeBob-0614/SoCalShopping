package com.spongebob.socalshopping.db.dao;

import com.spongebob.socalshopping.db.po.SoCalShoppingOrder;

public interface SoCalShoppingOrderDao {

    //买家可以下单
    int insertOrder(SoCalShoppingOrder order);

    //o 买家可以查看自己的订单
    SoCalShoppingOrder queryOrderById(long orderId);

    SoCalShoppingOrder queryOrderByOrderNo(String orderNo);

    //o 买家可以对订单进行付款
    int updateOrder(SoCalShoppingOrder order);

    //o 买家可以按商品名称或者描述进行搜索

    //o 十分钟以内不付款会 cancel order
    //mq

}
