package pe.com.boox.cargo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import pe.com.boox.cargo.core.BooxException;
import pe.com.boox.cargo.dao.repository.ChoferRepository;
import pe.com.boox.cargo.dao.repository.GuiaDetalleRepository;
import pe.com.boox.cargo.dao.repository.GuiaEstadoRepository;
import pe.com.boox.cargo.dao.repository.GuiaRepository;
import pe.com.boox.cargo.dao.repository.RutaRepository;
import pe.com.boox.cargo.dao.repository.SessionChoferRepository;
import pe.com.boox.cargo.dao.repository.SessionRepository;
import pe.com.boox.cargo.dao.repository.SessionTransportistaRepository;
import pe.com.boox.cargo.dao.repository.SubastaRepository;
import pe.com.boox.cargo.dao.repository.UsuarioTransporteRepository;
import pe.com.boox.cargo.dao.repository.VehiculoTransporteRepository;
import pe.com.boox.cargo.model.Alertas;
import pe.com.boox.cargo.model.Chofer;
import pe.com.boox.cargo.model.Coordenadas;
import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.GuiaDetalle;
import pe.com.boox.cargo.model.GuiaEstado;
import pe.com.boox.cargo.model.Location;
import pe.com.boox.cargo.model.Position;
import pe.com.boox.cargo.model.Ruta;
import pe.com.boox.cargo.model.Session;
import pe.com.boox.cargo.model.SessionChofer;
import pe.com.boox.cargo.model.SessionTransportista;
import pe.com.boox.cargo.model.Subasta;
import pe.com.boox.cargo.model.TransactionRs;
import pe.com.boox.cargo.model.UsuarioTransporte;
import pe.com.boox.cargo.model.VehiculoTransporte;
import pe.com.boox.cargo.service.GuiaService;
import pe.com.boox.cargo.service.RestService;
import pe.com.boox.cargo.service.core.Constants;
import pe.com.boox.cargo.service.core.Util;

@Service
public class GuiaServiceImpl implements GuiaService {

	private final Logger logger = LoggerFactory.getLogger(GuiaService.class);

	@Autowired
	GuiaRepository guiaRepository;

	@Autowired
	GuiaDetalleRepository guiaDetalleRepository;

	@Autowired
	SessionChoferRepository sessionChoferRepository;

	@Autowired
	SessionRepository sessionRepository;

	@Autowired
	SessionTransportistaRepository sessionTransportistaRepository;

	@Autowired
	UsuarioTransporteRepository usuarioTransporteRepository;

	@Autowired
	SubastaRepository subastaRepository;

	@Autowired
	RutaRepository rutaRepository;

	@Autowired
	ChoferRepository choferRepository;

	@Autowired
	GuiaEstadoRepository guiaEstadoRepository;
	
	@Autowired
	VehiculoTransporteRepository vehiculoTransporteRepository;

	@Autowired
	private Environment env;

	//@Autowired 	RestTemplate restTemplate;
	
	@Autowired 	
	RestService restService;
	
	/*
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}*/

	
	public static String URL_LIST_RUTA ="https://k6tc5wolml.execute-api.us-east-1.amazonaws.com/DevCoordenadasAdd/coordenadas/list";
	public static String URL_ADD_RUTA = "https://k6tc5wolml.execute-api.us-east-1.amazonaws.com/DevCoordenadasAdd/coordenadas/add";
	
	@Override
	public void insertRuta(Ruta ruta, String token) throws Exception {

		logger.info("insertRuta:::"+ruta.toString());
		//rutaRepository.insertRuta(ruta);
						
		logger.info("ADD:::::"+URL_ADD_RUTA);
		TransactionRs respuesta = restService.addCoordenadas(URL_ADD_RUTA, ruta);
		logger.info("addCoordenadas");
		
		if ("0000".equals(respuesta.getCodigoError())) {
			logger.info("OK::::"+respuesta.toString());
		} else {
			logger.info("ERROR:::"+respuesta.toString());
			throw new BooxException("0003","Error invocar service aws.");
		}
				
	}
	
	@Override
	public void addCoordenadas(Ruta ruta, String token) throws Exception {

		
		SessionChofer session = new SessionChofer();
		session.setToken(token);
		session = sessionChoferRepository.getSessionByToken(session);
			
		if(session==null) 
			throw new BooxException("5000","Error al obtener sessión de usuario");

		
		Chofer chofer = new Chofer();
		chofer.setNumDocumento(session.getNumDocumento());
		chofer = choferRepository.getChoferByDocumento(chofer);	
			
		if(chofer==null) 
			throw new BooxException("5000","Error al obtener datos de chofer");

		
		List<Guia> lista = null;
		
		chofer.setEstado(5); //Guia en ruta
		lista = guiaRepository.listViajesChofer(chofer);
		logger.info("listViajesChofer size:::"+lista.size());
		String nota="";
		if(lista.size()>0) {
		
		boolean status = false;
		try {
			String velocidadMax = env.getProperty("velocidad.maxima");
			double velocidad = Double.parseDouble(velocidadMax);
			double valor = Double.parseDouble(ruta.getVelocidad());
			if(valor>velocidad) {
				nota = "Exceso de Velocidad.";
				ruta.setAlerta(nota);
				status = true;
			}		
			
			
		} catch(Exception e) {
			logger.info("Error Exceso de Velocidad:"+e.getMessage());			
		}
			
			
		for(Guia g: lista) {
			ruta.setCodigoGuia(g.getCodigo());
			ruta.setCodigoChofer(chofer.getCodigo());
			rutaRepository.insertRuta(ruta);
			if(status)  {
			g.setNota(nota);
			guiaEstadoRepository.insertaGuiaEstado(g);
			}

		}
		
		} else {
			throw new BooxException("0002","No tiene Cargas en Ruta");
		}

		logger.info("fin insertRuta:::");
	}
		
	
	@Override
	public void addCoordenadasV2(Ruta ruta, String token) throws Exception {

		SessionChofer session = new SessionChofer();
		session.setToken(token);
		logger.info("init.getSessionByToken***********************************");
		session = sessionChoferRepository.getSessionByToken(session);
		logger.info("fin.getSessionByToken***********************************");
			
		if(session==null) 
			throw new BooxException("5000","Error al obtener sessión de usuario");

		
		Chofer chofer = new Chofer();
		chofer.setNumDocumento(session.getNumDocumento());
		logger.info("init.getChoferByDocumento***********************************");
		chofer = choferRepository.getChoferByDocumento(chofer);	
		logger.info("fin.getChoferByDocumento***********************************");
		
		if(chofer==null) 
			throw new BooxException("5000","Error al obtener datos de chofer");

		
		List<Guia> lista = null;
		
		chofer.setEstado(5); //Guia en ruta
		logger.info("init.listViajesChofer***********************************");
		lista = guiaRepository.listViajesChofer(chofer);
		logger.info("fin.listViajesChofer***********************************");
		logger.info("listViajesChofer size:::"+lista.size());
		String nota="";
		if(lista.size()>0) {

		boolean status =  false;	


		try {
			String velocidadMax = env.getProperty("velocidad.maxima");
			double velocidad = Double.parseDouble(velocidadMax);
			double valor = Double.parseDouble(ruta.getVelocidad());
			if(valor>velocidad) {
				nota = "Exceso de Velocidad.";
				ruta.setAlerta(nota);
				status = true;
			}					
		} catch(Exception e) {
			logger.info("Error Exceso de Velocidad:"+e.getMessage());			
		}
		
		
		for(Guia g: lista) {
			ruta.setCodigoGuia(g.getCodigo());
			ruta.setCodigoguia(g.getCodigo());
			ruta.setCodigoChofer(chofer.getCodigo());			
			ruta.setFecha(Util.getFecha(Util.PATTERN_YY_MM_DD_HH_MI_SS));
			logger.info("URL_ADD_RUTA:::::"+URL_ADD_RUTA);
			logger.info("init.aws.addCoordenadas***********************************");
			TransactionRs respuesta = restService.addCoordenadas(URL_ADD_RUTA, ruta);
			logger.info("fin.aws.addCoordenadas***********************************");
						
			if ("0000".equals(respuesta.getCodigoError())) {
				logger.info("OK::::"+respuesta.toString());
			} else {
				logger.info("ERROR:::"+respuesta.toString());
				throw new BooxException("0003","Error invocar service aws.");
			}
				
			
			//rutaRepository.insertRuta(ruta);
			if(status)  {
			g.setNota(nota);
			logger.info("init.aws.insertaGuiaEstado***********************************");
			guiaEstadoRepository.insertaGuiaEstado(g);
			logger.info("fin.aws.insertaGuiaEstado***********************************");
			}

		}
		
		} else {
			throw new BooxException("0002","No tiene Cargas en Ruta");
		}

		logger.info("fin insertRuta:::");
	}
	

