package cl.ofrecelo.api.offer.service;

import cl.ofrecelo.api.offer.model.Offer;
import org.springframework.web.multipart.MultipartFile;

public interface OfferService {
     Offer saveOffer(String offerTitle, Double offerLatitude, Double offerLongitude, MultipartFile file);
}
