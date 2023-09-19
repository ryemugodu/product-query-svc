package com.product.service;

import com.product.dto.ProductEvent;
import com.product.entity.Product;
import com.product.repository.ProductQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductQueryService {

    @Autowired
    ProductQueryRepository repository;

    public List<Product> getProducts(){
        return  repository.findAll();
    }

    public Product getProductById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @KafkaListener(groupId = "product-event-group", topics = "product-event-topic")
    public void processProductEvents(ProductEvent productEvent){
        if(productEvent.getEventType().equals("CreateProduct")) {
            repository.save(productEvent.getProduct());
        } else if (productEvent.getEventType().equals("UpdateProduct")) {
            Product existingProduct= repository.findById(productEvent.getProduct().getId()).orElseGet(() -> new Product());
            Product product = productEvent.getProduct();
            existingProduct.setName(product.getName());
            existingProduct.setDescription(product.getDescription());
            existingProduct.setPrice(product.getPrice());
            repository.save(productEvent.getProduct());
        }
    }
}
