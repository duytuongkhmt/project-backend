package project.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import project.common.Constant;
import project.model.entity.Order;
import project.model.data.ShowTopReport;
import project.payload.request.user.OrderCreateRequest;
import project.payload.request.user.OrderRequest;
import project.payload.request.user.OrderUpdateRequest;
import project.repository.OrderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.data.jpa.domain.Specification.where;
import static project.repository.spec.OrderSpecification.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final EntityManager entityManager;

    public Page<Order> getByFilter(OrderRequest request, PageRequest pageRequest) {
        return orderRepository.findAll(where(
                checkTimeFrom(request.getFrom().atStartOfDay())
                        .and(checkTimeTo(request.getTo().atStartOfDay()))
                        .and(statusIs(request.getStatus()))
                        .and(artistIdIn(request.getArtistIds()))
                        .and(bookerIdIn(request.getBookerIds()))
        ), pageRequest);
    }

    public List<Order> getByFilter(OrderRequest request) {
        return orderRepository.findAll(where(
                checkTimeFrom(request.getFrom().atStartOfDay())
                        .and(checkTimeTo(request.getTo().atStartOfDay()))
                        .and(statusIs(request.getStatus()))
                        .and(artistIdIn(request.getArtistIds()))
                        .and(bookerIdIn(request.getBookerIds()))
        ));
    }


    public List<Order> getConfirmedOrderByAristId(String id) {
        return orderRepository.findAll(where(
                checkTimeFrom(LocalDateTime.now())
                        .and(artistIdIn(List.of(id)))
                        .and(statusIn(List.of(Order.STATUS.CONFIRMED, Order.STATUS.SUCCESS)))
        ));
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
                checkRequestBoundaryWithinRange(request.getFrom(), request.getTo())
                        .and(artistIdIn(List.of(request.getArtistId())))
                        .and(statusIs(Order.STATUS.CONFIRMED)))
        );
        return orders.isEmpty();
    }

    public Boolean checkAvailableChangeStatus(String id) {
        Order order = getById(id);
//        OrderCreateRequest request = new OrderCreateRequest();
//        BeanUtils.copyProperties(order, request);
        return !Objects.equals(order.getStatus(), Order.STATUS.CONFIRMED);
    }

    public Boolean checkAvailable(OrderUpdateRequest request) {
        List<Order> orders = orderRepository.findAll(where(
                checkRequestBoundaryWithinRange(request.getFrom(), request.getTo())
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
        List<Order> updatedSuccessOrders = orders.stream()
                .filter(order -> Objects.equals(order.getStatus(), Order.STATUS.CONFIRMED))
                .peek(order -> order.setStatus(Order.STATUS.SUCCESS))
                .toList();

        List<Order> updatedCancelOrders = orders.stream()
                .filter(order -> Objects.equals(order.getStatus(), Order.STATUS.PENDING))
                .peek(order -> order.setStatus(Order.STATUS.CANCEL))
                .toList();

        List<Order> updatedOrders = new ArrayList<>(updatedSuccessOrders);
        updatedOrders.addAll(updatedCancelOrders);

        if (!updatedOrders.isEmpty()) {
            orderRepository.saveAll(updatedOrders);
        }
    }

    public List<ShowTopReport> getShowTopRevenueReport(OrderRequest request) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ShowTopReport> cq = cb.createQuery(ShowTopReport.class);
        Root<Order> root = cq.from(Order.class);

        List<Selection<?>> selections = new ArrayList<>();
        List<Predicate> predicates = new ArrayList<>();
        List<Expression<?>> groupByExpressions = new ArrayList<>();

        Expression<java.sql.Date> dayOnly = cb.function(
                "DATE", java.sql.Date.class, root.get(Constant.COLUMN.FROM)
        );

        selections.add(dayOnly.alias("key"));
        groupByExpressions.add(dayOnly);

        selections.add(cb.sum(root.get(Constant.COLUMN.PRICE)).alias("value"));

        predicates.add(checkTimeFrom(request.getFrom().atStartOfDay()).toPredicate(root, cq, cb));
        predicates.add(checkTimeTo(request.getTo().atStartOfDay()).toPredicate(root, cq, cb));
        predicates.add(statusIs(Order.STATUS.SUCCESS).toPredicate(root, cq, cb));

        cq.multiselect(selections);
        cq.where(predicates.stream().filter(Objects::nonNull).toList().toArray(new Predicate[0]));
        cq.groupBy(groupByExpressions);

        // Tạo và thực thi truy vấn
        TypedQuery<ShowTopReport> query = entityManager.createQuery(cq);
        return query.getResultList();
    }

}
