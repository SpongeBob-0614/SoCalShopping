package com.spongebob.socalshopping.db.dao.impl;

import com.spongebob.socalshopping.db.dao.SoCalShoppingCommodityDao;
import com.spongebob.socalshopping.db.mappers.SoCalShoppingCommodityMapper;
import com.spongebob.socalshopping.db.po.SoCalShoppingCommodity;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public int deductStock(long commodityId) {
        return mapper.deduckStock(commodityId);
    }

    @Override
    public int deductStockSP(long commodityId) { 
        Map<String, Object> params = new HashMap<>();
        params.put("commodityId", commodityId);
        params.put("res",0);
        mapper.deduckStockSP(params);
        Object res = params.getOrDefault("res",0);
        return (int) res;
    }

    @Override
    public void revertStock(Long commodityId) {
        mapper.revertStock(commodityId);
    }

    @Override
    public List<SoCalShoppingCommodity> queryCommodityByKeyword(String keyword) {
        
        return mapper.queryCommodityByKeyword(keyword);
    }
}
