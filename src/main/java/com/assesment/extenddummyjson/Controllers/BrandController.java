package com.assesment.extenddummyjson.Controllers;

import com.assesment.extenddummyjson.DTO.ProductResponseDto;
import com.assesment.extenddummyjson.Entities.Product;
import com.assesment.extenddummyjson.Exceptions.NotFoundException;
import com.assesment.extenddummyjson.Services.ProductEntityServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BrandController {

    @Autowired
    private ProductEntityServices productEntityServices;

    @GetMapping("/")
    public String home(){
        return "Hello Okta!";
    }

    @GetMapping("/brands")
    public List<String> getBrands() {
        try {
            return productEntityServices.getAllBrands();
        } catch (Exception e){
            System.out.println("Error while fetching Brands: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @GetMapping("/brand/{brandName}")
    public ProductResponseDto getBrandProducts(
            @PathVariable String brandName,
            @RequestParam(required = false, defaultValue = "0") Integer skip,
            @RequestParam(required = false, defaultValue = "10") Integer limit
    ){
        try {
            Page<Product> products = productEntityServices.getProductsByBrand(brandName, (int) skip / limit, limit);
            return new ProductResponseDto(products.getContent(), (int) products.getTotalElements(), skip, limit);
        } catch (Exception e) {
            System.out.println("Error while getting brand specific products");
            return new ProductResponseDto(new ArrayList<>(), -1, 0, 0);
        }
    }
}
