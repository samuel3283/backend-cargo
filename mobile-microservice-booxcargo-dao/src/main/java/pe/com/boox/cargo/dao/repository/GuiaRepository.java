package pe.com.boox.cargo.dao.repository;

import java.util.List;
import java.util.Map;

import pe.com.boox.cargo.model.BeanUtil;
import pe.com.boox.cargo.model.Chofer;
import pe.com.boox.cargo.model.Guia;

public interface GuiaRepository {

	public Integer insertaGuia(Guia guia) throws Exception;
	List<BeanUtil> lstCantidadGuia(Guia guia) throws Exception;
	Integer updateGuia(Guia guia) throws Exception;
	void insertGuia(Guia guia) throws Exception;
	public Guia getGuia(Guia guia) throws Exception;
	public List<Guia> listGuia(Guia guia) throws Exception;
	List<Guia> listVerifyGuia(Guia guia) throws Exception;
	List<Guia> listMiGuia(Guia guia) throws Exception;
	public List<Guia> listGuiaSubasta(Guia guia) throws Exception;
	public List<Guia> listGuiaUltimos(Guia guia) throws Exception;
	public List<Guia> listGuiaAll(Guia guia) throws Exception;
	public List<Guia> listGuiaMiViaje(Guia guia, Integer codigoUsuarioTransporte) throws Exception;
	public Integer getIdGuia(Guia guia) throws Exception;
	public Integer getMaxIdGuia(Guia guia) throws Exception;
	public Integer getMaxNumGuia() throws Exception;
	public Integer getCantidadGuia(Guia guia) throws Exception;
	public void updatecontadorGuia(Integer contador) throws Exception;
	public void updateEstadoGuia(Guia guia) throws Exception;
	public void updateAprobarGuia(Guia guia) throws Exception;
	public List<Guia> listViajesChofer(Chofer request) throws Exception; 
	public List<Guia> listMisCargas(String ruc) throws Exception;
	 public List<Guia> listMiGuiaViajes(Integer codigoUsuarioTransporte) throws Exception;
	 public void updateIndicadorInicio(Guia guia) throws Exception;
	 public void updateIndicadorFin(Guia guia) throws Exception;
	 public Integer getCantidadXPeriodo(Guia guia, String periodo) throws Exception;
	 public List<Map<String, Object>> tiempoViaje(String ruc) throws Exception;	 
}
