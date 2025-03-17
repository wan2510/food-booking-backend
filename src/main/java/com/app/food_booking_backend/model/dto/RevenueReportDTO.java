package com.app.food_booking_backend.model.dto;

import com.app.food_booking_backend.model.entity.OrderItem;
import lombok.Data;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class RevenueReportDTO {
    private BigDecimal totalRevenue;
    private Map<String, Integer> orderStats;
    private List<OrderDetailDTO> orders;

    @Data
    @Builder
    public static class OrderDetailDTO {
        private String uuid;
        private LocalDateTime createdAt;
        private BigDecimal totalPrice;
        private String status;
        private List<OrderItemDTO> items;
    }

    @Data
    @Builder
    public static class OrderItemDTO {
        private String foodId;
        private String foodName;
        private String categoryId;
        private String categoryName;
        private Integer quantity;
        private BigDecimal price;

        public static OrderItemDTO fromEntity(OrderItem item) {
            return OrderItemDTO.builder()
                    .foodId(item.getFood().getUuid())
                    .foodName(item.getFood().getName())
                    .categoryId(item.getFood().getCategory().getUuid())
                    .categoryName(item.getFood().getCategory().getName())
                    .quantity(item.getQuantity())
                    .price(item.getPrice())
                    .build();
        }
    }
}
