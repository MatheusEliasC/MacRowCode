package br.edu.fei.macrow.services;

import java.util.Optional;

import br.edu.fei.macrow.entities.ProdutoEntity;

public interface ProdutoService {

	public Optional<ProdutoEntity> FindById(int id);
	
}
