package de.lemo.dms.connectors.moodle_2_3.moodleDBclass;

public class User_LMS {

	private long id;
	private long firstaccess;
	private long lastaccess;
	private long lastlogin;
	private long currentlogin;
	private long timemodified;
	private String username;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getFirstaccess() {
		return firstaccess;
	}
	public void setFirstaccess(long firstaccess) {
		this.firstaccess = firstaccess;
	}
	public long getLastaccess() {
		return lastaccess;
	}
	public void setLastaccess(long lastaccess) {
		this.lastaccess = lastaccess;
	}
	public long getLastlogin() {
		return lastlogin;
	}
	public void setLastlogin(long lastlogin) {
		this.lastlogin = lastlogin;
	}
	public long getCurrentlogin() {
		return currentlogin;
	}
	public void setCurrentlogin(long currentlogin) {
		this.currentlogin = currentlogin;
	}
	public long getTimemodified() {
		return timemodified;
	}
	public void setTimemodified(long timemodified) {
		this.timemodified = timemodified;
	}
}