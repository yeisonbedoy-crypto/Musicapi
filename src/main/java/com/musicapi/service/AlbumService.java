package com.musicapi.service;

import com.musicapi.exception.ResourceNotFoundException;
import com.musicapi.model.Album;
import com.musicapi.model.Artista;
import com.musicapi.repository.AlbumRepository;
import com.musicapi.repository.ArtistaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistaRepository artistaRepository;

    public List<Album> findAll() {
        return albumRepository.findAll();
    }

    public Album findById(Long id) {
        return albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Album con id " + id + " no encontrado"));
    }

    public Album save(Album album) {
        if (album.getArtista() != null && album.getArtista().getId() != null) {
            Artista artista = artistaRepository.findById(album.getArtista().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Artista con id " + album.getArtista().getId() + " no encontrado"));
            album.setArtista(artista);
        }
        return albumRepository.save(album);
    }

    public Album update(Long id, Album album) {
        Album existing = findById(id);
        existing.setTitulo(album.getTitulo());
        existing.setAnio(album.getAnio());
        if (album.getArtista() != null && album.getArtista().getId() != null) {
            Artista artista = artistaRepository.findById(album.getArtista().getId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Artista con id " + album.getArtista().getId() + " no encontrado"));
            existing.setArtista(artista);
        }
        return albumRepository.save(existing);
    }

    public void delete(Long id) {
        findById(id);
        albumRepository.deleteById(id);
    }

    public Artista getArtista(Long albumId) {
        Album album = findById(albumId);
        if (album.getArtista() == null) {
            throw new ResourceNotFoundException("El album con id " + albumId + " no tiene artista asignado");
        }
        return album.getArtista();
    }

    public List<Album> buscar(String titulo, Integer anio, String sortBy, String order) {
        if (titulo == null) titulo = "";
        Sort sort = order.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        if (anio != null) {
            return albumRepository.findByTituloContainingIgnoreCaseAndAnio(titulo, anio, sort);
        }
        return albumRepository.findByTituloContainingIgnoreCase(titulo, sort);
    }
}
