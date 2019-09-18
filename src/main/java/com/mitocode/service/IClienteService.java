package com.mitocode.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mitocode.model.Cliente;
import com.mitocode.model.Usuario;

public interface IClienteService extends ICRUD<Cliente>{

	Page<Cliente> listarPageable(Pageable pageable);
	
	Cliente listarClientePorNombreUsuario(String nombre);	
	
	Usuario CargarDatosUsuario(String username);
	
	Usuario CargarUser(String nombre);
	
}
