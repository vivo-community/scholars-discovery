package edu.tamu.scholars.middleware.config;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.PATCH;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.tamu.scholars.middleware.auth.config.TokenConfig;
import edu.tamu.scholars.middleware.auth.handler.CustomAccessDeniedExceptionHandler;
import edu.tamu.scholars.middleware.auth.handler.CustomAuthenticationEntryPoint;
import edu.tamu.scholars.middleware.auth.handler.CustomAuthenticationFailureHandler;
import edu.tamu.scholars.middleware.auth.handler.CustomAuthenticationSuccessHandler;
import edu.tamu.scholars.middleware.auth.handler.CustomLogoutSuccessHandler;
import edu.tamu.scholars.middleware.config.model.MiddlewareConfig;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${spring.profiles.active:default}")
    private String profile;

    @Value("${spring.h2.console.enabled:false}")
    private boolean h2ConsoleEnabled;

    @Value("${spring.data.rest.authorize-hal-browser:false}")
    private boolean halBrowserAuthorized;

    @Autowired
    private MiddlewareConfig config;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityExpressionHandler<FilterInvocation> securityExpressionHandler;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TokenService tokenService() throws NoSuchAlgorithmException {
        KeyBasedPersistenceTokenService tokenService = new KeyBasedPersistenceTokenService();
        TokenConfig tokenConfig = config.getAuth().getToken();
        tokenService.setServerInteger(tokenConfig.getServerInteger());
        tokenService.setServerSecret(tokenConfig.getServerSecret());
        tokenService.setPseudoRandomNumberBytes(tokenConfig.getPseudoRandomNumberBytes());
        tokenService.setSecureRandom(SecureRandom.getInstanceStrong());
        return tokenService;
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration embedConfig = new CorsConfiguration();
        embedConfig.setAllowCredentials(true);
        embedConfig.addAllowedOrigin("*");
        embedConfig.addAllowedHeader("Origin");
        embedConfig.addAllowedHeader("Content-Type");
        embedConfig.addAllowedMethod("GET");
        embedConfig.addAllowedMethod("OPTION");

        source.registerCorsConfiguration("/displayViews/search/findByName", embedConfig);

        source.registerCorsConfiguration("/individual/{id}", embedConfig);

        source.registerCorsConfiguration("/individual/search/findByIdIn", embedConfig);

        CorsConfiguration primaryConfig = new CorsConfiguration();
        primaryConfig.setAllowCredentials(true);
        primaryConfig.setAllowedOrigins(config.getAllowedOrigins());
        primaryConfig.setAllowedMethods(Arrays.asList("GET", "DELETE", "PUT", "POST", "PATCH", "OPTIONS"));
        primaryConfig.setAllowedHeaders(Arrays.asList("Authorization", "Origin", "Content-Type"));

        // NOTE: most general path must be last
        source.registerCorsConfiguration("/**/*", primaryConfig);
        return new CorsFilter(source);
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        if(authorizeHalBrowser()) {
            // NOTE: permit all access for HAL browser
            http
                .authorizeRequests()
                    .antMatchers("/**")
                        .permitAll();
        }
        if(enableH2Console()) {
            // NOTE: permit all access to h2console
            http
                .authorizeRequests()
                    .antMatchers("/h2console/**")
                        .permitAll()
                .and()
                    .headers()
                        .frameOptions()
                            .sameOrigin();
        }
        http
            .authorizeRequests()
                .expressionHandler(securityExpressionHandler)

                .antMatchers("/connect/**")
                    .permitAll()

                .antMatchers(PATCH,
                        "/directoryViews/{id}",
                        "/discoveryViews/{id}",
                        "/displayViews/{id}",
                        "/themes/{id}",
                        "/users/{id}"
                    )
                    .hasRole("ADMIN")

                .antMatchers(POST,
                        "/registration",
                        "/graphql"
                    )
                    .permitAll()
                .antMatchers(POST,
                        "/directoryViews/{id}",
                        "/discoveryViews/{id}",
                        "/displayViews/{id}",
                        "/themes/{id}"
                    )
                    .hasRole("ADMIN")
                .antMatchers(POST,
                        "/actuator/restart"
                    )
                    .hasRole("SUPER_ADMIN")
                .antMatchers(POST, "/users/{id}")
                    .denyAll()

                .antMatchers(PUT, "/registration")
                    .permitAll()
                .antMatchers(PUT,
                        "/directoryViews/{id}",
                        "/discoveryViews/{id}",
                        "/displayViews/{id}",
                        "/themes/{id}"
                    )
                    .hasRole("ADMIN")
                .antMatchers(PUT, "/users/{id}")
                    .denyAll()

                .antMatchers(GET,
                        "/actuator/health",
                        "/actuator/info",
                        "/api",
                        "/gui",
                        "/graphql",
                        "/registration",
                        "/themes/search/active",
                        "/directoryViews", "/directoryViews/{id}",
                        "/discoveryViews", "/discoveryViews/{id}",
                        "/displayViews", "/displayViews/{id}", "/displayViews/search/findByTypesIn", "/displayViews/search/findByName",
                        "/individual", "/individual/{id}", "/individual/{id}/export", "/individual/search/findByIdIn", "/individual/search/faceted", "/individual/search/export", "/individual/search/count", "/individual/search/recently-updated"
                    )
                    .permitAll()
                .antMatchers(GET,
                        "/users",
                        "/users/{id}",
                        "/themes",
                        "/themes/{id}"
                    )
                    .hasRole("ADMIN")

                .antMatchers(DELETE,
                        "/directoryViews/{id}",
                        "/discoveryViews/{id}",
                        "/displayViews/{id}",
                        "/themes/{id}"
                    )
                    .hasRole("ADMIN")
                .antMatchers(DELETE, "/users/{id}")
                    .hasRole("SUPER_ADMIN")

                .anyRequest()
                    .authenticated()
            .and()
                .formLogin()
                    .successHandler(authenticationSuccessHandler())
                    .failureHandler(authenticationFailureHandler())
                        .permitAll()
            .and()
                .logout()
                    .deleteCookies("remove")
                    .invalidateHttpSession(true)
                    .logoutSuccessHandler(logoutSuccessHandler())
                        .permitAll()
            .and()
                .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint())
                    .accessDeniedHandler(accessDeniedHandler())
            .and()
                .requestCache()
                    .requestCache(nullRequestCache())
            .and()
                .cors()
            .and()
                .csrf()
                    .disable();
       // @formatter:on
    }

    private CustomAuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler(objectMapper);
    }

    private CustomAuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    private CustomLogoutSuccessHandler logoutSuccessHandler() {
        return new CustomLogoutSuccessHandler(messageSource);
    }

    private CustomAuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    private CustomAccessDeniedExceptionHandler accessDeniedHandler() {
        return new CustomAccessDeniedExceptionHandler();
    }

    private NullRequestCache nullRequestCache() {
        return new NullRequestCache();
    }

    private boolean authorizeHalBrowser() {
        return halBrowserAuthorized && !profile.equals("production") && !profile.equals("test");
    }

    private boolean enableH2Console() {
        return h2ConsoleEnabled && !profile.equals("production");
    }

}
