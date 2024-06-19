package com.spongebob.socalshopping.service.mq;

import com.alibaba.fastjson.JSON;
import com.spongebob.socalshopping.db.dao.SoCalShoppingCommodityDao;
import com.spongebob.socalshopping.db.dao.SoCalShoppingOrderDao;
import com.spongebob.socalshopping.db.po.SoCalShoppingOrder;
import com.spongebob.socalshopping.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
@RocketMQMessageListener(topic = "checkOrder", consumerGroup = "checkOrderGroup")
public class CheckOrderListener implements RocketMQListener<MessageExt> {

    @Resource
    SoCalShoppingOrderDao orderDao;

    @Resource
    SoCalShoppingCommodityDao commodityDao;

    @Resource
    RedisService redisService;



    @Override
    public void onMessage(MessageExt messageExt) {
        String message = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        log.info("ChcekOrder Message Body: " + message);
        SoCalShoppingOrder orderMsg = JSON.parseObject(message, SoCalShoppingOrder.class);
        SoCalShoppingOrder orderDB = orderDao.queryOrderByOrderNo(orderMsg.getOrderNo());

        if(orderDB == null){
            log.error("can't find order {} in MYSQL", orderMsg.getOrderNo());
            return;
        }
        if(orderDB.getOrderStatus()!= 2){
            orderDB.setOrderStatus(99);
            orderDao.updateOrder(orderDB);
            commodityDao.revertStock(orderDB.getCommodityId());
            String redisKey = "commodity:" + orderDB.getCommodityId();
            //After deny, remove
            redisService.revertStock(redisKey);
            redisService.removeToDenyList(orderDB.getUserId(), orderDB.getCommodityId());
            log.info("Revert order since it passed max payment time, order: {}", orderDB.getOrderNo());
        }else{
            log.info("Skip operation for order {}, since it is finished order", orderDB.getOrderNo());
        }

    }
}
