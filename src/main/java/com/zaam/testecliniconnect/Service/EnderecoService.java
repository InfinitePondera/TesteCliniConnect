package com.zaam.testecliniconnect.Service;

import com.zaam.testecliniconnect.Entity.Endereco;
import com.zaam.testecliniconnect.Entity.EnderecoDTO;
import com.zaam.testecliniconnect.Repository.EnderecoRepository;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    private EnderecoRepository enderecoRepository;

    public Endereco addEndereco(EnderecoDTO dto) {
        try {
            Endereco end = new Endereco(dto);
            return enderecoRepository.save(end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

