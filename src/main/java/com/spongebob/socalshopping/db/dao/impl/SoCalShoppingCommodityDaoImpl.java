package com.spongebob.socalshopping.db.dao.impl;

import com.spongebob.socalshopping.db.dao.SoCalShoppingCommodityDao;
import com.spongebob.socalshopping.db.mappers.SoCalShoppingCommodityMapper;
import com.spongebob.socalshopping.db.po.SoCalShoppingCommodity;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class SoCalShoppingCommodityDaoImpl implements SoCalShoppingCommodityDao {

    @Resource
    SoCalShoppingCommodityMapper mapper;
    @Override
    public int insertCommodity(SoCalShoppingCommodity commodity) {
        return mapper.insert(commodity);
    }

    @Override
    public int updateCommodity(SoCalShoppingCommodity commodity) {
        return mapper.updateByPrimaryKey(commodity);
    }

    @Override
    public List<SoCalShoppingCommodity> listCommoditiesByUserId(long userId) {

        return mapper.listCommoditiesByUserId(userId);
    }

    @Override
    public List<SoCalShoppingCommodity> listCommodities() {

        return mapper.listCommodities();
    }

    @Override
    public SoCalShoppingCommodity getCommodityDetails(long commodityId) {

        return mapper.selectByPrimaryKey(commodityId);
    }
}
