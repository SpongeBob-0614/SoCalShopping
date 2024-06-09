package com.spongebob.socalshopping.controller;

import com.spongebob.socalshopping.db.dao.SoCalShoppingCommodityDao;
import com.spongebob.socalshopping.db.po.SoCalShoppingCommodity;
import com.spongebob.socalshopping.db.po.SoCalShoppingOrder;
import com.spongebob.socalshopping.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Map;

@Controller
public class OrderController {

    @Resource
    OrderService orderService;

    @Resource
    SoCalShoppingCommodityDao commodityDao;

    @RequestMapping("/commodity/buy/{userId}/{commodityId}")
    public String buyCommodity(
            @PathVariable("userId") String userId,
            @PathVariable("commodityId") String commodityId,
            Map<String, Object> resultMap){

        //1. 无任何优化 会导致超卖问题
        //SoCalShoppingOrder order = orderService.processOrder(Long.parseLong(commodityId), Long.parseLong(userId));

        //2. atomic操作 通过将查询和update两个操作合为一个操作解决超卖问题
        //SoCalShoppingOrder order = orderService.processOrderOneSQL(Long.parseLong(commodityId), Long.parseLong(userId));

        //3. StoredProcedures
        //SoCalShoppingOrder order = orderService.processOrderOneSP(Long.parseLong(commodityId),Long.parseLong(userId));

        //4. Redis
        SoCalShoppingOrder order = orderService.processOrderRedis(Long.parseLong(commodityId),Long.parseLong(userId));
        String resultInfo = null;
        if(order != null){
            resultInfo = "Order created successfully, order info: " + order.getOrderNo();
            resultMap.put("orderNo", order.getOrderNo());
        }
        else{
            resultInfo = "The commodity is out of stock";
        }
        resultMap.put("resultInfo", resultInfo);
        return "order_result";
    }

    @RequestMapping("/commodity/orderQuery/{orderNum}")
    public String orderQuery(@PathVariable("orderNum") String orderNum,
                             Map<String, Object> resultMap){
        SoCalShoppingOrder order = orderService.getOrderByOrderNo(orderNum);
        SoCalShoppingCommodity commodityDetails = commodityDao.getCommodityDetails(order.getCommodityId());
        resultMap.put("commodity", commodityDetails);
        resultMap.put("order", order);
        return "order_check";
    }

    @RequestMapping("/commodity/payOrder/{orderNum}")
    public String payOrder(@PathVariable("orderNum") String orderNum,
                            Map<String, Object> resultMap){
        orderService.payOrder(orderNum);
        SoCalShoppingOrder order = orderService.getOrderByOrderNo(orderNum);
        SoCalShoppingCommodity commodityDetails = commodityDao.getCommodityDetails(order.getCommodityId());
        resultMap.put("commodity", commodityDetails);
        resultMap.put("order", order);
        return "order_check";
    }

}
