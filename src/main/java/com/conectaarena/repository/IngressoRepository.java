package com.conectaarena.repository;

import com.conectaarena.model.Ingresso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngressoRepository extends JpaRepository<Ingresso, Long> {
    // herdando métodos padrão como save(), findAll(), etc
}