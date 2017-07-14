package br.com.sintech.core.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.sintech.core.entity.Programa;

public class ProgramaDao extends GenericDao<Programa> {

	private static final String JPQL_MENUS_SISTEMA = "Select distinct p.menu From Programa p";

	public ProgramaDao() {
		super(Programa.class);
	}

	
	public List<String> findMenusSistema() {

		EntityManager em = getEntityManager();
		TypedQuery<String> query = em.createQuery(JPQL_MENUS_SISTEMA, String.class);

		List<String> lista;
		try {
			lista = query.getResultList();

		} catch (Exception ex) {
			lista = new ArrayList<String>();
		} finally {
			em.close();
		}
		return lista;
	}
}