	@Override
	public void addLstCoordenadas(List<Ruta> ruta, String token) throws Exception {

		SessionChofer session = new SessionChofer();
		session.setToken(token);
		session = sessionChoferRepository.getSessionByToken(session);
			
		if(session==null) 
			throw new BooxException("5000","Error al obtener sessión de usuario");

		
		Chofer chofer = new Chofer();
		chofer.setNumDocumento(session.getNumDocumento());
		chofer = choferRepository.getChoferByDocumento(chofer);	
			
		if(chofer==null) 
			throw new BooxException("5000","Error al obtener datos de chofer");

		
		List<Guia> lista = null;
		
		chofer.setEstado(5); //Guia en ruta
		lista = guiaRepository.listViajesChofer(chofer);
		logger.info("listViajesChofer size:::"+lista.size());
		
		if(lista.size()>0) {
		
		for(Guia g: lista) {
			
			for(Ruta r: ruta) {
			r.setCodigoGuia(g.getCodigo());
			r.setCodigoChofer(chofer.getCodigo());
			r.setTipo("OFF");
			rutaRepository.insertRutaOff(r);
			}
		}
		
		} else {
			throw new BooxException("0002","No tiene Cargas en Ruta");
		}

		logger.info("fin insertRuta:::");
	}
	

	
	@Override
	public String insertGuia(Guia guia, String token) throws Exception {
		String resultado ="";
		Integer idGuia = 0;
		Session session = new Session();
		session.setToken(token);
		session = sessionRepository.getSessionByToken(session);

		String fechaEmision = Util.getFecha(Util.PATTERN_DD_MM_YY_HH_MI_SS);
		guia.setFechaEmision(fechaEmision);
		
		String horaIniTraslado = Util.getDateToFormat(guia.getHoraIniTraslado(), Util.PATTERN_HH_MI_AM, 
				Util.PATTERN_HH_MI_SS);
		
		String horaFinTraslado = Util.getDateToFormat(guia.getHoraFinTraslado(), Util.PATTERN_HH_MI_AM, 
				Util.PATTERN_HH_MI_SS);

		guia.setFechaIniTraslado(guia.getFechaIniTraslado()+" "+horaIniTraslado);
		guia.setFechaFinTraslado(guia.getFechaFinTraslado()+" "+horaFinTraslado);
		
		logger.info("==>getFechaIniTraslado:"+guia.getFechaIniTraslado()+"==>getFechaFinTraslado:"+guia.getFechaFinTraslado());
		//guiaRepository.insertGuia(guia);
		//Integer id = guiaRepository.getIdGuia(guia);
		Integer numGuia = guiaRepository.getMaxNumGuia();
		resultado = String.valueOf(numGuia);
		guia.setNumeroGuia(String.valueOf(numGuia));
		guia.setRuc(session.getRuc());
		guia.setRucRemitente(session.getRuc());
		
		idGuia = guiaRepository.insertaGuia(guia);
		guiaRepository.updatecontadorGuia(numGuia);
		
		logger.info("idGuia:"+idGuia);
		if(guia.getListado()!=null) {
			
			logger.info("GuiaDetalle.size:"+guia.getListado().size());
			for (GuiaDetalle bean : guia.getListado()) {
				bean.setCodigoGuia(idGuia);
				logger.info("GuiaDetalle....."+bean.toString());
				guiaDetalleRepository.insertGuiaDetalle(bean);
			}
	 		
		}
		return resultado;
	}

	@Override
	public void updateIndicadorInicio(Guia guia, String token) throws Exception {
		guiaRepository.updateIndicadorInicio(guia);		
	}

	@Override
	public void updateIndicadorFin(Guia guia, String token) throws Exception {
		guiaRepository.updateIndicadorFin(guia);		
	}
	
	@Override
	public void updateGuia(Guia guia, String token) throws Exception {
		logger.info("==>updateGuia==>"+guia.toString());
		String resultado ="";
		Integer idGuia = 0;
		Session session = new Session();
		session.setToken(token);
		session = sessionRepository.getSessionByToken(session);

		String horaIniTraslado = Util.getDateToFormat(guia.getHoraIniTraslado(), Util.PATTERN_HH_MI_AM, 
				Util.PATTERN_HH_MI_SS);
		
		String horaFinTraslado = Util.getDateToFormat(guia.getHoraFinTraslado(), Util.PATTERN_HH_MI_AM, 
				Util.PATTERN_HH_MI_SS);
		
		String fechaIniTraslado = Util.getDateToFormat(guia.getFechaIniTraslado(), Util.PATTERN_DEFAULT, 
				Util.PATTERN_DEFAULT_YYMMDD);
		
		String fechaFinTraslado = Util.getDateToFormat(guia.getFechaFinTraslado(), Util.PATTERN_DEFAULT, 
				Util.PATTERN_DEFAULT_YYMMDD);
		
		//guia.setFechaIniTraslado(guia.getFechaIniTraslado()+" "+horaIniTraslado);
		//guia.setFechaFinTraslado(guia.getFechaFinTraslado()+" "+horaFinTraslado);
		guia.setFechaIniTraslado(fechaIniTraslado+" "+horaIniTraslado);
		guia.setFechaFinTraslado(fechaFinTraslado+" "+horaFinTraslado);
		
		logger.info("==>getFechaIniTraslado:"+guia.getFechaIniTraslado()+"==>getFechaFinTraslado:"+guia.getFechaFinTraslado());
		//guiaRepository.insertGuia(guia);
		//Integer id = guiaRepository.getIdGuia(guia);
		guia.setRuc(session.getRuc());
		guia.setRucRemitente(session.getRuc());
		
		idGuia = guiaRepository.updateGuia(guia);
		
		GuiaDetalle GuiaDetalle = new GuiaDetalle();
		GuiaDetalle.setCodigoGuia(guia.getCodigo());
		guiaDetalleRepository.deleteGuiaDetalle(GuiaDetalle);
		logger.info("idGuia:"+idGuia);
		if(guia.getListado()!=null) {
			
			logger.info("GuiaDetalle.size:"+guia.getListado().size());
			for (GuiaDetalle bean : guia.getListado()) {
				bean.setCodigoGuia(idGuia);
				logger.info("GuiaDetalle....."+bean.toString());
				guiaDetalleRepository.insertGuiaDetalle(bean);
			}
	 		
		}

	}

	@Override
	public void updateEstadoGuia(Guia guia, String token) throws Exception {

		Guia bean = guiaRepository.getGuia(guia);
		if(bean==null) 
			throw new BooxException("5000","Error al obtener datos de la guia.");

		if(bean.getEstado()==7) {
			guia.setEstado(bean.getEstado());
			/*1:PENDIENTE,2:APROBADO,3:POSTULADO,
			  4:ASIGNADO	5:EN RUTA 	6:EN PAUSA	7:TERMINADO			 */
		}
		
		if(bean.getEstado().intValue()==5) {			
			if(guia.getEstado().intValue()<5)
				guia.setEstado(bean.getEstado());
		}
		
		guiaRepository.updateEstadoGuia(guia);
		guiaEstadoRepository.insertaGuiaEstado(guia);
	}

	@Override
	public void insertGuia(Guia guia) throws Exception {
		
		//guiaRepository.insertGuia(guia);
		//Integer id = guiaRepository.getIdGuia(guia);
		Integer numGuia = guiaRepository.getMaxNumGuia();
		guia.setNumeroGuia(String.valueOf(numGuia));
		Integer id = guiaRepository.insertaGuia(guia);
		logger.info("listado:"+id);
		if(guia.getListado()!=null) {
			
			logger.info("GuiaDetalle.size:"+guia.getListado().size());
			for (GuiaDetalle bean : guia.getListado()) {
				logger.info("GuiaDetalle....."+bean.toString());
				guiaDetalleRepository.insertGuiaDetalle(bean);
			}
	 		
		}
		
	}

