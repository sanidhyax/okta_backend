package com.assesment.extenddummyjson.Services;

import com.assesment.extenddummyjson.Entities.Product;
import com.assesment.extenddummyjson.Repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductEntityServicesTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductEntityServices productEntityServices;

    @Test
    public void testGetAllBrands() {
        List<Product> products = Arrays.asList(
                new Product(1L, "Product1", "Description1", 100.0, 10.0, 4.5, 100, "Brand1", "Category1", "thumbnail1.jpg", Arrays.asList("image1.jpg", "image2.jpg")),
                new Product(2L, "Product2", "Description2", 150.0, 15.0, 4.0, 200, "Brand2", "Category2", "thumbnail2.jpg", Arrays.asList("image3.jpg", "image4.jpg"))
        );

        when(productRepository.findAll()).thenReturn(products);

        List<String> brands = productEntityServices.getAllBrands();

        List<String> expectedBrands = Arrays.asList("Brand1", "Brand2");
        assertEquals(expectedBrands, brands);
    }

    @Test
    public void testGetProductsByBrand() {
        List<Product> products = Arrays.asList(
                new Product(1L, "Product1", "Description1", 100.0, 10.0, 4.5, 100, "Brand1", "Category1", "thumbnail1.jpg", Arrays.asList("image1.jpg", "image2.jpg")),
                new Product(2L, "Product2", "Description2", 150.0, 15.0, 4.0, 200, "Brand1", "Category2", "thumbnail2.jpg", Arrays.asList("image3.jpg", "image4.jpg"))
        );
        Page<Product> page = new PageImpl<>(products);

        when(productRepository.findByBrand("Brand1", PageRequest.of(0, 10))).thenReturn(page);

        Page<Product> resultPage = productEntityServices.getProductsByBrand("Brand1", 0, 10);

        assertEquals(page, resultPage);
    }

    @Test
    public void testGetBrandsOfCategory() {
        // Given
        String category = "TestCategory";
        List<Product> products = Arrays.asList(
                new Product(1L, "Product1", "Description1", 10.0, 0.0, 4.5, 100, "Brand1", category, "thumbnail1", Arrays.asList("image1.jpg")),
                new Product(2L, "Product2", "Description2", 15.0, 0.0, 4.7, 150, "Brand2", category, "thumbnail2", Arrays.asList("image2.jpg")),
                new Product(3L, "Product3", "Description3", 20.0, 0.0, 4.2, 200, "Brand1", category, "thumbnail3", Arrays.asList("image3.jpg"))
        );
        when(productRepository.findByCategory(category)).thenReturn(products);

        // When
        List<String> brands = productEntityServices.getBrandsOfCategory(category);

        // Then
        assertEquals(2, brands.size());
        assertEquals("Brand1", brands.get(0));
        assertEquals("Brand2", brands.get(1));
    }

}
