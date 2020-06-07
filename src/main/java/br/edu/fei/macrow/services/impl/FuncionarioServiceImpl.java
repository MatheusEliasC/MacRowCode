package br.edu.fei.macrow.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.fei.macrow.entities.FuncionarioEntity;
import br.edu.fei.macrow.repository.FuncionarioRepository;
import br.edu.fei.macrow.services.FuncionarioService;

@Service
public class FuncionarioServiceImpl implements FuncionarioService{

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Override
	public Optional<FuncionarioEntity> FindById(int id) {
		return funcionarioRepository.findById(id);
	}
	
}