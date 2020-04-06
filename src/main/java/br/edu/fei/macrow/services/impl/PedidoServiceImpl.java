package br.edu.fei.macrow.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.fei.macrow.entities.PedidoEntity;
import br.edu.fei.macrow.repository.PedidoRepository;
import br.edu.fei.macrow.services.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService{

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Override
	public List<PedidoEntity> getPedidos() {
//		try {
			return pedidoRepository.findAll();
//		}catch(Exception e) {
////			
//		}
		
	}

	@Override
	public void salvar(PedidoEntity entity) {
		pedidoRepository.save(entity);
		
	}

	@Override
	public long quantidade() {
		return pedidoRepository.count();
	}

}
