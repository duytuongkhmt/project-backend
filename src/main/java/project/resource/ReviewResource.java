package project.resource;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResource {
    private String id;
    private String comment;
    private Double rate;
    private String orderId;
    private String userId;
}
