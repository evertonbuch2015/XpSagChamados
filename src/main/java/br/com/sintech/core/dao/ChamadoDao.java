package br.com.sintech.core.dao;

import java.math.BigInteger;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.sintech.core.entity.Chamado;

public class ChamadoDao extends GenericDao<Chamado> {

	public ChamadoDao() {
		super(Chamado.class);
	}

	
	public Integer getCodigoProtocolo(){
		
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		
		Query query = em.createNativeQuery("select gen_id( G_CODIGO_CHAMADO,1 )from RDB$DATABASE");
		
		BigInteger retorno = (BigInteger) query.getSingleResult();		
		
		em.getTransaction().commit();
		em.close();
		return retorno.intValue();
	}
}
