package ru.geekbrains.springmarket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.springmarket.entities.Product;
import ru.geekbrains.springmarket.entities.dto.ProductDto;
import ru.geekbrains.springmarket.exceptions.ProductNotFoundException;
import ru.geekbrains.springmarket.services.ProductService;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductDto> getProduct() {
        return productService.findAllProductsDto();
    }

    @GetMapping(value = "/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productService.findProductDtoById(id).orElseThrow(() -> new ProductNotFoundException
                ("No product found with id = " + id));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteProductById(@PathVariable Long id) {
        try {
            productService.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException("No product found with id = " + id);
        }
    }

    @PostMapping
    public Product saveProduct(@RequestBody Product product) {
        product.setId(null);
        return productService.saveOrUpdate(product);
    }

    @PutMapping
    public void updateProduct(@ModelAttribute Product product) {
        productService.saveOrUpdate(product);
    }
}
