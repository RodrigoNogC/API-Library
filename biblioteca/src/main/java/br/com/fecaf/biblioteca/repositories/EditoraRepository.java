package br.com.fecaf.biblioteca.repositories;

import br.com.fecaf.biblioteca.models.EditoraModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EditoraRepository extends JpaRepository<EditoraModel, UUID> {
}
