package theater.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.*;
import theater.domain.*;
import theater.infra.AbstractEvent;

//<<< DDD / Domain Event
@Data
@ToString
public class Reserved extends AbstractEvent {

    private Long id;
    private Long userId;
    private String userName;
    private Long movieId;
    private Integer count;
    private String reserveStatus;
    private Date date;

    public Reserved(Reservation aggregate) {
        super(aggregate);
    }

    public Reserved() {
        super();
    }
}
//>>> DDD / Domain Event
