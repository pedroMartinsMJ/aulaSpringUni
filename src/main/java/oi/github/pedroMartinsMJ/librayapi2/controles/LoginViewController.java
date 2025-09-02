package oi.github.pedroMartinsMJ.librayapi2.controles;


import oi.github.pedroMartinsMJ.librayapi2.security.CustomAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller//ele controla paginas
public class LoginViewController {

    @GetMapping("/login")
    public String paginaLogin(){
        return "login";
    }

    @GetMapping("/")
    @ResponseBody///não é uma pagina
    public String paginaHome(Authentication authentication){
        if (authentication instanceof CustomAuthentication customAuth) {
            System.out.println(customAuth.getUsuario());
        }
        if (authentication instanceof OAuth2User auth2User){
            System.out.println(auth2User.getName());
        }
        return "ola " + authentication.getName();
    }
}
