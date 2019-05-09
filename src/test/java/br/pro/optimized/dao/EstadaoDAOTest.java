package br.pro.optimized.dao;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import br.pro.optimized.domain.Estado;

public class EstadaoDAOTest {
	@Test
	@Ignore
	public void salvar() {
		Estado estado = new Estado();
		estado.setNome("Rio Grande do Sul");
		estado.setSigla("RS");

		EstadoDAO estadoDAO = new EstadoDAO();
		estadoDAO.salvar(estado);
	}

	@Test
	@Ignore
	public void listar() {
		EstadoDAO estadoDAO = new EstadoDAO();
		List<Estado> resultado = estadoDAO.listar();

		System.out.println("Total de Registros Encontrados: " + resultado.size());

		for (Estado estado : resultado) {
			System.out.println(estado.getCodigo() + " - " + estado.getSigla() + " - " + estado.getNome());
		}
	}

	@Test
	public void buscar() {

		Long codigo = 5L;

		EstadoDAO estadoDAO = new EstadoDAO();

		Estado estado = estadoDAO.buscar(codigo);

		if (estado == null) {

			System.out.println("Nenhum registro encontrado");
		}

		else {

			System.out.println(estado.getSigla() + " -  " + estado.getNome());
		}
	}

	@Test
	@Ignore
	public void excluir() {

		Long codigo = 2L;

		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.buscar(codigo);

		if (estado == null) {

			System.out.println("Nenhum registro encontrado");
		}

		else {
			estadoDAO.excluir(estado);
			System.out.println("Registro removido");
			System.out.println(estado.getSigla() + " -  " + estado.getNome());
		}

	}

	@Test
	@Ignore
	public void editar() {

		Long codigo = 1L;
		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.buscar(codigo);

		if (estado == null) {

			System.out.println("Nenhum registro encontrado");
		}

		else {

			estado.setSigla("SP");
			estado.setNome("São Paulo");
			estadoDAO.editar(estado);

			System.out.println("Registro alterado");

		}

	}

	@Test
	public void merge() {

		// Estado estado = new Estado();
		// estado.setNome("Minas Gerais");
		// estado.setSigla("MG");

		// EstadoDAO estadoDAO = new EstadoDAO();
		// estadoDAO.merge(estado);

		EstadoDAO estadoDAO = new EstadoDAO();
		Estado estado = estadoDAO.buscar(2L);
		estado.setNome("Maranhão");
		estadoDAO.merge(estado);

	}
}
