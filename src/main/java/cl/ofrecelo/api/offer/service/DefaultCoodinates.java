package cl.ofrecelo.api.offer.service;

import cl.ofrecelo.api.offer.model.Coordinates;

public enum DefaultCoodinates {

    SANTIAGO (Coordinates.fromLatLong(-33.4574739, -70.697282)),
    LAS_CONDES (Coordinates.fromLatLong(-33.4125944,-70.5689716));

    private final Coordinates coordinates;

    DefaultCoodinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }
}
