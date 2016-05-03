package com.mrll.javelin.search.modal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class Hit implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected String index;
	protected String type;
	protected String id;
	
	protected float score;
	
	protected Collection<String> highlights = new ArrayList<String>();
	//protected Map<String, Object>  source;
	
	/**
	 * @return the index
	 */
	public String getIndex() {
		return index;
	}
	/**
	 * @param index the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the source
	 */
	public float getScore() {
		return score;
	}
	/**
	 * @param source the source to set
	 */
	public void setScore(float score) {
		this.score = score;
	}
	/**
	 * @return the highlights
	 */
	public Collection<String> getHighlights() {
		return highlights;
	}
	/**
	 * @param highlights the highlights to set
	 */
	public void setHighlights(Collection<String> highlights) {
		this.highlights = highlights;
	}
	
	
	
}
