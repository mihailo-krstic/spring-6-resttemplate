package guru.springframework.spring7resttemplate.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.restclient.RestTemplateBuilder;
import org.springframework.boot.restclient.autoconfigure.RestTemplateBuilderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.Duration;

@Configuration
public class RestTemplateBuilderConfig {

    @Value("${rest.template.rootUrl}")
    String rootUrl;

    @Bean
    RestTemplateBuilder restTemplateBuilder(RestTemplateBuilderConfigurer configurer) {

        assert configurer != null;

        RestTemplateBuilder builder = configurer.configure(new RestTemplateBuilder())
                .connectTimeout(Duration.ofSeconds(5))
                .readTimeout(Duration.ofSeconds(2));

        DefaultUriBuilderFactory uriBuilderFactory = new
                DefaultUriBuilderFactory(rootUrl);


        return builder.uriTemplateHandler(uriBuilderFactory);

    }
}
