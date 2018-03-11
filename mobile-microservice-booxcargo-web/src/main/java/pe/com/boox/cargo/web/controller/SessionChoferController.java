package pe.com.boox.cargo.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import pe.com.boox.cargo.model.BeanResponse;
import pe.com.boox.cargo.model.Chofer;
import pe.com.boox.cargo.model.HeaderRq;
import pe.com.boox.cargo.model.ImageResponse;
import pe.com.boox.cargo.model.Session;
import pe.com.boox.cargo.model.SessionChofer;
import pe.com.boox.cargo.model.SessionTransportista;
import pe.com.boox.cargo.model.TransactionRs;
import pe.com.boox.cargo.model.UsuarioEmpresa;
import pe.com.boox.cargo.model.UsuarioTransporte;
import pe.com.boox.cargo.service.SessionChoferService;
import pe.com.boox.cargo.service.SessionTransportistaService;
import pe.com.boox.cargo.service.UsuarioEmpresaService;
import pe.com.boox.cargo.service.core.HeaderRqUtil;
import pe.com.boox.cargo.service.core.Util;


@RestController
@RequestMapping("/rest")
public class SessionChoferController {

	@Autowired
	private SessionChoferService sessionChoferService;

	@Autowired
	private HeaderRqUtil headerRqUtil;

	@Autowired
	private Environment env;

	private final Logger logger = LoggerFactory
			.getLogger(SessionChoferController.class);

		
		@RequestMapping(value ="/service/chofer/validaSession", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<SessionChofer> validaSessionChofer(
				@RequestHeader HttpHeaders headers, @RequestBody Chofer request) {
			
			logger.info("validaSessionChofer.");
			TransactionRs<SessionChofer> response = new TransactionRs<SessionChofer>();
			
			try {
				HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
				
				response.setRespuesta(sessionChoferService.validaSession(request,headerRq));
				
			} catch (Exception e) {
				logger.error("Error validaSessionChofer. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}
			
			return response;
		}


	
		
		@RequestMapping(value ="/service/chofer/exitSession", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<SessionTransportista> exitSessionChofer(
				@RequestHeader HttpHeaders headers) {
			
			logger.info("exitSessionChofer.");
			TransactionRs<SessionTransportista> response = new TransactionRs<SessionTransportista>();
			
			try {
				HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
				sessionChoferService.deleteSession(headerRq.getToken());
				
			} catch (Exception e) {
				logger.error("Error exitSessionChofer. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion("Error Session not found");
			}
			
			return response;
		}

		
		

		
		@RequestMapping(value ="/service/chofer/sendOlvido", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<BeanResponse> sendOlvidoChofer(
				@RequestHeader HttpHeaders headers, @RequestBody Chofer request) {
			
			logger.info("sendOlvido....");
			TransactionRs<BeanResponse> response = new TransactionRs<BeanResponse>();
			
			try {
				HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
				
				if(request.getEmail()==null){
					throw new Exception("Error not found");
				}
				
				BeanResponse respuesta = new BeanResponse();
				respuesta.setValor(sessionChoferService.generaCodeEmail(request));
				response.setRespuesta(respuesta);
				
			} catch (Exception e) {
				logger.error("Error sendOlvido. ", e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}
			
			return response;
		}
		
		
		@RequestMapping(value ="/service/chofer/updatePasswordOlvido", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs updatePasswordOlvido(
				@RequestHeader HttpHeaders headers, @RequestBody Chofer request) {
			
			logger.info("updatePasswordOlvido...."+request.toString());
			TransactionRs response = new TransactionRs();
			
			try {
				HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
							
				sessionChoferService.upddateClaveChofer(request);
				
			} catch (Exception e) {
				logger.error("Error updatePassword. ", e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}
			
			return response;
		}

		
		@RequestMapping(value ="/service/chofer/updatePassword", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs updatePasswordChofer(
				@RequestHeader HttpHeaders headers, @RequestBody Chofer request) {
			
			logger.info("updatePassword...."+request.toString());
			TransactionRs response = new TransactionRs();
			
			try {
				HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
							
				sessionChoferService.upddateClaveChofer(request);
				
			} catch (Exception e) {
				logger.error("Error updatePassword. ", e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}
			
			return response;
		}

}
