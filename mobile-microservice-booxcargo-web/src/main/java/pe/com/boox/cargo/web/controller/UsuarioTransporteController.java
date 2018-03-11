package pe.com.boox.cargo.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pe.com.boox.cargo.model.BeanResponse;
import pe.com.boox.cargo.model.HeaderRq;
import pe.com.boox.cargo.model.TransactionRs;
import pe.com.boox.cargo.model.UsuarioEmpresa;
import pe.com.boox.cargo.model.UsuarioTransporte;
import pe.com.boox.cargo.service.UsuarioEmpresaService;
import pe.com.boox.cargo.service.UsuarioTransporteService;
import pe.com.boox.cargo.service.core.HeaderRqUtil;


@RestController
@RequestMapping("/rest")
public class UsuarioTransporteController {

	private final Logger logger = LoggerFactory
			.getLogger(UsuarioTransporteController.class);

	@Autowired
	UsuarioTransporteService usuarioTransporteService;
	
	@Autowired
	private HeaderRqUtil headerRqUtil;


		@RequestMapping(value ="/service/transporte/insertUsuario", 
				method = RequestMethod.POST, produces = { "application/json" })
		@ResponseBody
		public TransactionRs<BeanResponse> insertUsuario(
				@RequestHeader HttpHeaders headers, @RequestBody UsuarioTransporte request) {
			
			logger.info("insertUsuario.");
			TransactionRs<BeanResponse> response = new TransactionRs<BeanResponse>();

			try {
			logger.info("==>data:"+request.toString());	
			usuarioTransporteService.insertUsuarioTransporte(request);

			} catch (Exception e) {
				logger.error("Error insertUsuario. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion(e.getMessage());
			}

			return response;
		}

	
		
		@RequestMapping(value = "/service/transporte/uploadPhotoProfile", method = RequestMethod.POST)
		  @ResponseBody
		  public TransactionRs<BeanResponse> uploadPhotoProfile(
				  @RequestHeader HttpHeaders headers,
				  @RequestParam("photo") MultipartFile photo
				  ) {
			
			logger.info("uploadPhotoProfile");
			TransactionRs<BeanResponse> response = new TransactionRs<BeanResponse>();
			HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
			logger.info("uploadSoap==>codigo:"+headerRq.getCodigo());
			
		    try {
		    	
		    	usuarioTransporteService.uploadFile("FOTO", headerRq, photo);

		    } catch (Exception e) {
				logger.error("Error uploadSoap. ",e.getMessage());
				response.setCodigoError("5000");
				response.setDescripcion("Error al guardar. "+e.getMessage());
		    }
		    
		    return response;
		  }

		
}
