package com.assesment.extenddummyjson.Repository;

import com.assesment.extenddummyjson.Entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByBrand(String brand, Pageable pageable);

    Page<Product> findByBrandAndCategory(String brandName, String category, Pageable pageable);

    List<Product> findByCategory(String category);
}