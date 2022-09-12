package com.cpcd.microservices.app.usuarios.microserviciousuarios.initialization;

import com.cpcd.microservices.app.servicescommons.models.entity.Role;
import com.cpcd.microservices.app.usuarios.microserviciousuarios.models.RepositorioRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DataInitialization implements ApplicationListener<ApplicationReadyEvent> {

    private final RepositorioRoles repositorioRoles;

    @Autowired
    DataInitialization(RepositorioRoles repositorioRoles) {
        this.repositorioRoles = repositorioRoles;
    }

    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (!repositorioRoles.existsByNombre("ROLE_USER")) {
            Role role = new Role();
            role.setNombre("ROLE_USER");
            repositorioRoles.save(role);
        }

        if (!repositorioRoles.existsByNombre("ROLE_ADMIN")) {
            Role role = new Role();
            role.setNombre("ROLE_ADMIN");
            repositorioRoles.save(role);
        }

        if (!repositorioRoles.existsByNombre("ROLE_INDIE_USER")) {
            Role role = new Role();
            role.setNombre("ROLE_INDIE_USER");
            repositorioRoles.save(role);
        }
    }
}
