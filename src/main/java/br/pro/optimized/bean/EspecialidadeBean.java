package br.pro.optimized.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.pro.optimized.dao.EspecialidadeDAO;
import br.pro.optimized.domain.Especialidade;

@ManagedBean
@ViewScoped

public class EspecialidadeBean implements Serializable {

	private Especialidade especialidade;
	private List<Especialidade> especialidades;

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}

	public List<Especialidade> getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

	@PostConstruct
	public void listar() {
		try {

			EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();
			especialidades = especialidadeDAO.listar("especialidade");

		} catch (RuntimeException erro) {

			Messages.addGlobalError("Ocorreu um erro ao tentar registrar");
			erro.printStackTrace();
		}
	}

	public void novo() {

		especialidade = new Especialidade();
	}

	public void salvar() {

		try {

			EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();
			especialidadeDAO.merge(especialidade);

			especialidade = new Especialidade();
			especialidades = especialidadeDAO.listar("especialidade");

			Messages.addGlobalInfo("Registrado com com sucesso");

		} catch (RuntimeException erro) {

			Messages.addGlobalError("Ocorreu um erro ao registrar");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			especialidade = (Especialidade) evento.getComponent().getAttributes().get("especialidadeSelecionada");

			EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();
			especialidadeDAO.excluir(especialidade);

			especialidades = especialidadeDAO.listar("especialidade");

			Messages.addGlobalInfo("Registro excluído com sucesso");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao excluir o registro");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {

		especialidade = (Especialidade) evento.getComponent().getAttributes().get("especialidadeSelecionada");

		EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();
		especialidades = especialidadeDAO.listar("especialidade");

	}

}
