package com.mitocode.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mitocode.model.Usuario;
import com.mitocode.repo.IUsuarioRepo;
import com.mitocode.service.IUsuarioService;

@Service
public class UserServiceImpl implements UserDetailsService, IUsuarioService {
	
	@Autowired
	private IUsuarioRepo userRepo;
	
	@Value("${mitocine.default-rol}")
	private Integer DEFAULT_ROL;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user = userRepo.findOneByUsername(username); 		
		if (user == null) {
			throw new UsernameNotFoundException(String.format("Usuario no existe", username));
		}
		List<GrantedAuthority> authorities = new ArrayList<>();		
		user.getRoles().forEach( role -> {
			authorities.add(new SimpleGrantedAuthority(role.getNombre()));
		});		
		UserDetails userDetails = new User(user.getUsername(), user.getPassword(), authorities);		
		return userDetails;
	}

	@Transactional
	@Override
	public Usuario registrarTransaccional(Usuario usuario) {	
		Usuario u;
		try {
			u = userRepo.save(usuario);	
			userRepo.registrarRolPorDefecto(u.getIdUsuario(), DEFAULT_ROL);	
		}catch(Exception e) {
			throw e;
		}		
		return u;		
	}
			
	@Transactional
	@Override
	public Usuario modificar(Usuario usuario) {		
		return userRepo.save(usuario);
	}

	@Override
	public Usuario registrar(Usuario t) {
		return userRepo.save(t);
	}

	@Override
	public List<Usuario> listar() {
		return null;
	}

	@Override
	public Usuario leer(Integer id) {
		Optional<Usuario> op = userRepo.findById(id);
		return op.isPresent() ? op.get() : new Usuario();
	}

	@Override
	public void eliminar(Integer id) {
		// TODO Auto-generated method stub		
	}	
	
	@Override
	public Usuario CargarDatosUsuario(String username) throws UsernameNotFoundException {
		Usuario user = userRepo.findOneByUsername(username); 		
		if (user == null) {
			throw new UsernameNotFoundException(String.format("Usuario no existe", username));
		}			
		return user;
	}	
		
	@Override
	public Usuario CargarUser(String nombre) throws UsernameNotFoundException {
		Usuario user = userRepo.findUser(nombre); 		
		if (user == null) {
			throw new UsernameNotFoundException(String.format("Usuario no existe", nombre));
		}		
		return user;
	}
		

}
