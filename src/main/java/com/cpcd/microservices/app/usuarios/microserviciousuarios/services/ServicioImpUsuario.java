package com.cpcd.microservices.app.usuarios.microserviciousuarios.services;

import com.cpcd.microservices.app.servicescommons.models.entity.Role;
import com.cpcd.microservices.app.servicescommons.models.entity.Usuario;
import com.cpcd.microservices.app.servicescommons.models.requests.NuevoUsuarioRequest;
import com.cpcd.microservices.app.usuarios.microserviciousuarios.models.RepositorioRoles;
import com.cpcd.microservices.app.usuarios.microserviciousuarios.models.RepositorioUsuario;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioImpUsuario implements ServicioUsuarios {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    private RepositorioRoles repositorioRoles;

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findById(String id) {
        return repositorioUsuario.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findByEmail(String email) {
        return repositorioUsuario.findByEmail(email);
    }

    @Override
    @Transactional
    public Usuario crearNuevoUsuario(NuevoUsuarioRequest nuevoUsuarioRequest) throws ServicioUsuariosException {
        if (repositorioUsuario.existsByEmail(nuevoUsuarioRequest.getEmail()))
            throw new ServicioUsuariosException("Email duplicado: " + nuevoUsuarioRequest.getEmail());

        Usuario usuario = new Usuario(nuevoUsuarioRequest.getNombre(), nuevoUsuarioRequest.getApellidos(), nuevoUsuarioRequest.getEmail());

        if (!StringUtils.isEmpty(nuevoUsuarioRequest.getAvatar()))
            usuario.setAvatar(nuevoUsuarioRequest.getAvatar());

        List<Role> roles = findRolesByApplication(nuevoUsuarioRequest.getAplicacion());
        usuario.setRoles(roles);
        usuario.setPassword(passwordEncoder.encode(nuevoUsuarioRequest.getPassword()));
        usuario.setIntentos(0);
        usuario.setEnabled(true);

        usuario = repositorioUsuario.save(usuario);

        return usuario;
    }

    private List<Role> findRolesByApplication(String application) {
        List<Role> roleList = new ArrayList<Role>();

        if ("INDIe".equals(application)){
            roleList.add(repositorioRoles.findByNombre("ROLE_INDIE_USER"));
        }else{
            roleList.add(repositorioRoles.findByNombre("ROLE_INDIE_USER"));
            roleList.add(repositorioRoles.findByNombre("ROLE_USER"));
        }

        return  roleList;
    }

    @Override
    @Transactional
    public Usuario save(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return repositorioUsuario.save(usuario);
    }

    @Override
    @Transactional
    public Usuario update(Usuario usuario) {
        return repositorioUsuario.save(usuario);
    }


    @Override
    @Transactional
    public void deleteById(String id) {
        repositorioUsuario.deleteById(id);
    }

    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
