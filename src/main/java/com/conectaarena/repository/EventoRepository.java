package com.conectaarena.repository;

import com.conectaarena.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;//interface com métodos prontos de banco de dados
import org.springframework.stereotype.Repository; //anotacao repository
import java.time.LocalDateTime;
import java.util.List;//pra poder mostrar varios eventos

@Repository
public interface EventoRepository extends JpaRepository<Evento, Integer>{
    //métodos de consulta , ver a data dos proximos eventos e ver por categoria
    List<Evento> findByDataAfterOrderByDataAsc(LocalDateTime data);
    List<Evento> findByCategoriaAndDataAfterOrderByDataAsc(String categoria, LocalDateTime data);
}