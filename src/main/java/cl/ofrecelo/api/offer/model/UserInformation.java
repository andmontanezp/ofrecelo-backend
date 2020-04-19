package cl.ofrecelo.api.offer.model;

import lombok.Data;
import lombok.Getter;


public class UserInformation {
    @Getter
    private Long userId;
    @Getter
    private String timeZone;
    @Getter
    private String language;

    public UserInformation(Long userId, String timeZone, String language) {
        this.userId = userId;
        this.timeZone = timeZone;
        this.language = language;
    }
}
