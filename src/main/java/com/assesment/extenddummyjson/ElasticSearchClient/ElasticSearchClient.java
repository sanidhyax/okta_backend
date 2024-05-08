package com.assesment.extenddummyjson.ElasticSearchClient;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchClient {

    public ElasticsearchClient createSecureClientFingerPrint() throws Exception {

        String serverUrl = "https://ec40966d97cd44709f26b749845dc5a3.us-central1.gcp.cloud.es.io";
        String apiKey = "NVZ1WVY0OEJSY2dTRXpldFNNalo6REpsTXUxeFhRMHFZR2dxcjgzVUdodw==";

        RestClient restClient = RestClient
                .builder(HttpHost.create(serverUrl))
                .setDefaultHeaders(new Header[]{
                        new BasicHeader("Authorization", "ApiKey " + apiKey)
                })
                .build();

        ElasticsearchTransport transport = new RestClientTransport(
                restClient, new JacksonJsonpMapper());

        ElasticsearchClient esClient = new ElasticsearchClient(transport);

        return esClient;


    }
    }
