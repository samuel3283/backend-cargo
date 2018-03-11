package pe.com.boox.cargo.core;

import org.springframework.http.HttpStatus;

public class BooxException extends Exception{
	private static final long serialVersionUID = 3880261424308084607L;
	
	/** Error code used to look up the error essage. */
	protected String exceptionCode = null;	
	/** Unique exception id associated with each Exception. */
	protected String exceptionId = null;
	
	protected HttpStatus httpStatus;
	
	/**
     * Constructor with a simple message
     *
     * @param message exception description
     */
    public BooxException(String message) {
        super(message);
        setExceptionId(CodeGeneratorUtil.generateCode());
    }

    /**
     * Exception with a nested exception
     *
     * @param message description of this exception
     * @param cause exception causing this exception
     */
    public BooxException(String message, Throwable cause) {
        super(message, cause);
        this.setExceptionId(CodeGeneratorUtil.generateCode());
    }
    
    /**
     * Exception with a nested exception
     *
     * @param message description of this exception
     * @param cause exception causing this exception
     */
    public BooxException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.exceptionId = CodeGeneratorUtil.generateCode();
        this.exceptionCode = errorCode;
    }
    
    public BooxException(String errorCode, String message) {
        super(message);
        this.exceptionId = CodeGeneratorUtil.generateCode();
        this.exceptionCode = errorCode;
    }
    
   	public BooxException(Throwable cause) {
		super(cause);
		setExceptionId(CodeGeneratorUtil.generateCode());
	}
   	
   	public BooxException(HttpStatus httpStatus) {		
   		this.httpStatus = HttpStatus.NOT_FOUND;
	}
   	
   	/**
     * @see SfException#fetchRootCause()
     */
    public Throwable fetchRootCause() {
        return ExceptionUtil.fetchRootCause(this);
    }

    /**
     * @see SfException#fetchRootCauseMessage()
     */
    public String fetchRootCauseMessage() {
        return fetchRootCause().getMessage();
    }
    
    public String toString(Throwable aThrowable) {
		return ExceptionUtil.toString(aThrowable);
	}
   	
    public String getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	public String getExceptionId() {
		return exceptionId;
	}

	public void setExceptionId(String exceptionId) {
		this.exceptionId = exceptionId;
	}
	
	}
