package cl.ofrecelo.api.offer.service;

import cl.ofrecelo.api.offer.dto.OfferDTO;
import cl.ofrecelo.api.offer.model.Offer;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OfferService {
     List<OfferDTO> getOffers();
     Offer saveOffer(String offerTitle, Double offerLatitude, Double offerLongitude, MultipartFile file);
     List<OfferDTO> getOffersByUser();
}
