package br.com.fecaf.biblioteca.controllers;

import br.com.fecaf.biblioteca.dtos.GeneroRecordDto;
import br.com.fecaf.biblioteca.models.GeneroModel;
import br.com.fecaf.biblioteca.repositories.GeneroRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class GeneroController {
    @Autowired
    GeneroRepository generoRepository;

    @PostMapping("/genero")
    public ResponseEntity<GeneroModel> saveGenenro(@RequestBody @Valid GeneroRecordDto generoRecordDto){
        var generoModel = new GeneroModel();
        BeanUtils.copyProperties(generoRecordDto, generoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(generoRepository.save(generoModel));
    }

    @GetMapping("/genero")
    public ResponseEntity<List<GeneroModel>> getAllGenero(){
        List<GeneroModel> generoList = generoRepository.findAll();
         if (!generoList.isEmpty()){
             for (GeneroModel genero:generoList){
                 UUID id = genero.getIdGenero();
                 genero.add(linkTo(methodOn(GeneroController.class).getOneGenero(id)).withSelfRel());
             }
         }
         return ResponseEntity.status(HttpStatus.OK).body(generoList);

    }
    @GetMapping("/genero/{id}")
    public ResponseEntity<Object> getOneGenero(@PathVariable (value = "id") UUID id){
        Optional<GeneroModel> generoO = generoRepository.findById(id);
        if (generoO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Genre not found");
        }
        generoO.get().add(linkTo(methodOn(GeneroController.class).getAllGenero()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(generoO.get());

    }
    @PutMapping("/genero/{id}")
    public ResponseEntity<Object> updateGeneto(@PathVariable(value = "id") UUID id,
                                               @RequestBody @Valid GeneroRecordDto generoRecordDto){
        Optional<GeneroModel> generoO = generoRepository.findById(id);
        if (generoO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Genre not found");
        }
        var generoModel = generoO.get();
        BeanUtils.copyProperties(generoRecordDto, generoModel);
        return ResponseEntity.status(HttpStatus.OK).body(generoRepository.save(generoModel));
    }
    @DeleteMapping("/genero/{id}")
    public ResponseEntity<Object> deleteGenero(@PathVariable(value = "id") UUID id){

        Optional<GeneroModel> generoO = generoRepository.findById(id);
        if (generoO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Genre not found");

        }
        generoRepository.delete(generoO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Deleted sucessfully");
    }

}
