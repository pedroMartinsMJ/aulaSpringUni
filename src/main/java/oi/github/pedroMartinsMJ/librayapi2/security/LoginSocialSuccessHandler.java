package oi.github.pedroMartinsMJ.librayapi2.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import oi.github.pedroMartinsMJ.librayapi2.model.Usuario;
import oi.github.pedroMartinsMJ.librayapi2.service.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class LoginSocialSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {



    private final UsuarioService usuarioService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws ServletException, IOException {
        OAuth2AuthenticationToken auth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User auth2User = auth2AuthenticationToken.getPrincipal();

        String email = auth2User.getAttribute("email");
        System.out.println(email);

        Usuario usuario = usuarioService.obterPorEmail(email);

        if (usuario == null) {
            cadastrarUsuaruioNovoGoogle(auth2User);
        }

        authentication = new CustomAuthentication(usuario);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        super.onAuthenticationSuccess(request, response, authentication);
    }

    public Usuario cadastrarUsuaruioNovoGoogle(OAuth2User oAuth2User){
        Usuario usuario = new Usuario();
        usuario.setLogin(oAuth2User.getAttribute("name"));
        usuario.setEmail(oAuth2User.getAttribute("email"));
        usuarioService.salvar(usuario);
        return usuarioService.obterPorLogin(usuario.getLogin());
    }
}
