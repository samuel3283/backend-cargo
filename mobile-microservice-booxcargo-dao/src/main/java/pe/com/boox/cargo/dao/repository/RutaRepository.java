package pe.com.boox.cargo.dao.repository;

import java.util.List;
import pe.com.boox.cargo.model.Ruta;

public interface RutaRepository {
	void insertRuta(Ruta ruta) throws Exception;
	void insertRutaOff(Ruta ruta) throws Exception;
	Ruta getRuta(Integer codigoGuia, int min, int max) throws Exception;
	List<Ruta> listRuta(Integer codigoGuia, int min, int max) throws Exception;
	Integer cantidad(Integer codigoGuia) throws Exception;

}
