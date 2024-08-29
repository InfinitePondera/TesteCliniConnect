package com.zaam.testecliniconnect.Entity;

import lombok.Data;

@Data
public class EnderecoDTO {
    private long id;

    private String rua;

    private int numero;

    private String bairro;

    private String cidade;

    private String estado;

    private PacienteEnderecoDTO paciente;
}
