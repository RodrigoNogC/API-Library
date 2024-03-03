package br.com.fecaf.biblioteca.controllers;

import br.com.fecaf.biblioteca.dtos.LivroRecordDto;
import br.com.fecaf.biblioteca.models.LivroModel;
import br.com.fecaf.biblioteca.repositories.LivroRepository;
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
public class LivroController {

    @Autowired
    LivroRepository livroRepository;

    @PostMapping("/livro")
    public ResponseEntity<LivroModel> saveLivro(@RequestBody @Valid LivroRecordDto livroRecordDto){
        var livroModel = new LivroModel();
        BeanUtils.copyProperties(livroRecordDto, livroModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(livroRepository.save(livroModel));
    }

    @GetMapping("/livro")
    public ResponseEntity<List<LivroModel>> getAllLivro(){
        List<LivroModel> livroList = livroRepository.findAll();
        if(!livroList.isEmpty()){
            for (LivroModel livro : livroList){
                UUID id = livro.getIsbn();
                livro.add(linkTo(methodOn(LivroController.class).getAllLivro()).withSelfRel());
            }

        }
        return ResponseEntity.status(HttpStatus.OK).body(livroList);
    }

    @GetMapping("/livro/{id}")
    public ResponseEntity<Object> getOneLivro(@PathVariable(value = "id") UUID id){
        Optional<LivroModel> livroO = livroRepository.findById(id);
        if (livroO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }
        livroO.get().add(linkTo(methodOn(LivroController.class).getAllLivro()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(livroO.get());
    }

    @PutMapping("/livro/{id}")
    public ResponseEntity<Object> updateLivro(@PathVariable(value = "id") UUID id,
                                              @RequestBody @Valid LivroRecordDto livroRecordDto){
        Optional<LivroModel> livroO = livroRepository.findById(id);
        if (livroO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }
        var livroModel = livroO.get();
        BeanUtils.copyProperties(livroRecordDto, livroModel);
        return ResponseEntity.status(HttpStatus.OK).body(livroRepository.save(livroModel));
    }

    @DeleteMapping("/livro/{id}")
    public ResponseEntity<Object> deleteLivro(@PathVariable(value = "id") UUID id){

        Optional<LivroModel> livroO = livroRepository.findById(id);
        if (livroO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book not found");
        }
        livroRepository.delete(livroO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Book delete sucessfully");
    }
}
