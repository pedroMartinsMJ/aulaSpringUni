package oi.github.pedroMartinsMJ.librayapi2.controles;

import lombok.RequiredArgsConstructor;
import oi.github.pedroMartinsMJ.librayapi2.model.Client;
import oi.github.pedroMartinsMJ.librayapi2.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('GERENTE')")
    public void salvar(@RequestBody Client client){
        clientService.salvar(client);
    }
}
