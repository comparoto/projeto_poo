package com.conectaarena.repository;

import com.conectaarena.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;//interface com métodos prontos de banco de dados
import org.springframework.stereotype.Repository;//anotacao repository
import java.util.Optional;//pra poder usar o optional

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{//optional pra evitar NullPointerException
    Optional<Usuario> findByEmail(String email);//busca o usuário pelo e-mail
    boolean existsByEmail(String email);//vê se o e-mail já existe
    boolean existsByCpf(String cpf);
}