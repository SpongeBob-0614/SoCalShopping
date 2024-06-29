package com.spongebob.socalshopping.db.dao;

import com.spongebob.socalshopping.db.po.SoCalShoppingOrder;
import com.spongebob.socalshopping.db.po.SoCalShoppingShardingOrder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SoCalShoppingShardingOrderDaoTest {

    @Resource
    SoCalShoppingShardingOrderDao orderDao;

    @Test
    void insertOrder() {
        for (int i = 0; i < 1; i++) {
            long orderId = i + 500L;
            SoCalShoppingShardingOrder order =SoCalShoppingShardingOrder.builder()
                            .orderStatus(0)
                            .orderNo("123")
                            .orderId(orderId)
                            .orderAmount(123L)
                            .commodityId(123L)
                            .createTime(new Date())
                            .payTime(new Date())
                            .userId(124L)
                            .orderStatus(0)
                            .build();
            orderDao.insertOrder(order);
        }
    }

    @Test
    void queryShardingOrder() {
    }
}