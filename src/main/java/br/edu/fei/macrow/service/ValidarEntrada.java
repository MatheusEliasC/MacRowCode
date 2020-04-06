package br.edu.fei.macrow.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import br.edu.fei.macrow.entities.ClienteEntity;
import br.edu.fei.macrow.services.ClienteService;

public class ValidarEntrada {
	
	public static void validarEntradaPedido(String entrada, String codigo, ClienteService service) {
		
		String[] pedidosString = entrada.split("_");
		
		for(int j= 0;j<pedidosString.length;j++) {
			if(pedidosString[j]==null || pedidosString[j].equals("")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Produto " + j+1 + " não pode ser vazio!");
			}
		}
		
		for(int i = 0;i<pedidosString.length;i++) {
			String[] pedidosSeparados = pedidosString[i].split("@");
			if(pedidosSeparados[0]==null || pedidosSeparados[0].equals("")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do produto " + i+1 + " não pode ser vazio!");
			}else if(Integer.parseInt(pedidosSeparados[0])<=0) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do produto " + i+1 + " só pode ser número inteiro, diferente de zero e positivo!");
			}
			if(pedidosSeparados[1]==null || pedidosSeparados[1].equals("")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantidade do produto " + i+1 + " não pode ser vazio!");
			}else if(Integer.parseInt(pedidosSeparados[1])<=0) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantidade do produto " + i+1 + " só pode ser número inteiro, diferente de zero e positivo!");
			}
		}
		
		validarCodigoCliente(codigo, service);
		
	}
	
	public static void validarCodigoCliente(String codigo, ClienteService service) {
		Optional<ClienteEntity> teste = service.FindById(Integer.parseInt(codigo));
		if(codigo.equals(null)|| codigo == "") {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do cliente incorreto!");
		}

		if(!teste.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do cliente não encontrado no banco!");
		}
	}
		
	public static void validarEntradaCadastroCliente(String entrada) {
		
		String[] dadosString = entrada.split("@");
		
		for(int i = 0;i<dadosString.length;i++){
			if(dadosString[i]== null || dadosString[i].equals("")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum dado pode ser nulo ou vazio!");
			}
		}
		
		if(dadosString.length <3 || dadosString.length >=4) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "São necessários 3 dados para completar essa operação!");
		}
		
		String Nome = dadosString[0];
		String Login = dadosString[1];
		String Senha = dadosString[2];
		
		if(Nome.length()<3) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O nome deve conter no mínimo 3 caracteres!");
		}else if(Nome.length()>60) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O nome contém muitos caracteres!");
		}
		
		if(Login.length()<3) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O login deve conter no mínimo 3 caracteres!");
		}else if(Login.length()>10) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O login contém muitos caracteres! O limite é de 10.");
		}else if(Login.contains("[^a-zA-Z0-9]")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O login não pode contem nenhuma caratere especial assim como espaços!");
		}
		
		if(Senha.length()<6) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A senha deve conter no mínimo 6 caracteres!");
		}else if(Senha.length()>20) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A senha contém muitos caracteres!");
		}
//		else if(!Senha.contains("[^A-Z]")) {
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A senha deve conter pelo menos 1 letra maiúscula!");
//		}
		
	}
	
	
	public static void validarEntradaLoginCliente(String entrada) {
		
		String[] dadosString = entrada.split("@");
		
		for(int i = 0;i<dadosString.length;i++){
			if(dadosString[i]== null || dadosString[i].equals("")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum dado pode ser nulo ou vazio!");
			}
		}
		
		if(dadosString.length <2 || dadosString.length >=3) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "São necessários 2 dados para completar essa operação!");
		}
		
		String Login = dadosString[0];
		String Senha = dadosString[1];
				
		if(Login.length()<3) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O login deve conter no mínimo 3 caracteres!");
		}else if(Login.length()>10) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O login contém muitos caracteres! O limite é de 10.");
		}else if(Login.contains("[^a-zA-Z0-9]")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O login não pode contem nenhuma caratere especial assim como espaços!");
		}
		
		if(Senha.length()<6) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A senha deve conter no mínimo 6 caracteres!");
		}else if(Senha.length()>20) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A senha contém muitos caracteres!");
		}
//		else if(!Senha.contains("[^A-Z]")) {
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A senha deve conter pelo menos 1 letra maiúscula!");
//		}
		
	}
		
}
