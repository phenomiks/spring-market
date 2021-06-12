package ru.geekbrains.springmarket.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.geekbrains.springmarket.entities.Cart;
import ru.geekbrains.springmarket.entities.CartItem;
import ru.geekbrains.springmarket.exceptions.ProductNotFoundException;
import ru.geekbrains.springmarket.repositories.CartRepository;

import java.math.BigDecimal;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductService productService;

    @Autowired
    public CartService(CartRepository cartRepository, ProductService productService) {
        this.cartRepository = cartRepository;
        this.productService = productService;
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

    public void addToCart(Long userId, Long productId) {
        Cart cart = findCartByOwnerId(userId);
        for (CartItem cartItem : cart.getCartItems()) {
            if (cartItem.getProductId().equals(productId)) {
                cartItem.setQuantity(cartItem.getQuantity() + 1);
                recalculateCart(cart);
                cartRepository.save(cart);
                return;
            }
        }
        CartItem item = new CartItem(productService.findProductById(productId).orElseThrow(() -> new ProductNotFoundException
                ("No product found with id = " + productId)));
        cart.getCartItems().add(item);
        recalculateCart(cart);
        cartRepository.save(cart);
    }
}
