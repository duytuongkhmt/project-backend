package project.model.data;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class ShowTopReport {
    private Integer eventDate;
    private String key;
    private Double value;

    public Integer getEventDateIfNull() {
        if(eventDate==null){
            try {
                return Integer.valueOf(this.key);
            } catch (NumberFormatException e) {
                log.info("Error at getEventDate, ShowTopReport model: Because don't cast string to number");
            }
        }
        return eventDate;
    }
}
