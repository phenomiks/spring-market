package ru.geekbrains.springmarket;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import ru.geekbrains.springmarket.entities.Cart;
import ru.geekbrains.springmarket.entities.CartItem;
import ru.geekbrains.springmarket.entities.Product;
import ru.geekbrains.springmarket.services.CartService;
import ru.geekbrains.springmarket.services.ProductService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CartControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    private Cart cart;

    @BeforeEach
    public void init() {
        cart = cartService.findCartByOwnerId(2L);
        List<Product> products = productService.findAllProducts();
        List<CartItem> list = new ArrayList<>();
        list.add(new CartItem(products.get(0)));
        cart.setCartItems(list);
        cart.setTotalPrice(products.get(0).getPrice());
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void getCartTest() throws Exception {
        mvc.perform(get("/api/v1/cart")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items").exists())
                .andExpect(jsonPath("$.totalPrice").exists());
    }

    @Test
    @WithMockUser(username = "manager", roles = "MANAGER")
    public void updateCartTest() throws Exception {
        mvc.perform(
                post("/api/v1/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cart))
            )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ownerId", is(2)))
                .andExpect(jsonPath("$.cartItems[0].title", is("Adobe Photoshop Elements 2021")))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void clearCartTest() throws Exception {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("user");

        mvc.perform(
                delete("/api/v1/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPrice", is(0.0)))
                .andDo(MockMvcResultHandlers.print());
    }
}
