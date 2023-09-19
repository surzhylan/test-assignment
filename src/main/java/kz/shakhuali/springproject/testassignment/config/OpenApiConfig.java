package kz.shakhuali.springproject.testassignment.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi(){
        Info info = new Info()
                .title("Test assignment")
                .description("Test assignment contract")
                .contact(new Contact()
                        .email("aliyashakhuali@mail.ru"))
                .version("1.0.0");

        OpenAPI openAPI = new OpenAPI().info(info);

        openAPI.externalDocs(new ExternalDocumentation()
                .description("Репозиторий проекта в GitHub")
                .url("https://github.com/surzhylan/test-assignment"));

        return openAPI;
    }
}
