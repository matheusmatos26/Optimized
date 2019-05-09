package br.pro.optimized.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity(name="tb_leito")
public class Leito extends GenericDomain {
	
	@Column( length = 200, nullable = false)
	private String numero;
	
	@Column( length = 200, nullable = false)
	private String descricao;

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	

}
