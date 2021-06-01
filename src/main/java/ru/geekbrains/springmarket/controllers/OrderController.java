package ru.geekbrains.springmarket.controllers;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.springmarket.entities.Order;
import ru.geekbrains.springmarket.entities.User;
import ru.geekbrains.springmarket.entities.dto.OrderDto;
import ru.geekbrains.springmarket.exceptions.OrderNotFoundException;
import ru.geekbrains.springmarket.services.CartService;
import ru.geekbrains.springmarket.services.OrderService;
import ru.geekbrains.springmarket.services.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/orders")
public class OrderController {
    public static final String REQUEST_QUEUE_NAME = "requestQueue";

    private final OrderService orderService;
    private final UserService userService;
    private final CartService cartService;

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public OrderController(OrderService orderService, UserService userService, CartService cartService) {
        this.orderService = orderService;
        this.userService = userService;
        this.cartService = cartService;
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrderFromCart(Principal principal, @RequestParam String address) {
        Order order = orderService.createFromUserCart(principal.getName(), address);
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        cartService.clearCart(user.getId());
        OrderDto orderDto = new OrderDto(order);
        send(orderDto);
        return orderDto;
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable Long id) {
        Order order = orderService.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found"));
        return new OrderDto(order);
    }

    @GetMapping
    public List<OrderDto> getUserOrders(Principal principal) {
        return orderService.findAllByOwner(principal.getName()).stream().map(OrderDto::new).collect(Collectors.toList());
    }

    private void send(OrderDto orderDto) {
        rabbitTemplate.convertAndSend(REQUEST_QUEUE_NAME, orderDto);
    }
}
