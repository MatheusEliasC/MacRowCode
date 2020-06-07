package br.edu.fei.macrow.services;

import java.util.Optional;

import br.edu.fei.macrow.entities.FuncionarioEntity;

public interface FuncionarioService {

	public Optional<FuncionarioEntity> FindById(int id);
	
}

