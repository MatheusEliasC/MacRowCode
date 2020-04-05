package br.edu.fei.macrow.service.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.fei.macrow.database.entities.PedidoEntity;
import br.edu.fei.macrow.database.repository.PedidosRepository;
import br.edu.fei.macrow.service.ValidarEntrada;
//import br.edu.fei.macrow.service.mapper.PedidoMapper;
import br.edu.fei.macrow.service.model.Greeting;
//import br.edu.fei.macrow.service.model.Pedido;

@RestController
public class MacRowController {
	
	@Autowired
	private PedidosRepository pedidosRepository;
	
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}
	
	@RequestMapping(value="/pedido/{CodigosRecebidos}", method= RequestMethod.GET)
	public ResponseEntity<Integer> pedido(@PathVariable("CodigosRecebidos") String codigosRecebidos) {
		
		ValidarEntrada.validarEntrada(codigosRecebidos);
		
		pedidosRepository.save(new PedidoEntity(codigosRecebidos, "Adicionado"));
		int id = (int)pedidosRepository.count();
//		Pedido pedidoNovo = PedidoMapper.converterPedido(id-1,codigosRecebidos,"Adicionado");
		
		return ResponseEntity.ok().body(id);
	}
}
