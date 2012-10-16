package de.lemo.dms.core;

import org.apache.log4j.Logger;

import de.lemo.dms.connectors.IConnector;

public class ConnectorGetDataWorkerThread extends Thread {
	private IConnector connector;
	private Logger logger = Logger.getLogger(getClass());
	
	public ConnectorGetDataWorkerThread(IConnector connector) {
		this.connector = connector;
	}
	
	public void run() {
		logger.info("connector start to load whole database");
		this.connector.getData("Moodle_1_9"); // XXX 
	}	
}
