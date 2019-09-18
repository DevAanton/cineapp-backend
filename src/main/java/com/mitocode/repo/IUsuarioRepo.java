package com.mitocode.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mitocode.model.Usuario;

public interface IUsuarioRepo extends JpaRepository<Usuario, Integer>{

	Usuario findOneByUsername(String username);
	
	@Query(value="SELECT usu.*\r\n" + 
			"	FROM Cliente clie\r\n" + 
			"	inner join usuario usu	on usu.id_usuario = clie.id_cliente\r\n" + 
			"	where clie.nombres = :nombre ;", nativeQuery = true)	
	Usuario findUser(@Param("nombre") String nombre);
	
	//Usuario findUser(String nombre);
	
	@Modifying
	@Query(value = "INSERT INTO usuario_rol (id_usuario, id_rol) VALUES (:idUsuario, :idRol)", nativeQuery = true)
	void registrarRolPorDefecto(@Param("idUsuario") Integer idUsuario, @Param("idRol") Integer idRol);
	
	/*
	@Modifying
	@Query("UPDATE Usuario set clave = :clave where id = :id")
	void modificarUsuarioP(@Param("id") Integer id, @Param("clave") String clave);*/
}
