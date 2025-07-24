package lhl.hana.xk.review_multi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/OpenAPI 配置类
 * 提供API文档的基本信息和配置
 * 
 * @author hana
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Review Multi API")
                        .version("1.0.0")
                        .description("多数据源商品评论系统 API 文档")
                        .contact(new Contact()
                                .name("Hana")
                                .email("hana@example.com")
                                .url("https://github.com/hana"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}