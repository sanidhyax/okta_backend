package com.assesment.extenddummyjson.Services;

import com.assesment.extenddummyjson.Entities.Product;
import com.assesment.extenddummyjson.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductEntityServices {

    @Autowired
    private ProductRepository productRepository;

    public void saveAllProducts(List<Product> products) {
        productRepository.saveAll(products);
    }

    public List<String> getAllBrands() {
        List<Product> products = productRepository.findAll();

        List<String> brands = products.stream()
                .map(Product::getBrand)
                .distinct()
                .collect(Collectors.toList());

        return brands;
    }


    public Page<Product> getProductsByBrand(String brandName, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return productRepository.findByBrand(brandName, pageable);
    }


    public Page<Product> getProductsByCategoryAndBrand(String category, String brandName, int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return productRepository.findByBrandAndCategory(brandName, category, pageable);
    }
}
