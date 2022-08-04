package com.jajodia.blog.exception;

public class ResourceNotFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4249433590339311269L;
	String resourceName;
	String fieldName;
	int fieldValue;
	
	
	public ResourceNotFoundException() {
		super();
	}


	public String getResourceName() {
		return resourceName;
	}


	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}


	public String getFieldName() {
		return fieldName;
	}


	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}


	public int getFieldValue() {
		return fieldValue;
	}


	public void setFieldValue(int fieldValue) {
		this.fieldValue = fieldValue;
	}


	public ResourceNotFoundException(String resourceName, String fieldName, int fieldValue) {
		super(String.format("%s not found with %s : %d",resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	

}
