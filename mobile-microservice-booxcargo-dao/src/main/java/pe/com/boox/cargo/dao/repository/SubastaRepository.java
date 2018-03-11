package pe.com.boox.cargo.dao.repository;

import java.util.List;

import pe.com.boox.cargo.model.Subasta;

public interface SubastaRepository {

	public void insertSubasta(Subasta request) throws Exception;
	public void asignaSubasta(Subasta request) throws Exception;		
	void deleteSubasta(Subasta request) throws Exception;
	public Subasta getSubasta(Subasta request) throws Exception;
	public Subasta getSubastaAsignada(Subasta request) throws Exception;
	public List<Subasta> lstSubasta(Subasta request) throws Exception;
	public List<Subasta> lstGuiaSubasta(Subasta request) throws Exception;
	public Subasta getSubastaByCodigo(Subasta request) throws Exception;
	public Integer getCantidadSuabastasGuia(Subasta request) throws Exception;
	List<Subasta> lstSubastaAsg(Subasta request) throws Exception;
	 List<Subasta> lstMiSubasta(Subasta request) throws Exception;	
}
