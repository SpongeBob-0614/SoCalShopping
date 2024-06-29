package com.spongebob.socalshopping.db.mappers;

import com.spongebob.socalshopping.db.po.SoCalShoppingOrder;
import com.spongebob.socalshopping.db.po.SoCalShoppingShardingOrder;

public interface SoCalShoppingShardingOrderMapper {

    int insert(SoCalShoppingShardingOrder record);

    SoCalShoppingShardingOrder queryOrder(Long orderId, Long userId);

    int insertSharding(SoCalShoppingShardingOrder order);
}
