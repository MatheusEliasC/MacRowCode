package br.edu.fei.macrow.database.entities;

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

	public PedidoEntity(String codigosRecebidos, String status) {
		super();
		CodigosRecebidos = codigosRecebidos;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	
	
}
