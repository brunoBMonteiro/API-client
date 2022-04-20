package com.example.firstapi.util;

import com.example.firstapi.requests.ClientePostRequestBody;

public class ClientePostRequestBodyCreator {
    public static ClientePostRequestBody createClientePostRequestBody(){
        return ClientePostRequestBody.builder()
                .nome(ClienteCreator.createClienteToBeSaved().getNome())
                .cpf(ClienteCreator.createClienteToBeSaved().getCpf())
                .idade(ClienteCreator.createClienteToBeSaved().getIdade())
                .endereco(ClienteCreator.createClienteToBeSaved().getEndereco())
                .build();
    }
}
