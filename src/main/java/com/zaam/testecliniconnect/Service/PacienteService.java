package com.zaam.testecliniconnect.Service;

import com.zaam.testecliniconnect.Entity.Paciente;
import com.zaam.testecliniconnect.Entity.PacienteDTO;
import com.zaam.testecliniconnect.Entity.Endereco;
import com.zaam.testecliniconnect.Repository.PacienteRepository;
import com.zaam.testecliniconnect.Util.ValidatorUtil;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PacienteService {

    private PacienteRepository pacienteRepository;

    private EnderecoService enderecoService;

    public PacienteService(PacienteRepository pacRepo) {
        this.pacienteRepository = pacRepo;
    }

    public Page<Paciente> getPacientesPaginated(int pageNum, int pageTam) {
        try {
            Pageable page = PageRequest.of(pageNum, pageTam);
            return pacienteRepository.findAll(page);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Paciente> getPacientesBySearchString(String searchString) {
        try {
            return pacienteRepository.findPacientesByNomeLikeOrEmailLikeOrCpfLike(searchString, searchString, searchString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Paciente savePaciente(PacienteDTO dto) {
        try {
            if (!ValidatorUtil.ValidateEmail(dto.getEmail())){
                throw new EmailValidationFailException();
            } else if (!ValidatorUtil.ValidateCPF(dto.getCpf())){
                throw new CPFValidationFailException();
            } else if (!ValidatorUtil.ValidateDataNascimento(dto.getDataNascimento())) {
                throw new DataNascimentoValidationFail();
            } else {
                Paciente paciente = new Paciente(dto);
                dto.getEnderecos().forEach(e -> {
                    Endereco end = new Endereco(e);
                    paciente.getEnderecos().add(end);
                });
                return pacienteRepository.save(paciente);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deletePacienteById(Long id) {
        try {
            Optional<Paciente> clt = pacienteRepository.findById(id);
            if (clt.isPresent()) {
                pacienteRepository.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Paciente updatePacienteById(PacienteDTO dto) {
        try {
            if (!ValidatorUtil.ValidateEmail(dto.getEmail())){
                throw new EmailValidationFailException();
            } else if (!ValidatorUtil.ValidateCPF(dto.getCpf())){
                throw new CPFValidationFailException();
            } else if (!ValidatorUtil.ValidateDataNascimento(dto.getDataNascimento())) {
                throw new DataNascimentoValidationFail();
            } else {
                Optional<Paciente> clt = pacienteRepository.findById(dto.getId());
                if (clt.isPresent()) {
                    Paciente updPaciente = clt.get();
                    Set<Endereco> updEnderecos = Collections.emptySet();

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
                            throw new PacienteNotFoundException();
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
                    throw new PacienteNotFoundException();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
