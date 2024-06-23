package com.spongebob.socalshopping.controller;

import com.spongebob.socalshopping.db.dao.SoCalShoppingCommodityDao;
import com.spongebob.socalshopping.db.po.SoCalShoppingCommodity;
import com.spongebob.socalshopping.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
public class CommodityController {

    @Resource
    SoCalShoppingCommodityDao commodityDao;

    @Resource
    SearchService searchService;

    @GetMapping("/addItem")
    public String addItem(){
        return "add_commodity";
    }

    @PostMapping("/addItemAction")
    public String addItemAction(@RequestParam("commodityId") long commodityId,
                                @RequestParam("commodityName") String commodityName,
                                @RequestParam("commodityDesc") String commodityDesc,
                                @RequestParam("price") int price,
                                @RequestParam("availableStock") int availableStock,
                                @RequestParam("creatorUserId") long creatorUserId,
                                Map<String, Object> resultMap){
        SoCalShoppingCommodity commodity = SoCalShoppingCommodity.builder()
                .commodityId(commodityId)
                .commodityName(commodityName)
                .commodityDesc(commodityDesc)
                .price(price)
                .availableStock(availableStock)
                .totalStock(availableStock)
                .lockStock(0)
                .creatorUserId(creatorUserId)
                .build();

        resultMap.put("Item", commodity);
        commodityDao.insertCommodity(commodity);

        return "add_commodity_success";
    }

    @GetMapping("/listItems/{sellerId}")//严格按照RestfulAPI这里应该是commodities
    public String listItemByUserId(@PathVariable("sellerId") String sellerId,
                                   Map<String, Object> resultMap){
        List<SoCalShoppingCommodity> commodityList = commodityDao.listCommoditiesByUserId(Long.parseLong(sellerId));
        resultMap.put("itemList",commodityList);
        return "list_items";
    }

    @GetMapping("/item/{commodityId}")
    public String getItem(@PathVariable("commodityId") String commodityId,
                          Map<String, Object> resultMap){
        SoCalShoppingCommodity commodity = commodityDao.getCommodityDetails(Long.parseLong(commodityId));
        resultMap.put("commodity", commodity);
        return "item_detail";

    }

    @RequestMapping("/searchAction")
    public String search(@RequestParam("keyword") String keyword,
                         Map<String, Object> resultMap){

        List<SoCalShoppingCommodity> soCalShoppingCommodities = searchService.searchCommodityDDB(keyword);
        resultMap.put("itemList", soCalShoppingCommodities);
        return "search_items";
    }

}
