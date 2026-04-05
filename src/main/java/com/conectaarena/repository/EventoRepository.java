package com.conectaarena.repository;

import com.conectaarena.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    // buscar eventos com data maior que a atual, ordenados pela mais próxima
    List<Evento> findByDataAfterOrderByDataAsc(LocalDateTime data);

    // busca por categoria e data futura
    List<Evento> findByCategoriaAndDataAfterOrderByDataAsc(String categoria, LocalDateTime data);
}
