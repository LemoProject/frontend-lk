/**
 * File ./main/java/de/lemo/dms/connectors/moodle_2_3/moodleDBclass/Forum_posts_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.moodle_2_3.moodleDBclass;

public class Forum_posts_LMS {

	private long id;
	private long userid;
	private long created;
	private long modified;
	private String subject;
	private String message;

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getUserid() {
		return this.userid;
	}

	public void setUserid(final long userid) {
		this.userid = userid;
	}

	public long getCreated() {
		return this.created;
	}

	public void setCreated(final long created) {
		this.created = created;
	}

	public long getModified() {
		return this.modified;
	}

	public void setModified(final long modified) {
		this.modified = modified;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(final String message) {
		this.message = message;
	}
}
