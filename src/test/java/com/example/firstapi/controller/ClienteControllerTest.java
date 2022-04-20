package com.example.firstapi.controller;

import com.example.firstapi.model.Cliente;
import com.example.firstapi.service.ClienteService;
import com.example.firstapi.util.ClienteCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.PageImpl;
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
    // @Mock é para todas as classes que estão sendo utilizadas dentro do ClienteController
    @Mock
    private ClienteService clienteServiceMock;

    @BeforeEach
    void setUp(){
        PageImpl<Cliente> clientePage = new PageImpl<>(List.of(ClienteCreator.createValidClient()));
        BDDMockito.when(clienteServiceMock.listAll())
                .thenReturn((List<Cliente>) clientePage);
    }

    @Test
    @DisplayName("Lista, retornar lista de cliente quando der sucesso")
    void list_ReturnListOfClient_WhenSuccessful(){
        String expectedName = ClienteCreator.createValidClient().getNome();
        List<Cliente> clienteList = clienteController.listAll().getBody();

        Assertions.assertThat(clienteList).isNotNull();
        Assertions.assertThat(clienteList.get(0).getNome()).isEqualTo(expectedName);
    }


}