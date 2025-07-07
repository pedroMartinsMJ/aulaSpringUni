package oi.github.pedroMartinsMJ.librayapi2.controles.mappers;

import oi.github.pedroMartinsMJ.librayapi2.controles.LivroController;
import oi.github.pedroMartinsMJ.librayapi2.controles.dto.CadastroLivroDTO;
import oi.github.pedroMartinsMJ.librayapi2.controles.dto.ResultadoPesquisaLivroDTO;
import oi.github.pedroMartinsMJ.librayapi2.model.Livro;
import oi.github.pedroMartinsMJ.librayapi2.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {


    @Autowired
    AutorRepository autorRepository;

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null) )")
    public abstract Livro toEntity(CadastroLivroDTO dto);

    @Mapping(target = "autor", source = "autor")
    public abstract ResultadoPesquisaLivroDTO toDTO(Livro livro);
}
