package com.example.firstapi.requests;

import lombok.Data;

// Data Transfer Object (DTO) ou simplesmente Transfer Object é um padrão de
// projetos bastante usado em Java para o transporte de dados entre
// diferentes componentes de um sistema, diferentes instâncias ou
// processos de um sistema distribuído ou diferentes sistemas via serialização.
@Data
public class ClientePutRequestBody {
    private Long id;
    private String nome;
    private String cpf;
    private int idade;
    private String endereco;
}
