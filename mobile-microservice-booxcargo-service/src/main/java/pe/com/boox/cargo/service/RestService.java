package pe.com.boox.cargo.service;

import java.util.List;

import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.Ruta;
import pe.com.boox.cargo.model.TransactionRs;

public interface RestService {

	List<Ruta> listCoordenadas(String urlList, Guia request) throws Exception;
	TransactionRs<String> addCoordenadas(String urlList, Ruta request);
}
