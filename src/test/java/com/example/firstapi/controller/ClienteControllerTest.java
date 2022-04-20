package com.example.firstapi.controller;

import com.example.firstapi.model.Cliente;
import com.example.firstapi.requests.ClientePostRequestBody;
import com.example.firstapi.requests.ClientePutRequestBody;
import com.example.firstapi.service.ClienteService;
import com.example.firstapi.util.ClienteCreator;
import com.example.firstapi.util.ClientePostRequestBodyCreator;
import com.example.firstapi.util.ClientePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;


//@SpringBootTest
// inicializa o contexto inteiro da aplicação
// ExtendWith
// utlizamos apena o jUnit com spring, fica mais rapido testar a aplicação
@ExtendWith(SpringExtension.class)
class ClienteControllerTest {
    // @InjectMocks quando quero testar a classe em si
    @InjectMocks
    private ClienteController clienteController;

    // @Mock é para todas os métodos que estão sendo utilizadas dentro do ClienteService
    @Mock
    private ClienteService clienteServiceMock;

    @BeforeEach
    void setUp(){
        // 1- Criando comportamento para o mockito e no passo 2- o teste
        BDDMockito.when(clienteServiceMock.listAll())
                .thenReturn(List.of(ClienteCreator.createValidClient()));

        BDDMockito.when(clienteServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(ClienteCreator.createValidClient());

        BDDMockito.when(clienteServiceMock.save(ArgumentMatchers.any(ClientePostRequestBody.class)))
                .thenReturn(ClienteCreator.createValidClient());

        //Quando retorna void, usa "não faça nada", "quando"
        BDDMockito.doNothing().when(clienteServiceMock).replace(ArgumentMatchers.any(ClientePutRequestBody.class));

        BDDMockito.doNothing().when(clienteServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("Listar todos, retornar lista de cliente quando der sucesso")
    void list_ReturnListOfClient_WhenSuccessful(){
        // 2 teste do comportamento
        String expectedName = ClienteCreator.createValidClient().getNome();
        List<Cliente> clienteList = clienteController.listAll().getBody();

        Assertions.assertThat(clienteList)
                        .isNotNull()
                        .isNotEmpty()
                        .hasSize(1);

        Assertions.assertThat(clienteList.get(0).getNome()).isEqualTo(expectedName);
    }

    @Test
    @DisplayName("findById returns cliente when successful")
    void findById_ReturnCliente_WhenSuccessful(){
        Long expectedId = ClienteCreator.createValidClient().getId();
        Cliente cliente = clienteController.findById(1).getBody();

        Assertions.assertThat(cliente).isNotNull();
        Assertions.assertThat(cliente.getId())
                .isNotNull()
                .isEqualTo(expectedId);
    }

    @Test
    @DisplayName("Salva cliente retorno quando der sucesso")
    void save_ReturnCliente_WhenSuccessful() {
        Cliente cliente = clienteController.save(ClientePostRequestBodyCreator.createClientePostRequestBody())
                .getBody();
        Assertions.assertThat(cliente).isNotNull().isEqualTo(ClienteCreator.createValidClient());
    }

    @Test
    @DisplayName("Atualiza cliente quando der sucesso")
    void replace_UpdateCliente_WhenSuccessful(){
        ResponseEntity<Void> entity = clienteController.replace(ClientePutRequestBodyCreator
                .createClientePutRequestBody());
        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }

    @Test
    @DisplayName("Deleta cliente quando der sucesso")
    void delete_RemoveCliente_WhenSuccessful(){
        ResponseEntity<Void> entity = clienteController.deleteClientById(1);

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}