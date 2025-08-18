package oi.github.pedroMartinsMJ.librayapi2.controles;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import oi.github.pedroMartinsMJ.librayapi2.controles.dto.CadastroLivroDTO;
import oi.github.pedroMartinsMJ.librayapi2.controles.dto.ResultadoPesquisaLivroDTO;
import oi.github.pedroMartinsMJ.librayapi2.controles.mappers.LivroMapper;
import oi.github.pedroMartinsMJ.librayapi2.model.GeneroLivro;
import oi.github.pedroMartinsMJ.librayapi2.model.Livro;
import oi.github.pedroMartinsMJ.librayapi2.service.LivroService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@Controller
@RequestMapping("/livros")
@RequiredArgsConstructor
public class LivroController implements GenericController{

    private final LivroService livroService;
    private final LivroMapper livroMapper;

    @PostMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO livroDTO){
        Livro livro = livroMapper.toEntity(livroDTO);
        livroService.salvar(livro);

        URI url = gererHeaderLocation(livro.getId());

        return ResponseEntity.created(url).build();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<ResultadoPesquisaLivroDTO> buscarLivro(@PathVariable("id") String path){
        UUID id = UUID.fromString(path);
        ResultadoPesquisaLivroDTO livroDTO = livroMapper.toDTO(livroService.buscarLivroPorID(id));
        return ResponseEntity.ok().body(livroDTO);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Void> deletar(@PathVariable("id") String path){
        UUID id = UUID.fromString(path);
        livroService.deletarLivroPorId(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Page<ResultadoPesquisaLivroDTO>> pesquisa(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "nomeAutor", required = false)
            String nomeAutor,
            @RequestParam(value = "genero", required = false)
            GeneroLivro genero,
            @RequestParam(value = "anoPublicacao", required = false)
            Integer anoPublicacao,
            @RequestParam(value = "pagina", defaultValue = "0")
            Integer pagina,
            @RequestParam(value = "tamanhoPagina", defaultValue = "10")
            Integer tamanhoPagina
    ){
        Pageable pageable = PageRequest.of(pagina, tamanhoPagina);
        Page<Livro> paginaResultado = livroService.pesquisa(isbn, titulo, nomeAutor, genero, anoPublicacao, pageable);

        Page<ResultadoPesquisaLivroDTO> resultado = paginaResultado.map(livroMapper::toDTO);

        return ResponseEntity.ok(resultado);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String path, @RequestBody @Valid CadastroLivroDTO dto){
        UUID id = UUID.fromString(path);
        livroService.atulaizarLivro(id, livroMapper.toEntity(dto));
        return ResponseEntity.noContent().build();
    }
}
