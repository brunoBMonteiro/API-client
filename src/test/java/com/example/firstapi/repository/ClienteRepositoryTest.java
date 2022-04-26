package com.example.firstapi.repository;

import com.example.firstapi.model.Cliente;
import com.example.firstapi.util.ClienteCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;

// Spring Data JPA test
// Teste de validação de banco de dados

@DataJpaTest
@DisplayName("Teste do cliente Repository")
class ClienteRepositoryTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    @DisplayName("Salva cliente persistido, quando der sucesso!")
    void savePersistClienteWhenSuccessful(){

        Cliente clienteToBeSaved = ClienteCreator.createClienteToBeSaved();
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
    void saveUpdatesClienteWhenSuccessful(){
        Cliente clienteToBeSaved = ClienteCreator.createClienteToBeSaved();
        Cliente clienteSaved = this.clienteRepository.save(clienteToBeSaved);
        Cliente clienteUpdated = this.clienteRepository.save(clienteSaved);


        Assertions.assertThat(clienteUpdated).isNotNull();
        Assertions.assertThat(clienteUpdated.getNome()).isEqualTo(clienteSaved.getNome());
        Assertions.assertThat(clienteUpdated.getCpf()).isEqualTo(clienteSaved.getCpf());
        Assertions.assertThat(clienteUpdated.getIdade()).isEqualTo(clienteSaved.getIdade());
        Assertions.assertThat(clienteUpdated.getEndereco()).isEqualTo(clienteSaved.getEndereco());
    }

    @Test
    @DisplayName("Delete, remove cliente quando der sucesso!")
    void deleteRemovesClienteWhenSuccessful(){
        Cliente clienteToBeSaved = ClienteCreator.createClienteToBeSaved();
        Cliente clienteSaved = this.clienteRepository.save(clienteToBeSaved);
        this.clienteRepository.delete(clienteSaved);

        Optional<Cliente> clienteOptional = this.clienteRepository.findById(clienteSaved.getId());
        Assertions.assertThat(clienteOptional).isEmpty();
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