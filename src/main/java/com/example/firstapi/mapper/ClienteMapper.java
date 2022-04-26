package com.example.firstapi.mapper;

import com.example.firstapi.model.Cliente;
import com.example.firstapi.requestsdto.ClientePostRequestBody;
import com.example.firstapi.requestsdto.ClientePutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class ClienteMapper {
    public static final ClienteMapper INSTANCE = Mappers.getMapper(ClienteMapper.class);

    public abstract Cliente cliente(ClientePostRequestBody clientePostRequestBody);
    public abstract Cliente cliente(ClientePutRequestBody clientePutRequestBody);
}


/*
O que é?
MapStruct é um gerador de código que simplifica muito a implementação de mapeamentos
entre tipos de feijão Java com base em uma convenção sobre abordagem de configuração.

O código de mapeamento gerado usa invocações de método simples e, portanto, é rápido,
seguro de tipo e fácil de entender.


Por que?
Aplicativos em várias camadas geralmente requerem mapear entre diferentes modelos de
objetos (por exemplo, entidades e DTOs). Escrever esse código de mapeamento é uma tarefa
tediosa e propensa a erros. MapStruct tem como objetivo simplificar este trabalho automatizando-o
tanto quanto possível.

Em contraste com outras estruturas de mapeamento, o MapStruct gera mapeamentos de feijão
no tempo de compilação, o que garante um alto desempenho, permite feedback rápido do desenvolvedor
e verificação completa de erros.


Como?
MapStruct é um processador de anotação que é conectado ao compilador Java e pode ser usado em
compilações de linha de comando (Maven, Gradle etc.) bem como dentro do seu IDE preferido.

O MapStruct usa padrões sensíveis, mas sai do seu caminho quando se trata de configurar ou
implementar comportamentos especiais.
 */