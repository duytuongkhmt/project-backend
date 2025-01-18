package project.repository.spec;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import project.common.Constant;
import project.model.entity.Account;
import project.model.entity.Profile;

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
            return criteriaBuilder.equal(profileAccountJoin.get(Constant.COLUMN.ROLE), role.toUpperCase());
        });
    }

    public static Specification<Profile> isActive() {
        return ((root, query, criteriaBuilder) -> {
            Join<Profile, Account> profileAccountJoin = root.join("user");
            return criteriaBuilder.equal(profileAccountJoin.get(Constant.COLUMN.IS_EMAIL_VERIFIED), true);
        });
    }

    public static Specification<Profile> rateGreaterThanOrEqualTo(Double rate) {
        if (rate == null) {
            return null;
        }
        return ((root, query, cb) -> cb.greaterThanOrEqualTo(root.get(Constant.COLUMN.RATE), rate));
    }

    public static Specification<Profile> fullNameContain(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }
        String normalizedKeyword = "%" + StringUtils.stripAccents(name.trim().toLowerCase()) + "%";
        return (root, query, cb) ->
                cb.like(cb.lower(cb.function("unaccent", String.class, root.get(Constant.COLUMN.FULL_NAME))), normalizedKeyword);
    }

    public static Specification<Profile> genreLike(String category) {
        if (category == null || category.trim().isEmpty()) {
            return null;
        }

        String normalizedKeyword = "%" + StringUtils.stripAccents(category.trim().toLowerCase()) + "%";

        return (root, query, cb) -> {
            // Sử dụng function trong CriteriaBuilder để gọi unaccent và jsonb_array_elements_text
            Expression<String> unaccentedGenre = cb.function("unaccent", String.class,
                    cb.function("jsonb_array_elements_text", String.class, root.get("genre"))
            );

            // Sử dụng LIKE và LOWER để so sánh genre với normalizedKeyword
            return cb.like(cb.lower(unaccentedGenre), normalizedKeyword);
        };
    }





}