	@Override
	public Guia getGuia(Guia guia) throws Exception {
		
		List<GuiaDetalle> listaDetalle = null;
		GuiaDetalle detalle = null;
		Guia objeto = guiaRepository.getGuia(guia);

		if(objeto!=null)
		{
			listaDetalle = null;

			detalle = new GuiaDetalle();
			detalle.setCodigoGuia(objeto.getCodigo());
			listaDetalle = guiaDetalleRepository.listGuiaDetalle(detalle);
			objeto.getListado().addAll(new ArrayList<GuiaDetalle>());
			objeto.getListado().addAll(listaDetalle);				
			List<Location> recorrido = new ArrayList<Location>();
			List<Position> positions = new ArrayList<Position>();
			List<Coordenadas> coordenadas = new ArrayList<Coordenadas>();
			
			if(objeto.getEstado()==4 || objeto.getEstado()==5 || objeto.getEstado()==6 || objeto.getEstado()==7) {
			Subasta subasta = new Subasta();
			subasta.setCodigoGuia(objeto.getCodigo());
			subasta.setEstado(Constants.ESTADO_SUBASTA_ASIGNADO);
			objeto.setSubasta(subastaRepository.getSubastaAsignada(subasta));					
			}
			
			if(objeto.getEstado()==5 || objeto.getEstado()==6 || objeto.getEstado()==7) {
				
				int cantidad = rutaRepository.cantidad(objeto.getCodigo());

				logger.info("==>cantidad:"+cantidad);
				List<Ruta> lstTotal = rutaRepository.listRuta(objeto.getCodigo(), 0, cantidad);
				
				
				
				
				
				List<Ruta> lstIni = new ArrayList();
				if(cantidad>0)
					lstIni = rutaRepository.listRuta(objeto.getCodigo(), 0, 1);
				
				Ruta rutaIni = null;
				if(lstIni.size()>0) {
					rutaIni = lstIni.get(0);
					double lati = Double.parseDouble(rutaIni.getLat());
					double lngi = Double.parseDouble(rutaIni.getLng());
					Location locationi = new Location(lati,lngi,rutaIni.getAlerta(),rutaIni.getFecha());
					//recorrido.add(locationi);
				}
				List<Ruta> lstFin = new ArrayList();
				if(cantidad>0) 
					lstFin = rutaRepository.listRuta(objeto.getCodigo(), cantidad-1, cantidad);
				
				Ruta rutaFin = null;
				if(lstFin.size()>0) {
					rutaFin = lstFin.get(0);
				}
				rutaIni = new Ruta();
				rutaFin = new Ruta();
				rutaIni.setLat(String.valueOf(objeto.getLatOri()));
				rutaIni.setLng(String.valueOf(objeto.getLngOri()));
				rutaFin.setLat(String.valueOf(objeto.getLatDes()));
				rutaFin.setLng(String.valueOf(objeto.getLngDes()));
				
				objeto.setRutaOri(rutaIni);
				objeto.setRutaDes(rutaFin);
				
				int limite = 11;
				if(cantidad<limite) {
				int total = lstTotal.size();	
				int total2 = lstTotal.size()-1;	
				for(int ii=0;ii<total2;ii=ii+1) {
					Location ori = new Location(Double.parseDouble(lstTotal.get(ii).getLat()),Double.parseDouble(lstTotal.get(ii).getLng()));
					Location des = new Location(Double.parseDouble(lstTotal.get(ii+1).getLat()),Double.parseDouble(lstTotal.get(ii+1).getLng()));
					Coordenadas coord = new Coordenadas(ori,des);
					coordenadas.add(coord);
				}
				
				for(int i=0;i<total;i=i+1) {
										
					//if(i!=0 || i!=total-1) {
					logger.info(".--------."+i+".getLat():"+lstTotal.get(i).getLat());						
					double lat = Double.parseDouble(lstTotal.get(i).getLat());
					double lng = Double.parseDouble(lstTotal.get(i).getLng());
					Location location = new Location(lat,lng,lstTotal.get(i).getAlerta(),lstTotal.get(i).getFecha());
					recorrido.add(location);
					
					Location locationes = new Location(lat,lng);
					Position positiones = new Position(locationes,true);
					positions.add(positiones);
					//}
				}					
							
				} else {
					
					int rango = cantidad / limite;
					rango= rango +1 ;
					logger.info("==>rango:"+rango);
					int total = lstTotal.size();
					int total2 = lstTotal.size()-rango;
					for(int ii=0;ii<total2;ii=ii+rango) {
						Location ori = new Location(Double.parseDouble(lstTotal.get(ii).getLat()),Double.parseDouble(lstTotal.get(ii).getLng()));
						Location des = new Location(Double.parseDouble(lstTotal.get(ii+rango).getLat()),Double.parseDouble(lstTotal.get(ii+rango).getLng()));
						Coordenadas coord = new Coordenadas(ori,des);
						coordenadas.add(coord);
					}

					for(int i=0;i<total;i=i+rango) {
						

						//if(i!=0 || i!=total-1) {
						if(lstTotal.get(i)!=null) {
						logger.info(".."+i+".getLat():"+lstTotal.get(i).getLat());						
						double lat = Double.parseDouble(lstTotal.get(i).getLat());
						double lng = Double.parseDouble(lstTotal.get(i).getLng());
						Location location = new Location(lat,lng,lstTotal.get(i).getAlerta(),lstTotal.get(i).getFecha());
						recorrido.add(location);
						
						Location locationes = new Location(lat,lng);
						Position positiones = new Position(locationes,true);
						positions.add(positiones);
						//}
						}
					}					
					
				}				
				
				if(lstFin.size()>0) {
					rutaFin = lstFin.get(0);
					double latf = Double.parseDouble(rutaFin.getLat());
					double lngf = Double.parseDouble(rutaFin.getLng());
					Location locationf = new Location(latf,lngf,rutaFin.getAlerta(),rutaFin.getFecha());
					//recorrido.add(locationf);
					objeto.setRutaDes(rutaFin);
				}


				rutaIni = new Ruta();
				rutaFin = new Ruta();
				rutaIni.setLat(String.valueOf(objeto.getLatOri()));
				rutaIni.setLng(String.valueOf(objeto.getLngOri()));
				rutaFin.setLat(String.valueOf(objeto.getLatDes()));
				rutaFin.setLng(String.valueOf(objeto.getLngDes()));
				objeto.setRutaOri(rutaIni);
				objeto.setRutaDes(rutaFin);

				objeto.setRecorrido(recorrido);				
				objeto.setPosition(positions);
				objeto.setCoordenadas(coordenadas);
				List<GuiaEstado> estados = guiaEstadoRepository.listGuiaEstadoAlerta(objeto);
				objeto.setAlertas(estados);
				
			}
				
			
		}

		return objeto;
	}

	@Override
	public Guia getGuiaV2(Guia guia) throws Exception {
		
		List<GuiaDetalle> listaDetalle = null;
		GuiaDetalle detalle = null;
		logger.info("ini.getGuia ***************************************");
		Guia objeto = guiaRepository.getGuia(guia);
		logger.info("fin.getGuia ***************************************");

		if(objeto!=null)
		{

			if(objeto.isShowDetalle()) {
			listaDetalle = null;
			detalle = new GuiaDetalle();
			detalle.setCodigoGuia(objeto.getCodigo());
			logger.info("ini.listGuiaDetalle ***************************************");
			listaDetalle = guiaDetalleRepository.listGuiaDetalle(detalle);
			logger.info("fin.listGuiaDetalle ***************************************");
			objeto.getListado().addAll(new ArrayList<GuiaDetalle>());
			objeto.getListado().addAll(listaDetalle);				
			}
			
			List<Location> recorrido = new ArrayList<Location>();
			List<Position> positions = new ArrayList<Position>();
			List<Coordenadas> coordenadas = new ArrayList<Coordenadas>();
			
			if(objeto.isShowSubasta()) {
			if(objeto.getEstado()==4 || objeto.getEstado()==5 || objeto.getEstado()==6 || objeto.getEstado()==7) {
			Subasta subasta = new Subasta();
			subasta.setCodigoGuia(objeto.getCodigo());
			subasta.setEstado(Constants.ESTADO_SUBASTA_ASIGNADO);
			logger.info("ini.getSubastaAsignada ***************************************");
			objeto.setSubasta(subastaRepository.getSubastaAsignada(subasta));					
			logger.info("fin.getSubastaAsignada ***************************************");
			} 
			}
			
			if(objeto.getEstado()==5 || objeto.getEstado()==6 || objeto.getEstado()==7) {
				
				
				logger.info("URL_LIST_RUTA:::::"+URL_LIST_RUTA);
				logger.info("ini.aws.listCoordenadas ***************************************");
				List<Ruta> lstTotal = restService.listCoordenadas(URL_LIST_RUTA, objeto);
				logger.info("fin.aws.listCoordenadas ***************************************");
				
				int cantidad = lstTotal.size();
				logger.info("==>INICIO");
				logger.info("==>cantidad:"+cantidad);
				
				Ruta rutaIni = null;
				if(lstTotal.size()>0) {
					rutaIni = lstTotal.get(0);
					double lati = Double.parseDouble(rutaIni.getLat());
					double lngi = Double.parseDouble(rutaIni.getLng());
					Location locationi = new Location(lati,lngi,rutaIni.getAlerta(),rutaIni.getFecha());
					
				}
				
				Ruta rutaFin = null;
				if(lstTotal.size()>1) {
					rutaFin = lstTotal.get(cantidad-1);
				}

				rutaIni = new Ruta();
				rutaFin = new Ruta();
				rutaIni.setLat(String.valueOf(objeto.getLatOri()));
				rutaIni.setLng(String.valueOf(objeto.getLngOri()));
				rutaFin.setLat(String.valueOf(objeto.getLatDes()));
				rutaFin.setLng(String.valueOf(objeto.getLngDes()));
				
				objeto.setRutaOri(rutaIni);
				objeto.setRutaDes(rutaFin);
				
				int limite = 11;
				if(cantidad<limite) {
				int total = lstTotal.size();	
				int total2 = lstTotal.size()-1;	
				for(int ii=0;ii<total2;ii=ii+1) {
					Location ori = new Location(Double.parseDouble(lstTotal.get(ii).getLat()),Double.parseDouble(lstTotal.get(ii).getLng()));
					Location des = new Location(Double.parseDouble(lstTotal.get(ii+1).getLat()),Double.parseDouble(lstTotal.get(ii+1).getLng()));
					Coordenadas coord = new Coordenadas(ori,des);
					coordenadas.add(coord);
				}
				
				for(int i=0;i<total;i=i+1) {
										
					//if(i!=0 || i!=total-1) {
					logger.info(".--------."+i+".getLat():"+lstTotal.get(i).getLat());						
					double lat = Double.parseDouble(lstTotal.get(i).getLat());
					double lng = Double.parseDouble(lstTotal.get(i).getLng());
					Location location = new Location(lat,lng,lstTotal.get(i).getAlerta(),lstTotal.get(i).getFecha());
					recorrido.add(location);
					
					Location locationes = new Location(lat,lng);
					Position positiones = new Position(locationes,true);
					positions.add(positiones);
					//}
				}					
							
				} else {
					
					int rango = cantidad / limite;
					rango= rango +1 ;
					logger.info("==>rango:"+rango);
					int total = lstTotal.size();
					int total2 = lstTotal.size()-rango;
					for(int ii=0;ii<total2;ii=ii+rango) {
						Location ori = new Location(Double.parseDouble(lstTotal.get(ii).getLat()),Double.parseDouble(lstTotal.get(ii).getLng()));
						Location des = new Location(Double.parseDouble(lstTotal.get(ii+rango).getLat()),Double.parseDouble(lstTotal.get(ii+rango).getLng()));
						Coordenadas coord = new Coordenadas(ori,des);
						coordenadas.add(coord);
					}

					for(int i=0;i<total;i=i+rango) {
						

						//if(i!=0 || i!=total-1) {
						if(lstTotal.get(i)!=null) {
						logger.info(".."+i+".getLat():"+lstTotal.get(i).getLat());						
						double lat = Double.parseDouble(lstTotal.get(i).getLat());
						double lng = Double.parseDouble(lstTotal.get(i).getLng());
						Location location = new Location(lat,lng,lstTotal.get(i).getAlerta(),lstTotal.get(i).getFecha());
						recorrido.add(location);
						
						Location locationes = new Location(lat,lng);
						Position positiones = new Position(locationes,true);
						positions.add(positiones);
						//}
						}
					}					
					
				}				
				
				if(lstTotal.size()>1) {
					rutaFin = lstTotal.get(cantidad-1);
					double latf = Double.parseDouble(rutaFin.getLat());
					double lngf = Double.parseDouble(rutaFin.getLng());
					Location locationf = new Location(latf,lngf,rutaFin.getAlerta(),rutaFin.getFecha());
					//recorrido.add(locationf);
					objeto.setRutaDes(rutaFin);
				}

				
				rutaIni = new Ruta();
				rutaFin = new Ruta();
				rutaIni.setLat(String.valueOf(objeto.getLatOri()));
				rutaIni.setLng(String.valueOf(objeto.getLngOri()));
				rutaFin.setLat(String.valueOf(objeto.getLatDes()));
				rutaFin.setLng(String.valueOf(objeto.getLngDes()));
				objeto.setRutaOri(rutaIni);
				objeto.setRutaDes(rutaFin);

				objeto.setRecorrido(recorrido);				
				objeto.setPosition(positions);
				objeto.setCoordenadas(coordenadas);
				//List<GuiaEstado> estados = guiaEstadoRepository.listGuiaEstadoAlerta(objeto);
				//objeto.setAlertas(estados);
				
			}
				
			
		}
		logger.info("==>FIN");
		
		return objeto;
	}

	
	@Override
	public List<Guia> listGuia(Guia guia) throws Exception {
		
		return guiaRepository.listGuia(guia);
	}
	@Override
	public List<Guia> listGuia(Guia guia, String token) throws Exception {
		Session session = new Session();
		session.setToken(token);
		session = sessionRepository.getSessionByToken(session);
		guia = new Guia();
		guia.setRuc(session.getRuc());
		
		List<Guia> lista = null;
		List<Guia> listaFinal = null;
		List<GuiaDetalle> listaDetalle = null;
		GuiaDetalle detalle = null;
		lista = guiaRepository.listGuia(guia);

		/*
		if(lista!=null && lista.size()>0)
		{
			listaDetalle = null;
			listaFinal = new ArrayList<Guia>();
			for(Guia bean: lista) {
				detalle = new GuiaDetalle();
				detalle.setCodigoGuia(bean.getCodigo());
				listaDetalle = guiaDetalleRepository.listGuiaDetalle(detalle);
				bean.getListado().addAll(new ArrayList<GuiaDetalle>());
				bean.getListado().addAll(listaDetalle);
				
				listaFinal.add(bean);				
			}
			
		}
		return listaFinal;
		*/
		return lista;
		
	}

