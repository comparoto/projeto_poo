package com.conectaarena.repository;

import com.conectaarena.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Integer> {

    List<Agendamento> findByUsuarioId(int usuarioId);

    void deleteByUsuarioId(int usuarioId);
}