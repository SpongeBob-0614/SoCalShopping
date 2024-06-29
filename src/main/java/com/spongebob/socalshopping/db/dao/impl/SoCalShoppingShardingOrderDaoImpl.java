package com.spongebob.socalshopping.db.dao.impl;

import com.spongebob.socalshopping.db.dao.SoCalShoppingShardingOrderDao;
import com.spongebob.socalshopping.db.mappers.SoCalShoppingShardingOrderMapper;
import com.spongebob.socalshopping.db.po.SoCalShoppingShardingOrder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class SoCalShoppingShardingOrderDaoImpl implements SoCalShoppingShardingOrderDao {

    @Resource
    SoCalShoppingShardingOrderMapper orderMapper;

    @Override
    public int insertOrder(SoCalShoppingShardingOrder order) {
        return orderMapper.insert(order);
    }

    @Override
    public SoCalShoppingShardingOrder queryShardingOrder(Long orderId, Long userId) {
        return orderMapper.queryOrder(orderId,userId);
    }
}
