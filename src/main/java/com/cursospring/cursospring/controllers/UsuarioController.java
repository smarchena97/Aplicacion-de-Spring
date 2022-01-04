package com.cursospring.cursospring.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cursospring.cursospring.dao.UsuarioDao;
import com.cursospring.cursospring.models.Usuario;
import com.cursospring.cursospring.utils.JWTUtil;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@RestController
public class UsuarioController {
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Autowired
	JWTUtil jwtutil;
	
	@RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.GET)
	public Usuario getUsuario (@PathVariable Long id) {
		
		Usuario usuario = new Usuario();
		usuario.setName("pepe");
		usuario.setApellido("moura");
		usuario.setId(id);
		usuario.setTelefono("312222");
		usuario.setEmail("pepe@mail.com");
		return usuario;
		
	}
	
	@RequestMapping(value = "api/usuarios", method = RequestMethod.GET)
	public List<Usuario> getUsuarios (@RequestHeader(value = "Authorization") String token ) {
		
		if(!validarToken(token)) {
			return null;
		}
		
		
//		String usuarioID = jwtutil.getKey(token);
//		if(usuarioID == null) {
//			return new ArrayList<>();
//		}
//		
		return usuarioDao.getUsuarios();
		
	}
	
	private boolean validarToken (String token) {
		
		String usuarioId = jwtutil.getKey(token);
		
		return usuarioId != null;
		
	}
	
	@RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
	public void registrarUsuario (@RequestBody Usuario usuario) {
		
		Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
		String hash = argon2.hash(1, 1024, 1, usuario.getPassword());
		usuario.setPassword(hash);
		usuarioDao.registrar(usuario);		
	}
	
	@RequestMapping(value = "usuario1")
	public Usuario editarUsuario (@PathVariable Long id) {
		
		Usuario usuario = new Usuario();
		usuario.setName("pepe");
		usuario.setApellido("moura");
		usuario.setId(id);
		usuario.setTelefono("312222");
		usuario.setEmail("pepe@mail.com");
		return usuario;
		
	}
	
	@RequestMapping(value = "api/usuarios/{id}", method = RequestMethod.DELETE)
	public void eliminarUsuario (@RequestHeader(value = "Authorization") String token ,@PathVariable Long id) {
		
		if(!validarToken(token)) {
			return;
		}
		usuarioDao.eliminar(id);
		
	}
	
	
	
}
