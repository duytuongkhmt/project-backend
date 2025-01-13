package project.repository.spec;

import org.springframework.data.jpa.domain.Specification;
import project.common.Constant.COLUMN;
import project.model.entity.Order;

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

    public static Specification<Order> unFinished() {
        return (root, cq, cb) -> {
            LocalDateTime now = LocalDateTime.now();
            return cb.greaterThanOrEqualTo(root.get(COLUMN.TO), now);
        };
    }

    public static Specification<Order> checkTiming() {
        return (root, cq, cb) -> {
            LocalDateTime now = LocalDateTime.now();
            return cb.and(
                    cb.lessThanOrEqualTo(root.get(COLUMN.FROM), now),
                    cb.greaterThanOrEqualTo(root.get(COLUMN.TO), now)
            );
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

    public static Specification<Order> checkRequestBoundaryWithinRange(LocalDateTime requestFrom, LocalDateTime requestTo) {
        return (root, cq, cb) -> {
            if (requestFrom == null && requestTo == null) {
                return null; // Không áp dụng điều kiện nếu cả requestFrom và requestTo đều null
            }

            if (requestFrom != null && requestTo != null) {
                return cb.or(
                        // Kiểm tra request.from nằm giữa db.from và db.to
                        cb.and(
                                cb.greaterThanOrEqualTo(root.get(COLUMN.FROM), requestFrom),
                                cb.lessThanOrEqualTo(root.get(COLUMN.TO), requestFrom)
                        ),
                        // Kiểm tra request.to nằm giữa db.from và db.to
                        cb.and(
                                cb.greaterThanOrEqualTo(root.get(COLUMN.FROM), requestTo),
                                cb.lessThanOrEqualTo(root.get(COLUMN.TO), requestTo)
                        )
                );
            }

            if (requestFrom != null) {
                // Chỉ kiểm tra request.from nằm giữa khoảng db.from và db.to
                return cb.and(
                        cb.greaterThanOrEqualTo(root.get(COLUMN.FROM), requestFrom),
                        cb.lessThanOrEqualTo(root.get(COLUMN.TO), requestFrom)
                );
            }

            // Chỉ kiểm tra request.to nằm giữa khoảng db.from và db.to
            return cb.and(
                    cb.greaterThanOrEqualTo(root.get(COLUMN.FROM), requestTo),
                    cb.lessThanOrEqualTo(root.get(COLUMN.TO), requestTo)
            );
        };
    }


    public static Specification<Order> artistIdIn(List<String> artistIds) {
        return (root, cq, cb) -> (artistIds != null && !artistIds.isEmpty()) ? root.get(COLUMN.ARTIST_ID).in(artistIds) : null;
    }

    public static Specification<Order> bookerIdIn(List<String> bookerIds) {
        return (root, cq, cb) -> (bookerIds != null && !bookerIds.isEmpty()) ? root.get(COLUMN.BOOKER_ID).in(bookerIds) : null;
    }

    public static Specification<Order> statusIn(List<String> statuses) {
        return (root, cq, cb) -> (statuses != null && !statuses.isEmpty()) ? root.get(COLUMN.STATUS).in(statuses) : null;
    }


    public static Specification<Order> statusIs(String status) {
        return (root, cq, cb) -> (status == null || status.isEmpty()) ? null : cb.equal(root.get(COLUMN.STATUS), status);
    }

    public static Specification<Order> statusNotDelete() {
        return (root, cq, cb) -> root.get(COLUMN.STATUS).in(Order.STATUS.DELETED).not();
    }


}
