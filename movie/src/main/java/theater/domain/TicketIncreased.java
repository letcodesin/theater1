package theater.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import theater.domain.*;
import theater.infra.AbstractEvent;

//<<< DDD / Domain Event
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

    public TicketIncreased(Movie aggregate) {
        super(aggregate);
    }

    public TicketIncreased() {
        super();
    }
}
//>>> DDD / Domain Event
