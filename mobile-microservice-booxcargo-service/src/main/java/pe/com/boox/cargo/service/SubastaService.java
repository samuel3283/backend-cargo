package pe.com.boox.cargo.service;

import java.util.List;

import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.Subasta;

public interface SubastaService {

	void deleteSubasta(Subasta request, String token) throws Exception;
	void updateSubasta(Subasta request, String token) throws Exception;
	public void asignaSubasta(Subasta request, String token) throws Exception;
	public List<Subasta> lstSubasta(Subasta request, String token) throws Exception;
	List<Subasta> lstMisSubasta(Subasta request, String token) throws Exception;

}
