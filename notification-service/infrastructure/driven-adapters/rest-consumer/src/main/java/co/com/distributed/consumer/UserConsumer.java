package co.com.distributed.consumer;

import co.com.distributed.model.userdata.UserData;
import co.com.distributed.model.userdata.gateways.IUserDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserConsumer implements IUserDataRepository {
    @Value("${user.connection.url}")
    private String url;

    @Override
    public Mono<UserData> getUserData(String id) {
        WebClient client = WebClient.builder()
                .baseUrl(url)
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                //.clientConnector(getClientHttpConnector())
                .build();
        return client
                .get()
                .uri("/v1/users/" + id) // Cambia la URI según tu API
                .retrieve()
                .bodyToMono(UserData.class) // Asumiendo que UserData es la clase que representa la respuesta
                .log()
                .doOnError(e -> {
                    // Manejo de errores: puedes registrar el error o devolver un valor por defecto
                    System.err.println("Error al obtener datos del usuario: " + e.getMessage());
                });
    }
}
