package com.spongebob.socalshopping.service;

import com.alibaba.fastjson.JSON;
import com.spongebob.socalshopping.db.dao.SoCalShoppingCommodityDao;
import com.spongebob.socalshopping.db.dao.SoCalShoppingOrderDao;
import com.spongebob.socalshopping.db.po.SoCalShoppingCommodity;
import com.spongebob.socalshopping.db.po.SoCalShoppingOrder;
import com.spongebob.socalshopping.service.mq.RocketMQService;
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

    @Resource
    RedisService redisService;

    @Resource
    RocketMQService rocketMQService;

    public SoCalShoppingOrder processOrder(long commodityId, long userId){
        SoCalShoppingCommodity commodity = commodityDao.getCommodityDetails(commodityId);
        //check
        int availableStock = commodity.getAvailableStock();
        if(availableStock > 0){
            availableStock--;
            log.info("Process successful for commodityId: " + commodityId+", current available stock: "+ availableStock);
            SoCalShoppingOrder order = SoCalShoppingOrder.builder()
                    .userId(userId)
                    .commodityId(commodityId)
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

    public SoCalShoppingOrder processOrderOneSQL(long commodityId, long userId){
        SoCalShoppingCommodity commodity = commodityDao.getCommodityDetails(commodityId);
        //check
        int availableStock = commodity.getAvailableStock();
        if(availableStock > 0){
            int result = commodityDao.deductStock(commodityId);
            if(result>0) {
                log.info("Process successful for commodityId: " + commodityId + ", current available stock: " + availableStock);
                SoCalShoppingOrder order = SoCalShoppingOrder.builder()
                        .userId(userId)
                        .commodityId(commodityId)
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
                return order;
            }
        }
            log.info("Process order failed, no available stock, commodityId: " + commodityId);
            return null;

    }

    public SoCalShoppingOrder processOrderOneSP(long commodityId, long userId){
        SoCalShoppingCommodity commodity = commodityDao.getCommodityDetails(commodityId);
        //check
        int availableStock = commodity.getAvailableStock();
        if(availableStock > 0){
            int result = commodityDao.deductStockSP(commodityId);
            if(result>0) {
                log.info("Process succesful for commodityId: " + commodityId + ", current available stock: " + availableStock);
                SoCalShoppingOrder order = SoCalShoppingOrder.builder()
                        .userId(userId)
                        .commodityId(commodityId)
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
                return order;
            }
        }
        log.info("Process order failed, no available stock, commodityId: " + commodityId);
        return null;

    }

    public SoCalShoppingOrder processOrderRedis(long commodityId, long userId){
        String redisKey = "commodity:"+commodityId;
        //check
        long availableStock = redisService.deductStock(redisKey);
        if(availableStock >= 0){
            int result = commodityDao.deductStock(commodityId);
            if(result>0) {
                SoCalShoppingCommodity commodity = commodityDao.getCommodityDetails(commodityId);
                log.info("Process successful for commodityId: " + commodityId + ", current available stock: " + availableStock);
                SoCalShoppingOrder order = SoCalShoppingOrder.builder()
                        .userId(userId)
                        .commodityId(commodityId)
                        .orderNo(UUID.randomUUID().toString())
                        .orderAmount(commodity.getPrice().longValue())
                        .createTime(new Date())
                        .orderStatus(1)
                        //create order
                        //0, invalid order, since no available stock
                        //1, already create order, pending for payment
                        //2, finishing payment
                        //99, invalid after due to payment process overtime
                        .build();
                orderDao.insertOrder(order);
                //更新库存
                return order;
            }
        }
        log.info("Process order failed, no available stock, commodityId: " + commodityId);
        return null;

    }

    public SoCalShoppingOrder processOrderDistributedLock(long commodityId, long userId) {
        String redisKey = "Lock_commodity:"+commodityId;
        String requestId = UUID.randomUUID().toString();
        Boolean getLock = redisService.tryDistributedLock(redisKey, requestId, 5000);
        if(getLock){
            int result = commodityDao.deductStock(commodityId);
            SoCalShoppingOrder order = null;
            if(result>0) {
                SoCalShoppingCommodity commodity = commodityDao.getCommodityDetails(commodityId);
                log.info("Process succesful for commodityId: " + commodityId);
                order = SoCalShoppingOrder.builder()
                        .userId(userId)
                        .commodityId(commodityId)
                        .orderNo(UUID.randomUUID().toString())
                        .orderAmount(commodity.getPrice().longValue())
                        .createTime(new Date())
                        .orderStatus(1)
                        //create order
                        //0, invalid order, since no available stock
                        //1, already create order, pending for payment
                        //2, finishing payment
                        //99, invalid after due to payment process overtime
                        .build();
                orderDao.insertOrder(order);

            }
            redisService.releaseLock(redisKey,requestId);
            //更新库存
            return order;
        }
        log.info("Process try again later for commodityId: " + commodityId);
        return null;

    }

    public SoCalShoppingOrder processOrderRocketMQ(long commodityId, long userId) throws Exception {
        String redisKey = "commodity:"+commodityId;
        //check
        long availableStock = redisService.deductStock(redisKey);
        if(availableStock >= 0){
            //produce message
            SoCalShoppingOrder order = SoCalShoppingOrder.builder()
                    .commodityId(commodityId)
                    .userId(userId)
                    .orderNo(UUID.randomUUID().toString())
                    .build();
            String msg = JSON.toJSONString(order);
            rocketMQService.sendMessage("createOrder", msg);
            return order;
        }
        log.info("Process order failed, no available stock, commodityId: " + commodityId);
        return null;

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
