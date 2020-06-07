package br.edu.fei.macrow.services;

import java.util.List;
import java.util.Optional;

import br.edu.fei.macrow.entities.PedidoEntity;

public interface PedidoService {


	public Optional<PedidoEntity> FindById(int id);
	
	public List<PedidoEntity> getPedidos();
	
	public void salvar(PedidoEntity entity);
	
	public long quantidade();
	
	public void delete(int id);
	
}
