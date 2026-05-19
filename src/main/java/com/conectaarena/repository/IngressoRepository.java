package com.conectaarena.repository;

import com.conectaarena.model.Ingresso;
import com.conectaarena.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IngressoRepository extends JpaRepository<Ingresso, Integer> {

    List<Ingresso> findByComprador(Usuario comprador);

    List<Ingresso> findByEventoId(int eventoId);
}