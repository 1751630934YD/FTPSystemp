package com.itheima.ftpfile.swagger;

import io.swagger.v3.oas.annotations.servers.Server;
import springfox.documentation.service.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
        @Bean
        public Docket api() {
            return new Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("com.itheima.ftpfile.controller"))
                    .paths(PathSelectors.any())
                    .build()
                    .apiInfo(new ApiInfoBuilder().title("FTP文件接口")
                            .description("")
                            .version("1.0.0")
                            .build());

        }



    }

