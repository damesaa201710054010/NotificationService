package co.com.distributed.model.documentData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class DocumentData {
    private String documentTitle;
    private String UrlDocument;
    private String idCitizen;
}