	@Override
	public List<Guia> listVerifyGuia(Guia guia, String token) throws Exception {
		Session session = new Session();
		session.setToken(token);
		session = sessionRepository.getSessionByToken(session);
		guia = new Guia();
		guia.setRuc(session.getRuc());
		
		List<Guia> lista = null;
		List<Guia> listaFinal = null;
		List<GuiaDetalle> listaDetalle = null;
		GuiaDetalle detalle = null;
		lista = guiaRepository.listVerifyGuia(guia);

		return lista;		
	}

	@Override
	public List<Guia> listMiGuia(Guia guia, String token) throws Exception {
		Session session = new Session();
		session.setToken(token);
		session = sessionRepository.getSessionByToken(session);
		guia = new Guia();
		guia.setRuc(session.getRuc());
		
		List<Guia> lista = null;
		List<Guia> listaFinal = null;
		List<GuiaDetalle> listaDetalle = null;
		GuiaDetalle detalle = null;
		lista = guiaRepository.listMiGuia(guia);

		/*
		if(lista!=null && lista.size()>0)
		{
			listaDetalle = null;
			listaFinal = new ArrayList<Guia>();
			for(Guia bean: lista) {
				detalle = new GuiaDetalle();
				detalle.setCodigoGuia(bean.getCodigo());
				listaDetalle = guiaDetalleRepository.listGuiaDetalle(detalle);
				bean.getListado().addAll(new ArrayList<GuiaDetalle>());
				bean.getListado().addAll(listaDetalle);
				
				listaFinal.add(bean);				
			}
			
		}*/
		return lista;
	}

	@Override
	public List<Guia> listGuiaSubasta(Guia guia, String token) throws Exception {
		Session session = new Session();
		session.setToken(token);
		session = sessionRepository.getSessionByToken(session);
		guia = new Guia();
		guia.setRuc(session.getRuc());
		
		List<Guia> lista = null;
		List<Guia> listaFinal = null;
		List<GuiaDetalle> listaDetalle = null;
		GuiaDetalle detalle = null;
		lista = guiaRepository.listGuiaSubasta(guia);

		/*
		if(lista!=null && lista.size()>0)
		{
			listaDetalle = null;
			listaFinal = new ArrayList<Guia>();
			for(Guia bean: lista) {
				detalle = new GuiaDetalle();
				detalle.setCodigoGuia(bean.getCodigo());
				//listaDetalle = guiaDetalleRepository.listGuiaDetalle(detalle);
				//bean.getListado().addAll(new ArrayList<GuiaDetalle>());
				//bean.getListado().addAll(listaDetalle);

				logger.info("BEAN de listGuiaSubasta::::"+bean.toString());

				if(bean.getEstado().intValue()==Constants.ESTADO_GUIA_POSTULADO.intValue()) {
					logger.info("Postulado");
					Subasta subasta = new Subasta();
					subasta.setCodigoGuia(bean.getCodigo());
					List<Subasta> lstSubasta = subastaRepository.lstGuiaSubasta(subasta);
					bean.getLstSubasta().addAll(lstSubasta);					
				}
				if(bean.getEstado().intValue()==Constants.ESTADO_GUIA_ASIGNADO.intValue()) {
					logger.info("Asignado");
					Subasta subasta = new Subasta();
					subasta.setCodigoGuia(bean.getCodigo());
					subasta.setEstado(Constants.ESTADO_SUBASTA_ASIGNADO);
					bean.setSubasta(subastaRepository.getSubastaAsignada(subasta));					
				}
									
				listaFinal.add(bean);				
			}			
		}
		return listaFinal;
		*/
		return lista;
	}

	
	@Override
	public List<Guia> listGuiaUltimos(Guia guia, String token) throws Exception {
		
		return guiaRepository.listGuiaUltimos(guia);
	}


	@Override
	public List<Guia> listGuiaAll(Guia guia, String token) throws Exception {
		
		SessionTransportista session = new SessionTransportista();
		session.setToken(token);
		session = sessionTransportistaRepository.getSessionByToken(session);
		
		if(session==null) 
			throw new BooxException("5000","Error al obtener sessión de usuario");

		UsuarioTransporte usuarioTransporte = new UsuarioTransporte();
		usuarioTransporte.setEmail(session.getEmail());
		usuarioTransporte = usuarioTransporteRepository.getUsuarioTransporte(usuarioTransporte);	
			
		if(usuarioTransporte==null) 
			throw new BooxException("5000","Error al obtener datos de usuario");


		List<Guia> lista = null;
		List<Guia> listaFinal = null;
		List<GuiaDetalle> listaDetalle = null;
		GuiaDetalle detalle = null;
		
		lista = guiaRepository.listGuiaAll(guia);
		
		if(lista!=null && lista.size()>0)
		{
			listaDetalle = null;
			listaFinal = new ArrayList<Guia>();
			for(Guia bean: lista) {
				Subasta subasta = new Subasta();
				subasta.setCodigoGuia(bean.getCodigo());
				subasta.setUsuarioTransporte(usuarioTransporte);
				
				subasta = subastaRepository.getSubasta(subasta);

				bean.setSubasta(subasta);
				detalle = new GuiaDetalle();
				detalle.setCodigoGuia(bean.getCodigo());
				listaDetalle = guiaDetalleRepository.listGuiaDetalle(detalle);
				bean.getListado().addAll(new ArrayList<GuiaDetalle>());
				bean.getListado().addAll(listaDetalle);
				
				listaFinal.add(bean);				
			}
			
		}
	    	
		return listaFinal;
	}


	
	
