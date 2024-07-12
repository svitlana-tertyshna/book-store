package com.bookstore.service;

import com.bookstore.dto.order.CreateOrderRequestDto;
import com.bookstore.dto.order.OrderDto;
import com.bookstore.dto.order.OrderItemDto;
import com.bookstore.model.Order;
import com.bookstore.model.User;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderDto createOrder(User user, CreateOrderRequestDto requestDto);

    List<OrderDto> getAll(Long userId, Pageable pageable);

    OrderDto update(Long orderId, Order.Status status);

    List<OrderItemDto> getAllOrderItemsByIdAngUserId(Long userId, Long orderId);

    OrderItemDto getOrderItemById(Long id, Long orderId, Long userId);
}
