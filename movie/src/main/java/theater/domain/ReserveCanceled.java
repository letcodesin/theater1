package theater.domain;

import java.util.*;
import lombok.*;
import theater.domain.*;
import theater.infra.AbstractEvent;

@Data
@ToString
public class ReserveCanceled extends AbstractEvent {

    private Long id;
    private Long userId;
    private String userName;
    private Long movieId;
    private Integer count;
    private String reserveStatus;
    private Date date;
}
