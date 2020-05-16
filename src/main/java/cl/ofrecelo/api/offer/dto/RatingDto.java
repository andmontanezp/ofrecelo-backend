package cl.ofrecelo.api.offer.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class RatingDto {

    private String offerId;
    private String userId;

    @Range(min = 0, max = 5)
    private int score;

    private String feedback;
}
