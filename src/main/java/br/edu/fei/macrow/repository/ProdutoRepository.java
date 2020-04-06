package br.edu.fei.macrow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.fei.macrow.entities.ProdutoEntity;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Integer>{

}
