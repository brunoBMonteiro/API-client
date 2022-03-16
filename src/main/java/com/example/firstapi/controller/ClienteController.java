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
import java.util.Optional;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Data
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository clienteRepository;
    private final ClienteService clienteService;


    @GetMapping
    public ResponseEntity<List<Cliente>> listAll() {
        //return ResponseEntity.ok(clienteService.listAll());

        List<Cliente> listClientes = clienteService.listAll();
        if(listClientes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else{
            for (Cliente cliente: listClientes) {
                long id = cliente.getId();
                cliente.add(linkTo(methodOn(ClienteController.class).findById(id)).withSelfRel());
            }
            return new ResponseEntity<>(listClientes, HttpStatus.OK);
        }


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
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
