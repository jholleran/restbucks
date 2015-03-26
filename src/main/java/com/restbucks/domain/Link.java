package com.restbucks.domain;

import javax.xml.bind.annotation.*;

@XmlRootElement(name="link", namespace="http://schemas.restbucks.com/dap")
public class Link {

    private String mediaType;

    private String uri;
    
    private String rel;

    public Link() {
	}
	
    public Link(String uri, String mediaType, String rel) {
    	this.uri = uri;
    	this.mediaType = mediaType;
    	this.rel = rel;
    }
    
	/**
	 * @return the mediaType
	 */
	public String getMediaType() {
		return mediaType;
	}

	/**
	 * @param mediaType the mediaType to set
	 */
	@XmlAttribute
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param uri the uri to set
	 */
	@XmlAttribute
	public void setUri(String uri) {
		this.uri = uri;
	}

	/**
	 * @return the rel
	 */
	public String getRel() {
		return rel;
	}

	/**
	 * @param rel the rel to set
	 */
	@XmlAttribute
	public void setRel(String rel) {
		this.rel = rel;
	}
    
}
