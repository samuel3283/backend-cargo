package pe.com.boox.cargo.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import pe.com.boox.cargo.model.Alertas;
import pe.com.boox.cargo.model.BeanResponse;
import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.HeaderRq;
import pe.com.boox.cargo.model.Ruta;
import pe.com.boox.cargo.model.Subasta;
import pe.com.boox.cargo.model.TransactionRs;
import pe.com.boox.cargo.service.GuiaService;
import pe.com.boox.cargo.service.SubastaService;
import pe.com.boox.cargo.service.core.HeaderRqUtil;


@RestController
@RequestMapping("/rest")
public class GuiaController {

	private final Logger logger = LoggerFactory
			.getLogger(GuiaController.class);

	@Autowired
	GuiaService gsuiaService;

	@Autowired
	SubastaService subastaService;
	
	@Autowired
	private HeaderRqUtil headerRqUtil;

		@RequestMapping(value ="/service/ruta/insert", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<Map<String, Object>> insertRuta(
				@RequestHeader HttpHeaders headers, @RequestBody Ruta request) {
			
			logger.info("insertRuta.");
			TransactionRs<Map<String, Object>> response = new TransactionRs<Map<String, Object>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			Map<String, Object> mapa = new HashMap<>();
			
			try {
			logger.info("==>insertGuia==>Token:"+headerRq.getToken());	
			logger.info("==>data:"+request.toString());	

			gsuiaService.insertRuta(request,headerRq.getToken());
			
			} catch (Exception e) {
				logger.error("Error insertRuta. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}

		@RequestMapping(value ="/service/guia/insert", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<Map<String, Object>> insertGuia(
				@RequestHeader HttpHeaders headers, @RequestBody Guia request) {
			
			logger.info("insertGuia.");
			TransactionRs<Map<String, Object>> response = new TransactionRs<Map<String, Object>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			Map<String, Object> mapa = new HashMap<>();
			
			try {
			logger.info("==>insertGuia==>Token:"+headerRq.getToken());	
			logger.info("==>data:"+request.toString());	
			String idGuia = String.valueOf(gsuiaService.insertGuia(request,headerRq.getToken()));
			//throw new ServiceException(UrlConstants.MENSAJE_INVALID_RESPONSE);
			mapa.put("numGuia", idGuia);
			response.setRespuesta(mapa);
			
			} catch (Exception e) {
				logger.error("Error insertGuia. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}

		
		@RequestMapping(value ="/service/guia/update", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<Map<String, Object>> updateGuia(
				@RequestHeader HttpHeaders headers, @RequestBody Guia request) {
			
			logger.info("updateGuia.");
			TransactionRs<Map<String, Object>> response = new TransactionRs<Map<String, Object>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			Map<String, Object> mapa = new HashMap<>();
			
			try {
			logger.info("==>data:"+request.toString());	
			gsuiaService.updateGuia(request,headerRq.getToken());
			
			} catch (Exception e) {
				logger.error("Error updateGuia. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}

		@RequestMapping(value ="/service/guia/updateStatus", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<Map<String, Object>> updateStatus(
				@RequestHeader HttpHeaders headers, @RequestBody Guia request) {
			
			logger.info("updateStatus.");
			TransactionRs<Map<String, Object>> response = new TransactionRs<Map<String, Object>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			Map<String, Object> mapa = new HashMap<>();
			
			try {
			logger.info("==>data:"+request.toString());	
			gsuiaService.updateEstadoGuia(request,headerRq.getToken());
			
			} catch (Exception e) {
				logger.error("Error updateStatus. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}

		
		@RequestMapping(value ="/service/guia/postular", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<Map<String, Object>> postular(
				@RequestHeader HttpHeaders headers, @RequestBody Subasta request) {
			
			logger.info("postular.");
			TransactionRs<Map<String, Object>> response = new TransactionRs<Map<String, Object>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			Map<String, Object> mapa = new HashMap<>();
			
			try {
			logger.info("==>data:"+request.toString());	
			//codigoGuia   monto
			subastaService.updateSubasta(request, headerRq.getToken());
			
			} catch (Exception e) {
				logger.error("Error postular. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}


		@RequestMapping(value ="/service/guia/indicadorInicio", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<Map<String, Object>> indicadorInicio(
				@RequestHeader HttpHeaders headers, @RequestBody Guia request) {
			
			logger.info("indicadorInicio.");
			TransactionRs<Map<String, Object>> response = new TransactionRs<Map<String, Object>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			
			try {
				gsuiaService.updateIndicadorInicio(request, headerRq.getToken());
			
			} catch (Exception e) {
				logger.error("Error indicadorInicio. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}

		@RequestMapping(value ="/service/guia/indicadorFin", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<Map<String, Object>> indicadorFin(
				@RequestHeader HttpHeaders headers, @RequestBody Guia request) {
			
			logger.info("updateIndicadorFin.");
			TransactionRs<Map<String, Object>> response = new TransactionRs<Map<String, Object>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			
			try {
				gsuiaService.updateIndicadorFin(request, headerRq.getToken());
			
			} catch (Exception e) {
				logger.error("Error updateIndicadorFin. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}
		
		@RequestMapping(value ="/service/guia/cancelapostular", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<Map<String, Object>> cancelapostular(
				@RequestHeader HttpHeaders headers, @RequestBody Subasta request) {
			
			logger.info("cancelapostular.");
			TransactionRs<Map<String, Object>> response = new TransactionRs<Map<String, Object>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			Map<String, Object> mapa = new HashMap<>();
			
			try {
			logger.info("==>data:"+request.toString());	
			subastaService.deleteSubasta(request, headerRq.getToken());			
			} catch (Exception e) {
				logger.error("Error cancelapostular. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}

		
		@RequestMapping(value ="/service/guia/list", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody		
		public TransactionRs<List<Guia>> listGuia(
				@RequestHeader HttpHeaders headers, @RequestBody Guia request) {
			
			logger.info("listGuia.");
			TransactionRs<List<Guia>> response = new TransactionRs<List<Guia>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);

			try {
			logger.info("==>listGuia==>TOKEN:::"+headerRq.getToken());	
			logger.info("==>data:"+request.toString());	
			response.setRespuesta(gsuiaService.listGuia(request,headerRq.getToken()));
			
			} catch (Exception e) {
				logger.error("Error listGuia. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}

		@RequestMapping(value ="/service/guia/verify", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody		
		public TransactionRs<List<Guia>> verifyGuia(
				@RequestHeader HttpHeaders headers, @RequestBody Guia request) {
			
			logger.info("verifyGuia.");
			TransactionRs<List<Guia>> response = new TransactionRs<List<Guia>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);

			try {
			logger.info("==>verifyGuia==>TOKEN:::"+headerRq.getToken());	
			logger.info("==>data:"+request.toString());	
			response.setRespuesta(gsuiaService.listVerifyGuia(request,headerRq.getToken()));
			
			} catch (Exception e) {
				logger.error("Error verifyGuia. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}

		
		@RequestMapping(value ="/service/guia/miList", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody		
		public TransactionRs<List<Guia>> miList(
				@RequestHeader HttpHeaders headers, @RequestBody Guia request) {
			
			logger.info("listMiGuia.");
			TransactionRs<List<Guia>> response = new TransactionRs<List<Guia>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);

			try {
			logger.info("==>listMiGuia==>TOKEN:::"+headerRq.getToken());	
			logger.info("==>data:"+request.toString());	
			response.setRespuesta(gsuiaService.listMiGuia(request,headerRq.getToken()));
			
			} catch (Exception e) {
				logger.error("Error listGuia. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}

		@RequestMapping(value ="/service/guia/listSubasta", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody		
		public TransactionRs<List<Guia>> listSubasta(
				@RequestHeader HttpHeaders headers, @RequestBody Guia request) {
			
			logger.info("listSubasta.");
			TransactionRs<List<Guia>> response = new TransactionRs<List<Guia>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);

			try {
			logger.info("==>listSubasta==>TOKEN:::"+headerRq.getToken());	
			logger.info("==>data:"+request.toString());	
			response.setRespuesta(gsuiaService.listGuiaSubasta(request,headerRq.getToken()));
			
			} catch (Exception e) {
				logger.error("Error listSubasta. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}


		@RequestMapping(value ="/service/guia/get", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<Guia> getGuia(
				@RequestHeader HttpHeaders headers, @RequestBody Guia request) {
			
			logger.info("INI getGuia *******************************************************");
			TransactionRs<Guia> response = new TransactionRs<Guia>();

			try {
			logger.info("==>data:"+request.toString());	
			response.setRespuesta(gsuiaService.getGuiaV2(request));
			
			} catch (Exception e) {
				logger.error("Error getGuia. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			logger.info("FIN getGuia *******************************************************");
			return response;
		}

		@RequestMapping(value ="/service/V2/guia/get", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<Guia> getGuiaV2(
				@RequestHeader HttpHeaders headers, @RequestBody Guia request) {
			
			logger.info("getGuia.");
			TransactionRs<Guia> response = new TransactionRs<Guia>();

			try {
			logger.info("==>data:"+request.toString());	
			response.setRespuesta(gsuiaService.getGuia(request));
			
			} catch (Exception e) {
				logger.error("Error getGuia. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}


		@RequestMapping(value ="/service/guia/listUltima", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<List<Guia>> listUltima(
				@RequestHeader HttpHeaders headers, @RequestBody Guia request) {
			
			logger.info("listGuiaUltimos.");
			TransactionRs<List<Guia>> response = new TransactionRs<List<Guia>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);

			try {
			logger.info("==>listGuiaUltimos==>TOKEN:::"+headerRq.getToken());	
			logger.info("==>data:"+request.toString());	
			response.setRespuesta(gsuiaService.listGuiaUltimos(request,headerRq.getToken()));
			
			} catch (Exception e) {
				logger.error("Error listGuiaUltimos. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}		
		
		@RequestMapping(value ="/service/guia/listAll", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<List<Guia>> listAll(
				@RequestHeader HttpHeaders headers, @RequestBody Guia request) {
			
			logger.info("listAll.");
			TransactionRs<List<Guia>> response = new TransactionRs<List<Guia>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);

			try {
			logger.info("==>listAll==>TOKEN:::"+headerRq.getToken());	
			logger.info("==>data:"+request.toString());	
			response.setRespuesta(gsuiaService.listGuiaAll(request,headerRq.getToken()));
			
			} catch (Exception e) {
				logger.error("Error listAll. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}

		
		@RequestMapping(value ="/service/guia/listMiViaje", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<List<Guia>> listarMiViaje(
				@RequestHeader HttpHeaders headers, @RequestBody Guia request) {
			
			logger.info("listMiViaje.");
			TransactionRs<List<Guia>> response = new TransactionRs<List<Guia>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);

			try {
			logger.info("==>listMiViaje==>TOKEN:::"+headerRq.getToken());	
			logger.info("==>data:"+request.toString());	
			response.setRespuesta(gsuiaService.listGuiaMiViaje(request,headerRq.getToken()));
			
			} catch (Exception e) {
				logger.error("Error listMiViaje. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}

		@RequestMapping(value ="/service/v2/guia/listMiViaje", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<List<Guia>> listarMiViajeV2(
				@RequestHeader HttpHeaders headers, @RequestBody Guia request) {
			
			logger.info("listarMiViajeV2.");
			TransactionRs<List<Guia>> response = new TransactionRs<List<Guia>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);

			try {
			logger.info("==>listarMiViajeV2==>TOKEN:::"+headerRq.getToken());	
			logger.info("==>data:"+request.toString());	
			response.setRespuesta(gsuiaService.listGuiaMiViajeV2(request,headerRq.getToken()));
			
			} catch (Exception e) {
				logger.error("Error listMiViaje. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}

		
		@RequestMapping(value ="/service/guia/listMisCargas", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<List<Guia>> listMisCargas(
				@RequestHeader HttpHeaders headers, @RequestBody Guia request) {
			
			logger.info("listMisCargas.");
			TransactionRs<List<Guia>> response = new TransactionRs<List<Guia>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);

			try {
			response.setRespuesta(gsuiaService.listMisCargas(headerRq.getToken()));			
			} catch (Exception e) {
				logger.error("Error listMisCargas. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}

		
		@RequestMapping(value ="/service/chofer/listViajeAsignado", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<List<Guia>> listViajeAsignado(
				@RequestHeader HttpHeaders headers) {
			
			logger.info("listViajeAsignado.");
			TransactionRs<List<Guia>> response = new TransactionRs<List<Guia>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);

			try {
			logger.info("==>listViajeAsignado==>TOKEN:::"+headerRq.getToken());	
			response.setRespuesta(gsuiaService.listChoferMiViajeV2(headerRq.getToken(),4));
			
			} catch (Exception e) {
				logger.error("Error listarMiViajeChofer. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}

		@RequestMapping(value ="/service/chofer/listViajeEnRuta", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<List<Guia>> listViajeEnRuta(
				@RequestHeader HttpHeaders headers) {
			
			logger.info("INI listViajeEnRuta ******************************************");
			TransactionRs<List<Guia>> response = new TransactionRs<List<Guia>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);

			try {
			logger.info("==>listViajeEnRuta==>TOKEN:::"+headerRq.getToken());	
			response.setRespuesta(gsuiaService.listChoferMiViajeV2(headerRq.getToken(),5));
			
			} catch (Exception e) {
				logger.error("Error listViajeEnRuta. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			logger.info("FIN listViajeEnRuta ******************************************");
			return response;
		}

		@RequestMapping(value ="/service/chofer/listViajeEnPausa", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<List<Guia>> listViajeEnPausa(
				@RequestHeader HttpHeaders headers) {
			
			logger.info("listViajeEnPausa.");
			TransactionRs<List<Guia>> response = new TransactionRs<List<Guia>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);

			try {
			logger.info("==>listViajeEnPausa==>TOKEN:::"+headerRq.getToken());	
			response.setRespuesta(gsuiaService.listChoferMiViajeV2(headerRq.getToken(),6));
			
			} catch (Exception e) {
				logger.error("Error listViajeEnPausa. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}

		
		@RequestMapping(value ="/service/chofer/listViajeHistorial", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<List<Guia>> listViajeHistorial(
				@RequestHeader HttpHeaders headers) {
			
			logger.info("listViajeHistorial.");
			TransactionRs<List<Guia>> response = new TransactionRs<List<Guia>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);

			try {
			logger.info("==>listViajeHistorial==>TOKEN:::"+headerRq.getToken());	
			response.setRespuesta(gsuiaService.listChoferMiViajeV2(headerRq.getToken(),7));
			
			} catch (Exception e) {
				logger.error("Error listViajeHistorial. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}

		
		@RequestMapping(value ="/service/chofer/addCoordenada", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<Map<String, Object>> addCoordenada(
				@RequestHeader HttpHeaders headers, @RequestBody Ruta request) {
			
			logger.info("INI addCoordenada *******************************************************");
			TransactionRs<Map<String, Object>> response = new TransactionRs<Map<String, Object>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			Map<String, Object> mapa = new HashMap<>();
			
			try {
			logger.info("==>addCoordenada==>Token:"+headerRq.getToken());	
			logger.info("==>data:"+request.toString());	

			gsuiaService.addCoordenadasV2(request, headerRq.getToken());
			
			} catch (Exception e) {
				logger.error("Error addCoordenada. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			logger.info("FIN addCoordenada *******************************************************");
			return response;
		}

		
		@RequestMapping(value ="/service/chofer/V2/addCoordenada", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<Map<String, Object>> addCoordenadaV2(
				@RequestHeader HttpHeaders headers, @RequestBody Ruta request) {
			
			logger.info("addCoordenadaV2.");
			TransactionRs<Map<String, Object>> response = new TransactionRs<Map<String, Object>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			Map<String, Object> mapa = new HashMap<>();
			
			try {
			logger.info("==>addCoordenadaV2==>Token:"+headerRq.getToken());	
			logger.info("==>data:"+request.toString());	

			gsuiaService.addCoordenadas(request, headerRq.getToken());
			
			} catch (Exception e) {
				logger.error("Error addCoordenada. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}

		@RequestMapping(value ="/service/chofer/addCoordenadaOff", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<Map<String, Object>> addCoordenadaOff(
				@RequestHeader HttpHeaders headers, @RequestBody List<Ruta> request) {
			
			logger.info("addCoordenada.");
			TransactionRs<Map<String, Object>> response = new TransactionRs<Map<String, Object>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			Map<String, Object> mapa = new HashMap<>();
			
			try {
			logger.info("==>addCoordenada==>Token:"+headerRq.getToken());	
			logger.info("==>data:"+request.toString());	

			gsuiaService.addLstCoordenadas(request, headerRq.getToken());
			
			} catch (Exception e) {
				logger.error("Error addCoordenada. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}
		
		@RequestMapping(value ="/service/chofer/iniciaRuta", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<Map<String, Object>> iniciaRuta(
				@RequestHeader HttpHeaders headers, @RequestBody Guia request) {
			
			logger.info("iniciaRuta.");
			TransactionRs<Map<String, Object>> response = new TransactionRs<Map<String, Object>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			Map<String, Object> mapa = new HashMap<>();
			
			try {
			logger.info("==>data:"+request.toString());	
			gsuiaService.iniciarRuta(request,headerRq.getToken());
			
			} catch (Exception e) {
				logger.error("Error iniciaRuta. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}


		@RequestMapping(value ="/service/chofer/finalizarRuta", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<Map<String, Object>> finalizarRuta(
				@RequestHeader HttpHeaders headers, @RequestBody Guia request) {
			
			logger.info("finalizarRuta.");
			TransactionRs<Map<String, Object>> response = new TransactionRs<Map<String, Object>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			Map<String, Object> mapa = new HashMap<>();
			
			try {
			logger.info("==>data:"+request.toString());	
			gsuiaService.updateEstadoRuta(request,headerRq.getToken(),7);
			
			} catch (Exception e) {
				logger.error("Error finalizarRuta. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}

		@RequestMapping(value ="/service/chofer/pararRuta", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<Map<String, Object>> pararRuta(
				@RequestHeader HttpHeaders headers, @RequestBody Guia request) {
			
			logger.info("pararRuta.");
			TransactionRs<Map<String, Object>> response = new TransactionRs<Map<String, Object>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			Map<String, Object> mapa = new HashMap<>();
			
			try {
			logger.info("==>data:"+request.toString());	
			gsuiaService.updateEstadoRuta(request,headerRq.getToken(),6);
			
			} catch (Exception e) {
				logger.error("Error finalizarRuta. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}
		
		
		@RequestMapping(value ="/service/guia/listAlerta", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<List<Alertas>> listAlerta(
				@RequestHeader HttpHeaders headers, @RequestBody Guia request) {
			
			logger.info("listAlerta.");
			TransactionRs<List<Alertas>> response = new TransactionRs<List<Alertas>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);

			try {
			response.setRespuesta(gsuiaService.listAlerta(headerRq.getToken()));
			
			} catch (Exception e) {
				logger.error("Error listAlerta. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}		


		@RequestMapping(value ="/service/transporte/listAlerta", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<List<Alertas>> listAlertaTransporte(
				@RequestHeader HttpHeaders headers, @RequestBody Guia request) {
			
			logger.info("listAlertaTransporte.");
			TransactionRs<List<Alertas>> response = new TransactionRs<List<Alertas>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);

			try {
			response.setRespuesta(gsuiaService.listAlertaTransporte(headerRq.getToken()));
			
			} catch (Exception e) {
				logger.error("Error listAlertaTransporte. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}		


		@RequestMapping(value ="/service/transporte/miListViaje", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody		
		public TransactionRs<List<Guia>> miListViaje(
				@RequestHeader HttpHeaders headers, @RequestBody Guia request) {
			
			logger.info("miListViaje.");
			TransactionRs<List<Guia>> response = new TransactionRs<List<Guia>>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);

			try {
			logger.info("==>listMiGuia==>TOKEN:::"+headerRq.getToken());	
			logger.info("==>data:"+request.toString());	
			response.setRespuesta(gsuiaService.listMiGuiaViaje(request,headerRq.getToken()));
			
			} catch (Exception e) {
				logger.error("Error listGuia. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}

		
}
