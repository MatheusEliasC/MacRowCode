package br.edu.fei.macrow.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "pedidos")
public class PedidoEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String CodigosRecebidos;
	
	private String status;
	
	private Integer idCliente;

	public PedidoEntity(String codigosRecebidos, String status, Integer idCliente) {
		super();
		CodigosRecebidos = codigosRecebidos;
		this.status = status;
		this.idCliente = idCliente;
	}
	
	public PedidoEntity() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public String getCodigosRecebidos() {
		return CodigosRecebidos;
	}

	public void setCodigosRecebidos(String codigosRecebidos) {
		CodigosRecebidos = codigosRecebidos;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	
}
