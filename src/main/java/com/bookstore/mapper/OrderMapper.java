package com.bookstore.mapper;

import com.bookstore.config.MapperConfig;
import com.bookstore.dto.order.OrderDto;
import com.bookstore.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    OrderDto toDto(Order order);
}
