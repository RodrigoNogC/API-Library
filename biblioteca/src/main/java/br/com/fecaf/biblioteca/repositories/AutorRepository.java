package br.com.fecaf.biblioteca.repositories;

import br.com.fecaf.biblioteca.models.AutorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AutorRepository extends JpaRepository<AutorModel, UUID> {
}
