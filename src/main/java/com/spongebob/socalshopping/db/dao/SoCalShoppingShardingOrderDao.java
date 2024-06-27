package com.spongebob.socalshopping.db.dao;

import com.spongebob.socalshopping.db.po.SoCalShoppingShardingOrder;

public interface SoCalShoppingShardingOrderDao {
    int insertOrder(SoCalShoppingShardingOrder order);
    SoCalShoppingShardingOrder queryShardingOrder(Long orderId, Long userId);
}
