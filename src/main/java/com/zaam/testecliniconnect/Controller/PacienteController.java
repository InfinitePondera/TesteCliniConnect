package com.zaam.testecliniconnect.Controller;

import com.zaam.testecliniconnect.Entity.Paciente;
import com.zaam.testecliniconnect.Entity.PacienteDTO;
import com.zaam.testecliniconnect.Service.PacienteService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin //mudar para endereco do front se nao causa problema CORS
public class PacienteController {

    private PacienteService pacienteService;

    @RequestMapping(value = "/pacientes/pacientesPaginated", method = RequestMethod.GET)
    public @ResponseBody Page<Paciente> getPacientesPaginated(@RequestParam("pageNum") int pageNum, @RequestParam("pageTam") int pageTam) {
        try {
            return pacienteService.getPacientesPaginated(pageNum, pageTam);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/pacientes/busca", method = RequestMethod.GET)
    public @ResponseBody List<Paciente> getPacientesBySearchString(@RequestParam("searchString") String searchString) {
        try {
            return pacienteService.getPacientesBySearchString(searchString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/pacientes", method = RequestMethod.POST)
    public @ResponseBody Paciente savePaciente(@RequestBody PacienteDTO dto) {
        try {
            return pacienteService.savePaciente(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/pacientes", method = RequestMethod.DELETE)
    public void deletePacienteById(@RequestParam("id") String id) {
        try {
            pacienteService.deletePacienteById(Long.parseLong(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/pacientes", method = RequestMethod.PUT)
    public @ResponseBody Paciente updatePacienteById(@RequestBody PacienteDTO dto) {
        try {
            return pacienteService.updatePacienteById(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
