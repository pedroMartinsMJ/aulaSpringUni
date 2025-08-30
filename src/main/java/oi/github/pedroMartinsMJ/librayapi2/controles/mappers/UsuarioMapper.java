package oi.github.pedroMartinsMJ.librayapi2.controles.mappers;


import oi.github.pedroMartinsMJ.librayapi2.controles.dto.UsuarioDTO;
import oi.github.pedroMartinsMJ.librayapi2.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
}
