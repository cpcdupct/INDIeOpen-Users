package com.cpcd.microservices.app.usuarios.microserviciousuarios.services;

public class ServicioUsuariosException extends Exception {

    public ServicioUsuariosException(String message) {
        super(message);
    }

    public ServicioUsuariosException(String message, Throwable cause) {
        super(message, cause);
    }
}
