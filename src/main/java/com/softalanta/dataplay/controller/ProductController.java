package com.softalanta.dataplay.controller;

import com.softalanta.dataplay.domain.Product;
import com.softalanta.dataplay.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductPageRepository productPageRepository;

    @GetMapping("/products")
    public List<Product> getAll(){
        return productRepository.findAll();
    }

    @GetMapping("/products/paginated")
    public List<Product> getPage(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(value = "search", defaultValue = "") String search) {
        System.out.println(search);
        SearchSpecificationBuilder<Product> builder = new SearchSpecificationBuilder<>(search);
        Specification<Product> spec = builder.build();
        return productPageRepository.findAll(spec, PageRequest.of(page,size, Sort.by(Sort.Direction.ASC,"name"))).getContent();
    }

    @GetMapping("/products/{name}")
    public Product getByName(@PathVariable String name){
        return productRepository.findByName(name);
    }

    @PostMapping("/products")
    public Product save(@RequestBody Product product){
        System.out.println(product);
        return productRepository.save(product);
    }
}
