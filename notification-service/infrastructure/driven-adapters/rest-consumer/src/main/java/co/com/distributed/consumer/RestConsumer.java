package co.com.distributed.consumer;

import co.com.distributed.model.notificationevent.NotificationEvent;
import co.com.distributed.model.notificationevent.gateways.INotificationEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Log4j2
public class RestConsumer implements INotificationEventRepository {
    private final WebClient client;
    //private static final long RETRIES = 3L;

    @Override
    public Mono<Boolean> publishEmail(NotificationEvent event, String userEmail) {
        return client.post()
                .uri("/mail/send")
                .header("Content-Type", "application/json")
                .body(BodyInserters.fromValue(
                        "{ \"personalizations\": [{ \"to\": [{ \"email\": \"" + userEmail + "\" }] }], " +
                                "\"from\": { \"email\": \"operadornsync@nsync.online\" }, " +
                                "\"subject\": \"" + event.getSubject() + "\", " +
                                "\"content\": [{ \"type\": \"text/plain\", \"value\": \"" + event.getContent() + "\" }] }"))
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    System.out.println("Correo enviado: " + userEmail);
                    log.info("Correo enviado: {}", userEmail);
                    log.info("Correo enviado: {}", response);
                    return Mono.just(true);
                })
                .log()
                .then(Mono.just(true)).log();
    }
}
