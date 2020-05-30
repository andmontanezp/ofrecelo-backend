package cl.ofrecelo.api.offer.service;

import cl.ofrecelo.api.offer.dto.OfferDTO;
import cl.ofrecelo.api.offer.dto.RatingDto;
import cl.ofrecelo.api.offer.exceptions.UserDoesNotExistsException;
import cl.ofrecelo.api.offer.model.*;
import com.google.gson.Gson;
import cl.ofrecelo.api.offer.repository.OfferRepository;
import cl.ofrecelo.api.offer.repository.UserRepository;
import cl.ofrecelo.api.offer.request.OfferRequest;
import cl.ofrecelo.api.offer.transformer.OfferTransformer;
import cl.ofrecelo.api.offer.transformer.RatingTransformer;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@RequestScope
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;

    private UserInformation userInformation;

    private UserRepository userRepository;

    private RatingTransformer ratingTransformer;

    private OfferTransformer offerTransformer;


    @Autowired
    private CloudStorageService cloudStorageService;

    @Value("${cloud.image.not.available}")
    private String defaultImage;


    public OfferServiceImpl(OfferRepository offerRepository, UserInformation userInformation,
                            UserRepository userRepository, RatingTransformer ratingTransformer,
                            OfferTransformer offerTransformer) {
        this.offerRepository = offerRepository;
        this.userInformation = userInformation;
        this.userRepository = userRepository;
        this.ratingTransformer = ratingTransformer;
        this.offerTransformer = offerTransformer;
    }

    @Override
    public OfferDTO saveOffer(OfferRequest offerRequest) {
        Offer offer = new Offer();
        offer.setTitle(offerRequest.getOfferTitle());
        offer.setCoordinates(Coordinates.fromLatLong(offerRequest.getOfferLatitude(), offerRequest.getOfferLongitude()));
        //User user = userRepository.findById(new ObjectId(String.valueOf(userInformation.getUserId()))).orElseThrow(() -> new UserDoesNotExistsException(""));
        offer.setUserId(String.valueOf(userInformation.getUserId()));
        //offer.setUser(user);
        offer.setAddress(Address.fromDistrict(offerRequest.getDistrict().toLowerCase()));
        offer.setDescription(offerRequest.getDescription());
        //upload file
        uploadFile(offerRequest, offerRequest.getFile(), offerRequest.getFileName(), offerRequest.getFileExtension(), offer);
        return offerTransformer.fromDomainObjectToResponse(offerRepository.save(offer));
    }

    private void uploadFile(OfferRequest offerRequest, String file, String fileName , String fileExtension, Offer offer) {
        if(file != null){
            String blobName = null;
            try {
                ObjectId userId= userInformation.getUserId();
                //Decodificando file
                byte[] fileDecoded = Base64.getDecoder().decode(file);
                blobName = cloudStorageService.uploadFile(fileDecoded, fileName, offerRequest.getOfferTitle(),
                        userId);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(blobName != null && !blobName.equalsIgnoreCase("")){
                offer.setBlobName(blobName);
            }
        }
    }

    @Override
    public List<OfferDTO> getOffers(String district) {
        List<Offer> offers = offerRepository.findAllByAddress_District(district);
        List<OfferDTO> offerResponse = formattOfferResponse(offers);
        return offerResponse;
    }

    @Override
    public List<OfferDTO> getOffersByUser() {
        ObjectId userId= userInformation.getUserId();
        List<Offer> offers = offerRepository.findAllByUserId(userId.toString());
        List<OfferDTO> offerResponse = formattOfferResponse(offers);
        return offerResponse;
    }

    private List<OfferDTO> formattOfferResponse(List<Offer> offers){
        List<OfferDTO> offerResponse = new ArrayList<>();
        if(offers != null) {
            offers.forEach(offer -> {
                OfferDTO dto = new OfferDTO();
                dto.setId(offer.getId().toString());
                dto.setTitle(offer.getTitle());
                dto.setCoordinates(offer.getCoordinates());
                dto.setBlobName(offer.getBlobName());
                dto.setRating(ratingTransformer.fromDomainListToResponseList(offer.getRatings()));
                byte[] file = new byte[0];
                try {
                    if (offer.getBlobName() != null && !"".equalsIgnoreCase(offer.getBlobName())) {
                        file = cloudStorageService.dowloadFile(offer.getBlobName());
                    } else {
                        file = cloudStorageService.dowloadFile(defaultImage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dto.setFile(file);
                offerResponse.add(dto);
            });
        }
        return offerResponse;
    }
}
