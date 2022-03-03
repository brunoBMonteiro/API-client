package com.example.firstapi.controller;

import com.example.firstapi.model.Cliente;
import com.example.firstapi.repository.ClienteRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.EmptyStackException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    
    private final ClienteRepository clienteRepository;
    
    public ClienteController(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }


    @PostMapping
    public ResponseEntity<Cliente> save(@RequestBody Cliente cliente){
        return new ResponseEntity<>(clienteRepository.save(cliente), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listAllClients(){
        List<Cliente> clientes;
        clientes = clienteRepository.findAll();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Cliente>> getClientById(@PathVariable Long id){
        Optional<Cliente> getClientById;
        try {
            getClientById = clienteRepository.findById(id);
            return new ResponseEntity<>(getClientById, HttpStatus.OK);
        }catch (NoSuchElementException nsee){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Optional<Cliente>> deleteClientById(@PathVariable Long id){
        try {
            clienteRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (NoSuchElementException nsee){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
