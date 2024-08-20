package com.zaam.testecliniconnect.Controller;

import com.zaam.testecliniconnect.Entity.Cliente;
import com.zaam.testecliniconnect.Entity.ClienteDTO;
import com.zaam.testecliniconnect.Service.ClienteService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin //mudar para endereco do front se nao causa problema CORS
public class ClienteController {

    private ClienteService clienteService;

    @RequestMapping(value = "/clientes/clientesPaginated", method = RequestMethod.GET)
    public @ResponseBody Page<Cliente> getClientesPaginated(@RequestParam("pageNum") int pageNum, @RequestParam("pageTam") int pageTam) {
        try {
            return clienteService.getClientesPaginated(pageNum, pageTam);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/clientes/busca", method = RequestMethod.GET)
    public @ResponseBody List<Cliente> getClientesBySearchString(@RequestParam("searchString") String searchString) {
        try {
            return clienteService.getClientesBySearchString(searchString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/clientes", method = RequestMethod.POST)
    public @ResponseBody Cliente saveCliente(@RequestBody ClienteDTO dto) {
        try {
            return clienteService.saveCliente(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping(value = "/clientes", method = RequestMethod.DELETE)
    public void deleteClienteById(@RequestParam("id") String id) {
        try {
            clienteService.deleteClienteById(Long.parseLong(id));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/clientes", method = RequestMethod.PUT)
    public @ResponseBody Cliente updateClienteById(@RequestBody ClienteDTO dto) {
        try {
            return clienteService.updateClienteById(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
