package it.sevenbits.cards.config;

import it.sevenbits.cards.web.utils.DomainResolver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ProfilesConfig {

    @Value("${spring.domain}")
    private String domain;

    @Bean
    public DomainResolver domainResolver(){
        DomainResolver domainResolver = new DomainResolver();
        domainResolver.setDomain(domain);
        return domainResolver;
    }
}

