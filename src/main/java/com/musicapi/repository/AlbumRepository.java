package com.musicapi.repository;

import com.musicapi.model.Album;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByTituloContainingIgnoreCase(String titulo, Sort sort);
    List<Album> findByTituloContainingIgnoreCaseAndAnio(String titulo, Integer anio, Sort sort);
}
