package com.app.food_booking_backend.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.app.food_booking_backend.model.dto.OrderDTO;
import com.app.food_booking_backend.model.dto.OrderItemDTO;
import com.app.food_booking_backend.model.entity.Food;
import com.app.food_booking_backend.model.entity.Order;
import com.app.food_booking_backend.model.entity.OrderItem;
import com.app.food_booking_backend.model.entity.User;
import com.app.food_booking_backend.model.entity.enums.OrderStatus;
import com.app.food_booking_backend.repository.FoodRepository;
import com.app.food_booking_backend.repository.OrderRepository;
import com.app.food_booking_backend.repository.UserRepository;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;
    private final ModelMapper modelMapper;

    public OrderService(OrderRepository orderRepository,
                        UserRepository userRepository,
                        FoodRepository foodRepository,
                        ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.foodRepository = foodRepository;
        this.modelMapper = modelMapper;
    }

    public List<OrderDTO> getOrdersByUser(String userUuid) {
        User user = userRepository.findByUuid(userUuid);
        if (user == null) {
            throw new RuntimeException("User không tồn tại với uuid: " + userUuid);
        }
        return orderRepository.findByUser(user)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderByUuid(String uuid) {
        Order order = orderRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Order không tồn tại với uuid: " + uuid));
        return convertToDTO(order);
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {
        User user = userRepository.findByUuid(orderDTO.getUserUuid());
        if (user == null) {
            throw new RuntimeException("User không tồn tại với uuid: " + orderDTO.getUserUuid());
        }

        Order order = new Order();
        order.setUuid(orderDTO.getUuid());
        order.setUser(user);
        order.setStatus(orderDTO.getStatus() != null ? orderDTO.getStatus() : OrderStatus.PENDING);

        BigDecimal totalPrice = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();
        if (orderDTO.getItems() != null) {
            for (OrderItemDTO itemDTO : orderDTO.getItems()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setUuid(itemDTO.getUuid());
                orderItem.setOrder(order);

                Food food = foodRepository.findByUuid(itemDTO.getFoodUuid())
                        .orElseThrow(() -> new RuntimeException("Food không tồn tại: " + itemDTO.getFoodUuid()));
                orderItem.setFood(food);

                orderItem.setQuantity(itemDTO.getQuantity());
                orderItem.setPrice(itemDTO.getPrice());

                BigDecimal itemTotal = itemDTO.getPrice().multiply(BigDecimal.valueOf(itemDTO.getQuantity()));
                totalPrice = totalPrice.add(itemTotal);

                orderItems.add(orderItem);
            }
        }
        order.setItems(orderItems);
        order.setTotalPrice(totalPrice);

        Order savedOrder = orderRepository.save(order);
        return convertToDTO(savedOrder);
    }

    public OrderDTO updateOrderStatus(String uuid, String statusStr) {
        Order order = orderRepository.findByUuid(uuid)
                .orElseThrow(() -> new RuntimeException("Order không tồn tại với uuid: " + uuid));
        OrderStatus status = OrderStatus.valueOf(statusStr);
        order.setStatus(status);
        Order updatedOrder = orderRepository.save(order);
        return convertToDTO(updatedOrder);
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = modelMapper.map(order, OrderDTO.class);
        dto.setUserUuid(order.getUser().getUuid());
        if (order.getItems() != null) {
            dto.setItems(order.getItems().stream().map(item -> {
                OrderItemDTO itemDTO = new OrderItemDTO();
                itemDTO.setUuid(item.getUuid());
                itemDTO.setFoodUuid(item.getFood().getUuid());
                itemDTO.setFoodName(item.getFood().getName());
                itemDTO.setQuantity(item.getQuantity());
                itemDTO.setPrice(item.getPrice());
                return itemDTO;
            }).collect(Collectors.toList()));
        }
        return dto;
    }
}
