package br.edu.fei.macrow.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import br.edu.fei.macrow.entities.ClienteEntity;
import br.edu.fei.macrow.entities.FuncionarioEntity;
import br.edu.fei.macrow.entities.PedidoEntity;
import br.edu.fei.macrow.services.ClienteService;
import br.edu.fei.macrow.services.FuncionarioService;
import br.edu.fei.macrow.services.PedidoService;

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
		if(codigo.equals(null)|| codigo == "") {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do cliente incorreto!");
		}
		
		Optional<ClienteEntity> teste = service.FindById(Integer.parseInt(codigo));
		
		if(!teste.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do cliente não encontrado no banco!");
		}
	}
	
	public static void validarCodigoPedido(String codigo, PedidoService service) {
		if(codigo.equals(null)|| codigo == "") {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do produto incorreto!");
		}
		
		Optional<PedidoEntity> teste = service.FindById(Integer.parseInt(codigo));
		
		if(!teste.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do produto não encontrado no banco!");
		}
	}
		
	public static void validarEntradaCadastroCliente(String entrada) {
		
		String[] dadosString = entrada.split("@");
		
		for(int i = 0;i<dadosString.length;i++){
			if(dadosString[i]== null || dadosString[i].equals("")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nenhum dado pode ser nulo ou vazio!");
			}
		}
		
		if(dadosString.length <6 || dadosString.length >=7) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "São necessários 6 dados separados por @ para completar essa operação!");
		}
		
		String Nome = dadosString[0];
		String Login = dadosString[1];
		String Senha = dadosString[2];
		String Email = dadosString[3];
		String Dominio = dadosString[4];
		String Celular = dadosString[5];
		
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
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O login não pode conter nenhum caratere especial assim como espaços!");
		}
		
		if(Senha.length()<6) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A senha deve conter no mínimo 6 caracteres!");
		}else if(Senha.length()>20) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A senha contém muitos caracteres!");
		}
//		else if(!Senha.contains("[^A-Z]")) {
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A senha deve conter pelo menos 1 letra maiúscula!");
//		}
		
		if(Email.length()<3) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O email deve conter no mínimo 3 caracteres! Fora o dominio ('dominio.com')");
		}else if(Login.length()>20) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O email contém muitos caracteres! O limite é de 10. Além do domínio ('dominio.com')");
		}else if(Login.contains("[^a-zA-Z0-9]")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O email não pode conter nenhum caratere especial assim como espaços!");
		}
		
		if(!Dominio.contains(".")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O dominio do email deve conter um ponto! Exemplo ('dominio.com')");
		}
		
		if(Celular.contains("[^0-9]")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O celular não pode ter carateres diferentes de números.");
		}else if(Celular.length()<11 || Celular.length()>=12) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O celular deve conter 11 números, sendo o DDD junto com o número.");
		}
		
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
	
	public static void validarEntradaStatusPedido(String entrada) {
		
		if(entrada.equals("") || entrada.equals(null)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O status não pode ser vazio!");
		}
		else if(!entrada.equals("Preparando")|| !entrada.equals("Pronto") || !entrada.equals("Finalizado")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Status inválido! Entradas possíveis: 'Preparando', 'Pronto' ou 'Finalizado'");
		}
		
	
	}
	
	public static void validarCodigoFuncionario(String codigo, FuncionarioService service) {
		
		if(codigo.equals(null)|| codigo == "") {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do funcionario incorreto!");
		}
		
		Optional<FuncionarioEntity> teste = service.FindById(Integer.parseInt(codigo));
		
		if(!teste.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID do funcionario não encontrado no banco!");
		}
		
	
	}
		
}
