package com.meetravel.global.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Collections;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
    private final Environment environment;

    private static final String BASE_PACKAGE = "com.meetravel.domain";

    @Getter
    public enum ApiUrl {
        AUTH("auth", "/auth"),
        SIGN_UP("sign_up", "/signup"),
        USER("user", "/users"),
        ADMIN("admin", "/admin"),
        MATCHING_FORM("matching_form","/matching-form"),
        CHAT("chat", "/chat-rooms"),
        PLACE("place", "/places"),
        FILE("file", "/files");

        private final String group;
        private final String urlPrefix;

        ApiUrl(String group, String urlPrefix) {
            this.group = group;
            this.urlPrefix = urlPrefix;
        }
    }

    @Bean
    public OpenAPI openAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name("Authorization");
        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        Server prodServer = new Server()
                .url("https://api.meetravel.life");
        Server devServer = new Server()
                .url("http://175.211.58.83:10200");
        Server localServer = new Server()
                .url("http://localhost:" + environment.getProperty("server.port"));

        return new OpenAPI()
                .servers(List.of(prodServer, devServer, localServer))
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                .security(Collections.singletonList(securityRequirement));
    }

    @Bean
    public GroupedOpenApi authApi() {
        final String name = ApiUrl.AUTH.getGroup();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.AUTH.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + "." + ApiUrl.AUTH.getGroup())
                .build();
    }

    @Bean
    public GroupedOpenApi signUpApi() {
        final String name = ApiUrl.SIGN_UP.getGroup();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.SIGN_UP.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + "." + ApiUrl.SIGN_UP.getGroup())
                .build();
    }

    @Bean
    public GroupedOpenApi userApi() {
        final String name = ApiUrl.USER.getGroup();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.USER.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + "." + ApiUrl.USER.getGroup())
                .build();
    }

    @Bean
    public GroupedOpenApi matchingFormApi() {
        final String name = ApiUrl.MATCHING_FORM.getGroup();
        return GroupedOpenApi.builder()
                .group(name)
                .pathsToMatch(ApiUrl.MATCHING_FORM.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + "." + ApiUrl.MATCHING_FORM.getGroup())
                .build();
    }

    @Bean
    public GroupedOpenApi chatApi() {
        return GroupedOpenApi.builder()
                .group(ApiUrl.CHAT.getGroup())
                .pathsToMatch(ApiUrl.CHAT.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + "." + "chatroom")
                .build();
    }

    @Bean
    public GroupedOpenApi placeApi() {
        return GroupedOpenApi.builder()
                .group(ApiUrl.PLACE.getGroup())
                .pathsToMatch(ApiUrl.PLACE.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + "." + "place")
                .build();
    }

    @Bean
    public GroupedOpenApi fileApi() {
        return GroupedOpenApi.builder()
                .group(ApiUrl.FILE.getGroup())
                .pathsToMatch(ApiUrl.FILE.getUrlPrefix() + "/**")
                .packagesToScan(BASE_PACKAGE + "." + "file")
                .build();
    }
}
