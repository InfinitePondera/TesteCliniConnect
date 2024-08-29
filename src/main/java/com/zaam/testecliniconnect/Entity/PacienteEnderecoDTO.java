package com.zaam.testecliniconnect.Entity;

import lombok.Data;

@Data
public class PacienteEnderecoDTO {
    private long id;

    public PacienteEnderecoDTO(long id) {
        this.id = id;
    }
    public PacienteEnderecoDTO(){
    }
}
