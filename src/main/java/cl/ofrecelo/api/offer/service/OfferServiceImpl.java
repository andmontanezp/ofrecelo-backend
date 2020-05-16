package cl.ofrecelo.api.offer.service;

import cl.ofrecelo.api.offer.dto.OfferDTO;
import cl.ofrecelo.api.offer.model.Coordinates;
import cl.ofrecelo.api.offer.model.Offer;
import cl.ofrecelo.api.offer.model.User;
import cl.ofrecelo.api.offer.model.UserInformation;
import cl.ofrecelo.api.offer.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequestScope
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private UserInformation userInformation;

    @Autowired
    private CloudStorageService cloudStorageService;

    @Value("${cloud.image.not.available}")
    private String defaultImage;

    public OfferServiceImpl(OfferRepository offerRepository, UserInformation userInformation) {
        this.offerRepository = offerRepository;
        this.userInformation = userInformation;
    }

    @Override
    public Offer saveOffer(String offerTitle, Double offerLatitude, Double offerLongitude, MultipartFile file){
        Offer offer = new Offer();
        //upload file
        if(file != null){
            offer.setTitle(offerTitle);
            offer.setCoordinates(new Coordinates(offerLatitude, offerLongitude));
            offer.setUserId(userInformation.getUserId());
            String blobName = null;
            try {
                blobName = cloudStorageService.uploadFile(file, file.getOriginalFilename(), offerTitle, userInformation.getUserId());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(blobName != null && !blobName.equalsIgnoreCase("")){
                offer.setBlobName(blobName);
            }
        }

        return offerRepository.save(offer);
    }

    @Override
    public List<OfferDTO> getOffers() {
        List<Offer> offers = offerRepository.findAll();
        List<OfferDTO> offerResponse = formattOfferResponse(offers);
        return offerResponse;
    }

    @Override
    public List<OfferDTO> getOffersByUser() {
        List<Offer> offers = offerRepository.findByUserId(userInformation.getUserId());
        List<OfferDTO> offerResponse = formattOfferResponse(offers);
        return offerResponse;
    }

    private List<OfferDTO> formattOfferResponse(List<Offer> offers){
        List<OfferDTO> offerResponse = new ArrayList<>();
        offers.forEach(offer -> {
            OfferDTO dto = new OfferDTO();
            dto.setId(offer.getId());
            dto.setTitle(offer.getTitle());
            dto.setCoordinates(offer.getCoordinates());
            dto.setBlobName(offer.getBlobName());
            byte[] file = new byte[0];
            try {
                if(offer.getBlobName() != null && !"".equalsIgnoreCase(offer.getBlobName())){
                    file = cloudStorageService.dowloadFile(offer.getBlobName());
                }else{
                    file = cloudStorageService.dowloadFile(defaultImage);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            dto.setFile(file);
            offerResponse.add(dto);
        });
        return offerResponse;
    }
}
