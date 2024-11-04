/*
 *  ElasticsearchConfig
 *  @author: Minhhieuano
 *  @created 11/1/2024 12:23 AM
 * */


package com.vibio.search.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.elasticsearch.support.HttpHeaders;
import org.springframework.lang.NonNull;

@Configuration
@EnableElasticsearchRepositories
public class ElasticsearchConfig extends ElasticsearchConfiguration {

    @Value("${elasticsearch.url}")
    private String elasticSearchEndpoint;

    @Value("${elasticsearch.apiKey}")
    private String elasticSearchApiKey;

    @Override
    @NonNull
    public ClientConfiguration clientConfiguration() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "ApiKey " + elasticSearchApiKey);

        return ClientConfiguration.builder()
                .connectedTo(elasticSearchEndpoint)
                .usingSsl()
                .withHeaders(() -> httpHeaders)
                .build();
    }

}

