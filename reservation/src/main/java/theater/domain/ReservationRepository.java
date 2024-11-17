package theater.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import theater.domain.*;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "reservations",
    path = "reservations"
)
public interface ReservationRepository
    extends PagingAndSortingRepository<Reservation, Long> {}
