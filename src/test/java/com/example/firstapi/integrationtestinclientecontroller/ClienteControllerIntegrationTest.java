package com.example.firstapi.integrationtestinclientecontroller;

import com.example.firstapi.model.Cliente;
import com.example.firstapi.repository.ClienteRepository;
import com.example.firstapi.util.ClienteCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

import java.util.List;

// Define porta aleatória toda vez que os teste forem executados
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase  //Configuração de banco, utiliza valor em  memória
class ClienteControllerIntegrationTest {
    //
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private ClienteRepository clienteRepository;


    @Test
    @DisplayName("Listar todos, retornar lista de cliente quando der sucesso")
    void listAllReturnListOfClientWhenSuccessful(){
        // 2 teste do comportamento
        Cliente savedCliente = clienteRepository.save(ClienteCreator.createClienteToBeSaved());
        String expectedName = savedCliente.getNome();

        List<Cliente> clientes = testRestTemplate.exchange("/clientes", HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Cliente>>() {
                }).getBody();

        Assertions.assertThat(clientes)
                .isNotNull()
                .isNotEmpty();


        Assertions.assertThat(clientes.get(0).getNome()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("Procura por id,  retorna cliente quando der sucesso")
    void findByIdReturnClienteWhenSuccessful(){
        Cliente savedCliente = clienteRepository.save(ClienteCreator.createClienteToBeSaved());
        Long expectedId = savedCliente.getId();

        Cliente cliente = testRestTemplate.getForObject("/clientes/{id}", Cliente.class, expectedId);

        Assertions.assertThat(cliente).isNotNull();
        Assertions.assertThat(cliente.getId()).isNotNull().isEqualTo(expectedId);
    }

    /*
    @Test
    @DisplayName("Salva, retorna cliente quando der sucesso")
    void save_ReturnCliente_WhenSuccessful() {
        final var cliente = clienteRepository.save(ClientePostRequestBodyCreator.createClientePostRequestBody())
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
