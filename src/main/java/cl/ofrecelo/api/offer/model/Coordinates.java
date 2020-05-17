package cl.ofrecelo.api.offer.model;

import lombok.Data;
import lombok.Getter;

@Getter
public class Coordinates {
    private double latitude;
    private double longitude;

    private Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static Coordinates fromLatLong(double latitude, double longitude) {
        return new Coordinates(latitude, longitude);
    }
}
