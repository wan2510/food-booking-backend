package com.app.food_booking_backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.food_booking_backend.model.dto.CartDTO;
import com.app.food_booking_backend.model.dto.CartItemDTO;
import com.app.food_booking_backend.model.dto.FoodDTO;
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

    public CartDTO getCartByUser(String userUuid) {
        User user = userRepository.findById(userUuid)
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
        Cart cart = cartRepository.findByUser(userRepository.findById(userUuid)
            .orElseThrow(() -> new RuntimeException("User not found")));
            
        if (cart != null) {
            cartItemRepository.deleteAll(cart.getItems());
            cart.getItems().clear();
            cartRepository.save(cart);
        }
    }

    private CartDTO toCartDTO(Cart cart) {
        List<CartItemDTO> items = cart.getItems().stream()
            .map(this::toCartItemDTO)
            .collect(Collectors.toList());

        double totalAmount = items.stream()
            .mapToDouble(item -> item.getFood().getPrice().doubleValue() * item.getQuantity())
            .sum();

        return CartDTO.builder()
            .uuid(cart.getUuid())
            .userUuid(cart.getUser().getUuid())
            .items(items)
            .totalAmount(totalAmount)
            .createdAt(cart.getCreatedAt())
            .updatedAt(cart.getUpdatedAt())
            .build();
    }

    private CartItemDTO toCartItemDTO(CartItem cartItem) {
        return CartItemDTO.builder()
            .uuid(cartItem.getUuid())
            .food(toFoodDTO(cartItem.getFood()))
            .quantity(cartItem.getQuantity())
            .createdAt(cartItem.getCreatedAt())
            .updatedAt(cartItem.getUpdatedAt())
            .build();
    }

    private FoodDTO toFoodDTO(Food food) {
        return FoodDTO.builder()
            .uuid(food.getUuid())
            .name(food.getName())
            .description(food.getDescription())
            .imageUrl(food.getImageUrl())
            .price(food.getPrice())
            .status(food.getStatus())
            .categoryName(food.getCategory().getName())
            .build();
    }
}