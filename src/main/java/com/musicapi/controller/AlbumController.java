package com.musicapi.controller;

import com.musicapi.model.Album;
import com.musicapi.model.Artista;
import com.musicapi.service.AlbumService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/albumes")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;

    @GetMapping
    public ResponseEntity<List<Album>> getAll() {
        return ResponseEntity.ok(albumService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> getById(@PathVariable Long id) {
        return ResponseEntity.ok(albumService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Album> create(@Valid @RequestBody Album album) {
        return ResponseEntity.status(201).body(albumService.save(album));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Album> update(@PathVariable Long id,
                                        @Valid @RequestBody Album album) {
        return ResponseEntity.ok(albumService.update(id, album));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        albumService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Modulo A — navegacion de relacion ManyToOne
    @GetMapping("/{id}/artista")
    public ResponseEntity<Artista> getArtista(@PathVariable Long id) {
        return ResponseEntity.ok(albumService.getArtista(id));
    }

    // Modulo B — busqueda con @RequestParam opcionales y Sort dinamico
    @GetMapping("/buscar")
    public ResponseEntity<List<Album>> buscar(
            @RequestParam(required = false, defaultValue = "") String titulo,
            @RequestParam(required = false) Integer anio,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order) {
        return ResponseEntity.ok(albumService.buscar(titulo, anio, sortBy, order));
    }
}
