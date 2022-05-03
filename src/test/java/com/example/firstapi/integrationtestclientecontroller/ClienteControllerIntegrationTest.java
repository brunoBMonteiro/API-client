package com.example.firstapi.integrationtestclientecontroller;

import com.example.firstapi.model.Cliente;
import com.example.firstapi.repository.ClienteRepository;
import com.example.firstapi.requestsdto.ClientePostRequestBody;
import com.example.firstapi.util.ClienteCreator;
import com.example.firstapi.util.ClientePostRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

    @Test
    @DisplayName("Salva, retorna cliente quando der sucesso")
    void saveReturnClienteWhenSuccessful() {
        ClientePostRequestBody clientePostRequestBody = ClientePostRequestBodyCreator.createClientePostRequestBody();

        ResponseEntity<Cliente> clienteResponseEntity = testRestTemplate.postForEntity("/clientes", clientePostRequestBody, Cliente.class);

        Assertions.assertThat(clienteResponseEntity).isNotNull();
        Assertions.assertThat(clienteResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Assertions.assertThat(clienteResponseEntity.getBody()).isNotNull();
        Assertions.assertThat(clienteResponseEntity.getBody().getId()).isNotNull();
    }

    @Test
    @DisplayName("Deleta, remove cliente quando der sucesso")
    void deleteRemoveClienteWhenSuccessful(){
        final var savedCliente = clienteRepository.save(ClienteCreator.createClienteToBeSaved());

        ResponseEntity<Void> clienteResponseEntity = testRestTemplate.exchange("/clientes/{id}",
                HttpMethod.DELETE, null, Void.class, savedCliente.getId());

        Assertions.assertThat(clienteResponseEntity).isNotNull();
        Assertions.assertThat(clienteResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}
