package project.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import project.common.Constant;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PagingUtils {
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

    public static Map<String, Object> createMeta(Page<?> page) {
        Map<String, Object> meta = new HashMap<>();
        meta.put("total", page.getTotalElements());
        meta.put("totalPages", page.getTotalPages());
        meta.put("size", page.getSize());
        meta.put("page", page.getNumber());
        return meta;
    }
}
