package cl.ofrecelo.api.offer.dto;

import cl.ofrecelo.api.offer.model.Coordinates;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OfferDTO implements Serializable {

    private String id;

    private String title;

    private Coordinates coordinates;

    private String userId;

    private String blobName;

    private List<RatingDto> rating;

    private byte[] file;

    private String description;

    private DefaultLocationMap defaultLocationMap;
}
