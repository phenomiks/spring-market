package ru.geekbrains.springmarket.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.springmarket.entities.Product;
import ru.geekbrains.springmarket.repositories.ProductRepository;
import ru.geekbrains.springmarket.soap.products.SoapProduct;

import java.util.List;
import java.util.Optional;

@Service
public class SoapProductService {
    private final ProductRepository productRepository;

    @Autowired
    public SoapProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> findProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public SoapProduct convertToSoapProduct(Product product) {
        SoapProduct soapProduct = new SoapProduct();
        soapProduct.setId(product.getId());
        soapProduct.setTitle(product.getTitle());
        soapProduct.setPrice(product.getPrice());
        return soapProduct;
    }
}
