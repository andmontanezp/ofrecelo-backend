package cl.ofrecelo.api;

import cl.ofrecelo.api.offer.model.Coordinates;
import cl.ofrecelo.api.offer.model.Offer;
import cl.ofrecelo.api.offer.repository.OfferRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@SpringBootApplication
public class App {
    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner(OfferRepository offerRepository) {
        return args -> {
            Offer offer = new Offer();
            offer.setTitle("Offer 1");
            offer.setCoordinates(new Coordinates("-33.403468", "-70.556611"));

            Offer offer2 = new Offer();
            offer.setTitle("Offer 2");
            offer.setCoordinates(new Coordinates("-33.403468", "-70.556611"));

            offerRepository.saveAll(Arrays.asList(offer, offer2));
        };
    }
}
