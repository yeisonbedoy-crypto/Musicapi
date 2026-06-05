package com.musicapi.repository;

import com.musicapi.model.Cancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CancionRepository extends JpaRepository<Cancion, Long> {
    List<Cancion> findByTituloContainingIgnoreCase(String titulo);
    List<Cancion> findByTituloContainingIgnoreCaseAndAlbumId(String titulo, Long albumId);

    @Query("SELECT COUNT(c) FROM Cancion c JOIN c.generos g WHERE g.id = :generoId")
    Long contarCancionesPorGenero(@Param("generoId") Long generoId);
}
