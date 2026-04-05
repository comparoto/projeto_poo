package com.conectaarena.repository;

import com.conectaarena.model.Sugestao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SugestaoRepository extends JpaRepository<Sugestao, Long> {
}