	@Override
	public List<Guia> listGuiaMiViaje(Guia guia, String token) throws Exception {
		
		SessionTransportista session = new SessionTransportista();
		session.setToken(token);
		session = sessionTransportistaRepository.getSessionByToken(session);
		
		if(session==null) 
			throw new BooxException("5000","Error al obtener sessión de usuario");

		UsuarioTransporte usuarioTransporte = new UsuarioTransporte();
		usuarioTransporte.setEmail(session.getEmail());
		usuarioTransporte = usuarioTransporteRepository.getUsuarioTransporte(usuarioTransporte);	
			
		if(usuarioTransporte==null) 
			throw new BooxException("5000","Error al obtener datos de usuario");


		List<Guia> lista = null;
		List<Guia> listaFinal = null;
		List<GuiaDetalle> listaDetalle = null;
		GuiaDetalle detalle = null;
		
		lista = guiaRepository.listGuiaMiViaje(guia,usuarioTransporte.getCodigo());
		
		if(lista!=null && lista.size()>0)
		{
			listaDetalle = null;
			listaFinal = new ArrayList<Guia>();
			for(Guia bean: lista) {
				
				/*
				if(bean.getEstado()==5) {
					int cantidad = rutaRepository.cantidad(bean.getCodigo());
					Position position = null;
					logger.info("==>cantidad:"+cantidad);
					List<Location> recorrido = new ArrayList<Location>();
					List<Ruta> lstTotal = rutaRepository.listRuta(bean.getCodigo(), 0, cantidad);
					for(int i=0;i<lstTotal.size();i=i+1) {
						position = new Position();
						double lat = Double.parseDouble(lstTotal.get(i).getLat());
						double lng = Double.parseDouble(lstTotal.get(i).getLng());
						Location location = new Location(lat,lng);
						recorrido.add(location);
					}					
								
					List<Ruta> lstIni = new ArrayList();
					if(cantidad>0)
						lstIni = rutaRepository.listRuta(bean.getCodigo(), 0, 1);
					
					Ruta rutaIni = null;
					if(lstIni.size()>0)
						rutaIni = lstIni.get(0);

					List<Ruta> lstFin = new ArrayList();
					if(cantidad>0)
						lstFin = rutaRepository.listRuta(bean.getCodigo(), cantidad-1, cantidad);
					
					Ruta rutaFin = null;
					if(lstFin.size()>0)
						rutaFin = lstFin.get(0);

					//Ruta rutaIni = rutaRepository.listRuta(bean.getCodigo(), 0, 1).get(0);
					//Ruta rutaFin = rutaRepository.listRuta(bean.getCodigo(), cantidad-1, cantidad).get(0);
					
					List<Ruta> rutas = new ArrayList();
					if(cantidad>0)
						rutas = rutaRepository.listRuta(bean.getCodigo(), 1, cantidad-2);
					
					//List<Location> recorrido = new ArrayList<Location>();
					
					//for(int x=0;x<rutas.size();x++) {
					//	recorrido.add(rutas.get(x).getJson());
					//}
					int rango = rutas.size() / 5;
					int cont=0;
					
					List<Position> positions = new ArrayList();
					List<Location> locations = new ArrayList(); 
					
					if(rutas.size()<1000) {
						System.out.println("111rango:"+rango);
										
					for(int i=0;i<rutas.size();i=i+1) {
						cont++;
						//if(i%rango==0) {
							//if(cont<=20) {
								//recorrido.add(rutas.get(i).getJson());		
								position = new Position();
								double lat = Double.parseDouble(rutas.get(i).getLat());
								double lng = Double.parseDouble(rutas.get(i).getLng());
								
								Location location = new Location(lat,lng);
								locations.add(location);
								//recorrido.add(location);
								position.setLocation(location);
								positions.add(position);
							//}
							
						//}

					}
					
					} else {
						rango = rutas.size() / 22;
						rango= rango +1 ;
						System.out.println("222rango:"+rango);

						for(int i=0;i<rutas.size();i=i+rango) {
							cont++;
							//if(i%rango==0) {
								//if(cont<=20) {
									//recorrido.add(rutas.get(i).getJson());		
									position = new Position();
									double lat = Double.parseDouble(rutas.get(i).getLat());
									double lng = Double.parseDouble(rutas.get(i).getLng());
									Location location = new Location(lat,lng);
									locations.add(location);
									//recorrido.add(location);
									position.setLocation(location);
									positions.add(position);
								//}								
							//}

						}

					}
					
					bean.setLocation(locations);
					bean.setPosition(positions);
					bean.setRecorrido(recorrido);
					bean.setRutaOri(rutaIni);
					bean.setRutaDes(rutaFin);
					bean.setRutas(rutas);
					
					List<GuiaEstado> listaGuiaEstado = guiaEstadoRepository.listGuiaEstado(bean);
					if(listaGuiaEstado!=null) {
						if(listaGuiaEstado.size()>0) {
							GuiaEstado guiaEstado = listaGuiaEstado.get(listaGuiaEstado.size()-1);
							bean.setAlerta(guiaEstado.getNota());
						}
					}

				}
				*/
				
				/*
				Subasta subasta = new Subasta();
				subasta.setCodigoGuia(bean.getCodigo());
				subasta.setUsuarioTransporte(usuarioTransporte);
				
				subasta = subastaRepository.getSubasta(subasta);

				bean.setSubasta(subasta);
				*/
				
				
				/*
				detalle = new GuiaDetalle();
				detalle.setCodigoGuia(bean.getCodigo());
				listaDetalle = guiaDetalleRepository.listGuiaDetalle(detalle);
				bean.getListado().addAll(new ArrayList<GuiaDetalle>());
				bean.getListado().addAll(listaDetalle);
				*/
				listaFinal.add(bean);				
			}
			
		}
	    	
		return listaFinal;
	}

	
	@Override
	public List<Guia> listGuiaMiViajeV2(Guia guia, String token) throws Exception {
		
		SessionTransportista session = new SessionTransportista();
		session.setToken(token);
		session = sessionTransportistaRepository.getSessionByToken(session);
		
		if(session==null) 
			throw new BooxException("5000","Error al obtener sessión de usuario");

		UsuarioTransporte usuarioTransporte = new UsuarioTransporte();
		usuarioTransporte.setEmail(session.getEmail());
		usuarioTransporte = usuarioTransporteRepository.getUsuarioTransporte(usuarioTransporte);	
			
		if(usuarioTransporte==null) 
			throw new BooxException("5000","Error al obtener datos de usuario");

		
		List<Guia> lista = null;
		List<Guia> listaFinal = null;
		List<GuiaDetalle> listaDetalle = null;
		GuiaDetalle detalle = null;
		
		lista = guiaRepository.listGuiaMiViaje(guia,usuarioTransporte.getCodigo());
		
		if(lista!=null && lista.size()>0)
		{
			listaDetalle = null;
			listaFinal = new ArrayList<Guia>();
			for(Guia bean: lista) {
				
				bean.setRecorrido(null);
				bean.setRutaOri(null);
				bean.setRutaDes(null);

				if(bean.getEstado()==5) {
					int cantidad = rutaRepository.cantidad(bean.getCodigo());
					Position position = null;
					logger.info("==>cantidad:"+cantidad);
					List<Location> recorrido = new ArrayList<Location>();
					List<Ruta> lstTotal = rutaRepository.listRuta(bean.getCodigo(), 0, cantidad);
					for(int i=0;i<lstTotal.size();i=i+1) {
						position = new Position();
						double lat = Double.parseDouble(lstTotal.get(i).getLat());
						double lng = Double.parseDouble(lstTotal.get(i).getLng());
						Location location = new Location(lat,lng);
						recorrido.add(location);
					}					
								
					List<Ruta> lstIni = new ArrayList();
					if(cantidad>0)
						lstIni = rutaRepository.listRuta(bean.getCodigo(), 0, 1);
					
					Ruta rutaIni = null;
					if(lstIni.size()>0)
						rutaIni = lstIni.get(0);

					List<Ruta> lstFin = new ArrayList();
					if(cantidad>0)
						lstFin = rutaRepository.listRuta(bean.getCodigo(), cantidad-1, cantidad);
					
					Ruta rutaFin = null;
					if(lstFin.size()>0)
						rutaFin = lstFin.get(0);

					bean.setRecorrido(recorrido);
					bean.setRutaOri(rutaIni);
					bean.setRutaDes(rutaFin);

				}
				
				
				detalle = new GuiaDetalle();
				detalle.setCodigoGuia(bean.getCodigo());
				listaDetalle = guiaDetalleRepository.listGuiaDetalle(detalle);
				bean.getListado().addAll(new ArrayList<GuiaDetalle>());
				bean.getListado().addAll(listaDetalle);

				bean.setDireccionOrigen(bean.getDesViaTipoOrigen()+" "+bean.getViaNombreOrigen()+" "+bean.getNumeroOrigen()+" - "+bean.getDesDistritoOrigen()+" - "+bean.getDesProvinciaOrigen()+" - "+bean.getDesDepartamentoOrigen());
				bean.setDireccionDestino(bean.getDesViaTipoDestino()+" "+bean.getViaNombreDestino()+" "+bean.getNumeroDestino()+" - "+bean.getDesDistritoDestino()+" - "+bean.getDesProvinciaDestino()+" - "+bean.getDesDepartamentoDestino());
				
				bean.setLocation(null);
				bean.setPosition(null);
				bean.setRutas(null);
				bean.setSubasta(null);
				
				bean.setViaTipoOrigen(null);
				bean.setViaNombreOrigen(null);
				bean.setNumeroOrigen(null);
				bean.setInteriorOrigen(null);
				bean.setZonaOrigen(null);
				bean.setDistritoOrigen(null);
				bean.setProvinciaOrigen(null);
				bean.setDepartamentoOrigen(null);
				bean.setViaTipoDestino(null);
				bean.setViaNombreDestino(null);
				bean.setNumeroDestino(null);
				bean.setInteriorDestino(null);
				bean.setZonaDestino(null);
				bean.setDistritoDestino(null);
				bean.setProvinciaDestino(null);
				bean.setDepartamentoDestino(null);
				bean.setRazonSocialRemitente(null);
				bean.setRucRemitente(null);
				bean.setTipoDocRemitente(null);
				bean.setNumeroDocRemitente(null);
				bean.setNombreContactoRemitente(null);
				bean.setTelefonoContactoRemitente(null);
				bean.setRazonSocialDestinatario(null);
				bean.setRucDestinatario(null);
				bean.setTipoDocDestinatario(null);
				bean.setNumeroDocDestinatario(null);
				bean.setNombreContactoDestinatario(null);
				bean.setTelefonoContactoDestinatario(null);
				bean.setDesViaTipoOrigen(null);
				bean.setDesViaTipoDestino(null);
				bean.setRazonSocial(null);
				bean.setDesZonaOrigen(null);
				bean.setDesDistritoOrigen(null);
				bean.setDesProvinciaOrigen(null);
				bean.setDesDepartamentoOrigen(null);
				bean.setDesZonaDestino(null);
				bean.setDesDistritoDestino(null);
				bean.setDesProvinciaDestino(null);
				bean.setDepartamentoDestino(null);
				bean.setDesDepartamentoDestino(null);
				bean.setSubasta(null);				
				bean.setLstSubasta(null);
				bean.setRutas(null);
				bean.setPosition(null);
				bean.setLocation(null);

				
				listaFinal.add(bean);				
				
			}
		
				
		}		
				
				
		return listaFinal;
	}
	
	
	@Override
	public List<Guia> listChoferMiViaje(String token, Integer estado) throws Exception {
		
		SessionChofer session = new SessionChofer();
		session.setToken(token);
		session = sessionChoferRepository.getSessionByToken(session);
		
		if(session==null) 
			throw new BooxException("5000","Error al obtener sessión de usuario");

		Chofer chofer = new Chofer();
		chofer.setNumDocumento(session.getNumDocumento());
		chofer = choferRepository.getChoferByDocumento(chofer);	
			
		if(chofer==null) 
			throw new BooxException("5000","Error al obtener datos de chofer");

		
		List<Guia> lista = null;
		List<Guia> listaFinal = null;
		List<GuiaDetalle> listaDetalle = null;
		GuiaDetalle detalle = null;
		
		chofer.setEstado(estado);
		lista = guiaRepository.listViajesChofer(chofer);
		
		if(lista!=null && lista.size()>0)
		{
			listaDetalle = null;
			listaFinal = new ArrayList<Guia>();
			for(Guia bean: lista) {
				bean.setRecorrido(null);
				bean.setRutaOri(null);
				bean.setRutaDes(null);

				if(bean.getEstado()==5 || bean.getEstado()==6 || bean.getEstado()==7) {
					int cantidad = rutaRepository.cantidad(bean.getCodigo());
					Position position = null;
					logger.info("==>cantidad:"+cantidad);
					List<Location> recorrido = new ArrayList<Location>();
					List<Ruta> lstTotal = rutaRepository.listRuta(bean.getCodigo(), 0, cantidad);
					
					int limite = 50;
					
					if(cantidad<limite) {
						
					for(int i=0;i<lstTotal.size();i=i+1) {
						position = new Position();
						double lat = Double.parseDouble(lstTotal.get(i).getLat());
						double lng = Double.parseDouble(lstTotal.get(i).getLng());
						Location location = new Location(lat,lng);
						recorrido.add(location);
					}					
								
					} else {
						
						int rango = cantidad / limite;
						rango= rango +1 ;
						logger.info("==>rango:"+rango);

						for(int i=1;i<lstTotal.size();i=i+rango) {
							position = new Position();
							if(lstTotal.get(i)!=null) {
							double lat = Double.parseDouble(lstTotal.get(i).getLat());
							double lng = Double.parseDouble(lstTotal.get(i).getLng());
							Location location = new Location(lat,lng);
							recorrido.add(location);
							}
						}					
						
					}
					
					List<Ruta> lstIni = new ArrayList();
					if(cantidad>0)
						lstIni = rutaRepository.listRuta(bean.getCodigo(), 0, 1);
					
					Ruta rutaIni = null;
					if(lstIni.size()>0)
						rutaIni = lstIni.get(0);

					List<Ruta> lstFin = new ArrayList();
					if(cantidad>0)
						lstFin = rutaRepository.listRuta(bean.getCodigo(), cantidad-1, cantidad);
					
					Ruta rutaFin = null;
					if(lstFin.size()>0)
						rutaFin = lstFin.get(0);

					bean.setRecorrido(recorrido);
					bean.setRutaOri(rutaIni);
					bean.setRutaDes(rutaFin);
					bean.setAlerta(null);
					
					List<GuiaEstado> listaGuiaEstado = guiaEstadoRepository.listGuiaEstado(bean);
					if(listaGuiaEstado!=null) {
						if(listaGuiaEstado.size()>0) {
							GuiaEstado guiaEstado = listaGuiaEstado.get(listaGuiaEstado.size()-1);
							bean.setAlerta(guiaEstado.getNota());
						}
					}
					
					
				}
				
				
				if(bean.getEstado()==4 || bean.getEstado()==5 || bean.getEstado()==6 || bean.getEstado()==7) {
					//Etado 5:EnRuta
					VehiculoTransporte vehiculo = vehiculoTransporteRepository.getVehiculoByGuia(bean);
					
					if(vehiculo!=null) {
						vehiculo.setExttpropiedad(Util.getExtension(vehiculo.getTpropiedad()));
						vehiculo.setExtsoat(Util.getExtension(vehiculo.getSoat()));
						vehiculo.setTpropiedad(Util.getImageEncode64(vehiculo.getTpropiedad(),env));
						vehiculo.setSoat(Util.getImageEncode64(vehiculo.getSoat(),env));
						vehiculo.setExtfoto1(Util.getExtension(vehiculo.getFoto1()));
						vehiculo.setFoto1(Util.getImageEncode64(vehiculo.getFoto1(),env));

						vehiculo.setChofer(null);
						vehiculo.setListaTipoCarga(null);
						vehiculo.setTipoCarga(null);
						vehiculo.setUsuarioTransporte(null);
						bean.setVehiculo(vehiculo);
					}
					
				}

				detalle = new GuiaDetalle();
				detalle.setCodigoGuia(bean.getCodigo());
				listaDetalle = guiaDetalleRepository.listGuiaDetalle(detalle);
				bean.getListado().addAll(new ArrayList<GuiaDetalle>());
				bean.getListado().addAll(listaDetalle);
				bean.setListado(listaDetalle);

				//bean.setDireccionOrigen(bean.getDesViaTipoOrigen()+" "+bean.getViaNombreOrigen()+" "+bean.getNumeroOrigen()+" - "+bean.getDesDistritoOrigen()+" - "+bean.getDesProvinciaOrigen()+" - "+bean.getDesDepartamentoOrigen());
				//bean.setDireccionDestino(bean.getDesViaTipoDestino()+" "+bean.getViaNombreDestino()+" "+bean.getNumeroDestino()+" - "+bean.getDesDistritoDestino()+" - "+bean.getDesProvinciaDestino()+" - "+bean.getDesDepartamentoDestino());
				bean.setDireccionOrigen(bean.getDireccionOrigen()+" - "+bean.getDesDistritoOrigen()+" - "+bean.getDesProvinciaOrigen()+" - "+bean.getDesDepartamentoOrigen());
				bean.setDireccionDestino(bean.getDireccionDestino()+" - "+bean.getDesDistritoDestino()+" - "+bean.getDesProvinciaDestino()+" - "+bean.getDesDepartamentoDestino());
				
				bean.setLocation(null);
				bean.setPosition(null);
				bean.setRutas(null);
				bean.setSubasta(null);
				
				bean.setViaTipoOrigen(null);
				bean.setViaNombreOrigen(null);
				bean.setNumeroOrigen(null);
				bean.setInteriorOrigen(null);
				bean.setZonaOrigen(null);
				bean.setDistritoOrigen(null);
				bean.setProvinciaOrigen(null);
				bean.setDepartamentoOrigen(null);
				bean.setViaTipoDestino(null);
				bean.setViaNombreDestino(null);
				bean.setNumeroDestino(null);
				bean.setInteriorDestino(null);
				bean.setZonaDestino(null);
				bean.setDistritoDestino(null);
				bean.setProvinciaDestino(null);
				bean.setDepartamentoDestino(null);
				bean.setRazonSocialRemitente(null);
				bean.setRucRemitente(null);
				bean.setTipoDocRemitente(null);
				bean.setNumeroDocRemitente(null);
				bean.setNombreContactoRemitente(null);
				bean.setTelefonoContactoRemitente(null);
				bean.setRazonSocialDestinatario(null);
				bean.setRucDestinatario(null);
				bean.setTipoDocDestinatario(null);
				bean.setNumeroDocDestinatario(null);
				bean.setNombreContactoDestinatario(null);
				bean.setTelefonoContactoDestinatario(null);
				bean.setDesViaTipoOrigen(null);
				bean.setDesViaTipoDestino(null);


				
				//if(estado.intValue()!=4)					
				//bean.setRazonSocial(null);
				
				bean.setDesZonaOrigen(null);
				bean.setDesDistritoOrigen(null);
				bean.setDesProvinciaOrigen(null);
				bean.setDesDepartamentoOrigen(null);
				bean.setDesZonaDestino(null);
				bean.setDesDistritoDestino(null);
				bean.setDesProvinciaDestino(null);
				bean.setDepartamentoDestino(null);
				bean.setDesDepartamentoDestino(null);
				bean.setSubasta(null);				
				bean.setLstSubasta(null);
				bean.setRutas(null);
				bean.setPosition(null);
				bean.setLocation(null);

				logger.info("estad........"+estado);
				bean.setEstado(estado);
				
				listaFinal.add(bean);				
				
			}
		
				
		}		
				
				
		return listaFinal;
	}


	
	@Override
	public List<Guia> listChoferMiViajeV2(String token, Integer estado) throws Exception {
		logger.info("ini.service.listChoferMiViajeV2 ******************************************");

		SessionChofer session = new SessionChofer();
		session.setToken(token);
		logger.info("ini.getSessionByToken *********************");
		session = sessionChoferRepository.getSessionByToken(session);
		logger.info("fin.getSessionByToken *********************");
		
		if(session==null) 
			throw new BooxException("5000","Error al obtener sessión de usuario");

		Chofer chofer = new Chofer();
		chofer.setNumDocumento(session.getNumDocumento());
		logger.info("ini.getChoferByDocumento *********************");
		chofer = choferRepository.getChoferByDocumento(chofer);	
		logger.info("fin.getChoferByDocumento *********************");
			
		if(chofer==null) 
			throw new BooxException("5000","Error al obtener datos de chofer");

		
		List<Guia> lista = null;
		List<Guia> listaFinal = null;
		List<GuiaDetalle> listaDetalle = null;
		GuiaDetalle detalle = null;
		
		chofer.setEstado(estado);
		logger.info("ini.listViajesChofer *********************");
		lista = guiaRepository.listViajesChofer(chofer);
		logger.info("fin.listViajesChofer *********************");
		
		if(lista!=null && lista.size()>0)
		{
			listaDetalle = null;
			listaFinal = new ArrayList<Guia>();
			for(Guia bean: lista) {
				bean.setRecorrido(null);
				bean.setRutaOri(null);
				bean.setRutaDes(null);

				if(bean.getEstado()==5 || bean.getEstado()==6 || bean.getEstado()==7) {

					
					logger.info("URL_LIST_RUTA:::::"+URL_LIST_RUTA);
					logger.info("ini.aws.listCoordenadas *********************");
					List<Ruta> lstTotal = restService.listCoordenadas(URL_LIST_RUTA, bean);
					logger.info("fin.aws.listCoordenadas *********************");
					int cantidad = lstTotal.size();
					logger.info("==>cantidad:"+cantidad);

										
					Position position = null;
					List<Location> recorrido = new ArrayList<Location>();
					int limite = 50;
					
					if(cantidad<limite) {
						
					for(int i=0;i<lstTotal.size();i=i+1) {
						position = new Position();
						double lat = Double.parseDouble(lstTotal.get(i).getLat());
						double lng = Double.parseDouble(lstTotal.get(i).getLng());
						Location location = new Location(lat,lng);
						recorrido.add(location);
					}					
								
					} else {
						
						int rango = cantidad / limite;
						rango= rango +1 ;
						logger.info("==>rango:"+rango);

						for(int i=1;i<lstTotal.size();i=i+rango) {
							position = new Position();
							if(lstTotal.get(i)!=null) {
							double lat = Double.parseDouble(lstTotal.get(i).getLat());
							double lng = Double.parseDouble(lstTotal.get(i).getLng());
							Location location = new Location(lat,lng);
							recorrido.add(location);
							}
						}					
						
					}
					
					
					Ruta rutaIni = null;
					if(lstTotal.size()>0)
						rutaIni = lstTotal.get(0);
					
					Ruta rutaFin = null;
					if(lstTotal.size()>1)
						rutaFin = lstTotal.get(cantidad-1);

					bean.setRecorrido(recorrido);
					bean.setRutaOri(rutaIni);
					bean.setRutaDes(rutaFin);
					bean.setAlerta(null);
					
					logger.info("ini.listGuiaEstado *********************");
					List<GuiaEstado> listaGuiaEstado = guiaEstadoRepository.listGuiaEstado(bean);
					logger.info("fin.listGuiaEstado *********************");
					if(listaGuiaEstado!=null) {
						if(listaGuiaEstado.size()>0) {
							GuiaEstado guiaEstado = listaGuiaEstado.get(listaGuiaEstado.size()-1);
							bean.setAlerta(guiaEstado.getNota());
						}
					}
					
					
				}
				
				
				if(bean.getEstado()==4 || bean.getEstado()==5 || bean.getEstado()==6 || bean.getEstado()==7) {
					//Etado 5:EnRuta
					logger.info("ini.getVehiculoByGuia *********************");
					VehiculoTransporte vehiculo = vehiculoTransporteRepository.getVehiculoByGuia(bean);
					logger.info("fin.getVehiculoByGuia *********************");
					
					if(vehiculo!=null) {
						vehiculo.setExttpropiedad(Util.getExtension(vehiculo.getTpropiedad()));
						vehiculo.setExtsoat(Util.getExtension(vehiculo.getSoat()));
						vehiculo.setTpropiedad(Util.getImageEncode64(vehiculo.getTpropiedad(),env));
						vehiculo.setSoat(Util.getImageEncode64(vehiculo.getSoat(),env));
						vehiculo.setExtfoto1(Util.getExtension(vehiculo.getFoto1()));
						vehiculo.setFoto1(Util.getImageEncode64(vehiculo.getFoto1(),env));

						vehiculo.setChofer(null);
						vehiculo.setListaTipoCarga(null);
						vehiculo.setTipoCarga(null);
						vehiculo.setUsuarioTransporte(null);
						bean.setVehiculo(vehiculo);
					}
					
				}

				detalle = new GuiaDetalle();
				detalle.setCodigoGuia(bean.getCodigo());
				logger.info("ini.listGuiaDetalle *********************");
				listaDetalle = guiaDetalleRepository.listGuiaDetalle(detalle);
				logger.info("fin.listGuiaDetalle *********************");
				bean.getListado().addAll(new ArrayList<GuiaDetalle>());
				bean.getListado().addAll(listaDetalle);
				bean.setListado(listaDetalle);

				//bean.setDireccionOrigen(bean.getDesViaTipoOrigen()+" "+bean.getViaNombreOrigen()+" "+bean.getNumeroOrigen()+" - "+bean.getDesDistritoOrigen()+" - "+bean.getDesProvinciaOrigen()+" - "+bean.getDesDepartamentoOrigen());
				//bean.setDireccionDestino(bean.getDesViaTipoDestino()+" "+bean.getViaNombreDestino()+" "+bean.getNumeroDestino()+" - "+bean.getDesDistritoDestino()+" - "+bean.getDesProvinciaDestino()+" - "+bean.getDesDepartamentoDestino());
				bean.setDireccionOrigen(bean.getDireccionOrigen()+" - "+bean.getDesDistritoOrigen()+" - "+bean.getDesProvinciaOrigen()+" - "+bean.getDesDepartamentoOrigen());
				bean.setDireccionDestino(bean.getDireccionDestino()+" - "+bean.getDesDistritoDestino()+" - "+bean.getDesProvinciaDestino()+" - "+bean.getDesDepartamentoDestino());
				
				bean.setLocation(null);
				bean.setPosition(null);
				bean.setRutas(null);
				bean.setSubasta(null);
				
				bean.setViaTipoOrigen(null);
				bean.setViaNombreOrigen(null);
				bean.setNumeroOrigen(null);
				bean.setInteriorOrigen(null);
				bean.setZonaOrigen(null);
				bean.setDistritoOrigen(null);
				bean.setProvinciaOrigen(null);
				bean.setDepartamentoOrigen(null);
				bean.setViaTipoDestino(null);
				bean.setViaNombreDestino(null);
				bean.setNumeroDestino(null);
				bean.setInteriorDestino(null);
				bean.setZonaDestino(null);
				bean.setDistritoDestino(null);
				bean.setProvinciaDestino(null);
				bean.setDepartamentoDestino(null);
				bean.setRazonSocialRemitente(null);
				bean.setRucRemitente(null);
				bean.setTipoDocRemitente(null);
				bean.setNumeroDocRemitente(null);
				bean.setNombreContactoRemitente(null);
				bean.setTelefonoContactoRemitente(null);
				bean.setRazonSocialDestinatario(null);
				bean.setRucDestinatario(null);
				bean.setTipoDocDestinatario(null);
				bean.setNumeroDocDestinatario(null);
				bean.setNombreContactoDestinatario(null);
				bean.setTelefonoContactoDestinatario(null);
				bean.setDesViaTipoOrigen(null);
				bean.setDesViaTipoDestino(null);


				
				//if(estado.intValue()!=4)					
				//bean.setRazonSocial(null);
				
				bean.setDesZonaOrigen(null);
				bean.setDesDistritoOrigen(null);
				bean.setDesProvinciaOrigen(null);
				bean.setDesDepartamentoOrigen(null);
				bean.setDesZonaDestino(null);
				bean.setDesDistritoDestino(null);
				bean.setDesProvinciaDestino(null);
				bean.setDepartamentoDestino(null);
				bean.setDesDepartamentoDestino(null);
				bean.setSubasta(null);				
				bean.setLstSubasta(null);
				bean.setRutas(null);
				bean.setPosition(null);
				bean.setLocation(null);

				logger.info("estad........"+estado);
				bean.setEstado(estado);
				
				listaFinal.add(bean);				
				
			}
		
				
		}		
				
		logger.info("fin.service.listChoferMiViajeV2 ******************************************");		
		return listaFinal;
	}

