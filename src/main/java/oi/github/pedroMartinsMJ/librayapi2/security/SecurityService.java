package oi.github.pedroMartinsMJ.librayapi2.security;


import lombok.RequiredArgsConstructor;
import oi.github.pedroMartinsMJ.librayapi2.model.Usuario;
import oi.github.pedroMartinsMJ.librayapi2.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UsuarioService usuarioService;

    public Usuario obterUsuarioLogado(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication instanceof CustomAuthentication customAuth){
            return  customAuth.getUsuario();
        }

        return null;
    }

}
