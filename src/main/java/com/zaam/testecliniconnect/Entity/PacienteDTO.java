package com.zaam.testecliniconnect.Entity;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class PacienteDTO {
    private Long id;

    private String nome;

    private SexoEnum sexo;

    private List<EnderecoDTO> enderecos;

    private String cpf;

    private String celular;

    private String dataNascimento;

    private String email;

    private String informacaoAtendimento;

}
