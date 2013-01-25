/**
 * File ./main/java/de/lemo/dms/processing/resulttype/ResultListUserLogObject.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.processing.resulttype;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ResultListUserLogObject {

	private List<UserLogObject> userLogs;

	public ResultListUserLogObject()
	{

	}

	public ResultListUserLogObject(final List<UserLogObject> userLogs)
	{
		this.setUserLogs(userLogs);
	}

	@XmlElement
	public List<UserLogObject> getUserLogs() {
		return this.userLogs;
	}

	public void setUserLogs(final List<UserLogObject> userLogs) {
		this.userLogs = userLogs;
	}

}
