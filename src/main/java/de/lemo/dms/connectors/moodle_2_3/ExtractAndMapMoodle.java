/**
 * File ./main/java/de/lemo/dms/connectors/moodle_2_3/ExtractAndMapMoodle.java
 * Date 2013-01-24
 * Project Lemo Learning Analytics
 * Copyright TODO (INSERT COPYRIGHT)
 */

package de.lemo.dms.connectors.moodle_2_3;

// import miningDBclass.Config_mining;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import de.lemo.dms.connectors.Encoder;
import de.lemo.dms.connectors.IConnector;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Assign_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Assign_Plugin_Config_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Assignment_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Assignment_submissions_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.ChatLog_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Chat_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Context_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.CourseCategories_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Course_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Course_Modules_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Enrol_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Forum_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Forum_discussions_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Forum_posts_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Grade_grades_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Grade_items_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Groups_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Groups_members_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Log_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Modules_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Question_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Question_states_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Quiz_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Quiz_attempts_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Quiz_grades_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Quiz_question_instances_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Resource_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Role_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Role_assignments_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Scorm_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.User_Enrolments_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.User_LMS;
import de.lemo.dms.connectors.moodle_2_3.moodleDBclass.Wiki_LMS;
import de.lemo.dms.db.DBConfigObject;
import de.lemo.dms.db.miningDBclass.AssignmentLogMining;
import de.lemo.dms.db.miningDBclass.AssignmentMining;
import de.lemo.dms.db.miningDBclass.ChatLogMining;
import de.lemo.dms.db.miningDBclass.ChatMining;
import de.lemo.dms.db.miningDBclass.CourseAssignmentMining;
import de.lemo.dms.db.miningDBclass.CourseForumMining;
import de.lemo.dms.db.miningDBclass.CourseGroupMining;
import de.lemo.dms.db.miningDBclass.CourseLogMining;
import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.db.miningDBclass.CourseQuizMining;
import de.lemo.dms.db.miningDBclass.CourseResourceMining;
import de.lemo.dms.db.miningDBclass.CourseScormMining;
import de.lemo.dms.db.miningDBclass.CourseUserMining;
import de.lemo.dms.db.miningDBclass.CourseWikiMining;
import de.lemo.dms.db.miningDBclass.ForumLogMining;
import de.lemo.dms.db.miningDBclass.ForumMining;
import de.lemo.dms.db.miningDBclass.GroupMining;
import de.lemo.dms.db.miningDBclass.GroupUserMining;
import de.lemo.dms.db.miningDBclass.LevelAssociationMining;
import de.lemo.dms.db.miningDBclass.LevelCourseMining;
import de.lemo.dms.db.miningDBclass.LevelMining;
import de.lemo.dms.db.miningDBclass.QuestionLogMining;
import de.lemo.dms.db.miningDBclass.QuestionMining;
import de.lemo.dms.db.miningDBclass.QuizLogMining;
import de.lemo.dms.db.miningDBclass.QuizMining;
import de.lemo.dms.db.miningDBclass.QuizQuestionMining;
import de.lemo.dms.db.miningDBclass.QuizUserMining;
import de.lemo.dms.db.miningDBclass.ResourceLogMining;
import de.lemo.dms.db.miningDBclass.ResourceMining;
import de.lemo.dms.db.miningDBclass.RoleMining;
import de.lemo.dms.db.miningDBclass.ScormLogMining;
import de.lemo.dms.db.miningDBclass.ScormMining;
import de.lemo.dms.db.miningDBclass.UserMining;
import de.lemo.dms.db.miningDBclass.WikiLogMining;
import de.lemo.dms.db.miningDBclass.WikiMining;

/**
 * The main class of the extraction process.
 * Implementation of the abstract extract class for the LMS Moodle.
 */
public class ExtractAndMapMoodle extends ExtractAndMap {// Versionsnummer in Namen einf�gen

	// LMS tables instances lists
	/** The log_lms. */
	private List<Log_LMS> logLms;
	private List<Resource_LMS> resourceLms;
	private List<Course_LMS> courseLms;
	private List<Forum_LMS> forumLms;
	private List<Wiki_LMS> wikiLms;
	private List<User_LMS> userLms;
	private List<Quiz_LMS> quizLms;
	private List<Quiz_question_instances_LMS> quizQuestionInstancesLms;
	private List<Question_LMS> questionLms;
	private List<Groups_LMS> groupLms;
	private List<Groups_members_LMS> groupMembersLms;
	private List<Question_states_LMS> questionStatesLms;
	private List<Forum_posts_LMS> forumPostsLms;
	private List<Role_LMS> roleLms;
	private List<Context_LMS> contextLms;
	private List<Role_assignments_LMS> roleAssignmentsLms;
	private List<Assignment_LMS> assignmentLms;
	private List<Assignment_submissions_LMS> assignmentSubmissionLms;
	private List<Quiz_grades_LMS> quizGradesLms;
	private List<Forum_discussions_LMS> forumDiscussionsLms;
	private List<Scorm_LMS> scormLms;
	private List<Grade_grades_LMS> gradeGradesLms;
	private List<Grade_items_LMS> gradeItemsLms;
	private List<Chat_LMS> chatLms;
	private List<ChatLog_LMS> chatLogLms;
	private List<CourseCategories_LMS> courseCategoriesLms;
	private List<Assign_LMS> assignLms;
	private List<Assign_Plugin_Config_LMS> assignPluginConfigLms;
	private List<Enrol_LMS> enrolLms;
	private List<User_Enrolments_LMS> userEnrolmentsLms;
	private List<Modules_LMS> modulesLms;
	private List<Course_Modules_LMS> courseModulesLms;
	private List<Quiz_attempts_LMS> quizAttemptsLms;

	private final Logger logger = Logger.getLogger(this.getClass());

	private final IConnector connector;

