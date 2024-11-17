package theater.domain;

import java.util.*;
import lombok.*;
import theater.domain.*;
import theater.infra.AbstractEvent;

@Data
@ToString
public class TicketIncreased extends AbstractEvent {

    private Long id;
    private String movieName;
    private Integer qty;
    private Integer count;
    private Long reserveId;
    private Long userId;
    private String userName;
}
