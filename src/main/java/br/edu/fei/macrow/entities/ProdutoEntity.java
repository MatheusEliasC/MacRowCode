package br.edu.fei.macrow.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "produtos")
public class ProdutoEntity {

	@Id
	private int idProduto;
	private String nome;
	
	public ProdutoEntity(Integer idProduto, String nome) {
		super();
		this.idProduto = idProduto;
		this.nome = nome;
	}
	
	public ProdutoEntity() {
		super();
	}

	public Integer getIdProduto() {
		return idProduto;
	}

	public String getNome() {
		return nome;
	}

	public void setIdProduto(int idProduto) {
		this.idProduto = idProduto;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
