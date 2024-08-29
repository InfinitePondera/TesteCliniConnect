package com.zaam.testecliniconnect.Service;

import com.zaam.testecliniconnect.Entity.Endereco;
import com.zaam.testecliniconnect.Entity.EnderecoDTO;
import com.zaam.testecliniconnect.Repository.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    public Optional<Endereco> findEnderecoById(long id) {
        try {
            return enderecoRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public List<Endereco> findEnderecosByPaciente(long id) {
        return enderecoRepository.findEnderecosByPacienteId(id);
    }

    public List<Endereco> addEndereco(List<Endereco> end) {
        try {
            List<Endereco> ends = new ArrayList<>();
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

