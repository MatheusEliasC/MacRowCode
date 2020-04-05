package br.edu.fei.macrow.service.mapper;

import br.edu.fei.macrow.service.model.Pedido;
import br.edu.fei.macrow.service.model.ProdutoModel;

public class PedidoMapper {
	
	public static Pedido converterPedido(int id,String entrada,String status) {
		
		String[] pedidosString = entrada.split("_");
		
		ProdutoModel[] produtos = new ProdutoModel[pedidosString.length];
		
		for(int i = 0;i<pedidosString.length;i++) {
			String[] idSeparados = pedidosString[i].split("@");
			produtos[i] = new ProdutoModel(Integer.parseInt(idSeparados[0]),Integer.parseInt(idSeparados[1]));
		}
		
		
		return new Pedido(id,produtos, status);
		
	}
	
}
