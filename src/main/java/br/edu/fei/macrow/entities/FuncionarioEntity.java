package br.edu.fei.macrow.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name = "funcionarios")
public class FuncionarioEntity {

	@Id
	private Integer id;
	
	private String nome;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public FuncionarioEntity(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	
	
}