	@Override
	public void iniciarRuta(Guia guia, String token) throws Exception {
		
		SessionChofer session = new SessionChofer();
		session.setToken(token);
		session = sessionChoferRepository.getSessionByToken(session);
		
		if(session==null) 
			throw new BooxException("5000","Error al obtener sessión de usuario");

		Chofer chofer = new Chofer();
		chofer.setNumDocumento(session.getNumDocumento());
		chofer = choferRepository.getChoferByDocumento(chofer);	
			
		if(chofer==null) 
			throw new BooxException("5000","Error al obtener datos de chofer");

		guia.setEstado(5); //Iniciar Ruta
		guiaRepository.updateEstadoGuia(guia);
		guiaEstadoRepository.insertaGuiaEstado(guia);

	}

	@Override
	public void updateEstadoRuta(Guia guia, String token, Integer estado) throws Exception {
		
		SessionChofer session = new SessionChofer();
		session.setToken(token);
		session = sessionChoferRepository.getSessionByToken(session);
		
		if(session==null) 
			throw new BooxException("5000","Error al obtener sessión de usuario");

		Chofer chofer = new Chofer();
		chofer.setNumDocumento(session.getNumDocumento());
		chofer = choferRepository.getChoferByDocumento(chofer);	
			
		if(chofer==null) 
			throw new BooxException("5000","Error al obtener datos de chofer");

		//(1:PENDIENTE,2:APROBADO,3:POSTULADO,4:ASIGNADO,5:EN RUTA, 6:EN PAUSA7:TERMINADO,8:CERRADO,9:CANCELADO)
		guia.setEstado(estado); 
		guiaRepository.updateEstadoGuia(guia);
		guiaEstadoRepository.insertaGuiaEstado(guia);

	}
	
