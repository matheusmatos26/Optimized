package br.pro.optimized.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")

@Entity
@Table(name = "tb_especialidade")

public class Especialidade extends GenericDomain {

	@Column(length = 50, nullable = false)
	private String especialidade;

	public String getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(String especialidade) {
		this.especialidade = especialidade;
	}

}
