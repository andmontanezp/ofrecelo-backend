package cl.ofrecelo.api.offer.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import java.util.List;

@Document(collection = "users")
@Data
public class User {

    @Id
    private String id;

    private String email;

    private String name;

    private String lastName;

    private String password;
}
