package ru.geekbrains.springmarket.entities.dto;

import lombok.Data;
import ru.geekbrains.springmarket.entities.Cart;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CartDto {
    private List<CartItemDto> items;
    private BigDecimal totalPrice;

    public CartDto(Cart cart) {
        this.totalPrice = cart.getTotalPrice();
        this.items = cart.getCartItems().stream().map(CartItemDto::new).collect(Collectors.toList());
    }
}
