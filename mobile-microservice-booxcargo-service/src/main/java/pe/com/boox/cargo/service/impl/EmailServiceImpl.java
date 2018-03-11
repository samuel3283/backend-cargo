package pe.com.boox.cargo.service.impl;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import pe.com.boox.cargo.model.Mail;
import pe.com.boox.cargo.service.EmailService;


@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private VelocityEngine velocityEngine;

    @Value("${mail.from}")
    private String from;

    @Value("${mail.fromName}")
    private String fromName;

    private final Logger logger = LoggerFactory
			.getLogger(EmailServiceImpl.class);
	
    @Override
    public void sendMail(Mail mail) throws Exception {
    	logger.info("sendMail....");
    	mail.setAttachments(null);   	
    	//mail.setAttachments(getTemplateImages());
    	mail.setFrom(from);
		mail.setFromName(fromName);
		
    	this.sendConfirmationEmail(mail);
    	logger.info("sendMail fin....");

    }
    
    @Override
    public void sendMailTransporte(Mail mail) throws Exception {
    	logger.info("sendMailTransporte....");
    	mail.setAttachments(null);   	
    	//mail.setAttachments(getTemplateImages());
    	mail.setFrom(from);
		mail.setFromName(fromName);
		
    	this.sendConfirmationEmailTransporte(mail);
    	logger.info("sendConfirmationEmailTransporte fin....");

    }

    @Override
    public void sendMailTransporteOlvido(Mail mail) throws Exception {
    	logger.info("sendMailTransporteOlvido....");
    	mail.setAttachments(null);   	
    	//mail.setAttachments(getTemplateImages());
    	mail.setFrom(from);
		mail.setFromName(fromName);
		
    	this.sendConfirmationEmailTransporteOlvido(mail);
    	logger.info("sendConfirmationEmailTransporteOlvido fin....");

    }
    
    private Map<String, String> getTemplateImages(){
		Map<String, String> images = new HashMap<String, String>();
		String imagesPath = "/Apps/taxiboox/img/logo.png";
		imagesPath = "images/logo.png";
		
		InitialContext ctx;
		//context.xml
		//	<Environment name="pathlogo" value="C:/images/" type="java.lang.String"/>
		try {
		    //ctx = new InitialContext();
		    //imagesPath = (String) ctx.lookup("java:comp/env/pathlogo");
		    logger.info("ruta pathlogo:::"+imagesPath);
			images.put("logo", imagesPath);
		   }
		catch (Exception e) {
			images = null;
			//imagesPath = "/Apps/taxiboox/img/logo.png";
			logger.info("error var ent::::"+e.getMessage());
		}
		//images.put("logo", imagesPath + File.pathSeparator + "logo.png");
		return images ;
	}

    private void sendConfirmationEmail(final Mail mail) throws Exception {
    	
		MimeMessage message = this.javaMailSender.createMimeMessage();
		try {
			logger.info("createMimeMessage....................");
			MimeMessageHelper helper = new MimeMessageHelper(message, true,
					"UTF-8");
			if (StringUtils.isEmpty(mail.getFromName())) {
				helper.setFrom(mail.getFrom());
			} else {
				helper.setFrom(mail.getFrom(), mail.getFromName());
			}
			helper.setSubject(mail.getSubject());
			//helper.setText(mail.getMessage(), true);
			helper.setTo(mail.getTo());	
			if(mail.getCc()!=null && !mail.getCc().isEmpty()){				
				String[] copias= mail.getCc().split(",");
				for (String copia : copias) {
					helper.addCc(copia);
				}
			}
			logger.info("templates/velocity/....................");

			helper.setText(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine
                    , "templates/velocity/registro.vm", "UTF-8", mail.getContents()), true);
			
			if (mail.getAttachments() != null && !mail.getAttachments().isEmpty()) {
				addInLinesMessage(helper, mail);
			}
			logger.info("javaMailSender.........");

			this.javaMailSender.send(message);		
		} catch (Exception e) {
			logger.info("javaMailSender==>"+e.getMessage());
			throw new Exception(e.getMessage(), e);
		}


    }

    private void sendConfirmationEmailTransporte(final Mail mail) throws Exception {
    	
		MimeMessage message = this.javaMailSender.createMimeMessage();
		try {
			logger.info("createMimeMessage....................");
			MimeMessageHelper helper = new MimeMessageHelper(message, true,
					"UTF-8");
			if (StringUtils.isEmpty(mail.getFromName())) {
				helper.setFrom(mail.getFrom());
			} else {
				helper.setFrom(mail.getFrom(), mail.getFromName());
			}
			helper.setSubject(mail.getSubject());
			//helper.setText(mail.getMessage(), true);
			helper.setTo(mail.getTo());	
			if(mail.getCc()!=null && !mail.getCc().isEmpty()){				
				String[] copias= mail.getCc().split(",");
				for (String copia : copias) {
					helper.addCc(copia);
				}
			}
			logger.info("templates/velocity/....................");

			helper.setText(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine
                    , "templates/velocity/tregistro.vm", "UTF-8", mail.getContents()), true);
			
			if (mail.getAttachments() != null && !mail.getAttachments().isEmpty()) {
				addInLinesMessage(helper, mail);
			}
			logger.info("javaMailSender.........");

			this.javaMailSender.send(message);		
		} catch (Exception e) {
			logger.info("javaMailSender==>"+e.getMessage());
			throw new Exception(e.getMessage(), e);
		}


    }

    private void sendConfirmationEmailTransporteOlvido(final Mail mail) throws Exception {
    	
		MimeMessage message = this.javaMailSender.createMimeMessage();
		try {
			logger.info("createMimeMessage....................");
			MimeMessageHelper helper = new MimeMessageHelper(message, true,
					"UTF-8");
			if (StringUtils.isEmpty(mail.getFromName())) {
				helper.setFrom(mail.getFrom());
			} else {
				helper.setFrom(mail.getFrom(), mail.getFromName());
			}
			helper.setSubject(mail.getSubject());
			//helper.setText(mail.getMessage(), true);
			helper.setTo(mail.getTo());	
			if(mail.getCc()!=null && !mail.getCc().isEmpty()){				
				String[] copias= mail.getCc().split(",");
				for (String copia : copias) {
					helper.addCc(copia);
				}
			}
			logger.info("templates/velocity/....................");

			helper.setText(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine
                    , "templates/velocity/tolvido.vm", "UTF-8", mail.getContents()), true);
			
			if (mail.getAttachments() != null && !mail.getAttachments().isEmpty()) {
				addInLinesMessage(helper, mail);
			}
			logger.info("javaMailSender.........");

			this.javaMailSender.send(message);		
		} catch (Exception e) {
			logger.info("javaMailSender==>"+e.getMessage());
			throw new Exception(e.getMessage(), e);
		}


    }
	/*
    private void sendConfirmationEmail(final Mail mail) {
        MimeMessagePreparator preparator = new MimeMessagePreparator() {
            @Override
            public void prepare(MimeMessage mimeMessage) throws Exception {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
                message.setFrom("backend@bancamovil.local");
                message.setTo(mail.getTo());
                message.setSubject(mail.getSubject());

                //Map model = new HashMap<>();
    			if (mail.getAttachments() != null && !mail.getAttachments().isEmpty()) {
    				addInLinesMessage(message, mail);
    			}
    			
                message.setText(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine
                        , "templates/velocity/registro.vm", "UTF-8", mail.getContents()), true);
            }
        };

        this.javaMailSender.send(preparator);
    }*/

    
    
	private void addInLinesMessage(MimeMessageHelper helper, Mail params)
			throws Exception {
		Iterator<String> it = params.getAttachments().keySet().iterator();
		while (it.hasNext()) {
			String key = it.next();
			try {
				FileSystemResource attachment = new FileSystemResource(
						new File(params.getAttachments().get(key)));
				helper.addInline(key, attachment);
			} catch (MessagingException e) {
				logger.info("ERROR_MAIL_RESOURCE_NOT_FOUND==>"+e.getMessage());
				throw new Exception(
						"ERROR_MAIL_RESOURCE_NOT_FOUND",e);
			}
		}
	}
}
