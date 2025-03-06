package oi.github.pedroMartinsMJ.librayapi2.repository;

import oi.github.pedroMartinsMJ.librayapi2.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {
}
