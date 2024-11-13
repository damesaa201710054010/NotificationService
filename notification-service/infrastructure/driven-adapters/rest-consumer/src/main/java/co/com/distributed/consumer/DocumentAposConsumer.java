package co.com.distributed.consumer;

import co.com.distributed.model.documentData.DocumentData;
import co.com.distributed.model.documentData.gateways.IDocumentData;
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
public class DocumentAposConsumer implements IDocumentData {
    @Value("${doc.connection.url}")
    private String url;

    @Override
    public Mono<Boolean> putApostDoc(DocumentData data) {
        WebClient client = WebClient.builder()
                .baseUrl(url)
                //.defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json") // Asegúrate de establecer el Content-Type
                .build();

        return client
                .put()
                .uri("/apis/authenticateDocument") // Cambia la URI según tu API
                .bodyValue(data) // Envía el objeto UserData como cuerpo de la solicitud
                .retrieve()
                .bodyToMono(String.class)// Asumiendo que UserData es la clase que representa la respuesta
                .log()
                .map(response -> {
                    // Procesa la respuesta y devuelve un valor booleano
                    System.out.println("Respuesta del servidor: " + response);
                    return true;
                })
                .onErrorResume(e -> {
                    // Manejo de errores: puedes registrar el error o devolver un valor por defecto
                    System.err.println("Error al actualizar datos del usuario: " + e.getMessage());
                    return Mono.just(false); // O devuelve un Mono con un valor por defecto
                });
    }
}
