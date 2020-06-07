package br.edu.fei.macrow.service.mapper;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import br.edu.fei.macrow.entities.ClienteEntity;
import br.edu.fei.macrow.services.ClienteService;

public class ClienteMapper {
	
	private static Integer findId(ClienteService service) {
		for(int i =1;i<101;i++) {
			if(!service.FindById(i).isPresent()) {
				return i;
			}
		}
		return null;
	}
	
	public static ClienteEntity converterEntradaPCliente(String entrada, ClienteService service) {
		
		String[] dadosString = entrada.split("@");
		
		String Nome = dadosString[0];
		String Login = dadosString[1];
		String Senha = dadosString[2];
		String Email = dadosString[3];
		String Dominio = dadosString[4];
		String Celular = dadosString[5];
		
		int id = findId(service);
		
		if(service.FindByLogin(Login) == null) {
			return new ClienteEntity(id,Nome,Login,Senha,Email,Dominio,Celular);
		}
		else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este login ja existe no banco de dados!");
		}
		
	}

	public static int checaLogin(String entrada, ClienteService service) {
	
		String[] dadosString = entrada.split("@");
		
		String Login = dadosString[0];
		String Senha = dadosString[1];
		
		ClienteEntity cliente = service.FindByLogin(Login);
		
		if(cliente == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Este login não existe no banco de dados!");
		}
		if(!cliente.getSenha().equals(Senha)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Senha incorreta!");
		}
		if(cliente.isVerificado() == false) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Conta não verificada.");
		}
		
		service.delete(cliente.getId());
		cliente.setLogado(true);
		service.salvar(cliente);
		
		return cliente.getId();
	
	}
	
	public static void confirmVerif(int codigo, ClienteService service) {
		
		ClienteEntity cliente = service.FindById(codigo).get();
		service.delete(codigo);
		cliente.setVerificado(true);
		service.salvar(cliente);
		
	}
	
	public static void verificarEAtualizarPedidoAtual(int codigoPedido, int codigoCliente,ClienteService service) {
		
		ClienteEntity cliente = service.FindById(codigoCliente).get();
		
		if(cliente.getPedido() != 0) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Já existe um pedido atual em andamento.");
		}
		
		atualizarPedidoAtual(codigoPedido, codigoCliente, service);
		
	}
	
	public static void atualizarPedidoAtual(int codigoPedido,int codigoCliente, ClienteService service) {
		
		ClienteEntity cliente = service.FindById(codigoCliente).get();
		
		if(cliente.isLogado() == false) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não logado.");
		}
		
		service.delete(codigoCliente);
		cliente.setPedido(codigoPedido);
		service.salvar(cliente);
		
	}
	
	public static int retornaIddoPedidoAtual(String codigoCliente, ClienteService clienteService) { 
	
	int id = Integer.parseInt(codigoCliente);
	
	Optional<ClienteEntity> clienteEntityOp = clienteService.FindById(id);
	
	if(!clienteEntityOp.isPresent()) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente não encontrado!");
	}

	ClienteEntity clienteEntity = clienteEntityOp.get();
	
	if(clienteEntity.isLogado()==false) {
		throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Usuário não logado.");
	}
		
	int idPedido = clienteEntity.getPedido();
	
	if(idPedido == 0 ) {
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O cliente não tem nenhum pedido atual!");
	}
	
	return idPedido;
	
	}
	
}
