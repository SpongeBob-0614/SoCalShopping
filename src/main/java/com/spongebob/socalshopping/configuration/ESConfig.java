package com.spongebob.socalshopping.configuration;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ESConfig {

    @Bean
    public RestHighLevelClient ESClient(){
        return new RestHighLevelClient(RestClient.builder(new HttpHost("localhost",9200,"http")));
    }
}
