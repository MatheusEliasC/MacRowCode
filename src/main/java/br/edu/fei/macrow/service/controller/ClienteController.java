package br.edu.fei.macrow.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.edu.fei.macrow.entities.ClienteEntity;
import br.edu.fei.macrow.entities.PedidoEntity;
import br.edu.fei.macrow.service.MailService;
import br.edu.fei.macrow.service.ValidarEntrada;
import br.edu.fei.macrow.service.mapper.ClienteMapper;
import br.edu.fei.macrow.service.mapper.PedidoMapper;
import br.edu.fei.macrow.service.model.Pedido;
import br.edu.fei.macrow.services.ClienteService;
import br.edu.fei.macrow.services.PedidoService;
import br.edu.fei.macrow.services.ProdutoService;

@RestController
public class ClienteController {

	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private MailService mail;
		
	/**
	 * Rota para realizar o cadastro do novo usuario
	 * 
	 * @param codigosRecebidos, dados recebidos no formato nome@login@senha@email@dominio@celular
	 * @return id gerado para o novo cliente
	 * @author Matheus Elias Cruz
	 */
	@RequestMapping(value="/cliente/cadastro/{CodigosRecebidos}", method= RequestMethod.POST)
	public ResponseEntity<Integer> cadastroCliente(@PathVariable("CodigosRecebidos") String codigosRecebidos) {
		
		ValidarEntrada.validarEntradaCadastroCliente(codigosRecebidos);
		
		ClienteEntity entity = ClienteMapper.converterEntradaPCliente(codigosRecebidos, clienteService);
						
		String confirmUrl = "http://localhost:3306/cliente/confirmar/"+entity.getId();
		
		mail.sendConfirmationEmail(entity.getEmail(),entity.getDominio(),confirmUrl);
		
		clienteService.salvar(entity);
		
		return ResponseEntity.ok().body(entity.getId());
	}
	
	/**
	 * Rota para realizar o login do usuário. O login só será realizado caso o usuário tenha o email verificado.
	 * 
	 * @param codigosRecebidos, dados recebidos no formato login@senha
	 * @return id do usuário logado
	 * @author Matheus Elias Cruz
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
	 * @author Matheus Elias Cruz
	 */
	@RequestMapping(value="/cliente/confirmar/{CodigosRecebidos}", method= RequestMethod.GET)
	public ResponseEntity<String> confirmarEmail(@PathVariable("CodigosRecebidos") String codigoCliente) {
		
		ValidarEntrada.validarCodigoCliente(codigoCliente, clienteService);
		
		ClienteMapper.confirmVerif(Integer.parseInt(codigoCliente),clienteService);
		
		return ResponseEntity.ok().body("Conta verificada!");
	}
	
	/**
	 * Rota para verificar qual o pedido atual linkado com o cliente
	 * 
	 * @param codigoCliente, recebe o id único do cliente.
	 * @return Pedido encontrado no banco, se houver.
	 * @author Matheus Elias Cruz
	 */
	@RequestMapping(value="/cliente/pedido/{CodigosRecebidos}", method= RequestMethod.POST)
	public ResponseEntity<Pedido> verificarPedidoAtual(@PathVariable("CodigosRecebidos") String codigoCliente) {
		
		ValidarEntrada.validarCodigoCliente(codigoCliente, clienteService);
		
		int idPedido = ClienteMapper.retornaIddoPedidoAtual(codigoCliente, clienteService);
		
		PedidoEntity pedidoEntity = PedidoMapper.encontrarEValidarPedidoEntityViaID(idPedido,pedidoService);
		
		Pedido pedido = PedidoMapper.converterPedido(pedidoEntity, produtoService);
		
		return ResponseEntity.ok().body(pedido);
		
	}
	
}
