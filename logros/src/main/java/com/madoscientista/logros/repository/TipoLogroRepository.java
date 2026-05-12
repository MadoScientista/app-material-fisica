package com.madoscientista.logros.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.madoscientista.logros.model.TipoLogro;

@Repository
public interface TipoLogroRepository extends JpaRepository<TipoLogro, Long>{

}
