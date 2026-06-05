package com.musicapi.service;

import com.musicapi.exception.ResourceNotFoundException;
import com.musicapi.model.Album;
import com.musicapi.model.Cancion;
import com.musicapi.model.Genero;
import com.musicapi.repository.AlbumRepository;
import com.musicapi.repository.CancionRepository;
import com.musicapi.repository.GeneroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CancionService {

    private final CancionRepository cancionRepository;
    private final AlbumRepository albumRepository;
    private final GeneroRepository generoRepository;

    public List<Cancion> findAll() {
        return cancionRepository.findAll();
    }

    public Cancion findById(Long id) {
        return cancionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cancion con id " + id + " no encontrada"));
    }

    public Cancion save(Cancion cancion) {
        if (cancion.getAlbum() != null && cancion.getAlbum().getId() != null) {
            Album album = albumRepository.findById(cancion.getAlbum().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Album con id " + cancion.getAlbum().getId() + " no encontrado"));
            cancion.setAlbum(album);
        }
        return cancionRepository.save(cancion);
    }

    public Cancion update(Long id, Cancion cancion) {
        Cancion existing = findById(id);
        existing.setTitulo(cancion.getTitulo());
        existing.setDuracion(cancion.getDuracion());
        if (cancion.getAlbum() != null && cancion.getAlbum().getId() != null) {
            Album album = albumRepository.findById(cancion.getAlbum().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Album con id " + cancion.getAlbum().getId() + " no encontrado"));
            existing.setAlbum(album);
        }
        return cancionRepository.save(existing);
    }

    public void delete(Long id) {
        findById(id);
        cancionRepository.deleteById(id);
    }

    public Album getAlbum(Long cancionId) {
        Cancion cancion = findById(cancionId);
        if (cancion.getAlbum() == null) {
            throw new ResourceNotFoundException("La cancion con id " + cancionId + " no tiene album asignado");
        }
        return cancion.getAlbum();
    }

    public List<Cancion> buscar(String titulo, Long albumId) {
        if (titulo == null) titulo = "";
        if (albumId != null) {
            return cancionRepository.findByTituloContainingIgnoreCaseAndAlbumId(titulo, albumId);
        }
        return cancionRepository.findByTituloContainingIgnoreCase(titulo);
    }

    @Transactional
    public Cancion addGenero(Long cancionId, Long generoId) {
        Cancion cancion = findById(cancionId);
        Genero genero = generoRepository.findById(generoId)
                .orElseThrow(() -> new ResourceNotFoundException("Genero con id " + generoId + " no encontrado"));
        if (!cancion.getGeneros().contains(genero)) {
            cancion.getGeneros().add(genero);
        }
        return cancionRepository.save(cancion);
    }

    @Transactional
    public void removeGenero(Long cancionId, Long generoId) {
        Cancion cancion = findById(cancionId);
        cancion.getGeneros().removeIf(g -> g.getId().equals(generoId));
        cancionRepository.save(cancion);
    }

    public Long contarPorGenero(Long generoId) {
        return cancionRepository.contarCancionesPorGenero(generoId);
    }
}
