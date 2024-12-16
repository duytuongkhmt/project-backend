package project.repository.spec;

import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import project.common.Constant;
import project.model.Account;
import project.model.Profile;

public class ProfileSpecification {

    public static Specification<Profile> notIsProfileId(String id) {
        if (id == null || id.isEmpty()) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> criteriaBuilder.notEqual(root.get(Constant.COLUMN.ID), id));
    }

    public static Specification<Profile> roleIs(String role) {
        if (role == null || role.isEmpty()) {
            return null;
        }
        return ((root, query, criteriaBuilder) -> {
            Join<Profile, Account> profileAccountJoin = root.join("user");
            return criteriaBuilder.equal(profileAccountJoin.get(Constant.COLUMN.ROLE), role);
        });
    }

    public static Specification<Profile> isActive() {
        return ((root, query, criteriaBuilder) -> {
            Join<Profile, Account> profileAccountJoin = root.join("user");
            return criteriaBuilder.equal(profileAccountJoin.get(Constant.COLUMN.IS_EMAIL_VERIFIED), true);
        });
    }
}
