package org.id2k1149.dinerVoting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class WebAppConfig {
    private ApiInfo apiDetails() {
        return new ApiInfo(
                "Diner Voting API",
                "API documentation",
                "1.0",
                "auth",
                new Contact("id2k1149", "id2k1149.org", "id2k1149@gmail.com"),
                "MIT License",
                "https://opensource.org/licenses/mit-license.php",
                Collections.emptyList());
    }

    @Bean
    public Docket swaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/api/v1/**"))
                .apis(RequestHandlerSelectors.basePackage(("org.id2k1149")))
                .build()
                .apiInfo(apiDetails());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}