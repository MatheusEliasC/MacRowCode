package br.edu.fei.macrow.services;

import java.util.Optional;

import br.edu.fei.macrow.entities.ClienteEntity;

public interface ClienteService {

	public void salvar(ClienteEntity entity);
	
	public Optional<ClienteEntity> FindById(int id);
	
	public ClienteEntity FindByLogin(String login);
	
	public long quantidade();
	
}
