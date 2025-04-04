package com.microservice.product_service.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.microservice.product_service.entity.Product;
import com.microservice.product_service.repository.ProductRepository;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product productSample1;
    private Product productSample2;

    @BeforeEach
    void setUp(){
        productSample1 = new Product(1L,"Iphone","New Iphone",1000.0); 
        productSample2 = new Product(2L,"TV","ONIDA LCD TV",85000.0);
    }

 //Test for GetAll Products
 @Test
 void testGetAllProducts(){
     
     //this is the sample List<Product> Data asigned on productList variable
     List<Product> productList = Arrays.asList(productSample1,productSample2);
     //pass the List of product , whenever we call findAll() in repository
     when(productRepository.findAll()).thenReturn(productList);

     //whenever we call getAllProducts(), it call the repo's findAll().
     //so the findAll() return the sample DataList 
     List<Product> retrivedProducts = productService.getAllProducts();


     assertNotNull(retrivedProducts);
     assertEquals(2, retrivedProducts.size());
     assertEquals("Iphone", retrivedProducts.get(0).getName());
     assertEquals("TV", retrivedProducts.get(1).getName());
     assertEquals("New Iphone", retrivedProducts.get(0).getDescription());
     assertEquals("ONIDA LCD TV", retrivedProducts.get(1).getDescription());
     assertEquals(1000.0, retrivedProducts.get(0).getPrice());
     assertEquals(85000.0, retrivedProducts.get(1).getPrice());
     
     //verify that findAll() is called only once
     verify(productRepository, times(1)).findAll();
 }

 // ✅ Test for getProductById()
 @Test
 void testGetProductById(){
     Long productId =1L; 
     when(productRepository.findById(productId)).thenReturn(Optional.of(productSample1));

     Product retrievedProduct  = productService.getProductById(productId);

     assertNotNull(retrievedProduct);
     assertEquals("Iphone",retrievedProduct.getName());
     assertEquals("New Iphone",retrievedProduct.getDescription());
     assertEquals(1000.0,retrievedProduct.getPrice());

     verify(productRepository,times(1)).findById(productId);
 }

     // ✅ Test for getProductByName()
    @Test
    void testGetProductByName(){
       String productName = "Iphone";
        when(productRepository.findByName(productName)).thenReturn(Optional.of(productSample1));

        Product retrievedProduct = productService.getProductByName(productName);

        assertNotNull(retrievedProduct);
        assertEquals("Iphone", retrievedProduct.getName());
        assertEquals("New Iphone", retrievedProduct.getDescription());
        assertEquals(1000.0, retrievedProduct.getPrice());

        verify(productRepository,times(1)).findByName(productName);
    }

  
   
    @Test
    void testSaveProduct(){
       
        when(productRepository.save(productSample2)).thenReturn(productSample2);

        Product saveProduct = productService.saveProduct(productSample2);

        assertNotNull(saveProduct);
        assertEquals("TV", saveProduct.getName());
        assertEquals("ONIDA LCD TV", saveProduct.getDescription());
        assertEquals(85000.0, saveProduct.getPrice());

        verify(productRepository, times(1)).save(productSample2);
    }

    // ✅ Test for deleteProduct()
    @Test
    void testDeleteProduct(){
        Long productId = 2L;
        productService.deleteProduct(productId);

        verify(productRepository,times(1)).deleteById(productId);
    }

    // ✅ Test for updateProduct()
    @Test
    void testUpdateProduct(){
        Long productId = 2L;
        Product updatedProduct = new Product(productId,"updated TV","updated ONIDA LCD TV",89000.0);
        when(productRepository.findById(productId)).thenReturn(Optional.of(productSample2));

        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product product = productService.updateProduct(productId, updatedProduct);

        assertNotNull(product);
        assertEquals("updated TV", product.getName());
        assertEquals("updated ONIDA LCD TV", product.getDescription());
        assertEquals(89000.0, product.getPrice());

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository,times(1)).save(any(Product.class));

    }

}
