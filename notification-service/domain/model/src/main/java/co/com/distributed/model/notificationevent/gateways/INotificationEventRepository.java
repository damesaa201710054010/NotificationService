package co.com.distributed.model.notificationevent.gateways;

import co.com.distributed.model.notificationevent.NotificationEvent;
import reactor.core.publisher.Mono;

public interface INotificationEventRepository {
    Mono<Boolean> publishEmail(NotificationEvent event, String userEmail);
}
