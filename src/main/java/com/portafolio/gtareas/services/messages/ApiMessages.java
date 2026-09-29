package com.portafolio.gtareas.services.messages;


public abstract class ApiMessages {


    private ApiMessages() {
    }

    public static final String ERROR_CORREO_REGISTRADO = "El correo ya se encuentra registrado";
    public static final String ERROR_NO_EXISTE_TAREA = "La tarea no existe o no se encuentra disponible.";
    public static final String ERROR_NO_EXISTE_USUARIO = "El usuario no existe";
    public static final String ERROR_JWT_MAL_FORMADO_EXPIRADO = "El token proporcionado ha expirado o es inválido.";
    public static final String ERROR_OPERACION = "No se puede realizar la operación requerida";
    public static final String ERROR_VALIDACIONES_REQUEST = "Existe los siguientes errores: ";
    public static final String ERROR_ESTADO_TAREA = "Estado de tarea no disponible: ";
    public static final String ERROR_TOKEN_NO_VALIDO = "El token enviado en  ";
    public static final String ERROR_UUID_NO_VALIDO = " no es un UUID válido. Valor recibido: ";
    public static final String ERROR_NO_ESPERADO = "El valor enviado no es un tipo esperado";


}
