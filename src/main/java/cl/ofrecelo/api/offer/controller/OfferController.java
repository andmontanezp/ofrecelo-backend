package cl.ofrecelo.api.offer.controller;

import cl.ofrecelo.api.offer.dto.OfferDTO;
import cl.ofrecelo.api.offer.exception.OfferNotFoundException;
import cl.ofrecelo.api.offer.model.Offer;
import cl.ofrecelo.api.offer.repository.OfferRepository;
import cl.ofrecelo.api.offer.request.OfferRequest;
import cl.ofrecelo.api.offer.service.OfferService;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/offer")
public class OfferController {

    private final OfferRepository offerRepository;
    private final OfferService offerService;

    public OfferController(OfferRepository offerRepository, OfferService offerService) {
        this.offerRepository = offerRepository;
        this.offerService = offerService;
    }

    @GetMapping("/location/{district}")
    public ResponseEntity<List<OfferDTO>> findOffers(@PathVariable("district") String district) {
        return ResponseEntity.ok(offerService.getOffers(district));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Offer> findOne(@PathVariable("id") String id) throws OfferNotFoundException {
        return ResponseEntity.ok(offerRepository.findById(new ObjectId(id)).orElseThrow(OfferNotFoundException::new));
    }

    @PostMapping
    public ResponseEntity<OfferDTO> saveOffer(@RequestBody OfferRequest offerRequest, @RequestParam(value = "offerFile", required = false) MultipartFile file) {
        return ResponseEntity.ok(offerService.saveOffer(offerRequest));
    }

    @PutMapping
    public ResponseEntity<Offer> editOffer() {
        return ResponseEntity.ok(new Offer());
    }

    @DeleteMapping
    public void deleteOffer() {
    }

    @GetMapping("/user")
    public ResponseEntity<List<OfferDTO>> findOne() {
        return ResponseEntity.ok(offerService.getOffersByUser());
    }
}
