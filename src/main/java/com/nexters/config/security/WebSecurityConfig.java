package com.nexters.config.security;

import com.nexters.config.security.oauth2.CustomOAuthProvider;
import com.nexters.config.security.oauth2.MyOAuth2AuthorizedClientService;
import com.nexters.config.security.oauth2.MyOAuth2SuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import java.util.Collections;

/**
 * Created by JaeeonJin on 2018-08-02.
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RESTAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder,
                             RESTAuthenticationEntryPoint authenticationEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // disables cors and csrf
        http.cors().and().csrf().disable();

        // authenticate
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/error/**").permitAll()
                .antMatchers("/actuartor/**").permitAll()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/login/**").permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .successHandler(new MyOAuth2SuccessHandler());

        // set filters
        http
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()));


        // 401 error
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint);

        // disables session creation on Spring Security
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService(MyOAuth2AuthorizedClientService myOAuth2AuthorizedClientService) {
        return myOAuth2AuthorizedClientService;
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        final String kakaoClientId = "69779556a1e86bbc4883911ac6062eb8";
        final String kakaoClientSecret = "tm0oPlUDEyltKNYEpcjvtse5PGmPz5T5";
        final ClientRegistration clientRegistration = CustomOAuthProvider.KAKAO
                .getBuilder("kakao")
                .clientId(kakaoClientId)
                .clientSecret(kakaoClientSecret)
                .jwkSetUri("temp")
                .build();


        return new InMemoryClientRegistrationRepository(Collections.singletonList(
                clientRegistration
        ));
    }
}