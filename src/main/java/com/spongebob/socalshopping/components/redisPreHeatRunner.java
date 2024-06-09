package com.spongebob.socalshopping.components;


import com.spongebob.socalshopping.db.dao.SoCalShoppingCommodityDao;
import com.spongebob.socalshopping.db.po.SoCalShoppingCommodity;
import com.spongebob.socalshopping.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
@Component
@Slf4j
public class redisPreHeatRunner implements ApplicationRunner {
    @Resource
    SoCalShoppingCommodityDao commodityDao;

    @Resource
    RedisService redisService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<SoCalShoppingCommodity> commodities = commodityDao.listCommodities();
        for(SoCalShoppingCommodity commodity:commodities){
            String redisKey = "commodity:" + commodity.getCommodityId();
            long value = commodity.getAvailableStock();
            redisService.setValue(redisKey, value);
            log.info("Preheat starting, initialize commodity: " + commodity.getCommodityId());
        }
    }
}
