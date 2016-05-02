package com.mrll.javelin.search.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mrll.javelin.search.modal.Document;
import com.mrll.javelin.search.service.SearchService;
import com.mrll.javelin.search.service.document.DocumentService;
import com.mrll.javelin.search.service.response.RestResponseSearchResponse;
import com.mrll.javelin.search.service.response.SearchResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api("")
public class SearchController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

	private DocumentService documentService;

	@Autowired
	private SearchService searchService;

	@Autowired
	public SearchController(DocumentService service) {
		this.documentService = service;
	}

	@ApiOperation(value = "", notes = "index")
	@RequestMapping(value = "/javelin/index", method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<String> index(@RequestBody Document document) throws Exception {
		documentService.push(document);

		return new ResponseEntity<>(HttpStatus.OK);

	}

	@ApiOperation(value = "", notes = "search")
	@RequestMapping(value = "{/javelin/search", method = RequestMethod.GET)
	public ResponseEntity<RestResponseSearchResponse> search(@RequestParam final String term) throws Exception {
		SearchResponse results = null;
		try {
			results = searchService.searchIndex(term, 0, 10);
		} catch (Exception e) {
			// return new RestResponseSearchResponse(new RestAPIException(e));
		}

		RestResponseSearchResponse response = new RestResponseSearchResponse(results);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}