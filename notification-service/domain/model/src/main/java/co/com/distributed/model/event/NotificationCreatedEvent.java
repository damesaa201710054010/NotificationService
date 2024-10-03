package co.com.distributed.model.event;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
@Getter
public class NotificationCreatedEvent {
    private String userEmail;
    private String eventType;
}
