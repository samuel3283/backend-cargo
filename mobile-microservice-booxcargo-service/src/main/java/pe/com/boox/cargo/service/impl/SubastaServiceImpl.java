package pe.com.boox.cargo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import pe.com.boox.cargo.core.BooxException;
import pe.com.boox.cargo.dao.repository.ChoferRepository;
import pe.com.boox.cargo.dao.repository.GuiaDetalleRepository;
import pe.com.boox.cargo.dao.repository.GuiaRepository;
import pe.com.boox.cargo.dao.repository.SessionRepository;
import pe.com.boox.cargo.dao.repository.SessionTransportistaRepository;
import pe.com.boox.cargo.dao.repository.SubastaRepository;
import pe.com.boox.cargo.dao.repository.UsuarioTransporteRepository;
import pe.com.boox.cargo.dao.repository.VehiculoTransporteRepository;
import pe.com.boox.cargo.dao.repository.impl.GuiaDetalleRepositoryImpl;
import pe.com.boox.cargo.model.Chofer;
import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.GuiaDetalle;
import pe.com.boox.cargo.model.Session;
import pe.com.boox.cargo.model.SessionTransportista;
import pe.com.boox.cargo.model.Subasta;
import pe.com.boox.cargo.model.UsuarioTransporte;
import pe.com.boox.cargo.model.VehiculoTransporte;
import pe.com.boox.cargo.service.GuiaService;
import pe.com.boox.cargo.service.SubastaService;
import pe.com.boox.cargo.service.core.Constants;
import pe.com.boox.cargo.service.core.Util;
import scala.Array;

@Service
public class SubastaServiceImpl implements SubastaService {

	private final Logger logger = LoggerFactory.getLogger(GuiaService.class);

	@Autowired
	GuiaRepository guiaRepository;

	@Autowired
	GuiaDetalleRepository guiaDetalleRepository;

	@Autowired
	SessionRepository sessionRepository;

	@Autowired
	SessionTransportistaRepository sessionTransportistaRepository;

	@Autowired
	UsuarioTransporteRepository usuarioTransporteRepository;

	@Autowired
	SubastaRepository subastaRepository;

	@Autowired
	ChoferRepository choferRepository;

	@Autowired
	VehiculoTransporteRepository vehiculoTransporteRepository;

	@Autowired
	private Environment env;

	@Override
	public void deleteSubasta(Subasta request, String token) throws Exception {
		
		Subasta subasta = subastaRepository.getSubastaByCodigo(request);
		if(subasta==null) 
			throw new BooxException("5000","Error subasta not found");
		
		subastaRepository.deleteSubasta(request);
		logger.info("subasta........."+subasta.toString());
		Integer cantidad = subastaRepository.getCantidadSuabastasGuia(subasta);
		
		if(cantidad.intValue()<=0) {
			Guia guia = new Guia();
			guia.setCodigo(subasta.getCodigoGuia());			
			guia.setEstado(Constants.ESTADO_GUIA_APROBAOD);
			guiaRepository.updateEstadoGuia(guia);
		}

		
		
	}
	
	@Override
	public void updateSubasta(Subasta request, String token) throws Exception {
		logger.info("==>updateGuia==>"+request.toString());

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
		
		
		VehiculoTransporte vehiculo = new VehiculoTransporte();
		vehiculo.setUsuarioTransporte(usuarioTransporte);
		vehiculo.setPlaca(request.getPlaca());
		VehiculoTransporte vehiculoTransporte = vehiculoTransporteRepository.getVehiculoByUserAndPlaca(vehiculo);
		logger.info("getVehiculoByUserAndPlaca");
		Chofer chofer = null;
		if(vehiculoTransporte!=null) {			
			chofer = choferRepository.getChofer(vehiculoTransporte.getChofer());			
			logger.info("getChofer==>:"+chofer.toString());
		}
		
		logger.info("usuarioTransporte::"+usuarioTransporte.getCodigo());
		request.setUsuarioTransporte(new UsuarioTransporte());
		request.setUsuarioTransporte(usuarioTransporte);
		request.setBrevete(chofer.getBrevete());
		subastaRepository.insertSubasta(request);

		Guia guia = new Guia();
		guia.setCodigo(request.getCodigoGuia());
		guia.setEstado(Constants.ESTADO_GUIA_POSTULADO);
		guiaRepository.updateEstadoGuia(guia);
		
	}

	@Override
	public void asignaSubasta(Subasta request, String token) throws Exception {
		
		subastaRepository.asignaSubasta(request);
		
		Guia guia = new Guia();
		guia.setCodigo(request.getCodigoGuia());
		guia.setEstado(Constants.ESTADO_GUIA_ASIGNADO);
		guia.setCodigoChofer(request.getCodigoChofer());
		guiaRepository.updateAprobarGuia(guia);
		
	}
	
	@Override
	public List<Subasta> lstSubasta(Subasta request, String token) throws Exception {
		
		return subastaRepository.lstMiSubasta(request);
		
	}

	@Override
	public List<Subasta> lstMisSubasta(Subasta request, String token) throws Exception {
		
		List<Subasta> response = subastaRepository.lstSubastaAsg(request);
		List<Subasta> lista = new ArrayList<Subasta>();
		for(Subasta bean: response) {

			bean.getVehiculoTransporte().setExtfoto1(Util.getExtension(bean.getVehiculoTransporte().getFoto1()));
			bean.getVehiculoTransporte().setFoto1(Util.getImageEncode64(bean.getVehiculoTransporte().getFoto1(),env));
			
			bean.getVehiculoTransporte().getChofer().setExtBrevete(Util.getExtension(bean.getVehiculoTransporte().getChofer().getFotoBrevete()));
			bean.getVehiculoTransporte().getChofer().setFotoBrevete(Util.getImageEncode64(bean.getVehiculoTransporte().getChofer().getFotoBrevete(),env));
			
			lista.add(bean);
		}
		return lista;
		
	}

}
