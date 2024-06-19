package com.spongebob.socalshopping.service.mq;

import com.alibaba.fastjson.JSON;
import com.spongebob.socalshopping.db.dao.SoCalShoppingCommodityDao;
import com.spongebob.socalshopping.db.dao.SoCalShoppingOrderDao;
import com.spongebob.socalshopping.db.po.SoCalShoppingCommodity;
import com.spongebob.socalshopping.db.po.SoCalShoppingOrder;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Slf4j
@RocketMQMessageListener(topic = "createOrder", consumerGroup = "createOrderGroup")
public class CreateOrderListener implements RocketMQListener<MessageExt> {

    @Resource
    SoCalShoppingCommodityDao commodityDao;

    @Resource
    SoCalShoppingOrderDao orderDao;

    @Resource
    RocketMQService rocketMQService;

    @Override
    public void onMessage(MessageExt messageExt) {
        String message = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        log.info("CreateOrder Message Body: " + message);
        SoCalShoppingOrder order = JSON.parseObject(message, SoCalShoppingOrder.class);
        Long commodityId = order.getCommodityId();
        int result = commodityDao.deductStock(order.getCommodityId());//库存扣减
        if(result > 0){
            SoCalShoppingCommodity commodity = commodityDao.getCommodityDetails(commodityId);
            log.info("Process successful for commodityId: " + commodityId + ", current available stock: " + commodity.getAvailableStock());
            order.setOrderAmount(commodity.getPrice().longValue());
            order.setCreateTime(new Date());
            //create order
            //0, invalid order, since no available stock
            //1, already create order, pending for payment
            //2, finishing payment
            //99, invalid after due to payment process overtime
            order.setOrderStatus(1);
            orderDao.insertOrder(order);
            try {
                rocketMQService.sendDelayMessage("checkOrder", JSON.toJSONString(order),3);
            } catch (Exception e) {
                throw new RuntimeException("Failed to send delay message to CheckOrder topic");
            }
        }
    }
}