	public ExtractAndMapMoodle(final IConnector connector) {
		super(connector);
		this.connector = connector;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void getLMStables(final DBConfigObject dbConfig, final long readingfromtimestamp) {

		// accessing DB by creating a session and a transaction using HibernateUtil
		final Session session = HibernateUtil.getSessionFactory(dbConfig).openSession();
		session.clear();
		final Transaction tx = session.beginTransaction();

		// Just for testing. has to be set to Long.MaxValue if not longer needed.
		final long ceiling = Long.MAX_VALUE;

		// reading the LMS Database, create tables as lists of instances of the DB-table classes

		final Criteria critty = session.createCriteria(Assign_LMS.class);
		this.assignLms = critty.list();
		System.out.println("Assign tables: " + this.assignLms.size());

		final Query assign = session.createQuery("from Assign_LMS order by id asc");
		this.assignLms = assign.list();
		System.out.println("Assign tables: " + this.assignLms.size());

		final Query enrol = session.createQuery("from Enrol_LMS x order by x.id asc");
		this.enrolLms = enrol.list();
		System.out.println("Enrol tables: " + this.enrolLms.size());

		final Query assignPC = session.createQuery("from Assign_Plugin_Config_LMS x order by x.id asc");
		this.assignPluginConfigLms = assignPC.list();
		System.out.println("Assign_Plugin_Config tables: " + this.assignPluginConfigLms.size());

		final Query modules = session.createQuery("from Modules_LMS x order by x.id asc");
		this.modulesLms = modules.list();
		System.out.println("Modules tables: " + this.modulesLms.size());

		final Query userEnrol = session.createQuery("from User_Enrolments_LMS x order by x.id asc");
		this.userEnrolmentsLms = userEnrol.list();
		System.out.println("User_Enrolments tables: " + this.userEnrolmentsLms.size());

		final Query coursMod = session.createQuery("from Course_Modules_LMS x order by x.id asc");
		this.courseModulesLms = coursMod.list();
		System.out.println("Course_Modules tables: " + this.courseModulesLms.size());

		final Query log = session
				.createQuery("from Log_LMS x where x.time>=:readingtimestamp and x.time<=:ceiling order by x.id asc");
		log.setParameter("readingtimestamp", readingfromtimestamp);
		log.setParameter("ceiling", ceiling);
		this.logLms = log.list();
		System.out.println("log_lms tables: " + this.logLms.size());

		final Query resource = session
				.createQuery("from Resource_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		resource.setParameter("readingtimestamp", readingfromtimestamp);
		resource.setParameter("ceiling", ceiling);
		this.resourceLms = resource.list();
		System.out.println("resource_lms tables: " + this.resourceLms.size());

		final Query quiz_attempts = session
				.createQuery("from Quiz_attempts_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		quiz_attempts.setParameter("readingtimestamp", readingfromtimestamp);
		quiz_attempts.setParameter("ceiling", ceiling);
		this.quizAttemptsLms = quiz_attempts.list();
		System.out.println("quiz_attempts_lms tables: " + this.quizAttemptsLms.size());

		final Query chat = session
				.createQuery("from Chat_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		chat.setParameter("readingtimestamp", readingfromtimestamp);
		chat.setParameter("ceiling", ceiling);
		this.chatLms = chat.list();
		System.out.println("chat_lms tables: " + this.chatLms.size());

		final Query chatlog = session
				.createQuery("from ChatLog_LMS x where x.timestamp>=:readingtimestamp and x.timestamp<=:ceiling order by x.id asc");
		chatlog.setParameter("readingtimestamp", readingfromtimestamp);
		chatlog.setParameter("ceiling", ceiling);
		this.chatLogLms = chatlog.list();
		System.out.println("chat_log_lms tables: " + this.chatLogLms.size());

		final Query courseCategories = session
				.createQuery("from CourseCategories_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		courseCategories.setParameter("readingtimestamp", readingfromtimestamp);
		courseCategories.setParameter("ceiling", ceiling);
		this.courseCategoriesLms = courseCategories.list();
		System.out.println("course_categories_lms tables: " + this.courseCategoriesLms.size());

		final Query course = session
				.createQuery("from Course_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		course.setParameter("readingtimestamp", readingfromtimestamp);
		course.setParameter("ceiling", ceiling);
		this.courseLms = course.list();
		System.out.println("course_lms tables: " + this.courseLms.size());

		final Query forum_posts = session
				.createQuery("from Forum_posts_LMS x where x.modified>=:readingtimestamp and x.modified<=:ceiling order by x.id asc");
		forum_posts.setParameter("readingtimestamp", readingfromtimestamp);
		forum_posts.setParameter("ceiling", ceiling);
		this.forumPostsLms = forum_posts.list();
		System.out.println("forum_posts_lms tables: " + this.forumPostsLms.size());

		final Query forum = session
				.createQuery("from Forum_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		forum.setParameter("readingtimestamp", readingfromtimestamp);
		forum.setParameter("ceiling", ceiling);
		this.forumLms = forum.list();
		System.out.println("forum_lms tables: " + this.forumLms.size());

		final Query group = session
				.createQuery("from Groups_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		group.setParameter("readingtimestamp", readingfromtimestamp);
		group.setParameter("ceiling", ceiling);
		this.groupLms = group.list();
		System.out.println("group_lms tables: " + this.groupLms.size());

		final Query quiz = session
				.createQuery("from Quiz_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		quiz.setParameter("readingtimestamp", readingfromtimestamp);
		quiz.setParameter("ceiling", ceiling);
		this.quizLms = quiz.list();
		System.out.println("quiz_lms tables: " + this.quizLms.size());

		final Query wiki = session
				.createQuery("from Wiki_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		wiki.setParameter("readingtimestamp", readingfromtimestamp);
		wiki.setParameter("ceiling", ceiling);
		this.wikiLms = wiki.list();
		System.out.println("wiki_lms tables: " + this.wikiLms.size());

		final Query group_members = session
				.createQuery("from Groups_members_LMS x where x.timeadded>=:readingtimestamp and x.timeadded<=:ceiling order by x.id asc");
		group_members.setParameter("readingtimestamp", readingfromtimestamp);
		group_members.setParameter("ceiling", ceiling);
		this.groupMembersLms = group_members.list();
		System.out.println("group_members_lms tables: " + this.groupMembersLms.size());

		final Query question_states = session
				.createQuery("from Question_states_LMS x where x.timestamp>=:readingtimestamp and x.timestamp<=:ceiling order by x.id asc");
		question_states.setParameter("readingtimestamp", readingfromtimestamp);
		question_states.setParameter("ceiling", ceiling);
		this.questionStatesLms = question_states.list();
		System.out.println("question_states_lms tables: " + this.questionStatesLms.size());

		final Query quiz_question_instances = session
				.createQuery("from Quiz_question_instances_LMS x order by x.id asc");
		this.quizQuestionInstancesLms = quiz_question_instances.list();
		System.out.println("quiz_question_instances_lms tables: " + this.quizQuestionInstancesLms.size());

		final Query question = session
				.createQuery("from Question_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		question.setParameter("readingtimestamp", readingfromtimestamp);
		question.setParameter("ceiling", ceiling);
		this.questionLms = question.list();
		System.out.println("question_lms tables: " + this.questionLms.size());

		final Query user = session
				.createQuery("from User_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		user.setParameter("ceiling", ceiling);
		user.setParameter("readingtimestamp", readingfromtimestamp);
		this.userLms = user.list();
		System.out.println("user_lms tables: " + this.userLms.size());

		final Query role = session.createQuery("from Role_LMS x order by x.id asc");
		this.roleLms = role.list();
		System.out.println("role_lms tables: " + this.roleLms.size());

		final Query context = session.createQuery("from Context_LMS x order by x.id asc");
		this.contextLms = context.list();
		System.out.println("context_lms tables: " + this.contextLms.size());

		final Query role_assignments = session
				.createQuery("from Role_assignments_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		role_assignments.setParameter("ceiling", ceiling);
		role_assignments.setParameter("readingtimestamp", readingfromtimestamp);
		this.roleAssignmentsLms = role_assignments.list();
		System.out.println("role_assignments_lms tables: " + this.roleAssignmentsLms.size());

		final Query assignments = session
				.createQuery("from Assignment_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		assignments.setParameter("ceiling", ceiling);
		assignments.setParameter("readingtimestamp", readingfromtimestamp);
		this.assignmentLms = assignments.list();
		System.out.println("assignment_lms tables: " + this.assignmentLms.size());

		final Query assignment_submission = session
				.createQuery("from Assignment_submissions_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		assignment_submission.setParameter("ceiling", ceiling);
		assignment_submission.setParameter("readingtimestamp", readingfromtimestamp);
		this.assignmentSubmissionLms = assignment_submission.list();
		System.out.println("assignment_submission_lms tables: " + this.assignmentSubmissionLms.size());

		final Query quiz_grades = session
				.createQuery("from Quiz_grades_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		quiz_grades.setParameter("ceiling", ceiling);
		quiz_grades.setParameter("readingtimestamp", readingfromtimestamp);
		this.quizGradesLms = quiz_grades.list();
		System.out.println("quiz_grades_lms tables: " + this.quizGradesLms.size());

		final Query forum_discussions = session
				.createQuery("from Forum_discussions_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		forum_discussions.setParameter("ceiling", ceiling);
		forum_discussions.setParameter("readingtimestamp", readingfromtimestamp);
		this.forumDiscussionsLms = forum_discussions.list();
		System.out.println("forum_discussions_lms tables: " + this.forumDiscussionsLms.size());

		final Query scorm = session
				.createQuery("from Scorm_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		scorm.setParameter("ceiling", ceiling);
		scorm.setParameter("readingtimestamp", readingfromtimestamp);
		this.scormLms = scorm.list();
		System.out.println("scorm_lms tables: " + this.scormLms.size());

		final Query grade_grades = session
				.createQuery("from Grade_grades_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		grade_grades.setParameter("ceiling", ceiling);
		grade_grades.setParameter("readingtimestamp", readingfromtimestamp);
		this.gradeGradesLms = grade_grades.list();
		System.out.println("grade_grades_lms tables: " + this.gradeGradesLms.size());

		final Query grade_items = session
				.createQuery("from Grade_items_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:ceiling order by x.id asc");
		grade_items.setParameter("ceiling", ceiling);
		grade_items.setParameter("readingtimestamp", readingfromtimestamp);
		this.gradeItemsLms = grade_items.list();
		System.out.println("grade_items_lms tables: " + this.gradeItemsLms.size());

		// hibernate session finish and close
		tx.commit();
		session.close();

	}

	@Override
	@SuppressWarnings("unchecked")
	public void getLMStables(final DBConfigObject dbConf, final long readingfromtimestamp, final long readingtotimestamp) {

		// accessing DB by creating a session and a transaction using HibernateUtil
		final Session session = HibernateUtil.getSessionFactory(dbConf).openSession();
		// Session session = HibernateUtil.getDynamicSourceDBFactoryMoodle("jdbc:mysql://localhost/moodle19",
		// "datamining", "LabDat1#").openSession();
		session.clear();
		final Transaction tx = session.beginTransaction();

		// reading the LMS Database, create tables as lists of instances of the DB-table classes

		if (this.userLms == null) {

			final Query assign = session.createQuery("from Assign_LMS x order by x.id asc");
			this.assignLms = assign.list();
			System.out.println("Assign tables: " + this.assignLms.size());

			final Query enrol = session.createQuery("from Enrol_LMS x order by x.id asc");
			this.enrolLms = enrol.list();
			System.out.println("Enrol tables: " + this.enrolLms.size());

			final Query assignPC = session.createQuery("from Assign_Plugin_Config_LMS x order by x.id asc");
			this.assignPluginConfigLms = assignPC.list();
			System.out.println("Assign_Plugin_Config tables: " + this.assignPluginConfigLms.size());

			final Query modules = session.createQuery("from Modules_LMS x order by x.id asc");
			this.modulesLms = modules.list();
			System.out.println("Modules tables: " + this.modulesLms.size());

			final Query userEnrol = session.createQuery("from User_Enrolments_LMS x order by x.id asc");
			this.userEnrolmentsLms = userEnrol.list();
			System.out.println("User_Enrolments tables: " + this.userEnrolmentsLms.size());

			final Query coursMod = session.createQuery("from Course_Modules_LMS x order by x.id asc");
			this.courseModulesLms = coursMod.list();
			System.out.println("Course_Modules tables: " + this.courseModulesLms.size());

			final Query resource = session.createQuery("from Resource_LMS x order by x.id asc");
			this.resourceLms = resource.list();
			System.out.println("Resource tables: " + this.resourceLms.size());

			final Query course = session.createQuery("from Course_LMS x order by x.id asc");
			this.courseLms = course.list();
			System.out.println("Course_LMS tables: " + this.courseLms.size());

			final Query chat = session.createQuery("from Chat_LMS x order by x.id asc");
			this.chatLms = chat.list();
			System.out.println("Chat_LMS tables: " + this.chatLms.size());

			final Query courseCategories = session.createQuery("from CourseCategories_LMS x order by x.id asc");
			this.courseCategoriesLms = courseCategories.list();
			System.out.println("CourseCategories_LMS tables: " + this.courseCategoriesLms.size());

			final Query forum = session.createQuery("from Forum_LMS x order by x.id asc");
			this.forumLms = forum.list();
			System.out.println("Forum_LMS tables: " + this.forumLms.size());

			final Query group = session.createQuery("from Groups_LMS x order by x.id asc");
			this.groupLms = group.list();
			System.out.println("Groups_LMS tables: " + this.groupLms.size());

			final Query quiz = session.createQuery("from Quiz_LMS x order by x.id asc");
			this.quizLms = quiz.list();
			System.out.println("Quiz_LMS tables: " + this.quizLms.size());

			final Query wiki = session.createQuery("from Wiki_LMS x order by x.id asc");
			this.wikiLms = wiki.list();
			System.out.println("Wiki_LMS tables: " + this.wikiLms.size());

			final Query quiz_question_instances = session
					.createQuery("from Quiz_question_instances_LMS x order by x.id asc");
			this.quizQuestionInstancesLms = quiz_question_instances.list();
			System.out.println("Quiz_question_instances_LMS tables: " + this.quizQuestionInstancesLms.size());

			final Query question = session.createQuery("from Question_LMS x order by x.id asc");
			this.questionLms = question.list();
			System.out.println("Question_LMS tables: " + this.questionLms.size());

			final Query user = session.createQuery("from User_LMS x order by x.id asc");
			this.userLms = user.list();
			System.out.println("User_LMS tables: " + this.userLms.size());

			final Query role = session.createQuery("from Role_LMS x order by x.id asc");
			this.roleLms = role.list();
			System.out.println("Role_LMS tables: " + this.roleLms.size());

			session.clear();

			final Query context = session.createQuery("from Context_LMS x order by x.id asc");
			this.contextLms = context.list();
			System.out.println("Context_LMS tables: " + this.contextLms.size());

			final Query assignments = session.createQuery("from Assignment_LMS x order by x.id asc");
			this.assignmentLms = assignments.list();
			System.out.println("Assignment_LMS tables: " + this.assignmentLms.size());

			final Query scorm = session.createQuery("from Scorm_LMS x order by x.id asc");
			this.scormLms = scorm.list();
			System.out.println("Scorm_LMS tables: " + this.scormLms.size());

			final Query grade_items = session.createQuery("from Grade_items_LMS x order by x.id asc");
			this.gradeItemsLms = grade_items.list();
			System.out.println("Grade_items_LMS tables: " + this.gradeItemsLms.size());
		}

		final Query quiz_attempts = session
				.createQuery("from Quiz_attempts_LMS_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:readingtotimestamp order by x.id asc");
		quiz_attempts.setParameter("readingtimestamp", readingfromtimestamp);
		quiz_attempts.setParameter("readingtimestamp2", readingtotimestamp);
		this.quizAttemptsLms = quiz_attempts.list();
		System.out.println("quiz_attempts_lms tables: " + this.quizAttemptsLms.size());

		final Query log = session
				.createQuery("from Log_LMS x where x.time>=:readingtimestamp and x.time<=:readingtimestamp2 order by x.id asc");
		log.setParameter("readingtimestamp", readingfromtimestamp);
		log.setParameter("readingtimestamp2", readingtotimestamp);
		this.logLms = log.list();
		System.out.println("Log_LMS tables: " + this.logLms.size());

		final Query chatlog = session
				.createQuery("from ChatLog_LMS x where x.timestamp>=:readingtimestamp and x.timestamp<=:readingtimestamp2 order by x.id asc");
		chatlog.setParameter("readingtimestamp", readingfromtimestamp);
		chatlog.setParameter("readingtimestamp2", readingtotimestamp);
		this.chatLogLms = chatlog.list();
		System.out.println("ChatLog_LMS tables: " + this.chatLogLms.size());

		final Query forum_posts = session
				.createQuery("from Forum_posts_LMS x where x.created>=:readingtimestamp and x.created<=:readingtimestamp2 order by x.id asc");
		forum_posts.setParameter("readingtimestamp", readingfromtimestamp);
		forum_posts.setParameter("readingtimestamp2", readingtotimestamp);
		this.forumPostsLms = forum_posts.list();
		System.out.println("Forum_posts_LMS tables: " + this.forumPostsLms.size());

		final Query forum_posts_modified = session
				.createQuery("from Forum_posts_LMS x where x.modified>=:readingtimestamp and x.modified<=:readingtimestamp2 order by x.id asc");
		forum_posts_modified.setParameter("readingtimestamp", readingfromtimestamp);
		forum_posts_modified.setParameter("readingtimestamp2", readingtotimestamp);
		this.forumPostsLms.addAll(forum_posts_modified.list());
		System.out.println("Forum_posts_LMS tables: " + this.forumPostsLms.size());

		session.clear();

		final Query group_members = session
				.createQuery("from Groups_members_LMS x where x.timeadded>=:readingtimestamp and x.timeadded<=:readingtimestamp2 order by x.id asc");
		group_members.setParameter("readingtimestamp", readingfromtimestamp);
		group_members.setParameter("readingtimestamp2", readingtotimestamp);
		this.groupMembersLms = group_members.list();
		System.out.println("Groups_members_LMS tables: " + this.groupMembersLms.size());

		final Query question_states = session
				.createQuery("from Question_states_LMS x where x.timestamp>=:readingtimestamp and x.timestamp<=:readingtimestamp2 order by x.id asc");
		question_states.setParameter("readingtimestamp", readingfromtimestamp);
		question_states.setParameter("readingtimestamp2", readingtotimestamp);
		this.questionStatesLms = question_states.list();
		System.out.println("Question_states_LMS tables: " + this.questionStatesLms.size());

		final Query role_assignments = session
				.createQuery("from Role_assignments_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:readingtimestamp2 order by x.id asc");
		role_assignments.setParameter("readingtimestamp", readingfromtimestamp);
		role_assignments.setParameter("readingtimestamp2", readingtotimestamp);
		this.roleAssignmentsLms = role_assignments.list();
		System.out.println("Role_assignments_LMS tables: " + this.roleAssignmentsLms.size());

		final Query assignment_submission = session
				.createQuery("from Assignment_submissions_LMS x where x.timecreated>=:readingtimestamp and x.timecreated<=:readingtimestamp2 order by x.id asc");
		assignment_submission.setParameter("readingtimestamp", readingfromtimestamp);
		assignment_submission.setParameter("readingtimestamp2", readingtotimestamp);
		this.assignmentSubmissionLms = assignment_submission.list();
		System.out.println("Assignment_submissions_LMS tables: " + this.assignmentSubmissionLms.size());

		final Query quiz_grades = session
				.createQuery("from Quiz_grades_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:readingtimestamp2 order by x.id asc");
		quiz_grades.setParameter("readingtimestamp", readingfromtimestamp);
		quiz_grades.setParameter("readingtimestamp2", readingtotimestamp);
		this.quizGradesLms = quiz_grades.list();
		System.out.println("Quiz_grades_LMS tables: " + this.quizGradesLms.size());

		final Query forum_discussions = session
				.createQuery("from Forum_discussions_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:readingtimestamp2 order by x.id asc");
		forum_discussions.setParameter("readingtimestamp", readingfromtimestamp);
		forum_discussions.setParameter("readingtimestamp2", readingtotimestamp);
		this.forumDiscussionsLms = forum_discussions.list();
		System.out.println("Forum_discussions_LMS tables: " + this.forumDiscussionsLms.size());

		final Query grade_grades = session
				.createQuery("from Grade_grades_LMS x where x.timemodified>=:readingtimestamp and x.timemodified<=:readingtimestamp2 order by x.id asc");
		grade_grades.setParameter("readingtimestamp", readingfromtimestamp);
		grade_grades.setParameter("readingtimestamp2", readingtotimestamp);
		this.gradeGradesLms = grade_grades.list();
		System.out.println("Grade_grades_LMS tables: " + this.gradeGradesLms.size());

		session.clear();

		// hibernate session finish and close
		tx.commit();
		session.close();

	}

	@Override
	public void clearLMStables() {
		this.logLms.clear();
		this.resourceLms.clear();
		this.courseLms.clear();
		this.forumLms.clear();
		this.wikiLms.clear();
		this.userLms.clear();
		this.quizLms.clear();
		this.gradeGradesLms.clear();
		this.groupLms.clear();
		this.groupMembersLms.clear();
		this.questionStatesLms.clear();
		this.forumPostsLms.clear();
		this.roleLms.clear();
		this.roleAssignmentsLms.clear();
		this.assignmentSubmissionLms.clear();
	}

	// methods for create and fill the mining-table instances

	@Override
	public HashMap<Long, CourseUserMining> generateCourseUserMining() {

		final HashMap<Long, CourseUserMining> course_user_mining = new HashMap<Long, CourseUserMining>();

		for (final Context_LMS loadedItem : this.contextLms)
		{
			if (loadedItem.getContextlevel() == 50) {
				for (final Role_assignments_LMS loadedItem2 : this.roleAssignmentsLms)
				{
					if (loadedItem2.getContextid() == loadedItem.getId()) {
						final CourseUserMining insert = new CourseUserMining();

						insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem2.getId()));
						insert.setRole(Long.valueOf(this.connector.getPrefix() + "" + loadedItem2.getRoleid()),
								this.roleMining, this.oldRoleMining);
						insert.setPlatform(this.connector.getPlatformId());
						insert.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem2.getUserid()),
								this.userMining, this.oldUserMining);
						insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getInstanceid()),
								this.courseMining, this.oldCourseMining);
						if ((insert.getUser() != null) && (insert.getCourse() != null) && (insert.getRole() != null)) {
							course_user_mining.put(insert.getId(), insert);
						}
					}
				}
			}
		}

		for (final CourseUserMining courseUser : course_user_mining.values())
		{
			long enrolid = 0;
			for (final Enrol_LMS loadedItem : this.enrolLms)
			{
				if (Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourseid()) == courseUser.getCourse()
						.getId())
				{
					enrolid = loadedItem.getId();
					break;
				}
			}
			for (final User_Enrolments_LMS loadedItem : this.userEnrolmentsLms)
			{
				if (loadedItem.getEnrolid() == enrolid)
				{
					courseUser.setEnrolstart(loadedItem.getTimestart());
					courseUser.setEnrolend(loadedItem.getTimeend());
				}
			}
		}

		return course_user_mining;
	}

	@Override
	public HashMap<Long, CourseForumMining> generateCourseForumMining() {

		final HashMap<Long, CourseForumMining> course_forum_mining = new HashMap<Long, CourseForumMining>();

		for (final Forum_LMS loadedItem : this.forumLms)
		{
			final CourseForumMining insert = new CourseForumMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()), this.courseMining,
					this.oldCourseMining);
			insert.setForum(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()), this.forumMining,
					this.oldForumMining);
			insert.setPlatform(this.connector.getPlatformId());
			if ((insert.getCourse() != null) && (insert.getForum() != null)) {
				course_forum_mining.put(insert.getId(), insert);
			}
		}
		return course_forum_mining;
	}

	@Override
	public HashMap<Long, CourseMining> generateCourseMining() {

		final HashMap<Long, CourseMining> course_mining = new HashMap<Long, CourseMining>();
		for (final Course_LMS loadedItem : this.courseLms)
		{
			final CourseMining insert = new CourseMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setStartDate(loadedItem.getStartdate());
			insert.setEnrolStart(loadedItem.getEnrolstartdate());
			insert.setEnrolEnd(loadedItem.getEnrolenddate());
			insert.setTimeCreated(loadedItem.getTimecreated());
			insert.setTimeModified(loadedItem.getTimemodified());
			insert.setTitle(loadedItem.getFullname());
			insert.setShortname(loadedItem.getShortname());
			insert.setPlatform(this.connector.getPlatformId());

			course_mining.put(insert.getId(), insert);
		}
		return course_mining;
	}

	@Override
	public HashMap<Long, CourseGroupMining> generateCourseGroupMining() {

		final HashMap<Long, CourseGroupMining> course_group_mining = new HashMap<Long, CourseGroupMining>();

		for (final Groups_LMS loadedItem : this.groupLms)
		{
			final CourseGroupMining insert = new CourseGroupMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setGroup(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()), this.groupMining,
					this.oldGroupMining);
			insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourseid()),
					this.courseMining, this.oldCourseMining);
			insert.setPlatform(this.connector.getPlatformId());
			if ((insert.getCourse() != null) && (insert.getGroup() != null)) {
				course_group_mining.put(insert.getId(), insert);
			}
		}
		return course_group_mining;
	}

	@Override
	public HashMap<Long, CourseQuizMining> generateCourseQuizMining() {

		final HashMap<Long, CourseQuizMining> course_quiz_mining = new HashMap<Long, CourseQuizMining>();

		for (final Quiz_LMS loadedItem : this.quizLms)
		{
			final CourseQuizMining insert = new CourseQuizMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()), this.courseMining,
					this.oldCourseMining);
			insert.setQuiz(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()), this.quizMining,
					this.oldQuizMining);
			insert.setPlatform(this.connector.getPlatformId());
			if ((insert.getCourse() != null) && (insert.getQuiz() != null)) {
				course_quiz_mining.put(insert.getQuiz().getId(), insert);
			}
			if (insert.getQuiz() == null) {
				this.logger.info("In Course_quiz_mining, quiz(quiz) not found: " + loadedItem.getId());
			}
		}
		return course_quiz_mining;
	}

	@Override
	public HashMap<Long, CourseAssignmentMining> generateCourseAssignmentMining() {

		final HashMap<Long, CourseAssignmentMining> course_assignment_mining = new HashMap<Long, CourseAssignmentMining>();

		for (final Assignment_LMS loadedItem : this.assignmentLms)
		{
			final CourseAssignmentMining insert = new CourseAssignmentMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()), this.courseMining,
					this.oldCourseMining);
			insert.setPlatform(this.connector.getPlatformId());
			if (insert.getCourse() == null) {
				this.logger.info("course not found for course-assignment: " + loadedItem.getId() + " and course: "
						+ loadedItem.getCourse());
			}
			insert.setAssignment(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()),
					this.assignmentMining, this.oldAssignmentMining);
			if ((insert.getCourse() != null) && (insert.getAssignment() != null)) {
				course_assignment_mining.put(insert.getId(), insert);
			}
			if (insert.getAssignment() == null) {
				this.logger.info("In Course_assignment_mining, assignment not found: " + loadedItem.getId());
			}
		}
		return course_assignment_mining;
	}

