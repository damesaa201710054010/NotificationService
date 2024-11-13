package co.com.distributed.events.handlers;

import co.com.distributed.model.event.NotificationCreatedEvent;
import co.com.distributed.usecase.notifications.SendEmailNotificationUseCase;;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.reactivecommons.api.domain.DomainEvent;
import org.reactivecommons.async.impl.config.annotations.EnableNotificationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@EnableNotificationListener
@Log4j2
@AllArgsConstructor
public class EventsHandler {
    private final SendEmailNotificationUseCase sendEmailNotificationUseCase;

    public Mono<Void> handleCreatedServiceEvent(DomainEvent<NotificationCreatedEvent> event) {

        if (event.getData() == null) {
            log.error("Received event with null data   " + event);
            return Mono.empty();
        }
        NotificationCreatedEvent notificationCreatedEvent = NotificationCreatedEvent.builder()
                .userEmail(event.getData().getUserEmail())
                    .eventType(event.getData().getEventType())
                .userId(event.getData().getUserId())
                .build();
        return sendEmailNotificationUseCase.sendEmail(notificationCreatedEvent)
                .doOnError(error ->
                        log.error("Error sending notification to user {} : {}", event.getData().getUserEmail(), error.getMessage()))
                .onErrorResume(error -> Mono.empty())
                .flatMap(Mono::just)
                .then();
    }
}
