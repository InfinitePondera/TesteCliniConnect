package com.zaam.testecliniconnect.Service;

import com.zaam.testecliniconnect.Entity.Endereco;
import com.zaam.testecliniconnect.Entity.EnderecoDTO;
import com.zaam.testecliniconnect.Repository.EnderecoRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
public class EnderecoService {

    private EnderecoRepository enderecoRepository;

    public EnderecoService(EnderecoRepository endRepo) {
        this.enderecoRepository = endRepo;
    }

    public Optional<Endereco> findEnderecoById(Long id) {
        try {
            return enderecoRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Set<Endereco> addEndereco(Set<Endereco> end) {
        try {
            Set<Endereco> ends = Collections.emptySet();
            end.forEach(e -> {
                ends.add(enderecoRepository.save(e));
            });
            return ends;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

