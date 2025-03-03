package oi.github.pedroMartinsMJ.librayapi2.repository;

import oi.github.pedroMartinsMJ.librayapi2.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {
}
