package ru.geekbrains.springmarket.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.springmarket.entities.Cart;
import ru.geekbrains.springmarket.entities.User;
import ru.geekbrains.springmarket.entities.dto.CartDto;
import ru.geekbrains.springmarket.services.CartService;
import ru.geekbrains.springmarket.services.ProductService;
import ru.geekbrains.springmarket.services.UserService;

import java.security.Principal;

@RestController
@RequestMapping(value = "/api/v1/cart")
@CrossOrigin("*")
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
    public CartDto getCart(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Cart cart = cartService.findCartByOwnerId(user.getId());
        return new CartDto(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProductToCart(Principal principal, @RequestParam(name = "product_id") Long productId) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        cartService.addToCart(user.getId(), productId);
        return ResponseEntity.ok("");
    }

    @PostMapping
    public Cart updateCart(@RequestBody Cart cart) {
        return cartService.updateCart(cart);
    }

    @DeleteMapping
    public CartDto clearCart(Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Cart cart = cartService.clearCart(user.getId());
        return new CartDto(cart);
    }
}
