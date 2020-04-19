package cl.ofrecelo.api.offer.security;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.Collections;


@Configuration
@EnableAuthorizationServer
public class AuthServerOAuth2Config
        extends AuthorizationServerConfigurerAdapter {

    @Value("${security.jwt.client-id}")
    private String clientId;

    @Value("${security.jwt.client-secret}")
    private String clientSecret;

    @Value("${security.jwt.grant-type}")
    private String grantType;

    @Value("${security.jwt.scope-read}")
    private String scopeRead;

    @Value("${security.jwt.scope-write}")
    private final String scopeWrite = "write";

    @Value("${security.jwt.resource-ids}")
    private String resourceIds;

    @Value("${security.jwt.token.timeoutInMin}")
    private Integer tokenTimeOutInMinutes;

    private final TokenStore tokenStore;

    private final JwtAccessTokenConverter accessTokenConverter;

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService customUserDetailsService;

    private final PasswordEncoder passwordEncoder;

    public AuthServerOAuth2Config(TokenStore tokenStore, @Qualifier("TenantCustomAccessTokenConverter") JwtAccessTokenConverter accessTokenConverter, AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) {
        this.tokenStore = tokenStore;
        this.accessTokenConverter = accessTokenConverter;
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void configure(ClientDetailsServiceConfigurer configurer)
    throws Exception {
        String[] grantTypes = new String[grantType.split(",").length];
        int i = 0;
        for (String s : grantType.split(","))
            grantTypes[i++] = s;
        configurer.inMemory().withClient(clientId).secret(passwordEncoder.encode(clientSecret)).authorizedGrantTypes(grantTypes).scopes(scopeRead, scopeWrite).resourceIds(resourceIds).accessTokenValiditySeconds(tokenTimeOutInMinutes * 60);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
        enhancerChain.setTokenEnhancers(Collections.singletonList(accessTokenConverter));
        endpoints.tokenStore(tokenStore).accessTokenConverter(accessTokenConverter).tokenEnhancer(enhancerChain).authenticationManager(authenticationManager).userDetailsService(customUserDetailsService);
    }
}
