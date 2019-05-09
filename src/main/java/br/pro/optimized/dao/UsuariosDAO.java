package br.pro.optimized.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.pro.optimized.domain.Usuarios;
import br.pro.optimized.util.HibernateUtil;

public class UsuariosDAO extends GenericDAO<Usuarios> {

	public Usuarios autenticar(String cpf, String senha) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Usuarios.class);
			consulta.add(Restrictions.eq("cpf", cpf));
			consulta.add(Restrictions.eq("senha", senha));
			Usuarios resultado = (Usuarios) consulta.uniqueResult();

			return resultado;

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}

	}
	
	
	
@SuppressWarnings("unchecked")
	
	public List<Usuarios> buscaUsuariosPorTipo(Character tipo) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			
			Criteria consulta = sessao.createCriteria(Usuarios.class);
			consulta.createAlias("perfil", "p");
			consulta.add(Restrictions.eq("p.abreviatura",tipo));
			
			// consulta.add(Restrictions.ilike("acomodacao.usuario.perfil.abreviatura",tipo));
			List<Usuarios> resultado = consulta.list();
			return resultado;
			

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}

}

@SuppressWarnings("unchecked")

public List<Usuarios> buscaUsuariosPorEspecialidade(Long especialidadeCodigo) {

	Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

	try {
		
		Criteria consulta = sessao.createCriteria(Usuarios.class);
		consulta.createAlias("especialidade", "e");
		consulta.add(Restrictions.eq("e.codigo",especialidadeCodigo));
		consulta.addOrder(Order.asc("especialidade"));
		
		List<Usuarios> resultado = consulta.list();
		return resultado;
		

	} catch (RuntimeException erro) {
		throw erro;
	} finally {
		sessao.close();
	}

}


}
