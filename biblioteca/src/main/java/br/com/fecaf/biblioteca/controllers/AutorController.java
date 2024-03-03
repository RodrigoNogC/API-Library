package br.com.fecaf.biblioteca.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import br.com.fecaf.biblioteca.dtos.AutorRecordDto;
import br.com.fecaf.biblioteca.models.AutorModel;
import br.com.fecaf.biblioteca.repositories.AutorRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class AutorController {

    @Autowired
    AutorRepository autorRepository;

    @PostMapping("/autor")
    public ResponseEntity<AutorModel> saveAutor(@RequestBody @Valid AutorRecordDto autorRecordDto){
        var autorModel = new AutorModel();
        BeanUtils.copyProperties(autorRecordDto, autorModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(autorRepository.save(autorModel));
    }

    @GetMapping("/autor")
    public ResponseEntity<List<AutorModel>> getAllAutores(){

        List<AutorModel> autorList = autorRepository.findAll();
        if (!autorList.isEmpty()){
            for (AutorModel autor:autorList){
                UUID id = autor.getIdAutor();
                autor.add(linkTo(methodOn(AutorController.class).getOneAutor(id)).withSelfRel());
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(autorList);
    }

    @GetMapping("/autor/{id}")
    public ResponseEntity<Object> getOneAutor(@PathVariable(value = "id")UUID id){
        Optional<AutorModel> autorO = autorRepository.findById(id);
        if (autorO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author is not found.");
        }

        autorO.get().add(linkTo(methodOn(AutorController.class).getAllAutores()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(autorO.get());

    }

    @PutMapping("/autor/{id}")
    public ResponseEntity<Object> updateAutor(@PathVariable(value = "id")UUID id,
                                              @RequestBody @Valid AutorRecordDto autorRecordDto){
        Optional<AutorModel> autorO = autorRepository.findById(id);
        if (autorO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author is not found.");
        }

        var autorModel = autorO.get();
        BeanUtils.copyProperties(autorRecordDto, autorModel);

        return ResponseEntity.status(HttpStatus.OK).body(autorRepository.save(autorModel));
    }

    @DeleteMapping("/autor/{id}")
    public ResponseEntity<Object> deleteAutor(@PathVariable(value = "id")UUID id){

        Optional<AutorModel> autorO = autorRepository.findById(id);

        if (autorO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author is not found");

        }
        autorRepository.delete(autorO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Author deleted sucessfully");
    }

}
