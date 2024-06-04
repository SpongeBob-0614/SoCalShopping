package com.spongebob.socalshopping.db.dao;

import com.spongebob.socalshopping.db.po.SoCalShoppingOrder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;

@SpringBootTest
@Slf4j
class SoCalShoppingOrderDaoTest {

    @Resource
    SoCalShoppingOrderDao dao;

    @Test
    void insertOrder() {
        dao.deleteOrder(123L);
        SoCalShoppingOrder order = SoCalShoppingOrder.builder()
                .orderStatus(0)
                .orderNo("123")
                .orderId(123L)
                .orderAmount(123L)
                .commodityId(123L)
                .createTime(new Date())
                .payTime(new Date())
                .userId(123L)
                .orderStatus(0)
                .build();
        dao.insertOrder(order);
    }

    @Test
    void queryOrderById() {
        SoCalShoppingOrder order = dao.queryOrderById(123L);
        log.info(order.toString());
    }

    @Test
    void queryOrderByOrderNo() {
        SoCalShoppingOrder order = dao.queryOrderByOrderNo("123");
        log.info(order.toString());
    }
}