package co.com.distributed.usecase.notifications;

import co.com.distributed.model.event.NotificationCreatedEvent;
import co.com.distributed.model.notificationevent.NotificationEvent;
import co.com.distributed.model.notificationevent.gateways.INotificationEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class SendEmailNotification {
    private final INotificationEventRepository notificationEventRepository;

    public Mono<Boolean> sendEmail(NotificationCreatedEvent event){
        NotificationEvent requestEvent = NotificationEvent.builder()
                .subject("notification.transfer")
                .content("notification.transfer")
                .build();
        return this.notificationEventRepository.publishEmail(requestEvent, event.getUserEmail()).hasElement();
    }
}
