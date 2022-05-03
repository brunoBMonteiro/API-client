package com.example.firstapi.util;

import com.example.firstapi.model.Cliente;

//Stubs
// Criando objetos que serão utilizados nas classes de teste
public class ClienteCreator {

    public static Cliente createClienteToBeSaved(){
        return Cliente.builder()
                .nome("Bruno")
                .idade(25)
                .cpf("255.894.321-20")
                .endereco("Avenida Rodolfo Muller")
                .build();
    }

    public static Cliente createValidClient(){
        return Cliente.builder()
                .id(1L)
                .nome("Bruno Monteiro")
                .cpf("031.766.811-20")
                .idade(25)
                .endereco("Em algum lugar")
                .build();
    }

    public static Cliente createValidUpdateCliente(){
        return Cliente.builder()
                .id(1L)
                .nome("Bruno Monteiro")
                .idade(25)
                .cpf("255.894.321-20")
                .endereco("Avenida Rodolfo Muller")
                .build();
    }

}
