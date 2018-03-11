package pe.com.boox.cargo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import pe.com.boox.cargo.model.Chofer;
import pe.com.boox.cargo.model.HeaderRq;

public interface ChoferService {
	
	int insertaChofer(Chofer request,String token) throws Exception;
	void insertChofer(Chofer request,String token) throws Exception;
	Chofer getChofer(Chofer request,String token) throws Exception;
	List<Chofer> listChofer(Chofer request,String token) throws Exception;
	int updateChofer(Chofer request,String token) throws Exception;
	void uploadFile(HeaderRq headerRq, MultipartFile uploadfile) throws Exception;
	
}
