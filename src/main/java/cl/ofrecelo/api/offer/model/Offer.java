package cl.ofrecelo.api.offer.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "offers")
@Data
public class Offer {

    @Id
    private String id;

    private String title;

    private Coordinates coordinates;

    private String userId;
}
