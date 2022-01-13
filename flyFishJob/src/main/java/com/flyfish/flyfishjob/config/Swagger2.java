package com.flyfish.flyfishjob.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author yumingjun
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
public class Swagger2 {
    /**
     * UI页面显示信息
     */
    private final String SWAGGER2_API_BASEPACKAGE = "com.flyfish.flyfishjob";

    private final String SWAGGER2_API_TITLE = "飞鱼任务";
    /** API 描述 */
    private final String SWAGGER2_API_DESCRIPTION = "飞鱼任务接口说明";
    private final String SWAGGER2_API_CONTACT = "Demo";
    private final String SWAGGER2_API_VERSION = "1.0";

    /**
     * createRestApi
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(SWAGGER2_API_BASEPACKAGE))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * apiInfo
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(SWAGGER2_API_TITLE)
                .description(SWAGGER2_API_DESCRIPTION)
                .contact(SWAGGER2_API_CONTACT)
                .version(SWAGGER2_API_VERSION)
                .build();
    }

}
