package project.payload.response;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Slf4j
public class ResponseObject {
    @JsonIgnore
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private Object meta;
    private Object data;
    private boolean success = false;
    private Integer statusCode = HttpStatus.OK.value();

    static {
        MAPPER.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public ResponseObject(Object data, boolean success) {
        this.data = data;
        this.success = success;
    }

    public ResponseObject(Object data) {
        this.data = data;
        this.success = true;
    }

    public ResponseObject(Object data, Object meta, boolean success) {
        this.data = data;
        this.meta = meta;
        this.success = success;
    }
    public static ResponseObject error() {
        return new ResponseObject("NOT FOUND",false);
    }
    public static ResponseObject ok(Object data) {
        return new ResponseObject(data, true);
    }

    public static ResponseObject ok(Object data, Object meta) {
        return new ResponseObject(data, meta, true);
    }

    public static <T> ResponseObject ok(Page<T> page) {
        Meta meta = new Meta();
        meta.setTotal(page.getTotalElements());
        meta.setPage(page.getPageable().getPageNumber());
        return new ResponseObject(page.getContent(), meta, true);
    }

    public static ResponseObject error(Object data, HttpStatus statusCode) {
        ResponseObject result = new ResponseObject(data, false);
        result.statusCode = statusCode.value();
        return result;
    }

    @Data
    public static class Meta {
        private String message;
        private int page;
        private long total;
    }

    @Override
    public String toString() {
        try {
            return MAPPER.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            log.error(e.toString());
            return "";
        }
    }
}
