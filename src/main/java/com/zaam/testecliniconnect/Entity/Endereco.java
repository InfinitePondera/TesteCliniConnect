package com.zaam.testecliniconnect.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Table(name = "endereco")
@Embeddable
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "endereco_sequence")
    @SequenceGenerator(name="endereco_sequence", sequenceName = "end_seq")
    private long id;

    @Column(name = "rua",  length = 255,  nullable = false, updatable = true)
    private  String rua;

    @Column(name = "numero",  length = 4,  nullable = false, updatable = true)
    private int numero;

    @Column(name = "bairro",  length = 255,  nullable = false, updatable = true)
    private String bairro;

    @Column(name = "cidade",  length = 255,  nullable = false, updatable = true)
    private String cidade;

    @Column(name = "estado",  length = 2,  nullable = false, updatable = true)
    private String estado;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    @JsonIgnore
    private Paciente paciente;

    public Endereco(EnderecoDTO dto) {
        this.rua = dto.getRua();
        this.numero = dto.getNumero();
        this.bairro = dto.getBairro();
        this.cidade = dto.getCidade();
        this.estado = dto.getEstado();
    }
}
