package com.zaam.testecliniconnect.Controller;

import com.zaam.testecliniconnect.Entity.Paciente;
import com.zaam.testecliniconnect.Entity.PacienteDTO;
import com.zaam.testecliniconnect.Service.PacienteService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200") //mudar para endereco do front se nao causa problema CORS
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @RequestMapping(value = "/pacientes/pacientesPaginated", method = RequestMethod.GET)
    public ResponseEntity<Page<Paciente>> getPacientesPaginated(@RequestParam("pageNum") int pageNum, @RequestParam("pageTam") int pageTam) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(pacienteService.getPacientesPaginated(pageNum, pageTam));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.toString(), e);
        }
    }

    @RequestMapping(value = "/pacientes/busca", method = RequestMethod.GET)
    public ResponseEntity<List<Paciente>> getPacientesBySearchString(@RequestParam("searchString") String searchString) {
        try {
            return ResponseEntity.ok(pacienteService.getPacientesBySearchString(searchString));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.toString(), e);
        }
    }

    @RequestMapping(value = "/pacientes", method = RequestMethod.POST)
    public ResponseEntity<Paciente> savePaciente(@RequestBody PacienteDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(pacienteService.savePaciente(dto));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.toString(), e);
        }
    }

    @RequestMapping(value = "/pacientes", method = RequestMethod.DELETE)
    public ResponseEntity deletePacienteById(@RequestParam("id") String id) {
        try {
            pacienteService.deletePacienteById(Long.parseLong(id));
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.toString(), e);
        }
    }

    @RequestMapping(value = "/pacientes", method = RequestMethod.PUT)
    public ResponseEntity<Paciente> updatePacienteById(@RequestBody PacienteDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(pacienteService.updatePacienteById(dto));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.toString(), e);
        }
    }
}
