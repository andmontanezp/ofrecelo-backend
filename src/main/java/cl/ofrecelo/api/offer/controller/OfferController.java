package cl.ofrecelo.api.offer.controller;

import cl.ofrecelo.api.offer.exception.OfferNotFoundException;
import cl.ofrecelo.api.offer.model.Offer;
import cl.ofrecelo.api.offer.repository.OfferRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "/offer")
public class OfferController {

    private final OfferRepository offerRepository;

    public OfferController(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
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
    public ResponseEntity<Offer> saveOffer() {
        return ResponseEntity.ok(new Offer());
    }

    @PutMapping
    public ResponseEntity<Offer> editOffer() {
        return ResponseEntity.ok(new Offer());
    }

    @DeleteMapping
    public void deleteOffer() {
    }
}
