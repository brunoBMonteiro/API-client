package com.example.firstapi.service;

import com.example.firstapi.exceptions.NotFoundRequestException;
import com.example.firstapi.model.Cliente;
import com.example.firstapi.repository.ClienteRepository;
import com.example.firstapi.requestsdto.ClientePostRequestBody;
import com.example.firstapi.requestsdto.ClientePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository clienteRepository;

    public List<Cliente> listAll() {
        return clienteRepository.findAll();
    }

    public Cliente findById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundRequestException("Cliente não encontrado!"));
    }

    @Transactional(rollbackFor = Exception.class)
    public Cliente save(ClientePostRequestBody clientePostRequestBody) {
        // Exemplo de Builder, forma de instanciar objeto
        return clienteRepository.save(Cliente.builder()
                .nome(clientePostRequestBody.getNome())
                .cpf(clientePostRequestBody.getCpf())
                .idade(clientePostRequestBody.getIdade())
                .endereco(clientePostRequestBody.getEndereco())
                .build());

        //return clienteRepository.save(ClienteMapper.INSTANCE.toCliente(clientePostRequestBody));

    }

    public void delete(long id) {
        clienteRepository.delete(findById(id));
    }


    public void replace(ClientePutRequestBody clientePutRequestBody) {
        // Exemplo de Builder, forma de instanciar objeto
        Cliente savedCliente = findById(clientePutRequestBody.getId());
        Cliente cliente = Cliente.builder()
                .id(savedCliente.getId())
                .nome(clientePutRequestBody.getNome())
                .cpf(clientePutRequestBody.getCpf())
                .idade(clientePutRequestBody.getIdade())
                .endereco(clientePutRequestBody.getEndereco())
                .build();
        clienteRepository.save(cliente);
    }
}
