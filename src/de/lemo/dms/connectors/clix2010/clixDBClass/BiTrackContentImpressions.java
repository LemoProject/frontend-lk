package de.lemo.dms.connectors.clix2010.clixDBClass;

public class BiTrackContentImpressions {
	
	private BiTrackContentImpressionsPK id;

	private long content;
	private String dayOfAccess;
	private long container;
	private long user;
	private long totalImpressions;
	private long characteristic;
	
	
	public BiTrackContentImpressionsPK getId() {
		return id;
	}

	public void setId(BiTrackContentImpressionsPK id) {
		this.id = id;
	}

	public long getContent() {
		return content;
	}

	public void setContent(long content) {
		this.content = content;
	}

	public long getCharacteristic() {
		return characteristic;
	}

	public void setCharacteristic(long characteristic) {
		this.characteristic = characteristic;
	}

	public BiTrackContentImpressions()
	{
		
	}

	public String getDayOfAccess() {
		return dayOfAccess;
	}

	public void setDayOfAccess(String dayOfAccess) {
		this.dayOfAccess = dayOfAccess;
	}

	public long getContainer() {
		return container;
	}

	public void setContainer(long container) {
		this.container = container;
	}

	public long getUser() {
		return user;
	}

	public void setUser(long user) {
		this.user = user;
	}

	public long getTotalImpressions() {
		return totalImpressions;
	}

	public void setTotalImpressions(long totalImpressions) {
		this.totalImpressions = totalImpressions;
	}
	
	

}
