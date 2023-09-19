package com.product.controller;

import com.product.entity.Product;
import com.product.service.ProductQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductQueryController {

    @Autowired
    ProductQueryService service;

    @GetMapping
    public List<Product> getProducts(){
        return service.getProducts();
    }

    @GetMapping("/{id}")
    public  Product getProduct(@PathVariable Long id){
        return service.getProductById(id);
    }
}
