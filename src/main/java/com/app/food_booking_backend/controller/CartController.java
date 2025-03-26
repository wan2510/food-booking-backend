package com.app.food_booking_backend.controller;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.food_booking_backend.model.dto.CartDTO;
import com.app.food_booking_backend.model.dto.CartRequestDTO;
import com.app.food_booking_backend.model.dto.CartUpdateRequestDTO;
import com.app.food_booking_backend.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@RequestBody CartRequestDTO request) {
        try {
            cartService.addToCart(Long.parseLong(request.getUserUuid()), Long.parseLong(request.getFoodUuid()), request.getQuantity());
            return ResponseEntity.ok().body("Item added to cart successfully");
        } catch (Exception e) {
            logger.error("Error adding item to cart: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @GetMapping("/{userUuid}")
    public ResponseEntity<?> getCart(@PathVariable String userUuid) {
        Cart cart = cartService.getCartByUser(Long.parseLong(userUuid));
        if (cart == null || cart.getItems().isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCart(@RequestBody CartUpdateRequestDTO request) {
        try {
            cartService.updateCartItem(Long.parseLong(request.getCartItemId()), request.getQuantity());
            return ResponseEntity.ok().body("Cart item updated successfully");
        } catch (Exception e) {
            logger.error("Error updating cart item: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<?> removeCartItem(@PathVariable String cartItemId) {
        try {
            cartService.removeCartItem(Long.parseLong(cartItemId));
            return ResponseEntity.ok().body("Cart item removed successfully");
        } catch (Exception e) {
            logger.error("Error removing cart item: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @DeleteMapping("/clear/{userUuid}")
    public ResponseEntity<Void> clearCart(@PathVariable String userUuid) {
        cartService.clearCart(Long.parseLong(userUuid));
        return ResponseEntity.ok().build();
    }
}
