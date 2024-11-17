package theater.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import theater.NotificationApplication;

@Entity
@Table(name = "Notification_table")
@Data
//<<< DDD / Aggregate Root
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long reserveId;

    private String reserveStatus;

    private Long userId;

    private String userName;

    private Long movieId;

    private String movieName;

    private Integer count;

    public static NotificationRepository repository() {
        NotificationRepository notificationRepository = NotificationApplication.applicationContext.getBean(
            NotificationRepository.class
        );
        return notificationRepository;
    }

    //<<< Clean Arch / Port Method
    public static void notify(TicketIncreased ticketIncreased) {
        //implement business logic here:

        /** Example 1:  new item 
        Notification notification = new Notification();
        repository().save(notification);

        */

        /** Example 2:  finding and process
        
        repository().findById(ticketIncreased.get???()).ifPresent(notification->{
            
            notification // do something
            repository().save(notification);


         });
        */

    }

    public static void notify(TicketDecreased ticketDecreased) {

        Notification notification = new Notification();
        notification.setReserveId(ticketDecreased.getId());
        notification.setReserveStatus("예약완료");
        notification.setUserId(ticketDecreased.getUserId());
        notification.setUserName(ticketDecreased.getUserName());
        notification.setMovieName(ticketDecreased.getMovieName());
        notification.setCount(ticketDecreased.getCount());
        repository().save(notification);

    }


    public static void notify(OutOfCount outOfCount) {

        Notification notification = new Notification();
        notification.setReserveId(outOfCount.getId());
        notification.setReserveStatus("예약취소");
        notification.setUserId(outOfCount.getUserId());
        notification.setUserName(outOfCount.getUserName());
        notification.setMovieName(outOfCount.getMovieName());
        notification.setCount(outOfCount.getCount());
        repository().save(notification);

    }


}
//>>> DDD / Aggregate Root
