package br.pro.optimized.dao;



import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.pro.optimized.domain.Consulta;
import br.pro.optimized.domain.Usuarios;
import br.pro.optimized.util.HibernateUtil;


public class ConsultaDAO extends GenericDAO<Consulta> {
	
	
	@SuppressWarnings("unchecked")

	public List<Consulta> listarPorPaciente(Long i) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			
			Criteria consulta = sessao.createCriteria(Consulta.class);
			consulta.createAlias("paciente", "u");
			consulta.add(Restrictions.eq("u.codigo",i));
			List<Consulta> resultado = consulta.list();
			return resultado;
			

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
	
	
	@SuppressWarnings("unchecked")

	public List<Consulta> listarConsultasAbertas(Long codigo) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			
			Criteria consulta = sessao.createCriteria(Consulta.class);
			consulta.add(Restrictions.eq("medico.codigo", codigo));
			consulta.add(Restrictions.eq("finalizado", false));
			
			List<Consulta> resultado = consulta.list();
			return resultado;
			

		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}
}	

