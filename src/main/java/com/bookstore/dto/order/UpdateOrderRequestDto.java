package com.bookstore.dto.order;

import com.bookstore.model.Order;
import lombok.Data;

@Data
public class UpdateOrderRequestDto {
    private Order.Status status;
}
