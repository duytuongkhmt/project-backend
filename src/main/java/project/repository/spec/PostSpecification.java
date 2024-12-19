package project.repository.spec;

import org.springframework.data.jpa.domain.Specification;
import project.common.Constant;
import project.model.Post;

public class PostSpecification {
    public static Specification<Post> statusNotDelete() {
        return (root, cq, cb) -> root.get(Constant.COLUMN.STATUS).in(Post.STATUS.DELETE).not();
    }
}
