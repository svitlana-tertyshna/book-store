package com.bookstore.controller;

import com.bookstore.dto.order.CreateOrderRequestDto;
import com.bookstore.dto.order.OrderDto;
import com.bookstore.dto.order.OrderItemDto;
import com.bookstore.dto.order.UpdateOrderRequestDto;
import com.bookstore.model.User;
import com.bookstore.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Order management", description = "Endpoints for managing orders")
@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Create a new order",
            description = "Creating a new order from a shipping cart")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(Authentication authentication,
                                @RequestBody CreateOrderRequestDto requestDto) {
        User user = (User) authentication.getPrincipal();
        return orderService.createOrder(user, requestDto);
    }

    @GetMapping
    @Operation(summary = "Get orders history",
            description = "Get all info about previous orders")
    @ApiResponse(responseCode = "200", description = "Order information",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = OrderDto.class))})
    public List<OrderDto> getAll(Authentication authentication, Pageable pageable) {
        User user = (User) authentication.getPrincipal();
        return orderService.getAll(user.getId(), pageable);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update an order by id",
            description = "Update an order by id with given status (Admin only)")
    @ApiResponse(responseCode = "200", description = "Updated",
            content = {@Content(mediaType = "application/json")})
    public OrderDto update(@PathVariable Long id,
                           @RequestBody UpdateOrderRequestDto requestDto) {
        return orderService.update(id, requestDto.getStatus());
    }

    @GetMapping("/{id}/items")
    @Operation(summary = "Get all order items",
            description = "Get info about the order items by order id")
    @ApiResponse(responseCode = "200", description = "Order information",
            content = {@Content(mediaType = "application/json")})
    public List<OrderItemDto> getAllItemsById(Authentication authentication,
                                              @PathVariable Long id) {
        User user = (User) authentication.getPrincipal();
        return orderService.getAllOrderItemsByIdAngUserId(id, user.getId());
    }

    @GetMapping("/{orderId}/items/{id}")
    @Operation(summary = "Get an order item",
            description = "Get an order item by item id and order id")
    @ApiResponse(responseCode = "200", description = "Order item information",
            content = {@Content(mediaType = "application/json")})
    public OrderItemDto getItemById(Authentication authentication,
                                    @PathVariable Long orderId,
                                    @PathVariable Long id) {
        User user = (User) authentication.getPrincipal();
        return orderService.getOrderItemById(id, orderId, user.getId());
    }
}
