package com.zaam.testecliniconnect.Repository;

import com.zaam.testecliniconnect.Entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    List<Paciente> findPacientesByNomeLikeOrEmailLikeOrCpfLike(String nome, String email, String cpf);
}
