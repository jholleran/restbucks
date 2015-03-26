@javax.xml.bind.annotation.XmlSchema(
		namespace = "http://schemas.restbucks.com", 
		elementFormDefault = XmlNsForm.QUALIFIED, 
		xmlns = { 
				@javax.xml.bind.annotation.XmlNs(prefix = "", namespaceURI = "http://schemas.restbucks.com"), 
				@javax.xml.bind.annotation.XmlNs(prefix = "dap", namespaceURI = "http://schemas.restbucks.com/dap") 
		}

)
package com.restbucks.domain;

import javax.xml.bind.annotation.*;