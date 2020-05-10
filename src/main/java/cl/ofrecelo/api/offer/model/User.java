package cl.ofrecelo.api.offer.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "users")
@Data
public class User {

    @Id
    private ObjectId _id;

    private String email;

    private String name;

    private String lastName;

    private String password;
}
