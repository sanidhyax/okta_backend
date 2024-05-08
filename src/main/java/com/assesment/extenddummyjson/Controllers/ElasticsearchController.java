package com.assesment.extenddummyjson.Controllers;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.assesment.extenddummyjson.Services.ElasticSearchServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class ElasticsearchController {

    @Autowired
    ElasticSearchServices esServices;

    @GetMapping("/products/elasticsearch")
    public List<String> elasticSearch(
            @RequestParam String q
    ){
        return esServices.searchProducts(q);
    }

//    @GetMapping("/elasticsearch/brands")
//    public void getAllBrands(){
//        esServices.test();
//    }
}
