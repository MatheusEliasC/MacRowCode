package br.edu.fei.macrow.service.mapper;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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
	
	public static PedidoEntity encontrarEValidarPedidoEntityViaID(int idPedido, PedidoService pedidoService) {
		Optional<PedidoEntity> pedidoEntity = pedidoService.FindById(idPedido);
		
		if(!pedidoEntity.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido não encontrado!");
		}
		
		return pedidoEntity.get();
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
	
	public static int cancelarPedido(int id, PedidoService service) {
		
		PedidoEntity pedido = encontrarEValidarPedidoEntityViaID(id,service);

		if(pedido.getStatus().equalsIgnoreCase("Adicionado") == false) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "O pedido ja esta sendo processado, não é possível cancelá-lo!");
		}
		
		service.delete(pedido.getId());
				
		return pedido.getIdCliente();
	}
	
	public static void atualizarStatus(int id, String novoStatus, PedidoService service) {
		
		PedidoEntity pedido = encontrarEValidarPedidoEntityViaID(id,service);
		
		pedido.setStatus(novoStatus);
		service.delete(id);
		service.salvar(pedido);
	}
	
}
