package com.assesment.extenddummyjson.Controllers;

import com.assesment.extenddummyjson.Entities.Product;
import com.assesment.extenddummyjson.Services.ProductEntityServices;
import org.hamcrest.collection.ArrayAsIterableMatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PropertyPermission;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class BrandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductEntityServices productEntityServices;

    Product product1 = new Product(1L, "Product1", "Description1", 100.0, 0.0, 4.5, 100, "Brand1", "Category1", "thumbnail1.jpg", Arrays.asList("image1.jpg"));
    Product product2 = new Product(2L, "Product2", "Description2", 150.0, 0.0, 4.0, 200, "Brand2", "Category2", "thumbnail2.jpg", Arrays.asList("image2.jpg"));


    // Test the response coming from /brands
    @Test
    public void testGetBrands() throws Exception {
        // Mock the service method call
        when(productEntityServices.getAllBrands()).thenReturn(Arrays.asList("Brand1", "Brand2"));

        // Perform the GET request
        mockMvc.perform(MockMvcRequestBuilders.get("/products/brands")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value("Brand1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]").value("Brand2"));
    }

    // Test the response coming from /brand/{brandName}. Check the structure of the response and status code
    @Test
    public void testGetBrandProducts() throws Exception {

        List<Product> mockList = Arrays.asList(product1);

        Page<Product> productPage = new PageImpl<>(mockList);

        // Mock service method call
        when(productEntityServices.getProductsByBrand(anyString(), anyInt(), anyInt())).thenReturn(productPage);

        // Perform the GET request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/products/brand/Brand1")
                .param("skip", "0")
                .param("limit", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.total").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.skip").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.limit").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].brand").value("Brand1"));

    }

    // Test the response coming from /brand/{brandName}. Check the structure of the response and status code
    @Test
    public void testGetBrandProductsWithException() throws Exception {
        // Mock service method call to throw an exception
        when(productEntityServices.getProductsByBrand(anyString(), anyInt(), anyInt())).thenThrow(new RuntimeException("Test exception"));

        // Perform the GET request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/products/brand/BrandName")
                .param("skip", "0")
                .param("limit", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.products").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.total").value(-1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.skip").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.limit").value(10));
    }


    // Test exception response
    @Test
    public void testGetProductsByBrandAndCategoryWithException() throws Exception {
        // Mock service method call to throw an exception
        when(productEntityServices.getProductsByCategoryAndBrand(anyString(), anyString(), anyInt(), anyInt()))
                .thenThrow(new RuntimeException("Test exception"));

        // Perform the GET request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/products/category/CategoryName/brand/BrandName")
                .param("skip", "0")
                .param("limit", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is5xxServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.products").isEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.total").value(-1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.skip").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.limit").value(10));
    }
}
