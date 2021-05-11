package ru.geekbrains.springmarket.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.springmarket.entities.Product;
import ru.geekbrains.springmarket.entities.dto.ProductDto;
import ru.geekbrains.springmarket.exceptions.ProductNotFoundException;
import ru.geekbrains.springmarket.services.ProductService;
import ru.geekbrains.springmarket.repositories.specifications.ProductSpecifications;

@RestController
@RequestMapping(value = "/api/v1/products")
@Api("Controller for interacting with the product table in the database")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

//    @GetMapping
//    public List<ProductDto> getProducts() {
//        return productService.findAllProductsDto();
//    }

    @GetMapping(value = "/{id}")
    @ApiOperation("Getting a productDto by ID")
    public ProductDto getProductById(@PathVariable Long id) {
        return productService.findProductDtoById(id).orElseThrow(() -> new ProductNotFoundException
                ("No product found with id = " + id));
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation("Removing a product by ID")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Saving a new product in database")
    public Product saveProduct(@RequestBody Product product) {
        product.setId(null);
        return productService.saveOrUpdate(product);
    }

    @PutMapping
    @ApiOperation("Updating product fields in database")
    public void updateProduct(@ModelAttribute Product product) {
        productService.saveOrUpdate(product);
    }

    @GetMapping
    @ApiOperation("Paginal retrieval of the product by its ID")
    public Page<ProductDto> getAllProducts(
            @RequestParam MultiValueMap<String, String> params,
            @RequestParam(name = "p", defaultValue = "1") Integer page
    ) {
        if (page < 1) {
            page = 1;
        }
        return productService.findAllProductsWithPages(ProductSpecifications.build(params), page, 10);
    }
}
