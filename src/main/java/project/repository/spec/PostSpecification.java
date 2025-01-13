package project.repository.spec;

import org.springframework.data.jpa.domain.Specification;
import project.common.Constant;
import project.model.entity.Post;
import project.model.entity.Profile;

public class PostSpecification {
    public static Specification<Post> statusNotDelete() {
        return (root, cq, cb) -> cb.notEqual(root.get(Constant.COLUMN.STATUS),Post.STATUS.DELETE);
    }

    public static Specification<Post> profileIs(Profile profile) {
        return (root, cq, cb) -> {
            if (profile == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get(Constant.COLUMN.PROFILE), profile);
        };
    }

}
