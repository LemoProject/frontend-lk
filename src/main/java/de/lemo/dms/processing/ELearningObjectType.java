/**
 * File ./main/java/de/lemo/dms/processing/ELearningObjectType.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.processing;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import org.apache.log4j.Logger;
import de.lemo.dms.db.miningDBclass.AssignmentLogMining;
import de.lemo.dms.db.miningDBclass.CourseLogMining;
import de.lemo.dms.db.miningDBclass.ForumLogMining;
import de.lemo.dms.db.miningDBclass.QuestionLogMining;
import de.lemo.dms.db.miningDBclass.QuizLogMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.ScormLogMining;
import de.lemo.dms.db.miningDBclass.WikiLogMining;
import de.lemo.dms.db.miningDBclass.abstractions.ILogMining;

/**
 * <em>Learning Object</em> types with corresponding {@link ILogMining} implementations.
 * 
 * @author Leonard Kappe
 */
public enum ELearningObjectType {

	ASSIGNMENT(AssignmentLogMining.class),
	COURSE(CourseLogMining.class),
	FORUM(ForumLogMining.class),
	QUESTION(QuestionLogMining.class),
	QUIZ(QuizLogMining.class),
	RESOURCE(ResourceLogMining.class),
	SCORM(ScormLogMining.class),
	WIKI(WikiLogMining.class),
	UNKNOWN(null);

	private static Logger logger = Logger.getLogger(ELearningObjectType.class);

	private Class<? extends ILogMining> type;

	private ELearningObjectType(final Class<? extends ILogMining> type) {
		this.type = type;
	}

	/**
	 * Gets the type that is used to store this Learning Object's log entries.
	 * 
	 * @return the type of the corresponding {@link ILogMining} implementation
	 */
	public Class<? extends ILogMining> getLogMiningType() {
		return this.type;
	}

	/**
	 * Gets enum constants from corresponding {@link ILogMining} classes.
	 * 
	 * @param log
	 *            an ILogMining implementation
	 * @return the corresponding enum constant
	 */
	public static ELearningObjectType fromLogMiningType(final ILogMining log) {
		for (final ELearningObjectType learnObjectType : ELearningObjectType.values()) {
			if (learnObjectType.getLogMiningType().equals(log)) {
				return learnObjectType;
			}
		}
		return UNKNOWN;
	}

	/**
	 * Gets a list of enum constants for the specified names .
	 * 
	 * @param names
	 *            case-insensitive names of enum constants to get
	 * @return the enum constants
	 */
	public static Set<ELearningObjectType> fromNames(final Collection<String> names) {
		final EnumSet<ELearningObjectType> result =
				EnumSet.noneOf(ELearningObjectType.class);
		for (final String name : names) {
			result.add(ELearningObjectType.valueOf(name.toUpperCase()));
		}
		if (result.remove(UNKNOWN)) {
			ELearningObjectType.logger.warn("Someone injected the string \"unknown\"!");
		}
		return result;
	}

	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
}