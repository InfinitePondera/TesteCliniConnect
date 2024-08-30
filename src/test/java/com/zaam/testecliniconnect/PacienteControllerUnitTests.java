package com.zaam.testecliniconnect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaam.testecliniconnect.Controller.PacienteController;
import com.zaam.testecliniconnect.Entity.*;
import com.zaam.testecliniconnect.Repository.PacienteRepository;
import com.zaam.testecliniconnect.Service.PacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(controllers = PacienteController.class)
public class PacienteControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PacienteService pacienteService;

    private PacienteDTO paciente1 = new PacienteDTO();
    private PacienteDTO paciente2 = new PacienteDTO();
    private EnderecoDTO endereco1 = new EnderecoDTO();
    private EnderecoDTO endereco2 = new EnderecoDTO();
    @BeforeEach
    public void init() {
        paciente1.setId(1L);
        paciente1.setSexo(SexoEnum.MASCULINO);
        paciente1.setNome("Joao");
        paciente1.setEmail("joao@gmail.com");
        paciente1.setCpf("060.087.540-77");
        paciente1.setCelular("(43) 97966-6976");
        paciente1.setDataNascimento("1969-04-20");
        
        endereco1.setId(1L);
        endereco1.setRua("Rua Aleatoria");
        endereco1.setNumero(7);
        endereco1.setBairro("Centro");
        endereco1.setCidade("Londrina");
        endereco1.setEstado("PR");
        endereco1.setPaciente(new PacienteEnderecoDTO(paciente1.getId()));
        List<EnderecoDTO> enderecos1 = new ArrayList<>();
        enderecos1.add(endereco1);
        
        paciente1.setEnderecos(enderecos1);

        paciente2.setId(2L);
        paciente2.setSexo(SexoEnum.FEMININO);
        paciente2.setNome("Maria");
        paciente2.setEmail("mariagmailcom");
        paciente2.setCpf("123.456.678-91");
        paciente2.setCelular("(48) 98326-6235");
        paciente2.setDataNascimento(LocalDate.now().toString());

        endereco2.setId(2L);
        endereco2.setRua("Rua Ainda Mais Aleatoria");
        endereco2.setNumero(14);
        endereco2.setBairro("Atiradores");
        endereco2.setCidade("Joinville");
        endereco2.setEstado("SC");
        endereco2.setPaciente(new PacienteEnderecoDTO(paciente2.getId()));
        List<EnderecoDTO> enderecos2 = new ArrayList<>();
        enderecos2.add(endereco2);

        paciente2.setEnderecos(enderecos2);
    }

    @Test
    public void givenSearchString_whenGetPacientes_thenStatus200() throws Exception {
        List<Paciente> pacientesSearchString = Arrays.asList(new Paciente(paciente1));
        Mockito.when(pacienteService.getPacientesBySearchString("Joao")).thenReturn(pacientesSearchString);
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/pacientes/busca")
                .param("searchString", "Joao")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nome", is("Joao")));
    }

    @Test
    public void whenGetPacientesPaginated_thenStatus200() throws Exception {
        List<Paciente> pacientesPageable = Arrays.asList(new Paciente(paciente1));
        Page<Paciente> pacientePage = new PageImpl<Paciente>(pacientesPageable);
        Mockito.when(pacienteService.getPacientesPaginated(0, 10)).thenReturn(pacientePage);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/pacientes/pacientesPaginated")
                        .param("pageNum", "0")
                        .param("pageTam", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content[*].id").isNotEmpty());
    }

    @Test
    public void givenPaciente_whenPostPaciente_thenStatus201() throws Exception {
        Paciente pacienteReturn = new Paciente(paciente1);
        pacienteReturn.setId(paciente1.getId());
        String requestBody = asJsonString(paciente1);
        Mockito.when(pacienteService.savePaciente(paciente1)).thenReturn(pacienteReturn);
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/pacientes")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    @Test
    public void givenPaciente_whenPostPaciente_thenStatus400()  throws Exception {
        Paciente pacienteReturn = new Paciente(paciente2);
        pacienteReturn.setId(paciente2.getId());
        String requestBody = asJsonString(paciente2);
        Mockito.when(pacienteService.savePaciente(paciente2)).thenThrow(new Exception("Cadastro invalido!"));
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/pacientes")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenPaciente_whenPutPaciente_thenStatus200() throws Exception {
        Paciente pacienteReturn = new Paciente(paciente1);
        pacienteReturn.setId(paciente1.getId());
        String requestBody = asJsonString(paciente1);
        Mockito.when(pacienteService.updatePacienteById(paciente1)).thenReturn(pacienteReturn);
        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/pacientes")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }
    @Test
    public void givenPaciente_whenPutPaciente_thenStatus400() throws Exception {
        Paciente pacienteReturn = new Paciente(paciente2);
        pacienteReturn.setId(paciente2.getId());
        String requestBody = asJsonString(paciente2);
        Mockito.doThrow(new Exception()).when(pacienteService).updatePacienteById(paciente2);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/pacientes")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenPaciente_whenDeletePaciente_thenStatus200() throws Exception {
        Mockito.doNothing().when(pacienteService).deletePacienteById(1L);
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/pacientes")
                .param("id", Long.toString(1L)))
                .andExpect(status().isOk());
    }

    @Test
    public void givenPaciente_whenDeletePaciente_thenStatus400() throws Exception {
        Mockito.doThrow(new Exception()).when(pacienteService).deletePacienteById(9L);
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/pacientes")
                        .param("id", Long.toString(9L)))
                .andExpect(status().isBadRequest());
    }

    private String asJsonString(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
