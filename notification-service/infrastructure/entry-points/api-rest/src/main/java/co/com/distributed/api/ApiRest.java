package co.com.distributed.api;
import co.com.distributed.model.documentData.DocumentData;
import co.com.distributed.usecase.document.DocumentPutUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.PUT})
@RequiredArgsConstructor
public class ApiRest {
    private final DocumentPutUseCase documentPutUseCase;


    @PutMapping(path = "/doc")
    public Mono<ResponseEntity<Object>> commandName(@RequestBody DocumentData data) {
        DocumentData.builder().documentTitle(data.getDocumentTitle())
                .UrlDocument(data.getUrlDocument()).idCitizen(data.getIdCitizen()).build();
        return this.documentPutUseCase.putDocument(data)
                .map(response -> {
                        if (response) {
                            return encapsulateResponse(response, HttpStatus.OK);
                        }
                        return encapsulateResponse(response, HttpStatus.INTERNAL_SERVER_ERROR);
                });
    }

    public static ResponseEntity<Object> encapsulateResponse(Object body, HttpStatus status) {
        var bodyTo = status.is2xxSuccessful() ?  "Documento Ciudadano Registrado" : "Error al registrar documento ciudadano";
        return ResponseEntity.status(status).body(bodyTo);
    }
}
