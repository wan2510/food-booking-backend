package com.app.food_booking_backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.food_booking_backend.model.entity.*;
import com.app.food_booking_backend.repository.CartItemRepository;
import com.app.food_booking_backend.repository.CartRepository;
import com.app.food_booking_backend.repository.FoodRepository;
import com.app.food_booking_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;

    public Cart getCartByUser(Long userId) {
        User user = userRepository.findById(userId.toString())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            return CartDTO.builder()
                .userUuid(userUuid)
                .items(java.util.Collections.emptyList())
                .totalAmount(0)
                .build();
        }

        return toCartDTO(cart);
    }

    @Transactional
    public void addToCart(Long userId, Long foodId, int quantity) {
        User user = userRepository.findById(userId.toString())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Food food = foodRepository.findById(foodId.toString())
                .orElseThrow(() -> new RuntimeException("Food not found"));

        Cart cart = cartRepository.findByUser(user);
        if (cart == null) {
            cart = new Cart(user);
            cartRepository.save(cart);
        }

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getFood().equals(food))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            CartItem cartItem = new CartItem(cart, food, quantity);
            cartItemRepository.save(cartItem);
            cart.getItems().add(cartItem);
        }

        cartRepository.save(cart);
    }

    @Transactional
    public void updateCartItem(Long cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId.toString())
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (quantity <= 0) {
            cartItemRepository.delete(cartItem);
        } else {
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }
    }

    @Transactional
    public void removeCartItem(Long cartItemId) {
        if (!cartItemRepository.existsById(cartItemId.toString())) {
            throw new RuntimeException("Cart item not found");
        }
        cartItemRepository.deleteById(cartItemId.toString());
    }

    @Transactional
    public void clearCart(Long userId) {
        Cart cart = getCartByUser(userId);
        if (cart != null) {
            cartItemRepository.deleteAll(cart.getItems());
            cart.getItems().clear();
            cartRepository.save(cart);
        }
    }
}
