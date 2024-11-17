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


    public static void increaseTicket(ReserveCanceled reserveCanceled) {

        repository().findById(reserveCanceled.getMovieId()).ifPresent(movie->{
            
            movie.setQty(movie.getQty() + reserveCanceled.getCount());
            repository().save(movie);

            TicketIncreased ticketIncreased = new TicketIncreased(movie);
            ticketIncreased.setReserveId(reserveCanceled.getId());
            ticketIncreased.setUserId(reserveCanceled.getUserId());
            ticketIncreased.setUserName(reserveCanceled.getUserName());
            ticketIncreased.publishAfterCommit();

         });

    }


    public static void decreaseTicket(Reserved reserved) {

        
        repository().findById(reserved.getMovieId()).ifPresent(movie->{

            if(movie.getQty() > reserved.getCount()){
                movie.setQty(movie.getQty() - reserved.getCount());
                repository().save(movie);

                TicketDecreased ticketDecreased = new TicketDecreased(movie);
                ticketDecreased.setReserveId(reserved.getId());
                ticketDecreased.setUserId(reserved.getUserId());
                ticketDecreased.setUserName(reserved.getUserName());
                ticketDecreased.setMovieId(id);
                ticketDecreased.setMovieName(movieName);
                ticketDecreased.setCount(reserved.getCount());
                ticketDecreased.publishAfterCommit();
            }
            else{
                OutOfCount outOfCount = new OutOfCount(movie);
                outOfCount.setReserveId(reserved.getId());
                outOfCount.setUserId(reserved.getUserId());
                outOfCount.setUserName(reserved.getUserName());
                outOfCount.publishAfterCommit();
            }

         });
        

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
