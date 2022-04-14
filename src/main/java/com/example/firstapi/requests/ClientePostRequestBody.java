package com.example.firstapi.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


// Data Transfer Object (DTO) ou simplesmente Transfer Object é um padrão de
// projetos bastante usado em Java para o transporte de dados entre
// diferentes componentes de um sistema, diferentes instâncias ou
// processos de um sistema distribuído ou diferentes sistemas via serialização.
@Data
public class ClientePostRequestBody {

    @NotBlank(message = "Preencha o nome")
    private String nome;
    @NotBlank(message = "Preencha o cpf")
    private String cpf;
    @NotNull(message = "Preencha a idade")
    private int idade;
    @NotBlank(message = "Preencha endereço")
    private String endereco;
}
