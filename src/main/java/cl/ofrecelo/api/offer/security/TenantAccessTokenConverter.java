package cl.ofrecelo.api.offer.security;


import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

import java.util.Map;

public class TenantAccessTokenConverter
        extends DefaultAccessTokenConverter {

    TenantAccessTokenConverter() {
    }

    @Override
    public OAuth2Authentication extractAuthentication(Map<String, ?> map) {
        OAuth2Authentication authentication = super.extractAuthentication(map);

        // set environment data
        TenantOAuth2Request tenantOAuth2Request = new TenantOAuth2Request(authentication.getOAuth2Request());
        if (map.containsKey("language"))
            tenantOAuth2Request.setLanguage((String) map.get("language"));
        if (map.containsKey("userId"))
            tenantOAuth2Request.setUserId(Long.valueOf(map.get("userId").toString()));
        if (map.containsKey("tenantTimeZone"))
            tenantOAuth2Request.setTimeZone(String.valueOf(map.get("tenantTimeZone").toString()));


        return new OAuth2Authentication(tenantOAuth2Request, authentication.getUserAuthentication());
    }

}