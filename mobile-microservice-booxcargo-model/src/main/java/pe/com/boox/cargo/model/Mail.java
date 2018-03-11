package pe.com.boox.cargo.model;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class Mail implements Serializable{
	private String to;
	private String from;
	private String fromName;
	private String subject;
	private String template;
	private String message;
	private Map<String, String> attachments;
	private Map<String, Object> contents;
	private String cc;
	private boolean attached;

	public Mail() {
		this.to = StringUtils.EMPTY;
		this.from = StringUtils.EMPTY;
		this.fromName = StringUtils.EMPTY;
		this.subject = StringUtils.EMPTY;
		this.message = StringUtils.EMPTY;
		this.template = StringUtils.EMPTY;
		this.attachments = null;
		this.cc=StringUtils.EMPTY;
		this.attached=false;
	}

	public Map<String, String> getAttachments() {
		return attachments;
	}

	public void setAttachments(Map<String, String> attachments) {
		this.attachments = attachments;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Map<String, Object> getContents() {
		return contents;
	}

	public void setContents(Map<String, Object> contents) {
		this.contents = contents;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public boolean isAttached() {
		return attached;
	}

	public void setAttached(boolean attached) {
		this.attached = attached;
	}

	@Override
	public String toString() {
		return "Mail [to=" + to + ", from=" + from + ", fromName=" + fromName + ", subject=" + subject + ", template="
				+ template + ", message=" + message + ", attachments=" + attachments + ", contents=" + contents
				+ ", cc=" + cc + ", attached=" + attached + "]";
	}

	
}
