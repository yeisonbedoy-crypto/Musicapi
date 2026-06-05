package com.musicapi.controller;

import com.musicapi.model.Artista;
import com.musicapi.service.ArtistaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/artistas")
@RequiredArgsConstructor
public class ArtistaController {

    private final ArtistaService artistaService;

    @GetMapping
    public ResponseEntity<List<Artista>> getAll() {
        return ResponseEntity.ok(artistaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artista> getById(@PathVariable Long id) {
        return ResponseEntity.ok(artistaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Artista> create(@Valid @RequestBody Artista artista) {
        return ResponseEntity.status(201).body(artistaService.save(artista));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artista> update(@PathVariable Long id,
                                          @Valid @RequestBody Artista artista) {
        return ResponseEntity.ok(artistaService.update(id, artista));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        artistaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Modulo B — busqueda con @RequestParam opcionales y Sort dinamico
    @GetMapping("/buscar")
    public ResponseEntity<List<Artista>> buscar(
            @RequestParam(required = false, defaultValue = "") String nombre,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order) {
        return ResponseEntity.ok(artistaService.buscar(nombre, sortBy, order));
    }
}
