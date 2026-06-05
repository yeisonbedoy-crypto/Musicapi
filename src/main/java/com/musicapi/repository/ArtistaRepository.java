package com.musicapi.repository;

import com.musicapi.model.Artista;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {
    List<Artista> findByNombreContainingIgnoreCase(String nombre, Sort sort);
}
