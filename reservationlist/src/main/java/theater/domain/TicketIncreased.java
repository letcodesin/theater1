package theater.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;
import theater.infra.AbstractEvent;

@Data
public class TicketIncreased extends AbstractEvent {

    private Long id;
    private String movieName;
    private Integer qty;
    private Integer count;
    private Long reserveId;
    private Long userId;
    private String userName;
}
