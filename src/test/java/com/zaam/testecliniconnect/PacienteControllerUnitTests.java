package com.zaam.testecliniconnect;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaam.testecliniconnect.Controller.PacienteController;
import com.zaam.testecliniconnect.Entity.*;
import com.zaam.testecliniconnect.Service.PacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
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
        paciente1.setNome("João");
        paciente1.setEmail("joao@gmail.com");
        paciente1.setCpf("060.087.540-77");
        paciente1.setCelular("(43) 97966-6976");
        paciente1.setDataNascimento("20/04/1969");
        
        endereco1.setId(1L);
        endereco1.setRua("Rua Aleatoria");
        endereco1.setNumero(7);
        endereco1.setBairro("Centro");
        endereco1.setCidade("Londrina");
        endereco1.setEstado("PR");
        endereco1.setPaciente(paciente1);
        Set<EnderecoDTO> enderecos1 = Collections.emptySet();
        enderecos1.add(endereco1);
        
        paciente1.setEnderecos(enderecos1);

        PacienteDTO paciente2 = new PacienteDTO();
        EnderecoDTO endereco2 = new EnderecoDTO();
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
        endereco2.setPaciente(paciente2);
        Set<EnderecoDTO> enderecos2 = Collections.emptySet();
        enderecos1.add(endereco2);

        paciente2.setEnderecos(enderecos2);
    }

    @Test
    public void givenSearchString_whenGetPacientes_thenStatus200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/pacientes/busca")
                .param("searchString", "João")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content[*].id").isNotEmpty());
    }

    @Test
    public void whenGetPacientesPaginated_thenStatus200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .get("/pacientes/pacientesPaginated")
                        .param("pageNum", "1")
                        .param("pageTam", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content[*].id").isNotEmpty());
    }

    @Test
    public void givenPaciente_whenPostPaciente_thenStatus201() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/pacientes")
                .content(asJsonString(paciente1))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content[*].id").isNotEmpty());
    }

    @Test
    public void givenPaciente_whenPostPaciente_thenStatus400()  throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .post("/pacientes")
                        .content(asJsonString(paciente2))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenPaciente_whenPutPaciente_thenStatus200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/pacientes")
                .param("id", "1L")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content[*].id").isNotEmpty());
    }

    @Test
    public void givenPaciente_whenPutPaciente_thenStatus404() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .put("/pacientes")
                .param("id", "9L")
                        .content(asJsonString(paciente2))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenPaciente_whenPutPaciente_thenStatus400() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .put("/pacientes")
                        .param("id", "2L")
                        .content(asJsonString(paciente2))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void givenPaciente_whenDeletePaciente_thenStatus200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                .delete("/pacientes")
                .param("id", "1L"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenPaciente_whenDeletePaciente_thenStatus404() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders
                        .delete("/pacientes")
                        .param("id", "1L"))
                .andExpect(status().isNotFound());
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
