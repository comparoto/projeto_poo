package com.conectaarena.repository;

import com.conectaarena.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // validar se o usuário existe no momento da compra ou login
    Optional<Usuario> findByEmail(String email);
}