package com.zaam.testecliniconnect.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Embeddable
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "endereco_sequence")
    @SequenceGenerator(name="endereco_sequence", sequenceName = "end_seq")
    private Long id;

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

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public Endereco(EnderecoDTO dto) {
        this.rua = dto.getRua();
        this.numero = dto.getNumero();
        this.bairro = dto.getBairro();
        this.cidade = dto.getCidade();
        this.estado = dto.getEstado();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Endereco endereco = (Endereco) o;
        return getId() != null && Objects.equals(getId(), endereco.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
