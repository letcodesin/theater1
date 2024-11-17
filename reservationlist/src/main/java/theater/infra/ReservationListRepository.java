package theater.infra;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import theater.domain.*;

@RepositoryRestResource(
    collectionResourceRel = "reservationLists",
    path = "reservationLists"
)
public interface ReservationListRepository
    extends PagingAndSortingRepository<ReservationList, Long> {}
