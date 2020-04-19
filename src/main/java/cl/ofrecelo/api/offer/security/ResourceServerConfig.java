package cl.ofrecelo.api.offer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.oauth2.config.annotation.web.configuration.*;
import org.springframework.security.oauth2.config.annotation.web.configurers.*;
import org.springframework.security.oauth2.provider.token.*;

@Configuration
@EnableResourceServer
public class ResourceServerConfig
        extends ResourceServerConfigurerAdapter {

    private RestAuthenticationEntryPoint unauthorizedHandler;

    private ResourceServerTokenServices tokenServices;


    @Value("${security.jwt.resource-ids}")
    private String resourceIds;

    @Autowired
    public ResourceServerConfig(RestAuthenticationEntryPoint unauthorizedHandler, ResourceServerTokenServices tokenServices) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.tokenServices = tokenServices;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(resourceIds).tokenServices(tokenServices);
    }

    @Override
    public void configure(HttpSecurity http)
    throws Exception {
        http.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and().requestMatchers().and().authorizeRequests()
                .antMatchers("/oauth/**","/user/**").permitAll()
                .antMatchers("/*/**").authenticated();
    }

}