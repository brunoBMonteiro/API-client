package com.example.firstapi.service;

import com.example.firstapi.model.Cliente;
import com.example.firstapi.repository.ClienteRepository;
import com.example.firstapi.requests.ClientePostRequestBody;
import com.example.firstapi.requests.ClientePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<Cliente> listAll(){
        return clienteRepository.findAll();
    }

    public Cliente findByIdOrThrowBadRequestException(long id){
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente não encontrado!"));
    }

    public Cliente save(ClientePostRequestBody clientePostRequestBody){
        return clienteRepository.save(Cliente.builder()
                .nome(clientePostRequestBody.getNome())
                        .cpf(clientePostRequestBody.getCpf())
                        .idade(clientePostRequestBody.getIdade())
                        .endereco(clientePostRequestBody.getEndereco())
                .build());
    }

    public void delete(long id){
        clienteRepository.delete(findByIdOrThrowBadRequestException(id));
    }


    public void replace(ClientePutRequestBody clientePutRequestBody){
        Cliente savedCliente = findByIdOrThrowBadRequestException(clientePutRequestBody.getId());
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
