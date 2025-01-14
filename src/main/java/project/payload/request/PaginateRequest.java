package project.payload.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class PaginateRequest {
    private Integer limit;
    private Integer page;
    private String column = "id";
    private String sortOrder = "desc";

    public Sort.Direction getSort() {
        return sortOrder.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
    }
}
