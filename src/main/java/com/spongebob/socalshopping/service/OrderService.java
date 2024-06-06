package com.spongebob.socalshopping.service;

import com.spongebob.socalshopping.db.dao.SoCalShoppingCommodityDao;
import com.spongebob.socalshopping.db.dao.SoCalShoppingOrderDao;
import com.spongebob.socalshopping.db.po.SoCalShoppingCommodity;
import com.spongebob.socalshopping.db.po.SoCalShoppingOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class OrderService {
    @Resource
    SoCalShoppingOrderDao orderDao;

    @Resource
    SoCalShoppingCommodityDao commodityDao;

    public SoCalShoppingOrder processOrder(long commodityId, long userId){
        SoCalShoppingCommodity commodity = commodityDao.getCommodityDetails(commodityId);
        //check
        int availableStock = commodity.getAvailableStock();
        if(availableStock > 0){
            availableStock--;
            log.info("Process succesful for commodityId: " + commodityId+", current available stock: "+ availableStock);
            SoCalShoppingOrder order = SoCalShoppingOrder.builder()
                    .userId(Long.valueOf(userId))
                    .commodityId(Long.valueOf(commodityId))
                    .orderNo(UUID.randomUUID().toString())
                    .orderAmount(commodity.getPrice().longValue())
                    .createTime(new Date())
                    .orderStatus(1)
                    //create order
                    //0, invalid ocrder, since no available stock
                    //1, already create order, pending for payment
                    //2, finishing payment
                    //99, invalid after due to payment process overtime
                    .build();
            orderDao.insertOrder(order);
            //更新库存
            commodity.setAvailableStock(availableStock);
            commodityDao.updateCommodity(commodity);
            return order;
        }
        else{
            log.info("Process order failed, no available stock, commodityId: " + commodityId);
            return null;
        }

    }

    public SoCalShoppingOrder getOrderByOrderNo(String orderNum){
        return orderDao.queryOrderByOrderNo(orderNum);
    }

    public int payOrder(String orderNum){
        SoCalShoppingOrder order = getOrderByOrderNo(orderNum);
        order.setOrderStatus(2);
        order.setPayTime(new Date());
        return orderDao.updateOrder(order);
    }

}
