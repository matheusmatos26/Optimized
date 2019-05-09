package br.pro.optimized.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@SuppressWarnings("serial")

@Entity
@Table(name = "tb_consulta")

public class Consulta extends GenericDomain {
	
	@ManyToOne
	@JoinColumn (nullable =false)
	private Usuarios paciente;
	
	@ManyToOne
	@JoinColumn (nullable =false)
	private Usuarios medico;
	
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	private Date data;
	
	
	@Column(nullable = false)
	private Boolean finalizado;
	
	
	@Column
	private String receita;
	
	@Column
	private String descricao;
	

		
	
	public String getReceita() {
		return receita;
	}

	public void setReceita(String receita) {
		this.receita = receita;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Usuarios getPaciente() {
		return paciente;
	}

	public void setPaciente(Usuarios paciente) {
		this.paciente = paciente;
	}

	public Usuarios getMedico() {
		return medico;
	}

	public void setMedico(Usuarios medico) {
		this.medico = medico;
	}


	public Boolean getFinalizado() {
		return finalizado;
	}

	public void setFinalizado(Boolean finalizado) {
		this.finalizado = finalizado;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}


	

}