	@Override
	public HashMap<Long, CourseScormMining> generateCourseScormMining() {

		final HashMap<Long, CourseScormMining> course_scorm_mining = new HashMap<Long, CourseScormMining>();
		for (final Scorm_LMS loadedItem : this.scormLms)
		{
			final CourseScormMining insert = new CourseScormMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()), this.courseMining,
					this.oldCourseMining);
			insert.setScorm(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()), this.scormMining,
					this.oldScormMining);
			insert.setPlatform(this.connector.getPlatformId());
			if ((insert.getCourse() != null) && (insert.getScorm() != null)) {
				course_scorm_mining.put(insert.getId(), insert);
			}
			if (insert.getScorm() == null) {
				this.logger.info("In Course_scorm_mining, scorm not found: " + loadedItem.getId());
			}
		}
		return course_scorm_mining;
	}

	@Override
	public HashMap<Long, CourseResourceMining> generateCourseResourceMining() {

		final HashMap<Long, CourseResourceMining> course_resource_mining = new HashMap<Long, CourseResourceMining>();

		for (final Resource_LMS loadedItem : this.resourceLms)
		{
			final CourseResourceMining insert = new CourseResourceMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()), this.courseMining,
					this.oldCourseMining);
			insert.setResource(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()), this.resourceMining,
					this.oldResourceMining);
			insert.setPlatform(this.connector.getPlatformId());
			if ((insert.getCourse() != null) && (insert.getResource() != null)) {
				course_resource_mining.put(insert.getId(), insert);
			}
		}
		return course_resource_mining;
	}

	@Override
	public HashMap<Long, CourseLogMining> generateCourseLogMining() {
		final HashMap<Long, CourseLogMining> courseLogMining = new HashMap<Long, CourseLogMining>();
		final HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();

		for (final Log_LMS loadedItem : this.logLms) {

			final long uid = Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid());

			// Creates a list of time stamps for every user indicating requests
			// We later use this lists to determine the time a user accessed a resource
			if (users.get(uid) == null)
			{
				// if the user isn't already listed, create a new key
				final ArrayList<Long> times = new ArrayList<Long>();
				times.add(loadedItem.getTime());
				users.put(uid, times);
			}
			else
			{
				final ArrayList<Long> times = users.get(uid);
				if (loadedItem.getAction() == "login") {
					times.add(0L);
				}
				if (!times.contains(loadedItem.getTime())) {
					times.add(loadedItem.getTime());
				}
				users.put(uid, times);
			}

			if (loadedItem.getModule().equals("course"))
			{
				final CourseLogMining insert = new CourseLogMining();

				insert.setId(courseLogMining.size() + 1 + this.courseLogMax);
				insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()),
						this.courseMining, this.oldCourseMining);
				insert.setPlatform(this.connector.getPlatformId());
				insert.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid()), this.userMining,
						this.oldUserMining);

				insert.setAction(loadedItem.getAction());
				insert.setTimestamp(loadedItem.getTime());
				if ((insert.getUser() != null) && (insert.getCourse() != null)) {
					courseLogMining.put(insert.getId(), insert);
				}

			}
		}

		for (final CourseLogMining r : courseLogMining.values())
		{
			if (r.getUser() != null)
			{
				long duration = -1;
				final ArrayList<Long> times = users.get(r.getUser().getId());

				final int pos = times.indexOf(r.getTimestamp());
				if ((pos > -1) && (pos < (times.size() - 1))) {
					if (times.get(pos + 1) != 0) {
						duration = times.get(pos + 1) - times.get(pos);
					}
				}
				// All duration that are longer than one hour are cut to an hour
				if (duration > 3600) {
					duration = 3600;
				}
				r.setDuration(duration);
			}
		}

		return courseLogMining;
	}

	@Override
	public HashMap<Long, CourseWikiMining> generateCourseWikiMining() {

		final HashMap<Long, CourseWikiMining> course_wiki_mining = new HashMap<Long, CourseWikiMining>();

		for (final Wiki_LMS loadedItem : this.wikiLms)
		{
			final CourseWikiMining insert = new CourseWikiMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()), this.courseMining,
					this.oldCourseMining);
			insert.setWiki(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()), this.wikiMining,
					this.oldWikiMining);
			insert.setPlatform(this.connector.getPlatformId());
			if ((insert.getCourse() != null) && (insert.getWiki() != null)) {
				course_wiki_mining.put(insert.getId(), insert);
			}
		}
		return course_wiki_mining;
	}

	@Override
	public HashMap<Long, ForumLogMining> generateForumLogMining() {
		final HashMap<Long, ForumLogMining> forumLogMining = new HashMap<Long, ForumLogMining>();
		final HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();

		for (final Log_LMS loadedItem : this.logLms) {

			final long uid = Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid());
			// Creates a list of time stamps for every user indicating requests
			// We later use this lists to determine the time a user accessed a resource
			if (users.get(uid) == null)
			{
				// if the user isn't already listed, create a new key
				final ArrayList<Long> times = new ArrayList<Long>();
				times.add(loadedItem.getTime());
				users.put(uid, times);
			}
			else
			{
				final ArrayList<Long> times = users.get(uid);
				if (loadedItem.getAction() == "login") {
					times.add(0L);
				}
				if (!times.contains(loadedItem.getTime())) {
					times.add(loadedItem.getTime());
				}
				users.put(uid, times);
			}

			if (loadedItem.getModule().equals("forum")) {

				final ForumLogMining insert = new ForumLogMining();

				insert.setId(forumLogMining.size() + 1 + this.forumLogMax);
				insert.setPlatform(this.connector.getPlatformId());
				insert.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid()), this.userMining,
						this.oldUserMining);

				if ((loadedItem.getAction().equals("view forum") || loadedItem.getAction().equals("subscribe"))
						&& loadedItem.getInfo().matches("[0-9]+")) {
					insert.setForum(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getInfo()),
							this.forumMining, this.oldForumMining);
				}
				else {
					if ((loadedItem.getAction().equals("add discussion") || loadedItem.getAction().equals(
							"view discussion"))
							&& loadedItem.getInfo().matches("[0-9]+")) {
						for (final Forum_discussions_LMS loadedItem2 : this.forumDiscussionsLms)
						{
							if (loadedItem2.getId() == Long.valueOf(loadedItem.getInfo()))
							{
								insert.setForum(Long.valueOf(this.connector.getPrefix() + "" + loadedItem2.getForum()),
										this.forumMining, this.oldForumMining);
								break;
							}
						}
					}
				}

				insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()),
						this.courseMining, this.oldCourseMining);
				insert.setAction(loadedItem.getAction());
				for (final Forum_posts_LMS loadedItem2 : this.forumPostsLms)
				{
					if ((loadedItem2.getUserid() == loadedItem.getUserid())
							&& ((loadedItem2.getCreated() == loadedItem.getTime()) || (loadedItem2.getModified() == loadedItem
									.getTime()))) {
						insert.setMessage(loadedItem2.getMessage());
						insert.setSubject(loadedItem2.getSubject());
						break;
					}
				}
				insert.setTimestamp(loadedItem.getTime());
				if ((insert.getCourse() != null) && (insert.getForum() != null) && (insert.getUser() != null)) {
					forumLogMining.put(insert.getId(), insert);
				}
			}
		}

		for (final ForumLogMining r : forumLogMining.values())
		{
			if (r.getUser() != null)
			{
				long duration = -1;
				final ArrayList<Long> times = users.get(r.getUser().getId());

				final int pos = times.indexOf(r.getTimestamp());
				if ((pos > -1) && (pos < (times.size() - 1))) {
					if (times.get(pos + 1) != 0) {
						duration = times.get(pos + 1) - times.get(pos);
					}
				}
				// All duration that are longer than one hour are cut to an hour
				if (duration > 3600) {
					duration = 3600;
				}
				r.setDuration(duration);
			}
		}
		return forumLogMining;
	}

	@Override
	public HashMap<Long, ForumMining> generateForumMining() {

		final HashMap<Long, ForumMining> forum_mining = new HashMap<Long, ForumMining>();

		for (final Forum_LMS loadedItem : this.forumLms)
		{
			final ForumMining insert = new ForumMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setTimeModified(loadedItem.getTimemodified());
			insert.setTitle(loadedItem.getName());
			insert.setSummary(loadedItem.getIntro());
			insert.setPlatform(this.connector.getPlatformId());
			forum_mining.put(insert.getId(), insert);
		}

		for (final Log_LMS loadedItem : this.logLms)
		{
			if ((forum_mining.get(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCmid())) != null)
					&& ((forum_mining.get(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCmid()))
							.getTimeCreated() == 0) || (forum_mining.get(
							Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCmid())).getTimeCreated() > loadedItem
							.getTime())))
			{
				forum_mining.get(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCmid())).setTimeCreated(
						loadedItem.getTime());
			}
		}
		return forum_mining;
	}

	@Override
	public HashMap<Long, GroupUserMining> generateGroupUserMining() {

		final HashMap<Long, GroupUserMining> group_members_mining = new HashMap<Long, GroupUserMining>();

		for (final Groups_members_LMS loadedItem : this.groupMembersLms)
		{
			final GroupUserMining insert = new GroupUserMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setGroup(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getGroupid()), this.groupMining,
					this.oldGroupMining);

			insert.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid()), this.userMining,
					this.oldUserMining);

			insert.setTimestamp(loadedItem.getTimeadded());
			insert.setPlatform(this.connector.getPlatformId());
			if ((insert.getUser() != null) && (insert.getGroup() != null)) {
				group_members_mining.put(insert.getId(), insert);
			}
		}
		return group_members_mining;
	}

	@Override
	public HashMap<Long, GroupMining> generateGroupMining() {

		final HashMap<Long, GroupMining> group_mining = new HashMap<Long, GroupMining>();

		for (final Groups_LMS loadedItem : this.groupLms)
		{
			final GroupMining insert = new GroupMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setTimeCreated(loadedItem.getTimecreated());
			insert.setTimeModified(loadedItem.getTimemodified());
			insert.setPlatform(this.connector.getPlatformId());
			group_mining.put(insert.getId(), insert);
		}
		return group_mining;
	}

	@Override
	public HashMap<Long, QuestionLogMining> generateQuestionLogMining()
	{

		final HashMap<Long, QuestionLogMining> questionLogMiningtmp = new HashMap<Long, QuestionLogMining>();
		final HashMap<Long, QuestionLogMining> questionLogMining = new HashMap<Long, QuestionLogMining>();
		final HashMap<String, Long> timestampIdMap = new HashMap<String, Long>();
		final HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();

		for (final Question_states_LMS loadedItem : this.questionStatesLms) {

			final QuestionLogMining insert = new QuestionLogMining();

			insert.setId(questionLogMiningtmp.size() + 1 + this.questionLogMax); // ID
			insert.setQuestion(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getQuestion()),
					this.questionMining, this.oldQuestionMining); // Question
			insert.setPenalty(loadedItem.getPenalty());
			insert.setAnswers(loadedItem.getAnswer());
			insert.setTimestamp(loadedItem.getTimestamp());
			insert.setPlatform(this.connector.getPlatformId());

			// Set Grades
			if ((loadedItem.getEvent() == 3) || (loadedItem.getEvent() == 6) || (loadedItem.getEvent() == 9)) {
				insert.setRawGrade(loadedItem.getRaw_grade());
				insert.setFinalGrade(loadedItem.getGrade());
			}

			switch (loadedItem.getEvent())
			{
				case 0:
					insert.setAction("OPEN");
					break;
				case 1:
					insert.setAction("NAVIGATE");
					break;
				case 2:
					insert.setAction("SAVE");
					break;
				case 3:
					insert.setAction("GRADE");
					break;
				case 4:
					insert.setAction("DUPLICATE");
					break;
				case 5:
					insert.setAction("VALIDATE");
					break;
				case 6:
					insert.setAction("CLOSEANDGRADE");
					break;
				case 7:
					insert.setAction("SUBMIT");
					break;
				case 8:
					insert.setAction("CLOSE");
					break;
				case 9:
					insert.setAction("MANUALGRADE");
					break;
				default:
					insert.setAction("UNKNOWN");
			}

			// Set quiz type
			if ((insert.getQuestion() != null) && (this.quizQuestionMining.get(insert.getQuestion().getId()) != null))
			{
				insert.setQuiz(this.quizQuestionMining.get(insert.getQuestion().getId()).getQuiz());
				if (this.courseQuizMining.get(insert.getQuiz().getId()) != null) {
					insert.setCourse(this.courseQuizMining.get(insert.getQuiz().getId()).getCourse());
				}
			}
			else if ((insert.getQuestion() != null)
					&& (this.oldQuizQuestionMining.get(insert.getQuestion().getId()) != null)
					&& (this.oldCourseQuizMining.get(insert.getQuiz().getId()) != null))
			{
				insert.setQuiz(this.oldQuizQuestionMining.get(insert.getQuestion().getId()).getQuiz());
				if (this.oldCourseQuizMining.get(insert.getQuiz().getId()) != null) {
					insert.setCourse(this.courseQuizMining.get(insert.getQuiz().getId()).getCourse());
				}
			}

			// Set Type
			for (final Question_LMS loadedItem2 : this.questionLms)
			{
				if (loadedItem2.getId() == (loadedItem.getQuestion())) {
					insert.setType(loadedItem2.getQtype());// Type
					break;
				}
			}
			if ((insert.getType() == null) && (this.oldQuestionMining.get(loadedItem.getQuestion()) != null)) {
				insert.setType(this.oldQuestionMining.get(loadedItem.getQuestion()).getType());
			}
			if (insert.getType() == null) {
				this.logger.info("In Question_log_mining, type not found for question_states: " + loadedItem.getId()
						+ " and question: " + loadedItem.getQuestion() + " question list size: "
						+ this.questionLms.size());
			}

			if ((insert.getQuestion() != null) && (insert.getQuiz() != null) && (insert.getCourse() != null))
			{

				timestampIdMap.put(insert.getTimestamp() + " " + insert.getQuiz().getId(), insert.getId());
				questionLogMiningtmp.put(insert.getId(), insert);
			}
		}

		// Set Course and
		for (final Log_LMS loadedItem : this.logLms)
		{
			final long uid1 = Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid());

			// Creates a list of time stamps for every user indicating requests
			// We later use this lists to determine the time a user accessed a resource
			if (users.get(uid1) == null)
			{
				// if the user isn't already listed, create a new key
				final ArrayList<Long> times = new ArrayList<Long>();
				times.add(loadedItem.getTime());
				users.put(uid1, times);
			}
			else
			{
				final ArrayList<Long> times = users.get(uid1);
				if (loadedItem.getAction() == "login") {
					times.add(0L);
				}
				if (!times.contains(loadedItem.getTime())) {
					times.add(loadedItem.getTime());
				}
				users.put(uid1, times);
			}

			if (loadedItem.getModule().equals("quiz")
					&& (timestampIdMap.get(loadedItem.getTime() + " " + this.connector.getPrefix() + ""
							+ loadedItem.getInfo()) != null)) {
				{
					final QuestionLogMining qlm = questionLogMiningtmp.get(timestampIdMap.get(loadedItem.getTime()
							+ " " + this.connector.getPrefix() + "" + loadedItem.getInfo()));
					qlm.setUser(uid1, this.userMining, this.oldUserMining);
					questionLogMining.put(qlm.getId(), qlm);
				}
			}
		}

		for (final QuestionLogMining r : questionLogMining.values())
		{
			if (r.getUser() != null)
			{
				long duration = -1;
				final ArrayList<Long> times = users.get(r.getUser().getId());

				final int pos = times.indexOf(r.getTimestamp());
				if ((pos > -1) && (pos < (times.size() - 1))) {
					if (times.get(pos + 1) != 0) {
						duration = times.get(pos + 1) - times.get(pos);
					}
				}
				// All duration that are longer than one hour are cut to an hour
				if (duration > 3600) {
					duration = 3600;
				}
				r.setDuration(duration);
			}
		}

		return questionLogMining;
	}

	@Override
	public HashMap<Long, QuizLogMining> generateQuizLogMining() {
		final HashMap<Long, QuizLogMining> quizLogMining = new HashMap<Long, QuizLogMining>();
		final HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();

		for (final Log_LMS loadedItem : this.logLms) {

			final long uid = Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid());

			// Creates a list of time stamps for every user indicating requests
			// We later use this lists to determine the time a user accessed a resource
			if (users.get(uid) == null)
			{
				// if the user isn't already listed, create a new key
				final ArrayList<Long> times = new ArrayList<Long>();
				times.add(loadedItem.getTime());
				users.put(uid, times);
			}
			else
			{
				final ArrayList<Long> times = users.get(uid);
				if (loadedItem.getAction() == "login") {
					times.add(0L);
				}
				if (!times.contains(loadedItem.getTime())) {
					times.add(loadedItem.getTime());
				}
				users.put(uid, times);
			}

			// insert quiz in quiz_log
			if (loadedItem.getModule().equals("quiz"))
			{
				final QuizLogMining insert = new QuizLogMining();

				insert.setId(quizLogMining.size() + 1 + this.quizLogMax);
				insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()),
						this.courseMining, this.oldCourseMining);
				insert.setPlatform(this.connector.getPlatformId());

				insert.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid()), this.userMining,
						this.oldUserMining);

				if (loadedItem.getInfo().matches("[0-9]+"))
				{
					insert.setQuiz(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getInfo()),
							this.quizMining, this.oldQuizMining);
				}
				insert.setAction(loadedItem.getAction());
				insert.setTimestamp(loadedItem.getTime());
				if ((insert.getQuiz() != null) && (insert.getUser() != null) && (loadedItem.getAction() != "review"))
				{
					for (final Quiz_attempts_LMS loadedItem2 : this.quizAttemptsLms)
					{
						final long id = Long.valueOf(this.connector.getPrefix() + "" + loadedItem2.getUserid());

						if ((Long.valueOf(this.connector.getPrefix() + "" + loadedItem2.getQuiz()) == insert.getQuiz()
								.getId())
								&& (id == insert.getUser().getId())
								&& (loadedItem2.getTimemodified() == insert.getTimestamp()))
						{
							insert.setGrade(loadedItem2.getSumgrades());
						}
					}
				}
				if ((insert.getQuiz() == null) && !(loadedItem.getAction().equals("view all"))) {
					this.logger.info("In Quiz_log_mining, quiz(quiz) not found for log: " + loadedItem.getId()
							+ " and cmid: " + loadedItem.getCmid() + " and info: " + loadedItem.getInfo()
							+ " and action: " + loadedItem.getAction());
				}
				if (insert.getUser() == null) {
					this.logger.info("In Quiz_log_mining(quiz), user not found for log: " + loadedItem.getId()
							+ " and user: " + loadedItem.getUserid());
				}
				if (insert.getCourse() == null) {
					this.logger.info("In Quiz_log_mining(quiz), course not found for log: " + loadedItem.getId()
							+ " and course: " + loadedItem.getCourse());
				}
				if ((insert.getCourse() != null) && (insert.getQuiz() != null) && (insert.getUser() != null)) {
					quizLogMining.put(insert.getId(), insert);
				}

			}
		}

		for (final QuizLogMining r : quizLogMining.values())
		{
			if (r.getUser() != null)
			{
				long duration = -1;
				final ArrayList<Long> times = users.get(r.getUser().getId());

				final int pos = times.indexOf(r.getTimestamp());
				if ((pos > -1) && (pos < (times.size() - 1))) {
					if (times.get(pos + 1) != 0) {
						duration = times.get(pos + 1) - times.get(pos);
					}
				}
				// All duration that are longer than one hour are cut to an hour
				if (duration > 3600) {
					duration = 3600;
				}
				r.setDuration(duration);
			}
		}
		return quizLogMining;
	}

	@Override
	public HashMap<Long, AssignmentLogMining> generateAssignmentLogMining() {

		final HashMap<Long, AssignmentLogMining> assignmentLogMining = new HashMap<Long, AssignmentLogMining>();
		final HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
		final HashMap<Long, ArrayList<Assignment_submissions_LMS>> asSub = new HashMap<Long, ArrayList<Assignment_submissions_LMS>>();

		for (final Assignment_submissions_LMS as : this.assignmentSubmissionLms)
		{
			if (asSub.get(as.getAssignment()) == null)
			{
				final ArrayList<Assignment_submissions_LMS> a = new ArrayList<Assignment_submissions_LMS>();
				a.add(as);
				asSub.put(as.getAssignment(), a);
			} else {
				asSub.get(as.getAssignment()).add(as);
			}

		}

		for (final Log_LMS loadedItem : this.logLms) {

			final long uid = Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid());

			// Creates a list of time stamps for every user indicating requests
			// We later use this lists to determine the time a user accessed a resource
			if (users.get(uid) == null)
			{
				// if the user isn't already listed, create a new key
				final ArrayList<Long> times = new ArrayList<Long>();
				times.add(loadedItem.getTime());
				users.put(uid, times);
			}
			else
			{
				final ArrayList<Long> times = users.get(uid);
				if (loadedItem.getAction() == "login") {
					times.add(0L);
				}
				if (!times.contains(loadedItem.getTime())) {
					times.add(loadedItem.getTime());
				}
				users.put(uid, times);
			}

			// insert assignments in assignment_log
			if (loadedItem.getModule().equals("assignment") && loadedItem.getInfo().matches("[0-9]++"))
			{
				final AssignmentLogMining insert = new AssignmentLogMining();
				insert.setId(assignmentLogMining.size() + 1 + this.assignmentLogMax);
				insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()),
						this.courseMining, this.oldCourseMining);
				insert.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid()), this.userMining,
						this.oldUserMining);
				insert.setAction(loadedItem.getAction());
				insert.setTimestamp(loadedItem.getTime());
				insert.setAssignment(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getInfo()),
						this.assignmentMining, this.oldAssignmentMining);
				insert.setPlatform(this.connector.getPlatformId());

				if ((insert.getAssignment() != null) && (insert.getUser() != null) && (insert.getCourse() != null))// &&
																													// insert.getAction().equals("upload"))
				{
					if (asSub.get(Long.valueOf(loadedItem.getInfo())) != null) {
						for (final Assignment_submissions_LMS loadedItem2 : asSub
								.get(Long.valueOf(loadedItem.getInfo())))
						{
							if ((loadedItem2.getAssignment() == Long.valueOf(loadedItem.getInfo()))
									&& loadedItem2.getUserid().equals(loadedItem.getUserid()))// &&
																								// loadedItem2.getTimemodified()
																								// ==
																								// loadedItem.getTime())
							{
								insert.setGrade((double) loadedItem2.getGrade());
								break;
							}
						}
					}
				}

				if (insert.getAssignment() == null) {
					this.logger.info("In Assignment_log_mining, assignment not found for log: " + loadedItem.getId()
							+ " and cmid: " + loadedItem.getCmid() + " and info: " + loadedItem.getInfo());
				}
				if (insert.getCourse() == null) {
					this.logger.info("In Assignment_log_mining, course not found for log: " + loadedItem.getId()
							+ " and course: " + loadedItem.getCourse());
				}
				if (insert.getUser() == null) {
					this.logger.info("In Assignment_log_mining, user not found for log: " + loadedItem.getId()
							+ " and user: " + loadedItem.getUserid());
				}
				insert.setPlatform(this.connector.getPlatformId());

				if ((insert.getUser() != null) && (insert.getAssignment() != null) && (insert.getCourse() != null)) {
					assignmentLogMining.put(insert.getId(), insert);
				}
			}
		}
		for (final AssignmentLogMining r : assignmentLogMining.values())
		{
			if (r.getUser() != null)
			{
				long duration = -1;
				final ArrayList<Long> times = users.get(r.getUser().getId());

				final int pos = times.indexOf(r.getTimestamp());
				if ((pos > -1) && (pos < (times.size() - 1))) {
					if (times.get(pos + 1) != 0) {
						duration = times.get(pos + 1) - times.get(pos);
					}
				}
				// All duration that are longer than one hour are cut to an hour
				if (duration > 3600) {
					duration = 3600;
				}
				r.setDuration(duration);
			}
		}

		return assignmentLogMining;
	}

	@Override
	public HashMap<Long, ScormLogMining> generateScormLogMining() {
		final HashMap<Long, ScormLogMining> scormLogMining = new HashMap<Long, ScormLogMining>();
		final HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();

		for (final Log_LMS loadedItem : this.logLms) {

			final long uid = Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid());

			// Creates a list of time stamps for every user indicating requests
			// We later use this lists to determine the time a user accessed a resource
			if (users.get(uid) == null)
			{
				// if the user isn't already listed, create a new key
				final ArrayList<Long> times = new ArrayList<Long>();
				times.add(loadedItem.getTime());
				users.put(uid, times);
			}
			else
			{
				final ArrayList<Long> times = users.get(uid);
				if (loadedItem.getAction() == "login") {
					times.add(0L);
				}
				if (!times.contains(loadedItem.getTime())) {
					times.add(loadedItem.getTime());
				}
				users.put(uid, times);
			}

			// insert scorm in scorm_log
			if (loadedItem.getModule().equals("scorm")) {
				final ScormLogMining insert = new ScormLogMining();

				insert.setId(scormLogMining.size() + 1 + this.scormLogMax);

				insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()),
						this.courseMining, this.oldCourseMining);
				insert.setPlatform(this.connector.getPlatformId());

				insert.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid()), this.userMining,
						this.oldUserMining);

				insert.setAction(loadedItem.getAction());
				insert.setTimestamp(loadedItem.getTime());
				if (loadedItem.getInfo().matches("[0-9]+")) {
					insert.setScorm(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getInfo()),
							this.scormMining, this.oldScormMining);
				}
				if ((insert.getScorm() != null) && (insert.getCourse() != null) && (insert.getUser() != null)) {
					scormLogMining.put(insert.getId(), insert);
				}
				if (insert.getScorm() == null) {
					this.logger.info("In Scorm_log_mining, scorm package not found for log: " + loadedItem.getId()
							+ " and cmid: " + loadedItem.getCmid() + " and info: " + loadedItem.getInfo());
				}
				if (insert.getCourse() == null) {
					this.logger.info("In Scorm_log_mining(scorm part), course not found for log: " + loadedItem.getId()
							+ " and course: " + loadedItem.getCourse());
				}
				if (insert.getUser() == null) {
					this.logger.info("In Scorm_log_mining(scorm part), user not found for log: " + loadedItem.getId()
							+ " and user: " + loadedItem.getUserid());
				}
			}
		}
		for (final ScormLogMining r : scormLogMining.values())
		{
			if (r.getUser() != null)
			{
				long duration = -1;
				final ArrayList<Long> times = users.get(r.getUser().getId());

				final int pos = times.indexOf(r.getTimestamp());
				if ((pos > -1) && (pos < (times.size() - 1))) {
					if (times.get(pos + 1) != 0) {
						duration = times.get(pos + 1) - times.get(pos);
					}
				}
				// All duration that are longer than one hour are cut to an hour
				if (duration > 3600) {
					duration = 3600;
				}
				r.setDuration(duration);
			}
		}

		return scormLogMining;
	}

	@Override
	public HashMap<Long, QuizMining> generateQuizMining() {

		final HashMap<Long, QuizMining> quiz_mining = new HashMap<Long, QuizMining>();

		for (final Quiz_LMS loadedItem : this.quizLms)
		{

			final QuizMining insert = new QuizMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setTitle(loadedItem.getName());
			insert.setTimeOpen(loadedItem.getTimeopen());
			insert.setTimeClose(loadedItem.getTimeclose());
			insert.setTimeCreated(loadedItem.getTimecreated());
			insert.setTimeModified(loadedItem.getTimemodified());
			insert.setQtype("quiz");
			insert.setPlatform(this.connector.getPlatformId());
			for (final Grade_items_LMS loadedItem2 : this.gradeItemsLms)
			{
				if ((loadedItem2.getIteminstance() != null) && (loadedItem2.getItemmodule() != null)) {
					this.logger.info("Iteminstance" + loadedItem2.getIteminstance() + " QuizId" + loadedItem.getId());
					if ((loadedItem.getId() == loadedItem2.getIteminstance().longValue())
							&& loadedItem2.getItemmodule().equals("quiz")) {
						insert.setMaxGrade(loadedItem2.getGrademax());
						break;
					}
				}
				else {
					this.logger.info("Iteminstance or Itemmodule not found for QuizId" + loadedItem.getId()
							+ " and type quiz and Iteminstance " + loadedItem2.getIteminstance() + " Itemmodule:"
							+ loadedItem2.getItemmodule());
				}
			}
			quiz_mining.put(insert.getId(), insert);
		}
		return quiz_mining;
	}

	@Override
	public HashMap<Long, AssignmentMining> generateAssignmentMining() {

		final HashMap<Long, AssignmentMining> assignment_mining = new HashMap<Long, AssignmentMining>();

		// Getting assignmentMining from Moodle's 'assignment'-table should be abandoned as quickly as possible due to
		// overlapping
		// primary-identifiers (assignment|assign)

		for (final Assignment_LMS loadedItem : this.assignmentLms)
		{
			final AssignmentMining insert = new AssignmentMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setTitle(loadedItem.getName());
			insert.setTimeOpen(loadedItem.getTimeavailable());
			insert.setTimeClose(loadedItem.getTimedue());
			insert.setTimeModified(loadedItem.getTimemodified());
			insert.setPlatform(this.connector.getPlatformId());
			for (final Grade_items_LMS loadedItem2 : this.gradeItemsLms)
			{
				if ((loadedItem2.getIteminstance() != null) && (loadedItem2.getItemmodule() != null))
				{
					this.logger.info("Iteminstance " + loadedItem2.getIteminstance() + " AssignmentId"
							+ loadedItem.getId());
					if ((loadedItem.getId() == loadedItem2.getIteminstance().longValue())
							&& loadedItem2.getItemmodule().equals("assignment")) {
						insert.setMaxGrade(loadedItem2.getGrademax());
						break;
					}
				}
				else {
					this.logger.info("Iteminstance or Itemmodule not found for AssignmentId" + loadedItem.getId()
							+ " and type quiz and Iteminstance " + loadedItem2.getIteminstance() + " Itemmodule:"
							+ loadedItem2.getItemmodule());
				}
			}
			assignment_mining.put(insert.getId(), insert);
		}

		final HashMap<Long, AssignmentMining> am_tmp = new HashMap<Long, AssignmentMining>();
		long moduleid = 0;
		for (final Modules_LMS loadedItem : this.modulesLms)
		{
			if (loadedItem.getName().equals("assign"))
			{
				moduleid = loadedItem.getId();
				break;
			}
		}

		for (final Assign_LMS loadedItem : this.assignLms)
		{
			final AssignmentMining insert = new AssignmentMining();

			final long course = loadedItem.getCourse();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setTitle(loadedItem.getName());
			insert.setTimeModified(loadedItem.getTimemodified());
			insert.setPlatform(this.connector.getPlatformId());

			for (final Grade_items_LMS loadedItem2 : this.gradeItemsLms)
			{
				if ((loadedItem2.getIteminstance() != null) && (loadedItem2.getItemmodule() != null))
				{
					this.logger.info("Iteminstance " + loadedItem2.getIteminstance() + " AssignmentId"
							+ loadedItem.getId());
					if ((loadedItem.getId() == loadedItem2.getIteminstance().longValue())
							&& loadedItem2.getItemmodule().equals("assignment")) {
						insert.setMaxGrade(loadedItem2.getGrademax());
						break;
					}
				}
				else {
					this.logger.info("Iteminstance or Itemmodule not found for AssignmentId" + loadedItem.getId()
							+ " and type quiz and Iteminstance " + loadedItem2.getIteminstance() + " Itemmodule:"
							+ loadedItem2.getItemmodule());
				}
			}

			for (final Course_Modules_LMS loadedItem3 : this.courseModulesLms)
			{
				if ((loadedItem3.getCourse() == course) && (loadedItem3.getModule() == moduleid))
				{
					insert.setTimeOpen(loadedItem3.getAvailablefrom());
					insert.setTimeClose(loadedItem3.getAvailableuntil());
					break;
				}
			}

			am_tmp.put(insert.getId(), insert);
		}

		assignment_mining.putAll(am_tmp);

		return assignment_mining;
	}

	@Override
	public HashMap<Long, ScormMining> generateScormMining() {

		final HashMap<Long, ScormMining> scorm_mining = new HashMap<Long, ScormMining>();

		for (final Scorm_LMS loadedItem : this.scormLms)
		{
			final ScormMining insert = new ScormMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setTitle(loadedItem.getName());
			insert.setTimeModified(loadedItem.getTimemodified());
			insert.setMaxGrade(loadedItem.getMaxgrade());
			insert.setPlatform(this.connector.getPlatformId());

			scorm_mining.put(insert.getId(), insert);
		}
		return scorm_mining;
	}

	@Override
	public HashMap<Long, QuizQuestionMining> generateQuizQuestionMining() {

		final HashMap<Long, QuizQuestionMining> quiz_question_mining = new HashMap<Long, QuizQuestionMining>();

		for (final Quiz_question_instances_LMS loadedItem : this.quizQuestionInstancesLms)
		{
			final QuizQuestionMining insert = new QuizQuestionMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setQuiz(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getQuiz()), this.quizMining,
					this.oldQuizMining);
			insert.setQuestion(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getQuestion()),
					this.questionMining, this.oldQuestionMining);
			insert.setPlatform(this.connector.getPlatformId());
			if ((insert.getQuiz() != null) && (insert.getQuestion() != null))
			{
				quiz_question_mining.put(insert.getQuestion().getId(), insert);
			}
			else
			{
				this.logger.info("In Quiz_question_mining, quiz not found: " + loadedItem.getQuiz());
			}
		}
		return quiz_question_mining;
	}

	@Override
	public HashMap<Long, QuestionMining> generateQuestionMining() {

		final HashMap<Long, QuestionMining> question_mining = new HashMap<Long, QuestionMining>();

		for (final Question_LMS loadedItem : this.questionLms)
		{
			final QuestionMining insert = new QuestionMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setTitle(loadedItem.getName());
			insert.setText(loadedItem.getQuestiontext());
			insert.setType(loadedItem.getQtype());
			insert.setTimeCreated(loadedItem.getTimecreated());
			insert.setPlatform(this.connector.getPlatformId());
			insert.setTimeModified(loadedItem.getTimemodified());

			question_mining.put(insert.getId(), insert);
		}
		return question_mining;
	}

	@Override
	public HashMap<Long, QuizUserMining> generateQuizUserMining() {

		final HashMap<Long, QuizUserMining> quiz_user_mining = new HashMap<Long, QuizUserMining>();
		for (final Grade_grades_LMS loadedItem : this.gradeGradesLms)
		{
			final QuizUserMining insert = new QuizUserMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setPlatform(this.connector.getPlatformId());
			if (loadedItem.getFinalgrade() != null) {
				insert.setFinalGrade(loadedItem.getFinalgrade());
			}
			if (loadedItem.getRawgrade() != null) {
				insert.setRawGrade(loadedItem.getRawgrade());
			}
			if (loadedItem.getTimemodified() != null) {
				insert.setTimeModified(loadedItem.getTimemodified());
			}
			insert.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid()), this.userMining,
					this.oldUserMining);

			for (final Grade_items_LMS loadedItem2 : this.gradeItemsLms)
			{
				if ((loadedItem2.getId() == loadedItem.getItemid()) && (loadedItem2.getIteminstance() != null)) {
					insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem2.getCourseid()),
							this.courseMining, this.oldCourseMining);
					insert.setQuiz(Long.valueOf(this.connector.getPrefix() + "" + loadedItem2.getIteminstance()),
							this.quizMining, this.oldQuizMining);
					if ((insert.getQuiz() != null) && (insert.getUser() != null)) {
						quiz_user_mining.put(insert.getId(), insert);
					}
					else {
						this.logger.info("In Quiz_user_mining, quiz not found for: Iteminstance: "
								+ loadedItem2.getIteminstance() + " Itemmodule: " + loadedItem2.getItemmodule()
								+ " course: " + loadedItem2.getCourseid() + " user: " + loadedItem.getUserid());
					}
				}
			}
		}
		return quiz_user_mining;
	}

	@Override
	public HashMap<Long, ResourceMining> generateResourceMining() {

		final HashMap<Long, ResourceMining> resource = new HashMap<Long, ResourceMining>();

		for (final Resource_LMS loadedItem : this.resourceLms)
		{
			final ResourceMining insert = new ResourceMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			// insert.setType(loadedItem.getType());
			insert.setTitle(loadedItem.getName());
			insert.setPlatform(this.connector.getPlatformId());

			// Get time of creation

			insert.setTimeModified(loadedItem.getTimemodified());

			resource.put(insert.getId(), insert);
		}

		for (final Log_LMS loadedItem : this.logLms)
		{
			if ((resource.get(loadedItem.getCmid()) != null)
					&& ((resource.get(loadedItem.getCmid()).getTimeCreated() == 0) || (resource.get(
							loadedItem.getCmid()).getTimeCreated() > loadedItem.getTime())))
			{
				resource.get(loadedItem.getCmid()).setTimeCreated(loadedItem.getTime());
			}
		}
		return resource;
	}

	@Override
	public HashMap<Long, ResourceLogMining> generateResourceLogMining() {
		final HashMap<Long, ResourceLogMining> resourceLogMining = new HashMap<Long, ResourceLogMining>();
		// A HashMap of list of timestamps. Every key represents one user, the according value is a list of his/her
		// requests times.
		final HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();

		for (final Log_LMS loadedItem : this.logLms)
		{

			final long uid = Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid());

			// Creates a list of time stamps for every user indicating requests
			// We later use this lists to determine the time a user accessed a resource
			if (users.get(uid) == null)
			{
				// if the user isn't already listed, create a new key
				final ArrayList<Long> times = new ArrayList<Long>();
				times.add(loadedItem.getTime());
				users.put(uid, times);
			}
			else
			{
				final ArrayList<Long> times = users.get(uid);
				if (loadedItem.getAction() == "login") {
					times.add(0L);
				}
				if (!times.contains(loadedItem.getTime())) {
					times.add(loadedItem.getTime());
				}
				users.put(uid, times);
			}

			if (loadedItem.getModule().equals("resource")) {
				final ResourceLogMining insert = new ResourceLogMining();
				insert.setPlatform(this.connector.getPlatformId());

				insert.setId(resourceLogMining.size() + 1 + this.resourceLogMax);

				insert.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid()), this.userMining,
						this.oldUserMining);
				insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()),
						this.courseMining, this.oldCourseMining);
				insert.setAction(loadedItem.getAction());

				if (loadedItem.getInfo().matches("[0-9]+")) {
					insert.setResource(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getInfo()),
							this.resourceMining, this.oldResourceMining);
				}
				insert.setTimestamp(loadedItem.getTime());
				if ((insert.getResource() == null) && !(loadedItem.getAction().equals("view all"))) {
					this.logger.info("In Resource_log_mining, resource not found for log: " + loadedItem.getId()
							+ " and cmid: " + loadedItem.getCmid() + " and info: " + loadedItem.getInfo()
							+ " and action: " + loadedItem.getAction());
				}
				if ((insert.getCourse() != null) && (insert.getResource() != null) && (insert.getUser() != null)) {
					resourceLogMining.put(insert.getId(), insert);
				}

			}
		}
		// For
		for (final ResourceLogMining r : resourceLogMining.values())
		{
			if (r.getUser() != null)
			{
				long duration = -1;
				final ArrayList<Long> times = users.get(r.getUser().getId());

				final int pos = times.indexOf(r.getTimestamp());
				if ((pos > -1) && (pos < (times.size() - 1))) {
					if (times.get(pos + 1) != 0) {
						duration = times.get(pos + 1) - times.get(pos);
					}
				}
				// All duration that are longer than one hour are cut to an hour
				if (duration > 3600) {
					duration = 3600;
				}
				r.setDuration(duration);
			}
		}
		return resourceLogMining;
	}

	@Override
	public HashMap<Long, UserMining> generateUserMining() {

		final HashMap<Long, UserMining> user_mining = new HashMap<Long, UserMining>();

		for (final User_LMS loadedItem : this.userLms)
		{

			final UserMining insert = new UserMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));

			insert.setLastLogin(loadedItem.getLastlogin());
			insert.setFirstAccess(loadedItem.getFirstaccess());
			insert.setLastAccess(loadedItem.getLastaccess());
			insert.setCurrentLogin(loadedItem.getCurrentlogin());
			insert.setPlatform(this.connector.getPlatformId());
			insert.setLogin(Encoder.createMD5(loadedItem.getUsername()));

			user_mining.put(insert.getId(), insert);
		}
		return user_mining;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.lemo.dms.connectors.moodle_2_3.ExtractAndMap#generateWikiLogMining()
	 */
	@Override
	public HashMap<Long, WikiLogMining> generateWikiLogMining() {
		final HashMap<Long, WikiLogMining> wikiLogMining = new HashMap<Long, WikiLogMining>();
		final HashMap<Long, ArrayList<Long>> users = new HashMap<Long, ArrayList<Long>>();
		final HashMap<Long, Course_Modules_LMS> couMod = new HashMap<Long, Course_Modules_LMS>();

		for (final Course_Modules_LMS cm : this.courseModulesLms)
		{
			couMod.put(cm.getId(), cm);
		}

		for (final Log_LMS loadedItem : this.logLms) {

			final long uid = Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid());

			// Creates a list of time stamps for every user indicating requests
			// We later use this lists to determine the time a user accessed a resource
			if (users.get(uid) == null)
			{
				// if the user isn't already listed, create a new key
				final ArrayList<Long> times = new ArrayList<Long>();
				times.add(loadedItem.getTime());
				users.put(uid, times);
			}
			else
			{
				final ArrayList<Long> times = users.get(uid);
				if (loadedItem.getAction() == "login") {
					times.add(0L);
				}
				if (!times.contains(loadedItem.getTime())) {
					times.add(loadedItem.getTime());
				}
				users.put(uid, times);
			}

			if (loadedItem.getModule().equals("wiki"))
			{
				final WikiLogMining insert = new WikiLogMining();

				insert.setId(wikiLogMining.size() + 1 + this.wikiLogMax);
				if (couMod.get(loadedItem.getCmid()) != null) {
					insert.setWiki(
							Long.valueOf(this.connector.getPrefix() + ""
									+ couMod.get(loadedItem.getCmid()).getInstance()), this.wikiMining,
							this.oldWikiMining);
				}

				insert.setPlatform(this.connector.getPlatformId());
				insert.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid()), this.userMining,
						this.oldUserMining);
				insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()),
						this.courseMining, this.oldCourseMining);
				insert.setAction(loadedItem.getAction());
				insert.setTimestamp(loadedItem.getTime());

				if ((insert.getUser() != null) && (insert.getCourse() != null) && (insert.getWiki() != null)) {
					wikiLogMining.put(insert.getId(), insert);
				}
			}
		}

		for (final WikiLogMining r : wikiLogMining.values())
		{
			if (r.getUser() != null)
			{
				long duration = -1;
				final ArrayList<Long> times = users.get(r.getUser().getId());

				final int pos = times.indexOf(r.getTimestamp());
				if ((pos > -1) && (pos < (times.size() - 1))) {
					if (times.get(pos + 1) != 0) {
						duration = times.get(pos + 1) - times.get(pos);
					}
				}
				// All duration that are longer than one hour are cut to an hour
				if (duration > 3600) {
					duration = 3600;
				}
				r.setDuration(duration);
			}
		}
		return wikiLogMining;
	}

	@Override
	public HashMap<Long, WikiMining> generateWikiMining() {

		final HashMap<Long, WikiMining> wiki_mining = new HashMap<Long, WikiMining>();

		for (final Wiki_LMS loadedItem : this.wikiLms)
		{
			final WikiMining insert = new WikiMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setTitle(loadedItem.getName());
			insert.setSummary(loadedItem.getSummary());
			insert.setTimeModified(loadedItem.getTimemodified());
			insert.setPlatform(this.connector.getPlatformId());
			wiki_mining.put(insert.getId(), insert);
		}
		for (final Log_LMS loadedItem : this.logLms)
		{
			if (loadedItem.getModule().equals("Wiki")
					&& (wiki_mining.get(loadedItem.getCmid()) != null)
					&& ((wiki_mining.get(loadedItem.getCmid()).getTimeCreated() == 0) || (wiki_mining.get(
							loadedItem.getCmid()).getTimeCreated() > loadedItem.getTime())))
			{
				wiki_mining.get(loadedItem.getCmid()).setTimeCreated(loadedItem.getTime());
			}
		}
		return wiki_mining;
	}

	@Override
	public HashMap<Long, RoleMining> generateRoleMining() {
		// generate role tables
		final HashMap<Long, RoleMining> role_mining = new HashMap<Long, RoleMining>();

		for (final Role_LMS loadedItem : this.roleLms)
		{
			final RoleMining insert = new RoleMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setName(loadedItem.getName());
			insert.setShortname(loadedItem.getShortname());
			insert.setDescription(loadedItem.getDescription());
			insert.setSortOrder(loadedItem.getSortorder());
			insert.setPlatform(this.connector.getPlatformId());

			role_mining.put(insert.getId(), insert);
		}
		return role_mining;
	}

	@Override
	public HashMap<Long, LevelMining> generateLevelMining() {
		final HashMap<Long, LevelMining> level_mining = new HashMap<Long, LevelMining>();

		for (final CourseCategories_LMS loadedItem : this.courseCategoriesLms)
		{
			final LevelMining insert = new LevelMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setTitle(loadedItem.getTitle());
			insert.setPlatform(this.connector.getPlatformId());
			insert.setDepth(loadedItem.getDepth());
			level_mining.put(insert.getId(), insert);

		}
		return level_mining;
	}

	@Override
	public HashMap<Long, LevelAssociationMining> generateLevelAssociationMining() {
		final HashMap<Long, LevelAssociationMining> level_association = new HashMap<Long, LevelAssociationMining>();

		for (final CourseCategories_LMS loadedItem : this.courseCategoriesLms)
		{
			final String[] s = loadedItem.getPath().split("/");
			if (s.length >= 3)
			{
				final LevelAssociationMining insert = new LevelAssociationMining();
				insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
				insert.setLower(Long.valueOf(this.connector.getPrefix() + "" + s[s.length - 1]), this.levelMining,
						this.oldLevelMining);
				insert.setUpper(Long.valueOf(this.connector.getPrefix() + "" + s[s.length - 2]), this.levelMining,
						this.oldLevelMining);
				insert.setPlatform(this.connector.getPlatformId());

				if ((insert.getLower() != null) && (insert.getUpper() != null)) {
					level_association.put(insert.getId(), insert);
				}
			}
		}
		return level_association;
	}

	@Override
	public HashMap<Long, LevelCourseMining> generateLevelCourseMining() {
		final HashMap<Long, LevelCourseMining> level_course = new HashMap<Long, LevelCourseMining>();

		for (final Context_LMS loadedItem : this.contextLms)
		{
			if ((loadedItem.getDepth() >= 4) && (loadedItem.getContextlevel() == 50))
			{
				final LevelCourseMining insert = new LevelCourseMining();

				final String[] s = loadedItem.getPath().split("/");
				insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
				insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getInstanceid()),
						this.courseMining, this.oldCourseMining);
				insert.setPlatform(this.connector.getPlatformId());
				for (final Context_LMS loadedItem2 : this.contextLms)
				{
					if ((loadedItem2.getContextlevel() == 40) && (loadedItem2.getId() == Integer.parseInt(s[3])))
					{
						insert.setLevel(Long.valueOf(this.connector.getPrefix() + "" + loadedItem2.getInstanceid()),
								this.levelMining, this.oldLevelMining);
						break;
					}
				}
				if ((insert.getLevel() != null) && (insert.getCourse() != null)) {
					level_course.put(insert.getId(), insert);
				}
			}
		}
		return level_course;
	}

	@Override
	public HashMap<Long, ChatMining> generateChatMining() {
		final HashMap<Long, ChatMining> chat_mining = new HashMap<Long, ChatMining>();

		for (final Chat_LMS loadedItem : this.chatLms)
		{
			final ChatMining insert = new ChatMining();

			insert.setId(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getId()));
			insert.setChatTime(loadedItem.getChattime());
			insert.setDescription(loadedItem.getDescription());
			insert.setTitle(loadedItem.getTitle());
			insert.setPlatform(this.connector.getPlatformId());
			insert.setCourse(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getCourse()), this.courseMining,
					this.oldCourseMining);

			if (insert.getCourse() != null) {
				chat_mining.put(insert.getId(), insert);
			}
		}

		return chat_mining;
	}

	@Override
	public HashMap<Long, ChatLogMining> generateChatLogMining() {
		final HashMap<Long, ChatLogMining> chatLogMining = new HashMap<Long, ChatLogMining>();

		for (final ChatLog_LMS loadedItem : this.chatLogLms)
		{
			final ChatLogMining insert = new ChatLogMining();
			insert.setId(chatLogMining.size() + 1 + this.chatLogMax);
			insert.setChat(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getChat_id()), this.chatMining,
					this.oldChatMining);
			insert.setMessage(loadedItem.getMessage());
			insert.setTimestamp(loadedItem.getTimestamp());
			insert.setPlatform(this.connector.getPlatformId());
			if (insert.getChat() != null) {
				insert.setCourse(insert.getChat().getCourse().getId(), this.courseMining, this.oldCourseMining);
			}
			insert.setDuration(0L);
			insert.setUser(Long.valueOf(this.connector.getPrefix() + "" + loadedItem.getUserid()), this.userMining,
					this.oldUserMining);

			if (insert.getUser() == null) {
				this.logger.info("In Chat_log_mining(chat part), user not found for log: " + loadedItem.getId()
						+ " and user: " + loadedItem.getUserid());
			}
			if (insert.getChat() == null) {
				this.logger.info("In Chat_log_mining(chat part), chat not found for log: " + loadedItem.getId()
						+ " and chat: " + loadedItem.getChat_id());
			}
			if ((insert.getChat() != null) && (insert.getUser() != null) && (insert.getCourse() != null)) {
				chatLogMining.put(insert.getId(), insert);
			}

		}
		return chatLogMining;
	}

}
