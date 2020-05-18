package cl.ofrecelo.api.offer.service;

import cl.ofrecelo.api.offer.dto.OfferDTO;
import cl.ofrecelo.api.offer.model.Offer;
import cl.ofrecelo.api.offer.repository.OfferRepository;
import cl.ofrecelo.api.offer.request.OfferRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OfferService {
     List<OfferDTO> getOffers(String district);
     OfferDTO saveOffer(OfferRequest offerRequest, MultipartFile file);
     List<OfferDTO> getOffersByUser();
}
