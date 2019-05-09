package br.pro.optimized.bean;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.tools.FileObject;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.selectonemenu.SelectOneMenu;

import br.pro.optimized.dao.AcomodacaoDAO;
import br.pro.optimized.dao.CidadeDAO;
import br.pro.optimized.dao.ConsultaDAO;
import br.pro.optimized.dao.EspecialidadeDAO;
import br.pro.optimized.dao.LeitoDAO;

import br.pro.optimized.dao.UsuariosDAO;
import br.pro.optimized.domain.Acomodacao;
import br.pro.optimized.domain.Consulta;
import br.pro.optimized.domain.Especialidade;
import br.pro.optimized.domain.Leito;
import br.pro.optimized.domain.Usuarios;
import br.pro.optimized.util.AutenticacaoListener;
import br.pro.optimized.util.HibernateUtil;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.view.JasperViewer;


@ManagedBean
@ViewScoped
public class ConsultaBean implements Serializable {

	private Consulta consulta;
	private Consulta consultaPront;
	private List<Consulta> consultas, consultas2, prontuarios;

	private Character perfil;

	private AutenticacaoBean autenticacao;

	private Usuarios usuarioLogado, usuarioProntuario;


	private Especialidade especialidade;
	private List<Especialidade> especialidades;

	private Usuarios paciente;
	private List<Usuarios> pacientes;
	private List<Usuarios> medicos;

	public Consulta getConsultaPront() {
		return consultaPront;
	}

	public void setConsultaPront(Consulta consultaPront) {
		this.consultaPront = consultaPront;
	}

	public AutenticacaoBean getAutenticacao() {
		return autenticacao;
	}

	public void setAutenticacao(AutenticacaoBean autenticacao) {
		this.autenticacao = autenticacao;
	}

	public List<Consulta> getProntuarios() {
		return prontuarios;
	}

	public void setProntuarios(List<Consulta> prontuarios) {
		this.prontuarios = prontuarios;
	}

	public Usuarios getPaciente() {
		return paciente;
	}

	public void setPaciente(Usuarios paciente) {
		this.paciente = paciente;
	}

	public Usuarios getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuarios usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	

	public List<Consulta> getConsultas2() {
		return consultas2;
	}

	public void setConsultas2(List<Consulta> consultas2) {
		this.consultas2 = consultas2;
	}

	public Especialidade getEspecialidade() {
		return especialidade;
	}

	public void setEspecialidade(Especialidade especialidade) {
		this.especialidade = especialidade;
	}

	public Consulta getConsulta() {
		return consulta;
	}

	public void setConsulta(Consulta consulta) {
		this.consulta = consulta;
	}

	public List<Consulta> getConsultas() {
		return consultas;
	}

	public void setConsultas(List<Consulta> consultas) {
		this.consultas = consultas;
	}

	public List<Usuarios> getPacientes() {
		return pacientes;
	}

	public void setPacientes(List<Usuarios> pacientes) {
		this.pacientes = pacientes;
	}

	public List<Usuarios> getMedicos() {
		return medicos;
	}

