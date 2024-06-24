package com.spongebob.socalshopping.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.spongebob.socalshopping.db.dao.SoCalShoppingCommodityDao;
import com.spongebob.socalshopping.db.po.SoCalShoppingCommodity;
import com.spongebob.socalshopping.service.ESService;
import com.spongebob.socalshopping.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
public class CommodityController {

    @Resource
    SoCalShoppingCommodityDao commodityDao;

    @Resource
    SearchService searchService;

    @Resource
    ESService esService;

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
        searchService.addCommodityToES(commodity);

        return "add_commodity_success";
    }

    @GetMapping("/listItems/{sellerId}")//严格按照RestfulAPI这里应该是commodities
    public String listItemByUserId(@PathVariable("sellerId") String sellerId,
                                   Map<String, Object> resultMap){

        try(Entry entry= SphU.entry("listItemsRule", EntryType.IN, 1, sellerId)){
        List<SoCalShoppingCommodity> commodityList = commodityDao.listCommoditiesByUserId(Long.parseLong(sellerId));
        resultMap.put("itemList",commodityList);
            log.info("Successfully fetched item list for sellerId: {}", sellerId);
        return "list_items";
        } catch (BlockException e) {
            log.error("ListItems API get throttled with sellerId {}", sellerId);
            return "wait";
        }
    }

    @GetMapping("/item/{commodityId}")
    public String getItem(@PathVariable("commodityId") String commodityId,
                          Map<String, Object> resultMap){
        SoCalShoppingCommodity commodity = commodityDao.getCommodityDetails(Long.parseLong(commodityId));
        resultMap.put("commodity", commodity);
        return "item_detail";

    }

    @RequestMapping("/staticItem/{commodityId}")
    public String staticItemPage(
            @PathVariable("commodityId") long commodityId
    ) {
        return "item_detail_" + commodityId;
    }

    @RequestMapping("/searchAction")
    public String search(@RequestParam("keyWord") String keyword,
                         Map<String, Object> resultMap){
        //mysql database
        //List<SoCalShoppingCommodity> soCalShoppingCommodities = searchService.searchCommodityDDB(keyword);

        //elasticsearch
        List<SoCalShoppingCommodity> soCalShoppingCommodities = esService.searchCommodities(keyword, 0, 10);
        resultMap.put("itemList", soCalShoppingCommodities);
        return "search_items";
    }

    @PostConstruct
    public void rateLimit(){
        FlowRule flowRule = new FlowRule();
        flowRule.setResource("listItemsRule");
        flowRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        flowRule.setCount(1);//1s之内OK
        //FlowRuleManager.loadRules(Collections.singletonList(flowRule));

        List<FlowRule> rules = new ArrayList<>();
        rules.add(flowRule);
        FlowRuleManager.loadRules(rules);
        log.info("Sentinel flow rules initialized");
    }

}
