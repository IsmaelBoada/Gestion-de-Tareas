package com.portafolio.gtareas.controller;


import com.portafolio.gtareas.rest.models.ModelResponseDto;
import com.portafolio.gtareas.rest.models.TareaDto;
import com.portafolio.gtareas.services.operators.TareaService;
import com.portafolio.gtareas.utils.HttpServletRequestService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("tareas")
public class TareaController {

    private final TareaService tareaService;
    private final HttpServletRequestService httpServletRequestService;

    @PostMapping("save")
    public ModelResponseDto saveData(@Valid @RequestBody TareaDto model,
                                     HttpServletRequest request) {

        return ModelResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .body(tareaService.save(model, httpServletRequestService.getTokenAccess(request)))
                .build();
    }


    @PutMapping("update")
    public ModelResponseDto updateData(@RequestParam UUID tokenId,
                                       @Valid @RequestBody TareaDto model,
                                       HttpServletRequest request) {
        return ModelResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .body(tareaService.update(tokenId, model, httpServletRequestService.getTokenAccess(request)))
                .build();
    }


    @GetMapping("getAll")
    public ModelResponseDto getAllData(HttpServletRequest request) {
        return ModelResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .body(tareaService.getAllForUser(httpServletRequestService.getTokenAccess(request).getTokenId()))
                .build();
    }

    @DeleteMapping("delete")
    public ModelResponseDto deleteData(@RequestParam UUID tokenId,
                                       HttpServletRequest request) {
        tareaService.delete(tokenId, httpServletRequestService.getTokenAccess(request));
        return ModelResponseDto.builder()
                .statusCode(HttpStatus.OK)
                .build();
    }


}
