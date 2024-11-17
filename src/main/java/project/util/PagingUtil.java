package project.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import project.common.Constant;

import java.util.Objects;

public class PagingUtil {
    public static PageRequest getPageRequest(Integer page, Integer limit, Sort sort) {
        return PageRequest.of(
                Objects.requireNonNullElse(page, Constant.PAGE.DEFAULT),
                Objects.requireNonNullElse(limit, Constant.PAGE.LIMIT),
                sort);
    }

    public static PageRequest getPageRequest(Integer page, Integer limit) {
        return PageRequest.of(
                Objects.requireNonNullElse(page, Constant.PAGE.DEFAULT),
                Objects.requireNonNullElse(limit, Constant.PAGE.LIMIT));
    }
}
