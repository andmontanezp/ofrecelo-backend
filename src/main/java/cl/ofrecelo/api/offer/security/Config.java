package cl.ofrecelo.api.offer.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.*;


@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Config
        extends WebSecurityConfigurerAdapter {

    @Value("${security.signing-key}")
    private String signingKey;

    @Value("${security.encoding-strength}")
    private Integer encodingStrength;

    @Value("${security.security-realm}")
    private String securityRealm;

    private CustomUserDetailsService userDetailsService;

    @Autowired
    public Config(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
    throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http)
    throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().httpBasic().realmName(securityRealm)
                //    .and()
                //    .authorizeRequests().antMatchers("/oauth/**").permitAll()
                //    .antMatchers("/*/**").authenticated()
                .and().csrf().disable();
    }

    @Bean
    protected PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(encodingStrength);
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager()
    throws Exception {
        return super.authenticationManager();
    }

    @Bean
    @Qualifier("TenantCustomAccessTokenConverter")
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new TenantJwtAccessTokenConverter();
        converter.setSigningKey(signingKey);
        return converter;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Autowired
    public void setJwtAccessTokenConverter(JwtAccessTokenConverter jwtAccessTokenConverter) {
        jwtAccessTokenConverter.setAccessTokenConverter(defaultAccessTokenConverter());
    }


    @Bean
    DefaultAccessTokenConverter defaultAccessTokenConverter() {
        return new TenantAccessTokenConverter();
    }

}
