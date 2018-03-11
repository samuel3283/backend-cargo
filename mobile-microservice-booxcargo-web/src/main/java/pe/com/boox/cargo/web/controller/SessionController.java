package pe.com.boox.cargo.web.controller;

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

import pe.com.boox.cargo.model.HeaderRq;
import pe.com.boox.cargo.model.Session;
import pe.com.boox.cargo.model.TransactionRs;
import pe.com.boox.cargo.model.UsuarioEmpresa;
import pe.com.boox.cargo.service.SessionService;
import pe.com.boox.cargo.service.UsuarioEmpresaService;
import pe.com.boox.cargo.service.core.HeaderRqUtil;


@RestController
@RequestMapping("/rest")
public class SessionController {

	@Autowired
	private SessionService sessionService;

	@Autowired
	private HeaderRqUtil headerRqUtil;

	private final Logger logger = LoggerFactory
			.getLogger(SessionController.class);


		@RequestMapping(value ="/service/genera", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs generaSms(
				@RequestHeader HttpHeaders headers, @RequestBody Usuario request) {
			
			logger.info("genera.............");
			TransactionRs response = new TransactionRs();
						
			return response;
		}

		
		@RequestMapping(value ="/service/validaSession", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<Session> validaSession(
				@RequestHeader HttpHeaders headers, @RequestBody UsuarioEmpresa request) {
			
			logger.info("validaSession.");
			TransactionRs<Session> response = new TransactionRs<Session>();
			
			try {
				HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
				
				response.setRespuesta(sessionService.validaSession(request,headerRq));
				
			} catch (Exception e) {
				logger.error("Error validaSession. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}
			
			return response;
		}


		@RequestMapping(value ="/service/guia/estadistica", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<Map<String, Object>> getEstadistica(
				@RequestHeader HttpHeaders headers, @RequestBody UsuarioEmpresa request) {
			
			TransactionRs<Map<String, Object>> response = new TransactionRs<Map<String, Object>>();			
			try {
				HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);				
				response.setRespuesta(sessionService.getEstadistica(headerRq));				
			} catch (Exception e) {
				logger.error("Error getEstadistica. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}			
			return response;
		}

		@RequestMapping(value ="/service/loginSession", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<Session> loginSession(
				@RequestHeader HttpHeaders headers, @RequestBody UsuarioEmpresa request) {
			
			logger.info("loginSession."+request.getEmail());
			TransactionRs<Session> response = new TransactionRs<Session>();
			
			try {
				HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
				
				response.setRespuesta(sessionService.loginDirect(request,headerRq));
				
			} catch (Exception e) {
				logger.error("Error loginSession. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}
			
			return response;
		}

		@RequestMapping(value ="/service/exitSession", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<Session> exitSession(
				@RequestHeader HttpHeaders headers) {
			
			logger.info("exitSession.");
			TransactionRs<Session> response = new TransactionRs<Session>();
			
			try {
				HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
				sessionService.deleteSession(headerRq.getToken());
				
			} catch (Exception e) {
				logger.error("Error validaSession. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion("Error Session not found");
			}
			
			return response;
		}

		
}