	@Override
	public Integer getCantidadGuia(Guia guia) throws Exception {

		return guiaRepository.getCantidadGuia(guia);
	}
	
	@Override
	public List<Alertas> listAlerta(String token) throws Exception {
		
		Session session = new Session();
		session.setToken(token);
		session = sessionRepository.getSessionByToken(session);
		
		if(session==null) 
			throw new BooxException("5000","Error al obtener datos de sesión.");
		
		return guiaEstadoRepository.listAlerta(session.getRuc());

	}

	@Override
	public List<Alertas> listAlertaTransporte(String token) throws Exception {
		
		SessionTransportista session = new SessionTransportista();
		session.setToken(token);
		session = sessionTransportistaRepository.getSessionByToken(session);
		
		if(session==null) 
			throw new BooxException("5000","Error al obtener sessión de usuario");

		UsuarioTransporte usuarioTransporte = new UsuarioTransporte();
		usuarioTransporte.setEmail(session.getEmail());
		usuarioTransporte = usuarioTransporteRepository.getUsuarioTransporte(usuarioTransporte);	
			
		if(usuarioTransporte==null) 
			throw new BooxException("5000","Error al obtener datos de usuario");
		
		return guiaEstadoRepository.listAlertaTransporte(usuarioTransporte.getCodigo());

	}

