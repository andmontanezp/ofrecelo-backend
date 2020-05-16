package cl.ofrecelo.api.offer.model;

import cl.ofrecelo.api.offer.dto.RatingDto;
import cl.ofrecelo.api.offer.exception.OfferNotFoundException;
import cl.ofrecelo.api.offer.exception.UserNotFoundException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.bson.types.ObjectId;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rating")
@Data
public class Rating {

    private ObjectId id;

    @DBRef(lazy = true)
    @JsonIgnoreProperties({"offer", "ratings"})
    private User user;

    @DBRef(lazy = true)
    @JsonIgnoreProperties({"user", "ratings"})
    private Offer offer;

    @Range(min = 0, max = 5)
    private int score;

    private String feedback;
}
