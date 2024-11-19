package project.repository.spec;

import org.springframework.data.jpa.domain.Specification;
import project.common.Constant.COLUMN;
import project.model.Order;
import project.common.Constant.FORMAT_DATE;

import java.time.LocalDate;
import java.util.List;

public class OrderSpecification {

    public static Specification<Order> idIn(List<String> ids) {
        return (root, cq, cb) -> (ids != null && !ids.isEmpty()) ? root.get(COLUMN.ID).in(ids) : null;
    }

    public static Specification<Order> idIs(String id) {
        return (root, cq, cb) -> (id == null || id.isEmpty()) ? null : cb.equal(root.get(COLUMN.ID), id);
    }

    public static Specification<Order> dateBetween(LocalDate from, LocalDate to) {
        return (root, cq, cb) -> {
            if (from == null || to == null) {
                return null;
            }
            Integer fromDate = Integer.parseInt(from.format(FORMAT_DATE.FORMATTER));
            Integer toDate = Integer.parseInt(to.format(FORMAT_DATE.FORMATTER));

            return cb.between(root.get(COLUMN.DATE), fromDate, toDate);
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
        return (root, cq, cb) -> root.get(COLUMN.STATUS).in(Order.STATUS.DELETE).not();
    }


}
