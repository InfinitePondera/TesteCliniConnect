package com.zaam.testecliniconnect.Repository;

import com.zaam.testecliniconnect.Entity.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
    List<Endereco> findEnderecosByPacienteId(Long id);
}
