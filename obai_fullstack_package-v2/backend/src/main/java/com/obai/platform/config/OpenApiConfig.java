package com.obai.platform.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("OBAI Platform API")
                .description("耦白 OBAI 肠道微生态稳态建设数据 AI 健康管理平台接口")
                .version("1.0.0"));
    }
}
