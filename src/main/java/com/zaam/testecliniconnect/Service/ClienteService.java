package com.zaam.testecliniconnect.Service;

import com.zaam.testecliniconnect.Entity.Cliente;
import com.zaam.testecliniconnect.Entity.ClienteDTO;
import com.zaam.testecliniconnect.Entity.Endereco;
import com.zaam.testecliniconnect.Repository.ClienteRepository;
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
public class ClienteService {

    private ClienteRepository clienteRepository;

    public Page<Cliente> getClientesPaginated(int pageNum, int pageTam) {
        try {
            Pageable page = PageRequest.of(pageNum, pageTam);
            return clienteRepository.findAll(page);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Cliente> getClientesBySearchString(String searchString) {
        try {
            return clienteRepository.findClientesByNomeOrEmailOrCpf(searchString, searchString, searchString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Optional<Cliente> getClienteByNome(String nome) {
        try {
            Cliente clienteProbe = new Cliente();
            clienteProbe.setNome(nome);
            return clienteRepository.findOne(Example.of(clienteProbe));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Optional<Cliente> findClienteByCPF(String cpf) {
        try {
            Cliente clienteProbe = new Cliente();
            clienteProbe.setCpf(cpf);
            return clienteRepository.findOne(Example.of(clienteProbe));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Optional<Cliente> findClienteByEmail(String email) {
        try {
            Cliente clienteProbe = new Cliente();
            clienteProbe.setEmail(email);
            return clienteRepository.findOne(Example.of(clienteProbe));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Cliente saveCliente(ClienteDTO dto) {
        try {
            if (!ValidatorUtil.ValidateEmail(dto.getEmail())){
                throw new EmailValidationFailException();
            } else if (!ValidatorUtil.ValidateCPF(dto.getCpf())){
                throw new CPFValidationFailException();
            } else if (!ValidatorUtil.ValidateDataNascimento(dto.getDataNascimento())) {
                throw new DataNascimentoValidationFail();
            } else {
                Cliente cliente = new Cliente(dto);
                return clienteRepository.save(cliente);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteClienteById(Long id) {
        try {
            Optional<Cliente> clt = clienteRepository.findById(id);
            if (clt.isPresent()) {
                clienteRepository.deleteById(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Cliente updateClienteById(ClienteDTO dto) {
        try {
            if (!ValidatorUtil.ValidateEmail(dto.getEmail())){
                throw new EmailValidationFailException();
            } else if (!ValidatorUtil.ValidateCPF(dto.getCpf())){
                throw new CPFValidationFailException();
            } else if (!ValidatorUtil.ValidateDataNascimento(dto.getDataNascimento())) {
                throw new DataNascimentoValidationFail();
            } else {
                Optional<Cliente> clt = clienteRepository.findById(dto.getId());
                if (clt.isPresent()) {
                    Cliente updCliente = clt.get();
                    Set<Endereco> updEnderecos = Collections.emptySet();

                    updCliente.setNome(dto.getNome());
                    updCliente.setSexo(dto.getSexo());
                    dto.getEnderecos().forEach(e -> {
                        updEnderecos.add(new Endereco(e));
                    });
                    updCliente.setEnderecos(updEnderecos);
                    updCliente.setCpf(dto.getCpf());
                    updCliente.setCelular(dto.getCelular());
                    updCliente.setDataNascimento(LocalDate.parse(dto.getDataNascimento()));
                    updCliente.setEmail(dto.getEmail());

                    return clienteRepository.save(updCliente);
                } else {
                    throw new ClienteNotFoundException();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
