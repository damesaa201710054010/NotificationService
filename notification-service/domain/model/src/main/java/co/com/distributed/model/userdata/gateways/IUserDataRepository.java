package co.com.distributed.model.userdata.gateways;

import co.com.distributed.model.userdata.UserData;
import reactor.core.publisher.Mono;

public interface IUserDataRepository {

    Mono<UserData> getUserData(String id);
}
