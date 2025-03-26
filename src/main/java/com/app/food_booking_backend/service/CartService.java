package com.app.food_booking_backend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.food_booking_backend.model.entity.Cart;
import com.app.food_booking_backend.model.entity.CartItem;
import com.app.food_booking_backend.model.entity.Food;
import com.app.food_booking_backend.model.entity.User;
import com.app.food_booking_backend.repository.CartItemRepository;
import com.app.food_booking_backend.repository.CartRepository;
import com.app.food_booking_backend.repository.FoodRepository;
import com.app.food_booking_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;

    public Cart getCartByUser(String userUuid) {
        User user = userRepository.findById(userUuid)
            .orElseThrow(() -> new RuntimeException("User not found"));

        return cartRepository.findByUser(user);
    }

    @Transactional
    public void addToCart(String userUuid, String foodUuid, int quantity) {
        User user = userRepository.findById(userUuid)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Food food = foodRepository.findById(foodUuid)
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
    public void updateCartItem(String cartItemId, int quantity) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
            .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (quantity <= 0) {
            cartItemRepository.delete(cartItem);
        } else {
            cartItem.setQuantity(quantity);
            cartItemRepository.save(cartItem);
        }
    }

    @Transactional
    public void removeCartItem(String cartItemId) {
        if (!cartItemRepository.existsById(cartItemId)) {
            throw new RuntimeException("Cart item not found");
        }
        cartItemRepository.deleteById(cartItemId);
    }

    @Transactional
    public void clearCart(String userUuid) {
        Cart cart = getCartByUser(userUuid);
        if (cart != null) {
            cartItemRepository.deleteAll(cart.getItems());
            cart.getItems().clear();
            cartRepository.save(cart);
        }
    }
}