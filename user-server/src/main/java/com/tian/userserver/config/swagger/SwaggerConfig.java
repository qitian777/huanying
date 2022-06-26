package com.tian.userserver.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: swagger配置类
 * @Author QiGuang
 * @Date 2022/6/13
 * @Version 1.0
 */
@Configuration
@EnableSwagger2
@Profile({"test","dev"})
public class SwaggerConfig {

    /**
     * @Author QiGuang
     * @Description Swagger实例Bean是Docket，所以通过配置Docket实例来配置Swagger。
     * @Param
     */
    @Bean
    public Docket docker() {
        return new Docket(DocumentationType.SWAGGER_2)
                // Docket 实例关联上ApiInfo
                .apiInfo(apiInfo())
                // 通过.select()方法，去配置扫描接口,RequestHandlerSelectors配置如何扫描接口
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tian.userserver.controller"))
                // 配置如何通过path过滤,这里扫描所有请求接口
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    /**
     * @Author QiGuang
     * @Description 通过apiInfo()属性配置文档信息
     * @Param
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("后端服务器")
                .description("接口文档")
                .contact(new Contact("QiGuang", "http://localhost:8081/doc.html", "1097598544@qq.com"))
                .version("V1.0")
                .build();
    }

    private List<ApiKey> securitySchemes() {
        //设置请求头信息
        List<ApiKey> apiKeys = new ArrayList<>();
        apiKeys.add(new ApiKey("Authorization", "Authorization", "header"));
        return apiKeys;
    }

    private List<SecurityContext> securityContexts() {
        //设置需要登录认证的路径
        List<SecurityContext> list = new ArrayList<>();
        list.add(getContextByPath());
        return list;
    }

    private SecurityContext getContextByPath() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex("/user/.*"))
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> list = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        list.add(new SecurityReference("Authorization", authorizationScopes));
        return list;
    }
}

