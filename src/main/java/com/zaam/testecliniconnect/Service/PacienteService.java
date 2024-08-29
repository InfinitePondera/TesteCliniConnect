package com.zaam.testecliniconnect.Service;

import com.zaam.testecliniconnect.Entity.Endereco;
import com.zaam.testecliniconnect.Entity.Paciente;
import com.zaam.testecliniconnect.Entity.PacienteDTO;
import com.zaam.testecliniconnect.Repository.PacienteRepository;
import com.zaam.testecliniconnect.Util.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private EnderecoService enderecoService;

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Page<Paciente> getPacientesPaginated(int pageNum, int pageTam) {
        Pageable page = PageRequest.of(pageNum, pageTam);
        return pacienteRepository.findAll(page);
    }

    public List<Paciente> getPacientesBySearchString(String searchString) {
        List<Paciente> pacienteList = pacienteRepository.findPacientesByNomeLikeOrEmailLikeOrCpfLike(searchString, searchString, searchString);
        pacienteList.forEach(p -> {
            p.setEnderecos(enderecoService.findEnderecosByPaciente(p.getId()));
        });
        return pacienteList;
    }

    public Paciente savePaciente(PacienteDTO dto) throws Exception {
        if (!ValidatorUtil.ValidateEmail(dto.getEmail())) {
            throw new Exception("Email inválido!");
        } else if (!ValidatorUtil.ValidateCPF(dto.getCpf())) {
            throw new Exception("CPF inválido!");
        } else if (!ValidatorUtil.ValidateDataNascimento(dto.getDataNascimento())) {
            throw new Exception("Data de nascimento inválida!");
        } else {
            Paciente paciente = new Paciente(dto);
            paciente.setEnderecos(new ArrayList<>());
            dto.getEnderecos().forEach(e -> {
                Endereco end = new Endereco(e);
                end.setPaciente(paciente);
                paciente.getEnderecos().add(end);
            });
            return pacienteRepository.save(paciente);
        }
    }

    public void deletePacienteById(long id) throws Exception {
        Optional<Paciente> clt = pacienteRepository.findById(id);
        if (clt.isPresent()) {
            pacienteRepository.deleteById(id);
        } else {
            throw new Exception("Paciente não encontrado!");
        }
    }

    public Paciente updatePacienteById(PacienteDTO dto) throws Exception {
        if (!ValidatorUtil.ValidateEmail(dto.getEmail())) {
            throw new Exception("Email inválido!");
        } else if (!ValidatorUtil.ValidateCPF(dto.getCpf())) {
            throw new Exception("CPF inválido!");
        } else if (!ValidatorUtil.ValidateDataNascimento(dto.getDataNascimento())) {
            throw new Exception("Data de nascimento inválida!");
        } else {
            Optional<Paciente> clt = pacienteRepository.findById(dto.getId());
            if (clt.isPresent()) {
                Paciente updPaciente = clt.get();
                List<Endereco> updEnderecos = new ArrayList<>();

                updPaciente.setNome(dto.getNome());
                updPaciente.setSexo(dto.getSexo());
                dto.getEnderecos().forEach(e -> {
                    Optional<Endereco> endOp = enderecoService.findEnderecoById(e.getId());
                    if (endOp.isPresent()) {
                        Endereco endUpd = endOp.get();
                        endUpd.setRua(e.getRua());
                        endUpd.setBairro(e.getBairro());
                        endUpd.setNumero(e.getNumero());
                        endUpd.setCidade(e.getCidade());
                        endUpd.setEstado(e.getEstado());
                        updEnderecos.add(endUpd);
                    } else {
                        try {
                            throw new Exception("Endereço não encontrado!");
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                updPaciente.setEnderecos(updEnderecos);
                updPaciente.setCpf(dto.getCpf());
                updPaciente.setCelular(dto.getCelular());
                updPaciente.setDataNascimento(LocalDate.parse(dto.getDataNascimento()));
                updPaciente.setEmail(dto.getEmail());
                enderecoService.addEndereco(updEnderecos);
                return pacienteRepository.save(updPaciente);
            } else {
                throw new Exception("Paciente não encontrado!");
            }
        }
    }
}
