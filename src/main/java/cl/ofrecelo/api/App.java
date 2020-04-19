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
            offerRepository.deleteAll();

            Offer offer = new Offer();
            offer.setTitle("Casa");
            offer.setCoordinates(new Coordinates(-33.403468, -70.556611));

            Offer offer2 = new Offer();
            offer2.setTitle("Tavelli");
            offer2.setCoordinates(new Coordinates(-33.404769,-70.556809));

            Offer offer3 = new Offer();
            offer3.setTitle("Inacap");
            offer3.setCoordinates(new Coordinates(-33.404151,-70.557002));


            Offer offer4 = new Offer();
            offer4.setTitle("PDI");
            offer4.setCoordinates(new Coordinates(-33.4474022,-70.6509871));

            Offer offer5 = new Offer();
            offer5.setTitle("Orixas");
            offer5.setCoordinates(new Coordinates(-33.4462046,-70.6471097));

            offerRepository.saveAll(Arrays.asList(offer, offer2, offer3, offer4, offer5));
        };
    }

}
