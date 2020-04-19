package cl.ofrecelo.api.offer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UserInformation {
    @Getter
    private String userId;

    @Getter
    private String email;
}
