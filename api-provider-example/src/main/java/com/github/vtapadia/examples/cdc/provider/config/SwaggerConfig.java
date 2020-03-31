package com.github.vtapadia.examples.cdc.provider.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.StringVendorExtension;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * Provides the API version.
     * Should follow Semantic Versioning <MAJOR>.<MINOR>.<PATCH>
     */
    private static final String API_VERSION = "1.0.0";

    @Autowired(required = false)
    private BuildProperties properties;

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/api/.*"))
                .build()
                .pathMapping("/")
                .apiInfo(apiInfo())
                .genericModelSubstitutes(ResponseEntity.class)
                .useDefaultResponseMessages(false);
    }


    private ApiInfo apiInfo() {
        return new ApiInfo(
                Optional.ofNullable(properties).map(BuildProperties::getName).orElse("Unknown"),
                Optional.ofNullable(properties).map(BuildProperties::getArtifact).orElse("Unknown"),
                API_VERSION,
                null,
                getTeamContact(),
                null,
                null,
            getVendorExtensions());
    }

    private List<VendorExtension> getVendorExtensions() {
        return Arrays.asList(
            new StringVendorExtension("x-audience", "component-internal")
            );
    }

    private Contact getTeamContact() {
        return new Contact("Varesh Tapadia",
                "https://github.com/vtapadia",
                "");
    }

}
