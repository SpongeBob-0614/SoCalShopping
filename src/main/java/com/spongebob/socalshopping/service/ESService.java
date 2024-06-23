package com.spongebob.socalshopping.service;

import com.alibaba.fastjson.JSON;
import com.spongebob.socalshopping.db.po.SoCalShoppingCommodity;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ESService {

    @Resource
    RestHighLevelClient restHighLevelClient;

    public int addCommodityToES(SoCalShoppingCommodity commodity){
        try{
            String indexname = "commodity";
            boolean isIndexExist = restHighLevelClient.indices().exists(new GetIndexRequest(indexname), RequestOptions.DEFAULT);
            if(!isIndexExist){
                XContentBuilder builder = XContentFactory.jsonBuilder();
                builder.startObject()
                        .startObject("dynamic_templates")
                        .startObject("strings")
                        .field("match_mapping_type", "string")
                        .startObject("mapping")
                        .field("type", "text")
                        .field("analyzer", "ik_smart")
                        .endObject()
                        .endObject()
                        .endObject()
                        .endObject();
                CreateIndexRequest request = new CreateIndexRequest(indexname);
                request.source(builder);
                CreateIndexResponse response = restHighLevelClient.indices().create(request,RequestOptions.DEFAULT);
                if(!response.isAcknowledged()){
                    log.error("Failed to create ES index: commodity");
                    return RestStatus.INTERNAL_SERVER_ERROR.getStatus();
                }
            }
            String data = JSON.toJSONString(commodity);
            IndexRequest request = new IndexRequest("commodity").source(data, XContentType.JSON);
            IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
            log.info("addCommodityToES commodity:{} result:{}", data, response);
            return response.status().getStatus();
        } catch (Exception e) {
            log.error("SearchService addCommodityToEs error", e);
            return RestStatus.INTERNAL_SERVER_ERROR.getStatus();
        }
    }

    public List<SoCalShoppingCommodity> searchCommodities(String keyword, int from, int size){
        try {
            //构建查询请求，指定查询的索引库
            SearchRequest searchRequest = new SearchRequest("commodity");
            //创建查询条件构造器 SearchSourceBuilder
            SearchSourceBuilder searchSourceBuilder = new
                    SearchSourceBuilder();
            QueryBuilder queryBuilder =
                    QueryBuilders.multiMatchQuery(keyword, "commodityName",
                            "commodityDesc");
            //指定查询条件
            searchSourceBuilder.query(queryBuilder);
            /*
             * 指定分页查询信息
             * 从哪里开始查
             */
            searchSourceBuilder.from(from);
            //每次查询的数量
            searchSourceBuilder.size(size);
            /*
             * 设置排序规则
             * 按照销量排序
             */
            searchSourceBuilder.sort("price", SortOrder.DESC);
            searchRequest.source(searchSourceBuilder);
            //查询获取查询结果
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest,
                    RequestOptions.DEFAULT);
            System.out.println(JSON.toJSONString(searchResponse));
            //获取命中对象
            SearchHits searchHits = searchResponse.getHits();
            long totalNum = searchHits.getTotalHits().value;
            log.info("search 总记录数： {}", totalNum);
            List<SoCalShoppingCommodity> onlineShoppingCommodities =
                    new ArrayList<>();
            //获取命中的 hits 数据,搜索结果数据
            SearchHit[] hits = searchHits.getHits();
            for (SearchHit searchHit : hits) {
                //获取 json 字符串格式的数据
                String sourceAsString = searchHit.getSourceAsString();
                SoCalShoppingCommodity onlineShoppingCommodity =
                        JSON.parseObject(sourceAsString, SoCalShoppingCommodity.class);

                onlineShoppingCommodities.add(onlineShoppingCommodity);
            }
            log.info("search result {}",
                    JSON.toJSONString(onlineShoppingCommodities));
            return onlineShoppingCommodities;
        } catch (Exception e) {
            log.error("SearchService searchCommodities error", e);
            throw new RuntimeException(e);
        }
    }

}

