package br.edu.fei.macrow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.fei.macrow.entities.FuncionarioEntity;

public interface FuncionarioRepository extends JpaRepository<FuncionarioEntity, Integer>{

}
