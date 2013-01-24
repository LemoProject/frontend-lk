/**
 * File ./main/java/de/lemo/dms/service/servicecontainer/SCConnectors.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.service.servicecontainer;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * container for the REST/JSON communication to transfer the list of connectors
 * 
 * @author Boris Wenzlaff
 */
@XmlRootElement
public class SCConnectors {

	private List<String> connectors;

	public List<String> getConnectors() {
		return this.connectors;
	}

	public void setConnectors(final List<String> connectors) {
		this.connectors = connectors;
	}
}