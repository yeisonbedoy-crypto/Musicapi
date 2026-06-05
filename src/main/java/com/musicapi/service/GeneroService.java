package com.musicapi.service;

import com.musicapi.exception.ResourceNotFoundException;
import com.musicapi.model.Cancion;
import com.musicapi.model.Genero;
import com.musicapi.repository.GeneroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GeneroService {

    private final GeneroRepository generoRepository;

    public List<Genero> findAll() {
        return generoRepository.findAll();
    }

    public Genero findById(Long id) {
        return generoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genero con id " + id + " no encontrado"));
    }

    public Genero save(Genero genero) {
        return generoRepository.save(genero);
    }

    public Genero update(Long id, Genero genero) {
        Genero existing = findById(id);
        existing.setNombre(genero.getNombre());
        existing.setDescripcion(genero.getDescripcion());
        return generoRepository.save(existing);
    }

    public void delete(Long id) {
        findById(id);
        generoRepository.deleteById(id);
    }

    @Transactional
    public List<Cancion> getCanciones(Long generoId) {
        Genero genero = findById(generoId);
        return genero.getCanciones();
    }
}
