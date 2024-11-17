package theater.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import theater.MovieApplication;
import theater.domain.OutOfCount;
import theater.domain.TicketDecreased;
import theater.domain.TicketIncreased;

@Entity
@Table(name = "Movie_table")
@Data
//<<< DDD / Aggregate Root
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String movieName;

    private Integer qty;

    private String description;

    @PostPersist
    public void onPostPersist() {
        TicketIncreased ticketIncreased = new TicketIncreased(this);
        ticketIncreased.publishAfterCommit();

        TicketDecreased ticketDecreased = new TicketDecreased(this);
        ticketDecreased.publishAfterCommit();

        OutOfCount outOfCount = new OutOfCount(this);
        outOfCount.publishAfterCommit();
    }

    public static MovieRepository repository() {
        MovieRepository movieRepository = MovieApplication.applicationContext.getBean(
            MovieRepository.class
        );
        return movieRepository;
    }

    //<<< Clean Arch / Port Method
    public static void increaseTicket(ReserveCanceled reserveCanceled) {
        //implement business logic here:

        /** Example 1:  new item 
        Movie movie = new Movie();
        repository().save(movie);

        TicketIncreased ticketIncreased = new TicketIncreased(movie);
        ticketIncreased.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(reserveCanceled.get???()).ifPresent(movie->{
            
            movie // do something
            repository().save(movie);

            TicketIncreased ticketIncreased = new TicketIncreased(movie);
            ticketIncreased.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void decreaseTicket(Reserved reserved) {
        //implement business logic here:

        /** Example 1:  new item 
        Movie movie = new Movie();
        repository().save(movie);

        TicketDecreased ticketDecreased = new TicketDecreased(movie);
        ticketDecreased.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(reserved.get???()).ifPresent(movie->{
            
            movie // do something
            repository().save(movie);

            TicketDecreased ticketDecreased = new TicketDecreased(movie);
            ticketDecreased.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
