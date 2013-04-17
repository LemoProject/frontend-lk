/**
 * File ./main/java/de/lemo/dms/connectors/moodleNumericId/moodleDBclass/Log_LMS.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 */

package de.lemo.dms.connectors.moodleNumericId.moodleDBclass;

/**
 * Mapping class for table Log.
 * 
 * @author S.Schwarzrock, B.Wolf
 *
 */
public class LogLMS {

	private long id;
	private long time;
	private long userid;
	private long course;
	private long cmid;
	private String module;
	private String action;
	private String info;

	public String getModule() {
		return this.module;
	}

	public void setModule(final String module) {
		this.module = module;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(final String action) {
		this.action = action;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(final String info) {
		this.info = info;
	}

	public long getId() {
		return this.id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public long getTime() {
		return this.time;
	}

	public void setTime(final long time) {
		this.time = time;
	}

	public long getUserid() {
		return this.userid;
	}

	public void setUserid(final long userid) {
		this.userid = userid;
	}

	public long getCourse() {
		return this.course;
	}

	public void setCourse(final long course) {
		this.course = course;
	}

	public void setCmid(final long cmid) {
		this.cmid = cmid;
	}

	public long getCmid() {
		return this.cmid;
	}
}
