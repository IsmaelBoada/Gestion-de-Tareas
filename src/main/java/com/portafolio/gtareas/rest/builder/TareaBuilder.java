package com.portafolio.gtareas.rest.builder;

import com.portafolio.gtareas.database.models.Tarea;
import com.portafolio.gtareas.database.models.Usuario;
import com.portafolio.gtareas.rest.models.TareaDto;
import com.portafolio.gtareas.services.enums.EstadoTarea;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;


@Component
@AllArgsConstructor
public class TareaBuilder {

    public Tarea builder(TareaDto model, Usuario usuario) {
        return Tarea.builder()
                .id(model.getId())
                .tokenId(model.getTokenId())
                .titulo(model.getTitulo())
                .descripcion(model.getDescripcion())
                .estadoTarea(Objects.nonNull(model.getEstadoTarea())
                        ? model.getEstadoTarea()
                        : EstadoTarea.PENDIENTE)
                .usuario(usuario)
                .build();
    }


    public Tarea builderUpdate(TareaDto model, Tarea item, Usuario usuario) {
        return Tarea.builder()
                .id(item.getId())
                .tokenId(item.getTokenId())
                .fechaCreacion(item.getFechaCreacion())
                .titulo(model.getTitulo())
                .descripcion(model.getDescripcion())
                .estadoTarea(Objects.nonNull(model.getEstadoTarea())
                        ? model.getEstadoTarea()
                        : EstadoTarea.PENDIENTE)
                .fechaActualizacion(model.getFechaActualizacion())
                .usuario(usuario)
                .build();
    }

    public TareaDto builderDto(Tarea model) {
        return TareaDto.builder()
                .id(model.getId())
                .tokenId(model.getTokenId())
                .fechaCreacion(model.getFechaCreacion())
                .fechaActualizacion(model.getFechaActualizacion())
                .titulo(model.getTitulo())
                .descripcion(model.getDescripcion())
                .estadoTarea(model.getEstadoTarea())
                .tokenIdUsuario(model.getUsuario().getTokenId())
                .build();
    }


}
