package com.conectaarena.repository;

import com.conectaarena.model.Sugestao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SugestaoRepository extends JpaRepository<Sugestao, Integer> {

    List<Sugestao> findAllByOrderByDataCriacaoDesc();
}