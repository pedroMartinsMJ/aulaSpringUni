package oi.github.pedroMartinsMJ.librayapi2.controles;

import oi.github.pedroMartinsMJ.librayapi2.controles.dto.AutorDTO;
import oi.github.pedroMartinsMJ.librayapi2.model.Autor;
import oi.github.pedroMartinsMJ.librayapi2.service.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService){
        this.autorService = autorService;
    }

    //não vai retorna bory, entao poder ser Void
    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody AutorDTO autor){
        Autor supostoAutor = autor.mapearParaAutor();
        autorService.salvar(supostoAutor);

        //http://localhost:8080/autores/
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(supostoAutor.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }
}
