package com.cpcd.microservices.app.usuarios.microserviciousuarios.models;

import com.cpcd.microservices.app.servicescommons.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RepositorioUsuario extends CrudRepository<Usuario, String> {

    public Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);
}
