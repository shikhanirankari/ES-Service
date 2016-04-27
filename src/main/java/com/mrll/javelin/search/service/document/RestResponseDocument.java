
package com.mrll.javelin.search.service.document;


import com.mrll.javelin.search.exception.RestAPIException;
import com.mrll.javelin.search.modal.Document;

public class RestResponseDocument extends RestResponse<Document> {
	private static final long serialVersionUID = 1L;

	public RestResponseDocument(Document doc) {
		super(doc);
	}

	public RestResponseDocument() {
		super();
	}

	public RestResponseDocument(RestAPIException e) {
		super();
	}
}
