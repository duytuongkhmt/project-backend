package project.business;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import project.mapper.OrderMapper;
import project.mapper.UserMapper;
import project.model.entity.Account;
import project.model.entity.Order;
import project.model.data.ShowTopReport;
import project.payload.request.user.OrderCreateRequest;
import project.payload.request.user.OrderRequest;
import project.payload.request.user.OrderUpdateRequest;
import project.resource.OrderResource;
import project.resource.ProfileResource;
import project.resource.ShowTopResource;
import project.service.OrderService;
import project.service.ProfileService;
import project.service.UserService;
import project.util.AuthUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class OrderBusiness {
    private final OrderService orderService;
    private final UserService userService;
    private final ProfileService profileService;


    public Page<OrderResource> getOrderResources(OrderRequest request, PageRequest pageRequest) {
        String userName = AuthUtils.getCurrentUsername();
        Account account = userService.findByUsername(userName);
        if ("artist".equals(request.getType())) {
            request.setArtistIds(List.of(account.getProfile().getId()));
        } else {
            request.setBookerIds(List.of(account.getProfile().getId()));
        }

        Page<Order> orders = orderService.getByFilter(request, pageRequest);
        List<String> bookerIds = orders.stream()
                .map(Order::getBookerId)
                .distinct()
                .toList();

        List<String> artistIds = orders.stream()
                .map(Order::getArtistId)
                .distinct()
                .toList();
        List<String> relatedIds = new ArrayList<>(bookerIds);
        relatedIds.addAll(artistIds);
        Map<String, ProfileResource> profileResourceMap = profileService.findByIds(relatedIds).stream()
                .map(UserMapper::map)
                .collect(Collectors.toMap(ProfileResource::getId, Function.identity()));
        return orders.map(order -> {
            ProfileResource booker = profileResourceMap.get(order.getBookerId());
            ProfileResource artist = profileResourceMap.get(order.getArtistId());
            return OrderMapper.map(order, booker, artist);
        });
    }

    public OrderResource getById(String id) {
        Order order = orderService.findById(id);
        OrderResource orderResource = new OrderResource();
        BeanUtils.copyProperties(order, orderResource);
        return orderResource;
    }

    public List<OrderResource> getScheduledOfArtist(String id) {
        List<Order> orders = orderService.getConfirmedOrderByAristId(id);
       return orders.stream().map(order->{
            OrderResource orderResource = new OrderResource();
            BeanUtils.copyProperties(order, orderResource);
            return orderResource;
        }).toList();
    }

    public void deleteById(String id) {
        orderService.deleteById(id);
    }

    public OrderResource create(OrderCreateRequest request) {
        String userName = AuthUtils.getCurrentUsername();
        Account account = userService.findByUsername(userName);
        Order order = new Order();
        BeanUtils.copyProperties(request, order);
        order.setBookerId(account.getProfile().getId());
        order = orderService.save(order);
        OrderResource orderResource = new OrderResource();
        BeanUtils.copyProperties(order, orderResource);
        return orderResource;

    }

    public Boolean checkAvailable(OrderCreateRequest request) {
        if (request.getFrom().isBefore(LocalDateTime.now())) {
            return false;
        }
        return orderService.checkAvailable(request);
    }

    public Boolean checkAvailable(OrderUpdateRequest request) {
        if (request.getFrom().isBefore(LocalDateTime.now())) {
            return false;
        }
        return orderService.checkAvailable(request);
    }

    public Boolean checkAvailable(String id) {
        return orderService.checkAvailableChangeStatus(id);
    }


    public OrderResource update(OrderUpdateRequest request) {
        Order order = orderService.findById(request.getId());
        BeanUtils.copyProperties(request, order);

        order = orderService.save(order);
        OrderResource orderResource = new OrderResource();
        BeanUtils.copyProperties(order, orderResource);
        return orderResource;

    }

    public OrderResource updateStatus(String id, String status, String reason) {
        Order order = orderService.findById(id);
        order.setStatus(status);
        order.setReasonReject(reason);
        order = orderService.save(order);
        OrderResource orderResource = new OrderResource();
        BeanUtils.copyProperties(order, orderResource);
        return orderResource;

    }

    public List<ShowTopResource> getShowTopReport(OrderRequest request) {
        String userName = AuthUtils.getCurrentUsername();
        Account account = userService.findByUsername(userName);
        request.setArtistIds(List.of(account.getProfile().getId()));
        List<ShowTopReport> showTopReports = orderService.getShowTopRevenueReport(request);


        return showTopReports.parallelStream().map(top -> {
            ShowTopResource resource = new ShowTopResource();
            resource.setKey(top.getKey());
            resource.setValue(top.getValue());
            return resource;
        }).toList();
    }
}
