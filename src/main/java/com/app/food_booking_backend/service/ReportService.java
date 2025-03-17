package com.app.food_booking_backend.service;

import com.app.food_booking_backend.model.dto.RevenueReportDTO;
import com.app.food_booking_backend.model.dto.RevenueRequestDTO;
import com.app.food_booking_backend.model.entity.Order;
import com.app.food_booking_backend.model.entity.OrderItem;
import com.app.food_booking_backend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final OrderRepository orderRepository;

    public RevenueReportDTO generateRevenueReport(RevenueRequestDTO request) {
        // Chuyển đổi LocalDate sang LocalDateTime để query
        LocalDateTime startDateTime = request.getStartDate().atStartOfDay();
        LocalDateTime endDateTime = request.getEndDate().plusDays(1).atStartOfDay();

        // Lấy danh sách đơn hàng trong khoảng thời gian
        List<Order> orders = orderRepository.findByCreatedAtBetween(startDateTime, endDateTime);

        // Lọc theo trạng thái nếu không phải 'all'
        if (!"all".equals(request.getStatus())) {
            orders = orders.stream()
                    .filter(order -> order.getStatus().toString().equals(request.getStatus()))
                    .collect(Collectors.toList());
        }

        // Tính tổng doanh thu
        BigDecimal totalRevenue = orders.stream()
                .map(Order::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Thống kê số lượng đơn hàng theo trạng thái
        Map<String, Integer> orderStats = calculateOrderStats(orders);

        // Chuyển đổi orders sang DTO
        List<RevenueReportDTO.OrderDetailDTO> orderDTOs = orders.stream()
                .map(this::convertToOrderDetailDTO)
                .collect(Collectors.toList());

        // Tạo báo cáo
        return RevenueReportDTO.builder()
                .totalRevenue(totalRevenue)
                .orderStats(orderStats)
                .orders(orderDTOs)
                .build();
    }

    private Map<String, Integer> calculateOrderStats(List<Order> orders) {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("COMPLETED", 0);
        stats.put("PENDING", 0);
        stats.put("CANCELLED", 0);

        orders.forEach(order -> {
            String status = order.getStatus().toString();
            stats.put(status, stats.get(status) + 1);
        });

        return stats;
    }

    private RevenueReportDTO.OrderDetailDTO convertToOrderDetailDTO(Order order) {
        List<RevenueReportDTO.OrderItemDTO> itemDTOs = order.getItems().stream()
                .map(RevenueReportDTO.OrderItemDTO::fromEntity)
                .collect(Collectors.toList());

        return RevenueReportDTO.OrderDetailDTO.builder()
                .uuid(order.getUuid())
                .createdAt(order.getCreatedAt())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus().toString())
                .items(itemDTOs)
                .build();
    }
}
