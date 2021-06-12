package ru.geekbrains.springmarket.entities.dto;

import lombok.Data;
import ru.geekbrains.springmarket.entities.CartItem;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private Long productId;
    private String productTitle;
    private int quantity;
    private BigDecimal pricePerProduct;
    private BigDecimal total_price;

    public CartItemDto(CartItem cartItem) {
        this.productId = cartItem.getProductId();
        this.productTitle = cartItem.getTitle();
        this.quantity = cartItem.getQuantity();
        this.pricePerProduct = cartItem.getPricePerProduct();
        this.total_price = pricePerProduct.multiply(new BigDecimal(quantity));
    }
}
