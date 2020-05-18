package cl.ofrecelo.api.offer.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Address {

    private String district;

    public static Address fromDistrict(String district) {
        return new Address(district);
    }
}
