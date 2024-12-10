package project.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.model.Order;
import project.payload.request.user.OrderRequest;
import project.repository.OrderRepository;

import java.util.Optional;

import static org.springframework.data.jpa.domain.Specification.where;
import static project.repository.spec.OrderSpecification.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public Page<Order> getByFilter(OrderRequest request, PageRequest pageRequest) {
        return orderRepository.findAll(where(
                dateBetween(request.getFrom(), request.getTo())
                        .and(statusIs(request.getStatus()))
                        .and(artistIdIn(request.getArtistIds()))
                        .and(bookerIdIn(request.getBookerIds()))
        ), pageRequest);
    }

    public void deleteById(String id) {
        Order order = getById(id);
        order.setStatus(Order.STATUS.DELETE);
        orderRepository.save(order);
    }

    public Order getById(String id) {
        Optional<Order> order = orderRepository.findById(id);
        return order.orElse(null);
    }

    public Order findById(String id) {
        return orderRepository.findByIdIs(id);
    }

    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