	@Override
	public List<Guia> listMiGuiaViaje(Guia guia, String token) throws Exception {
		
		SessionTransportista session = new SessionTransportista();
		session.setToken(token);
		session = sessionTransportistaRepository.getSessionByToken(session);
		
		if(session==null) 
			throw new BooxException("5000","Error al obtener sessión de usuario");

		UsuarioTransporte usuarioTransporte = new UsuarioTransporte();
		usuarioTransporte.setEmail(session.getEmail());
		usuarioTransporte = usuarioTransporteRepository.getUsuarioTransporte(usuarioTransporte);	
			
		if(usuarioTransporte==null) 
			throw new BooxException("5000","Error al obtener datos de usuario");


		List<Guia> lista = null;
		lista = guiaRepository.listMiGuiaViajes(usuarioTransporte.getCodigo());

		/*
		if(lista!=null && lista.size()>0)
		{
			listaDetalle = null;
			listaFinal = new ArrayList<Guia>();
			for(Guia bean: lista) {
				detalle = new GuiaDetalle();
				detalle.setCodigoGuia(bean.getCodigo());
				listaDetalle = guiaDetalleRepository.listGuiaDetalle(detalle);
				bean.getListado().addAll(new ArrayList<GuiaDetalle>());
				bean.getListado().addAll(listaDetalle);
				
				listaFinal.add(bean);				
			}
			
		}
		return listaFinal;
		*/
		return lista;
	}

	
	
	
	@Override
	public List<Guia> listMisCargas(String token) throws Exception {
		
		Session session = new Session();
		session.setToken(token);
		session = sessionRepository.getSessionByToken(session);
		
		if(session==null) 
			throw new BooxException("5000","Error al obtener sessión de usuario");
		
		List<Guia> lista = null;
		List<Guia> listaFinal = null;
		List<GuiaDetalle> listaDetalle = null;
		GuiaDetalle detalle = null;
		
		lista = guiaRepository.listMisCargas(session.getRuc());
		
		if(lista!=null && lista.size()>0)
		{
			listaDetalle = null;
			listaFinal = new ArrayList<Guia>();
			for(Guia bean: lista) {
				
				/*
				if(bean.getEstado()==5) {
					int cantidad = rutaRepository.cantidad(bean.getCodigo());
					Position position = null;
					logger.info("==>cantidad:"+cantidad);
					List<Location> recorrido = new ArrayList<Location>();
					List<Ruta> lstTotal = rutaRepository.listRuta(bean.getCodigo(), 0, cantidad);
					for(int i=0;i<lstTotal.size();i=i+1) {
						position = new Position();
						double lat = Double.parseDouble(lstTotal.get(i).getLat());
						double lng = Double.parseDouble(lstTotal.get(i).getLng());
						Location location = new Location(lat,lng);
						recorrido.add(location);
					}					
								
					List<Ruta> lstIni = new ArrayList();
					if(cantidad>0)
						lstIni = rutaRepository.listRuta(bean.getCodigo(), 0, 1);
					
					Ruta rutaIni = null;
					if(lstIni.size()>0)
						rutaIni = lstIni.get(0);

					List<Ruta> lstFin = new ArrayList();
					if(cantidad>0)
						lstFin = rutaRepository.listRuta(bean.getCodigo(), cantidad-1, cantidad);
					
					Ruta rutaFin = null;
					if(lstFin.size()>0)
						rutaFin = lstFin.get(0);

					//Ruta rutaIni = rutaRepository.listRuta(bean.getCodigo(), 0, 1).get(0);
					//Ruta rutaFin = rutaRepository.listRuta(bean.getCodigo(), cantidad-1, cantidad).get(0);
					
					List<Ruta> rutas = new ArrayList();
					if(cantidad>0)
						rutas = rutaRepository.listRuta(bean.getCodigo(), 1, cantidad-2);
					
					//List<Location> recorrido = new ArrayList<Location>();
					
					//for(int x=0;x<rutas.size();x++) {
					//	recorrido.add(rutas.get(x).getJson());
					//}
					int rango = rutas.size() / 5;
					int cont=0;
					
					List<Position> positions = new ArrayList();
					List<Location> locations = new ArrayList(); 
					
					if(rutas.size()<1000) {
						System.out.println("111rango:"+rango);
										
					for(int i=0;i<rutas.size();i=i+1) {
						cont++;
						//if(i%rango==0) {
							//if(cont<=20) {
								//recorrido.add(rutas.get(i).getJson());		
								position = new Position();
								double lat = Double.parseDouble(rutas.get(i).getLat());
								double lng = Double.parseDouble(rutas.get(i).getLng());
								
								Location location = new Location(lat,lng);
								locations.add(location);
								//recorrido.add(location);
								position.setLocation(location);
								positions.add(position);
							//}
							
						//}

					}
					
					} else {
						rango = rutas.size() / 22;
						rango= rango +1 ;
						System.out.println("222rango:"+rango);

						for(int i=0;i<rutas.size();i=i+rango) {
							cont++;
							//if(i%rango==0) {
								//if(cont<=20) {
									//recorrido.add(rutas.get(i).getJson());		
									position = new Position();
									double lat = Double.parseDouble(rutas.get(i).getLat());
									double lng = Double.parseDouble(rutas.get(i).getLng());
									Location location = new Location(lat,lng);
									locations.add(location);
									//recorrido.add(location);
									position.setLocation(location);
									positions.add(position);
								//}								
							//}

						}

					}
					
					bean.setLocation(locations);
					bean.setPosition(positions);
					bean.setRecorrido(recorrido);
					bean.setRutaOri(rutaIni);
					bean.setRutaDes(rutaFin);
					bean.setRutas(rutas);
					
					List<GuiaEstado> listaGuiaEstado = guiaEstadoRepository.listGuiaEstado(bean);
					if(listaGuiaEstado!=null) {
						if(listaGuiaEstado.size()>0) {
							GuiaEstado guiaEstado = listaGuiaEstado.get(listaGuiaEstado.size()-1);
							bean.setAlerta(guiaEstado.getNota());
						}
					}

				}
				*/
				
				/*
				Subasta subasta = new Subasta();
				subasta.setCodigoGuia(bean.getCodigo());
				subasta.setUsuarioTransporte(usuarioTransporte);
				
				subasta = subastaRepository.getSubasta(subasta);

				bean.setSubasta(subasta);
				*/
				
				
				/*
				detalle = new GuiaDetalle();
				detalle.setCodigoGuia(bean.getCodigo());
				listaDetalle = guiaDetalleRepository.listGuiaDetalle(detalle);
				bean.getListado().addAll(new ArrayList<GuiaDetalle>());
				bean.getListado().addAll(listaDetalle);
				*/
				listaFinal.add(bean);				
			}
			
		}
	    	
		return listaFinal;
	}

	
	
	public Map<String, Object> rptGuiaMensual(String token) throws Exception {
		
		Session session = new Session();
		session.setToken(token);
		session = sessionRepository.getSessionByToken(session);
		Guia guia = new Guia();
		guia.setRuc(session.getRuc());
		
		Map<String, Object> mapa = new HashMap<>();
		List<String> lista = Util.getUltimosMeses(6);
		Map<String, Object> objeto = new HashMap<>();		

		int indice = 0;
		for(String valor: lista) {
			int cantidad = guiaRepository.getCantidadXPeriodo(guia,valor);
			indice++;
			objeto = new HashMap<>();		
			objeto.put("periodo",Util.getPeriodo(valor));
			objeto.put("cantidad",cantidad);
			String key = "mes".concat(String.valueOf(indice));
			mapa.put(key, objeto);			
		}
		
		
/*		
		Map<String, Object> objeto = new HashMap<>();		
		objeto.put("periodo","Julio 2017");
		objeto.put("cantidad","185");
		
		mapa.put("mes1", objeto);
		
		objeto = new HashMap<>();		
		objeto.put("periodo","Agosto 2017");
		objeto.put("cantidad","201");
		
		mapa.put("mes2", objeto);
		
		objeto = new HashMap<>();		
		objeto.put("periodo","Setiembre 2017");
		objeto.put("cantidad","303");
		
		mapa.put("mes3", objeto);

		objeto = new HashMap<>();		
		objeto.put("periodo","Octubre 2017");
		objeto.put("cantidad","436");
				
		mapa.put("mes4", objeto);

		objeto = new HashMap<>();		
		objeto.put("periodo","Noviembre 2017");
		objeto.put("cantidad","501");
				
		mapa.put("mes5", objeto);

		objeto = new HashMap<>();		
		objeto.put("periodo","Diciembre 2017");
		objeto.put("cantidad","659");
				
		mapa.put("mes6", objeto);
	*/	
		return mapa;
	}
	

	
	public List<Map<String, Object>> rptGuiaTIempos(String token) throws Exception {
		
		Session session = new Session();
		session.setToken(token);
		session = sessionRepository.getSessionByToken(session);
		Guia guia = new Guia();
		guia.setRuc(session.getRuc());
		
		List<Map<String, Object>> mapa = new ArrayList();
		mapa = guiaRepository.tiempoViaje(session.getRuc());
		
		return mapa;
	}
	

}
