package project.payload.request.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OrderCreateRequest {
    private String artistId;
    private String bookerId;
    private LocalDate from;
    private LocalDate to;
    private Double price;
    private String note;
}
