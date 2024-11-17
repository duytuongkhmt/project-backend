package project.resource;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderResource {
    private String id;
    private String artistId;
    private String bookerId;
    private LocalDateTime from;
    private LocalDateTime to;
    private Double price;
    private String note;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
