package cl.ofrecelo.api.offer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "offers")
@Data
public class Offer {

    @Id
    private ObjectId id;

    private String title;

    private Coordinates coordinates;

    private String blobName;

    private Address address;

    private String description;

    @DBRef(lazy = true)
    @JsonIgnoreProperties({"offer", "ratings"})
    private User user;

    private String userId;

    @DBRef(lazy = true)
    private List<Rating> ratings;
}
