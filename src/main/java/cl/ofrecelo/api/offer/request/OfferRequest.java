package cl.ofrecelo.api.offer.request;

import lombok.Data;

@Data
public class OfferRequest {
    private String userId;
    private String offerTitle;
    private double offerLatitude;
    private double offerLongitude;
    private String district;
    private String file;
    private String fileExtension;
    private String fileName;
    private String description;
}
