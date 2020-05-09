package cl.ofrecelo.api.offer.service;

import cl.ofrecelo.api.offer.model.Coordinates;
import cl.ofrecelo.api.offer.model.Offer;
import cl.ofrecelo.api.offer.model.User;
import cl.ofrecelo.api.offer.model.UserInformation;
import cl.ofrecelo.api.offer.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequestScope
public class OfferServiceImpl implements OfferService {
    private final OfferRepository offerRepository;
    private UserInformation userInformation;

    @Autowired
    private CloudStorageService cloudStorageService;

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
}
