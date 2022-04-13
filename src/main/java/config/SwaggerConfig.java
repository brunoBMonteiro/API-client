package config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("API REST Client")
                                .description("Api de cadastro de clientes criado usando spring docs, " +
                                        "contendo nome, cpf, idade, endereço")
                                .termsOfService("https://swagger.io/terms/")
                                .version("1.0.0")
                                .license(new License().name("Apache 2.0"))
                                .contact(new Contact()
                                        .email("brunobilheri@hotmail.com")
                                        .name("Bruno Monteiro"))
                );
    }




}
