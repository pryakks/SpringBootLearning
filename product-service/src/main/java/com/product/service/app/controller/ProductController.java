package com.product.service.app.controller;

import com.product.service.app.dto.Coupon;
import com.product.service.app.model.Product;
import com.product.service.app.model.ProductResponse;
import com.product.service.app.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/productapi")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${couponservice.url}")
    private String couponServiceURL;

    @Autowired
    ProductResponse productResponse;

    @PostMapping("/products")
    public Product createProduct(@RequestBody Product product) {
        Coupon coupon = restTemplate.getForObject(couponServiceURL+product.getCouponCode(), Coupon.class);
        product.setPrice(product.getPrice().subtract(coupon.getDiscount()));
        return productRepository.save(product);
    }

    @GetMapping("/products")
    public ProductResponse getProductDetailsByName(@RequestParam String name){
        Product product = productRepository.findByName(name);
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        return productResponse;
    }

    @GetMapping("/products/{Id}")
    public ProductResponse getProductDetailsById(@PathVariable Long Id){
        Product product = productRepository.findById(Id).get();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        return productResponse;
    }
}
