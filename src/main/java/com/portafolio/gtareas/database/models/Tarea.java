package com.portafolio.gtareas.database.models;

import com.portafolio.gtareas.services.enums.EstadoTarea;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tarea implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private UUID tokenId;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoTarea estadoTarea;

    @Column(nullable = false)
    private LocalDate fechaCreacion;

    private LocalDate fechaActualizacion;

    @ManyToOne
    private Usuario usuario;


    @PrePersist
    public void prePersist() {
        if (Objects.isNull(tokenId)) tokenId = UUID.randomUUID();
        if (Objects.isNull(fechaCreacion)) fechaCreacion = LocalDate.now();
        if(Objects.isNull(getFechaActualizacion())) fechaActualizacion = LocalDate.now();
    }

}
