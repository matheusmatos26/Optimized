package br.pro.optimized.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.pro.optimized.dao.LeitoDAO;
import br.pro.optimized.domain.Leito;

@ManagedBean
@ViewScoped

public class LeitoBean implements Serializable {

	private Leito leito;
	private List<Leito> leitos;

	
	
	public Leito getLeito() {
		return leito;
	}

	public void setLeito(Leito leito) {
		this.leito = leito;
	}

	public List<Leito> getLeitos() {
		return leitos;
	}

	public void setLeitos(List<Leito> leitos) {
		this.leitos = leitos;
	}

	@PostConstruct
	public void listar() {
		try {
			LeitoDAO leitoDAO = new LeitoDAO();
			leitos = leitoDAO.listar("descricao");

		} catch (RuntimeException erro) {

			Messages.addGlobalError("Ocorreu um erro ao tentar registrar");
			erro.printStackTrace();
		}
	}

	public void novo() {

		leito = new Leito();
	}

	public void salvar() {

		try {

			LeitoDAO leitoDAO = new LeitoDAO();
			leitoDAO.merge(leito);

			leito = new Leito();
			leitos = leitoDAO.listar("descricao");

			Messages.addGlobalInfo("Registrado com com sucesso");

		} catch (RuntimeException erro) {

			Messages.addGlobalError("Ocorreu um erro ao registrar");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			leito = (Leito) evento.getComponent().getAttributes().get("leitoSelecionado");

			LeitoDAO leitoDAO = new LeitoDAO();
			leitoDAO.excluir(leito);

			leitos = leitoDAO.listar("descricao");

			Messages.addGlobalInfo("Registro excluído com sucesso");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao excluir o registro");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {

		leito = (Leito) evento.getComponent().getAttributes().get("leitoSelecionado");

		LeitoDAO leitoDAO = new LeitoDAO();
		leitos = leitoDAO.listar("descricao");

	}

}
