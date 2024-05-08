package com.assesment.extenddummyjson.Services;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import com.assesment.extenddummyjson.ElasticSearchClient.ElasticSearchClient;
import com.assesment.extenddummyjson.Entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ElasticSearchServices {

    @Autowired
    private ElasticSearchClient elasticSearchClient;

    public List<String> searchProducts(String searchParam) {

        List<String> responseList = new ArrayList<>();

        try {
            ElasticsearchClient esClient = elasticSearchClient.createSecureClientFingerPrint();

            SearchResponse<Product> response = esClient.search(s -> s
                            .index("dummyproducts")
                            .size(100)
                            .query(q -> q
                                    .wildcard(w -> w
                                            .field("title")
                                            .value(searchParam + "*")
                                    )
                            ),
                    Product.class
            );

            TotalHits total = response.hits().total();
            boolean isExactResult = total.relation() == TotalHitsRelation.Eq;

            if (isExactResult) {
                System.out.println("There are " + total.value() + " results");
            } else {
                System.out.println("There are more than " + total.value() + " results");
            }

            List<Hit<Product>> hits = response.hits().hits();
            if (total.value() > 0) {
                for (Hit<Product> hit : hits) {
                    Product product = hit.source();
                    responseList.add(product.getTitle());
                    System.out.println("Found product " + product.getTitle() + ", score " + hit.score());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseList;
    }

    public List<String> getAllBrands(String searchParam) {

        List<String> responseList = new ArrayList<>();

        try {
            ElasticsearchClient esClient = elasticSearchClient.createSecureClientFingerPrint();

            SearchResponse<Product> response = esClient.search(s -> s
                            .index("dummyproducts")
                            .size(100)
                            .query(q -> q
                                    .wildcard(w -> w
                                            .field("brand")
                                            .value("*")
                                    )
                            ),
                    Product.class
            );

            TotalHits total = response.hits().total();

            System.out.println("There are " + total.value() + " results");

            List<Hit<Product>> hits = response.hits().hits();
            if (total.value() > 0) {
                for (Hit<Product> hit : hits) {
                    Product product = hit.source();
                    responseList.add(product.getTitle());
                    System.out.println("Found product " + product.getTitle() + ", score " + hit.score());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseList;
    }

//    Getting only brands from elasticSearch cloud - In Development
//    public void getAllBrands() {
//        try {
//            ElasticsearchClient esClient = elasticSearchClient.createSecureClientFingerPrint();
//
////            SearchResponse<String> response = esClient.search(s -> s
////                            .index("dummyproducts")
////                            .size(100)
////                            .query(q -> q
////                                    .wildcard(w -> w
////                                            .field("brand")
////                                            .value("*")
////                                    )
////                            ).aggregations("brand", a -> a
////                            .terms(h -> h
////                            .field("brand")
////                            .size(100))),
////                    String.class
////            );
//
//
//            SearchResponse<Product> response = esClient.search(b -> b
//                    .index("dummyproducts")
//                    .size(0)
//                    .aggregations("brand", a -> a
//                            .terms(h -> h
//                                    .field("brand")
//                                    .size(100)
//                            )
//                    ),
//            Product.class);
//
//            System.out.println(response.aggregations().get("brand").sterms().buckets().array());
//
//
//            TotalHits total = response.hits().total();
//
//            List<String> responseList = new ArrayList<>();
//
//            List<Hit<Product>> hits = response.hits().hits();
//            if (total.value() > 0) {
//                for (Hit<Product> hit : hits) {
//                    Product product = hit.source();
//                    responseList.add(product.getBrand());
//                    System.out.println("Found brand " + product.getBrand() + ", score " + hit.score());
//                }
//            }
//
//            System.out.println("There are " + total.value() + " brands");
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }


}
