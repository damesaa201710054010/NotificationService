package co.com.distributed.model.notificationevent;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class NotificationEvent {
        private String subject;
        private String content;
}
