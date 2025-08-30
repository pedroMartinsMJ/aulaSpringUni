package oi.github.pedroMartinsMJ.librayapi2.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import oi.github.pedroMartinsMJ.librayapi2.model.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
//essa classe vai ser usada na CustomAuthenticationProvider
public class CustomAuthentication implements Authentication {

    private final Usuario usuario;

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.usuario.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role))
                //se vc tiver na config padrão, tem que por o prefixo ROLE_ -> ("ROLE_" + rolett)
                .collect(Collectors.toList());
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return usuario;
    }

    @Override
    public Object getPrincipal() {
        return usuario;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return usuario.getLogin();
    }
}
