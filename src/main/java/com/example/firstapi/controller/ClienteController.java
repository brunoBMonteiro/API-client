package com.example.firstapi.controller;

import com.example.firstapi.model.Cliente;
import com.example.firstapi.repository.ClienteRepository;
import com.example.firstapi.requests.ClientePostRequestBody;
import com.example.firstapi.requests.ClientePutRequestBody;
import com.example.firstapi.service.ClienteService;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Data
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;
    private final ClienteService clienteService;
    private Cliente cliente;

    @GetMapping
    public ResponseEntity<List<Cliente>> listAllClients(){
        return ResponseEntity.ok(clienteService.listAll());
    }

   @GetMapping(path = "/{id}")
   public ResponseEntity<Cliente> findById(@PathVariable long id){
        return ResponseEntity.ok(clienteService.findByIdOrThrowBadRequestException(id));
   }

   @PostMapping
   public ResponseEntity<Cliente> save(@RequestBody ClientePostRequestBody clientePostRequestBody){
        return new ResponseEntity<>(clienteService.save(clientePostRequestBody), HttpStatus.CREATED);
   }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteClientById(@PathVariable long id) {
        clienteService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> replace(@RequestBody ClientePutRequestBody clientePutRequestBody) {
        clienteService.replace(clientePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
