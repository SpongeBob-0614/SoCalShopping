package com.spongebob.socalshopping.db.mappers;

import com.spongebob.socalshopping.db.po.SoCalShoppingOrder;
import com.spongebob.socalshopping.db.po.SoCalShoppingShardingOrder;

public interface SoCalShoppingShardingOrderMapper {

    int insetOrder(SoCalShoppingShardingOrder record);

    SoCalShoppingOrder queryOrder(Long orderId, Long userId);
}
