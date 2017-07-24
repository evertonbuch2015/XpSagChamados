package br.com.sintech.core.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.sintech.core.entity.Chamado;
import br.com.sintech.core.entity.Empresa;
import br.com.sintech.core.entityVO.ChamadoDashboardVO;
import br.com.sintech.core.util.PersistenciaException;

public class ChamadoDao extends GenericDao<Chamado> {

	public ChamadoDao() {
		super(Chamado.class);
	}

	
	public Integer getCodigoProtocolo(){
		
		EntityManager em = getEntityManager();
		em.getTransaction().begin();
		
		Query query = em.createNativeQuery("select gen_id( G_PROTOCOLO_CHAMADO,1 )from RDB$DATABASE");
		
		BigInteger retorno = (BigInteger) query.getSingleResult();		
		
		em.getTransaction().commit();
		em.close();
		return retorno.intValue();
	}
	
	public List<ChamadoDashboardVO> getChamadosVOBySituacao(Empresa empresa)throws PersistenciaException{
		
		String sql =  " SELECT CAST('01.'||LPAD(EXTRACT(MONTH FROM S.DATA_SOLICITACAO), 2,'0')||'.'||EXTRACT(YEAR FROM S.DATA_SOLICITACAO)as date)AS DATA_SOLICITACAO, "
				+ " CAST(COUNT(*) AS INTEGER)AS QTD FROM UPD_SUPORTE S WHERE S.COD_UPDCLIENTES = %s GROUP BY 1";
		
		List<ChamadoDashboardVO> entities = new ArrayList<>();
		EntityManager em = getEntityManager();
		
		try {
			Query query = em.createNativeQuery(String.format(sql, empresa.getIdEmpresa()));
			
			@SuppressWarnings("unchecked")
			List<Object[]> results = (List<Object[]>) query.getResultList();
			
			
			for (Object[] entity : results) {
				entities.add(new ChamadoDashboardVO((Date)entity[0],(Integer)entity[1]));
			}
			
			return entities;
		} catch (Exception e) {
			throw new PersistenciaException(e);
		} finally {
			em.close();
		}		
	}
}
