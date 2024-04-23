package com.assesment.extenddummyjson.ScheduleTasks;

import com.assesment.extenddummyjson.DTO.ProductResponseDto;
import com.assesment.extenddummyjson.Services.ProductEntityServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


/**
 * This class defines a scheduled task to fetch products from the products API at regular intervals. This
 * mocks the behaviour of having the latest data at all times in 10 second intervals. Preferred
 * would've been a push service
 *
 * The fetched products are then processed and saved to the H2 database.
 */
@Component
public class ScheduleProductFetch {

    @Autowired
    private ProductEntityServices productEntityServices;

    @EventListener(ApplicationReadyEvent.class)
    public void fetchProducts() {
        String endpointUrl = "https://dummyjson.com/products?limit=0";
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<ProductResponseDto> responseEntity = restTemplate.getForEntity(endpointUrl, ProductResponseDto.class);

            HttpStatusCode statusCode = responseEntity.getStatusCode();
            if (statusCode.is2xxSuccessful()) {
                ProductResponseDto response = responseEntity.getBody();
                System.out.println("Total Products Fetched: " + response.getTotal());
                if (response != null && response.getProducts() != null) {
                    productEntityServices.saveAllProducts(response.getProducts());
                }
            } else {
                System.out.println("HTTP error: " + statusCode);
            }
        } catch (Exception e) {
            System.err.println("Error occurred while fetching products: " + e.getMessage());
        }
    }
}
