package theater.infra;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import theater.config.kafka.KafkaProcessor;
import theater.domain.*;

@Service
public class ReservationListViewHandler {

    //<<< DDD / CQRS
    @Autowired
    private ReservationListRepository reservationListRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whenTicketDecreased_then_CREATE_1(
        @Payload TicketDecreased ticketDecreased
    ) {
        try {
            if (!ticketDecreased.validate()) return;

            // view 객체 생성
            ReservationList reservationList = new ReservationList();
            // view 객체에 이벤트의 Value 를 set 함
            reservationList.setReserveId(ticketDecreased.getId());
            reservationList.setReserveStatus("예약완료");
            reservationList.setUserId(ticketDecreased.getUserId());
            reservationList.setCount(ticketDecreased.getCount());
            reservationList.setMovieName(ticketDecreased.getMovieName());
            // view 레파지 토리에 save
            reservationListRepository.save(reservationList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void whenTicketIncreased_then_DELETE_1(
        @Payload TicketIncreased ticketIncreased
    ) {
        try {
            if (!ticketIncreased.validate()) return;
            // view 레파지 토리에 삭제 쿼리
            reservationListRepository.deleteByReserveId(
                ticketIncreased.getReserveId()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //>>> DDD / CQRS
}
