package com.spongebob.socalshopping.service;

import com.spongebob.socalshopping.db.dao.SoCalShoppingCommodityDao;
import com.spongebob.socalshopping.db.po.SoCalShoppingCommodity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SearchService {
    @Resource
    SoCalShoppingCommodityDao commodityDao;

    @Resource
    ESService esService;

    public List<SoCalShoppingCommodity> searchCommodityDDB(String keyword){
        return commodityDao.queryCommodityByKeyword(keyword);
    }

    public List<SoCalShoppingCommodity> searchCommodityES(String keyword,int from,int size){
        return esService.searchCommodities(keyword, from, size);
    }

    public int addCommodityToES(SoCalShoppingCommodity commodity){
        return esService.addCommodityToES(commodity);
    }
}
