package br.edu.fei.macrow.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ValidarEntrada {
	
	public static void validarEntrada(String entrada) {
		
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
			}
			if(pedidosSeparados[1]==null || pedidosSeparados[1].equals("")) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantidade do produto " + i+1 + " não pode ser vazio!");
			}
		}
		
	}
	
}
