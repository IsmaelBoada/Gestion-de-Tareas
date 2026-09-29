package com.portafolio.gtareas.rest.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelResponseDto implements Serializable {


    private HttpStatus statusCode;
    private Object body;
    private String message;

}
