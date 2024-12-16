package project.payload.request.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankUpdateRequest {
    private String id;
    private String bankNumber;
    private String bankName;
    private String bankAddress;
    private String name;
}
