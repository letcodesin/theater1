package theater.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import theater.config.kafka.KafkaProcessor;
import theater.domain.*;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    ReservationRepository reservationRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='OutOfCount'"
    )
    public void wheneverOutOfCount_UpdateStatus(
        @Payload OutOfCount outOfCount
    ) {
        OutOfCount event = outOfCount;
        System.out.println(
            "\n\n##### listener UpdateStatus : " + outOfCount + "\n\n"
        );

        // Sample Logic //
        Reservation.updateStatus(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='TicketIncreased'"
    )
    public void wheneverTicketIncreased_UpdateStatus(
        @Payload TicketIncreased ticketIncreased
    ) {
        TicketIncreased event = ticketIncreased;
        System.out.println(
            "\n\n##### listener UpdateStatus : " + ticketIncreased + "\n\n"
        );

        // Sample Logic //
        Reservation.updateStatus(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='TicketDecreased'"
    )
    public void wheneverTicketDecreased_UpdateStatus(
        @Payload TicketDecreased ticketDecreased
    ) {
        TicketDecreased event = ticketDecreased;
        System.out.println(
            "\n\n##### listener UpdateStatus : " + ticketDecreased + "\n\n"
        );

        // Sample Logic //
        Reservation.updateStatus(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
