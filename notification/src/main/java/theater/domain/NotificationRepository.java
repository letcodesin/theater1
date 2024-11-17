package theater.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import theater.domain.*;

//<<< PoEAA / Repository
@RepositoryRestResource(
    collectionResourceRel = "notifications",
    path = "notifications"
)
public interface NotificationRepository
    extends PagingAndSortingRepository<Notification, Long> {}
