package co.com.distributed.model.userdata;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@ToString
@Getter
public class UserData {
    private String id;
    private String govCarpetaId;
    private String identification;
    private String name;
    private String email;
    private String operatorId;
    private String operatorName;
    private String transferRequest;
    private String createdDate;
    private String updatedDate;
    private boolean isActived;
    private String age;
}
