package cl.ofrecelo.api.offer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Coordinates {
    private double latitude;
    private double longitude;
}
