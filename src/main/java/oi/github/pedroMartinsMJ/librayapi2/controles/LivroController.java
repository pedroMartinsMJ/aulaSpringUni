package oi.github.pedroMartinsMJ.librayapi2.controles;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import oi.github.pedroMartinsMJ.librayapi2.controles.dto.CadastroLivroDTO;
import oi.github.pedroMartinsMJ.librayapi2.controles.dto.ResultadoPesquisaLivroDTO;
import oi.github.pedroMartinsMJ.librayapi2.controles.mappers.LivroMapper;
import oi.github.pedroMartinsMJ.librayapi2.model.Livro;
import oi.github.pedroMartinsMJ.librayapi2.service.LivroService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController{

    private final LivroService livroService;
    private final LivroMapper livroMapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO livroDTO){
        Livro livro = livroMapper.toEntity(livroDTO);
        livroService.salvar(livro);

        URI url = gererHeaderLocation(livro.getId());

        return ResponseEntity.created(url).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<ResultadoPesquisaLivroDTO> buscarLivro(@PathVariable("id") String path){
        UUID id = UUID.fromString(path);
        ResultadoPesquisaLivroDTO livroDTO = livroMapper.toDTO(livroService.buscarLivroPorID(id));
        return ResponseEntity.ok().body(livroDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletar(@PathVariable("id") String path){
        UUID id = UUID.fromString(path);
        livroService.deletarLivroPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ResultadoPesquisaLivroDTO>> pesquisa(){
        
    }
}
