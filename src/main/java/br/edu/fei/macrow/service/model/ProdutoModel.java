package br.edu.fei.macrow.service.model;

public class ProdutoModel {

	private int idProduto;
	private String nome;
	private int quantidade;
	
	public ProdutoModel(Integer idProduto, Integer quantidade) {
		super();
		this.idProduto = idProduto;
		this.quantidade = quantidade;
	}

	public Integer getIdProduto() {
		return idProduto;
	}

	public String getNome() {
		return nome;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
}
