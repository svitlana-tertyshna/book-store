package com.bookstore.repository.order;

import com.bookstore.model.OrderItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    Optional<OrderItem> getOrderItemByIdAndOrderIdAndOrderUserId(
            Long id, Long orderId, Long userId);
}
