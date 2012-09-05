package de.lemo.dms.service;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.hibernate.Session;

import de.lemo.dms.db.EQueryType;
import de.lemo.dms.db.miningDBclass.CourseMining;
import de.lemo.dms.processing.resulttype.CourseObject;
import de.lemo.dms.processing.resulttype.ResultListCourseObject;

@Path("users")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceUserInformation extends BaseService {


    @GET
    @Path("/{uid}/courses")
    public ResultListCourseObject getCoursesByUser(@PathParam("uid") long id, @QueryParam("course_count") Long count,
            @QueryParam("course_offset") Long offset) {
        logger.info("## " + id);
        ArrayList<CourseObject> courses = new ArrayList<CourseObject>();

        // Set up db-connection
        
        Session session = dbHandler.getMiningSession();

        @SuppressWarnings("unchecked")
        ArrayList<Long> cu = (ArrayList<Long>) dbHandler.performQuery(session, EQueryType.SQL,
                "Select course_id from course_user where user_id=" + id);

        String query = "";
        for(int i = 0; i < cu.size(); i++) {
            if(i == 0)
                query += "(" + cu.get(i);
            else
                query += "," + cu.get(i);
            if(i == cu.size() - 1)
                query += ")";
        }

        if(cu.size() > 0) {
            @SuppressWarnings("unchecked")
            ArrayList<CourseMining> ci = (ArrayList<CourseMining>) dbHandler.performQuery(session, EQueryType.HQL,
                    "from CourseMining where id in " + query);
            for(int i = 0; i < ci.size(); i++) {
                @SuppressWarnings("unchecked")
                ArrayList<Long> parti = (ArrayList<Long>) dbHandler.performQuery(session, EQueryType.HQL,
                        "Select count(DISTINCT user) from CourseUserMining where course=" + ci.get(i).getId());
                @SuppressWarnings("unchecked")
                ArrayList<Long> latest = (ArrayList<Long>) dbHandler.performQuery(session, EQueryType.HQL,
                        "Select max(timestamp) FROM ResourceLogMining x WHERE x.course=" + ci.get(i).getId());
                @SuppressWarnings("unchecked")
                ArrayList<Long> first = (ArrayList<Long>) dbHandler.performQuery(session, EQueryType.HQL,
                        "Select min(timestamp) FROM ResourceLogMining x WHERE x.course=" + ci.get(i).getId());
                Long c_pa = 0L;
                if(parti.size() > 0 && parti.get(0) != null)
                    c_pa = parti.get(0);
                Long c_la = 0L;
                if(latest.size() > 0 && latest.get(0) != null)
                    c_la = latest.get(0);
                Long c_fi = 0L;
                if(first.size() > 0 && first.get(0) != null)
                    c_fi = first.get(0);
                CourseObject co = new CourseObject(ci.get(i).getId(), ci.get(i).getShortname(), ci.get(i).getTitle(),
                        c_pa, c_la, c_fi);
                courses.add(co);
            }
        }
        dbHandler.closeSession(session);
        return new ResultListCourseObject(courses);
    }

}
