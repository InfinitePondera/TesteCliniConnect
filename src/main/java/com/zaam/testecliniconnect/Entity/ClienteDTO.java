package com.zaam.testecliniconnect.Entity;

import lombok.Data;

import java.util.Set;

@Data
public class ClienteDTO {
    private Long id;

    private String nome;

    private SexoEnum sexo;

    private Set<EnderecoDTO> enderecos;

    private String cpf;

    private String celular;

    private String dataNascimento;

    private String email;
}
