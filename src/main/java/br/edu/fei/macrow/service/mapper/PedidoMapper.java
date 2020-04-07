package br.edu.fei.macrow.service.mapper;

import br.edu.fei.macrow.entities.PedidoEntity;
import br.edu.fei.macrow.service.model.Pedido;
import br.edu.fei.macrow.service.model.Produto;
import br.edu.fei.macrow.services.PedidoService;
import br.edu.fei.macrow.services.ProdutoService;

public class PedidoMapper {
	
	public static Integer findId(PedidoService service) {
		for(int i =1;i<101;i++) {
			if(!service.FindById(i).isPresent()) {
				return i;
			}
		}
		return null;
	}
	
	public static Pedido converterPedido(int id,String entrada,String status, int idCliente) {
		
		String[] pedidosString = entrada.split("_");
		
		Produto[] produtos = new Produto[pedidosString.length];
		
		for(int i = 0;i<pedidosString.length;i++) {
			String[] idSeparados = pedidosString[i].split("@");
			produtos[i] = new Produto(Integer.parseInt(idSeparados[0]),Integer.parseInt(idSeparados[1]));
		}
		
		
		return new Pedido(id,produtos, status, idCliente);
		
	}
	
	public static Pedido converterPedido(PedidoEntity entity, ProdutoService service) {
		
		String[] pedidosString = entity.getCodigosRecebidos().split("_");
		Produto[] produtos = new Produto[pedidosString.length];
		
		for(int i = 0;i<pedidosString.length;i++) {
			String[] idSeparados = pedidosString[i].split("@");
			int id = Integer.parseInt(idSeparados[0]);
			produtos[i] = new Produto( id,service.FindById(id).get().getNome(),Integer.parseInt(idSeparados[1]));
		}
		
		return new Pedido(entity.getId(),produtos, entity.getStatus(), entity.getIdCliente());	
	}
	
	public static int contarProdutos(PedidoEntity entity) {
	
		String[] pedidosString = entity.getCodigosRecebidos().split("_");
		
		return pedidosString.length;
	}
	
}
