package com.assesment.extenddummyjson.Controllers;

import com.assesment.extenddummyjson.DTO.ProductResponseDto;
import com.assesment.extenddummyjson.Entities.Product;
import com.assesment.extenddummyjson.Services.ProductEntityServices;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = {"http://localhost:3000", "https://cosmic-druid-28d7d7.netlify.app/"})
@RestController
public class BrandController {

    @Autowired
    private ProductEntityServices productEntityServices;

    @GetMapping("/")
    public String home(){
        return "Hello Okta!";
    }

    @GetMapping("/products/brands")
    public List<String> getBrands(
            HttpServletResponse response,
            @RequestParam(required = false, defaultValue = "") String category
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            if (StringUtils.isNotBlank(category)){
                return productEntityServices.getBrandsOfCategory(category);
            }
            return productEntityServices.getAllBrands();
        } catch (Exception e){
            System.out.println("Error while fetching Brands: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @GetMapping("/products/brand/{brandName}")
    public ProductResponseDto getBrandProducts(
            HttpServletResponse response,
            @PathVariable String brandName,
            @RequestParam(required = false, defaultValue = "0") Integer skip,
            @RequestParam(required = false, defaultValue = "10") Integer limit
    ){
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            Page<Product> products = productEntityServices.getProductsByBrand(brandName, (int) skip / limit, limit);
            return new ProductResponseDto(products.getContent(), (int) products.getTotalElements(), skip, limit);
        } catch (Exception e) {
            System.out.println("Error while getting brand specific products");
            response.setStatus(500);
            return new ProductResponseDto(new ArrayList<>(), -1, skip, limit);
        }
    }

    @GetMapping("/products/category/{category}/brand/{brandName}")
    public ProductResponseDto getProductsByBrandAndCategory(
            HttpServletResponse response,
            @PathVariable String category,
            @PathVariable String brandName,
            @RequestParam(required = false, defaultValue = "0") Integer skip,
            @RequestParam(required = false, defaultValue = "10") Integer limit
    ) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        try {
            Page<Product> products = productEntityServices.getProductsByCategoryAndBrand(category, brandName, (int) skip / limit, limit);

            return new ProductResponseDto(products.getContent(), (int) products.getTotalElements(), skip, limit);
        } catch (Exception e) {
            System.out.println("Error while getting products by brand and category: " + e.getMessage());
            response.setStatus(500);
            return new ProductResponseDto(new ArrayList<>(), -1, skip, limit);
        }
    }
}
