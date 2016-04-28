
package com.mrll.javelin.search.service.response;

import com.mrll.javelin.search.exception.RestAPIException;
import com.mrll.javelin.search.service.document.RestResponse;

public class RestResponseSearchResponse extends RestResponse<SearchResponse> {
	private static final long serialVersionUID = 1L;

	public RestResponseSearchResponse(SearchResponse doc) {
		super(doc);
	}

	public RestResponseSearchResponse() {
		super();
	}

	public RestResponseSearchResponse(RestAPIException e) {
		super(e);
	}
}
