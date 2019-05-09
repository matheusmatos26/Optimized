package br.pro.optimized.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.pro.optimized.dao.EstadoDAO;
import br.pro.optimized.domain.Estado;

@ManagedBean
@ViewScoped

public class EstadoBean implements Serializable {

	private Estado estado;
	private List<Estado> estados;

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	@PostConstruct
	public void listar() {
		try {
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar("nome");

		} catch (RuntimeException erro) {

			Messages.addGlobalError("Ocorreu um erro ao tentar registrar");
			erro.printStackTrace();
		}
	}

	public void novo() {

		estado = new Estado();
	}

	public void salvar() {

		try {

			EstadoDAO estadoDAO = new EstadoDAO();
			estadoDAO.merge(estado);

			estado = new Estado();
			estados = estadoDAO.listar("nome");

			Messages.addGlobalInfo("Registrado com com sucesso");

		} catch (RuntimeException erro) {

			Messages.addGlobalError("Ocorreu um erro ao registrar");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");

			EstadoDAO estadoDAO = new EstadoDAO();
			estadoDAO.excluir(estado);

			estados = estadoDAO.listar("nome");

			Messages.addGlobalInfo("Registro excluído com sucesso");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao excluir o registro");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {

		estado = (Estado) evento.getComponent().getAttributes().get("estadoSelecionado");

		EstadoDAO estadoDAO = new EstadoDAO();
		estados = estadoDAO.listar("nome");

	}

}
