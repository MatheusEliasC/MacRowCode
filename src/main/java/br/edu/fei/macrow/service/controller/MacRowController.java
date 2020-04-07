package br.edu.fei.macrow.service.controller;

import java.util.ArrayList;
import java.util.List;
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

import br.edu.fei.macrow.entities.ClienteEntity;
import br.edu.fei.macrow.entities.PedidoEntity;
import br.edu.fei.macrow.service.MailService;
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
public class MacRowController {
	
	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private MailService mail;
	
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	/**
	 * Rota para teste.
	 * 
	 * @param name
	 * @return pagina 
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
	 */
	@RequestMapping(value="/pedido/{CodigosRecebidos}/{CodigoCliente}", method= RequestMethod.POST)
	public ResponseEntity<Integer> novoPedido(@PathVariable("CodigosRecebidos") String codigosRecebidos, 
										  @PathVariable("CodigoCliente") String codigoCliente) {
		
		ValidarEntrada.validarEntradaPedido(codigosRecebidos, codigoCliente, clienteService);
		
		int idCliente = Integer.parseInt(codigoCliente);
		
		PedidoEntity novoPedido = new PedidoEntity(PedidoMapper.findId(pedidoService),codigosRecebidos, "Adicionado",idCliente);
				
		ClienteMapper.atualizarPedidoAtual(novoPedido.getId(), clienteService);
		
		pedidoService.salvar(novoPedido);
		
		return ResponseEntity.ok().body(novoPedido.getId());
	}
	
	/**
	 * Rota para listar os pedidos
	 * @return lista de todos os pedidos feitos
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
	 * @return lista de todos os pedidos feitos
	 */
	@RequestMapping(value="/pedidos/{CodigosRecebidos}", method= RequestMethod.POST)
	public ResponseEntity<Pedido> filtrarPedidos(@PathVariable("CodigosRecebidos") String codigosRecebidos) {
			
		ValidarEntrada.validarCodigoPedido(codigosRecebidos, pedidoService);
		
		int id = Integer.parseInt(codigosRecebidos);
		
		List<PedidoEntity> entities = pedidoService.getPedidos();
		
		List<Pedido> pedidos = new ArrayList<>();
		
		for(int i = 0;i<entities.size();i++) {
			pedidos.add(PedidoMapper.converterPedido(entities.get(i), produtoService));
		}
		
		Pedido filtro = null;
						
		for(int j=0;j<pedidos.size();j++) {
			if(pedidos.get(j).getId() == id) {
				filtro = pedidos.get(j);
			}
		}
		
		if(filtro == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Pedido não encontrado!");
		}
		
		
		return ResponseEntity.ok().body(filtro);
	}
	
	/**
	 * Rota para realizar o cadastro do novo usuario
	 * 
	 * @param codigosRecebidos, dados recebidos no formato nome@login@senha@email@dominio@celular
	 * @return id gerado para o novo cliente
	 */
	@RequestMapping(value="/cliente/cadastro/{CodigosRecebidos}", method= RequestMethod.POST)
	public ResponseEntity<Integer> cadastroCliente(@PathVariable("CodigosRecebidos") String codigosRecebidos) {
		
		ValidarEntrada.validarEntradaCadastroCliente(codigosRecebidos);
		
		ClienteEntity entity = ClienteMapper.converterEntradaPCliente(codigosRecebidos, clienteService);
				
		clienteService.salvar(entity);
		
		String confirmUrl = "http://localhost:8080/cliente/confirmar/"+entity.getId();
		
		mail.sendConfirmationEmail(entity.getEmail(),entity.getDominio(),confirmUrl);
		
		return ResponseEntity.ok().body(entity.getId());
	}
	
	/**
	 * Rota para realizar o login do usuário. O login só será realizado caso o usuário tenha o email verificado.
	 * 
	 * @param codigosRecebidos, dados recebidos no formato login@senha
	 * @return id do usuário logado
	 */
	@RequestMapping(value="/cliente/login/{CodigosRecebidos}", method= RequestMethod.POST)
	public ResponseEntity<Integer> loginCliente(@PathVariable("CodigosRecebidos") String codigosRecebidos) {
		
		ValidarEntrada.validarEntradaLoginCliente(codigosRecebidos);
		
		int id = ClienteMapper.checaLogin(codigosRecebidos, clienteService);
						
		return ResponseEntity.ok().body(id);
	}
	
	/**
	 * Rota para confirmar o email do usuario, enviada ao email cadastrado assim que o usuário for cadastrado no sistema.
	 * 
	 * @param codigoCliente, id único do cliente gerado no cadastro
	 * @return Notificação de conta verificada.
	 */
	@RequestMapping(value="/cliente/confirmar/{CodigosRecebidos}", method= RequestMethod.GET)
	public ResponseEntity<String> confirmarEmail(@PathVariable("CodigosRecebidos") String codigoCliente) {
		
		ValidarEntrada.validarCodigoCliente(codigoCliente, clienteService);
		
		ClienteMapper.confirmVerif(Integer.parseInt(codigoCliente),clienteService);
		
		return ResponseEntity.ok().body("Conta verificada!");
	}
	
	/**
	 * 
	 * 
	 * @param codigoCliente
	 * @return
	 */
//	@RequestMapping(value="/cliente/pedido/{CodigosRecebidos}", method= RequestMethod.GET)
//	public ResponseEntity<String> verificarPedidoAtual(@PathVariable("CodigosRecebidos") String codigoCliente) {
//		
//		ValidarEntrada.validarCodigoCliente(codigoCliente, clienteService);
//		
//		ClienteMapper.confirmVerif(Integer.parseInt(codigoCliente),clienteService);
//		
//		return ResponseEntity.ok().body("Conta verificada!");
//	}
	
}
