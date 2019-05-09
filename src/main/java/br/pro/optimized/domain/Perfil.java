package br.pro.optimized.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@SuppressWarnings("serial")
@Entity(name = "tb_perfil")

public class Perfil extends GenericDomain {

	@Column(length = 32, nullable = false)
	private String descricao;

	@Column(length = 1, nullable = false)
	private Character abreviatura;

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Character getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(Character abreviatura) {
		this.abreviatura = abreviatura;
	}


			
}
