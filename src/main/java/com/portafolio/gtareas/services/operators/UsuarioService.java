package com.portafolio.gtareas.services.operators;

import com.portafolio.gtareas.config.jwt.JwtService;
import com.portafolio.gtareas.database.models.Usuario;
import com.portafolio.gtareas.database.repository.IUsuarioRepository;
import com.portafolio.gtareas.rest.builder.UsuarioBuilder;
import com.portafolio.gtareas.rest.models.AuthenticationDto;
import com.portafolio.gtareas.rest.models.AuthenticationResponse;
import com.portafolio.gtareas.rest.models.UsuarioDto;
import com.portafolio.gtareas.services.exception.ApiException;
import com.portafolio.gtareas.services.messages.ApiMessages;
import com.portafolio.gtareas.config.UtilPassword;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuarioService {

    private final IUsuarioRepository iUsuarioRepository;
    private final UsuarioBuilder usuarioBuilder;
    private final UtilPassword utilPassword;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UsuarioDto save(UsuarioDto model) {
        validateEmail(model.getEmail());
        model.setPassword(utilPassword.passwordEncoder().encode(model.getPassword()));
        return usuarioBuilder.builderDto(iUsuarioRepository.save(usuarioBuilder.builder(model)));
    }

    private void validateEmail(String email) {
        iUsuarioRepository.findByEmail(email).ifPresent(x -> {
            throw new ApiException(ApiMessages.ERROR_CORREO_REGISTRADO);
        });
    }


    public AuthenticationResponse login(AuthenticationDto model) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                model.getEmail(), model.getPassword()));

        Usuario usuario = iUsuarioRepository.findByEmail(model.getEmail())
                .orElseThrow(() -> new ApiException(ApiMessages.ERROR_NO_EXISTE_USUARIO));

        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(usuario))
                .build();
    }

}
