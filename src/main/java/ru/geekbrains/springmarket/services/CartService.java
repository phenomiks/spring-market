package ru.geekbrains.springmarket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.springmarket.entities.Cart;
import ru.geekbrains.springmarket.entities.CartItem;
import ru.geekbrains.springmarket.repositories.CartRepository;

import java.math.BigDecimal;

@Service
public class CartService {
    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart findCartByOwnerId(Long id) {
        Cart cart = cartRepository.findById(id).orElse(new Cart(id));
        recalculateCart(cart);
        return cart;
    }

    public Cart updateCart(Cart cart) {
        recalculateCart(cart);
        return cartRepository.save(cart);
    }

    public Cart clearCart(Long id) {
        Cart cart = findCartByOwnerId(id);
        cart.getCartItems().clear();
        cart.setTotalPrice(new BigDecimal("0.0"));
        return cartRepository.save(cart);
    }

    private void recalculateCart(Cart cart) {
        cart.setTotalPrice(new BigDecimal("0.0"));
        for (CartItem cartItem : cart.getCartItems()) {
            cart.setTotalPrice(cart.getTotalPrice().add(
                    cartItem.getPricePerProduct().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
            );
        }
    }
}
