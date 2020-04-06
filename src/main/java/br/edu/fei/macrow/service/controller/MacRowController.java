package br.edu.fei.macrow.service.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.fei.macrow.entities.ClienteEntity;
import br.edu.fei.macrow.entities.PedidoEntity;
import br.edu.fei.macrow.service.ValidarEntrada;
import br.edu.fei.macrow.service.mapper.ClienteMapper;
import br.edu.fei.macrow.service.mapper.PedidoMapper;
import br.edu.fei.macrow.service.model.Greeting;
import br.edu.fei.macrow.service.model.Pedido;
import br.edu.fei.macrow.services.ClienteService;
import br.edu.fei.macrow.services.PedidoService;
import br.edu.fei.macrow.services.ProdutoService;


@RestController
public class MacRowController {
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
	
	@RequestMapping(value="/pedido/{CodigosRecebidos}/{CodigoCliente}", method= RequestMethod.POST)
	public ResponseEntity<Integer> pedido(@PathVariable("CodigosRecebidos") String codigosRecebidos, 
										  @PathVariable("CodigoCliente") String codigoCliente) {
		
		ValidarEntrada.validarEntradaPedido(codigosRecebidos, codigoCliente, clienteService);
		
		
		
		pedidoService.salvar(new PedidoEntity(codigosRecebidos, "Adicionado",Integer.parseInt(codigoCliente)));
		int id = (int)pedidoService.quantidade();
		
		return ResponseEntity.ok().body(id);
	}
	
	@GetMapping("/pedidos")
	public ResponseEntity<List<Pedido>> pedidos() {
		
		List<PedidoEntity> entities = pedidoService.getPedidos();
		
		List<Pedido> pedidos = new ArrayList<>();
		
		for(int i = 0;i<entities.size();i++) {
			pedidos.add(PedidoMapper.converterPedido(entities.get(i), produtoService));
		}
		
		
		return ResponseEntity.ok().body(pedidos);
	}
	
	@RequestMapping(value="/cliente/cadastro/{CodigosRecebidos}", method= RequestMethod.POST)
	public ResponseEntity<Integer> cadastroCliente(@PathVariable("CodigosRecebidos") String codigosRecebidos) {
		
		ValidarEntrada.validarEntradaCadastroCliente(codigosRecebidos);
		
		ClienteEntity entity = ClienteMapper.converterEntradaPCliente(codigosRecebidos, clienteService);
		
		clienteService.salvar(entity);
				
		return ResponseEntity.ok().body(entity.getId());
	}
	
	@RequestMapping(value="/cliente/login/{CodigosRecebidos}", method= RequestMethod.POST)
	public ResponseEntity<Integer> loginCliente(@PathVariable("CodigosRecebidos") String codigosRecebidos) {
		
		ValidarEntrada.validarEntradaLoginCliente(codigosRecebidos);
		
		int id = ClienteMapper.checaLogin(codigosRecebidos, clienteService);
						
		return ResponseEntity.ok().body(id);
	}
	
}
