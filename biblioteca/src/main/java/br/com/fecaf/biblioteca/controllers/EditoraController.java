package br.com.fecaf.biblioteca.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import br.com.fecaf.biblioteca.dtos.EditoraRecordDto;
import br.com.fecaf.biblioteca.models.EditoraModel;
import br.com.fecaf.biblioteca.repositories.EditoraRepository;
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
public class EditoraController {

    @Autowired
    EditoraRepository editoraRepository;

    @PostMapping("/editora")
    public ResponseEntity<EditoraModel> saveEditora(@RequestBody @Valid EditoraRecordDto editoraRecordDto){
        var editoraModel = new EditoraModel();
        BeanUtils.copyProperties(editoraRecordDto, editoraModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(editoraRepository.save(editoraModel));
    }

    @GetMapping("/editora")
    public ResponseEntity<List<EditoraModel>> getAllEditoras(){
        List<EditoraModel> editoraList = editoraRepository.findAll();
        if (!editoraList.isEmpty()){

            for (EditoraModel editora: editoraList){

                UUID id = editora.getIdEditora();
                editora.add(linkTo(methodOn(EditoraController.class).getOneEditora(id)).withSelfRel());
            }

        }

        return ResponseEntity.status(HttpStatus.OK).body(editoraList);
    }

    @GetMapping("/editora/{id}")
    public ResponseEntity<Object> getOneEditora(@PathVariable(value = "id") UUID id){
        Optional<EditoraModel> editoraO = editoraRepository.findById(id);
        if(editoraO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Publisher not found");
        }
        editoraO.get().add(linkTo(methodOn(EditoraController.class).getAllEditoras()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(editoraO.get());
    }

    @PutMapping("/editora/{id}")
    public ResponseEntity<Object> updateEditora(@PathVariable(value = "id") UUID id,
                                                @RequestBody @Valid EditoraRecordDto editoraRecordDto){
        Optional<EditoraModel> editoraO = editoraRepository.findById(id);
        if (editoraO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Publisher not found");
        }
        var editoraModel = editoraO.get();
        BeanUtils.copyProperties(editoraRecordDto, editoraModel);
        return ResponseEntity.status(HttpStatus.OK).body(editoraRepository.save(editoraModel));
    }

    @DeleteMapping("/editora/{id}")
    public ResponseEntity<Object> deleteEditora(@PathVariable(value = "id") UUID id){

        Optional<EditoraModel> editoraO = editoraRepository.findById(id);
        if (editoraO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Publisher not found");
        }
        editoraRepository.delete(editoraO.get());
        return ResponseEntity.status(HttpStatus.OK).body("Delete sucessfully");
    }
}
