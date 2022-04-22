package com.example.firstapi.controller;


import com.example.firstapi.requestsdto.ClientePostRequestBody;
import com.example.firstapi.requestsdto.ClientePutRequestBody;
import com.example.firstapi.service.ClienteService;
import com.example.firstapi.util.ClienteCreator;
import com.example.firstapi.util.ClientePostRequestBodyCreator;
import com.example.firstapi.util.ClientePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;


//@SpringBootTest
// inicializa o contexto inteiro da aplicação
// ExtendWith
// utlizamos apena o jUnit com spring, fica mais rapido testar a aplicação
@ExtendWith(SpringExtension.class)
class ClienteControllerTest {
    // @InjectMocks quando quero testar a classe em si, clienteController
    @InjectMocks
    private ClienteController clienteController;

    // @Mock é para todas as classes que estão sendo utilizadas, injetadas dentro do ClienteService
    @Mock
    private ClienteService clienteServiceMock;


    @Test
    @DisplayName("Listar todos, retornar lista de cliente quando der sucesso")
    void list_ReturnListOfClient_WhenSuccessful(){
        // 2 teste do comportamento
        final var expectedName = ClienteCreator.createValidClient();

        Mockito.when(clienteServiceMock.listAll())
                .thenReturn(List.of(ClienteCreator.createValidClient()));

        final var clienteList = clienteController.listAll().getBody();

        Assertions.assertThat(clienteList)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        // "assegurar que"
        Assertions.assertThat(clienteList.get(0)).isEqualTo(expectedName);

    }

    @Test
    @DisplayName("Procura por id,  retorna cliente quando der sucesso")
    void findById_ReturnCliente_WhenSuccessful(){
        final var expectedId = ClienteCreator.createValidClient().getId();

        Mockito.when(clienteServiceMock.findByIdOrThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(ClienteCreator.createValidClient());

        final var cliente = clienteController.findById(1).getBody();

        Assertions.assertThat(cliente).isNotNull();
        Assertions.assertThat(cliente.getId())
                .isNotNull()
                .isEqualTo(expectedId);
    }



    @Test
    @DisplayName("Salva, retorna cliente quando der sucesso")
    void save_ReturnCliente_WhenSuccessful() {
        Mockito.when(clienteServiceMock.save(ArgumentMatchers.any(ClientePostRequestBody.class)))
                .thenReturn(ClienteCreator.createValidClient());

        final var cliente = clienteController.save(ClientePostRequestBodyCreator.createClientePostRequestBody())
                .getBody();

        Assertions.assertThat(cliente).isNotNull().isEqualTo(ClienteCreator.createValidClient());
    }

    @Test
    @DisplayName("Atualiza, atualizar cliente quando der sucesso")
    void replace_UpdateCliente_WhenSuccessful(){
        final var entity = clienteController.replace(ClientePutRequestBodyCreator
                .createClientePutRequestBody());

        Mockito.doNothing().when(clienteServiceMock).replace(ArgumentMatchers.any(ClientePutRequestBody.class));

        Assertions.assertThat(entity).isNotNull();
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }

    @Test
    @DisplayName("Deleta, remove cliente quando der sucesso")
    void delete_RemoveCliente_WhenSuccessful(){
        final var clientById = clienteController.deleteClientById(1);

        Mockito.doNothing().when(clienteServiceMock).delete(ArgumentMatchers.anyLong());

        Assertions.assertThat(clientById).isNotNull();
        Assertions.assertThat(clientById.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}