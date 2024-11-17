package theater.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import theater.ReservationApplication;
import theater.domain.ReserveCanceled;
import theater.domain.Reserved;

@Entity
@Table(name = "Reservation_table")
@Data
//<<< DDD / Aggregate Root
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    private String userName;

    private Long movieId;

    private Integer count;

    private String reserveStatus;

    private Date date;

    @PostPersist
    public void onPostPersist() {
        Reserved reserved = new Reserved(this);
        reserved.publishAfterCommit();
    }

    public void reserveCancel(){
        ReserveCanceled reserveCanceled = new ReserveCanceled(this);
        reserveCanceled.publishAfterCommit();
    }

    public static ReservationRepository repository() {
        ReservationRepository reservationRepository = ReservationApplication.applicationContext.getBean(
            ReservationRepository.class
        );
        return reservationRepository;
    }

    public static void updateStatus(OutOfCount outOfCount) {
        
        repository().findById(outOfCount.getReserveId()).ifPresent(reservation->{
            reservation.setUserId(outOfCount.getUserId());
            reservation.setUserName(outOfCount.getUserName());
            reservation.setReserveStatus("예약취소");
            repository().save(reservation);
         });
        

    }


    public static void updateStatus(TicketIncreased ticketIncreased) {
        
        repository().findById(ticketIncreased.getReserveId()).ifPresent(reservation->{
            reservation.setUserId(ticketIncreased.getUserId());
            reservation.setUserName(ticketIncreased.getUserName());
            reservation.setReserveStatus("예약취소");
            repository().save(reservation);


         });
        

    }

    public static void updateStatus(TicketDecreased ticketDecreased) {

        repository().findById(ticketDecreased.getReserveId()).ifPresent(reservation->{
            reservation.setUserId(ticketDecreased.getUserId());
            reservation.setUserName(ticketDecreased.getUserName());
            reservation.setReserveStatus("예약완료");
            repository().save(reservation);


         });
        

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
