package com.zaam.testecliniconnect.Repository;

import com.zaam.testecliniconnect.Entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findClientesByNomeOrEmailOrCpf(String nome, String email, String cpf);
}
