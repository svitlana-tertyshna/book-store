package com.bookstore.service.impl;

import com.bookstore.dto.order.CreateOrderRequestDto;
import com.bookstore.dto.order.OrderDto;
import com.bookstore.dto.order.OrderItemDto;
import com.bookstore.exception.EntityNotFoundException;
import com.bookstore.mapper.OrderItemMapper;
import com.bookstore.mapper.OrderMapper;
import com.bookstore.model.CartItem;
import com.bookstore.model.Order;
import com.bookstore.model.OrderItem;
import com.bookstore.model.ShoppingCart;
import com.bookstore.model.User;
import com.bookstore.repository.order.OrderItemRepository;
import com.bookstore.repository.order.OrderRepository;
import com.bookstore.repository.shoppingcart.ShoppingCartRepository;
import com.bookstore.service.OrderService;
import com.bookstore.service.ShoppingCartService;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderRepository orderRepository;
    private final ShoppingCartService shoppingCartService;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    @Override
    public OrderDto createOrder(User user, CreateOrderRequestDto requestDto) {
        Order order = new Order();
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartByUser(user)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cannot find shopping cart by user id = " + user.getId()));
        List<OrderItem> orderItems = shoppingCart.getCartItems().stream()
                .map(c -> convertCartItemToOrderItem(c, order))
                .toList();
        order.setOrderItems(orderItems);
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Order.Status.PENDING);

        BigDecimal total = orderItems.stream()
                .map(item ->
                        item.getBook().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotal(total);
        order.setShippingAddress(requestDto.getShippingAddress());
        Order saved = orderRepository.save(order);
        shoppingCartService.cleanShoppingCart(user.getId());
        return orderMapper.toDto(saved);
    }

    @Transactional
    @Override
    public List<OrderDto> getAll(Long userId, Pageable pageable) {
        return orderRepository.findAllByUserId(userId, pageable).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public OrderDto update(Long orderId, Order.Status status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find order by id = "
                        + orderId));
        order.setStatus(status);
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Transactional
    @Override
    public List<OrderItemDto> getAllOrderItemsByIdAngUserId(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Cannot find order by id = "
                        + orderId));
        return order.getOrderItems().stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemDto getOrderItemById(Long id, Long orderId, Long userId) {
        OrderItem orderItem = orderItemRepository
                .getOrderItemByIdAndOrderIdAndOrderUserId(id, orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cannot find order item by id = " + id));
        return orderItemMapper.toDto(orderItem);
    }

    private OrderItem convertCartItemToOrderItem(CartItem cartItem, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setBook(cartItem.getBook());
        orderItem.setQuantity(cartItem.getQuantity());
        orderItem.setPrice(cartItem.getBook().getPrice());
        orderItem.setOrder(order);
        return orderItem;
    }
}
