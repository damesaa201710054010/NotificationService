package co.com.distributed.model.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ConnectionProperties {
    public static final int DEFAULT_AMQP_PORT = 5672;
    public static final int DEFAULT_AMQPS_PORT = 5671;
    public static final String AMQPS = "amqps";
    private String virtualhost = "/";

    private String hostname;

    private String username;

    private String password;

    private String protocol;
}
