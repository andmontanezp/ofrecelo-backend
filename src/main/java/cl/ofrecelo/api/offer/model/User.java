package cl.ofrecelo.api.offer.model;

import lombok.Data;
import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@Document(collection = "users")
@Data
public class User {

    @Id
    private ObjectId _id;

    private String email;

    private String name;

    private String lastName;

    private String password;

    private String phone;

    @DBRef(lazy = true)
    Offer offer;

    @DBRef(lazy = true)
    @JsonManagedReference
    List<Rating> ratings;
}
