package cl.ofrecelo.api.offer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

@AllArgsConstructor
@NoArgsConstructor
public class UserInformation {
    @Getter
    private ObjectId userId;

    @Getter
    private String email;
}
