package ru.geekbrains.springmarket.entities.dto;

import lombok.Data;
import ru.geekbrains.springmarket.entities.OrderItem;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long productId;
    private String productTitle;
    private Integer quantity;
    private BigDecimal pricePerProduct;
    private BigDecimal total_price;

    public OrderItemDto(OrderItem orderItem) {
        this.productId = orderItem.getProductId();
        this.productTitle = orderItem.getTitle();
        this.quantity = orderItem.getQuantity();
        this.pricePerProduct = orderItem.getPricePerProduct();
        this.total_price = pricePerProduct.multiply(new BigDecimal(quantity));
    }
}
