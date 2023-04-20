package com.xxm.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.List;

@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {

    private static final String AUTHORIZATION = "Authorization";

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.family"))
                .paths(PathSelectors.any())
                .build()
                //增加全局token
                .securitySchemes(securitySchemes());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("家谱API接口文档")
                //网站描述
                .description("")
                //版本
                .version("1.0")
                //条款地址
                .termsOfServiceUrl("http://despairyoke.github.io/")
                //联系人
                .contact(new Contact("", "", ""))
                //协议
                .license("The Apache License")
                //协议url
                .licenseUrl("")
                .build();
    }


    private List<ApiKey> securitySchemes() {
        return Lists.newArrayList(new ApiKey(AUTHORIZATION, AUTHORIZATION, "header"));
    }
}
