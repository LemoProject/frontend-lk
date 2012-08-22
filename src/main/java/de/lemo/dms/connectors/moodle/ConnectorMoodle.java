package de.lemo.dms.connectors.moodle;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.core.ServerConfigurationHardCoded;
import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.IDBHandler;

public class ConnectorMoodle implements IConnector{

	private static DBConfigObject sourceDBConf;
 
	
	@Override
	public boolean testConnections() {
		try{
            Session session = HibernateUtil.getDynamicSourceDBFactoryMoodle(ServerConfigurationHardCoded.getInstance().getSourceDBConfig()).openSession();
            session.close();
       
            IDBHandler dbHandler = ServerConfigurationHardCoded.getInstance().getDBHandler();
            dbHandler.closeSession(dbHandler.getMiningSession());
		}catch(HibernateException he)
		{
			return false;
		}
		return true;
	}

	@Override
	public void getData() {
		ExtractAndMapMoodle extract = new ExtractAndMapMoodle();	
		String[] s = new String[1];
		s[0] = "ExtractAndMapMoodle";
		extract.start(s, sourceDBConf);		
	}

	@Override
	public void updateData(long fromTimestamp) {
		ExtractAndMapMoodle extract = new ExtractAndMapMoodle();	
		String[] s = new String[2];
		s[0] = "ExtractAndMapMoodle";
		s[1] = fromTimestamp+"";
		extract.start(s, sourceDBConf);
		
	}

	@Override
	public void setSourceDBConfig(DBConfigObject dbConf) {
		sourceDBConf = dbConf;
	}
}