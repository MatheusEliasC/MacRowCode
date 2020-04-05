package br.edu.fei.macrow.service.model;

public class Pedido {
	
	public Pedido(int id, ProdutoModel[] pedidos, String status) {
		super();
		this.id =id;
		this.pedidos = pedidos;
		this.status = status;
	}

	private int id;
	
	private ProdutoModel[] pedidos;
	
	private String status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ProdutoModel[] getPedidos() {
		return pedidos;
	}

	public void setPedidos(ProdutoModel[] pedidos) {
		this.pedidos = pedidos;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	
}
