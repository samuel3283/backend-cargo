package pe.com.boox.cargo.web.controller;

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

import pe.com.boox.cargo.model.BeanResponse;
import pe.com.boox.cargo.model.TransactionRs;
import pe.com.boox.cargo.model.UsuarioEmpresa;
import pe.com.boox.cargo.service.UsuarioEmpresaService;


@RestController
@RequestMapping("/rest")
public class UsuarioEmpresaController {

	private final Logger logger = LoggerFactory
			.getLogger(UsuarioEmpresaController.class);

	@Autowired
	UsuarioEmpresaService usuarioEmpresaService;
	
		@RequestMapping(value ="/service/empresa/insertUsuarioEmpresa", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<BeanResponse> insertUsuarioEmpresa(
				@RequestHeader HttpHeaders headers, @RequestBody UsuarioEmpresa request) {
			
			logger.info("insertUsuarioEmpresa.");
			TransactionRs<BeanResponse> response = new TransactionRs<BeanResponse>();

			try {
			logger.info("==>data:"+request.toString());	
			usuarioEmpresaService.insertUsuarioEmpresa(request);

			} catch (Exception e) {
				logger.error("Error insertUsuarioEmpresa. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}

		
}
