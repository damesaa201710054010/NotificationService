package co.com.distributed.model.notificationevent;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
@Getter
public class NotificationEvent {
        private String subject;
        private String content;
}
