package com.musicapi.controller;

import com.musicapi.model.Album;
import com.musicapi.model.Cancion;
import com.musicapi.service.CancionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/canciones")
@RequiredArgsConstructor
public class CancionController {

    private final CancionService cancionService;

    @GetMapping
    public ResponseEntity<List<Cancion>> getAll() {
        return ResponseEntity.ok(cancionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cancion> getById(@PathVariable Long id) {
        return ResponseEntity.ok(cancionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Cancion> create(@Valid @RequestBody Cancion cancion) {
        return ResponseEntity.status(201).body(cancionService.save(cancion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cancion> update(@PathVariable Long id,
                                          @Valid @RequestBody Cancion cancion) {
        return ResponseEntity.ok(cancionService.update(id, cancion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cancionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Modulo A — navegacion de relacion ManyToOne
    @GetMapping("/{id}/album")
    public ResponseEntity<Album> getAlbum(@PathVariable Long id) {
        return ResponseEntity.ok(cancionService.getAlbum(id));
    }

    // Modulo B — busqueda con @RequestParam opcionales
    @GetMapping("/buscar")
    public ResponseEntity<List<Cancion>> buscar(
            @RequestParam(required = false, defaultValue = "") String titulo,
            @RequestParam(required = false) Long albumId) {
        return ResponseEntity.ok(cancionService.buscar(titulo, albumId));
    }

    // Modulo C — añadir genero a cancion (ManyToMany)
    @PostMapping("/{id}/generos/{generoId}")
    public ResponseEntity<Cancion> addGenero(@PathVariable Long id,
                                             @PathVariable Long generoId) {
        return ResponseEntity.ok(cancionService.addGenero(id, generoId));
    }

    // Modulo C — quitar genero de cancion
    @DeleteMapping("/{id}/generos/{generoId}")
    public ResponseEntity<Void> removeGenero(@PathVariable Long id,
                                             @PathVariable Long generoId) {
        cancionService.removeGenero(id, generoId);
        return ResponseEntity.noContent().build();
    }

    // Modulo C — @Query JPQL: contar canciones por genero
    @GetMapping("/contar-por-genero/{generoId}")
    public ResponseEntity<Map<String, Long>> contarPorGenero(@PathVariable Long generoId) {
        Long count = cancionService.contarPorGenero(generoId);
        return ResponseEntity.ok(Map.of("generoId", generoId, "totalCanciones", count));
    }
}
