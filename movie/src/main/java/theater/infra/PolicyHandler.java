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
    MovieRepository movieRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='ReserveCanceled'"
    )
    public void wheneverReserveCanceled_IncreaseTicket(
        @Payload ReserveCanceled reserveCanceled
    ) {
        ReserveCanceled event = reserveCanceled;
        System.out.println(
            "\n\n##### listener IncreaseTicket : " + reserveCanceled + "\n\n"
        );

        // Sample Logic //
        Movie.increaseTicket(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='Reserved'"
    )
    public void wheneverReserved_DecreaseTicket(@Payload Reserved reserved) {
        Reserved event = reserved;
        System.out.println(
            "\n\n##### listener DecreaseTicket : " + reserved + "\n\n"
        );

        // Sample Logic //
        Movie.decreaseTicket(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
