package com.example.firstapi.repository;

import com.example.firstapi.model.Cliente;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
@DisplayName("Teste do cliente Repository")
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    @DisplayName("Salva cliente criado quando der sucesso!")
    void save_PersistCliente_WhenSuccessful(){

        Cliente clienteToBeSaved = createCliente();
        Cliente savedClient =  this.clienteRepository.save(clienteToBeSaved);

        // Verifique que cliente salvo
        Assertions.assertThat(savedClient).isNotNull();
        Assertions.assertThat(savedClient.getNome()).isEqualTo(clienteToBeSaved.getNome());
        Assertions.assertThat(savedClient.getCpf()).isEqualTo(clienteToBeSaved.getCpf());
        Assertions.assertThat(savedClient.getIdade()).isEqualTo(clienteToBeSaved.getIdade());
        Assertions.assertThat(savedClient.getEndereco()).isEqualTo(clienteToBeSaved.getEndereco());
    }

    @Test
    @DisplayName("Salva clientes atualizados quando der sucesso!")
    void save_UpdatesCliente_WhenSuccessful(){
        Cliente clienteToBeSaved = createCliente();
        Cliente clienteSaved = this.clienteRepository.save(clienteToBeSaved);
        clienteSaved.setNome("Pedro");
        Cliente clienteUpdated = this.clienteRepository.save(clienteSaved);


        Assertions.assertThat(clienteUpdated).isNotNull();
        Assertions.assertThat(clienteSaved.getNome()).isEqualTo(clienteSaved.getNome());
        Assertions.assertThat(clienteUpdated.getCpf()).isNull();
        Assertions.assertThat(clienteUpdated.getIdade()).isNegative();
        Assertions.assertThat(clienteUpdated.getEndereco()).isNull();
    }



    private Cliente createCliente(){
        return Cliente.builder()
                .nome("Bruno")
                .cpf("022.654.878-30")
                .idade(25)
                .endereco("Avenida dos devs back end")
                .build();
    }
}