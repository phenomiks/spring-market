package ru.geekbrains.springmarket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.springmarket.entities.Cart;
import ru.geekbrains.springmarket.entities.User;
import ru.geekbrains.springmarket.services.CartService;
import ru.geekbrains.springmarket.services.ProductService;
import ru.geekbrains.springmarket.services.UserService;

import java.security.Principal;

@RestController
@RequestMapping(value = "/api/v1/cart")
public class CartController {
    private final CartService cartService;
    private final UserService userService;
    private final ProductService productService;

    @Autowired
    public CartController(CartService cartService, UserService userService, ProductService productService) {
        this.cartService = cartService;
        this.userService = userService;
        this.productService = productService;
    }

    @GetMapping
    public Cart getCart(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return cartService.findCartByOwnerId(user.getId());
    }

    @PostMapping
    public Cart updateCart(@RequestBody Cart cart) {
        return cartService.updateCart(cart);
    }

    @DeleteMapping
    public Cart clearCart(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return cartService.clearCart(user.getId());
    }
}
