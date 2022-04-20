package com.example.firstapi.util;

import com.example.firstapi.requests.ClientePutRequestBody;

public class ClientePutRequestBodyCreator {
    public static ClientePutRequestBody createClientePutRequestBody(){
        return ClientePutRequestBody.builder()
                .id(ClienteCreator.createValidUpdateCliente().getId())
                .nome(ClienteCreator.createValidUpdateCliente().getNome())
                .idade(ClienteCreator.createValidUpdateCliente().getIdade())
                .cpf(ClienteCreator.createValidUpdateCliente().getCpf())
                .endereco(ClienteCreator.createValidUpdateCliente().getEndereco())
                .build();
    }
}
