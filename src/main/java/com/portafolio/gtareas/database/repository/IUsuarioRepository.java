package com.portafolio.gtareas.database.repository;

import com.portafolio.gtareas.database.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByTokenId(UUID tokenId);

    Optional<Usuario> findByEmail(String email);


}