	public void setMedicos(List<Usuarios> medicos) {
		this.medicos = medicos;
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
			
			ConsultaDAO consultaDAO = new ConsultaDAO();
			
			
			AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
			
			usuarioLogado = autenticacaoBean.getUsuarioLogado();
			
			perfil = usuarioLogado.getPerfil().getAbreviatura().charValue(); 
			
			if ( perfil == 'A'){
					
					consultas = consultaDAO.listar();
			}
					else {
			
						consultas = consultaDAO.listarConsultasAbertas(usuarioLogado.getCodigo());

			}
			

		} catch (RuntimeException erro) {

			Messages.addGlobalError("Ocorreu um erro ao tentar registrar");
			erro.printStackTrace();
		}
	}

	public void novo() {

		try {
			consulta = new Consulta();

			EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();
			especialidades = especialidadeDAO.listar("especialidade");

			UsuariosDAO pacienteDAO = new UsuariosDAO();
			pacientes = pacienteDAO.buscaUsuariosPorTipo('P');

			medicos = new ArrayList<>();

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao iniciar o cadastro");

		}
	}

	public void salvar() {

		try {
			
			AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");
			
			usuarioLogado = autenticacaoBean.getUsuarioLogado();
			
			perfil = usuarioLogado.getPerfil().getAbreviatura().charValue(); 
			
			if ( perfil == 'A'){
				
				
				ConsultaDAO consultaDAO = new ConsultaDAO();
				consultaDAO.merge(consulta);

				consulta = new Consulta();

				EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();
				especialidades = especialidadeDAO.listar("especialidade");

				UsuariosDAO pacienteDAO = new UsuariosDAO();
				pacientes = pacienteDAO.buscaUsuariosPorTipo('P');

				medicos = new ArrayList<>();
				
				consultas = consultaDAO.listar();	


					
					
			}
					else {
						
						
						
						ConsultaDAO consultaDAO = new ConsultaDAO();
						consultaDAO.merge(consulta);
						
						consultas = consultaDAO.listarConsultasAbertas(usuarioLogado.getCodigo());

						consulta = new Consulta();

						EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();
						especialidades = especialidadeDAO.listar("especialidade");

						UsuariosDAO pacienteDAO = new UsuariosDAO();
						pacientes = pacienteDAO.buscaUsuariosPorTipo('P');

						medicos = new ArrayList<>();
				

			}
			
			

			Messages.addGlobalInfo("Registrado com com sucesso");

		} catch (RuntimeException erro) {

			Messages.addGlobalError("Ocorreu um erro ao registrar");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			
			
			
			AutenticacaoBean autenticacaoBean = Faces.getSessionAttribute("autenticacaoBean");

			usuarioLogado = autenticacaoBean.getUsuarioLogado();

			perfil = usuarioLogado.getPerfil().getAbreviatura().charValue();

			if (perfil == 'A') {
				
				
				consulta = (Consulta) evento.getComponent().getAttributes().get("consultaSelecionado");

				ConsultaDAO consultaDAO = new ConsultaDAO();
				consultaDAO.excluir(consulta);
				
				consultas = consultaDAO.listar();

				
									
			}
					else {
						
						
						
						consulta = (Consulta) evento.getComponent().getAttributes().get("consultaSelecionado");

						ConsultaDAO consultaDAO = new ConsultaDAO();
						consultaDAO.excluir(consulta);
						
						usuarioLogado = (Usuarios) getAutenticacao().getUsuarioLogado();

						consultas = consultaDAO.listarConsultasAbertas(usuarioLogado.getCodigo());


			}
			
			
			

			
			Messages.addGlobalInfo("Registro excluído com sucesso");

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao excluir o registro");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {

		try {
			
			consulta = (Consulta) evento.getComponent().getAttributes().get("consultaSelecionado");

			especialidade = consulta.getMedico().getEspecialidade();

			EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();
			especialidades = especialidadeDAO.listar("especialidade");

			UsuariosDAO pacienteDAO = new UsuariosDAO();
			pacientes = pacienteDAO.buscaUsuariosPorTipo('P');

			UsuariosDAO medicoDAO = new UsuariosDAO();
			medicos = medicoDAO.buscaUsuariosPorEspecialidade(especialidade.getCodigo());
			
			ConsultaDAO consultaDAO = new ConsultaDAO();			
			consultas = consultaDAO.listar();


		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao registrar");

		}

	}

	public void prontuario(ActionEvent evento) {

		try {

			consulta = (Consulta) evento.getComponent().getAttributes().get("consultaSelecionado");

			paciente = consulta.getPaciente();

			ConsultaDAO consultaDAO = new ConsultaDAO();
			prontuarios = consultaDAO.listarPorPaciente(paciente.getCodigo());

	

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro no prontuario");

		}

	}
	
	public void imprimir() {
		
		System.out.println("TESTE");
		try {
		
			SelectOneMenu pacienteSelecionado  =  (SelectOneMenu) Faces.getViewRoot().findComponent("formListagem:pep");
			usuarioProntuario = (Usuarios) pacienteSelecionado.getValue();
			
			String codPaciente = Long.toString(usuarioProntuario.getCodigo());
			
			System.out.println(codPaciente);
			
		String caminho = Faces.getRealPath("/reports/prontuario.jasper");
			
		Map<String, Object> parametros = new HashMap<> ();
		parametros.put("PRONTUARIO_CODPACIENTE", codPaciente);
			
		Connection conexao = HibernateUtil.getConexao();
			
		JasperPrint relatorio =  JasperFillManager.fillReport(caminho, parametros, conexao);
	    JasperViewer.viewReport(relatorio, false);
	    
	   	    
	    
		} catch (JRException erro) {
		Messages.addGlobalError("Ocorreu um erro ao imprimir o relatório");
		erro.printStackTrace();
		}		
	}

	
public void receita(ActionEvent evento) {
		
		System.out.println("TESTE");
		try {
			
			consulta = (Consulta) evento.getComponent().getAttributes().get("consultaSelecionado");
			
			  String param_consulta = String.valueOf(consulta.getCodigo());			
					
			
			
		String caminho = Faces.getRealPath("/reports/receita.jasper");
			
		Map<String, Object> parametros = new HashMap<> ();
		parametros.put("RECEITA_CONSULTA", param_consulta);
		

		
			
		Connection conexao = HibernateUtil.getConexao();
			
		JasperPrint relatorio =  JasperFillManager.fillReport(caminho, parametros, conexao);
	    JasperViewer.viewReport(relatorio, false);
	    
	   	    
	    
		} catch (JRException erro) {
		Messages.addGlobalError("Ocorreu um erro ao imprimir o relatório");
		erro.printStackTrace();
		}		
	}
	public void popular() {
		try {
			if (especialidade != null) {

				UsuariosDAO medicoDAO = new UsuariosDAO();
				medicos = medicoDAO.buscaUsuariosPorEspecialidade(especialidade.getCodigo());

			} else {
				medicos = new ArrayList<>();
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar as cidades");
			erro.printStackTrace();
		}
	}

}
