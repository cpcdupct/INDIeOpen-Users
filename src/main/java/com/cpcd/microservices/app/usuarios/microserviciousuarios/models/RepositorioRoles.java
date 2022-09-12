package com.cpcd.microservices.app.usuarios.microserviciousuarios.models;

import com.cpcd.microservices.app.servicescommons.models.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RepositorioRoles extends CrudRepository<Role, Long> {

    boolean existsByNombre(String role_usuario);

    Role findByNombre(String role_indie_user);
}
