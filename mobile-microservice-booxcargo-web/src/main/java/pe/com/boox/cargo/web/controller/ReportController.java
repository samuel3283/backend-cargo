package pe.com.boox.cargo.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.jxls.template.SimpleExporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import pe.com.boox.cargo.model.BeanResponse;
import pe.com.boox.cargo.model.Guia;
import pe.com.boox.cargo.model.HeaderRq;
import pe.com.boox.cargo.model.Ruta;
import pe.com.boox.cargo.model.TransactionRs;
import pe.com.boox.cargo.service.GuiaService;
import pe.com.boox.cargo.service.core.HeaderRqUtil;
import pe.com.boox.cargo.service.impl.ExcelUtilsHelper;


@RestController
@RequestMapping("/rest")
public class ReportController {


	@Autowired
	private Environment env;
	  
	@Autowired
	private HeaderRqUtil headerRqUtil;

	private final Logger logger = LoggerFactory
			.getLogger(ReportController.class);

	@Autowired
	GuiaService gsuiaService;

	
	@RequestMapping(value="/report/guias", method= RequestMethod.GET )
    public void  rptGuias(HttpServletRequest request, HttpServletResponse response) throws Exception {

		logger.info("rptGuias...");
		List<Guia> listCourses =  gsuiaService.listGuiaUltimos(new Guia(), "");
        HashMap<String, Object> model = new HashMap<>();
        model.put("guias", listCourses);
        new ExcelUtilsHelper().renderMergedOutputModel(model, request, response);
    }
	/*
	  @RequestMapping(value = "/report/guias", method = RequestMethod.GET)
	  @ResponseBody 
	  public void export(HttpServletResponse response) {
				//List<Person> persons = personRepository.findAll();
				List<String> headers = Arrays.asList("First Name", "Last Name");
				try {
					response.addHeader("Content-disposition", "attachment; filename=People.xlsx");
					response.setContentType("application/vnd.ms-excel");
					new SimpleExporter().gridExport(headers, new ArrayList(), "firstName, lastName, ", response.getOutputStream());
					response.flushBuffer();
				} catch (IOException e) {
					logger.info(e.getMessage(), e);
				}	  
	    
	    //return new ResponseEntity<>(HttpStatus.OK);
	  } 
*/

	
	
	@RequestMapping(value ="/report/mensual", 
			method = RequestMethod.POST, produces = { "application/json" })
	@ResponseBody
	public TransactionRs<Map<String, Object>> reportMonth(
			@RequestHeader HttpHeaders headers, @RequestBody Guia request) {
		
		logger.info("reportMonth.");
		TransactionRs<Map<String, Object>> response = new TransactionRs<Map<String, Object>>();
		HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
		Map<String, Object> mapa = new HashMap<>();
		
		try {
			response.setRespuesta(gsuiaService.rptGuiaMensual(headerRq.getToken()));
			
		} catch (Exception e) {
			logger.error("Error reportMonth. ",e.getMessage());
			response.setCodigoError("5000");
			response.setDescripcion(e.getMessage());
			response.setRespuesta(null);
		}

		return response;
	}

	
	@RequestMapping(value ="/report/guia/tiempos", 
			method = RequestMethod.POST, produces = { "application/json" })
	@ResponseBody
	public TransactionRs<List<Map<String, Object>>> reortGuiaTiempos(
			@RequestHeader HttpHeaders headers, @RequestBody Guia request) {
		
		logger.info("reortGuiaTiempos.");
		TransactionRs<List<Map<String, Object>>> response = new TransactionRs<List<Map<String, Object>>>();
		HeaderRq headerRq = headerRqUtil.getHttpHeader(headers);
		Map<String, Object> mapa = new HashMap<>();
		
		try {
			response.setRespuesta(gsuiaService.rptGuiaTIempos(headerRq.getToken()));
			
		} catch (Exception e) {
			logger.error("Error reportMonth. ",e.getMessage());
			response.setCodigoError("5000");
			response.setDescripcion(e.getMessage());
			response.setRespuesta(null);
		}

		return response;
	}

}
