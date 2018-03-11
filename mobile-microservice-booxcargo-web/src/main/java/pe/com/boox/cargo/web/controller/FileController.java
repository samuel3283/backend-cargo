package pe.com.boox.cargo.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.List;

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
import pe.com.boox.cargo.model.TransactionRs;
import pe.com.boox.cargo.service.GuiaService;
import pe.com.boox.cargo.service.core.HeaderRqUtil;


@RestController
@RequestMapping("/rest")
public class FileController {

	@Autowired
	private Environment env;
	  
	private final Logger logger = LoggerFactory
			.getLogger(FileController.class);

	@Autowired
	GuiaService gsuiaService;

	
	  @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	  @ResponseBody
	  public ResponseEntity<?> uploadFile(
	      @RequestParam("uploadfile") MultipartFile uploadfile) {
	    
	    try {
	      // Get the filename and build the local file path
	      String filename = uploadfile.getOriginalFilename();
	      String directory = env.getProperty("netgloo.paths.uploadedFiles");
	      String filepath = Paths.get(directory, filename).toString();
	      
	      // Save the file locally
	      BufferedOutputStream stream =
	          new BufferedOutputStream(new FileOutputStream(new File(filepath)));
	      stream.write(uploadfile.getBytes());
	      stream.close();
	    }
	    catch (Exception e) {
	      System.out.println(e.getMessage());
	      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    }
	    
	    return new ResponseEntity<>(HttpStatus.OK);
	  } // method uploadFile

	  

	  
	  @RequestMapping(value = "/subirFile", method = RequestMethod.POST)
	  @ResponseBody
	  public TransactionRs<BeanResponse> uploadFileAll(
	      @RequestParam("uploadfile") MultipartFile uploadfile) {
	    
		logger.info("uploadFileAll.");
		TransactionRs<BeanResponse> response = new TransactionRs<BeanResponse>();
		//String uploadingdir = System.getProperty("user.dir") + "/uploadingdir/";

		try {
	      // Get the filename and build the local file path
	      String filename = uploadfile.getOriginalFilename();
	      String directory = env.getProperty("netgloo.paths.uploadedFiles");
	      String filepath = Paths.get(directory, filename).toString();
	      
	      // Save the file locally
	      BufferedOutputStream stream =
	          new BufferedOutputStream(new FileOutputStream(new File(filepath)));
	      stream.write(uploadfile.getBytes());
	      stream.close();
	    }
	    catch (Exception e) {
	      System.out.println(e.getMessage());
			logger.error("Error uploadFileAll. ",e.getMessage());
			response.setCodigoError("5000");
			response.setDescripcion(e.getMessage());
	    }
	    
	    return response;
	  } // method uploadFile


	  
	  @RequestMapping(value = "/verPath", method = RequestMethod.POST)
	  @ResponseBody
	  public TransactionRs<BeanResponse> verPath(BeanResponse request) {
	    
		logger.info("verPath.");
		TransactionRs<BeanResponse> response = new TransactionRs<BeanResponse>();
		//String uploadingdir = System.getProperty("user.dir") + "/uploadingdir/";
		
		try {
			String userDir = System.getProperty("user.dir");
			logger.info("userDir:"+userDir);
			String userHome = System.getProperty("user.home");
			logger.info("userHome:"+userHome);

			String filename ="prueba.txt";
			String filepath = Paths.get(userDir, filename).toString();
		    
			File dir = new File(userDir);
			logger.info("dir:"+dir.exists());
			File home = new File(userHome);
			logger.info("home:"+home.exists());
			File path = new File(filepath);
			logger.info("path:"+path.exists());
	      
	    }
	    catch (Exception e) {
	      System.out.println(e.getMessage());
			logger.error("Error verPath. ",e.getMessage());
			response.setCodigoError("5000");
			response.setDescripcion(e.getMessage());
	    }
	    
	    return response;
	  } // method uploadFile


}
