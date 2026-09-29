package com.portafolio.gtareas.services.operators;

import com.portafolio.gtareas.database.models.Tarea;
import com.portafolio.gtareas.database.models.Usuario;
import com.portafolio.gtareas.database.repository.ITareaRepository;
import com.portafolio.gtareas.database.repository.IUsuarioRepository;
import com.portafolio.gtareas.rest.models.TareaDto;
import com.portafolio.gtareas.services.enums.EstadoTarea;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class TareaServiceTest {

    private final IUsuarioRepository iUsuarioRepository;
    private final TareaService tareaService;
    private final ITareaRepository iTareaRepository;

    @Autowired
    public TareaServiceTest(IUsuarioRepository iUsuarioRepository, TareaService tareaService, ITareaRepository iTareaRepository) {
        this.iUsuarioRepository = iUsuarioRepository;
        this.tareaService = tareaService;
        this.iTareaRepository = iTareaRepository;
    }

    private UUID tokenIdUser;
    private UUID tokenIdTarea;


    @BeforeEach
    void setUp() {

        tokenIdUser = UUID.fromString("e6b3aec0-6bef-4f4b-9b1c-7ae90004e856");
        tokenIdTarea = UUID.fromString("0a4f321e-3319-4db6-aa3e-ccd8d39e7250");
    }

    @Test
    @Order(1)
    void save() {

        TareaDto tareaDto = TareaDto.builder()
                .tokenId(tokenIdTarea)
                .titulo("titulo test")
                .descripcion("descripcion test")
                .estadoTarea(EstadoTarea.EN_PROGRESO)
                .build();

        Usuario usuario = iUsuarioRepository.save(Usuario.builder()
                .tokenId(tokenIdUser)
                .nombre("Usuario Test")
                .email("usuario_test@correo.com")
                .password("123456768")
                .build());


        TareaDto saved = tareaService.save(tareaDto, usuario);
        assertThat(saved.getTitulo()).isEqualTo(tareaDto.getTitulo());
        assertThat(saved.getDescripcion()).isEqualTo(tareaDto.getDescripcion());
        assertThat(saved.getEstadoTarea()).isEqualTo(tareaDto.getEstadoTarea());

    }

    @Test
    @Order(2)
    void update() {

        TareaDto tareaDtoUpdate = TareaDto.builder()
                .tokenId(tokenIdTarea)
                .titulo("Arreglar bug en servicio de ventas")
                .descripcion("Se estan duplicando los registros en la tabla")
                .estadoTarea(EstadoTarea.COMPLETADA)
                .build();

        TareaDto updated = tareaService.update(tokenIdTarea, tareaDtoUpdate, iUsuarioRepository.findByTokenId(tokenIdUser));
        assertThat(updated.getTitulo()).isEqualTo(tareaDtoUpdate.getTitulo());
        assertThat(updated.getDescripcion()).isEqualTo(tareaDtoUpdate.getDescripcion());
        assertThat(updated.getEstadoTarea()).isEqualTo(tareaDtoUpdate.getEstadoTarea());

    }

    @Test
    @Order(3)
    void getAllForUser() {
        List<TareaDto> tareaDtoList = tareaService.getAllForUser(tokenIdUser);
        assertNotNull(tareaDtoList, "La lista no debería ser null");
        assertFalse(tareaDtoList.isEmpty(), "La lista debería contener al menos una tarea");
        for (TareaDto tarea : tareaDtoList) {
            assertEquals(tokenIdUser, tarea.getTokenIdUsuario());
        }
    }

    @Test
    @Order(4)
    void delete() {

        TareaDto tareaDelete = TareaDto.builder()
                .tokenId(UUID.randomUUID())
                .titulo("Arreglar bug en servicio de compra")
                .descripcion("Se estan duplicando los registros")
                .estadoTarea(EstadoTarea.PENDIENTE)
                .build();


        Usuario user = iUsuarioRepository.findByTokenId(tokenIdUser);
        TareaDto saved = tareaService.save(tareaDelete, user);
        tareaService.delete(saved.getTokenId(), user);

        Optional<Tarea> tareaEliminada = iTareaRepository.findByTokenId(saved.getTokenId());
        assertFalse(tareaEliminada.isPresent());


    }
}