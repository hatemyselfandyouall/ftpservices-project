package com.insigma.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@EnableWebMvc
//@Profile("dev")
public class SwaggerController extends WebMvcConfigurationSupport {

    public static final String SWAGGER_SCAN_BASE_PACKAGE = "com.insigma.web";

    @Bean
    public Docket customDocket() {
        //
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage(SWAGGER_SCAN_BASE_PACKAGE)).build()
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("", "", "");
        return new ApiInfo("后台API接口",//大标题 title
                "",//小标题
                "",//版本
                "",//termsOfServiceUrl
                contact,//作者
                "",//链接显示文字
                "e"//网站链接
        );
    }


}
