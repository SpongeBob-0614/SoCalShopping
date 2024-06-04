package com.spongebob.socalshopping.db.mappers;

import com.spongebob.socalshopping.db.po.SoCalShoppingCommodity;
import com.spongebob.socalshopping.db.po.SoCalShoppingUser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Resource;


@Slf4j
@SpringBootTest
class SoCalShoppingCommodityMapperTest {

    @Resource
    SoCalShoppingCommodityMapper commodityMapper;
    @Resource
    SoCalShoppingUserMapper userMapper;
    @Test
    void insert() {
        userMapper.deleteByPrimaryKey(123L);
        SoCalShoppingUser user = SoCalShoppingUser.builder()
                .userId(123L)
                .name("spongebob")
                .address("Seattle")
                .email("zhangsan@hotmail.com")
                .phone("111111")
                .userType(1)
                .build();
        userMapper.insert(user);

        commodityMapper.deleteByPrimaryKey(123L);
        SoCalShoppingCommodity commodity =
                SoCalShoppingCommodity.builder()
                        .commodityId(123L)
                        .price(123)
                        .commodityDesc("desc")
                        .commodityName("name")
                        .creatorUserId(123L)
                        .availableStock(111)
                        .totalStock(10)
                        .lockStock(0)
                        .build();
        commodityMapper.insert(commodity);
    }

    @Test
    void selectByPrimaryKey() {
        SoCalShoppingCommodity commodity = commodityMapper.selectByPrimaryKey(123L);
        log.info(commodity.getCommodityName());
    }
}