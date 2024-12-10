package project.business;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import project.model.Order;
import project.payload.request.user.OrderCreateRequest;
import project.payload.request.user.OrderRequest;
import project.payload.request.user.OrderUpdateRequest;
import project.resource.OrderResource;
import project.service.OrderService;
import project.service.UserService;


@Component
@RequiredArgsConstructor
public class OrderBusiness {
    private final OrderService orderService;

    public Page<OrderResource> getOrderResources(OrderRequest request, PageRequest pageRequest) {
        Page<Order> orders = orderService.getByFilter(request, pageRequest);
        return orders.map(order -> {
            OrderResource orderResource = new OrderResource();
            BeanUtils.copyProperties(order, orderResource);
            return orderResource;
        });
    }

    public OrderResource getById(String id) {
        Order order = orderService.findById(id);
        OrderResource orderResource = new OrderResource();
        BeanUtils.copyProperties(order, orderResource);
        return orderResource;
    }

    public void deleteById(String id) {
        orderService.deleteById(id);
    }

    public OrderResource create(OrderCreateRequest request) {
        Order order = new Order();
        BeanUtils.copyProperties(request, order);

        order = orderService.save(order);
        OrderResource orderResource = new OrderResource();
        BeanUtils.copyProperties(order, orderResource);
        return orderResource;

    }
    public OrderResource update(OrderUpdateRequest request) {
        Order order = orderService.findById(request.getId());
        BeanUtils.copyProperties(request, order);

        order = orderService.save(order);
        OrderResource orderResource = new OrderResource();
        BeanUtils.copyProperties(order, orderResource);
        return orderResource;

    }
}
