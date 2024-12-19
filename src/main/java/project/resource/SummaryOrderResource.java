package project.resource;

import lombok.Getter;
import lombok.Setter;
import project.model.entity.Order;

import java.util.List;


@Getter
@Setter
public class SummaryOrderResource {
    private Integer successTotal = 0;
    private Integer pendingTotal = 0;
    private Integer confirmedTotal = 0;
    private Integer cancelTotal = 0;

    public SummaryOrderResource(List<Order> orders) {

        if (orders == null || orders.isEmpty()) {
            return;
        }
        orders.forEach(order -> {
            if (order.getStatus() != null) {
                switch (order.getStatus()) {
                    case Order.STATUS.SUCCESS -> incrementSuccessTotal();
                    case Order.STATUS.CONFIRMED -> incrementConfirmedTotal();
                    case Order.STATUS.PENDING -> incrementPendingTotal();
                    case Order.STATUS.CANCEL -> incrementCancelTotal();
                    default -> {
                    }
                }
            }
        });
    }

    public void incrementSuccessTotal() {
        this.successTotal++;
    }

    public void incrementConfirmedTotal() {
        this.pendingTotal++;
    }

    public void incrementPendingTotal() {
        this.confirmedTotal++;
    }

    public void incrementCancelTotal() {
        this.cancelTotal++;
    }

}
