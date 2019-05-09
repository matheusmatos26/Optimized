package br.pro.optimized.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.pro.optimized.dao.AcomodacaoDAO;

import br.pro.optimized.dao.LeitoDAO;
import br.pro.optimized.dao.UsuariosDAO;
import br.pro.optimized.domain.Acomodacao;
import br.pro.optimized.domain.Leito;
import br.pro.optimized.domain.Usuarios;

@ManagedBean
@ViewScoped
public class AcomodacaoBean implements Serializable {

	private Acomodacao acomodacao;
	private List<Acomodacao> acomodacoes;
	
	private List<Leito> leitos;
	private List<Usuarios> usuarios;
	
	
	public Acomodacao getAcomodacao() {
		return acomodacao;
	}

	public void setAcomodacao(Acomodacao acomodacao) {
		this.acomodacao = acomodacao;
	}

	public List<Acomodacao> getAcomodacoes() {
		return acomodacoes;
	}

	public void setAcomodacoes(List<Acomodacao> acomodacoes) {
		this.acomodacoes = acomodacoes;
	}

	public List<Leito> getLeitos() {
		return leitos;
	}

	public void setLeitos(List<Leito> leitos) {
		this.leitos = leitos;
	}



	public List<Usuarios> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuarios> usuarios) {
		this.usuarios = usuarios;
	}

	@PostConstruct
	public void listar() {
		try {
			AcomodacaoDAO acomodacaoDAO = new AcomodacaoDAO();
			acomodacoes= acomodacaoDAO.listar("dataInicio");

		} catch (RuntimeException erro) {

			Messages.addGlobalError("Ocorreu um erro ao tentar registrar");
			erro.printStackTrace();
		}
	}

	public void novo() {

		try {
			acomodacao = new Acomodacao();
			
			LeitoDAO leitoDAO = new LeitoDAO();
			leitos = leitoDAO.listar("descricao");
			
			UsuariosDAO usuarioDAO = new UsuariosDAO();
			usuarios = usuarioDAO.buscaUsuariosPorTipo('P');
			

			

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao iniciar o cadastro");

		}
	}

	public void salvar() {

		try {

			AcomodacaoDAO acomodacaoDAO = new AcomodacaoDAO();
			acomodacaoDAO.merge(acomodacao);

			acomodacao= new Acomodacao();

					
			LeitoDAO leitoDAO = new LeitoDAO();
			leitos = leitoDAO.listar("descricao");
			
			UsuariosDAO usuarioDAO = new UsuariosDAO();
			usuarios = usuarioDAO.buscaUsuariosPorTipo('P');
			
			acomodacoes = acomodacaoDAO.listar("dataInicio");

			Messages.addGlobalInfo("Registrado com com sucesso");

		} catch (RuntimeException erro) {

			Messages.addGlobalError("Ocorreu um erro ao registrar");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			acomodacao = (Acomodacao) evento.getComponent().getAttributes().get("acomodacaoSelecionado");

			AcomodacaoDAO acomodacaoDAO = new AcomodacaoDAO();
			acomodacaoDAO.excluir(acomodacao);

			acomodacoes= acomodacaoDAO.listar("dataInicio");

			Messages.addGlobalInfo("Registro excluído com sucesso");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao excluir o registro");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {

		try {

			acomodacao = (Acomodacao) evento.getComponent().getAttributes().get("acomodacaoSelecionado");

			
			
			LeitoDAO leitoDAO = new LeitoDAO();
			leitos = leitoDAO.listar("descricao");
			
			UsuariosDAO usuarioDAO = new UsuariosDAO();
			usuarios = usuarioDAO.buscaUsuariosPorTipo('P');
			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao registrar");

		}

	}

}
