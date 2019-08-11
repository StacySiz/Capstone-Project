package ru.stacy.capstone.configuration;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Date;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  public static final String AUTHORIZATION_HEADER = "Authorization";


  @Bean
  public Docket swaggerSpringfoxDocket() {

    Docket docket = new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(metadata())
            .pathMapping("/")
            .apiInfo(ApiInfo.DEFAULT)
            .forCodeGeneration(true)
            .genericModelSubstitutes(ResponseEntity.class)
            .ignoredParameterTypes(Pageable.class)
            .ignoredParameterTypes(java.sql.Date.class)
            .directModelSubstitute(java.time.LocalDate.class, java.sql.Date.class)
            .directModelSubstitute(java.time.ZonedDateTime.class, Date.class)
            .directModelSubstitute(java.time.LocalDateTime.class, Date.class)
            .securityContexts(Lists.newArrayList(securityContext()))
            .securitySchemes(Lists.newArrayList(apiKey()))
            .useDefaultResponseMessages(false);

    docket = docket.select()
            .build();
    return docket;
  }

  private ApiKey apiKey() {
    return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder()
            .securityReferences(defaultAuth())
            .build();
  }

  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope
            = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Lists.newArrayList(
            new SecurityReference("JWT", authorizationScopes));
  }

  private ApiInfo metadata() {
    return new ApiInfoBuilder()//
            .title("Capstone project")//
        .description(
                "description")//TODO add description
        .version("1.0.0")//
            .contact(new Contact("Stacy", null, "nastyasizova@gmail.com"))//
        .build();
  }

}
