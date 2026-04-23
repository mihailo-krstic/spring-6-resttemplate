package guru.springframework.spring7resttemplate.config;

import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.boot.restclient.autoconfigure.RestTemplateBuilderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.Duration;

@Configuration
public class RestTemplateBuilderConfig {

    @Bean
    RestTemplateBuilder restTemplateBuilder(RestTemplateBuilderConfigurer configurer) {

        RestTemplateBuilder builder = configurer.configure(new RestTemplateBuilder())
                .connectTimeout(Duration.ofSeconds(5))
                .readTimeout(Duration.ofSeconds(2));

        DefaultUriBuilderFactory uriBuilderFactory = new
                DefaultUriBuilderFactory("http://localhost:8080");


        return builder.uriTemplateHandler(uriBuilderFactory);

    }
}
