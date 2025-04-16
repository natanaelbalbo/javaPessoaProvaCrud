package com.api.crud.controller;

import com.api.crud.dto.TrabalhoDTO;
import com.api.crud.service.TrabalhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/trabalhos")
public class TrabalhoController {

    @Autowired
    private TrabalhoService trabalhoService;

    @GetMapping
    public ResponseEntity<List<TrabalhoDTO>> listarTodos() {
        List<TrabalhoDTO> trabalhos = trabalhoService.listarTodos();
        return ResponseEntity.ok(trabalhos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrabalhoDTO> buscarPorId(@PathVariable Long id) {
        TrabalhoDTO trabalho = trabalhoService.buscarPorId(id);
        return ResponseEntity.ok(trabalho);
    }

    @PostMapping
    public ResponseEntity<TrabalhoDTO> cadastrar(@Valid @RequestBody TrabalhoDTO trabalhoDTO) {
        TrabalhoDTO trabalhoCadastrado = trabalhoService.cadastrar(trabalhoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(trabalhoCadastrado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrabalhoDTO> atualizar(@PathVariable Long id, @Valid @RequestBody TrabalhoDTO trabalhoDTO) {
        TrabalhoDTO trabalhoAtualizado = trabalhoService.atualizar(id, trabalhoDTO);
        return ResponseEntity.ok(trabalhoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        trabalhoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
} 