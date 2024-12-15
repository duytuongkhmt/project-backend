package project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.model.Order;
import project.payload.request.user.OrderCreateRequest;
import project.payload.request.user.OrderRequest;
import project.payload.request.user.OrderUpdateRequest;
import project.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.domain.Specification.where;
import static project.repository.spec.OrderSpecification.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Page<Order> getByFilter(OrderRequest request, PageRequest pageRequest) {
        return orderRepository.findAll(where(
                checkTimeFrom(request.getFrom().atStartOfDay())
                        .and(checkTimeTo(request.getTo().atStartOfDay()))
                        .and(statusIs(request.getStatus()))
                        .and(artistIdIn(request.getArtistIds()))
                        .and(bookerIdIn(request.getBookerIds()))
        ), pageRequest);
    }

    public void deleteById(String id) {
        Order order = getById(id);
        if (Order.STATUS.SUCCESS.equals(order.getStatus())) {
            throw new IllegalArgumentException("Order cannot be deleted: request is invalid or resources are unavailable.");
        }
        order.setStatus(Order.STATUS.DELETED);
        orderRepository.save(order);
    }

    public Order getById(String id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElse(null);
    }

    public Order findById(String id) {
        return orderRepository.findByIdIs(id);
    }

    public Boolean checkAvailable(OrderCreateRequest request) {
        List<Order> orders = orderRepository.findAll(where(
                checkTimeFrom(request.getFrom())
                        .and(checkTimeTo(request.getTo()))
                        .and(artistIdIn(List.of(request.getArtistId())))
                        .and(statusIs(Order.STATUS.CONFIRMED)))
        );
        return orders.isEmpty();
    }

    public Boolean checkAvailableChangeStatus(String id) {
        Order order = getById(id);
        return !Objects.equals(order.getStatus(), Order.STATUS.CONFIRMED);
    }

    public Boolean checkAvailable(OrderUpdateRequest request) {
        List<Order> orders = orderRepository.findAll(where(
                checkTimeFrom(request.getFrom())
                        .and(checkTimeTo(request.getTo()))
                        .and(artistIdIn(List.of(request.getArtistId())))
                        .and(statusIs(Order.STATUS.CONFIRMED)))
        );
        return orders.isEmpty();
    }


    public Order save(Order order) {
        return orderRepository.save(order);
    }

    public void updateAllOrders() {
        List<Order> orders = orderRepository.findAll(where(
                checkTimeTo(LocalDateTime.now())
                        .and(statusIs(Order.STATUS.CONFIRMED)))
        );
        List<Order> updatedOrders = orders.stream()
                .filter(order -> Objects.equals(order.getStatus(), Order.STATUS.CONFIRMED))
                .peek(order -> order.setStatus(Order.STATUS.SUCCESS))
                .collect(Collectors.toList());

        // Lưu các đơn hàng đã cập nhật
        if (!updatedOrders.isEmpty()) {
            orderRepository.saveAll(updatedOrders);
        }
    }
}
