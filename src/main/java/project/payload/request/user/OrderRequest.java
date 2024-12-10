package project.payload.request.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private List<String> artistIds;
    private List<String> bookerIds;
    private LocalDate from;
    private LocalDate to;
    private Double price;
    private String note;
    private String status;
}
