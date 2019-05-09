package br.pro.optimized.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.pro.optimized.dao.PerfilDAO;
import br.pro.optimized.domain.Perfil;

@ManagedBean
@ViewScoped
public class PerfilBean implements Serializable {

	private Perfil perfil;
	private List<Perfil> perfis;

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public List<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
	}

	@PostConstruct
	public void listar() {
		try {
			PerfilDAO perfilDAO = new PerfilDAO();
			perfis = perfilDAO.listar("descricao");

		} catch (RuntimeException erro) {

			Messages.addGlobalError("Ocorreu um erro ao tentar registrar");
			erro.printStackTrace();
		}
	}

	public void novo() {

		try {
			perfil = new Perfil();

			PerfilDAO perfilDAO = new PerfilDAO();
			perfis= perfilDAO.listar("descricao");

			
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao iniciar o cadastro");

		}
	}

	public void salvar() {

		try {

			PerfilDAO perfilDAO = new PerfilDAO();
			perfilDAO.merge(perfil);

			perfil = new Perfil();

			perfis = perfilDAO.listar("descricao");

			Messages.addGlobalInfo("Registrado com com sucesso");

		} catch (RuntimeException erro) {

			Messages.addGlobalError("Ocorreu um erro ao registrar");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			perfil  = (Perfil) evento.getComponent().getAttributes().get("perfilSelecionado");

			PerfilDAO perfilDAO = new PerfilDAO();
			perfilDAO.excluir(perfil);

			perfis = perfilDAO.listar("descricao");

			Messages.addGlobalInfo("Registro excluído com sucesso");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao excluir o registro");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {

		try {

			perfil = (Perfil) evento.getComponent().getAttributes().get("perfilSelecionado");

			PerfilDAO perfilDAO = new PerfilDAO();

			perfis= perfilDAO.listar("descricao");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao registrar");

		}

	}

}
