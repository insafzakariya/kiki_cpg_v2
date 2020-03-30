package com.kiki_cpg.development.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
//@ConfigurationProperties(prefix = "helios.admin")
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket productApi() {

        Docket docket =
                new Docket(DocumentationType.SWAGGER_2).select()
                        .apis(RequestHandlerSelectors.basePackage("com")).build()
                        .apiInfo(metaData());
//        docket.host(swaggerHost);
        return docket;
    }

    private ApiInfo metaData() {

        String title = "KIKI CRON API ";
        String description = "";
        String version = "";
        String termsOfServiceUrl = "";
        Contact contactPerson = new Contact("", "", "" + "");
        String license = "";
        String licenseUrl = "";

        return new ApiInfo(title, description, version, termsOfServiceUrl, contactPerson, license, licenseUrl);
    }


}
