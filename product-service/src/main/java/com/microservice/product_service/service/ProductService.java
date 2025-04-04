package com.microservice.product_service.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.product_service.entity.Product;
import com.microservice.product_service.repository.ProductRepository;

import java.util.List;


@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Product getProductById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: "+id));
    }

    public Product getProductByName(String name){
        return productRepository.findByName(name).orElseThrow(() -> new RuntimeException("Product not found with name: "+name));
    }

    public Product saveProduct(Product product){
        return productRepository.save(product);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }

    public Product updateProduct(Long id, Product product) {
       Product existingProduct = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with id: "+id));
       existingProduct.setName(product.getName());
       existingProduct.setDescription(product.getDescription());
       existingProduct.setPrice(product.getPrice());
       return productRepository.save(existingProduct);
    }
    

}
