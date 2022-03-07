package com.example.firstapi.service;

import com.example.firstapi.model.Cliente;
import com.example.firstapi.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<Cliente> listAll(){
        return clienteRepository.findAll();
    }

    public Cliente findById(long id){
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente não encontrado!"));
    }

    public Cliente save(Cliente cliente){
        cliente.setId(ThreadLocalRandom.current().nextLong(3, 10000));
        cliente.add(anime);
    }
}
