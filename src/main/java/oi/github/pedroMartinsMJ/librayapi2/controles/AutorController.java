package oi.github.pedroMartinsMJ.librayapi2.controles;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import oi.github.pedroMartinsMJ.librayapi2.controles.dto.AutorDTO;
import oi.github.pedroMartinsMJ.librayapi2.controles.mappers.AutorMapper;
import oi.github.pedroMartinsMJ.librayapi2.model.Autor;
import oi.github.pedroMartinsMJ.librayapi2.model.Usuario;
import oi.github.pedroMartinsMJ.librayapi2.service.AutorService;

import oi.github.pedroMartinsMJ.librayapi2.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/autores")
@RequiredArgsConstructor
public class AutorController implements GenericController{

    private final AutorService autorService;
    private final UsuarioService usuarioService;
    private final AutorMapper autorMapper;

    //não vai retorna bory, entao poder ser Void
    @PostMapping
    @PreAuthorize("hasAnyRole('GERENTE')")
    public ResponseEntity<Void> salvar(@RequestBody @Valid AutorDTO autorDTO,
                                       Authentication authentication){
        System.out.println(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Usuario usuario = usuarioService.obterPorLogin(userDetails.getUsername());


        Autor supostoAutor = autorMapper.toEntity(autorDTO);
        supostoAutor.setIdUsuario(usuario.getId());
        autorService.salvar(supostoAutor);

        //http://localhost:8080/autores/
        URI location = gererHeaderLocation(supostoAutor.getId());

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<AutorDTO> AutorPorId(@PathVariable("id") String id){
        UUID path = UUID.fromString(id);

        return autorService
                .obterPorId(path)
                .map(autor -> {
                    AutorDTO dto = autorMapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build() );
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('GERENTE')")
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
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<List<AutorDTO>> pesquisaAutor(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade
    ){
        List<Autor> listaAutor = autorService.buscaNomeNacionalidadeByExample(nome, nacionalidade);

        List<AutorDTO> autorDTOList = listaAutor
                .stream()
                .map(autor -> autorMapper.toDTO(autor))
                .collect(Collectors.toList());

        return ResponseEntity.ok(autorDTOList);
    }



    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('GERENTE')")
    public ResponseEntity<Void> Atualizar(@PathVariable("id") String id, @RequestBody @Valid AutorDTO dto){
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
