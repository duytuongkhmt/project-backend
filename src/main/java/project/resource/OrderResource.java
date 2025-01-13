package project.resource;

import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
public class OrderResource {
    private String id;
    private String artistId;
    private String bookerId;
    private LocalDateTime from;
    private LocalDateTime to;
    private Double price;
    private String note;
    private String address;
    private String status;
    private String reasonReject;
    private ProfileResource booker;
    private ProfileResource artist;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public String getTotalTime() {
        if (from == null || to == null) {
            return "Invalid time range";
        }
        Duration duration = Duration.between(from, to);

        if (duration.isNegative()) {
            return "Invalid time range";
        }

        long days = duration.toDays();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();

        if (days > 0) {
            return String.format("%d day(s) %02dh%02d", days, hours, minutes);
        } else {
            return String.format("%02dh%02d", hours, minutes);
        }
    }
}
