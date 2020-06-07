package br.edu.fei.macrow.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.edu.fei.macrow.entities.ProdutoEntity;
import br.edu.fei.macrow.service.ValidarEntrada;
import br.edu.fei.macrow.service.mapper.PedidoMapper;
import br.edu.fei.macrow.services.FuncionarioService;
import br.edu.fei.macrow.services.PedidoService;
import br.edu.fei.macrow.services.ProdutoService;

@RestController
public class FuncionarioController {

	@Autowired
	private PedidoService pedidoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private FuncionarioService funcionarioService;
		
	/**
	 * Rota para realizar a atualização do status do pedido, utilizavel pelo funcionario responsavel
	 * 
	 * @param codigosRecebidos, novo status para substituir o anterior
	 * @param codigoPedido, id único do pedido ativo
	 * @author Matheus Elias Cruz
	 */
	@RequestMapping(value="/funcionario/pedido/{CodigoFuncionario}/{CodigoPedido}/{CodigosRecebidos}", method= RequestMethod.POST)
	public ResponseEntity<String> atualizarStatusPedido(@PathVariable("CodigoFuncionario") String codigoFunc,
														@PathVariable("CodigoPedido") String codigoPedido,
														 @PathVariable("CodigosRecebidos") String codigosRecebidos) {
		
		ValidarEntrada.validarCodigoFuncionario(codigoFunc, funcionarioService);
		ValidarEntrada.validarEntradaStatusPedido(codigosRecebidos);
		ValidarEntrada.validarCodigoPedido(codigoPedido, pedidoService);
		
		PedidoMapper.atualizarStatus(Integer.parseInt(codigoPedido), codigosRecebidos, pedidoService);
				
		return ResponseEntity.ok().body(null);
	}
	
	@RequestMapping(value="/funcionario/{CodigoFuncionario}/produtos", method= RequestMethod.POST)
	public ResponseEntity<List<ProdutoEntity>> listarProdutos(@PathVariable("CodigoFuncionario") String codigoFunc) {
					
		ValidarEntrada.validarCodigoFuncionario(codigoFunc, funcionarioService);
		
		return ResponseEntity.ok().body(produtoService.getProdutos());
	}
	
}
