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
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "paciente_sequence")
    @SequenceGenerator(name="paciente_sequence", sequenceName = "pac_seq")
    private Long id;

    @Column(name = "nome",  length = 255,  nullable = false, updatable = true)
    private String nome;

    @Column(name = "sexo",  length = 255,  nullable = false, updatable = true)
    private SexoEnum sexo;

    @OneToMany(mappedBy = "paciente", cascade = CascadeType.PERSIST, orphanRemoval = true, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Endereco> enderecos;

    @Column(name = "cpf",  length = 255,  nullable = false, updatable = true, unique = true)
    private String cpf;

    @Column(name = "celular",  length = 255,  nullable = false, updatable = true, unique = true)
    private String celular;

    @Column(name = "dataNascimento",  length = 255,  nullable = false, updatable = true)
    private LocalDate dataNascimento;

    @Column(name = "email",  length = 255,  nullable = false, updatable = true, unique = true)
    private String email;

    public Paciente(PacienteDTO dto) {
        this.nome = dto.getNome();
        this.sexo = dto.getSexo();
        this.cpf = dto.getCpf();
        this.celular = dto.getCelular();
        this.dataNascimento = LocalDate.parse(dto.getDataNascimento());
        this.email = dto.getEmail();
    }
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Paciente paciente = (Paciente) o;
        return getId() != null && Objects.equals(getId(), paciente.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
