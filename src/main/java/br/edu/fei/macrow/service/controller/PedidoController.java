package br.edu.fei.macrow.service.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.edu.fei.macrow.entities.PedidoEntity;
import br.edu.fei.macrow.service.ValidarEntrada;
import br.edu.fei.macrow.service.mapper.ClienteMapper;
import br.edu.fei.macrow.service.mapper.PedidoMapper;
import br.edu.fei.macrow.service.model.Greeting;
import br.edu.fei.macrow.service.model.Pedido;
import br.edu.fei.macrow.services.ClienteService;
import br.edu.fei.macrow.services.PedidoService;
import br.edu.fei.macrow.services.ProdutoService;

/**
 * Classe que controla os entrypoints
 * 
 * @author Matheus Elias Cruz
 *
 */
@RestController
public class PedidoController {
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	/**
	 * Rota para teste.
	 * 
	 * @param name
	 * @return pagina 
	 * @author Matheus Elias Cruz
	 */
	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
	
	/**
	 * Rota para fazer um novo pedido
	 * 
	 * @param codigosRecebidos, formato codigoProduto1@QuantidadeProduto1_codigoProduto2@QuantidadeProduto2...
	 * @param codigoCliente, id único do cliente que é enviado quando é feito o login
	 * @return pedidoID, id do pedido gerado
	 * @author Matheus Elias Cruz
	 */
	@RequestMapping(value="/pedido/{CodigosRecebidos}/{CodigoCliente}", method= RequestMethod.POST)
	public ResponseEntity<Integer> novoPedido(@PathVariable("CodigosRecebidos") String codigosRecebidos, 
										  @PathVariable("CodigoCliente") String codigoCliente) {
		
		ValidarEntrada.validarEntradaPedido(codigosRecebidos, codigoCliente, clienteService);
		
		int idCliente = Integer.parseInt(codigoCliente);
		
		PedidoEntity novoPedido = new PedidoEntity(PedidoMapper.findId(pedidoService),codigosRecebidos, "Adicionado",idCliente);
				
		ClienteMapper.verificarEAtualizarPedidoAtual(novoPedido.getId(),novoPedido.getIdCliente(), clienteService);
		
		pedidoService.salvar(novoPedido);
		
		return ResponseEntity.ok().body(novoPedido.getId());
	}
	
	/**
	 * Rota para listar os pedidos
	 * 
	 * @return lista de todos os pedidos feitos
	 * @author Matheus Elias Cruz
	 */
	@GetMapping("/pedidos")
	public ResponseEntity<List<Pedido>> pedidos() {
		
		List<PedidoEntity> entities = pedidoService.getPedidos();
		
		List<Pedido> pedidos = new ArrayList<>();
		
		for(int i = 0;i<entities.size();i++) {
			pedidos.add(PedidoMapper.converterPedido(entities.get(i), produtoService));
		}
		
		
		return ResponseEntity.ok().body(pedidos);
	}
	
	/**
	 * Rota para filtrar a lista de pedidos e mostrar somente o pedido de id escolhido
	 * 
	 * @param codigosRecebidos, id do pedido buscado para ser usado como filtro
	 * @return lista de todos os pedidos feitos
	 * @author Matheus Elias Cruz
	 */
	@RequestMapping(value="/pedidos/{CodigosRecebidos}", method= RequestMethod.POST)
	public ResponseEntity<Pedido> filtrarPedidos(@PathVariable("CodigosRecebidos") String codigosRecebidos) {
			
		ValidarEntrada.validarCodigoPedido(codigosRecebidos, pedidoService);
		
		int id = Integer.parseInt(codigosRecebidos);
		
		Optional<PedidoEntity> pedidoEntity = pedidoService.FindById(id);
		
		if(!pedidoEntity.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido não encontrado!");
		}
		
		Pedido pedido = PedidoMapper.converterPedido(pedidoEntity.get(), produtoService);
		
		return ResponseEntity.ok().body(pedido);
	}
	
	/**
	 * rota para cancelar pedido, somente funcionará se o pedido estiver com o status inicial 'Adicionado'
	 * 
	 * @param codigosRecebidos, id do pedido que deseja ser cancelado
	 * @return
	 */
	@RequestMapping(value="/pedidos/cancelar/{CodigosRecebidos}", method= RequestMethod.POST)
	public ResponseEntity<String> cancelarPedido(@PathVariable("CodigosRecebidos") String codigosRecebidos) {
		
		ValidarEntrada.validarCodigoPedido(codigosRecebidos, pedidoService);
		
		int idCliente = PedidoMapper.cancelarPedido(Integer.parseInt(codigosRecebidos), pedidoService);
		
		ClienteMapper.atualizarPedidoAtual(0, idCliente, clienteService);
		
		return ResponseEntity.ok().body("Pedido Removido!");
	}
	
	
	
}
