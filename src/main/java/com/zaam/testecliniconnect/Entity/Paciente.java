package com.zaam.testecliniconnect.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "paciente")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "paciente_sequence")
    @SequenceGenerator(name="paciente_sequence", sequenceName = "pac_seq")
    private long id;

    @Column(name = "nome",  length = 255,  nullable = false, updatable = true)
    private String nome;

    @Column(name = "sexo",  length = 255,  nullable = false, updatable = true)
    private SexoEnum sexo;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Endereco> enderecos;

    @Column(name = "cpf",  length = 255,  nullable = false, updatable = true, unique = true)
    private String cpf;

    @Column(name = "celular",  length = 255,  nullable = false, updatable = true, unique = true)
    private String celular;

    @Column(name = "dataNascimento",  length = 255,  nullable = false, updatable = true)
    private LocalDate dataNascimento;

    @Column(name = "email",  length = 255,  nullable = false, updatable = true, unique = true)
    private String email;

    @Column(name = "informacao_atendimento", updatable = true)
    private String informacaoAtendimento;

    public Paciente(PacienteDTO dto) {
        this.nome = dto.getNome();
        this.sexo = dto.getSexo();
        this.cpf = dto.getCpf();
        this.celular = dto.getCelular();
        this.dataNascimento = LocalDate.parse(dto.getDataNascimento());
        this.email = dto.getEmail();
        this.informacaoAtendimento = dto.getInformacaoAtendimento();
    }
}
