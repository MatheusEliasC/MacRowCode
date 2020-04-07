package br.edu.fei.macrow.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.fei.macrow.entities.ClienteEntity;
import br.edu.fei.macrow.repository.ClienteRepository;
import br.edu.fei.macrow.services.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService{

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Override
	public void salvar(ClienteEntity entity) {
		clienteRepository.save(entity);
	}

	@Override
	public Optional<ClienteEntity> FindById(int id) {
		return clienteRepository.findById(id);
	}

	@Override
	public ClienteEntity FindByLogin(String login) {
		
		List<ClienteEntity> lista = clienteRepository.findAll();
		
		for(int i = 0;i<lista.size();i++) {
			if(lista.get(i).getLogin().equals(login)) {
				return lista.get(i);
			}
		}
		return null;
	}
	
	@Override
	public long quantidade() {
		return clienteRepository.count();
	}

	@Override
	public void delete(int id) {
		clienteRepository.deleteById(id);
		
	}
	
	

}
