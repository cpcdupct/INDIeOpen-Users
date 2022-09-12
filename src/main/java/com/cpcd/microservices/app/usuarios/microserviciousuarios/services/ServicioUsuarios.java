package com.cpcd.microservices.app.usuarios.microserviciousuarios.services;

import com.cpcd.microservices.app.servicescommons.models.entity.Usuario;
import com.cpcd.microservices.app.servicescommons.models.requests.NuevoUsuarioRequest;

import java.util.Optional;

public interface ServicioUsuarios {

    public Optional<Usuario> findById(String id);

    public Optional<Usuario> findByEmail(String id);

    public Usuario crearNuevoUsuario(NuevoUsuarioRequest nuevoUsuarioRequest) throws ServicioUsuariosException;

    public Usuario save(Usuario autor);

    public Usuario update(Usuario autor);

    public void deleteById(String id);
}
