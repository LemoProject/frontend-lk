/**
 * File ./main/java/de/lemo/dms/processing/resulttype/ResultListStringObject.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.processing.resulttype;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResultListStringObject {

	private List<String> elements;

	public ResultListStringObject()
	{

	}

	public ResultListStringObject(final List<String> elements)
	{
		this.elements = elements;
	}

	@XmlElement
	public List<String> getElements()
	{
		return this.elements;
	}

}
