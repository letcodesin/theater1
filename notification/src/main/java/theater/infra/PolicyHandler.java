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
    NotificationRepository notificationRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='TicketIncreased'"
    )
    public void wheneverTicketIncreased_Notify(
        @Payload TicketIncreased ticketIncreased
    ) {
        TicketIncreased event = ticketIncreased;
        System.out.println(
            "\n\n##### listener Notify : " + ticketIncreased + "\n\n"
        );

        // Sample Logic //
        Notification.notify(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='TicketDecreased'"
    )
    public void wheneverTicketDecreased_Notify(
        @Payload TicketDecreased ticketDecreased
    ) {
        TicketDecreased event = ticketDecreased;
        System.out.println(
            "\n\n##### listener Notify : " + ticketDecreased + "\n\n"
        );

        // Sample Logic //
        Notification.notify(event);
    }
}
//>>> Clean Arch / Inbound Adaptor
