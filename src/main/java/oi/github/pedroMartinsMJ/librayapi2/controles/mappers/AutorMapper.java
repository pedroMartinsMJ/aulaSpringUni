package oi.github.pedroMartinsMJ.librayapi2.controles.mappers;

import oi.github.pedroMartinsMJ.librayapi2.controles.dto.AutorDTO;
import oi.github.pedroMartinsMJ.librayapi2.model.Autor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    Autor toEntity(AutorDTO dto);

    AutorDTO toDTO(Autor autor);

}
