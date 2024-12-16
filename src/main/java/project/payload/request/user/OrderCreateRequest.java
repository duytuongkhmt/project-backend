package project.payload.request.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderCreateRequest {
    private String artistId;
    private String company;
    private LocalDateTime from;
    private LocalDateTime to;
    private Double price;
    private String address;
    private String note;

    public LocalDateTime getFrom() {
        return from.plusHours(7);
    }

    public LocalDateTime getTo() {
        return to.plusHours(7);
    }
}
