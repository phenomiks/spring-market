package ru.geekbrains.springmarket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.springmarket.entities.Product;
import ru.geekbrains.springmarket.entities.dto.ProductDto;
import ru.geekbrains.springmarket.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    public Optional<ProductDto> findProductDtoById(Long id) {
        return productRepository.findById(id).map(ProductDto::new);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public List<ProductDto> findAllProductsDto() {
        return productRepository.findAll().stream().map(ProductDto::new).collect(Collectors.toList());
    }

    public Product saveOrUpdate(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
}
