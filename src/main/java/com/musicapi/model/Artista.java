package com.musicapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artistas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "albumes")
public class Artista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El país no puede estar vacío")
    private String pais;

    private Boolean activo;

    @OneToMany(mappedBy = "artista", cascade = CascadeType.ALL)
    @JsonIgnore
    @Builder.Default
    private List<Album> albumes = new ArrayList<>();
}
