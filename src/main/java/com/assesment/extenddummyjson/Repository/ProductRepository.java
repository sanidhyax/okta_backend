package com.assesment.extenddummyjson.Repository;

import com.assesment.extenddummyjson.Entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByBrand(String brand, Pageable pageable);

    Page<Product> findByBrandAndCategory(String brandName, String category, Pageable pageable);
}