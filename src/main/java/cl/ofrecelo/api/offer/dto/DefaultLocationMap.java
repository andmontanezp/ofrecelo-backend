package cl.ofrecelo.api.offer.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DefaultLocationMap implements Serializable {

    private Double longitude;

    private Double latitude;

}
