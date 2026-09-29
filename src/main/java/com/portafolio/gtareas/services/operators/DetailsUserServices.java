package com.portafolio.gtareas.services.operators;

import com.portafolio.gtareas.database.models.Usuario;
import com.portafolio.gtareas.database.repository.IUsuarioRepository;
import com.portafolio.gtareas.services.exception.ApiException;
import com.portafolio.gtareas.services.messages.ApiMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DetailsUserServices implements UserDetailsService {

    private final IUsuarioRepository iUsuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = iUsuarioRepository.findByEmail(username)
                .orElseThrow(() -> new ApiException(ApiMessages.ERROR_NO_EXISTE_USUARIO));


        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .build();

    }
}
