package com.fluig.identity;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

@RestController
@SpringBootApplication
public class AuthServerApplication extends SpringBootServletInitializer {

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(AuthServerApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

    @Bean
    CommandLineRunner init(final DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
        jdbcUserDetailsManager.setDataSource(dataSource);
        JdbcClientDetailsService jdbcClientDetailsService = new JdbcClientDetailsService(dataSource);
        return new CommandLineRunner() {
            @Override
            public void run(String... arg0) throws Exception {

                if(!jdbcUserDetailsManager.userExists("user")) {
                    jdbcUserDetailsManager.createUser(new User("user", "user", Lists.newArrayList(new SimpleGrantedAuthority("READ"))));
                }
                if(!jdbcUserDetailsManager.userExists("admin")) {
                    jdbcUserDetailsManager.createUser(new User("admin", "admin", Lists.newArrayList(new SimpleGrantedAuthority("READ"), new SimpleGrantedAuthority("WRITE"))));
                }
                if(jdbcClientDetailsService.listClientDetails().isEmpty()) {
                    Collection<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("READ"), new SimpleGrantedAuthority("WRITE"));
                    Collection<String> authoritiedGrantTypes = Arrays.asList("implicit", "refresh_token", "password", "authorization_code", "client_credentials");
                    BaseClientDetails clientDetails = new BaseClientDetails();
                    clientDetails.setClientId("acme");
                    clientDetails.setClientSecret("acmesecret");
                    clientDetails.setScope(Collections.singleton("openid"));
                    clientDetails.setAuthorities(authorities);
                    clientDetails.setAuthorizedGrantTypes(authoritiedGrantTypes);
                    clientDetails.setRegisteredRedirectUri(Collections.singleton("https://www.google.com.br"));
                    jdbcClientDetailsService.addClientDetails(clientDetails);
                }

            }
        };

    }
}
