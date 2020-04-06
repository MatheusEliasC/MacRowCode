package br.edu.fei.macrow.service.model;

public class Pedido {

	private Integer id;
	
	private Produto[] produtos;
	
	private String status;

	private Integer idCliente;
	
	public Pedido(int id,Produto[] produtos, String status, int idCliente) {
		super();
		this.id = id;
		this.produtos = produtos;
		this.status = status;
		this.idCliente = idCliente;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Produto[] getProdutos() {
		return produtos;
	}

	public void setProdutos(Produto[] produtos) {
		this.produtos = produtos;
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
