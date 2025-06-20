package oi.github.pedroMartinsMJ.librayapi2.controles;

import oi.github.pedroMartinsMJ.librayapi2.controles.dto.AutorDTO;
import oi.github.pedroMartinsMJ.librayapi2.controles.dto.ErroResposta;
import oi.github.pedroMartinsMJ.librayapi2.execeptions.RegistroDuplicadoException;
import oi.github.pedroMartinsMJ.librayapi2.model.Autor;
import oi.github.pedroMartinsMJ.librayapi2.service.AutorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService){
        this.autorService = autorService;
    }

    //não vai retorna bory, entao poder ser Void
    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody AutorDTO autor){
        try {
            Autor supostoAutor = autor.mapearParaAutor();
            autorService.salvar(supostoAutor);

            //http://localhost:8080/autores/
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(supostoAutor.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
        }catch (RegistroDuplicadoException e){
            ErroResposta erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> AutorPorId(@PathVariable("id") String id){
        UUID path = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(path);
        if(autorOptional.isPresent()){
            Autor autor = autorOptional.get();
            AutorDTO dto = new AutorDTO(
                    autor.getNome(),
                    autor.getDataNascimento(),
                    autor.getNacionalidade()
            );
            return ResponseEntity.ok(dto);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> autorDeleteId(@PathVariable("id") String id) {
        UUID path = UUID.fromString(id);
        Optional<Autor> autorOptional = autorService.obterPorId(path);

        if(autorOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        autorService.deletarPorAutor(autorOptional.get());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> pesquisaAutor(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade
    ){
        List<Autor> listaAutor = autorService.buscaNomeNacionalidade(nome, nacionalidade);

        List<AutorDTO> autorDTOList = listaAutor.stream()
                .map(autor -> new AutorDTO(
                        autor.getNome(),
                        autor.getDataNascimento(),
                        autor.getNacionalidade()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(autorDTOList);
    }



    @PutMapping("{id}")
    public ResponseEntity<Void> Atualizar(@PathVariable("id") String id, @RequestBody AutorDTO dto){

        UUID suposto = UUID.fromString(id);
        Optional<Autor> supostoAutor = autorService.obterPorId(suposto);
        if(supostoAutor.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        Autor autor = supostoAutor.get();
        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        autor.setDataNascimento(dto.dataNascimento());

        autorService.atualizar(autor);

        return ResponseEntity.noContent().build();
    }

}
