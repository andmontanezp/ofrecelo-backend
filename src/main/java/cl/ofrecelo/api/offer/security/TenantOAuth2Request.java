package cl.ofrecelo.api.offer.security;


import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.oauth2.provider.OAuth2Request;

@EqualsAndHashCode(callSuper = true)
@Data
public class TenantOAuth2Request
        extends OAuth2Request {

    private static final long serialVersionUID = -396143215213000470L;
    private Long tenantId;
    private Long userId;
    private String timeZone;
    private String language;

    TenantOAuth2Request(OAuth2Request other) {
        super(other);
        tenantId = null;
        userId = null;
        timeZone = null;
    }

}