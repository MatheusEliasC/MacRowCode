package br.edu.fei.macrow.services;

import java.util.List;

import br.edu.fei.macrow.entities.PedidoEntity;

public interface PedidoService {

	public List<PedidoEntity> getPedidos();
	
	public void salvar(PedidoEntity entity);
	
	public long quantidade();
	
}
