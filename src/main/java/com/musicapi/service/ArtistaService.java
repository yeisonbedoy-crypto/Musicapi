package com.musicapi.service;

import com.musicapi.exception.ResourceNotFoundException;
import com.musicapi.model.Artista;
import com.musicapi.repository.ArtistaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtistaService {

    private final ArtistaRepository artistaRepository;

    public List<Artista> findAll() {
        return artistaRepository.findAll();
    }

    public Artista findById(Long id) {
        return artistaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artista con id " + id + " no encontrado"));
    }

    public Artista save(Artista artista) {
        return artistaRepository.save(artista);
    }

    public Artista update(Long id, Artista artista) {
        Artista existing = findById(id);
        existing.setNombre(artista.getNombre());
        existing.setPais(artista.getPais());
        existing.setActivo(artista.getActivo());
        return artistaRepository.save(existing);
    }

    public void delete(Long id) {
        findById(id);
        artistaRepository.deleteById(id);
    }

    public List<Artista> buscar(String nombre, String sortBy, String order) {
        Sort sort = order.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        return artistaRepository.findByNombreContainingIgnoreCase(nombre, sort);
    }
}
