package oi.github.pedroMartinsMJ.librayapi2.service;

import lombok.RequiredArgsConstructor;
import oi.github.pedroMartinsMJ.librayapi2.model.Usuario;
import oi.github.pedroMartinsMJ.librayapi2.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public void salvar(Usuario usuario){
        String senha = usuario.getSenha();
        usuario.setSenha(passwordEncoder.encode(senha));
        usuarioRepository.save(usuario);
    }

    public void salvarAll(Usuario[] usuarios) {
        Arrays.stream(usuarios)
                .forEach(usuario -> usuario.setSenha(passwordEncoder.encode(usuario.getSenha())));
        usuarioRepository.saveAll(Arrays.asList(usuarios));
    }

    public List<Usuario> buscarUsuarios(){
        return usuarioRepository.findAll();
    }


    public Usuario obterPorLogin(String login){
        return usuarioRepository.findByLogin(login);
    }
}
