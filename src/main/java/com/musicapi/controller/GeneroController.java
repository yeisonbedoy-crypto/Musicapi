package com.musicapi.controller;

import com.musicapi.model.Cancion;
import com.musicapi.model.Genero;
import com.musicapi.service.GeneroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/generos")
@RequiredArgsConstructor
public class GeneroController {

    private final GeneroService generoService;

    @GetMapping
    public ResponseEntity<List<Genero>> getAll() {
        return ResponseEntity.ok(generoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genero> getById(@PathVariable Long id) {
        return ResponseEntity.ok(generoService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Genero> create(@Valid @RequestBody Genero genero) {
        return ResponseEntity.status(201).body(generoService.save(genero));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genero> update(@PathVariable Long id,
                                         @Valid @RequestBody Genero genero) {
        return ResponseEntity.ok(generoService.update(id, genero));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        generoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Modulo C — canciones de un genero (ManyToMany inverso)
    @GetMapping("/{id}/canciones")
    public ResponseEntity<List<Cancion>> getCanciones(@PathVariable Long id) {
        return ResponseEntity.ok(generoService.getCanciones(id));
    }
}
