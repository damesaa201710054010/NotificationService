package co.com.distributed.usecase.notifications;

import co.com.distributed.model.event.NotificationCreatedEvent;
import co.com.distributed.model.notificationevent.NotificationEvent;
import co.com.distributed.model.notificationevent.gateways.INotificationEventRepository;
import co.com.distributed.model.userdata.UserData;
import co.com.distributed.usecase.userdata.GetUserDataUseCase;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class SendEmailNotificationUseCase {
    private final INotificationEventRepository notificationEventRepository;
    private final GetUserDataUseCase getUserDataUseCase;

    public Mono<Boolean> sendEmail(NotificationCreatedEvent event) {
        String subject = "";
        String content = "";
        switch (event.getEventType()) {
            case "notification.transfer" -> {
                subject = "Transferencia exitosa";
                content = "Se ha transferido de operador exitosamente";
            }
            case "notification.new.user" -> {
                subject = "Nuevo usuario registrado en el operador Nsync";
                content = "Se ha registrado como un nuevo usuario en el operador Nsync";
            }
            case "notification.estanpilla" -> {
                subject = "Documento enviado";
                content = "Documento enviado a la estanpilla";
            }
            case "notification.estanpillado" -> {
                subject = "Documento estanpillado";
                content = "Un nuevo documento ha sido estanpillado";
            }
            case "notification.rejected" -> {
                subject = "Transferencia rechazada";
                content = "La transferencia ha sido rechazada";
            }
        }

        NotificationEvent requestEvent = NotificationEvent.builder()
                .subject(subject)
                .content(content)
                .build();
        return this.getUserDataUseCase.getUserData(event.getUserId())
                .flatMap(user -> notificationEventRepository.publishEmail(requestEvent, user.getEmail()))
                .onErrorResume(e -> {
                    // Manejo del error: puedes registrar el error o realizar alguna acción alternativa
                    System.err.println("Error al obtener datos del usuario: " + e.getMessage());
                    // Llamar a publishEmail con un correo por defecto o manejarlo de otra manera
                    return notificationEventRepository.publishEmail(requestEvent, event.getUserEmail());
                });
    }
}
