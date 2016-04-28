
package com.mrll.javelin.search.service.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.mrll.javelin.search.modal.Hit;

public class SearchResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected long took;
	protected long totalHits;
	protected List<Hit> hits = new ArrayList<Hit>();

	public SearchResponse() {
	}
	
	
	/**
	 * @param took
	 * @param totalHits
	 * @param hits
	 */
	public SearchResponse(long took, long totalHits, List<Hit> hits) {
		super();
		this.took = took;
		this.totalHits = totalHits;
		this.hits = hits;
	}

	/**
	 * @return the took
	 */
	public long getTook() {
		return took;
	}

	/**
	 * @param took
	 *            the took to set
	 */
	public void setTook(long took) {
		this.took = took;
	}

	/**
	 * @return the totalHits
	 */
	public long getTotalHits() {
		return totalHits;
	}

	/**
	 * @param totalHits
	 *            the totalHits to set
	 */
	public void setTotalHits(long totalHits) {
		this.totalHits = totalHits;
	}

	/**
	 * @return the hits
	 */
	public List<Hit> getHits() {
		return hits;
	}

	/**
	 * @param hits
	 *            the hits to set
	 */
	public void setHits(List<Hit> hits) {
		this.hits = hits;
	}

}
