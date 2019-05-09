package br.pro.optimized.bean;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;

import br.pro.optimized.dao.UsuariosDAO;
import br.pro.optimized.domain.Usuarios;



@ManagedBean
@SessionScoped
public class AutenticacaoBean {
	
	private Usuarios usuario;
	private Usuarios usuarioLogado;
	private HttpSession session;

	public Usuarios getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuarios usuario) {
		this.usuario = usuario;
	}

	public Usuarios getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuarios usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	@PostConstruct
	public void iniciar() {
		usuario = new Usuarios();
		
	}

	public void autenticar() {
		try {
			UsuariosDAO usuarioDAO = new UsuariosDAO();
			usuarioLogado = usuarioDAO.autenticar(getUsuario().getCpf(), usuario.getSenha());
			if (usuarioLogado == null) {
				Messages.addGlobalError("CPF e/ou senha incorretos");
				return;
			}

			Faces.redirect("./pages/principal.xhtml");
		} catch (IOException erro) {
			erro.printStackTrace();
			Messages.addGlobalError(erro.getMessage());
		}
	}

	public boolean temPermissoes(List<String> permissoes) {
		for (String permissao : permissoes) {
			if (usuarioLogado.getPerfil().getAbreviatura() == permissao.charAt(0)) {
				return true;
			}
		}

		return false;
	}

	public void encerraSessao() {
		try {
			FacesContext ctx = FacesContext.getCurrentInstance();
			session = (HttpSession) ctx.getExternalContext().getSession(false);
			session.setAttribute("usuarioAutenticado", null);
			ctx.getExternalContext().redirect("/Optimized/pages/autenticacao.xhtml");
			session.invalidate();
		} catch (Exception e) {
		}
	}

}
