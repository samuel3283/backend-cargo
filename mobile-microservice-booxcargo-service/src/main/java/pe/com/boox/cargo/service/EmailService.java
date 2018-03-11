package pe.com.boox.cargo.service;

import pe.com.boox.cargo.model.Mail;

public interface EmailService {

	void sendMail(Mail mail) throws Exception;
	void sendMailTransporte(Mail mail) throws Exception;	 
	void sendMailTransporteOlvido(Mail mail) throws Exception;

}
