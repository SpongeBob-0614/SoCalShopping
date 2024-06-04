package com.spongebob.socalshopping.db.dao.impl;

import com.spongebob.socalshopping.db.dao.SoCalShoppingOrderDao;
import com.spongebob.socalshopping.db.mappers.SoCalShoppingOrderMapper;
import com.spongebob.socalshopping.db.po.SoCalShoppingOrder;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository
public class SoCalShoppingOrderDaoImpl implements SoCalShoppingOrderDao {

    @Resource
    SoCalShoppingOrderMapper mapper;

    @Override
    public int insertOrder(SoCalShoppingOrder order) {
        return mapper.insert(order);
    }

    @Override
    public SoCalShoppingOrder queryOrderById(long orderId) {
        return mapper.selectByPrimaryKey(orderId);
    }

    @Override
    public SoCalShoppingOrder queryOrderByOrderNo(String orderNo) {

        return mapper.selectByOrderNo(orderNo);
    }

    @Override
    public int updateOrder(SoCalShoppingOrder order) {
        return mapper.updateByPrimaryKey(order);
    }

    @Override
    public int deleteOrder(long orderId) {
        return mapper.deleteByPrimaryKey(orderId);
    }


}
