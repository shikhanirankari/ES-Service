
package com.mrll.javelin.search.service.document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.mrll.javelin.search.exception.RestAPIException;



public class RestResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean ok;
	private String errors[];
	private T object;

	/**
	 * Default constructor : we suppose here that ok = true
	 */
	public RestResponse() {
		this.ok = true;
	}
	
	/**
	 * We can get an object back, so there is no error and ok=true
	 * @param object
	 */
	public RestResponse(T object) {
		this.object = object;
		this.ok = true;
	}


	/**
	 * We build a response when we have errors
	 * @param errors
	 */
	public RestResponse(String[] errors) {
		this.errors = errors;
		this.ok = false;
	}

	/**
	 * We build a response when we have a single exception
	 * @param e
	 */
	public RestResponse(RestAPIException e) {
		addError(e);
	}

	public boolean isOk() {
		return ok;
	}

	public void setOk(boolean ok) {
		this.ok = ok;
	}

	public String[] getErrors() {
		return errors;
	}

	public void setErrors(String[] errors) {
		this.errors = errors;
	}

	public Object getObject() {
		return object;
	}
	
	public void setObject(T object) {
		this.object = object;
	}
	
	/**
	 * Add an error to the error list
	 * @param e
	 */
	public void addError(RestAPIException e) {
		this.ok = false;
		Collection<String> errs = new ArrayList<String>();
		if (this.errors != null) Collections.addAll(errs, this.errors);
		errs.add(e.getMessage());
		this.errors = (String[])errs.toArray(new String[errs.size()]);
	}
}
