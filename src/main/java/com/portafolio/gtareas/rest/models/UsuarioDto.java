package com.portafolio.gtareas.rest.models;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto implements Serializable {

    private Long id;

    private UUID tokenId;

    @NotNull(message = "Es requerido el nombre del usuario")
    private String nombre;

    @NotNull(message = "Es requerido el email del usuario")
    @Email(message = "Debe tener el formato válido de un correo, por ejemplo: usuario@dominio.com")
    private String email;

    @NotNull(message = "Es requerido la contraseña")
    private String password;


}
