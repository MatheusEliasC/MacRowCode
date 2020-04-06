package br.edu.fei.macrow.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.fei.macrow.entities.ProdutoEntity;
import br.edu.fei.macrow.repository.ProdutoRepository;
import br.edu.fei.macrow.services.ProdutoService;

@Service
public class ProdutoServiceImpl implements ProdutoService{

	@Autowired
	private ProdutoRepository produtoRepository;

	@Override
	public Optional<ProdutoEntity> FindById(int id) {
		return produtoRepository.findById(id);
	}
	
}
