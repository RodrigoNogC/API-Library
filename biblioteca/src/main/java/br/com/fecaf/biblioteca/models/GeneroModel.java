package br.com.fecaf.biblioteca.models;

import jakarta.persistence.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "tb_generos")
public class GeneroModel extends RepresentationModel<GeneroModel> implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID idGenero;
    private String name;

    public UUID getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(UUID idGenero) {
        this.idGenero = idGenero;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

