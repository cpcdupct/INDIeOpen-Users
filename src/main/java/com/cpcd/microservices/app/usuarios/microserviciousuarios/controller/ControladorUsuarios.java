package com.cpcd.microservices.app.usuarios.microserviciousuarios.controller;

import com.cpcd.microservices.app.servicescommons.models.entity.Usuario;
import com.cpcd.microservices.app.servicescommons.models.requests.ActualizarInfoUsuarioRequest;
import com.cpcd.microservices.app.servicescommons.models.requests.NuevoUsuarioRequest;
import com.cpcd.microservices.app.usuarios.microserviciousuarios.services.ServicioUsuarios;

import com.cpcd.microservices.app.usuarios.microserviciousuarios.services.ServicioUsuariosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class ControladorUsuarios {
	@Autowired
	private ServicioUsuarios servicioUsuarios;

	@GetMapping("/search/{id}")
	public ResponseEntity<?> devolverUsuario(@PathVariable String id) {
		Optional<Usuario> cu = servicioUsuarios.findById(id);

		if (cu.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(cu.get());
		}
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<?> devolverUsuarioEmail(@PathVariable String email) {
		Optional<Usuario> cu = servicioUsuarios.findByEmail(email);

		if (cu.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(cu.get());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> borrarUsuario(@PathVariable String id) {
		servicioUsuarios.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PostMapping
	public ResponseEntity<?> addUsuario(@Valid @RequestBody NuevoUsuarioRequest nuevoUsuarioRequest,
			BindingResult result) {
		if (result.hasErrors()) {
			return validar(result);
		}

		try {
			Usuario cu = servicioUsuarios.crearNuevoUsuario(nuevoUsuarioRequest);
			return ResponseEntity.status(HttpStatus.CREATED).body(cu);
		} catch (ServicioUsuariosException exception) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
		}
	}

	@PutMapping("/password/{id}/{password}")
	public ResponseEntity<?> cambiarPassword(@PathVariable String id, @PathVariable String password) {
		Optional<Usuario> cu = servicioUsuarios.findById(id);
		if (cu.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Usuario ou = cu.get();
		ou.setPassword(password);
		Usuario ou2 = servicioUsuarios.save(ou);

		return ResponseEntity.status(HttpStatus.CREATED).body(ou2);
	}

	@PutMapping("/info/{id}")
	public ResponseEntity<?> editarInfoUsuario(@Valid @RequestBody ActualizarInfoUsuarioRequest reqeuest,
			@PathVariable String id, BindingResult result) {
		if (result.hasErrors()) {
			return validar(result);
		}

		Optional<Usuario> cu = servicioUsuarios.findById(id);
		if (cu.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Usuario usuario = cu.get();

		usuario.setNombre(reqeuest.getNombre());
		usuario.setApellido(reqeuest.getApellidos());
		usuario.setAvatar(reqeuest.getAvatar());

		servicioUsuarios.update(usuario);

		return ResponseEntity.ok().build();

	}

	@PutMapping("/{id}")
	public ResponseEntity<?> editarUsuario(@Valid @RequestBody Usuario usuario, @PathVariable String id,
			BindingResult result) {
		if (result.hasErrors()) {
			return validar(result);
		}

		Optional<Usuario> cu = servicioUsuarios.findById(id);
		if (cu.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Usuario ou = cu.get();
		ou.setNombre(usuario.getNombre());
		ou.setApellido(usuario.getApellido());
		ou.setPassword(usuario.getPassword());
		ou.setEmail(usuario.getEmail());

		Usuario ou2 = servicioUsuarios.update(ou);

		return ResponseEntity.status(HttpStatus.CREATED).body(ou2);

	}

	protected ResponseEntity<?> validar(BindingResult result) {

		Map<String, Object> errores = new HashMap<>();
		result.getFieldErrors().forEach(err -> {
			errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
		});
		return ResponseEntity.badRequest().body(errores);
	}
}
