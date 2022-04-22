package com.example.firstapi.service;

import com.example.firstapi.model.Cliente;
import com.example.firstapi.repository.ClienteRepository;
import com.example.firstapi.util.ClienteCreator;
import com.example.firstapi.util.ClientePostRequestBodyCreator;
import com.example.firstapi.util.ClientePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class ClienteServiceTest {

        // @InjectMocks quando quero testar a classe em si, clienteService
        @InjectMocks
        private ClienteService clienteService;
        @Mock
        private ClienteRepository clienteRepositoryMock;

        @BeforeEach
        void setUp(){
            // 1- Criando comportamento para o mockito e no passo 2- o teste
            Mockito.when(clienteRepositoryMock.findAll())
                    .thenReturn(List.of(ClienteCreator.createValidClient()));

            Mockito.when(clienteRepositoryMock.findById(ArgumentMatchers.anyLong()))
                    .thenReturn(Optional.of(ClienteCreator.createValidClient()));

            Mockito.when(clienteRepositoryMock.save(ArgumentMatchers.any(Cliente.class)))
                    .thenReturn(ClienteCreator.createValidClient());

            Mockito.doNothing().when(clienteRepositoryMock).delete(ArgumentMatchers.any(Cliente.class));
        }

        @Test
        @DisplayName("Listar todos, retornar lista de cliente quando der sucesso")
        void listAll_ReturnListOfClient_WhenSuccessful(){
            // 2 teste do comportamento
            final var expectedName = ClienteCreator.createValidClient();
            final var clienteList = clienteService.listAll();

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
            final var cliente = clienteService.findByIdOrThrowBadRequestException(1);

            Assertions.assertThat(cliente).isNotNull();
            Assertions.assertThat(cliente.getId()).isNotNull().isEqualTo(expectedId);

        }



        @Test
        @DisplayName("Salva, retorna cliente quando der sucesso")
        void save_ReturnCliente_WhenSuccessful() {
            final var cliente = clienteService.save(ClientePostRequestBodyCreator
                    .createClientePostRequestBody());

            Assertions.assertThat(cliente).isNotNull().isEqualTo(ClienteCreator.createValidClient());
        }

        @Test
        @DisplayName("Atualiza, atualizar cliente quando der sucesso")
        void replace_UpdateCliente_WhenSuccessful(){
            Assertions.assertThatCode(() ->clienteService.replace(ClientePutRequestBodyCreator
                            .createClientePutRequestBody()))
                    .doesNotThrowAnyException();
        }

        @Test
        @DisplayName("Deleta, remove cliente quando der sucesso")
        void delete_RemoveCliente_WhenSuccessful(){
            Assertions.assertThatCode(() ->clienteService.delete(1))
                    .doesNotThrowAnyException();

        }

}
