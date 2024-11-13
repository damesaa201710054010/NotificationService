package co.com.distributed.model.documentData.gateways;

import co.com.distributed.model.documentData.DocumentData;
import reactor.core.publisher.Mono;

public interface IDocumentData {
    Mono<Boolean> putApostDoc(DocumentData data);
}
