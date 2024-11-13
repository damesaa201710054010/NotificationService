package co.com.distributed.usecase.document;

import co.com.distributed.model.documentData.DocumentData;
import co.com.distributed.model.documentData.gateways.IDocumentData;
import co.com.distributed.model.event.Notification;
import co.com.distributed.model.event.NotificationCreatedEvent;
import co.com.distributed.usecase.notifications.SendEmailNotificationUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class DocumentPutUseCase {
    private final IDocumentData documentRepository;
    private final SendEmailNotificationUseCase sendEmailNotificationUseCase;

    public Mono<Boolean> putDocument(DocumentData documentData) {
        return this.documentRepository.putApostDoc(documentData)
                .doOnSuccess(response -> {
                    if (response) {
                        NotificationCreatedEvent notification = NotificationCreatedEvent
                                .builder()
                                        .userId(documentData.getIdCitizen())
                                        .eventType("notification.estanpilla")
                                .build();
                        System.out.println("Evento de notificacion creado");
                        this.sendEmailNotificationUseCase.sendEmail(notification)
                                .log().doOnSuccess(response1 -> {
                                    if (response1) {
                                        System.out.println("Email enviado");
                                    } else {
                                        System.out.println("Error al enviar email");
                                    }
                                });
                        System.out.println("Documento Ciudadano Registrado");
                    } else {
                        System.out.println("Error al registrar documento ciudadano");
                    }
                });
    }
}
