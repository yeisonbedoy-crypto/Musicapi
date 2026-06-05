package com.musicapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "canciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "generos")
public class Cancion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título no puede estar vacío")
    private String titulo;

    @Positive(message = "La duración debe ser un valor positivo en segundos")
    private Integer duracion;

    @ManyToOne
    @JoinColumn(name = "album_id")
    private Album album;

    @ManyToMany
    @JoinTable(
        name = "cancion_genero",
        joinColumns = @JoinColumn(name = "cancion_id"),
        inverseJoinColumns = @JoinColumn(name = "genero_id")
    )
    @Builder.Default
    private List<Genero> generos = new ArrayList<>();
}
