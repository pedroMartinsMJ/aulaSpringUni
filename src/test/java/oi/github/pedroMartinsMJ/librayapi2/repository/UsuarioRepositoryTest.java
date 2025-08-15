package oi.github.pedroMartinsMJ.librayapi2.repository;

import oi.github.pedroMartinsMJ.librayapi2.model.Usuario;
import oi.github.pedroMartinsMJ.librayapi2.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    PasswordEncoder passwordEncoder;

//    @Autowired
//    private MockMvc mockMvc;

    @Test
    public void addUsuario(){
        Usuario usuario1 = new Usuario("pedro", "123", "ADMIN");
        Usuario usuario2 = new Usuario("leon", "123", "USER");
        Usuario[] usuarios = { usuario1, usuario2 };

        usuarioService.salvarAll(usuarios);

        buscarUsuario(usuario1);
    }

    @Test
    public void addUsuarioEncode(){
        Usuario usuario1 = new Usuario("admin", "admin", "ADMIN");

        usuario1.setSenha(passwordEncoder.encode(usuario1.getSenha()));

        usuarioRepository.save(usuario1);

        buscarUsuario(usuario1);
    }

    public void buscarUsuario(Usuario usuario){
        Usuario SupostoUsuario = usuarioRepository.findByLogin(usuario.getLogin());
        System.out.println(usuario.toString());
    }

//    @Test
//    public void loginHttpBasicUsuario(){
//        mockMvc.perform(get("/livros")
//                        .with(httpBasic("pedro", "123")))
//                .andExpect(status().isOk());
//    }
}
