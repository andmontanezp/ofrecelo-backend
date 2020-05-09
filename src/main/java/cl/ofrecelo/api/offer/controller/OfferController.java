package cl.ofrecelo.api.offer.controller;

import cl.ofrecelo.api.offer.exception.OfferNotFoundException;
import cl.ofrecelo.api.offer.model.Offer;
import cl.ofrecelo.api.offer.repository.OfferRepository;
import cl.ofrecelo.api.offer.service.OfferService;
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

    @GetMapping
    public ResponseEntity<List<Offer>> findOffers() {
        return ResponseEntity.ok(offerRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Offer> findOne(@PathVariable("id") String id) throws OfferNotFoundException {
        return ResponseEntity.ok(offerRepository.findById(id).orElseThrow(OfferNotFoundException::new));
    }

    @PostMapping
    public ResponseEntity<Offer> saveOffer(@RequestParam("offerTitle") String offerTitle,
                                           @RequestParam("offerLatitude") Double offerLatitude,
                                           @RequestParam("offerLongitude") Double offerLongitude,
                                           @RequestParam("offerFile") MultipartFile file) {
        return ResponseEntity.ok(offerService.saveOffer(offerTitle, offerLatitude, offerLongitude, file));
    }

    @PutMapping
    public ResponseEntity<Offer> editOffer() {
        return ResponseEntity.ok(new Offer());
    }

    @DeleteMapping
    public void deleteOffer() {
    }
}
