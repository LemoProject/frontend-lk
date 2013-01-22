package de.lemo.dms.connectors.moodle;

import java.util.HashMap;
import java.util.Map.Entry;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.google.common.collect.Maps;

import de.lemo.dms.db.DBConfigObject;

/**
 * Startup Hibernate and provide access to the singleton SessionFactory
 */
public class MoodleHibernateUtil {

    private static HashMap<DBConfigObject, SessionFactory> sessionFactories = Maps.newHashMap();

    public static SessionFactory getSessionFactory(DBConfigObject dbconfig) {
        SessionFactory sessionFactory = sessionFactories.get(dbconfig);
        if(sessionFactory == null) {
            sessionFactory = createSessionFactory(dbconfig);
            sessionFactories.put(dbconfig, sessionFactory);
        }
        return sessionFactory;
    }

    public static void closeSessionFactory(DBConfigObject dbconfig) {
        SessionFactory sessionFactory = sessionFactories.remove(dbconfig);
        if(sessionFactory != null) {
            sessionFactory.close();
        }
    }

    private static SessionFactory createSessionFactory(DBConfigObject dbConfig) {
        Configuration cfg = new Configuration();

        // add properties from files
        for(Entry<String, String> entry : dbConfig.getProperties().entrySet()) {
            cfg.setProperty(entry.getKey(), entry.getValue());
        }

        // add mapping classes
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/CourseLMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/CourseModulesLMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/ForumDiscussionsLMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/ForumPostsLMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/ForumLMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/GradeGradesLMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/GroupsMembersLMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/GroupsLMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/LogLMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/QuestionStatesLMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/QuestionLMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/QuizGradesLMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/QuizLMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/QuizQuestionInstancesLMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/WikiLMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/ResourceLMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/UserLMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/RoleLMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/ContextLMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/RoleAssignmentsLMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/AssignmentLMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/AssignmentSubmissionLMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/ScormLMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/GradeItemsLMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/ChatLMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/ChatLogLMS.hbm.xml");
        cfg.addResource("de/lemo/dms/connectors/moodle/moodleDBclass/CourseCategoriesLMS.hbm.xml");

        return cfg.buildSessionFactory();
    }

}
