package com.cursospring.cursospring.dao;

import java.util.List;

import com.cursospring.cursospring.models.Usuario;

public interface UsuarioDao {
	
	List<Usuario> getUsuarios();

	void eliminar(Long id);

	void registrar(Usuario usuario);

	Usuario obtenerUsuarioPorCredenciales(Usuario usuario);

}
