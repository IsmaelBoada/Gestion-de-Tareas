package com.portafolio.gtareas.controller;

import com.portafolio.gtareas.rest.models.AuthenticationDto;
import com.portafolio.gtareas.rest.models.ModelResponseDto;
import com.portafolio.gtareas.rest.models.UsuarioDto;
import com.portafolio.gtareas.services.operators.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("auth")
public class UsuarioController {

    private final UsuarioService usuarioService;


    @PostMapping("usuario/login")
    public ModelResponseDto login(@Valid @RequestBody AuthenticationDto model) {
        return ModelResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .body(usuarioService.login(model))
                .build();
    }

    @PostMapping("usuario/save")
    public ModelResponseDto saveData(@Valid @RequestBody UsuarioDto model) {
        return ModelResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .body(usuarioService.save(model))
                .build();
    }


}
