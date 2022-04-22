package com.example.firstapi.controller;

import com.example.firstapi.model.Cliente;
import com.example.firstapi.requestsdto.ClientePostRequestBody;
import com.example.firstapi.requestsdto.ClientePutRequestBody;
import com.example.firstapi.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = "*")
@Tag(name = "Cliente", description = "a API cliente com anotações de documentação")
public class ClienteController {

    private final ClienteService clienteService;

    @Operation(summary = "Obter uma lista de clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "encontrou lista", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))}),
            @ApiResponse(responseCode = "204", description = "Tem uma lista mas está vazia", content = @Content),
            @ApiResponse(responseCode = "404", description = "lista de clientes não encontrada", content = @Content)
    })
    @GetMapping
    public ResponseEntity<List<Cliente>> listAll() {
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


    @Operation(summary = "Obter um cliente por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "encontrou o cliente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))}),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
   @GetMapping(path = "/{id}")
   public ResponseEntity<Cliente> findById(@PathVariable long id){
       return ResponseEntity.ok(clienteService.findByIdOrThrowBadRequestException(id));

   }


    @Operation(summary = "Salva um novo cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "salvou novo cliente", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))}),
            @ApiResponse(responseCode = "400", description = "Campo do cliente em branco", content = @Content)
    })
   @PostMapping
   public ResponseEntity<Cliente> save(@RequestBody @Valid ClientePostRequestBody clientePostRequestBody){
        return new ResponseEntity<>(clienteService.save(clientePostRequestBody), HttpStatus.CREATED);
   }

    @Operation(summary = "Deleta um cliente por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "cliente deletado com sucesso", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))}),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteClientById(@PathVariable long id) {
        clienteService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Atualiza informações de um cliente por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Informações do cliente atualizadas", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Cliente.class))}),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> replace(@RequestBody ClientePutRequestBody clientePutRequestBody) {
        clienteService.replace(clientePutRequestBody);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

}
