package ru.geekbrains.springmarket.soap;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.geekbrains.springmarket.entities.Product;
import ru.geekbrains.springmarket.exceptions.ProductNotFoundException;
import ru.geekbrains.springmarket.soap.products.GetAllProductsRequest;
import ru.geekbrains.springmarket.soap.products.GetAllProductsResponse;
import ru.geekbrains.springmarket.soap.products.GetProductByIdRequest;
import ru.geekbrains.springmarket.soap.products.GetProductByIdResponse;

import java.util.Optional;

@Endpoint
@RequiredArgsConstructor
public class SoapProductController {
    private static final String NAMESPACE_URI = "http://www.geekbrains.ru/springmarket/ws/products";
    private final SoapProductService soapProductService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByIdRequest")
    @ResponsePayload
    public GetProductByIdResponse getProductById(@RequestPayload GetProductByIdRequest request) {
        GetProductByIdResponse response = new GetProductByIdResponse();
        Optional<Product> product = soapProductService.findProductById(request.getId());
        if (product.isPresent()) {
            response.setProduct(soapProductService.convertToSoapProduct(product.get()));
        } else {
            throw new ProductNotFoundException("No product found with id = " + request.getId());
        }
        return response;
    }

    /*
        Example SOAP request: POST http://localhost:8081/app/ws

        <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:f="http://www.geekbrains.ru/springmarket/ws/products">
            <soapenv:Header/>
            <soapenv:Body>
                <f:getProductByIdRequest>
                    <f:id>1</f:id>
                </f:getProductByIdRequest>
            </soapenv:Body>
        </soapenv:Envelope>
     */

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts() {
        GetAllProductsResponse response = new GetAllProductsResponse();
        soapProductService.findAllProducts().forEach(p -> response.getProducts().add(soapProductService.convertToSoapProduct(p)));
        return response;
    }
}
