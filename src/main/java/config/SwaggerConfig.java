package config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
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
                                .description("Api para cadastro de clientes contendo nome, cpf, idade, endereço")
                                .version("1.0.0")
                                .contact(new Contact().email("brunobilheri@hotmail.com").name("Bruno Monteiro"))
                );
    }

}
