package com.example.firstapi.controllertestintegration;

import com.example.firstapi.model.Cliente;
import com.example.firstapi.repository.ClienteRepository;
import com.example.firstapi.requestsdto.ClientePostRequestBody;
import com.example.firstapi.requestsdto.ClientePutRequestBody;
import com.example.firstapi.util.ClienteCreator;
import com.example.firstapi.util.ClientePostRequestBodyCreator;
import com.example.firstapi.util.ClientePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase  //Configuração de banco utiliza a configuração em memória
public class ClienteControllerIntegrationTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ClienteRepository clienteRepository;

    @LocalServerPort
    private int port;

    @Test
    @DisplayName("Listar todos, retornar lista de cliente quando der sucesso")
    void list_ReturnListOfClient_WhenSuccessful(){
        // 2 teste do comportamento
        Cliente savedCliente = clienteRepository.save(ClienteCreator.createClienteToBeSaved());
        String expectedName = savedCliente.getNome();

        List<Cliente> clientes = testRestTemplate.exchange("/clientes", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Cliente>>() {
                }).getBody();

        Assertions.assertThat(clientes)
                .isNotNull()
                .hasSize(1)
                .isNotEmpty();


        Assertions.assertThat(clientes.get(0).getNome()).isEqualTo(expectedName);
    }


    @Test
    @DisplayName("Procura por id,  retorna cliente quando der sucesso")
    void findById_ReturnCliente_WhenSuccessful(){
        Cliente savedCliente = clienteRepository.save(ClienteCreator.createClienteToBeSaved());
        Long expectedId = savedCliente.getId();


    }

    /*
    @Test
    @DisplayName("Salva, retorna cliente quando der sucesso")
    void save_ReturnCliente_WhenSuccessful() {
        final var cliente = clienteController.save(ClientePostRequestBodyCreator.createClientePostRequestBody())
                .getBody();
        Assertions.assertThat(cliente).isNotNull().isEqualTo(ClienteCreator.createValidClient());
    }

    @Test
    @DisplayName("Atualiza, atualizar cliente quando der sucesso")
    void replace_UpdateCliente_WhenSuccessful(){
        final var entity = clienteController.replace(ClientePutRequestBodyCreator
                .createClientePutRequestBody());
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }

    @Test
    @DisplayName("Deleta, remove cliente quando der sucesso")
    void delete_RemoveCliente_WhenSuccessful(){
        final var clientById = clienteController.deleteClientById(1);

        Assertions.assertThat(clientById).isNotNull();
        Assertions.assertThat(clientById.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

     */

}
