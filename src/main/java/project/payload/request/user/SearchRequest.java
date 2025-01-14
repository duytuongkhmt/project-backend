package project.payload.request.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchRequest {
    private String key;
    private String role;
    private Double rate;
    private String category;
}
