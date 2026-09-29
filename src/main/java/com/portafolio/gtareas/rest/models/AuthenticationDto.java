package com.portafolio.gtareas.rest.models;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationDto {

    @NotNull(message = "Es requerido el email usuario")
    private String email;

    @NotNull(message = "Es requerido la contraseña")
    private String password;
}
