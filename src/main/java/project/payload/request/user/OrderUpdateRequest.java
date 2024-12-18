package project.payload.request.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class OrderUpdateRequest {
    private String id;
    private LocalDateTime from;
    private LocalDateTime to;
    private String artistId;
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
