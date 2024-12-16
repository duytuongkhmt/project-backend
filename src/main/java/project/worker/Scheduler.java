package project.worker;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import project.service.OrderService;

@Component
public class Scheduler {
    private final OrderService orderService;

    public Scheduler(OrderService orderService) {
        this.orderService = orderService;
    }

//    @Scheduled(fixedRate = 300000)
    @Scheduled(cron = "0 */5 * * * *")
    public void updateAllOrdersTask() {
        orderService.updateAllOrders();
    }
}
