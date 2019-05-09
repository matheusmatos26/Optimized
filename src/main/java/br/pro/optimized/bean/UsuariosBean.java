package br.pro.optimized.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.pro.optimized.dao.CidadeDAO;
import br.pro.optimized.dao.EspecialidadeDAO;
import br.pro.optimized.dao.EstadoDAO;
import br.pro.optimized.dao.PerfilDAO;
import br.pro.optimized.dao.UsuariosDAO;
import br.pro.optimized.domain.Cidade;
import br.pro.optimized.domain.Especialidade;
import br.pro.optimized.domain.Estado;
import br.pro.optimized.domain.Perfil;
import br.pro.optimized.domain.Usuarios;

@ManagedBean
@ViewScoped
public class UsuariosBean implements Serializable {

	private Usuarios usuario;
	private List<Usuarios> usuarios;

	private Estado estado;
	private List<Estado> estados;

	private List<Cidade> cidades;
	
	private List<Perfil> perfis;
	
	private List<Especialidade> especialidades;	



	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	public List<Usuarios> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuarios> usuarios) {
		this.usuarios = usuarios;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public List<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(List<Perfil> perfis) {
		this.perfis = perfis;
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
			UsuariosDAO usuariosDAO = new UsuariosDAO();
			usuarios = usuariosDAO.listar("nome");

		} catch (RuntimeException erro) {

			Messages.addGlobalError("Ocorreu um erro ao tentar registrar");
			erro.printStackTrace();
		}
	}

	public void novo() {

		try {
			usuario = new Usuarios();

			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar("nome");

			cidades = new ArrayList<>();
			
			PerfilDAO perfilDAO = new PerfilDAO();
			perfis = perfilDAO.listar("descricao");
			
			EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();
			especialidades = especialidadeDAO.listar("especialidade");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao iniciar o cadastro");

		}
	}

	public void salvar() {

		try {

			UsuariosDAO usuariosDAO = new UsuariosDAO();
			usuariosDAO.merge(usuario);
			usuario= new Usuarios();

			usuarios= usuariosDAO.listar("nome");

			estado = new Estado();
			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar("nome");

			cidades = new ArrayList<>();
			
			PerfilDAO perfilDAO = new PerfilDAO();
			perfis = perfilDAO.listar("descricao");
			
			EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();
			especialidades = especialidadeDAO.listar("especialidade");

			Messages.addGlobalInfo("Registrado com com sucesso");

		} catch (RuntimeException erro) {

			Messages.addGlobalError("Ocorreu um erro ao registrar");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			usuario  = (Usuarios) evento.getComponent().getAttributes().get("usuarioSelecionado");

			UsuariosDAO usuariosDAO = new UsuariosDAO();
			usuariosDAO.excluir(usuario);

			usuarios = usuariosDAO.listar("nome");

			Messages.addGlobalInfo("Registro excluído com sucesso");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao excluir o registro");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			
			usuario = (Usuarios) evento.getComponent().getAttributes().get("usuarioSelecionado");

			estado = usuario.getCidade().getEstado();

			EstadoDAO estadoDAO = new EstadoDAO();
			estados = estadoDAO.listar("nome");

			CidadeDAO cidadeDAO = new CidadeDAO();
		
			cidades = cidadeDAO.buscarPorEstado(estado.getCodigo());
			
			PerfilDAO perfilDAO = new PerfilDAO();
			perfis = perfilDAO.listar("descricao");
			
			EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();
			especialidades = especialidadeDAO.listar("especialidade");
			

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar selecionar uma pessoa");
		}
	}

	public void popular() {
		try {
			if (estado != null) {
				CidadeDAO cidadeDAO = new CidadeDAO();
				cidades = cidadeDAO.buscarPorEstado(estado.getCodigo());
			} else {
				cidades = new ArrayList<>();
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar as cidades");
			erro.printStackTrace();
		}
	}
}
