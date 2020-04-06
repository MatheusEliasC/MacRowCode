package br.edu.fei.macrow.service.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import br.edu.fei.macrow.entities.ClienteEntity;
import br.edu.fei.macrow.services.ClienteService;

public class ClienteMapper {
	
	public static ClienteEntity converterEntradaPCliente(String entrada, ClienteService service) {
		
		String[] dadosString = entrada.split("@");
		
		String Nome = dadosString[0];
		String Login = dadosString[1];
		String Senha = dadosString[2];
		
		if(service.FindByLogin(Login) == null) {
			return new ClienteEntity(Nome,Login,Senha);
		}
		else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este login ja existe no banco de dados!");
		}
		
	}

	public static int checaLogin(String entrada, ClienteService service) {
	
		String[] dadosString = entrada.split("@");
		
		String Login = dadosString[0];
		String Senha = dadosString[1];
		
		if(service.FindByLogin(Login) == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este login não existe no banco de dados!");
		}
		if(!service.FindByLogin(Login).getSenha().equals(Senha)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha incorreta!");
		}
		return service.FindByLogin(Login).getId();
	
	}
	
}
