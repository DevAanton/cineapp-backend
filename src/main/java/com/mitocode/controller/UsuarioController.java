package com.mitocode.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mitocode.exception.ModeloNotFoundException;
import com.mitocode.model.Cliente;
import com.mitocode.model.Usuario;
import com.mitocode.service.IUsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private IUsuarioService service;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	@PostMapping 
	private Usuario registrar(@RequestPart("usuario") Usuario usuario, @RequestPart("file") MultipartFile file)
			throws IOException {
		
		Cliente c = usuario.getCliente();
		c.setFoto(file.getBytes());
				
		usuario.setPassword(bcrypt.encode(usuario.getPassword()));
		return service.registrarTransaccional(usuario);
	}	
	
	@PutMapping//(produces = "application/json", consumes = "application/json")
	public Usuario modificar(@RequestPart("usuario") Usuario usuario, @RequestPart("file") MultipartFile file)
			throws IOException {
		
		Cliente c = usuario.getCliente();
		c.setFoto(file.getBytes());				
		usuario.setPassword(bcrypt.encode(usuario.getPassword()));		
		return service.modificar(usuario);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Usuario> leer(@PathVariable("id") Integer idUsuario) {
		Usuario us = service.leer(idUsuario);
		if(us.getIdUsuario()==null) {
			throw new ModeloNotFoundException("ID NO ENCONTRADO: " + idUsuario);
		}
		return new ResponseEntity<Usuario>(us, HttpStatus.OK);
	}
		
	
}
