package oi.github.pedroMartinsMJ.librayapi2.service;

import lombok.RequiredArgsConstructor;
import oi.github.pedroMartinsMJ.librayapi2.execeptions.BuscaSQLnaoEncontrado;
import oi.github.pedroMartinsMJ.librayapi2.model.Usuario;
import oi.github.pedroMartinsMJ.librayapi2.repository.UsuarioRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
        Usuario supostoUsuario = usuarioRepository.findByLogin(login);

        if (supostoUsuario == null) {
            throw new UsernameNotFoundException("não foi encontrado o Login do usuario");
        }

        return supostoUsuario;
    }

    public Usuario obterPorEmail(String email){
        return usuarioRepository.findByEmail(email);
    }
}
