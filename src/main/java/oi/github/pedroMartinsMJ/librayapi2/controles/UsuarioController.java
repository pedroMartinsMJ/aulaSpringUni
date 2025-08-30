package oi.github.pedroMartinsMJ.librayapi2.controles;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import oi.github.pedroMartinsMJ.librayapi2.controles.dto.UsuarioDTO;
import oi.github.pedroMartinsMJ.librayapi2.controles.mappers.UsuarioMapper;
import oi.github.pedroMartinsMJ.librayapi2.model.Usuario;
import oi.github.pedroMartinsMJ.librayapi2.service.UsuarioService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody @Valid UsuarioDTO usuarioDTO){
        Usuario usuario = usuarioMapper.toEntity(usuarioDTO);
        usuarioService.salvar(usuario);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('GERENTE')")
    public ResponseEntity<List<Usuario>> buscarUsuarios(){
        List<Usuario> usuarioList = usuarioService.buscarUsuarios();
        return ResponseEntity.ok().body(usuarioList);
    }
}
