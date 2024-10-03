package co.com.distributed.events;
import co.com.distributed.events.handlers.EventsHandler;
import co.com.distributed.model.event.NotificationCreatedEvent;
import org.reactivecommons.async.api.HandlerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class HandlerRegistryConfiguration {

    // see more at: https://reactivecommons.org/reactive-commons-java/#_handlerregistry_2
    @Bean
    public HandlerRegistry handlerRegistry(EventsHandler events) {
        return HandlerRegistry.register()
                .listenNotificationEvent("notification.nsync", events::handleCreatedServiceEvent, NotificationCreatedEvent.class);
    }
}
