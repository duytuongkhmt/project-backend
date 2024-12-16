package project.repository.spec;

import org.springframework.data.jpa.domain.Specification;
import project.common.Constant.COLUMN;
import project.model.Order;
import project.common.Constant.FORMAT_DATE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class OrderSpecification {

    public static Specification<Order> idIn(List<String> ids) {
        return (root, cq, cb) -> (ids != null && !ids.isEmpty()) ? root.get(COLUMN.ID).in(ids) : null;
    }

    public static Specification<Order> idIs(String id) {
        return (root, cq, cb) -> (id == null || id.isEmpty()) ? null : cb.equal(root.get(COLUMN.ID), id);
    }

    public static Specification<Order> checkTimeFrom(LocalDateTime from) {
        return (root, cq, cb) -> {
            if (from == null) {
                return null;
            }
            return cb.greaterThanOrEqualTo(root.get(COLUMN.FROM), from);
        };
    }

    public static Specification<Order> checkTimeTo(LocalDateTime to) {
        return (root, cq, cb) -> {
            if (to == null) {
                return null;
            }
            return cb.lessThanOrEqualTo(root.get(COLUMN.TO), to);
        };
    }


    public static Specification<Order> artistIdIn(List<String> artistIds) {
        return (root, cq, cb) -> (artistIds != null && !artistIds.isEmpty()) ? root.get(COLUMN.ARTIST_ID).in(artistIds) : null;
    }

    public static Specification<Order> bookerIdIn(List<String> bookerIds) {
        return (root, cq, cb) -> (bookerIds != null && !bookerIds.isEmpty()) ? root.get(COLUMN.BOOKER_ID).in(bookerIds) : null;
    }

    public static Specification<Order> statusIs(String status) {
        return (root, cq, cb) -> (status == null || status.isEmpty()) ? null : cb.equal(root.get(COLUMN.STATUS), status);
    }

    public static Specification<Order> statusNotDelete() {
        return (root, cq, cb) -> root.get(COLUMN.STATUS).in(Order.STATUS.DELETED).not();
    }


}
