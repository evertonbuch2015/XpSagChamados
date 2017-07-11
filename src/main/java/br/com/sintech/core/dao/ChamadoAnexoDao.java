package br.com.sintech.core.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.sintech.core.entity.Chamado;
import br.com.sintech.core.entity.ChamadoAnexo;

public class ChamadoAnexoDao extends GenericDao<ChamadoAnexo> {

	public ChamadoAnexoDao() {
		super(ChamadoAnexo.class);
	}

	
	@SuppressWarnings("unchecked")
	public List<ChamadoAnexo> findByChamado(Chamado chamado)throws Exception{
		EntityManager em = getEntityManager();
        em.getTransaction().begin();        
        
        Query query = em.createQuery("Select new br.com.sintech.core.entity.ChamadoAnexo("
        		+ "a.idChamadoAnexo, a.nome, a.caminho, a.extensao, a.tamanho,a.chamado) "
        		+ "From ChamadoAnexo a where a.chamado = ?1");
        query.setParameter(1, chamado);
        
        List<ChamadoAnexo> entities = null;
        try {
        	entities = query.getResultList();        	
        	em.getTransaction().commit();
        	
		} catch (Exception e) {
			e.printStackTrace();
			doRollback(em);  
			throw e;
		}finally{
			em.close();
		}
	            
        return entities; 
	}
	
	
	public ChamadoAnexo findAllAtributos(ChamadoAnexo chamadoAnexo)throws Exception{
		EntityManager em = getEntityManager();
        em.getTransaction().begin();        
        
        Query query = em.createQuery("Select new br.com.sintech.core.entity.ChamadoAnexo("
        		+ " a.idChamadoAnexo, a.nome, a.caminho, a.extensao, "
        		+ " a.tamanho, a.arquivo, a.chamado, a.contentType) "
        		+ " From ChamadoAnexo a where a.idChamadoAnexo = ?1");
        query.setParameter(1, chamadoAnexo.getIdChamadoAnexo());
        
        
        ChamadoAnexo entity = null;
        try {
        	entity = (ChamadoAnexo) query.getSingleResult();        	
        	em.getTransaction().commit();
        	
		} catch (Exception e) {
			e.printStackTrace();
			doRollback(em);  
			throw e;
		}finally{
			em.close();
		}
	            
        return entity; 
	}
}
