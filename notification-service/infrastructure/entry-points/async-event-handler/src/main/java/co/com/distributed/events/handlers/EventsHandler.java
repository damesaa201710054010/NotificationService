package co.com.distributed.events.handlers;

import co.com.distributed.model.event.NotificationCreatedEvent;
import co.com.distributed.usecase.notifications.SendEmailNotification;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.reactivecommons.api.domain.DomainEvent;
import org.reactivecommons.async.impl.config.annotations.EnableNotificationListener;
import reactor.core.publisher.Mono;

@Log4j2
@AllArgsConstructor
@EnableNotificationListener
public class EventsHandler {
    private final SendEmailNotification sendEmailNotification;

    public Mono<Void> handleCreatedServiceEvent(DomainEvent<NotificationCreatedEvent> event) {

        if (event.getData() == null) {
            log.error("Received event with null data   " + event);
            return Mono.empty();
        }
        NotificationCreatedEvent notificationCreatedEvent = NotificationCreatedEvent.builder()
                .userEmail(event.getData().getUserEmail())
                    .eventType(event.getData().getEventType())
                .build();
        return sendEmailNotification.sendEmail(notificationCreatedEvent)
                .doOnError(error ->
                        log.error("Error sending notification to user {} : {}", event.getData().getUserEmail(), error.getMessage()))
                .onErrorResume(error -> Mono.empty())
                .flatMap(serviceResponse -> {
                    log.info("Notification send : {}", event.getData().getUserEmail());
                    return Mono.just(serviceResponse);
                })
                .then();
    }
}
