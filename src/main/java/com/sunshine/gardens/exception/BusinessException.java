package com.sunshine.gardens.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * root exception of business operation layer.throw this exception when some exception occured.
 * 
 * @author Liuhaiing
 * @date 2011-10-12
 * 
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	/**
	 * error key data, message key and key argument.
	 * @author hxdhan
	 *
	 */
	public static class ErrorKey {
		//message key
		String key;
		//arguments
		Object[] args = new Object[]{};

		public Object[] getArgs() {
			return args;
		}

		public ErrorKey(String key) {
			this.key = key;
		}

		public ErrorKey(String key, Object... args) {
			this.key = key;
			this.args = args;
		}
		
		public String getKey(){
			return this.key;
		}
	}


	private ErrorKey errorKey;

	private List<ErrorKey> errorKeys = new ArrayList<ErrorKey>();
	
	
    //==========================================
	public BusinessException(ErrorKey errorKey) {
		super();
		this.errorKey = errorKey;
	}
	
	public BusinessException(String message, ErrorKey errorKey) {
		super(message);
		this.errorKey = errorKey;
	}

	public BusinessException(String message, List<ErrorKey> errorKeys) {
		super(message);
		this.errorKeys = errorKeys;
	}

	public BusinessException(String message, ErrorKey errorKey, List<ErrorKey> errorKeys) {
		this(message, errorKey);
		this.errorKeys = errorKeys;
	}

	public BusinessException(String message, ErrorKey errorKey, List<ErrorKey> errorKeys, Throwable cause) {
		super(message, cause);
		this.errorKey = errorKey;
		this.errorKeys = errorKeys;
	}

	public List<ErrorKey> getErrorKeys() {
		return errorKeys;
	}

	public ErrorKey getErrorKey() {
		return errorKey;
	}

	//==============================================
	public BusinessException() {
		super();
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

}
