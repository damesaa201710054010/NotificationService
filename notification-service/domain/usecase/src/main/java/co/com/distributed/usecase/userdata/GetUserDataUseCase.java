package co.com.distributed.usecase.userdata;

import co.com.distributed.model.userdata.UserData;
import co.com.distributed.model.userdata.gateways.IUserDataRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetUserDataUseCase {
    private final IUserDataRepository userDataRepository;

    public Mono<UserData> getUserData(String id) {
        return this.userDataRepository.getUserData(id);
    }
}
