package project.mapper;

import org.springframework.beans.BeanUtils;
import project.model.Order;
import project.resource.OrderResource;
import project.resource.ProfileResource;

public class OrderMapper {
    private OrderMapper(){

    }

    public static OrderResource map(Order order, ProfileResource booker,ProfileResource artist) {
        OrderResource orderResource = new OrderResource();

        BeanUtils.copyProperties(order, orderResource);
        orderResource.setBooker(booker);
        orderResource.setArtist(artist);
        return orderResource;
    }
}
